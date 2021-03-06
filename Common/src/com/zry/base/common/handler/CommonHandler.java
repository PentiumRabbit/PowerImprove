package com.zry.base.common.handler;

import android.app.Fragment;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 通用的Handler
 * ZhaoRuYang
 * 2015/11/28 20:12
 */
public class CommonHandler<T extends IHandlerMessage> extends Handler {
    private static final String TAG = CommonHandler.class.getSimpleName();


    private WeakReference<T> reference;

    public CommonHandler(T t) {
        reference = new WeakReference<T>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (reference == null) {
            return;
        }
        T t = reference.get();
        if (t == null) {
            return;
        }
        if (t instanceof Fragment) {
            Fragment fragment = (Fragment) t;
            if (fragment.getActivity() == null || !(fragment).isAdded()) {
                return;
            }
        }
        t.handlerCallback(msg);
    }

}

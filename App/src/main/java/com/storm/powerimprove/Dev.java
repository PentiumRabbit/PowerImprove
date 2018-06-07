package com.storm.powerimprove;

import android.content.Context;

import com.android.base.common.utils.Logger;
import com.android.base.common.value.ValueTAG;

import java.lang.reflect.Method;

/**
 * @author : ZhaoRuYang
 * @date : 2018/6/7
 */
public class Dev {

    public static void startDevUI(Context context) {
        if (!AppArg.IS_DEBUG) {
            return;
        }

        try {
            Class<?> clazz = Class.forName("com.dev.devmodule.DevActivity");
            Method method = clazz.getMethod("start", Context.class);
            method.invoke(null, context);
        } catch (Exception e) {
            Logger.w(ValueTAG.EXCEPTION, "dev", e);
        }

    }

}

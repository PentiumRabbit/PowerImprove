package com.storm.powerimprove;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.android.base.common.value.ValueTAG;
import com.android.base.utils.Logger;
import com.storm.powerimprove.utils.LogRecondUtil;

import java.io.IOException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private static final String TAG = ApplicationTest.class.getName();

    public ApplicationTest() {
        super(Application.class);
    }

    public void testLogCmd() {
        Logger.i(TAG, "测试log命令");
        try {
            LogRecondUtil.logOutput();
        } catch (IOException e) {
            Logger.e(ValueTAG.EXCEPTION, "*****EXCEPTION*****\n", e);
        }
    }
}
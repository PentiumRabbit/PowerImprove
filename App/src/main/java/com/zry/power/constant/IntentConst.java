package com.zry.power.constant;


import com.zry.power.AppArg;

/**
 * Intent动作
 * <p/>
 * ZhaoRuYang
 * 3/18/16 1:37 PM
 */
public class IntentConst {

    public interface Action {
        String jump = AppArg.PACKAGE_ID + ".JUMP";
        String matchRemind = AppArg.PACKAGE_ID + ".BROADCAST_ALARM";
    }

    public interface Type {

        String from        = "intent_from";
        String requestCode = "request_code";
    }


    public interface From {
        String notifyMatch  = "notify_match";
        String logoActivity = "LogoActivity";
    }

}

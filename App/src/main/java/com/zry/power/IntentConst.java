package com.zry.power;

/**
 * Intent动作
 * <p/>
 * ZhaoRuYang
 * 3/18/16 1:37 PM
 */
public class IntentConst {

    public interface Action {
        String jump = AppArg.PACKAGE_ID + ".JUMP";
        String push = AppArg.PACKAGE_ID + ".PUSH";

    }

    static int typeAnchor = 0;
    public interface Type {
        int jump = typeAnchor++;
    }


    public interface From {

    }

    public interface To {

    }

    public interface Paras {

    }

}

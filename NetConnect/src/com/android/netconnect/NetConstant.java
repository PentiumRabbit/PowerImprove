package com.android.netconnect;

/**
 * 网络请求中的常量
 *
 * @author ----zhaoruyang----
 * @data: 2014/12/25
 */
public interface NetConstant {

    String TAG_CONNECT_NET = "storm.market.connect_net";


    /**
     * *************************************************************************
     * <p/>
     * ------------------------------网络请求参数-----------------------------------
     * <p/>
     * *************************************************************************
     */

    String PARAM_SCREEN = "screen";

    String PARAM_VER = "ver";

    String PARAM_PLATF = "platf";

    String PARAM_IMEI = "imei";


    /**
     * *************************************************************************
     * <p/>
     * ------------------------------网络错误码-----------------------------------
     * <p/>
     * *************************************************************************
     */


    /*请求成功,但没有数据*/
    int ERROR_NO_MSG = -1;

    /*请求异常*/
    int ERROR_EXCEPTION = -2;


    /**
     * *************************************************************************
     * <p/>
     * ------------------------------Net连接模式-----------------------------------
     * <p/>
     * *************************************************************************
     */
    int MODE_CONNECT_URL = 1;

    int MODE_CONNECT_CLIENT = 2;


    /**
     * *************************************************************************
     * <p/>
     * ------------------------------NetCache的缓存标志--------------------------
     * <p/>
     * *************************************************************************
     */

    /* 不缓存 */
    int CACHE_TAG_NO_CACHE = 0x00000001;
    /* 退出清理 */
    int CACHE_TAG_CLEAR = 0x00000002;
    /* 一直保留 */
    int CACHE_TAG_SAVE = 0x00000003;

}

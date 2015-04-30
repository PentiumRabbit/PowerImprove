/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.android.base.GloableParams;

import com.android.base.common.SharedPref.Impl.CommonSettingImpl;
import com.android.base.common.SharedPref.SharedPref;
import com.android.base.common.Observer.CommonObserver;
import com.android.base.common.Observer.ObserverType;
import com.android.base.utils.LogUtil;
import com.android.base.utils.SysInfoUtil;

/**
 * 获取网络变化
 *
 * @author ----zhaoruyang----
 * @data: 2015/1/9
 */
public class NetStatusReceiver extends BroadcastReceiver {
    private static final String TAG = "NetStatusReceiver";
    public static final int NET_NONE = 0;

    public static final int NET_WIFI = 1;
    public static final int NET_MOBILE = 2;
    public static final int NET_WIFI_CHANGE = 3;

    @Override
    public void onReceive(Context context, Intent intent) {

        //获取手机的连接服务管理器，这里是连接管理器类
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        NetworkInfo.State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();


        if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED == mobileState) {
            if (GloableParams.currentNetStatus == NET_MOBILE) {
                return;
            }
            GloableParams.currentNetStatus = NET_MOBILE;
            LogUtil.i(TAG, "手机网络连接成功!");
            CommonObserver.getInstance().notifyListener(ObserverType.NET_STATUS, NET_MOBILE);
        } else if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED == wifiState
                && NetworkInfo.State.CONNECTED != mobileState) {

            String wifiSSID = SysInfoUtil.getWifiSSID(context);

            LogUtil.i(TAG, "SSID : " + wifiSSID);

            if (GloableParams.currentNetStatus != NET_WIFI) {
                GloableParams.currentNetStatus = NET_WIFI;
                LogUtil.i(TAG, "无线网络连接成功！");
                CommonObserver.getInstance().notifyListener(ObserverType.NET_STATUS, NET_WIFI);
            } else {
                //判断wifi是否变化
                String old_SSID = SharedPref.getSettingString(context, CommonSettingImpl.RECORD_WIFI_SSID, "");

                if (!TextUtils.equals(old_SSID, wifiSSID)) {
                    LogUtil.i(TAG, "无线网络发生变化！");
                    CommonObserver.getInstance().notifyListener(ObserverType.NET_STATUS, NET_WIFI_CHANGE);
                }

            }
            SharedPref.setSettingString(context, CommonSettingImpl.RECORD_WIFI_SSID,
                    wifiSSID);

        } else if (wifiState != null && mobileState != null
                && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED != mobileState) {
            if (GloableParams.currentNetStatus == NET_NONE) {
                return;
            }
            GloableParams.currentNetStatus = NET_NONE;
            LogUtil.i(TAG, "手机没有网络...");
            CommonObserver.getInstance().notifyListener(ObserverType.NET_STATUS, NET_NONE);
        }
    }
}

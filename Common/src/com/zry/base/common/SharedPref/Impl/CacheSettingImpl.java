/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.zry.base.common.SharedPref.Impl;

import com.zry.base.common.SharedPref.ISettingsField;

import android.content.Context;

/**
 * CacheSettingImpl
 *
 * @author ----zhaoruyang----
 * @version: V1.0
 * @data: 2014年11月10日 下午12:01:42
 */

public enum CacheSettingImpl implements ISettingsField {
    report_active,

    ;

    @Override
    public String getPreferenceName() {
        return "cache";
    }

    @Override
    public int getFileMode() {
        return Context.MODE_PRIVATE;
    }

}

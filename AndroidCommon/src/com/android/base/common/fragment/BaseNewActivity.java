package com.android.base.common.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @author ----zhaoruyang----
 * @data: 2015/2/6
 */
public class BaseNewActivity extends FragmentActivity {
    private static final String TAG = "BaseNewActivity";
    protected FragmentManager supportFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportFragmentManager = getSupportFragmentManager();
    }

    /**
     * 替换Fragment
     *
     * @param layout_id
     *         布局id
     * @param fragment
     *         替换的fragment
     * @param needBack
     *         是否添加到回退栈
     */
    protected void replaceFragment(int layout_id, Fragment fragment, boolean needBack) {
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();

        beginTransaction.replace(layout_id, fragment, fragment.getClass().getName());
        if (needBack) {
            beginTransaction.addToBackStack(fragment.getClass().getName());
        }
        beginTransaction.commitAllowingStateLoss();

    }
}
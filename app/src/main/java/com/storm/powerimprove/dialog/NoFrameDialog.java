
package com.storm.powerimprove.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.storm.powerimprove.R;


/**
 * 无边框对话框
 *
 * @author ----zhaoruyang----
 * @data: 2015/4/14
 */
public abstract class NoFrameDialog extends DialogFragment {
    public static final String BUNDLE_MSG = "bundle_msg";

    public static final int DIALOG_TYPE_RENAME = 1;

    public static final int DIALOG_TYPE_DEL = 2;

    public static final int DIALOG_TYPE_PASSWORD_SUCCESS = 3;

    protected OnDialogCallBack onDialogCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // //
        // 如果setCancelable()中参数为true，若点击dialog覆盖不到的activity的空白或者按返回键，则进行cancel，状态检测依次onCancel()和onDismiss()。如参数为false，则按空白处或返回键无反应。缺省为true
        setCancelable(true);
        // 可以设置dialog的显示风格，如style为STYLE_NO_TITLE，将被显示title。遗憾的是，我没有在DialogFragment中找到设置title内容的方法。theme为0，表示由系统选择合适的theme。
        int style = DialogFragment.STYLE_NO_TITLE;
        setStyle(style, R.style.DialogNoFullTheme);

    }

    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState);

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    /**
     * 不可取消
     */
    protected void setNoCancel() {
        Dialog dialog = getDialog();
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 设置对话框消息回调
     */
    public void setOnDialogCallback(OnDialogCallBack onDialogCallback) {
        this.onDialogCallback = onDialogCallback;
    }

    protected void showInput(final EditText editText) {
        editText.setFocusable(true);
        Dialog dialog = getDialog();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) editText.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        });

    }
}
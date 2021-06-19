package com.wangzs.app_base.dialog;

import android.content.Context;


public abstract class CommonDialog {
    protected Context mContext;
    protected AlertDialog mAlertDialog;

    public CommonDialog(Context context) {
        this.mContext = context;
        if (mAlertDialog == null) {
            if (fromBottom()) {
                mAlertDialog = new AlertDialog.Builder(mContext)
                        .setContentView(getContentView())
                        .fullWidth()
                        .fromBottom(true)
                        .create();
            } else {
                mAlertDialog = new AlertDialog.Builder(mContext)
                        .setContentView(getContentView())
                        .fullWidth()
                        .create();
            }
        }
    }

    protected boolean fromBottom() {
        return false;
    }

    public void show() {
        if (mAlertDialog != null) {
            mAlertDialog.show();
        }
    }

    protected void dismiss() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
    }

    protected boolean isShowing() {
        if (mAlertDialog != null) {
            return mAlertDialog.isShowing();
        }
        return false;
    }

    public void release() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }

    protected abstract int getContentView();
}
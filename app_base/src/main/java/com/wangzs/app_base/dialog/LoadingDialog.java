//package com.wangzs.app_base.dialog;
//
//import android.content.Context;
//
//import com.wangzs.app_base.R;
//
//
//public class LoadingDialog {
//
//    private Context mContext;
//    private AlertDialog mAlertDialog;
//
//    public LoadingDialog(Context context) {
//        this.mContext = context;
//    }
//
//    public void show() {
//        show(null);
//    }
//
//    public void show(String tips) {
//        if (mAlertDialog == null) {
//            mAlertDialog = new AlertDialog.Builder(mContext, R.style.dialog_translucent_theme)
//                    .setContentView(R.layout.layout_dialog_loading)
//                    .setCancelable(false)
//                    .setCanceledOnTouchOutside(false)
//                    .fullWidth()
//                    .create();
//        }
//        if (!StringUtils.isEmpty(tips)) {
//            mAlertDialog.setText(R.id.tv_dialog_loading_text, tips);
//        } else {
//            mAlertDialog.setText(R.id.tv_dialog_loading_text, mContext.getResources().getString(R.string.amicable_view_loading));
//        }
//        mAlertDialog.show();
//    }
//
//    public void dismiss() {
//        if (mAlertDialog != null && mAlertDialog.isShowing()) {
//            mAlertDialog.dismiss();
//        }
//    }
//
//    public boolean isShowing() {
//        return mAlertDialog != null && mAlertDialog.isShowing();
//    }
//
//    public void release() {
//        dismiss();
//        mAlertDialog = null;
//    }
//}
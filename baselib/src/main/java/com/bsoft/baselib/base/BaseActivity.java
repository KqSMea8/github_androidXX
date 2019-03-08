package com.bsoft.baselib.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bsoft.baselib.R;
import com.bsoft.baselib.util.EffectUtil;
import com.bsoft.baselib.util.ExitUtil;
import com.bsoft.baselib.util.ScreenUtil;

import com.bsoft.baselib.util.statusBarUtil.StatusBarUtil;
import com.bsoft.baselib.widget.AutoCardView;
import com.bsoft.baselib.widget.BsoftActionBar;
import com.bsoft.baselib.widget.dialog.LoadingDialog;
import com.bsoft.baselib.widget.loading.LoadViewHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import java.util.List;


public abstract  class BaseActivity extends RxAppCompatActivity {
    public BaseActivity baseActivity;
    public Context baseContext;
    LoadingDialog loadingDialog;
    protected RxPermissions rxPermissions;
    public LoadViewHelper viewHelper;
    public BsoftActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.baseActivity = this;
        this.baseContext = this;
        loadingDialog = new LoadingDialog();
        rxPermissions = new RxPermissions(this);
        setStatusBarColor();

    }

    protected void setStatusBarColor() {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this,true);    //在baseActivity 不起作用
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }


    }

    public abstract void findView();


    public void back() {
        hideKeyboard();
        finish();
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals("FrameLayout")) {
            view = new AutoFrameLayout(context, attrs);
        }

        if (name.equals("LinearLayout")) {
            view = new AutoLinearLayout(context, attrs);
        }

        if (name.equals("RelativeLayout")) {
            view = new AutoRelativeLayout(context, attrs);
        }
        if (name.equals("android.support.v7.widget.CardView")) {
            view = new AutoCardView(context, attrs);
        }

        return (View) (view != null ? view : super.onCreateView(name, context, attrs));
    }








    /**
     * 提示框（统一风格）
     */
    private Dialog alertDialog;

    public void showDialog(String title, String content, String confirmStr, String cancelstr, final View.OnClickListener confirmListener, final View.OnClickListener cancellistener) {
        showDialog(baseContext, title, content, null, confirmStr, cancelstr, confirmListener, cancellistener, false);
    }
    public void showDialog(String title, String content, final View.OnClickListener confirmListener, final View.OnClickListener cancellistener) {
        showDialog(baseContext, title, content, null, "确认", "取消", confirmListener, cancellistener, false);
    }
    public void showDialog(String title, String content, final View.OnClickListener confirmListener) {
        showDialog(baseContext, title, content, null, "确认", "取消", confirmListener, null, false);
    }
    public void showDialog(String title, String content, final View.OnClickListener confirmListener, boolean isShowCancel) {
        showDialog(baseContext, title, content, null, "确认", isShowCancel ? "取消" : "", confirmListener, null, false);
    }
    public void showDialog(String title, String content, String info, final View.OnClickListener confirmListener, boolean isShowCancel) {
        showDialog(baseContext, title, content, info, "确认", isShowCancel ? "取消" : "", confirmListener, null, false);
    }

    public void showDialog(Context baseContext, String title, String content, String info,
                           String confirmStr, String cancelstr,
                           final View.OnClickListener confirmListener,
                           final View.OnClickListener cancellistener,
                           boolean isCancelable) {
        if (alertDialog != null && alertDialog.isShowing()){ return;}
        alertDialog = new Dialog(baseContext, R.style.alertDialogTheme);
        View viewDialog = LayoutInflater.from(baseContext).inflate(R.layout.dialog_alert, null);
        // 设置对话框的宽高
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ScreenUtil.getScreenWidth() * 85 / 100, LinearLayout.LayoutParams.WRAP_CONTENT);
        alertDialog.setCancelable(isCancelable);
        alertDialog.setCanceledOnTouchOutside(isCancelable);
        alertDialog.setContentView(viewDialog, layoutParams);

        //确定按钮
        TextView tv_confirm = (TextView) viewDialog.findViewById(R.id.tv_confirm);
        //取消按钮，默认隐藏
        TextView tv_cancel = (TextView) viewDialog.findViewById(R.id.tv_cancel);
        EffectUtil.addClickEffect(tv_confirm);
        EffectUtil.addClickEffect(tv_cancel);

        if (!TextUtils.isEmpty(confirmStr) && !TextUtils.isEmpty(cancelstr)) {
            viewDialog.findViewById(R.id.tv_divider).setVisibility(View.VISIBLE);
//			tv_confirm.setBackgroundResource(R.drawable.item_selected_white);
//			tv_cancel.setBackgroundResource(R.drawable.item_selected_white);
        }
        if (!TextUtils.isEmpty(confirmStr)) {
            tv_confirm.setText(confirmStr);
        }
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmListener != null) {
                    confirmListener.onClick(v);
                    dismissAlertDialog();
                }
                else {
                    dismissAlertDialog();
                }
            }
        });
//        RxUtil.setOnClickListener(tv_confirm, v -> {
//            if (confirmListener != null) {
//                confirmListener.onClick(v);
//                dismissAlertDialog();
//            }
//            else {
//                dismissAlertDialog();
//            }
//        });


        if (!TextUtils.isEmpty(cancelstr)) {
            tv_cancel.setVisibility(View.VISIBLE);
            tv_cancel.setText(cancelstr);
        }else{
            tv_cancel.setVisibility(View.GONE);
        }
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancellistener != null) {
                    cancellistener.onClick(v);
                    dismissAlertDialog();
                }
                else {
                    dismissAlertDialog();
                }
            }
        });
//        RxUtil.setOnClickListener(tv_cancel, v -> {
//            if (cancellistener != null) {
//                cancellistener.onClick(v);
//                dismissAlertDialog();
//            }
//            else {
//                dismissAlertDialog();
//            }
//        });


        TextView tv_content = (TextView) alertDialog.findViewById(R.id.tv_content);
        //标题默认隐藏
        if (!TextUtils.isEmpty(title)) {
            TextView tv_title = (TextView) alertDialog.findViewById(R.id.tv_title);
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }
        if (!TextUtils.isEmpty(info)) {
            TextView tvInfo = (TextView) alertDialog.findViewById(R.id.tv_info);
            tvInfo.setVisibility(View.VISIBLE);
            tvInfo.setText(info);
        }
        if (!TextUtils.isEmpty(content)) {
            tv_content.setText(content);
        }
        alertDialog.show();
    }
    /**
     * 底部列表提示框
     */
    public void showListDialog(String title, List<String> btn_strs, final View.OnClickListener listener) {
        final Dialog dialog = new Dialog(baseContext, R.style.dialog_fullscreen);
        dialog.show();
        View viewDialog = LayoutInflater.from(baseContext).inflate(R.layout.layout_alert_list_dialog, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ScreenUtil.getScreenWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(viewDialog, lp);
        if (!TextUtils.isEmpty(title)) {
            TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
            tv_title.setText(title);
        }
        LinearLayout parent = (LinearLayout) viewDialog.findViewById(R.id.dialogLayout);
        parent.removeAllViews();
        int length = btn_strs.size();
        for (int i = 0; i < length; i++) {
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(-1, -2);
            params1.rightMargin = 1;
            final TextView tv = new TextView(baseContext);
            tv.setLayoutParams(params1);
            tv.setTextSize(18);
            tv.setText(btn_strs.get(i));
            tv.setTag(i);
            int pad = baseContext.getResources().getDimensionPixelSize(R.dimen.padding10);
            tv.setPadding(pad, pad, pad, pad);
            tv.setSingleLine(true);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(baseContext.getResources().getColor(R.color.blue));
            if (i != length - 1) {
                tv.setBackgroundResource(R.drawable.dialog_select);
            }
            else {
                tv.setBackgroundResource(R.drawable.dialog_item_selector);
            }
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                    if (listener != null){
                        listener.onClick(arg0);}
                }
            });
            parent.addView(tv);
            if (i != length - 1) {
                TextView divider = new TextView(baseContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, (int) 1);
                divider.setLayoutParams(params);
                divider.setBackgroundResource(R.color.divider2bg);
                parent.addView(divider);
            }
        }
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        viewDialog.findViewById(R.id.btn_cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                    }
                });
    }
    public void dismissAlertDialog(){
        if(alertDialog != null && alertDialog.isShowing()){
            alertDialog.dismiss();
        }
    }
    @Override
    protected void onDestroy() {
        dismissAlertDialog();
        ExitUtil.clear();
        dismissLoadingDialog();
        super.onDestroy();
    }

    public int getDialogWidth() {
        return ScreenUtil.getScreenWidth() * 75 / 100;
    }

    public void showErrorView() {
        showErrorView("加载失败，点击重试");
    }

    public void showErrorView(String error) {
        if(null!=viewHelper){
            viewHelper.showError(error);
        }
    }

    public void showEmptyView() {
        if(null!=viewHelper){
            viewHelper.showEmpty();
        }
    }
    public void showEmptyView(View view) {
        if(null!=viewHelper){
            viewHelper.showEmpty(view);
        }
    }
    public void showLoadingView() {
        if(null!=viewHelper){
            viewHelper.showLoading();
        }
    }

    public void restoreView() {
        if(null!=viewHelper){
            viewHelper.restore();
        }
    }

    public void hideKeyboard(){
        if (null != getCurrentFocus()
                && null != getCurrentFocus().getWindowToken()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            // 隐藏软键盘
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public void showKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(getWindow().getDecorView(), InputMethodManager.SHOW_FORCED);
    }
    public void showKeyboard(EditText editText){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(editText.getWindowToken(), 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(isLoadingDialogShowing()){
                finish();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }

    public void showToast(String text) {
        if (text != null && !text.trim().equals("")) {
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            //  ToastUtil.showAtCenter(baseContext,text);
            // EToastUtils.show(text);
        }
    }
    public void showToast(@StringRes int resId) {
        Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
    }

    public void showLoadingDialog(){
        showLoadingDialog(null);
    }
    public void showLoadingDialog(String msg){
        loadingDialog.build(this).show(msg);
    }
    public void dismissLoadingDialog(){
        loadingDialog.dismiss();
    }

    public boolean isLoadingDialogShowing(){
        return loadingDialog.isShowing();
    }



    public int getActionBarBg() {
        return R.color.colorPrimary;
    }

    public int getActionBarTitleColor() {
        return R.color.black_text;
    }

    public int getActionBarActionColor() {
        return R.color.colorAccent;
    }

    public void findActionBar() {
        actionBar = (BsoftActionBar) findViewById(R.id.actionbar);
        actionBar.setBackGround(ContextCompat.getColor(baseContext, getActionBarBg()));
        actionBar.setTitleColor(ContextCompat.getColor(baseContext, getActionBarTitleColor()));
        actionBar.setActionTxtColor(ContextCompat.getColor(baseContext, getActionBarActionColor()));

    }




}

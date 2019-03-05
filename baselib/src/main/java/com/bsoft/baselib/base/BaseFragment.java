package com.bsoft.baselib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.bsoft.baselib.AppContext;
import com.bsoft.baselib.widget.loading.LoadViewHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxFragment;

public  abstract class BaseFragment extends RxFragment {



    public View mainView;
    protected RxPermissions rxPermissions;
    public Context baseContext;
    public BaseActivity baseActivity;
   // public AppApplication application;
    public LoadViewHelper viewHelper;
    public boolean isCreated = false;//界面是否加载完成
    public boolean isLoaded = false;//数据是否加载过了
    public boolean isReceiver = false;//是否可见
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isReceiver = isVisibleToUser;
        if (!isCreated) {
            return;
        }
        if (isVisibleToUser) {
            startHint();
        } else {
            endHint();
        }
    }

    public abstract void startHint();

    public abstract void endHint();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseContext = getActivity();
        baseActivity = (BaseActivity) getActivity();
        rxPermissions = new RxPermissions(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mainView == null) {
            mainView = inflater.inflate(getLayoutRes(), container, false);
        }

        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isCreated = true;
        initView(mainView);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
    public void showEmptyViewLayoutId(int layoutId) {
        if(null!=viewHelper){
            viewHelper.showEmpty(layoutId);
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
        if (null != getActivity().getCurrentFocus()
                && null != getActivity().getCurrentFocus().getWindowToken()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            // 隐藏软键盘
            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public void showKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(getActivity().getWindow().getDecorView(), InputMethodManager.SHOW_FORCED);
    }




    public void showToast(String text) {
        if (text != null && !text.trim().equals("")) {
            Toast.makeText(AppContext.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }
    public void showToast(@StringRes int resId) {
        Toast.makeText(AppContext.getContext(), resId, Toast.LENGTH_SHORT).show();
    }
    /**
     * 返回布局 resId
     *
     * @return layoutId
     */
    protected abstract int getLayoutRes();
    /**
     * 初始化view
     *
     * @param mainView
     */
    protected abstract void initView(View mainView);
}

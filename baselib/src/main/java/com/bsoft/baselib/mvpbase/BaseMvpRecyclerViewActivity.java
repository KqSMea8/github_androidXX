package com.bsoft.baselib.mvpbase;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bsoft.baselib.base.BaseRecyclerViewActivity;


public abstract class BaseMvpRecyclerViewActivity <V extends IView, T extends BasePresenter<V>> extends BaseRecyclerViewActivity implements IView {

    protected T mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);//因为之后所有的子类都要实现对应的View接口
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showLoading() {
        if(actionBar!=null) {
            actionBar.startTitleRefresh();
        }
        showLoadingDialog();
    }

    @Override
    public void dismissLoading() {
        if(actionBar!=null) {
            actionBar.endTitleRefresh();
        }
        dismissLoadingDialog();
    }
    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

}

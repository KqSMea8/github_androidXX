package com.bsoft.baselib.mvpbase;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bsoft.baselib.base.BaseActivity;
import com.bsoft.baselib.base.BaseRecyclerViewFragment;


public abstract class BaseMvpRecyclerViewFragment<V extends IView, T extends BasePresenter<V>> extends BaseRecyclerViewFragment implements IView {

    protected T mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);//因为之后所有的子类都要实现对应的View接口
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
    @Override
    public void showLoading() {
        if( ((BaseActivity) getActivity()).actionBar!=null) {
            ((BaseActivity) getActivity()).actionBar.startTitleRefresh();
        }
       // ((BaseActivity) getActivity()).showLoadingView();
        showLoadingView();
    }

    @Override
    public void dismissLoading() {
        if( ((BaseActivity) getActivity()).actionBar!=null) {
            ((BaseActivity) getActivity()).actionBar.endTitleRefresh();
        }
      //  ((BaseActivity) getActivity()).dismissLoadingDialog();
        refreshComplete();
    }


    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

}

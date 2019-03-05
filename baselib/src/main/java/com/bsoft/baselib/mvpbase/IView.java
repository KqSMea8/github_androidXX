package com.bsoft.baselib.mvpbase;

/**
 * Created by hugege on 2018/4/3.
 */

public interface IView<P extends IPresenter> {

    void showLoading();

    void dismissLoading();

}

package com.bsoft.baselib.mvpbase;

import android.support.annotation.UiThread;

/**
 * Created by hugege on 2018/4/3.
 */

public interface IPresenter<V extends IView> {

    /**
     * attachView 绑定View
     */
    @UiThread
    void attachView(V view);

    /**
     * detach View 解除绑定
     */
    void detachView();

}

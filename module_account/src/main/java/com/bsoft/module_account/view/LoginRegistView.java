package com.bsoft.module_account.view;


import com.bsoft.baselib.mvpbase.IView;
import com.bsoft.module_account.bean.LoginUser;

/**
 * user：lqm
 * desc：登录注册
 */

public interface LoginRegistView extends IView {




    void loginSuccess(LoginUser user);

    void registerSuccess(LoginUser user);

    void loginFail();

    void registerFail();

}

package com.bsoft.module_account.presenter;


import android.text.TextUtils;
import android.util.ArrayMap;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONObject;

import com.bsoft.baselib.base.BaseActivity;
import com.bsoft.baselib.mvpbase.BasePresenter;
import com.bsoft.commonlib.Constants;
import com.bsoft.commonlib.util.MD5;
import com.bsoft.module_account.bean.LoginUser;
import com.bsoft.module_account.service.LoginUserService;
import com.bsoft.module_account.view.LoginRegistView;
import com.bsoft.network_api.bean.ResultModel;
import com.bsoft.network_api.net.NetClient;

/**
 * user：lqm
 * desc：登录注册
 */

public class LoginRegistPresenter extends BasePresenter<LoginRegistView> {

    //登录
    public void toLogin(final BaseActivity baseActivity, final String userName, String pwd) {

        ArrayMap<String, Object> body = new ArrayMap<>();
        body.put("tenantId", Constants.TENANT_ID);
        body.put("loginName", userName);
        body.put("rid", "patient");
        body.put("pwd", MD5.getMD5(pwd));
        body.put("forAccessToken", true);
        NetClient.post(baseActivity, "login", null, body, LoginUser.class,
                new NetClient.Listener<LoginUser>() {
                    @Override
                    public void onPrepare() {
                      //  baseActivity.showLoadingDialog();
                        getView().showLoading();
                    }

                    @Override
                    public void onSuccess(ResultModel<LoginUser> result) {
                        getView().dismissLoading();
                        if (result.isSuccess()) {

                            if (!result.isEmpty() && !TextUtils.isEmpty(result.pro)) {

                                ((LoginUserService) ARouter.getInstance().build("/service/login/user").navigation())
                                        .setUserPhone(userName);
                                ((LoginUserService) ARouter.getInstance().build("/service/login/user").navigation())
                                        .setAccessToken(JSONObject.parseObject(result.pro).getString("accessToken"));
                                ((LoginUserService) ARouter.getInstance().build("/service/login/user").navigation())
                                        .setLoginUser(result.data);
                                ((LoginUserService) ARouter.getInstance().build("/service/login/user").navigation())
                                        .setLoginState(true);

//                                ARouter.getInstance().build(ConstantUrl.MAINTAB).withBoolean("isLoad", false)
//                                        .navigation();
//                                baseActivity.finish();
////                                getAppInfoByDevice();
                                //  doLoginNext();

                                LoginUser user =new LoginUser();
                                user.userName="qq";
                                getView().loginSuccess(user);

                            } else {
                                baseActivity.showToast("登录失败");

                            }
                        } else {
                            if (result.getCode() == 501) {
                                baseActivity.showToast("用户名或密码错误");
                            } else if (result.getCode() == 403) {
                                baseActivity.showToast("用户名已被禁用");
                            } else if (result.getCode() == 404) {
                                baseActivity.showToast("用户名不存在");
                            } else {
                                baseActivity.showToast(result.getToast());
                            }

                        }
                    }

                    @Override
                    public void onFaile(Throwable t) {
                       // baseActivity.dismissLoadingDialog();
                        getView().dismissLoading();
                        getView().loginFail();
                    }
                });



//        WanService.login(username, password)
//                .compose(RxSchedulersHelper.io_main())
//                .compose(RxResultHelper.handleResult())
//                .subscribe(new RxObserver<UserBean>() {
//
//                    @Override
//                    public void _onSubscribe(Disposable d) {
//                        getView().showProgress("正在登陆...");
//                    }
//
//                    @Override
//                    public void _onNext(UserBean userBean) {
//                        getView().loginSuccess(userBean);
//                    }
//
//                    @Override
//                    public void _onError(String errorMessage) {
//                        getView().loginFail();
//
//                    }
//
//                    @Override
//                    public void _onComplete() {
//                        getView().hideProgress();
//                    }
//
//                });
    }

    //注册
    public void toRegist(String username, String password) {
//        WanService.regist(username, password)
//            .compose(RxSchedulersHelper.io_main())
//            .compose(RxResultHelper.handleResult())
//            .subscribe(new RxObserver<UserBean>() {
//                @Override
//                public void _onSubscribe(Disposable d) {
//                    getView().showProgress("正在注册...");
//                }
//
//                @Override
//                public void _onNext(UserBean userBean) {
//                    getView().registerSuccess(userBean);
//                }
//
//                @Override
//                public void _onError(String errorMessage) {
//                    getView().registerFail();
//                }
//
//                @Override
//                public void _onComplete() {
//                    getView().hideProgress();
//                }
//            });
    }
}

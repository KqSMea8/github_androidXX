package com.bsoft.module_account.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.commonlib.RouterPath;
import com.bsoft.commonlib.util.ToastUtil;
import com.bsoft.module_account.service.LoginUserService;


/**
 * Created by jinyd
 * Email : jinyd@bsoft.com.cn
 * date : 2019/3/15
 * describe :登录拦截
 */

public class LoginNavigationCallBack implements NavigationCallback {
    @Override
    public void onFound(Postcard postcard) {
        Log.d("ARouter", "找到了");
    }

    @Override
    public void onLost(Postcard postcard) {
        Log.d("ARouter", "找不到了");
    }

    @Override
    public void onArrival(Postcard postcard) {
        Log.d("ARouter", "跳转完了");
    }

    @Override
    public void onInterrupt(Postcard postcard) {
        Log.d("ARouter", "被拦截了");
        ARouter.getInstance().build(RouterPath.LOGIN).navigation();
//        if (!((LoginUserService) ARouter.getInstance().build("/service/login/user").navigation()).getLoginState()) {
//            ToastUtil.showShort("请先登录");
//            ARouter.getInstance().build(RouterPath.LOGIN).navigation();
//        }else {  //if (TextUtils.isEmpty(LocalData.getLoginUser().idcard)) {
//            ToastUtil.showShort("证件号码还未填写，请先完善信息");
//            ARouter.getInstance()
//                    .build(RouterPath.MAINTAB)
//                    .withBoolean("isCanModify", true)
//                    .navigation();
//        }
    }
}

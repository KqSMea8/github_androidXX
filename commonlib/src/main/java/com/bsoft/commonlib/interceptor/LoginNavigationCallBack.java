package com.bsoft.commonlib.interceptor;

import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;


/**
 * Created by jinyd
 * Email : jinyd@bsoft.com.cn
 * date : 2019/3/15
 * describe :登录拦截
 */
public class LoginNavigationCallBack implements NavigationCallback {
    @Override
    public void onFound(Postcard postcard) {

    }

    @Override
    public void onLost(Postcard postcard) {

    }

    @Override
    public void onArrival(Postcard postcard) {

    }

    @Override
    public void onInterrupt(Postcard postcard) {
//        if (!LocalData.isLogin()) {
//            ToastUtil.showShort("请先登录");
//            ARouter.getInstance().build(RouterPath.ACCOUNT_LOGIN_ACTIVITY).navigation();
//        }else if (TextUtils.isEmpty(LocalData.getLoginUser().idcard)) {
//            ToastUtil.showShort("证件号码还未填写，请先完善信息");
//            ARouter.getInstance()
//                    .build(RouterPath.FAMILY_COMPLETE_INFO_ACTIVITY)
//                    .withBoolean("isCanModify", true)
//                    .navigation();
//        }
    }
}

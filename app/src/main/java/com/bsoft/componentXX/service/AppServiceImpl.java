package com.bsoft.componentXX.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bsoft.baselib.service.IAppService;
import com.bsoft.commonlib.RouterPath;
import com.bsoft.commonlib.util.ActivityUtil;
import com.bsoft.componentXX.BuildConfig;
import com.bsoft.module_main.MainTabActivity;


/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2018/7/12
 * describe :
 */
@Route(path = RouterPath.APP_SERVICE)
public class AppServiceImpl implements IAppService {

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public String getAppPackageName() {
        return BuildConfig.APPLICATION_ID;
    }

    @Override
    public String getHttpPcnUrl() {
        return BuildConfig.httpPcnUrl;
    }

    @Override
    public String getHttpHcnUrl() {
        return BuildConfig.httpHcnUrl;
    }

    @Override
    public String getHttpImgUrl() {
        return BuildConfig.httpImgUrl;
    }

    @Override
    public String getHttpWebUrl() {
        return BuildConfig.httpWebUrl;
    }

    @Override
    public String getWechatAppId() {
        return BuildConfig.wechatId;
    }

    @Override
    public String getEnvironment() {
        return BuildConfig.environment;
    }


    @Override
    public boolean isMainTabActivityLiveInStack() {//MainTabActivity是否存活于栈中
        return ActivityUtil.isActivityLiveInStack(MainTabActivity.class);
    }

    @Override
    public void init(Context context) {

    }
}

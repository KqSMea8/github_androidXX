package com.bsoft.baselib.service;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2018/5/6
 * describe :
 */
public interface IAppService extends IProvider {

    boolean isDebug();

    String getAppPackageName();

    String getHttpHcnUrl();

    String getHttpPcnUrl();

    String getHttpImgUrl();

    String getHttpWebUrl();

    String getWechatAppId();

    String getEnvironment();
    boolean isMainTabActivityLiveInStack();
}

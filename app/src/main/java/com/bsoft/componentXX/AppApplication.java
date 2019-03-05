package com.bsoft.componentXX;

import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.baselib.AppContext;
import com.bsoft.baselib.BaseApplication;
import com.zhy.autolayout.config.AutoLayoutConifg;

public class AppApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        AppContext.initialize(getApplicationContext());
        //设置autolayout不忽略状态栏高度
        AutoLayoutConifg.getInstance().useDeviceSize();
        startService(new Intent(this,LocalService.class));
        startService(new Intent(this,RemoteService.class));
        if (BuildConfig.DEBUG) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }
}

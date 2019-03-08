package com.bsoft.componentXX;

import android.content.Intent;
import android.support.v4.util.ArrayMap;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.baselib.AppContext;
import com.bsoft.baselib.BaseApplication;
import com.bsoft.baselib.util.LogUtil;
import com.bsoft.commonlib.util.TPreferences;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.mmkv.MMKV;
import com.zhy.autolayout.config.AutoLayoutConifg;


public class AppApplication extends BaseApplication {

    public ArrayMap<String, Object> serviceMap;

    @Override
    public void onCreate() {
        super.onCreate();

        AppContext.initialize(getApplicationContext());
        //设置autolayout不忽略状态栏高度
        AutoLayoutConifg.getInstance().useDeviceSize();
        startService(new Intent(this, LocalService.class));
        startService(new Intent(this, RemoteService.class));

        serviceMap = new ArrayMap<String, Object>();
        TPreferences preferences = new TPreferences(this);
        serviceMap.put("com.bsoft.app.preferences", preferences);
        if (BuildConfig.DEBUG) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        MMKV.initialize(this);
//        String rootDir = MMKV.initialize(this);
//        System.out.println("mmkv root: ========" + rootDir);


        LogUtil.setType(BuildConfig.DEBUG ? LogUtil.TYPE_DEBUG : LogUtil.TYPE_RELEASE);
        Logger.init()                 // default PRETTYLOGGER or use just init()
                .methodCount(2)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)        // default LogLevel.FULL
                .methodOffset(0) ;
    }

    /**
     * 获取服务，自定义的服务
     *
     * @param key
     * @return
     */
    @Override
    public Object getSystemService(String key) {
        if (null != serviceMap) {
            if (this.serviceMap.containsKey(key)) {
                return serviceMap.get(key);
            }
        }
        return super.getSystemService(key);
    }
}

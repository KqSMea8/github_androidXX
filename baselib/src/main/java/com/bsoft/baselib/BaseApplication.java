package com.bsoft.baselib;

import android.support.multidex.MultiDexApplication;

import com.bsoft.baselib.util.FileUriPermissionCompat;

/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2018/4/25
 * describe : 维护一个全局Context
 */
public class BaseApplication extends MultiDexApplication {

    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;


    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }

}

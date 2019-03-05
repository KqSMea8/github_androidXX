package com.bsoft.componentXX;

import com.bsoft.baselib.AppContext;
import com.bsoft.baselib.BaseApplication;

public class AppApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        AppContext.initialize(getApplicationContext());
    }
}

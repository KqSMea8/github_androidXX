/*
 * Tencent is pleased to support the open source community by making Tinker available.
 *
 * Copyright (C) 2016 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bsoft.componentXX;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.v4.util.ArrayMap;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.SDKInitializer;
import com.bsoft.baselib.AppContext;
import com.bsoft.baselib.util.FileUriPermissionCompat;
import com.bsoft.baselib.util.LogUtil;
import com.bsoft.commonlib.util.FrescoImageLoader;
import com.caimuhao.rxpicker.RxPicker;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.mmkv.MMKV;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.entry.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.Locale;

import cn.jpush.android.api.JPushInterface;


@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.tinker.deeson.mytinkerdemo.SampleApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class SampleApplicationLike extends DefaultApplicationLike {
    private static final String TAG = "Tinker.SampleApplicationLike";
    public ArrayMap<String, Object> serviceMap;
    public SampleApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                                 long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    /**
     * install multiDex before install tinker
     * so we don't need to put the tinker lib classes in the main dex
     *
     * @param base
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
//        TinkerInstaller.install(this,new DefaultLoadReporter(getApplication())
//                ,new DefaultPatchReporter(getApplication()),new DefaultPatchListener(getApplication()), SampleResultService.class,new UpgradePatch());
//        Tinker tinker = Tinker.with(getApplication());

        Toast.makeText(getApplication(),"加载----------完成", Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //此处写自己的Application逻辑


        MultiDex.install(getApplication());
        FileUriPermissionCompat.init(BuildConfig.APPLICATION_ID + ".myFileProvider");
        AppContext.initialize(getApplication());
        //设置autolayout不忽略状态栏高度
        AutoLayoutConifg.getInstance().useDeviceSize();
        //  startService(new Intent(this, LocalService.class));
        //   startService(new Intent(this, RemoteService.class));


        if (BuildConfig.DEBUG) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(getApplication());
        MMKV.initialize(getApplication());
        Fresco.initialize(getApplication());
//        String rootDir = MMKV.initialize(this);
//        System.out.println("mmkv root: ========" + rootDir);

        // 调试时，将第三个参数改为true
        Bugly.init(getApplication(), BuildConfig.BUGLY_APPID,BuildConfig.DEBUG);


        LogUtil.setType(BuildConfig.DEBUG ? LogUtil.TYPE_DEBUG : LogUtil.TYPE_RELEASE);
        Logger.init()                 // default PRETTYLOGGER or use just init()
                .methodCount(2)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)        // default LogLevel.FULL
                .methodOffset(0) ;

        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(getApplication());
        SDKInitializer.initialize(getApplication());
        //RxPicker.init(new FrescoImageLoader());
     //   RxPicker.init(new FrescoImageLoader());

        Album.initialize(AlbumConfig.newBuilder(getApplication())
                .setAlbumLoader(new FrescoImageLoader())
                .setLocale(Locale.getDefault())
                .build()
        );
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
        Beta.unInit();
    }




}

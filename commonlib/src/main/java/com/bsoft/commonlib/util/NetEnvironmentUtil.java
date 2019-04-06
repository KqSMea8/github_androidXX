package com.bsoft.commonlib.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;


import com.alibaba.android.arouter.launcher.ARouter;

import com.bsoft.baselib.service.IAppService;
import com.bsoft.commonlib.BuildConfig;
import com.bsoft.commonlib.Constants;
import com.bsoft.commonlib.RouterPath;
import com.bsoft.commonlib.bean.NetAddressVo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by 83990 on 2018/2/8.
 */

public class NetEnvironmentUtil {
    /*Default*/
    public static final String SP_KEY_HTTP_ENVIROMENT = "http_enviroment";
    public static final String SP_KEY_HTTP_HCN_URL = "http_hcn_url";
    public static final String SP_KEY_HTTP_PCN_URL = "http_pcn_url";
    public static final String SP_KEY_HTTP_IMG_URL = "http_img_url";
    public static final String SP_KEY_HTTP_WEB_URL = "http_web_url";
    private static IAppService iAppService;


    /**
     * 初始化网络环境
     *
     * @param context
     * @return
     */
    public static boolean initConstans(Context context) {
        if (context == null) {
            throw new RuntimeException("context can't be null");
        }

        NetAddressVo vo = getCurEnvironment(context);

        if (vo != null) {

            Constants.HTTP_HCN_URL_TEXT=vo.getHttpHcnUrl();
         //   ((IAppService) ARouter.getInstance().build(RouterPath.APP_SERVICE).navigation()).getHttpHcnUrl();
            Constants.HTTP_PCN_URL_TEXT=vo.getHttpPcnUrl();
          //  ((IAppService) ARouter.getInstance().build(RouterPath.APP_SERVICE).navigation()).getHttpPcnUrl();
            Constants.HTTP_IMG_URL_TEXT=vo.getHttpImgUrl();
          //  ((IAppService) ARouter.getInstance().build(RouterPath.APP_SERVICE).navigation()).getHttpImgUrl();
            Constants.HTTP_WEB_URL_TEXT=vo.getHttpWebUrl();
       //     ((IAppService) ARouter.getInstance().build(RouterPath.APP_SERVICE).navigation()).getHttpWebUrl();
            return true;
        } else {
            return false;
        }
    }


    /**
     * 获取网络环境参数List
     *
     * @param context
     * @return
     */
    public static ArrayList<NetAddressVo> getNetEnvironments(Context context) {
        if (context == null) {
            throw new RuntimeException("context can't be null");
        }
        AssetManager assetManager = context.getAssets();
        TPreferences preferences = new TPreferences(context);
        try {
            InputStream is = assetManager.open("net_configs");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer stringBuffer = new StringBuffer();
            String str = null;
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }

            if (!TextUtils.isEmpty(stringBuffer)) {
                Gson gson = new Gson();
                ArrayList<NetAddressVo> netBeans = gson.fromJson(stringBuffer.toString()
                        , new TypeToken<ArrayList<NetAddressVo>>() {
                        }.getType());
                if (netBeans != null && !netBeans.isEmpty()) {
                    for (NetAddressVo vo : netBeans) {
                        vo.setHttpHcnUrl(preferences.getStringData(SP_KEY_HTTP_HCN_URL, vo.getHttpHcnUrl()));
                        vo.setHttpPcnUrl(preferences.getStringData(SP_KEY_HTTP_PCN_URL, vo.getHttpPcnUrl()));
                        vo.setHttpImgUrl(preferences.getStringData(SP_KEY_HTTP_IMG_URL, vo.getHttpImgUrl()));
                        vo.setHttpWebUrl(preferences.getStringData(SP_KEY_HTTP_WEB_URL, vo.getHttpWebUrl()));
                    }
                    return netBeans;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取当前网络环境参数
     *
     * @param context
     * @return
     */
    public static NetAddressVo getCurEnvironment(Context context) {
        if (context == null) {
            throw new RuntimeException("context can't be null");
        }
        String enviroment = BuildConfig.environment;
        TPreferences preferences = new TPreferences(context);
        if (BuildConfig.DEBUG) {
            enviroment = preferences.getStringData(SP_KEY_HTTP_ENVIROMENT, BuildConfig.environment);
        }

        ArrayList<NetAddressVo> vos = getNetEnvironments(context);
        if (vos != null) {
            for (NetAddressVo vo : vos) {
                if (TextUtils.equals(vo.getEnvironment(), enviroment)) {
                    vo.setHttpHcnUrl(preferences.getStringData(SP_KEY_HTTP_HCN_URL, vo.getHttpHcnUrl()));
                    vo.setHttpPcnUrl(preferences.getStringData(SP_KEY_HTTP_PCN_URL, vo.getHttpPcnUrl()));
                    vo.setHttpImgUrl(preferences.getStringData(SP_KEY_HTTP_IMG_URL, vo.getHttpImgUrl()));
                    vo.setHttpWebUrl(preferences.getStringData(SP_KEY_HTTP_WEB_URL, vo.getHttpWebUrl()));
                    return vo;
                }
            }
        }

        return null;
    }

    /**
     * 设置当前网络环境
     *
     * @param context
     * @param enviroment
     * @return
     */
    public static boolean setEnvironment(Context context, String enviroment) {
        if (context == null) {
            throw new RuntimeException("context can't be null");
        }

        if (TextUtils.isEmpty(enviroment)) {
            return false;
        }
        TPreferences preferences = new TPreferences(context);
        ArrayList<NetAddressVo> vos = getNetEnvironments(context);
        if (vos != null) {
            for (NetAddressVo vo : vos) {
                if (TextUtils.equals(vo.getEnvironment(), enviroment)) {
                    preferences.setStringData(SP_KEY_HTTP_ENVIROMENT, enviroment);
                    return true;
                }
            }
        }

        return false;
    }


}

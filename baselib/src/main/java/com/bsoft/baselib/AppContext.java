package com.bsoft.baselib;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Administrator on 2017/3/8.
 */
public class AppContext {
    private static Context sContext;
    private static Resources sResource;//直接获取Resources 放在application，可以减少调用getResources的次数
    private static String baseUrl;
    private static String httpHeaderAction;

    private static String httpsCer;

    private static boolean debug;
    
    public static void initialize(Context context){
        sContext=context;
        sResource=sContext.getResources();
    }
    public static void initialize(Context context, String url, String action){
        initialize(context);
        baseUrl = url;
        httpHeaderAction = action;
    }


    public static Context getContext(){
        return sContext;
    }
    public static String getBaseUrl(){
        return baseUrl;
    }
    public static String getHttpHeaderAction(){
        return httpHeaderAction;
    }
    

    public static Resources resources(){
        return sResource;
    }


    public static void clear() {
        sContext = null;
        sResource = null;
        baseUrl = null;
        httpHeaderAction = null;
    }

    public static String getHttpsCer() {
        return httpsCer;
    }

    public static void setHttpsCer(String httpsCer) {
        AppContext.httpsCer = httpsCer;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        AppContext.debug = debug;
    }
}

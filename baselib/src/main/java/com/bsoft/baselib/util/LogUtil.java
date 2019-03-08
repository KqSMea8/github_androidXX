package com.bsoft.baselib.util;

import android.util.Log;

/**
 * Created by kai.chen on 2016/10/9.
 */

public class LogUtil {
    //Default
    public static final String TAG = "android-doc";

    public static final int TYPE_DEBUG = 1;
    public static final int TYPE_RELEASE = 2;

    private static final int LOG_MAXLENGTH = 2000;

    private static final int TYPE_I = 1;
    private static final int TYPE_D = 2;
    private static final int TYPE_E = 3;

    //Flag
    private static int curType = TYPE_DEBUG;

    private static boolean typeCheck(int type) {
        if (type != TYPE_DEBUG
                && type != TYPE_RELEASE) {
            throw new IllegalArgumentException("type inexistence");
        }
        return true;
    }

    public static void setType(int type) {
        if (typeCheck(type)) {curType = type;}
    }

    //**********  I   *************//
    public static void LOGI(String msg) {
        LOGI(TYPE_RELEASE, TAG, msg);
    }

    public static void LOGI(int type, String msg) {
        LOGI(type, TAG, msg);
    }

    public static void LOGI(int type, String tag, String msg) {
        if (typeCheck(type) && type >= curType) {LogLong(tag, msg, TYPE_I);}
    }

    //**********  D   ************//
    public static void LOGD(String msg) {
        LOGD(TYPE_RELEASE, TAG, msg);
    }

    public static void LOGD(int type, String msg) {
        LOGD(type, TAG, msg);
    }

    public static void LOGD(int type, String tag, String msg) {
        if (typeCheck(type) && type >= curType) {LogLong(tag, msg, TYPE_D);}
    }

    //**********  E   ************//
    public static void LOGE(String msg) {
        LOGE(TYPE_RELEASE, TAG, msg);
    }

    public static void LOGE(int type, String msg) {
        LOGE(type, TAG, msg);
    }

    public static void LOGE(int type, String tag, String msg) {
        if (typeCheck(type) && type >= curType){ LogLong(tag, msg, TYPE_E);}
    }

    public static void LOGE(String msg, Throwable ex) {
        LOGE(TYPE_RELEASE, TAG, msg, ex);
    }

    public static void LOGE(int type, String msg, Throwable ex) {
        LOGE(type, TAG, msg, ex);
    }

    public static void LOGE(int type, String tag, String msg, Throwable ex) {
        if (typeCheck(type) && type >= curType){ Log.e(tag, msg, ex);}
    }


    private static synchronized void LogLong(String tag, String msg, int type) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        int size = (strLength / LOG_MAXLENGTH) + 1;
        for (int i = 0; i < size; i++) {
            String str = null;
            if (strLength > end) {
                str = msg.substring(start, end);
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                str = msg.substring(start, strLength);
                start = strLength;
            }
            switch (type) {
                case TYPE_I:
                    Log.i(tag + "-" + i, str);
                    break;
                case TYPE_D:
                    Log.d(tag + "-" + i, str);
                    break;
                case TYPE_E:
                    Log.e(tag + "-" + i, str);
                    break;
            }
        }
    }


}

package com.bsoft.commonlib.util;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bsoft.baselib.AppContext;
import com.bsoft.baselib.TConfig;


/**
 * 本地持久化类
 *
 * @author zkl
 */
public class TPreferences {
    public static final String KEY_DEBUG = "debug";

    private SharedPreferences mPrefs;

    public TPreferences(Context paramContext) {
        this.mPrefs = PreferenceManager
                .getDefaultSharedPreferences(paramContext);
    }

    @SuppressLint("WrongConstant")
    public static TPreferences getInstance() {
        Context paramContext = AppContext.getContext();
        TPreferences localTPreferences = (TPreferences) paramContext
                .getSystemService(TConfig.SERVICES_PREFERENCES);
        if (localTPreferences == null)
            localTPreferences = (TPreferences) paramContext
                    .getApplicationContext().getSystemService(
                            TConfig.SERVICES_PREFERENCES);
        if (localTPreferences == null) {
            throw new IllegalStateException("TPreferences not available");
        }
        return localTPreferences;
    }


    public void setStringData(String key, String value) {
        this.mPrefs.edit().putString(key, value).commit();
    }

    public String getStringData(String key) {
        return this.mPrefs.getString(key, null);
    }

    //add change_net chenkai 20170904 start
    public String getStringData(String key, String defaultValue) {
        return this.mPrefs.getString(key, defaultValue);
    }
    //add change_net chenkai 20170904 end

    public void setLongData(String key, long value) {
        this.mPrefs.edit().putLong(key, value).commit();
    }

    public long getLongData(String key) {
        return this.mPrefs.getLong(key, 0);
    }

    public void setBooleanData(String key, boolean value) {
        this.mPrefs.edit().putBoolean(key, value).commit();
    }

    public boolean getBooleanData(String key) {
        return this.mPrefs.getBoolean(key, false);
    }
    public boolean getBooleanData(String key, boolean flag) {
        return this.mPrefs.getBoolean(key, flag);
    }

}

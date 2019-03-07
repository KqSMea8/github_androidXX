package com.bsoft.commonlib.util;


import com.tencent.mmkv.MMKV;


/**
 * 本地持久化类
 *
 * @author zkl
 */
public class TMmkv {

    private MMKV mMkv;
    private static TMmkv instance;
    private TMmkv() {

    }

    public static synchronized TMmkv getInstance(String key) {
        if (instance == null) {
            instance = new TMmkv(key);
        }
        return instance;
    }

    public TMmkv(String key) {
        this. mMkv = MMKV.mmkvWithID(key, MMKV.SINGLE_PROCESS_MODE);
    }

    public void setStringData(String key, String value) {
        this. mMkv.encode(key, value);
    }

    public String getStringData(String key) {
        return this.mMkv.decodeString(key, null);
    }
    public String getStringData(String key, String defaultValue) {
        return this.mMkv.decodeString(key, defaultValue);
    }
    public void setIntData(String key, int value) {
        this. mMkv.encode(key, value);
    }

    public int getIntData(String key) {
        return this.mMkv.decodeInt(key, -1);
    }

    public void setFloatData(String key, float value) {
        this. mMkv.encode(key, value);
    }

    public float getFloatData(String key) {
        return this.mMkv.decodeFloat(key, -1f);
    }

    public void setLongData(String key, long value) {
        this. mMkv.encode(key, value);
    }

    public long getLongData(String key) {
        return this.mMkv.decodeLong(key, -1L);
    }

    public void setBooleanData(String key, boolean value) {
        this. mMkv.encode(key, value);
    }

    public boolean getBooleanData(String key) {
        return this.mMkv.decodeBool(key, false);
    }
    public boolean getBooleanData(String key, boolean flag) {
        return this.mMkv.decodeBool(key, flag);
    }

}

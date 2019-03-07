package com.bsoft.baselib.bean;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.lang.reflect.Constructor;

public class TabInfo extends BaseVo {


    private int id;
    private int icon;
    private String name = null;
    public boolean hasTips = false;
    public Fragment fragment = null;
    public boolean notifyChange = false;
    public Class fragmentClass = null;
    private Bundle bundle = null;



    public TabInfo(int id, String name, Class clazz) {
        this(id, name, 0, clazz);
    }
    public TabInfo(int id, String name, Class clazz, Bundle bundle) {
        this(id, name, 0, clazz);
        this.bundle = bundle;
    }

    @SuppressWarnings("rawtypes")
    public TabInfo(int id, String name, boolean hasTips, Class clazz) {
        this(id, name, 0, clazz);
        this.hasTips = hasTips;
    }

    @SuppressWarnings("rawtypes")
    public TabInfo(int id, String name, int iconid, Class clazz) {
        super();

        this.name = name;
        this.id = id;
        icon = iconid;
        fragmentClass = clazz;
    }


    public Fragment createFragment() {
        if (fragment == null) {
            Constructor constructor;
            try {
                constructor = fragmentClass.getConstructor(new Class[0]);
                fragment = (Fragment) constructor
                        .newInstance(new Object[0]);
                if(bundle != null) {
                    fragment.setArguments(bundle);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }

    public int getId() {
        return id;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public boolean isHasTips() {
        return hasTips;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public boolean isNotifyChange() {
        return notifyChange;
    }

    public Class getFragmentClass() {
        return fragmentClass;
    }

    public Bundle getBundle() {
        return bundle;
    }
}

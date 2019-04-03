package com.bsoft.module_splash.guide;

import android.Manifest;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.baselib.base.BaseActivity;
import com.bsoft.baselib.util.LogUtil;
import com.bsoft.commonlib.RouterPath;
import com.bsoft.commonlib.util.Installation;
import com.bsoft.commonlib.util.TMmkv;
import com.bsoft.module_splash.R;

import io.reactivex.functions.Consumer;


/**
 * Created by Administrator on 2017/4/26.
 */

public class GuideActivity extends BaseActivity {
    
    ViewPager viewPager;
    GuideAdapter pagerAdapter;

    //1 第一次进入  2 关于那里进入
    public int flag = 1;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        flag = getIntent().getIntExtra("flag", 1);

        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new GuideAdapter(getSupportFragmentManager());
        TypedArray guideArr = getResources().obtainTypedArray(R.array.guide_res);
        int length = guideArr.length();
        for(int i = 0; i < length; i++){
            pagerAdapter.addItem(CommonFragment.getInstance(guideArr.getResourceId(i, 0), i == length-1));
        }
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(pagerAdapter);
            setDeviceId();
    }

    private void setDeviceId() {

        rxPermissions.request(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            // 设置设备唯一号
                            TMmkv.getInstance("key").setStringData("deviceId",  Installation.id(getApplicationContext()));
                            LogUtil.LOGE(  TMmkv.getInstance("key").getStringData("deviceId"));
                        } else {
                            Toast.makeText(GuideActivity.this, "未能获取到设备权限!", Toast.LENGTH_SHORT).show();
                            //finish();
                        }
                    }
                });
    }


    @Override
    public void findView() {
        
    }

    public void goIn() {
        if (flag == 1) {
            TMmkv.getInstance("key").setStringData("first", "1");

            ARouter.getInstance().build(RouterPath.MAINTAB).navigation();
            finish();

        } else if (flag == 2) {
            finish();
        }
    }
}

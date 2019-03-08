package com.bsoft.module_splash.guide;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.baselib.base.BaseActivity;
import com.bsoft.commonlib.RouterPath;
import com.bsoft.commonlib.util.TPreferences;
import com.bsoft.module_splash.R;



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
    }



    @Override
    public void findView() {
        
    }

    public void goIn() {
        if (flag == 1) {
            TPreferences.getInstance().setStringData("first", "1");

            ARouter.getInstance().build(RouterPath.MAINTAB).navigation();
            finish();

        } else if (flag == 2) {
            finish();
        }
    }
}

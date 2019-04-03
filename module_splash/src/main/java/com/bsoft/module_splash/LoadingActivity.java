package com.bsoft.module_splash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.alibaba.android.arouter.launcher.ARouter;

import com.bsoft.baselib.base.BaseActivity;
import com.bsoft.baselib.util.statusBarUtil.StatusBarUtil;
import com.bsoft.commonlib.RouterPath;
import com.bsoft.commonlib.util.StringUtil;
import com.bsoft.commonlib.util.TMmkv;
import com.bsoft.module_splash.guide.GuideActivity;


/**
 * @author Tank
 * @类说明
 */

public class LoadingActivity extends BaseActivity {

    private  boolean flag =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.white));
        long startTime = System.currentTimeMillis();

        setContentView(R.layout.activity_loading);
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        Log.d("SplashActivity", "time:" + time);

        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(1500);
        findViewById(R.id.loadingImg).startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });

//        if(findViewById(R.id.tv_name)!=null) {
//            AlphaAnimation bb = new AlphaAnimation(0.3f, 1.0f);
//            bb.setDuration(2000);
//            findViewById(R.id.tv_name).startAnimation(bb);
//        }



    }

    @Override
    public void findView() {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    /**
     * 跳转到...
     */
    private void redirectTo() {


        if (!StringUtil.isEmpty(TMmkv.getInstance("key").getStringData(
                "first"))
                && "1".equals(TMmkv.getInstance("key").getStringData(
                "first"))) {
            ARouter.getInstance().build(RouterPath.MAINTAB).navigation();
        } else {
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

}

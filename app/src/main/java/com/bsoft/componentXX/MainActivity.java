package com.bsoft.componentXX;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bsoft.baselib.base.BaseActivity;

import com.bsoft.baselib.base.TabLayoutFragmentActivity;
import com.bsoft.baselib.bean.TabInfo;
import com.bsoft.baselib.util.ScreenUtil;
import com.bsoft.baselib.util.statusBarUtil.StatusBarUtil;
import com.bsoft.baselib.widget.BsoftActionBar;
import com.bsoft.commonlib.RouterPath;
import com.bsoft.commonlib.util.TMmkv;
import com.bsoft.componentXX.fragment.XFragment;

import java.util.List;

@Route(path = RouterPath.MAINTAB)
public class MainActivity extends TabLayoutFragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        findView();

    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setVisibility(View.GONE);

    }

    @Override
    protected int getMainViewResId() {
        return R.layout.base_tab_activity;
    }


    @Override
    protected int supplyTabs(List<TabInfo> tabs) {
        tabs.add(new TabInfo(0, "全部", XFragment.class));
        tabs.add(new TabInfo(1, "待回复", XFragment.class));
        tabs.add(new TabInfo(2, "已待回回复", XFragment.class));
        tabs.add(new TabInfo(2, "已待回回复", XFragment.class));
//        tabs.add(new TabInfo(3, "已结束", XFragment.class));
//        tabs.add(new TabInfo(4, "待回复", XFragment.class));
//        tabs.add(new TabInfo(5, "已回复", XFragment.class));
//        tabs.add(new TabInfo(6, "已结束", XFragment.class));
        return 0;
    }


}

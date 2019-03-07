package com.bsoft.baselib.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;


import com.bsoft.baselib.R;
import com.bsoft.baselib.bean.TabInfo;
import com.bsoft.baselib.util.DensityUtil;
import com.bsoft.baselib.util.TabUtil;
import com.bsoft.baselib.util.TabUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 标签页 可滑动
 * 
 * 
 * @author Administrator
 * 
 */
public abstract class TabLayoutFragmentActivity extends BaseActivity {
	private static final String TAG = "DxFragmentActivity";

	public static final String EXTRA_TAB = "tab";
	public static final String EXTRA_QUIT = "extra.quit";

	protected int mCurrentTab = 0;
	protected int mLastTab = -1;

	// 默认显示第几页
	public int index = 0;

	// 存放选项卡信息的列表
	protected ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

	// viewpager adapter
	protected MyAdapter myAdapter = null;

	// viewpager
	protected ViewPager mViewPager;

	// 选项卡控件
	private TabLayout mTabLayout;

	public TabLayout getTabLayout() {
		return mTabLayout;
	}


	static class MyAdapter extends FragmentPagerAdapter {
		ArrayList<TabInfo> tabs = null;

		public MyAdapter(FragmentManager fm,
						 ArrayList<TabInfo> tabs) {
			super(fm);
			this.tabs = tabs;
		}

		@Override
		public Fragment getItem(int pos) {
			Fragment fragment = null;
			if (tabs != null && pos < tabs.size()) {
				TabInfo tab = tabs.get(pos);
				if (tab == null) {
					return null;
				}
				fragment = tab.createFragment();
			}
			return fragment;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public int getCount() {
			if (tabs != null && tabs.size() > 0) {
				return tabs.size();
			}
			return 0;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabInfo tab = tabs.get(position);
			Fragment fragment = (Fragment) super.instantiateItem(container,
					position);
			tab.fragment = fragment;
			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Log.e("arg0",position+"=======```````````");
			return tabs.get(position).getName();
		}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		index = getIntent().getIntExtra("index", 0);
		setContentView(getMainViewResId());
		initViews();


		// //设置viewpager内部页面之间的间距
		// mPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin_width));
		// //设置viewpager内部页面间距的drawable
		// mPager.setPageMarginDrawable(R.color.page_viewer_margin_color);
	}

	public int getCurrentItem() {
		return mViewPager.getCurrentItem();
	}

	@Override
	public void onDestroy() {
		mTabs.clear();
		mTabs = null;
		myAdapter.notifyDataSetChanged();
		myAdapter = null;
		mViewPager.setAdapter(null);
		mViewPager = null;
		mTabLayout = null;

		super.onDestroy();
	}

	private final void initViews() {
		// 这里初始化界面
		mCurrentTab = supplyTabs(mTabs);
		Intent intent = getIntent();
		if (intent != null) {
			mCurrentTab = intent.getIntExtra(EXTRA_TAB, mCurrentTab);
		}
		Log.d(TAG, "mTabs.size() == " + mTabs.size() + ", cur: " + mCurrentTab);
		myAdapter = new MyAdapter(getSupportFragmentManager(), mTabs);

		mViewPager = (ViewPager) findViewById(R.id.mViewPager);
		mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
		mViewPager.setAdapter(myAdapter);
		mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
		mViewPager.setOffscreenPageLimit(mTabs.size());
		mTabLayout.setupWithViewPager(mViewPager);
		mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				mViewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});


		mViewPager.setCurrentItem(mCurrentTab);
		mLastTab = mCurrentTab;

		if(mTabs.size()>=5){
			mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
		}else {
			mTabLayout.setTabMode(TabLayout.MODE_FIXED);

			mTabLayout.post(new Runnable() {
				@Override
				public void run() {
					TabUtil.setIndicatorWidth(mTabLayout,DensityUtil.dip2px(5));
				}
			});
		}
	}

	/**
	 * 添加一个选项卡
	 *
	 * @param tab
	 */
	public void addTabInfo(TabInfo tab) {
		mTabs.add(tab);
		myAdapter.notifyDataSetChanged();
		mTabLayout.setupWithViewPager(mViewPager);
	}

	/**
	 * 从列表添加选项卡
	 *
	 * @param tabs
	 */
	public void addTabInfos(ArrayList<TabInfo> tabs) {
		mTabs.addAll(tabs);
		myAdapter.notifyDataSetChanged();
		mTabLayout.setupWithViewPager(mViewPager);
	}


	protected TabInfo getFragmentById(int tabId) {
		if (mTabs == null) {
			return null;
		}
		for (int index = 0, count = mTabs.size(); index < count; index++) {
			TabInfo tab = mTabs.get(index);
			if (tab.getId() == tabId) {
				return tab;
			}
		}
		return null;
	}

	/**
	 * 跳转到任意选项卡
	 *
	 * @param tabId 选项卡下标
	 */
	public void navigate(int tabId) {
		for (int index = 0, count = mTabs.size(); index < count; index++) {
			if (mTabs.get(index).getId() == tabId) {
				mViewPager.setCurrentItem(index);
			}
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	/**
	 * 返回layout id
	 *
	 * @return layout id
	 */
	protected abstract int getMainViewResId();

	/**
	 * 在这里提供要显示的选项卡数据
	 */
	protected abstract int supplyTabs(List<TabInfo> tabs);

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// for fix a known issue in support library
		// https://code.google.com/p/android/issues/detail?id=19917
		outState.putString("WORKAROUND_FOR_BUG_19917_KEY",
				"WORKAROUND_FOR_BUG_19917_VALUE");
		super.onSaveInstanceState(outState);
	}


	public ArrayList<TabInfo> getTabs() {
		return mTabs;
	}
}



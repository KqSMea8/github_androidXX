package com.bsoft.module_main.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.Space;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bigkoo.convenientbanner.view.CBLoopViewPager;
import com.bsoft.baselib.base.BaseFragment;
import com.bsoft.baselib.util.ScreenUtil;
import com.bsoft.baselib.widget.BsoftActionBar;
import com.bsoft.commonlib.widget.WrapViewpager;
import com.bsoft.commonlib.widget.viewpager.transformer.RoateTransformer;
import com.bsoft.module_main.R;
import com.bsoft.module_main.bean.ModuleVo;
import com.bsoft.module_main.bean.banner.BannerVo;
import com.bsoft.module_main.util.NetworkImageHolderView;
import com.viewpagerindicator.CirclePageIndicator;


import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends BaseFragment {
//       ARouter.getInstance().build(ConstantUrl.SEARCH).navigation();
//                Intent intent=new Intent(getContext(), SearchActivity.class);
//                startActivity(intent);
    ArrayList<BannerVo> bannerList = new ArrayList<>();
    MyPagerAdapter myPagerAdapter;
    WrapViewpager viewPager;
    CirclePageIndicator indicator;
    private BsoftActionBar mActionBar;
    private ConvenientBanner convenientBanner;

    @Override
    public void startHint() {
        if (isLoaded || !isReceiver) {
            return;
        }
       // taskGetData();
        isLoaded = true;
    }

    @Override
    public void endHint() {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View mainView) {

        mActionBar = (BsoftActionBar) mainView.findViewById(R.id.actionbar);
        mActionBar.setBackgroundColor(ContextCompat.getColor(baseContext, R.color.actionbar_bg));
        mActionBar.setTitle("首页");
        convenientBanner = (ConvenientBanner) mainView.findViewById(R.id.convenientBanner);
        viewPager = (WrapViewpager) mainView.findViewById(R.id.viewPager);
        indicator = (CirclePageIndicator)mainView.findViewById(R.id.indicator);
       // Eyes.setStatusBarLightMode(getActivity(), Color.WHITE);

        initBanner();
        showLocalBanner();
        setModuleTest(13);

    }

    private void initBanner() {
        ViewGroup.LayoutParams lp = convenientBanner.getLayoutParams();
        int windowWidth= ScreenUtil.getScreenWidth();
        lp.width = windowWidth;
        lp.height = (int)(lp.width * 36.0/75);
        convenientBanner.setLayoutParams(lp);
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, bannerList)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                //设置翻页的效果，不需要翻页效果可用不设
//                .setPageTransformer(new ZoomOutSlideTransformer())
                .setPageTransformer(new RoateTransformer())
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                       //TODO
                    }
                })
        ;
        CBLoopViewPager viewPager = convenientBanner.getViewPager();
        int pagerWidth = (int)(windowWidth * 64.0 / 75);
        RelativeLayout.LayoutParams vpLayout = (RelativeLayout.LayoutParams) viewPager.getLayoutParams();
        if (vpLayout == null) {
            vpLayout = new RelativeLayout.LayoutParams(pagerWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
        } else {
            vpLayout.width = pagerWidth;
        }
        vpLayout.addRule(RelativeLayout.CENTER_IN_PARENT);

        viewPager.setLayoutParams(vpLayout);
        viewPager.setClipChildren(false);

        ((RelativeLayout) (convenientBanner.getViewPager().getParent())).setClipChildren(false);

        viewPager.setOffscreenPageLimit(3);
        int margin = (int) ((windowWidth - pagerWidth) / 1.8);
        viewPager.setPageMargin(-1 * margin);

    }
    //先显示本地banner
    private void showLocalBanner() {
      //  ArrayList<BannerVo> localBannerList = application.getBannerList();
        ArrayList<BannerVo> localBannerList = new ArrayList<>();
        if (localBannerList != null && !localBannerList.isEmpty()) {
            bannerList.clear();
            bannerList.addAll(localBannerList);
            convenientBanner.notifyDataSetChanged();
            convenientBanner.startTurning(5000);
        } else {
            showDefaultBanner();
        }
    }
    //显示默认banner
    private void showDefaultBanner() {
        bannerList.clear();
        BannerVo bannerVo=new BannerVo();
        bannerVo.resId= R.drawable.ic_default_banner;
        bannerList.add(bannerVo);
        bannerList.add(bannerVo);
        bannerList.add(bannerVo);
        convenientBanner.notifyDataSetChanged();
        convenientBanner.stopTurning();
    }
    private void setModuleTest(int count) {
        ArrayList<ModuleVo> list = new ArrayList<>();
        list.add(new ModuleVo("预约挂号", R.drawable.mid_register));
        list.add(new ModuleVo("医学功能", R.drawable.mid_service_function));
        list.add(new ModuleVo("转诊管理", R.drawable.mid_change));
        list.add(new ModuleVo("健康档案", R.drawable.mid_heath));
        list.add(new ModuleVo("健康随访", R.drawable.mid_follow));
        list.add(new ModuleVo("慢病档案", R.drawable.mid_disease));
        list.add(new ModuleVo("中医辩体", R.drawable.mid_medicine));
        list.add(new ModuleVo("自理能力", R.drawable.mid_themselves));
        list.add(new ModuleVo("妇幼健康", R.drawable.mid_children));
        list.add(new ModuleVo("儿童保健", R.drawable.mid_mat));
        list.add(new ModuleVo("健康教育", R.drawable.mid_book));
        list.add(new ModuleVo("家庭病床", R.drawable.mid_hospital_bed));
        list.add(new ModuleVo("结核病随访", R.drawable.mid_tuberculosis));
        setModulePager(list);
    }

    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        //此值不能小于1200（即ViewPagerScroller的mScrollDuration的值），否则最后一页翻页效果会出问题。如果硬要兼容1200以下，那么请修改ViewPagerScroller的mScrollDuration的值，不过修改后，3d效果就没那么明显了。
        convenientBanner.startTurning(5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    private void setModulePager(ArrayList<ModuleVo> allModuleList) {
        int step = 8;
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < allModuleList.size(); i = i + step) {

            List<ModuleVo> attentionVos = allModuleList.subList(i,
                    (i + step) > allModuleList.size() ? allModuleList.size() : (i + step));
            ArrayList<ModuleVo> temp = new ArrayList<>();
            for (ModuleVo vo : attentionVos) {
                temp.add(vo);
            }
            ModuleFragment fragment = ModuleFragment.newInstance(temp);
            fragments.add(fragment);
        }
         myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), fragments);  //fragment
      //  myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);   //activity
        viewPager.setAdapter(myPagerAdapter);
        indicator.setViewPager(viewPager);
    }

    private static class MyPagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<Fragment> mFragmentList;
//        ArrayList<ArrayList<ModuleVo>> datas;

        public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragmentList) {
            super(fm);
            this.mFragmentList = mFragmentList;
            notifyDataSetChanged();
        }

        /**
         * 得到每个页面
         */
        @Override
        public Fragment getItem(int arg0) {
            Fragment fragment = (mFragmentList == null || mFragmentList.size() == 0) ? null
                    : mFragmentList.get(arg0);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("dataList", datas.get(arg0));
//            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return mFragmentList == null ? 0 : mFragmentList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }
}

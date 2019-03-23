package com.bsoft.module_main.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import com.bsoft.baselib.base.BaseFragment;
import com.bsoft.baselib.util.ScreenUtil;
import com.bsoft.commonlib.widget.LinearLineWrapLayout;
import com.bsoft.module_main.R;



/**
 *
 */
public class MyFragment extends BaseFragment {

    LayoutInflater mLayoutInflater;
    private LinearLineWrapLayout layOpt;
    private ImageView mSetting;

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
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View rootView) {
        this.mLayoutInflater = (LayoutInflater) baseContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layOpt = rootView.findViewById(R.id.lay_app);
        mSetting = rootView.findViewById(R.id.setting);

        createOptView();
        findView();
        setClick();
       // showLoadingView();
//        final LoadingDialog  loadingDialog = new LoadingDialog();
//        loadingDialog.build(getContext()).show(null);
//        loadingDialog.dismiss();
    }

    private void setClick() {
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), SettingActivity.class);
//                startActivity(intent);
               // showToast("qqqqqqqq");
        //        ARouter.getInstance().build(ConstantUrl.CONSULTHIS).navigation();
             //   ARouter.getInstance().build(ConstantUrl.SETTINGOUT).navigation();
            }
        });

    }

    void createOptView() {  // new Intent(baseContext, AppointHistroyActivity.class)
        layOpt.removeAllViews();
        layOpt.addView(createAppView(R.drawable.icon_yuyue, "挂号预约记录",
                null, true));
        layOpt.addView(createAppView(R.drawable.icon_yuyue, "体检预约记录",
                null, true));
        layOpt.addView(createAppView(R.drawable.icon_fuwu, "服务记录",
                null, true));
        layOpt.addView(createAppView(R.drawable.icon_pingjia, "评价建议",
                null, false));
//动态获取
//        ArrayList<MyItemVo>  list=new  ArrayList<MyItemVo>();
//        for(int i = 0; i < list.size(); i++){
//
//            layOpt.addView(createAppView(R.drawable.icon_pingjia, "评价建议",
//                    null,  i == list.size()-1?true:false));
//        }
    }

    View createAppView(int icon, String content, final Intent intent, boolean showLine, final int... spTag) {
        LinearLayout view = (LinearLayout) mLayoutInflater.inflate(
                R.layout.home_app2, null);
        int laywidth = ScreenUtil.getScreenWidth();
//        LinearLineWrapLayout.LayoutParams layoutParams = new LinearLineWrapLayout.LayoutParams(laywidth - 4,
//                ViewGroup.LayoutParams.WRAP_CONTENT - 4);
        LinearLineWrapLayout.LayoutParams layoutParams = new LinearLineWrapLayout.LayoutParams(laywidth,
                LinearLineWrapLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);

        ((TextView) view.findViewById(R.id.tvName)).setText(content);
        ((ImageView) view.findViewById(R.id.ivIcon)).setImageResource(icon);
        view.findViewById(R.id.vLine).setVisibility(showLine ? View.VISIBLE : View.INVISIBLE);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (null != intent) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "功能暂未开通...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }




    private void findView() {

    }
}

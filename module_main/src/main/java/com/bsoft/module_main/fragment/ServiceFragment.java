package com.bsoft.module_main.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.Space;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.baselib.base.BaseFragment;
import com.bsoft.baselib.util.EffectUtil;
import com.bsoft.baselib.util.ScreenUtil;

import com.bsoft.commonlib.RouterPath;
import com.bsoft.commonlib.widget.LinearLineWrapLayout;
import com.bsoft.module_main.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends BaseFragment {
    LayoutInflater mLayoutInflater;
    private Space mSpace;
    LinearLineWrapLayout layApp;
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
        return R.layout.fragment_service;
    }

    @Override
    protected void initView(View rootView) {
        this.mLayoutInflater = (LayoutInflater) getActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        layApp=rootView.findViewById(R.id.layApp);
        createAppView();


//        bt1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ARouter.getInstance().build(ConstantUrl.CONSULTHIS).navigation();
//            }
//        });
//        bt2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ARouter.getInstance().build(ConstantUrl.IMAGECHOOSE).navigation();
//            }
//        });
//        bt3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ARouter.getInstance().build(ConstantUrl.TABLAYOUT).navigation();
//            }
//        });
//        bt4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // ARouter.getInstance().build(ConstantUrl.STRPER).navigation();
//            }
//        });
    }
    void createAppView() {
        int laywidth = ScreenUtil.getScreenWidth() / 3;

        //Intent intent = (Intent) ARouter.getInstance().build(ConstantUrl.CONSULTHIS).navigation();
        layApp.addView(createAppView("咨询记录",
                R.mipmap.icon_jkjc, null, laywidth));
        layApp.addView(createAppView("图片选择",
                R.mipmap.icon_yytx, null, laywidth));
        /* new Intent(baseContext, WebActivity.class)
                        .putExtra("title", "健康百科")
                        .putExtra("url", "http://115.236.19.147:14188/ckbserver/view/index")*/
        layApp.addView(createAppView("TABLAYOUT",
                R.mipmap.icon_jkbk,
               null, laywidth));
        layApp.addView(createAppView("SEGMENTGROUP",
                R.mipmap.icon_bgcx, null, laywidth));
        layApp.addView(createAppView("IPC",
                R.mipmap.icon_pjjy, null, laywidth));
        layApp.addView(createAppView("POPWINDOW",
                R.mipmap.icon_hdzq, null, laywidth));

        layApp.addView(createAppView("权限申请",
                R.mipmap.icon_jkjc, null, laywidth));
        layApp.addView(createAppView("通讯录索引",
                R.mipmap.icon_yytx, null, laywidth));
        /* new Intent(baseContext, WebActivity.class)
                        .putExtra("title", "健康百科")
                        .putExtra("url", "http://115.236.19.147:14188/ckbserver/view/index")*/
        layApp.addView(createAppView("沉浸式Behavior",
                R.mipmap.icon_jkbk,
                null, laywidth));
        layApp.addView(createAppView("COORDINATORTAB",
                R.mipmap.icon_bgcx, null, laywidth));
        layApp.addView(createAppView("仿阿里首页",
                R.mipmap.icon_pjjy, null, laywidth));
        layApp.addView(createAppView("POPWINDOW",
                R.mipmap.icon_hdzq, null, laywidth));

        layApp.addView(createAppView("沉浸式Behavior",
                R.mipmap.icon_jkbk,
                null, laywidth));
        layApp.addView(createAppView("COORDINATORTAB",
                R.mipmap.icon_bgcx, null, laywidth));
        layApp.addView(createAppView("WHEELVIEW",
                R.mipmap.icon_pjjy, null, laywidth));
        layApp.addView(createAppView("LIVEDATA",
                R.mipmap.icon_hdzq, null, laywidth));

        layApp.addView(createAppView("沉浸式Behavior",
                R.mipmap.icon_jkbk,
                null, laywidth));
        layApp.addView(createAppView("COORDINATORTAB",
                R.mipmap.icon_bgcx, null, laywidth));
        layApp.addView(createAppView("仿阿里首页",
                R.mipmap.icon_pjjy, null, laywidth));
        layApp.addView(createAppView("POPWINDOW",
                R.mipmap.icon_hdzq, null, laywidth));

    }

    View createAppView(final String title, int icon, final Intent intent, int laywidth) {
        LinearLayout view = (LinearLayout) mLayoutInflater.inflate(
                R.layout.home_app, null);

//分割线
        // LinearLineWrapLayout.LayoutParams layoutParams = new LinearLineWrapLayout.LayoutParams(laywidth,
        //   LinearLineWrapLayout.LayoutParams.WRAP_CONTENT);

        LinearLineWrapLayout.LayoutParams layoutParams = new LinearLineWrapLayout.LayoutParams(laywidth - 4,
                LinearLineWrapLayout.LayoutParams.WRAP_CONTENT - 4);
        layoutParams.setMargins(2, 2, 2, 2);
        view.setLayoutParams(layoutParams);
        ((TextView) view.findViewById(R.id.indexText)).setText(title);
        ((ImageView) view.findViewById(R.id.index)).setImageResource(icon);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (null != intent) {
                    startActivity(intent);
                } else {
                    if(title.equals("咨询记录")){
                        ARouter.getInstance().build(RouterPath.SHAPE)  /**可以针对性跳转跳转动画*/
                                .withTransition(R.anim.activity_up_in, R.anim.activity_up_out).navigation();
                    }
// else if(title.equals("图片选择")){
//                        ARouter.getInstance().build(ConstantUrl.IMAGECHOOSE)
//                                .withTransition(R.anim.activity_up_in, R.anim.activity_up_out).navigation();
//                    } else if(title.equals("TABLAYOUT")){
//                        ARouter.getInstance().build(ConstantUrl.TABLAYOUT).navigation();
//                    } else if(title.equals("SEGMENTGROUP")){
//                        ARouter.getInstance().build(ConstantUrl.IPC).navigation();
//                     } else if(title.equals("IPC")){
//                        ARouter.getInstance().build(ConstantUrl.IPC).navigation();
//                    } else if(title.equals("POPWINDOW")){
//                        ARouter.getInstance().build(ConstantUrl.POPWINDOW).navigation();
//                    } else if(title.equals("权限申请")){
//                        ARouter.getInstance().build(ConstantUrl.RXPERMISSON).navigation();
//                    } else if(title.equals("咨询")){
//                        ARouter.getInstance().build(ConstantUrl.CONSULT).navigation();
//                    }
//                    else if(title.equals("通讯录索引")){
//                        ARouter.getInstance().build(ConstantUrl.INDEXBAR).navigation();
//                    } else if(title.equals("沉浸式Behavior")){
//                        ARouter.getInstance().build(ConstantUrl.MYVIEW).navigation();
//                    }else if(title.equals("COORDINATORTAB")){
//                        ARouter.getInstance().build(ConstantUrl.COORDINATORTABLAYOUT).navigation();
//                    }else if(title.equals("WHEELVIEW")){
//                        ARouter.getInstance().build(ConstantUrl.WHEELVIEW).navigation();
//                    } else if(title.equals("LIVEDATA")){
//                        ARouter.getInstance().build(ConstantUrl.LIVEDATA).navigation();
//                    }
//
//                    else {
//                        Toast.makeText(application, "开发中...", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });
        EffectUtil.addClickEffect(view);
        return view;
    }
}

/*   if(TextUtils.isEmpty(item.actPath)){
                showToast("暂未开放");
            }else{
                ARouter.getInstance().build(item.actPath).navigation();
            }*/
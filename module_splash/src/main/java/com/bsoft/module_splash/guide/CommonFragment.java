package com.bsoft.module_splash.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bsoft.baselib.util.EffectUtil;
import com.bsoft.module_splash.R;



/**
 * Created by Administrator on 2017/9/14.
 */

public class CommonFragment extends BaseFragment {
    ImageView iv;
    ImageView ivIn;
    private View mainView;

    public static BaseFragment getInstance(int imgRes, boolean isEnd){
        CommonFragment fragment = new CommonFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("imgRes", imgRes);
        bundle.putBoolean("isEnd", isEnd);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_guide, container, false);
        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iv = (ImageView) mainView.findViewById(R.id.iv);
        ivIn = (ImageView) mainView.findViewById(R.id.ivIn);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int imgRes = bundle.getInt("imgRes", 0);
            boolean isEnd = bundle.getBoolean("isEnd", false);
            iv.setImageResource(imgRes);
            ivIn.setVisibility(isEnd ? View.VISIBLE : View.GONE);
        }



        EffectUtil.addClickEffect(ivIn);
        ivIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GuideActivity)getActivity()).goIn();
            }
        });
    }
}

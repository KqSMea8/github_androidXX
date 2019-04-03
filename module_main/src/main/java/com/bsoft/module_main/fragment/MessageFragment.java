package com.bsoft.module_main.fragment;


import android.app.Fragment;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.view.View;


import com.bsoft.baselib.base.BaseFragment;
import com.bsoft.baselib.util.LiveDataBus;
import com.bsoft.baselib.widget.BsoftActionBar;
import com.bsoft.commonlib.widget.ImageChooseView;
import com.bsoft.module_main.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment {

    private BsoftActionBar actionBar;

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
        return R.layout.fragment_message;
    }

    @Override
    protected void initView(View rootView) {

        ImageChooseView mImageChoose = (ImageChooseView) rootView.findViewById(R.id.imageChoose);

        mImageChoose.setTittleAndMaxCount("上传图文照片",4,2,9);


        LiveDataBus.get()
                .with("key_name", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        showToast(com.alibaba.fastjson.JSONArray.toJSONString(mImageChoose.getLocalPaths()));
                    }
                });


    }

}

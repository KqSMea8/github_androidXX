package com.bsoft.module_main.fragment;


import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.bsoft.baselib.base.BaseFragment;
import com.bsoft.baselib.widget.BsoftActionBar;
import com.bsoft.commonlib.util.TMmkv;
import com.bsoft.commonlib.util.TPreferences;
import com.bsoft.module_main.R;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.functions.Consumer;


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


    }

}

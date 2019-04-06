package com.bsoft.module_account.activity.changenet;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.bsoft.baselib.base.BaseActivity;
import com.bsoft.baselib.widget.BsoftActionBar;
import com.bsoft.commonlib.widget.FilterEmoji;
import com.bsoft.module_account.R;
import com.bsoft.commonlib.bean.NetAddressVo;
import com.bsoft.module_account.bean.NetRadio;
import com.bsoft.commonlib.util.NetEnvironmentUtil;

import java.util.ArrayList;




/**
 * create change_net chenkai 20170904
 */

public class ChangeNetActivity extends BaseActivity {
    /*Default*/
    /*Util*/
    private Handler handler = new Handler();
    /*Flag*/
    private NetAddressVo originVo;
    private ArrayList<NetAddressVo> netAddressVos;
    private ArrayList<NetRadio> radioButtons = new ArrayList<>();
    private NetAddressVo curAddressVo;
    /*View*/


    RadioGroup changeItems;
    private EditText edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_net);
        //default
        netAddressVos = NetEnvironmentUtil.getNetEnvironments(this);
        originVo = NetEnvironmentUtil.getCurEnvironment(this);
        //findview
        findView();
        //init

        updateVo(originVo);
        //动态添加选项
        radioButtons.clear();
        if (netAddressVos != null && !netAddressVos.isEmpty()) {
            for (int i = 0; i < netAddressVos.size(); i++) {
                NetAddressVo vo = netAddressVos.get(i);
                if (vo != null) {
                    addRadioBtn(i, vo);
                }
            }
        }
        //listener
        edit.addTextChangedListener(new FilterEmoji(edit, baseContext));
        changeItems.setOnCheckedChangeListener(checkedChangeListener);
//        edit.addTextChangedListener(textWatcher);


        actionBar.addAction(new BsoftActionBar.Action() {
            @Override
            public int getDrawable() {
                return 0;
            }

            @Override
            public String getText() {
                return getString(R.string.ok);
            }

            @Override
            public void performAction(View view) {
                hideKeyboard();
                if (originVo != null && curAddressVo != null
                        && (!TextUtils.equals(originVo.getHttpHcnUrl(), edit.getText().toString())
                        || !TextUtils.equals(originVo.getEnvironment(), curAddressVo.getEnvironment()))) {
                    NetEnvironmentUtil.setEnvironment(ChangeNetActivity.this, curAddressVo.getEnvironment());

                    handler.removeCallbacks(exitRunnable);
                    handler.postDelayed(exitRunnable, 100);
                } else {
                    finish();
                }
            }
        });
    }

    //******************************公共方法**************************************
    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle(R.string.change_api_url);
        actionBar.setBackAction(new BsoftActionBar.Action() {
            @Override
            public void performAction(View view) {
                hideKeyboard();
                finish();
            }

            @Override
            public int getDrawable() {
                return R.drawable.btn_back;
            }

            @Override
            public String getText() {
                return null;
            }
        });

        edit = (EditText) findViewById(R.id.edit);
        changeItems = (RadioGroup) findViewById(R.id.change_items);
    }

    //******************************私有方法**************************************
    private void updateVo(NetAddressVo addressVo) {
        if (addressVo == null) {
            return;
        }
        curAddressVo = addressVo;
        edit.setText(addressVo.getHttpHcnUrl());
        edit.setEnabled(false);
    }

    private Runnable exitRunnable = new Runnable() {
        @Override
        public void run() {
            System.exit(0);//重启应用
        }
    };

    private void addRadioBtn(int id, NetAddressVo addressVo) {
        if (addressVo == null) {
            return;
        }
        RadioButton radioButton = new RadioButton(this);
        NetRadio netRadio = new NetRadio();

        radioButton.setId(id);
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT
                , RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        radioButton.setLayoutParams(layoutParams);
        radioButton.setText(addressVo.getEnvironmentText() + " " + addressVo.getHttpHcnUrl());
        radioButton.setTextSize(12);
        radioButton.setTextColor(Color.parseColor("#333333"));
        //radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setPadding(10, 10, 10, 10);
        if (originVo != null && TextUtils.equals(originVo.getEnvironment(), addressVo.getEnvironment())) {
            radioButton.setChecked(true);
        } else {
            radioButton.setChecked(false);
        }
        netRadio.addressVo = addressVo;
        netRadio.radioButton = radioButton;
        radioButtons.add(netRadio);
        changeItems.addView(radioButton);
    }

    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            NetRadio radio = radioButtons.get(i);
            if (radio != null) {
                updateVo(radio.addressVo);
            }

        }
    };
}

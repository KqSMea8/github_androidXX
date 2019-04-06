package com.bsoft.module_account.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bsoft.baselib.base.BaseActivity;
import com.bsoft.baselib.util.ScreenUtil;
import com.bsoft.baselib.util.StringUtil;
import com.bsoft.baselib.widget.BsoftActionBar;
import com.bsoft.commonlib.Constants;
import com.bsoft.commonlib.widget.CountDownHelper;
import com.bsoft.commonlib.widget.FilterEmoji;
import com.bsoft.module_account.R;
import com.bsoft.network_api.bean.ResultModel;
import com.bsoft.network_api.net.NetClient;

import java.util.ArrayList;


/**
 * 修改手机（hcn代码）
 */
public class SettingPhoneActivity extends BaseActivity implements View.OnClickListener{

    private TextView tvNext,tvT1,tvT2,tvT3;
    private EditText etPhone1,etPhone2,etCode1,etCode2,et_card;
    private LinearLayout step1Layout,ll_verify;
    private LinearLayout step2Layout;//验证新手机
    private Button btnCheckCard1,btnCheckCard2,btn_vertify_card,btn_vertify_msg;
    private ImageView ivPhoneClear2,ivP1,ivP2,ivP3,ivPp;
    private View view1,view1_2,view2_1,view2;//两条横线
    private CountDownHelper countDownTimer1,countDownTimer2;

    private RelativeLayout rl_identifying_card;//验证卡
    private RelativeLayout rl_identifying_msg;//验证短信

    //private CheckIdCardTask checkIdCardTask;

    int getCode;
    int step = 0;
    float fromx = 0;
    float tox = 0;

    public int currentState = 1;//1短信验证，2证件验证



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingphone);
        findView();
        setClick();
        initData();
    }

    @Override
    public void findView() {
        findActionBar();
        actionBar.setTitle("修改手机");
        actionBar.setBackAction(new BsoftActionBar.Action() {
            @Override
            public int getDrawable() {
                return R.drawable.btn_back;
            }

            @Override
            public String getText() {
                return null;
            }

            @Override
            public void performAction(View view) {
                switch (step) {
                    case 0:
                        finish();
                        break;
                    case 1:
                        fromx = ScreenUtil.getScreenWidth() * 1 / 6 - ivPp.getWidth() / 2;
                        tox = ScreenUtil.getScreenWidth() * 3 / 6 - ivPp.getWidth() / 2;
                        startAnimationBack();
                        break;
                    case 2:
                        fromx = ScreenUtil.getScreenWidth() * 3 / 6 - ivPp.getWidth() / 2;
                        tox = ScreenUtil.getScreenWidth() * 5 / 6 - ivPp.getWidth() / 2;
                        startAnimationBack();
                        break;
                }
            }
        });

        tvNext = (TextView)findViewById(R.id.tv_next);
        step1Layout = (LinearLayout)findViewById(R.id.step1Layout);
        step2Layout = (LinearLayout)findViewById(R.id.step2Layout);
        ll_verify = (LinearLayout)findViewById(R.id.ll_verify);
        btn_vertify_msg = (Button)findViewById(R.id.btn_vertify_msg);
        btn_vertify_card = (Button)findViewById(R.id.btn_vertify_card);
        btnCheckCard1 = (Button)findViewById(R.id.btn_checkcard_1);
        btnCheckCard2 = (Button)findViewById(R.id.btn_checkcard_2);
        rl_identifying_card = (RelativeLayout)findViewById(R.id.rl_identifying_card);
        rl_identifying_msg = (RelativeLayout)findViewById(R.id.rl_identifying_msg);
        ivPhoneClear2 = (ImageView)findViewById(R.id.iv_phoneclear_2);
        etPhone1 = (EditText)findViewById(R.id.et_phone_1);
        etPhone1.addTextChangedListener(new FilterEmoji(etPhone1, baseContext));
        etPhone2 = (EditText)findViewById(R.id.et_phone_2);
        etPhone2.addTextChangedListener(new FilterEmoji(etPhone2, baseContext));
        et_card = (EditText)findViewById(R.id.et_card);
        et_card.addTextChangedListener(new FilterEmoji(et_card, baseContext));
        etCode1 = (EditText)findViewById(R.id.et_code_1);
        etCode1.addTextChangedListener(new FilterEmoji(etCode1, baseContext));
        etCode2 = (EditText)findViewById(R.id.et_code_2);
        etCode2.addTextChangedListener(new FilterEmoji(etCode2, baseContext));
        ivP1 = (ImageView)findViewById(R.id.iv_p1);
        ivP2 = (ImageView)findViewById(R.id.iv_p2);
        ivP3 = (ImageView)findViewById(R.id.iv_p3);
        view1 = (View)findViewById(R.id.view_1);
        view2 = (View)findViewById(R.id.view_2);
        view1_2 = (View)findViewById(R.id.view1_2);
        view2_1 = (View)findViewById(R.id.view2_1);


        ivPp = (ImageView)findViewById(R.id.iv_pp);
        tvT1 = (TextView)findViewById(R.id.tv_t1);
        tvT2 = (TextView)findViewById(R.id.tv_t2);
        tvT3 = (TextView)findViewById(R.id.tv_t3);

        setView();
    }

    private void setClick()
    {
        btnCheckCard1.setOnClickListener(this);
        btnCheckCard2.setOnClickListener(this);
        ivPhoneClear2.setOnClickListener(this);
        tvNext.setOnClickListener(this);

        btn_vertify_card.setOnClickListener(this);
        btn_vertify_msg.setOnClickListener(this);

        etPhone2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etPhone2.getText().toString().length() == 0) {
                    ivPhoneClear2.setVisibility(View.INVISIBLE);
                } else {
                    ivPhoneClear2.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void changeStatue() {
        switch (step) {
            case 0:
                ivP1.setImageResource(R.drawable.pwd_q3);
                ivP2.setImageResource(R.drawable.pwd_q1);
                ivP3.setImageResource(R.drawable.pwd_q1);
                tvT1.setTextColor(getResources().getColor(R.color.pwdtest1));
                tvT2.setTextColor(getResources().getColor(R.color.pwdtest2));
                tvT3.setTextColor(getResources().getColor(R.color.pwdtest2));
                view1.setBackgroundResource(R.color.pwd_view_1);
                view1_2.setBackgroundResource(R.color.pwd_view_1);
                view2.setBackgroundResource(R.color.pwd_view_1);
                view2_1.setBackgroundResource(R.color.pwd_view_1);
                break;
            case 1:
                ivP1.setImageResource(R.drawable.pwd_q2);
                ivP2.setImageResource(R.drawable.pwd_q3);
                ivP3.setImageResource(R.drawable.pwd_q1);
                tvT1.setTextColor(getResources().getColor(R.color.actionbar_bg));
                tvT2.setTextColor(getResources().getColor(R.color.pwdtest1));
                tvT3.setTextColor(getResources().getColor(R.color.pwdtest2));
                view1.setBackgroundColor(getResources().getColor(R.color.pwd_view_2));
                view1_2.setBackgroundColor(getResources().getColor(R.color.pwd_view_2));
                view2.setBackgroundColor(getResources().getColor(R.color.pwd_view_1));
                view2_1.setBackgroundColor(getResources().getColor(R.color.pwd_view_1));
                break;
            case 2:
                ivP1.setImageResource(R.drawable.pwd_q2);
                ivP2.setImageResource(R.drawable.pwd_q2);
                ivP3.setImageResource(R.drawable.pwd_q3);
                tvT1.setTextColor(getResources().getColor(R.color.actionbar_bg));
                tvT2.setTextColor(getResources().getColor(R.color.actionbar_bg));
                tvT3.setTextColor(getResources().getColor(R.color.pwdtest1));
                view1.setBackgroundColor(getResources().getColor(R.color.pwd_view_2));
                view2.setBackgroundColor(getResources().getColor(R.color.pwd_view_2));
                view2.setBackgroundColor(getResources().getColor(R.color.pwd_view_2));
                view2_1.setBackgroundColor(getResources().getColor(R.color.pwd_view_2));
                break;
            default:
                break;
        }
    }

    private void initData(){

        countDownTimer1 = new CountDownHelper(btnCheckCard1, 60,
                "获取验证码", "重新获取",
                ContextCompat.getColor(baseActivity, R.color.white),
                ContextCompat.getColor(baseActivity, R.color.white),
                R.drawable.bule_small_round_rect_n,
                R.drawable.gray_small_round_rect_n);
        countDownTimer2 = new CountDownHelper(btnCheckCard2, 60,
                "获取验证码", "重新获取",
                ContextCompat.getColor(baseActivity, R.color.white),
                ContextCompat.getColor(baseActivity, R.color.white),
                R.drawable.bule_small_round_rect_n,
                R.drawable.gray_small_round_rect_n);
       
        etPhone1.setText(getUserPhone());
        changeStatue();
    }

    /**
     * 获取用户手机号
     * @return
     */
    private String getUserPhone() {
        return null;
                //application.getUserPhone();
    }

    private void setView() {
        switch (step) {
            case 0:
                step1Layout.setVisibility(View.GONE);
                step2Layout.setVisibility(View.GONE);
                tvNext.setVisibility(View.GONE);
                ll_verify.setVisibility(View.VISIBLE);
                break;
            case 1:
                step1Layout.setVisibility(View.VISIBLE);
                step2Layout.setVisibility(View.GONE);
                ll_verify.setVisibility(View.GONE);
                tvNext.setVisibility(View.VISIBLE);
                tvNext.setText("下一步");
                break;
            case 2:
                step1Layout.setVisibility(View.GONE);
                step2Layout.setVisibility(View.VISIBLE);
                ll_verify.setVisibility(View.GONE);
                tvNext.setVisibility(View.VISIBLE);
                tvNext.setText("验证");
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_checkcard_1) {
            if (!StringUtil.isEmpty(etPhone1.getText().toString())) {
                getCheckCode(etPhone1.getText().toString());
            }

        } else if (i == R.id.btn_checkcard_2) {
            if (!StringUtil.isEmpty(etPhone2.getText().toString())) {
                getCheckCode(etPhone2.getText().toString());
            } else {
                Toast.makeText(baseContext, "请输入手机号", Toast.LENGTH_SHORT).show();
            }

        } else if (i == R.id.tv_next) {
            switch (step) {
                case 1:
                    if (currentState == 1) {
                        if (StringUtil.isEmpty(etCode1.getText().toString())) {
                            etCode1.requestFocus();
                            Toast.makeText(SettingPhoneActivity.this, "验证码不能为空，请输入", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        fromx = ScreenUtil.getScreenWidth() * 3 / 6 - ivPp.getWidth()
                                / 2;
                        tox = ScreenUtil.getScreenWidth() * 5 / 6
                                - ivPp.getWidth() / 2;
                        passCall(etPhone1.getText().toString(), etCode1.getText().toString());
                    } else if (currentState == 2) {
                        if (StringUtil.isEmpty(et_card.getText().toString())) {
                            et_card.requestFocus();
                            Toast.makeText(SettingPhoneActivity.this, "证件号不能为空，请输入", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        fromx = ScreenUtil.getScreenWidth() * 3 / 6 - ivPp.getWidth()
                                / 2;
                        tox = ScreenUtil.getScreenWidth() * 5 / 6
                                - ivPp.getWidth() / 2;
                        passCall(etPhone1.getText().toString(), et_card.getText().toString());
                    }
                    break;
                case 2:
                    if (StringUtil.isEmpty(etPhone2.getText().toString())) {
                        etPhone2.requestFocus();
                        Toast.makeText(SettingPhoneActivity.this, "手机号不能为空，请输入",
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else if (!StringUtil.isMobilPhoneNumber(etPhone2.getText()
                            .toString())) {
                        etPhone2.requestFocus();
                        Toast.makeText(SettingPhoneActivity.this, "手机号不符合规则，请重新输入",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (StringUtil.isEmpty(etCode2.getText().toString())) {
                        etCode2.requestFocus();
                        Toast.makeText(SettingPhoneActivity.this, "验证码不能为空，请输入", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    passCall(etPhone2.getText().toString(), etCode2.getText().toString());
                    break;
                default:
                    break;
            }

        } else if (i == R.id.iv_phoneclear_2) {
            etPhone2.setText("");

        } else if (i == R.id.btn_vertify_msg) {
            currentState = 1;
            ll_verify.setVisibility(View.GONE);
            step1Layout.setVisibility(View.VISIBLE);
            rl_identifying_msg.setVisibility(View.VISIBLE);
            rl_identifying_card.setVisibility(View.GONE);
            tvNext.setVisibility(View.VISIBLE);
            fromx = ScreenUtil.getScreenWidth() * 1 / 6 - ivPp.getWidth()
                    / 2;
            tox = ScreenUtil.getScreenWidth() * 3 / 6
                    - ivPp.getWidth() / 2;
            startAnimation();

        } else if (i == R.id.btn_vertify_card) {
            currentState = 2;
            ll_verify.setVisibility(View.GONE);
            step1Layout.setVisibility(View.VISIBLE);
            rl_identifying_card.setVisibility(View.VISIBLE);
            rl_identifying_msg.setVisibility(View.GONE);
            tvNext.setVisibility(View.VISIBLE);
            fromx = ScreenUtil.getScreenWidth() * 1 / 6 - ivPp.getWidth()
                    / 2;
            tox = ScreenUtil.getScreenWidth() * 3 / 6
                    - ivPp.getWidth() / 2;
            startAnimation();

        }
    }

    /**
     * 验证
     */
    private void passCall(String phone, String code) {

        ArrayMap<String, String> head = new ArrayMap<>();
        ArrayList body = new ArrayList();
        switch (step)
        {
            case 1:
                if(currentState == 1){
               //     head.put(Constants.Head_Id, Constants.Sms_Service);
                    head.put(Constants.Head_Method, "validateCode");
                    body.add(Constants.Product_Name);
                    body.add(phone);
                    body.add(code);
                }else if(currentState == 2){
                    head.put(Constants.Head_Id, Constants.Person_Service);
                    head.put(Constants.Head_Method, "docCertificate");
              //      head.put(Constants.Head_Token, application.getAccessToken());
                    body.add(phone);
                    body.add(code);
                }
                break;
            case 2:
            //    head.put(Constants.Head_Token, application.getAccessToken());
                head.put(Constants.Head_Id, Constants.Person_Service);
                head.put(Constants.Head_Method, "changePhoneNo");
                body.add(Constants.Product_Name);
                body.add(phone);
                body.add(code);
                break;
            default:
                break;
        }

        
        NetClient.post(baseActivity, Constants.Json_Request, head, body, String.class,
                new NetClient.Listener<Boolean>() {
                    @Override
                    public void onPrepare() {
                        showLoadingDialog();
                    }

                    @Override
                    public void onSuccess(ResultModel<Boolean> result) {
                        dismissLoadingDialog();
                        if (result.isSuccess()) {
                            switch (step)
                            {
                                case 0:
                                    // 开始动画
                                    startAnimation();
                                    break;
                                case 1:
                                    if(currentState == 1){
                                        startAnimation();
                                    }else if(currentState == 2){
                                        Boolean state = result.data;
                                        if(state){
                                            startAnimation();
                                        }else{
                                            showToast("证件验证失败");
                                        }
                                    }
                                    break;
                                case 2:
                                    showToast("修改手机成功");
                                    String newPhone = etPhone2.getText().toString().trim();
                                    resetPhone(newPhone);
                                    Intent intent = getIntent();
                                    intent.putExtra("phoneNo", newPhone);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                    break;
                            }
                        } else {
                            showToast(result.getToast());
                        }
                    }

                    @Override
                    public void onFaile(Throwable t) {
                        dismissLoadingDialog();
                    }
                });
        
    }

    /**
     * 重置手机号
     * @param phone
     */
    private void resetPhone(String phone) {
//        application.setUserPhone(phone);
//        DocInfoVo docInfo = application.getDocInfo();
//        if(docInfo != null){
//            docInfo.phoneNo = phone;
//            application.setDocInfo(docInfo);
//        }
    }


    private void startAnimation() {
        TranslateAnimation tranAnim = new TranslateAnimation(fromx, tox, 0, 0);
        tranAnim.setDuration(600);
        ivPp.startAnimation(tranAnim);
        tranAnim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                ivPp.setVisibility(View.VISIBLE);
                switch (step)
                {
                    case 0:
                        ivP1.setImageResource(R.drawable.pwd_q1);
                        break;
                    case 1:
                        ivP2.setImageResource(R.drawable.pwd_q1);
                        break;
                }
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                step++;
                setView();
                ivPp.setVisibility(View.GONE);
                changeStatue();

            }
        });
    }

    //返回动画
    private void startAnimationBack() {
        TranslateAnimation tranAnim = new TranslateAnimation(tox, fromx, 0, 0);
        tranAnim.setDuration(600);
        ivPp.startAnimation(tranAnim);
        tranAnim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                ivPp.setVisibility(View.VISIBLE);
                switch (step) {
                    case 2:
                        ivP3.setImageResource(R.drawable.pwd_q1);
                        break;
                    case 1:
                        ivP2.setImageResource(R.drawable.pwd_q1);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                step--;
                switch (step) {
                    case 0:
                        ivPp.setVisibility(View.GONE);
                        setView();
                        changeStatue();
                        break;
                    case 1:
                        ivPp.setVisibility(View.GONE);
                        setView();
                        changeStatue();
                        break;
                    default:
                        break;
                }

            }
        });
    }

    /**
     * 获取验证码
     */
    private void getCheckCode(String phone) {
        ArrayMap<String, String> head = new ArrayMap<>();
    //    head.put(Constants.Head_Id, Constants.Sms_Service);

        ArrayList body = new ArrayList();
        switch (step){
            case 1:
                head.put(Constants.Head_Method, "getIdentifyingCodeByRegister");
                body.add(Constants.Product_Name);
                body.add(phone);
                break;
            case 2:
                head.put(Constants.Head_Method, "getIdentifyingCodeByUnRegister");
                body.add(Constants.Product_Name);
                body.add(phone);
                break;
        }
       
        NetClient.post(baseActivity, Constants.Json_Request, head, body,String.class,
                new NetClient.Listener<String>() {
                    @Override
                    public void onPrepare() {
                        switch (step) {
                            case 1:
                                countDownTimer1.start();
                                break;
                            case 2:
                                countDownTimer2.start();
                                break;
                        }
                    }

                    @Override
                    public void onSuccess(ResultModel<String> result) {
                        if(result.isSuccess()){
                            showToast("已成功发送短信");
                        }else{
                            showToast(result.getToast());
                            onFaile(null);
                        }
                    }

                    @Override
                    public void onFaile(Throwable t) {
                        switch (step) {
                            case 1:
                                countDownTimer1.clear();
                                break;
                            case 2:
                                countDownTimer2.clear();
                                break;
                        }
                    }
                });

        
    }

    

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                switch (step) {
                    case 0:
                        finish();
                        break;
                    case 1:
                        fromx = ScreenUtil.getScreenWidth() * 1 / 6 - ivPp.getWidth()
                                / 2;
                        tox = ScreenUtil.getScreenWidth() * 3 / 6 - ivPp.getWidth() / 2;
                        startAnimationBack();
                        break;
                    case 2:
                        fromx = ScreenUtil.getScreenWidth() * 3 / 6 - ivPp.getWidth()
                                / 2;
                        tox = ScreenUtil.getScreenWidth() * 5 / 6 - ivPp.getWidth() / 2;
                        startAnimationBack();
                        break;
                }
                break;
        }
        return true;
    }
}

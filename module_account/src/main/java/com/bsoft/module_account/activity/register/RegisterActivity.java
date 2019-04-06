

package com.bsoft.module_account.activity.register;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bsoft.baselib.base.BaseActivity;
import com.bsoft.baselib.util.StringUtil;
import com.bsoft.baselib.widget.BsoftActionBar;
import com.bsoft.commonlib.RouterPath;

import com.bsoft.commonlib.util.CommonUtil;
import com.bsoft.commonlib.util.ScaleAnimation;
import com.bsoft.commonlib.util.TPreferences;
import com.bsoft.commonlib.widget.AdjustSizeLinearLayout;
import com.bsoft.commonlib.widget.CountDownHelper;
import com.bsoft.commonlib.widget.FilterEmoji;
import com.bsoft.module_account.BuildConfig;
import com.bsoft.module_account.R;


/**
 * @author Tank E-mail:zkljxq@126.com
 * @类说明
 */
@Route(path = RouterPath.REGISTER)
public class RegisterActivity extends BaseActivity {

//    String checkCodeVo;


    EditText user;

    ImageView userclear;

    EditText etPwd;

    ImageView pwdclear;

    EditText checkcard;

    TextView tvCheckcard;

    EditText etWelcome;

    ImageView ivScan;

    TextView tvReg;

    TextView protocol;

    LinearLayout mainView;
    

    CheckBox cb_ifcansee;

    CountDownHelper countDownHelper;

    BsoftActionBar actionBar;

    ImageView iv_logo;

    AdjustSizeLinearLayout asll_login;

    ScaleAnimation scaleAnimation;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       // StatusUtil.setColor(baseActivity,getResources().getColor(R.color.theme_bule));
        user = (EditText) findViewById(R.id.et_user);
        user.addTextChangedListener(new FilterEmoji(user, baseContext));
        userclear = (ImageView) findViewById(R.id.iv_userclear);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etPwd.addTextChangedListener(new FilterEmoji(etPwd, baseContext));
        pwdclear = (ImageView) findViewById(R.id.iv_pwdclear);
        checkcard = (EditText) findViewById(R.id.et_checkcard);
        checkcard.addTextChangedListener(new FilterEmoji(checkcard, baseContext));
        tvCheckcard = (TextView) findViewById(R.id.tv_checkcard);
        etWelcome = (EditText) findViewById(R.id.et_welcome);
        etWelcome.addTextChangedListener(new FilterEmoji(etWelcome, baseContext));
        ivScan = (ImageView) findViewById(R.id.iv_scan);
        tvReg = (TextView) findViewById(R.id.tv_reg);
        protocol = (TextView) findViewById(R.id.protocol);
        mainView = (LinearLayout) findViewById(R.id.mainView);
        cb_ifcansee = (CheckBox) findViewById(R.id.cb_ifcansee);
        actionBar = (BsoftActionBar) findViewById(R.id.actionbar);
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        asll_login = (AdjustSizeLinearLayout) findViewById(R.id.asll_login);

        findView();
        setClick();
    }
    @Override
    public void findView() {
        actionBar.setBackGround(ContextCompat.getColor(baseContext, R.color.transparent));
        actionBar.setBackAction(new BsoftActionBar.Action() {
            @Override
            public void performAction(View view) {
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

        countDownHelper = new CountDownHelper(tvCheckcard, 60,
                "获取验证码", "重新获取",
                ContextCompat.getColor(baseActivity, R.color.white),
                ContextCompat.getColor(baseActivity, R.color.white),
                R.drawable.small_round_rect_orange,
                R.drawable.gray_small_round_rect_n);
    }
    void setClick() {
//        //监听键盘弹出/隐藏
//        mainView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Rect rect = new Rect();
//                //getWindowVisibleDisplayFrame 获取当前窗口可视区域大小
//                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
//                int screenHeight = getWindow().getDecorView().getHeight();
//                //键盘弹出时，可视区域大小改变，屏幕高度 - 窗口可视区域高度 = 键盘弹出高度
//                int softHeight = screenHeight - rect.bottom;
//                /**
//                 * 上移的距离 = 键盘的高度 - 按钮距离屏幕底部的高度(如果手机高度很大，上移的距离会是负数，界面将不会上移)
//                 * 按钮距离屏幕底部的高度是用屏幕高度 - 按钮底部距离父布局顶部的高度
//                 * 注意这里 btn.getBottom() 是按钮底部距离父布局顶部的高度，这里也就是距离最外层布局顶部高度
//                 */
//                int scrollDistance = softHeight - (screenHeight - tvReg.getBottom());
//                if (scrollDistance > 0) {
//                    //具体移动距离可自行调整
//                    mainView.scrollTo(0, scrollDistance + 60);
//                } else {
//                    //键盘隐藏，页面复位
//                    mainView.scrollTo(0, 0);
//                }
//            }
//        });

        iv_logo.post(new Runnable() {
            @Override
            public void run() {
                scaleAnimation = new ScaleAnimation(2, iv_logo.getHeight(), iv_logo.getWidth(), new AccelerateInterpolator(), 250);
            }

        });
        asll_login.setSoftKeyBoardListener(new AdjustSizeLinearLayout.SoftkeyBoardListener() {
            @Override
            public void keyBoardVisable(int move) {
                scaleLogo();
            }

            @Override
            public void keyBoardInvisable(int move) {
                scaleLogo();
            }
        });
        cb_ifcansee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb_ifcansee.isChecked()) {

                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {

                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                etPwd.setSelection(etPwd.getText().length());
            }
        });


        tvCheckcard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isEmpty(user.getText().toString().trim())) {
                    user.requestFocus();
                    showToast("电话号码不能为空，请输入");
                } else if (!StringUtil.isMobilPhoneNumber(user.getText()
                        .toString().trim())) {
                    user.requestFocus();
                    showToast("电话号码不符合，请重新输入");
                } else {

                  //  getCheckCode();
                }
            }
        });
        protocol.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
//                Intent intent = new Intent(RegisterActivity.this,
//                        WebActivity.class);
//                intent.putExtra("url",
//                        Constants.HttpApiUrl + BuildConfig.regProtocol);
//                intent.putExtra("title", "注册协议");
//                startActivity(intent);
            }
        });
        user.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (user.getText().toString().length() == 0) {
                    userclear.setVisibility(View.GONE);
                } else {
                    userclear.setVisibility(View.VISIBLE);
                }
            }
        });
        userclear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                user.requestFocus();
                user.setText("");
            }
        });
//        etPwd.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                if (etPwd.getText().toString().length() == 0) {
//                    pwdclear.setVisibility(View.GONE);
//                } else {
//                    pwdclear.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//        pwdclear.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                
//                etPwd.setText("");
//            }
//        });
        findViewById(R.id.tv_reg).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (StringUtil.isEmpty(user.getText().toString().trim())
                        && TextUtils.equals(etPwd.getText().toString(), "*#*#debug#*#*")) {
                    TPreferences tPreferences = TPreferences.getInstance();
                    tPreferences.setBooleanData(TPreferences.KEY_DEBUG, !BuildConfig.DEBUG);
                    showToast(!BuildConfig.DEBUG ? "已切换至debug模式" : "已切换至release模式");
                    handler.removeCallbacks(exitRunnable);
                    handler.postDelayed(exitRunnable, 100);
                } else if (StringUtil.isEmpty(user.getText().toString().trim())) {
                    user.requestFocus();
                    showToast("电话号码不能为空，请输入");
                } else if (!StringUtil.isMobilPhoneNumber(user.getText()
                        .toString().trim())) {
                    user.requestFocus();
                    showToast("电话号码不符合，请重新输入");
                } else if (StringUtil.isEmpty(etPwd.getText().toString())) {
                    etPwd.requestFocus();
                    showToast("密码不能为空，请输入");
                } else if (!CommonUtil.validatePwd(etPwd.getText().toString())) {
                    etPwd.requestFocus();
                    showToast(R.string.pwd_toast);
                } else if (StringUtil.isEmpty(checkcard.getText().toString())) {
                    checkcard.requestFocus();
                    showToast("验证码不能为空，请输入");
                } else {
                    //registerCall();
                }
            }
        });
    }

    private void scaleLogo(){
        if(scaleAnimation!=null){
            scaleAnimation.startAnimation(iv_logo);
        }
    }

    private Runnable exitRunnable = new Runnable() {
        @Override
        public void run() {
            System.exit(0);//重启应用
        }
    };
    

    /**
     * 获取验证码
     */
//    private void getCheckCode() {
//        ArrayMap<String, String> head = new ArrayMap<>();
//        head.put(ConstantsHttp.Head_Id, ConstantsHttp.Register_Service);
//        head.put(ConstantsHttp.Head_Method, "registerSmsCode");
//
//        ArrayList body = new ArrayList();
//        body.add(Constants.Product_Name);
//        body.add(user.getText().toString().trim());
//
//        NetClient.post(baseActivity, ConstantsHttp.Json_Request, head, body, String.class,
//                new NetClient.Listener<String>() {
//                    @Override
//                    public void onPrepare() {
//
//                        countDownHelper.start();
//                    }
//
//                    @Override
//                    public void onSuccess(ResultModel<String> result) {
//                        if (result.isSuccess()) {
//                            showToast("已成功发送短信");
//                        } else {
//                            if(result.getCode() == 670){
//                                showDialog("提示", result.getToast(),"验证", "取消", new OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        Intent intent = new Intent(baseContext, AccountValidateActivity.class);
//                                        intent.putExtra("phoneNo", user.getText().toString().trim());
//                                        startActivity(intent);
//                                    }
//                                }, null);
//                            }else if(result.getCode() == 603){
//                                showDialog("提示", result.getToast(),"登录", "取消", new OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        sendBroadcast(new Intent(Constants.CLOSE_ACTION));
//                                        Intent intent = new Intent(baseContext, LoginActivity.class);
//                                        intent.putExtra("phoneNo", user.getText().toString().trim());
//                                        startActivity(intent);
//                                    }
//                                }, null);
//                            }
//
//                            showToast(result.getToast());
//                            onFaile(null);
//                        }
//                    }
//
//                    @Override
//                    public void onFaile(Throwable t) {
//                        countDownHelper.clear();
//                    }
//                });
//
//
//    }


//    private void registerCall() {
//
//        ArrayMap<String, String> head = new ArrayMap<>();
//        head.put(ConstantsHttp.Head_Id, ConstantsHttp.Register_Service);
//        head.put(ConstantsHttp.Head_Method, "register");
//
//        ArrayList body = new ArrayList();
//        body.add(Constants.Product_Name);
//        body.add(user.getText().toString());
//        body.add(MD5.getMD5(etPwd.getText().toString()));
//        body.add(checkcard.getText().toString());
//
//        NetClient.post(baseActivity, ConstantsHttp.Json_Request, head, body, String.class,
//                new NetClient.Listener<String>() {
//                    @Override
//                    public void onPrepare() {
//                        showLoadingDialog("注册中...");
//                    }
//
//                    @Override
//                    public void onSuccess(ResultModel<String> result) {
//                        dismissLoadingDialog();
//                        if (result.isSuccess()) {
//                            application.setUserPhone(user.getText().toString());
//                            showToast("注册成功");
//                            Intent intent = new Intent(baseContext, AuthDocActivity.class);
//                            intent.putExtra("userId", result.data);
//                            intent.putExtra("userName", user.getText().toString());
//                            intent.putExtra("pwd", etPwd.getText().toString());
//                            startActivity(intent);
//                            finish();
//
//                        } else {
//                            showToast(result.getToast());
//                            onFaile(null);
//                        }
//                    }
//
//                    @Override
//                    public void onFaile(Throwable t) {
//                        dismissLoadingDialog();
//                    }
//                });
//
//
//    }


    @Override
    public void onDestroy() {
        
        countDownHelper.clear();
        super.onDestroy();
    }
}

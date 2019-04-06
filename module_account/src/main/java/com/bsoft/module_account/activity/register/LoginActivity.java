package com.bsoft.module_account.activity.register;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.ArrayMap;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONObject;

import com.bsoft.baselib.base.BaseActivity;
import com.bsoft.baselib.util.EffectUtil;
import com.bsoft.baselib.util.StringUtil;
import com.bsoft.baselib.widget.BsoftActionBar;
import com.bsoft.commonlib.Constants;
import com.bsoft.commonlib.RouterPath;
import com.bsoft.commonlib.util.MD5;
import com.bsoft.commonlib.util.RxBd;
import com.bsoft.commonlib.util.ScaleAnimation;
import com.bsoft.commonlib.widget.AdjustSizeLinearLayout;
import com.bsoft.commonlib.widget.FilterEmoji;
import com.bsoft.module_account.BuildConfig;
import com.bsoft.module_account.R;
import com.bsoft.module_account.activity.account.SettingPhoneActivity;
import com.bsoft.module_account.activity.changenet.ChangeNetActivity;
import com.bsoft.module_account.bean.LoginUser;
import com.bsoft.module_account.service.LoginUserService;
import com.bsoft.network_api.bean.ResultModel;
import com.bsoft.network_api.net.NetClient;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * @author Tank E-mail:zkljxq@126.com
 * @类说明 登陆
 */
@Route(path = RouterPath.LOGIN)
public class LoginActivity extends BaseActivity {


    String digitsTxt;
    String digitsNumber;


    BsoftActionBar actionBar;

    ImageView iv_logo;

    ImageView ivUserclear;

    EditText etUser;

    CheckBox cb_ifcansee;

    RelativeLayout rl_secret_text;

    EditText etPwd;

    TextView tvLogin;

    TextView tvPassword;

    AdjustSizeLinearLayout asll_login;

    LinearLayout mainView;

    private ScaleAnimation scaleAnimation;

    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
      //  StatusUtil.setColor(baseActivity,getResources().getColor(R.color.theme_bule));
        actionBar = (BsoftActionBar) findViewById(R.id.actionbar);
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        ivUserclear = (ImageView) findViewById(R.id.iv_userclear);
        etUser = (EditText) findViewById(R.id.et_user);
        etUser.addTextChangedListener(new FilterEmoji(etUser, baseContext));
        cb_ifcansee = (CheckBox) findViewById(R.id.cb_ifcansee);
        rl_secret_text = (RelativeLayout) findViewById(R.id.rl_secret_text);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etPwd.addTextChangedListener(new FilterEmoji(etPwd, baseContext));
        tvLogin = (TextView) findViewById(R.id.tv_login);
        tvPassword = (TextView) findViewById(R.id.tv_password);
        asll_login = (AdjustSizeLinearLayout) findViewById(R.id.asll_login);
        mainView = (LinearLayout) findViewById(R.id.mainView);
        digitsTxt = getString(R.string.digits_idcard);
        digitsNumber = getString(R.string.digits_number);
        findView();
        setClick();
//        doLoginNext();
        updateVersion();

    }

    private void updateVersion() {

//        CheckVersionCall call = new CheckVersionCall(baseContext, application.getStoreDir());
//        call.enqueue(false, true);
    }





    @Override
    public void findView() {
        actionBar.setBackGround(ContextCompat.getColor(baseContext, R.color.transparent));
        //add change_net chenkai 20170904 start
        if (BuildConfig.DEBUG) {
            actionBar.addAction(new BsoftActionBar.Action() {
                @Override
                public int getDrawable() {
                    return 0;
                }

                @Override
                public String getText() {
                    return getString(R.string.change_api_url);
                }

                @Override
                public void performAction(View view) {
                    hideKeyboard();
                    Intent intent = new Intent(LoginActivity.this,
                            ChangeNetActivity.class);
                    startActivity(intent);
                }
            });
        }
        actionBar.addAction(new BsoftActionBar.Action() {
            @Override
            public int getDrawable() {
                return 0;
            }

            @Override
            public String getText() {
                return getString(R.string.register);
            }

            @Override
            public void performAction(View view) {
                hideKeyboard();
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
            }
        });
        etUser.setKeyListener(DigitsKeyListener.getInstance(digitsNumber));


        iv_logo.post(new Runnable() {
            @Override
            public void run() {
                scaleAnimation = new ScaleAnimation(2, iv_logo.getHeight(), iv_logo.getWidth(), new AccelerateInterpolator(), 250);
            }

        });


    }

    void setClick() {

        EffectUtil.addClickEffect(tvPassword);
        tvPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

//                Intent intent = new Intent(LoginActivity.this,
//                        ImgForFrescoActivity.class);
//                ArrayList<String> arr = new ArrayList();
//                arr.add("http://4493bz.1985t.com/uploads/allimg/150127/4-15012G52133.jpg");
//                arr.add("http://pic41.nipic.com/20140509/18696269_121755386187_2.png");
//                intent.putExtra("imgList", arr);
//                Intent intent = new Intent(LoginActivity.this,
//                        MemberJoinActivity.class);

                Intent intent = new Intent(LoginActivity.this,
                        SettingPhoneActivity.class);
                startActivity(intent);
            }
        });
        etUser.addTextChangedListener(new TextWatcher() {
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
                if (etUser.getText().toString().length() == 0) {
                    ivUserclear.setVisibility(View.GONE);
                } else {
                    ivUserclear.setVisibility(View.VISIBLE);
                }
            }
        });
        ivUserclear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etUser.setText("");
                etUser.requestFocus();
                showKeyboard(etUser);
            }
        });

        etPwd.addTextChangedListener(new TextWatcher() {
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
            }
        });

        cb_ifcansee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb_ifcansee.isChecked()) {
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {

                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                etPwd.requestFocus();
                etPwd.setSelection(etPwd.getText().length());

            }
        });


      //第一种

        RxBd.click(tvLogin).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                if (StringUtil.isEmpty(etUser.getText().toString().trim())) {
                    etUser.requestFocus();
                    showToast(R.string.user_name_null);
                } else if (StringUtil.isEmpty(etPwd.getText().toString())) {
                    etPwd.requestFocus();
                    showToast(R.string.pwd_null);
                } else if (!StringUtil.isMobilPhoneNumber(etUser.getText().toString().trim())) {
                    etUser.requestFocus();
                    showToast("手机号码不合法");
                } else {
                    hideKeyboard();
                   // taskGetRoleList(etUser.getText().toString(), etPwd.getText().toString());
                   // login(etUser.getText().toString(), etPwd.getText().toString());
                    login("15195958515","djtc3344");
                }
            }
        });


        mainView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (null != getCurrentFocus()
                        && null != getCurrentFocus().getWindowToken()) {
                    hideKeyboard();
                }
                return false;
            }
        });
        asll_login.setSoftKeyBoardListener(new AdjustSizeLinearLayout.SoftkeyBoardListener() {
            @Override
            public void keyBoardVisable(int move) {

                if (scaleAnimation != null) {
                    scaleAnimation.startAnimationIn(iv_logo);
                }

            }

            @Override
            public void keyBoardInvisable(int move) {
                if (scaleAnimation != null) {
                    scaleAnimation.startAnimationOut(iv_logo);
                }
            }
        });

    }

//    private void taskGetRoleList(final String userName, final String pwd) {
//        ArrayMap<String, String> head = new ArrayMap<>();
//        head.put(ConstantsHttp.Head_Id, ConstantsHttp.Register_Service);
//        head.put(ConstantsHttp.Head_Method, "findDoctorRoleAndState");
//        ArrayList body = new ArrayList();
//        body.add(Constants.Product_Name);
//        body.add(userName);
//        body.add(MD5.getMD5(pwd));
//
//        NetClient.post(baseActivity, ConstantsHttp.Json_Request, head, body, AuthLoginRoleVo.class,
//                new NetClient.Listener<AuthLoginRoleVo>() {
//                    @Override
//                    public void onPrepare() {
//                        showLoadingDialog();
//                    }
//
//                    @Override
//                    public void onSuccess(ResultModel<AuthLoginRoleVo> result) {
//
//                        if (result.isSuccess()) {
//                            if (!result.isEmpty()) {
//                                if (result.data.isAuthSuccess()) {
//                                    if (result.data.roles != null && result.data.roles.size() > 1) {
//                                        dismissLoadingDialog();
//                                        showRoleDialog(result.data.roles);
//                                    } else {
//                                        login(userName, pwd, result.data.roles.get(0));
//                                    }
//                                } else {
//                                    dismissLoadingDialog();
//                                    if (result.data.doctor != null) {
//                                        Intent intent = new Intent(baseContext, AuthStateActivity.class);
//                                        intent.putExtra("auth", result.data.doctor);
//                                        intent.putExtra("userId", result.data.userId);
//                                        intent.putExtra("userName", userName);
//                                        intent.putExtra("pwd", pwd);
//                                        startActivity(intent);
//                                    } else {
//                                        Intent intent = new Intent(baseContext, AuthDocActivity.class);
//                                        intent.putExtra("userId", result.data.userId);
//                                        intent.putExtra("userName", userName);
//                                        intent.putExtra("pwd", pwd);
//                                        startActivity(intent);
//                                    }
//                                }
//                            } else {
//                                showToast("登录失败");
//                                dismissLoadingDialog();
//                            }
//                        } else {
//                            dismissLoadingDialog();
//                            showToast(result.getToast());
//                        }
//                    }
//
//                    @Override
//                    public void onFaile(Throwable t) {
//                        dismissLoadingDialog();
//                    }
//                });
//    }

//    private void showRoleDialog(ArrayList<UserRole> roleList) {
//        RecyclerView recyclerView = null;
//        dialog = new Dialog(baseContext, R.style.alertDialogTheme);
//        View view = getLayoutInflater().inflate(R.layout.dialog_role_select, null);
//        ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
//        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
//        RecyclerViewUtil.initLinearV(baseContext, recyclerView, R.color.divider2bg, R.dimen.dp0_6);
//        EffectUtil.addClickEffect(ivClose);
//        ivClose.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(AppApplication.getWidthPixels() * 80 / 100,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.setContentView(view, layoutParams);
//        dialog.setCancelable(false);
//        RoleAdapter adapter = new RoleAdapter(roleList);
//        adapter.setOnItemClickListener(adapterListener);
//        recyclerView.setAdapter(adapter);
//        dialog.show();
//
//    }

//    MultiItemTypeAdapter.OnItemClickListener adapterListener
//            = new MultiItemTypeAdapter.OnItemClickListener<UserRole>() {
//        @Override
//        public void onItemClick(ViewGroup parent, View view, ViewHolder holder, List<UserRole> datas, int position) {
//            login(etUser.getText().toString(), etPwd.getText().toString(), datas.get(position));
//            dialog.dismiss();
//
//
//        }
//
//        @Override
//        public void onItemViewClick(View view, ViewHolder holder, UserRole item, int position, int tPos) {
//        }
//
//        @Override
//        public boolean onItemLongClick(ViewGroup parent, View view, ViewHolder holder, List<UserRole> datas, int position) {
//            return false;
//        }
//    };

    @Override
    protected void onResume() {
        super.onResume();
        etUser.postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (!TextUtils.isEmpty(application.getUserPhone())
//                        && TextUtils.isEmpty(etUser.getText())) {
//                    etUser.setText(application.getUserPhone());
//                    etPwd.requestFocus();
//                    etPwd.requestFocus();
//                    showKeyboard(etPwd);
//                }
            }
        }, 500);
    }

    private void login(final String userName, String pwd) {


        ArrayMap<String, Object> body = new ArrayMap<>();
        body.put("tenantId", Constants.TENANT_ID);
        body.put("loginName", userName);
        body.put("rid", "patient");
        body.put("pwd", MD5.getMD5(pwd));
        body.put("forAccessToken", true);
        NetClient.post(baseActivity, "login", null, body, LoginUser.class,
                new NetClient.Listener<LoginUser>() {
                    @Override
                    public void onPrepare() {
                        showLoadingDialog();
                    }

                    @Override
                    public void onSuccess(ResultModel<LoginUser> result) {
                        if (result.isSuccess()) {

                            if (!result.isEmpty() && !TextUtils.isEmpty(result.pro)) {

                                ((LoginUserService) ARouter.getInstance().build("/service/login/user").navigation())
                                        .setUserPhone(userName);
                                ((LoginUserService) ARouter.getInstance().build("/service/login/user").navigation())
                                        .setAccessToken(JSONObject.parseObject(result.pro).getString("accessToken"));
                                ((LoginUserService) ARouter.getInstance().build("/service/login/user").navigation())
                                        .setLoginUser(result.data);
                                ((LoginUserService) ARouter.getInstance().build("/service/login/user").navigation())
                                        .setLoginState(true);

                                ARouter.getInstance().build(RouterPath.MAINTAB).withBoolean("isLoad", false)
                                        .navigation();
                                finish();
////                                getAppInfoByDevice();
                              //  doLoginNext();
                                dismissLoadingDialog();
                            } else {
                                showToast("登录失败");
                                dismissLoadingDialog();
                            }
                        } else {
                            if (result.getCode() == 501) {
                                showToast("用户名或密码错误");
                            } else if (result.getCode() == 403) {
                                showToast("用户名已被禁用");
                            } else if (result.getCode() == 404) {
                                showToast("用户名不存在");
                            } else {
                                showToast(result.getToast());
                            }
                            dismissLoadingDialog();
                        }
                    }

                    @Override
                    public void onFaile(Throwable t) {
                        dismissLoadingDialog();
                    }
                });

    }
//
//    @Deprecated
//    private void getAppInfoByDevice() {
//        ArrayMap<String, String> head = new ArrayMap<>();
//        head.put(ConstantsHttp.Head_Id, ConstantsHttp.Device_Service);
//        head.put(ConstantsHttp.Head_Method, "getAppInfoByDevice");
//        head.put(ConstantsHttp.Head_Token, application.getAccessToken());
//
//        ArrayList body = new ArrayList();
//        NetClient.post(baseActivity, ConstantsHttp.Json_Request, head, body, DeviceVo.class,
//                new NetClient.Listener<DeviceVo>() {
//                    @Override
//                    public void onPrepare() {
//                        showLoadingDialog();
//                    }
//
//                    @Override
//                    public void onSuccess(ResultModel<DeviceVo> result) {
//                        if (result.isSuccess()) {
//                            if (!result.isEmpty()) {
////                                application.setUserInfo(result.data.user);
//                                PropertiesVo propertiesVo = JSONObject.parseObject(result.pro, PropertiesVo.class);
//
//                            } else {
//                                showToast("登录失败");
//                                dismissLoadingDialog();
//                            }
//                        } else {
//                            showToast(result.getToast());
//                            dismissLoadingDialog();
//                        }
//                    }
//
//                    @Override
//                    public void onFaile(Throwable t) {
//                        dismissLoadingDialog();
//                    }
//                });
//    }

//    private void doLoginNext() {
//        Flowable.merge(/*getModule(),getIndex(), getBanner()*/getIndex())
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Consumer<Subscription>() {//subscribe()调用后而且在事件发送前执行(默认情况下subcribe发生的线程决定，可以通过最近的跟在后面的subscribeOn指定)
//                    @Override
//                    public void accept(@NonNull Subscription subscription) throws Exception {
//
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())//指定doOnSubscribe的线程
//                .compose(RxLifecycle.bindUntilEvent(this.lifecycle(), ActivityEvent.DESTROY))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new FlowableSubscriber<Object>() {
//                    @Override
//                    public void onSubscribe(Subscription s) {
//                        s.request(Long.MAX_VALUE);
//                    }
//
//                    @Override
//                    public void onNext(Object o) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        if (t instanceof SocketTimeoutException) {
//                            showToast("请求超时");
//                        } else {
//                            showToast(t.getMessage());
//                        }
//                        t.printStackTrace();
//                        dismissLoadingDialog();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        dismissLoadingDialog();
//                        application.setLoginState(true);
////                        Intent intent = new Intent(LoginActivity.this,
////                                MainTabActivity.class);
////                        intent.putExtra("isLoad", false);
////                        startActivity(intent);
////                        finish();
//                        ARouter.getInstance().build(ConstantUrl.MAINTAB).withBoolean("isLoad", false)
//                                .navigation();
//                    }
//                });
//    }

//
//
//    //获取模块
//    private Flowable<ResultModel> getModule() {
//        ArrayMap<String, String> head = new ArrayMap<>();
//        head.put(ConstantsHttp.Head_Id, ConstantsHttp.Product_Service);
//        head.put(ConstantsHttp.Head_Method, "menuList");
//        head.put(ConstantsHttp.Head_Token, application.getAccessToken());
//
//        ArrayList body = new ArrayList();
//        body.add(Constants.Product_Name);
//
//        return RetrofitClient.getInstance().post2(ConstantsHttp.Json_Request, head, body)
//                .map(new Function<String, ResultModel>() {
//                    @Override
//                    public ResultModel apply(@NonNull String s) throws Exception {
//                        ResultModel<ArrayList<ModuleVo>> resultModel = Parser.getInstance().parserData(s, ModuleVo.class);
//                        if (resultModel.isSuccess() && !resultModel.isEmpty()) {
//                            application.setModuleList(resultModel.data);
//                        } else {
//                            throw new Exception(resultModel.getToast());
//                        }
//                        return resultModel;
//                    }
//                });
//    }
//
//    //获取设备信息
//    private Flowable<ResultModel> getDeviceInfo() {
//        ArrayMap<String, String> head = new ArrayMap<>();
//        head.put(ConstantsHttp.Head_Id, ConstantsHttp.Device_Service);
//        head.put(ConstantsHttp.Head_Method, "getAppInfoByDevice");
//        head.put(ConstantsHttp.Head_Token, application.getAccessToken());
//
//        ArrayList body = new ArrayList();
//
//        return RetrofitClient.getInstance().post2(ConstantsHttp.Json_Request, head, body)
//                .map(new Function<String, ResultModel>() {
//                    @Override
//                    public ResultModel apply(@NonNull String s) throws Exception {
//                        ResultModel<DeviceVo> resultModel = Parser.getInstance().parserData(s, DeviceVo.class);
//                        if (resultModel.isSuccess() && !resultModel.isEmpty()) {
////                            application.setDevice(resultModel.data);
//                        } else {
//                            throw new Exception(resultModel.getToast());
//                        }
//                        return resultModel;
//                    }
//                });
//    }
//
//    //获取首页
//    private Flowable<ResultModel> getIndex() {
//        ArrayMap<String, String> head = new ArrayMap<>();
//        head.put(ConstantsHttp.Head_Id, ConstantsHttp.App_Service);
//        head.put(ConstantsHttp.Head_Method, "doctIndex");
//        head.put(ConstantsHttp.Head_Token, application.getAccessToken());
//
//        ArrayList body = new ArrayList();
//
//        return RetrofitClient.getInstance().post2(ConstantsHttp.Json_Request, head, body)
//                .map(new Function<String, ResultModel>() {
//                    @Override
//                    public ResultModel apply(@NonNull String s) throws Exception {
//                        ResultModel<IndexVo> resultModel = Parser.getInstance().parserData(s, IndexVo.class);
//                        if (resultModel.isSuccess() && !resultModel.isEmpty()) {
//                            if (resultModel.data.teamList != null && resultModel.data.teamList.size() > 0) {
//                                application.setIndexInfo(resultModel.data);
//                            } else {
//                                Intent intent = new Intent(LoginActivity.this, NoTeamActivity.class);
//                                startActivity(intent);
//                                throw new Exception(resultModel.getToast());
//                            }
//                        } else {
//                            throw new Exception(resultModel.getToast());
//                        }
//                        return resultModel;
//                    }
//                });
//    }
//
//    //获取广告条
//    private Flowable<ResultModel> getBanner() {
//        ArrayMap<String, String> head = new ArrayMap<>();
//        head.put(ConstantsHttp.Head_Id, ConstantsHttp.Product_Service);
//        head.put(ConstantsHttp.Head_Method, "listProductbanner");
//        head.put(ConstantsHttp.Head_Token, application.getAccessToken());
//
//        ArrayList body = new ArrayList();
//        body.add(Constants.Product_Name);
//        return RetrofitClient.getInstance().post2(ConstantsHttp.Json_Request, head, body)
//                .map(new Function<String, ResultModel>() {
//                    @Override
//                    public ResultModel apply(@NonNull String s) throws Exception {
//                        ResultModel<ArrayList<BannerVo>> resultModel = Parser.getInstance().parserData(s, BannerVo.class);
//                        if (resultModel.isSuccess() && !resultModel.isEmpty()) {
//                            application.setBannerList(resultModel.data);
//                        } else {
//                            //throw new Exception(resultModel.getToast());
//                        }
//                        return resultModel;
//                    }
//                });
//    }
//
//    //获取module配置
//    @Deprecated
//    private void getModuleInfo() {
//        ArrayMap<String, String> head = new ArrayMap<>();
//        head.put(ConstantsHttp.Head_Id, ConstantsHttp.Product_Service);
//        head.put(ConstantsHttp.Head_Method, "listMenus");
//        head.put(ConstantsHttp.Head_Token, application.getAccessToken());
//
//        ArrayList body = new ArrayList();
//        body.add(Constants.Product_Name);
//
//        NetClient.post(baseActivity, ConstantsHttp.Json_Request, head, body, ModuleVo.class,
//                new NetClient.Listener<ArrayList<ModuleVo>>() {
//                    @Override
//                    public void onPrepare() {
//                        showLoadingDialog();
//                    }
//
//                    @Override
//                    public void onSuccess(ResultModel<ArrayList<ModuleVo>> result) {
//                        if (result.isSuccess()) {
//                            if (!result.isEmpty()) {
//                                application.setModuleList(result.data);
//                            } else {
//                                showToast("登录失败");
//                                dismissLoadingDialog();
//                            }
//                        } else {
//                            showToast(result.getToast());
//                            dismissLoadingDialog();
//                        }
//                    }
//
//                    @Override
//                    public void onFaile(Throwable t) {
//                        dismissLoadingDialog();
//                    }
//                });
//    }


    @Override
    public void onDestroy() {


        super.onDestroy();
    }
}

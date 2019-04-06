package com.bsoft.module_account.activity.register;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import com.bsoft.baselib.mvpbase.BaseMvpActivity;
import com.bsoft.baselib.util.StringUtil;
import com.bsoft.baselib.widget.BsoftActionBar;
import com.bsoft.commonlib.RouterPath;
import com.bsoft.commonlib.util.RxBd;
import com.bsoft.commonlib.util.ScaleAnimation;
import com.bsoft.commonlib.widget.AdjustSizeLinearLayout;
import com.bsoft.commonlib.widget.FilterEmoji;
import com.bsoft.module_account.R;
import com.bsoft.module_account.bean.LoginUser;
import com.bsoft.module_account.presenter.LoginRegistPresenter;
import com.bsoft.module_account.view.LoginRegistView;


import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

//@Route(path = ConstantUrl.LOGIN1)
public class MvpLoginActivity extends BaseMvpActivity<LoginRegistView, LoginRegistPresenter>
        implements LoginRegistView {

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
       // updateVersion();

    }

    @Override
    protected LoginRegistPresenter createPresenter() {
        return new LoginRegistPresenter();
    }

    private void setClick() {
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
                   // login("15195958515","djtc3344");
                  //  mPresenter.toLogin(baseActivity,etUser.getText().toString(),etPwd.getText().toString());
                    mPresenter.toLogin(MvpLoginActivity.this,"15195958515","djtc3344");
                }
            }
        });




 //       if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())){
//            T.showShort(LoginActivity.this,"用户名和密码不能为空");
//        }else if (etName.getText().toString().length() < 6 || etName.getText().toString().length() <6){
//            T.showShort(LoginActivity.this,"用户名和密码长度不能小于6位");
//        }else{
//            mPresenter.toLogin(etName.getText().toString(),etPassword.getText().toString());
//        }
    }

    private void updateVersion() {

//        CheckVersionCall call = new CheckVersionCall(baseContext, application.getStoreDir());
//        call.enqueue(false, true);
    }

    @Override
    public void findView() {

    }

    @Override
    public void loginSuccess(LoginUser user) {
        if(user!=null) {
            ARouter.getInstance().build(RouterPath.MAINTAB).withBoolean("isLoad", false)
                    .navigation();
            finish();
        }
    }

    @Override
    public void registerSuccess(LoginUser user) {

    }

    @Override
    public void loginFail() {
        showToast("登录失败");
    }

    @Override
    public void registerFail() {

    }


}

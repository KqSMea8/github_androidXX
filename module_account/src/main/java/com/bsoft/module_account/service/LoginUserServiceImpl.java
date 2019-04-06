package com.bsoft.module_account.service;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.alibaba.fastjson.JSON;
import com.bsoft.baselib.util.StringUtil;
import com.bsoft.commonlib.Constants;
import com.bsoft.commonlib.util.TPreferences;
import com.bsoft.module_account.bean.LoginUser;


/**
 * TODO feature
 *
 * @author Alex <a href="mailto:zhilong.lzl@alibaba-inc.com">Contact me.</a>
 * @version 1.0
 * @since 2017/1/3 10:26
 */
@Route(path = "/service/login/user")
public class LoginUserServiceImpl implements LoginUserService {
    Context mContext;
    LoginUser loginUser;
    String accessToken;
    String userPhone;
    /**
     * Do your init work in this method, it well be call when processor has been load.
     *
     * @param context ctx
     */
    @Override
    public void init(Context context) {
        mContext = context;
        Log.e("testService", LoginUserService.class.getName() + " has init.");
    }

    @Override
    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
        if (null != loginUser) {
            TPreferences.getInstance().setStringData(Constants.Login_User, JSON.toJSONString(loginUser));
        } else {
            TPreferences.getInstance().setStringData(Constants.Login_User, null);
        }
    }

    @Override
    public LoginUser getLoginUser() {
        if (null != loginUser) {
            return loginUser;
        }
        String json = TPreferences.getInstance().getStringData(Constants.Login_User);
        if (StringUtil.isEmpty(json)) {
            return null;
        }
        loginUser = JSON.parseObject(json, LoginUser.class);
        return loginUser;
    }

    @Override
    public void setAccessToken(String token) {
        this.accessToken = token;
        if (null != accessToken) {
            TPreferences.getInstance().setStringData(Constants.AccessToken, token);
        } else {
            TPreferences.getInstance().setStringData(Constants.AccessToken, null);
        }
    }

    @Override
    public String getAccessToken() {
        if (null != accessToken) {
            return accessToken;
        }
        String json = TPreferences.getInstance().getStringData(Constants.AccessToken);
        if (StringUtil.isEmpty(json)) {
            return null;
        }
        accessToken = json;
        return accessToken;
    }
    @Override
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
        if (null != userPhone) {
            TPreferences.getInstance().setStringData(Constants.User_Phone, userPhone);
        } else {
            TPreferences.getInstance().setStringData(Constants.User_Phone, null);
        }
    }

    @Override
    public String getUserPhone() {
        if (null != userPhone) {
            return userPhone;
        }
        String json = TPreferences.getInstance().getStringData(Constants.User_Phone);
        if (StringUtil.isEmpty(json)) {
            return null;
        }
        userPhone = json;
        return userPhone;
    }

    @Override
    public void setLoginState(boolean flag) {
        TPreferences.getInstance().setBooleanData(Constants.Login_State, flag);
    }
    @Override
    public  boolean getLoginState() {
        return TPreferences.getInstance().getBooleanData(Constants.Login_State);
    }

    @Override
    public void clearLoginUser() {
        setLoginUser(null);
        setAccessToken(null);
        setLoginState(false);
    }
}

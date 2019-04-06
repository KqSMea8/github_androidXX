package com.bsoft.module_account.interceptor;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.commonlib.RouterPath;
import com.bsoft.module_account.service.LoginUserService;


/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2018/12/18.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
@Interceptor(priority = 3)
public class LoginInterceptor implements IInterceptor {
    private Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (RouterPath.NEED_LOGIN_FILTER_LIST.contains(postcard.getPath())) {
            //|| TextUtils.isEmpty(LocalData.getLoginUser().idcard)
            if (!((LoginUserService) ARouter.getInstance().build("/service/login/user").navigation()).getLoginState() ) {
                callback.onInterrupt(null);
                return;
            }
        }
        callback.onContinue(postcard);


    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}

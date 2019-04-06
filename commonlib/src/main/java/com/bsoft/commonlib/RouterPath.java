package com.bsoft.commonlib;

import java.util.Arrays;
import java.util.List;

public class RouterPath {

    /**
     * app模块
     */
    //AppService的路径
    public static final String APP_SERVICE = "/app/AppService";


    public static final String MAINTAB = "/main/MainTabActivity";


    //test
    public static final String SHAPE = "/test/ShapeActivity";
    /*登录*/
    public static final String LOGIN = "/account/LoginActivity";

    /*注册*/
    public static final String REGISTER = "/account/RegisterActivity";





    public static final List<String> NEED_LOGIN_FILTER_LIST = Arrays.asList(
            SHAPE

    );



}

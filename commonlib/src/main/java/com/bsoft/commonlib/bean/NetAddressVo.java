package com.bsoft.commonlib.bean;

import com.bsoft.baselib.bean.BaseVo;

/**
 * Created by 83990 on 2018/2/7.
 */


/*
 public static final String HTTP_URL = "http://218.92.200.226:13360/hcn-web/";// 正式
    public static final String HTTP_URL2 = "http://218.92.200.226:13360/pcn-core/";// 正式

    //图片地址
    public static String HttpImgUrl=HTTP_URL + "upload/";
* */
public class NetAddressVo extends BaseVo {


    private String environment;
    private String environmentText;
    private String httpPcnUrl;
    private String httpHcnUrl;
    private String httpImgUrl;
    private String httpWebUrl;


    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getEnvironmentText() {
        return environmentText;
    }

    public void setEnvironmentText(String environmentText) {
        this.environmentText = environmentText;
    }

    public String getHttpPcnUrl() {
        return httpPcnUrl;
    }

    public void setHttpPcnUrl(String httpPcnUrl) {
        this.httpPcnUrl = httpPcnUrl;
    }

    public String getHttpHcnUrl() {
        return httpHcnUrl;
    }

    public void setHttpHcnUrl(String httpHcnUrl) {
        this.httpHcnUrl = httpHcnUrl;
    }

    public String getHttpImgUrl() {
        return httpImgUrl;
    }

    public void setHttpImgUrl(String httpImgUrl) {
        this.httpImgUrl = httpImgUrl;
    }

    public String getHttpWebUrl() {
        return httpWebUrl;
    }

    public void setHttpWebUrl(String httpWebUrl) {
        this.httpWebUrl = httpWebUrl;
    }
}

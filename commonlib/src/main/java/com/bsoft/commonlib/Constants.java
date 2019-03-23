package com.bsoft.commonlib;

public class Constants {

    public static final String TENANT_ID="hcn.dongtai";
    //public static final String Product_Name = BuildConfig.ProductName;//产品编码
    public static final String productName = "hcn.dongtai.patient_android";
    public static final String Product_Name = "hcn.doctor_android";
    public static final String Json_Request = "*.jsonRequest";
    public static final String Dic_Url = "cfs.dic.";
    public static final String Head_Id = "X-Service-Id";
    public static final String Head_Token = "X-Access-Token";
    public static final String Content_Type = "Content-Type";
    public static final String Application_Json = "application/json";

    //    public static final String HTTP_URL = "http://218.92.200.226:13357/hcn-web/";// 测试
//    public static final String HTTP_URL2 = "http://218.92.200.226:13357/pcn-core/";// 测试

    public static  String HTTP_URL = "http://218.92.200.226:13360/hcn-web/";// 正式
    public static  String HTTP_URL2 = "http://218.92.200.226:13360/pcn-core/";// 正式

    //图片地址
    public static String HttpImgUrl=HTTP_URL + "upload/";
}

package com.bsoft.commonlib.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


import com.bsoft.baselib.AppContext;
import com.bsoft.commonlib.R;

import java.text.DecimalFormat;

/**
 * 其他的一些方法
 * Created by Administrator on 2016/6/17.
 */
public class CommonUtil {

    // 隐藏手机号中间4位
    public static String getPhoneStr(String idcard) {
        if (idcard == null) {return "";}
        if (idcard.length() >= 11) {
            String str1 = idcard.substring(0, 3);
            String str2 = idcard.substring(idcard.length() - 4);
            return str1 + "****" + str2;
        }
        return idcard;
    }

    public static String replaceLast4WithStar(String str) {
        if (str == null) {
            return "";
        }
        if (str.length() > 3) {
            try {
                return str.substring(0,str.length()-4)+"****";
            }catch (Exception e) {
                return str;
            }
        }
        return str;
    }

    // 隐藏后4位
    public static String getCardStr(String idcard) {
        if (idcard == null) {
            return "";
        }
        if (idcard.length() > 6) {
            String s = idcard.substring(0, idcard.length() - 4);
            return s + "****";
        }
        return idcard;
    }

    public static String parseDouble2String(double num) {
        return String.valueOf(Double.parseDouble(new DecimalFormat("#.##").format(num)));
    }

    /**
     * @param idcard
     * @return
     * @deprecated Use {@link #getCardStr(String)} instead.
     */
    @Deprecated
    public static String getIdcardStr(String idcard) {
        if (idcard == null) {return "";}
        if (idcard.length() >= 12) {
            String str1 = idcard.substring(0, 4);
            String str2 = idcard.substring(4, 8);
            String str3 = idcard.substring(8, 12);
            String str4 = idcard.substring(12, idcard.length());
            return str1 + " " + str2 + " " + str3 + " " + str4;
        }
        return idcard;
    }

    /**
     * 检查当前网络是否可用有提示信息
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            Toast.makeText(context, context.getResources().getString(R.string.net_error), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    //System.out.println(i + "===状态===" + networkInfo[i].getState());
                    //System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        Toast.makeText(context, context.getResources().getString(R.string.net_error), Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 检查当前网络是否可用没有提示信息
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailableWithNoToast(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    //System.out.println(i + "===状态===" + networkInfo[i].getState());
                    //System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //add Id:none 咨询聊天 chenkai 20170916 start
    public static String getSexStr(String sexId) {
        if ("1".equals(sexId)) {
            return AppContext.resources().getString(R.string.male);
        } else if ("2".equals(sexId)) {
            return AppContext.resources().getString(R.string.female);
        } else {
            return AppContext.resources().getString(R.string.sex_not_know);
        }
    }
    //add Id:none 咨询聊天 chenkai 20170916 end

    /**
     * 密码复杂性验证
     * @param pwd
     * @return
     */
    public static boolean validatePwd(String pwd){
        if(pwd == null){ return false;}
        if(pwd.matches("^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{8,20}$")){
            return true;
        }
        return false;
    }
}

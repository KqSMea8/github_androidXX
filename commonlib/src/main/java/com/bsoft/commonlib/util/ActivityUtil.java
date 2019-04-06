package com.bsoft.commonlib.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.bsoft.baselib.AppContext;


import java.util.List;

/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2018/10/26
 * describe :
 */
public class ActivityUtil {

    /**
     * 判断Activity是否存活于栈中
     */
    public static boolean isActivityLiveInStack(Class<?> activity){
        Context context = AppContext.getContext();
        Intent intent = new Intent(context,activity);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        if (componentName != null){ //说明系统中存在这个activity    
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null) return false;
            // TODO: 获取从栈顶开始往下查找的10个activity
            List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(10);
            if (taskInfoList != null && taskInfoList.size()>0){
                for (ActivityManager.RunningTaskInfo runningTaskInfo : taskInfoList){
                    if (runningTaskInfo.baseActivity.equals(componentName)){ //说明它已经启动了
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

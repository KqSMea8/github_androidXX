package com.bsoft.componentXX;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2018/8/20
 * describe : 接收推送消息
 */
public class PushReceiver extends BroadcastReceiver {

    private static final String TAG = "PushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //极光推送发来的自定义消息
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                Log.d(TAG, "接收到推送JPush下来的自定义消息： " + msg);

                JSONObject object = JSON.parseObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                int kind = object.getIntValue("kinds");
                switch (kind) {
                    case 1: //排队叫号
                        // TODO: 2018/8/20
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }
        }
    }

}

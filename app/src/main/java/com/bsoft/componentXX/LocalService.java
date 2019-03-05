package com.bsoft.componentXX;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class LocalService  extends ForegroundService{


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        bindService(new Intent(this,RemoteService.class),connection , Context.BIND_IMPORTANT);
        return super.onStartCommand(intent, flags, startId);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(LocalService.this,RemoteService.class));
            bindService(new Intent(LocalService.this,RemoteService.class),connection , Context.BIND_IMPORTANT);
        }
    };


}

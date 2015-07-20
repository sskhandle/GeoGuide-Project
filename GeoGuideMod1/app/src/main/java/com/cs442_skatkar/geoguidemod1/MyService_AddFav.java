package com.cs442_skatkar.geoguidemod1;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService_AddFav extends Service {

    private final IBinder lvBinder = new MyLocalBinder();
    String infor;

    public MyService_AddFav() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return lvBinder;
    }

    public class MyLocalBinder extends Binder{
        MyService_AddFav getService(){
            return MyService_AddFav.this;
        }
    }

    public String get_Info(){

        return infor;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        infor = intent.getStringExtra("infor");

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

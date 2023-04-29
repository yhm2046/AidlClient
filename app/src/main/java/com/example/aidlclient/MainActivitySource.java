package com.example.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.aidlclient.databinding.ActivityMainBinding;
import com.example.aidlserver.IMyAidlInterface;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 客户端aidl测试
 */
public class MainActivitySource extends AppCompatActivity {
    int i = 0;
    private static final String TAG = "MainActivity:xwg";
    private ActivityMainBinding activityMainBinding;
    IMyAidlInterface myAidlInterface ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(activityMainBinding.getRoot());

        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                try {
                    Log.i(TAG,"onServiceConnected..");
                    myAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
//                    String st= myAidlInterface.getStr();
//                    Log.i(TAG,"st:" + st);
//                    activityMainBinding.tvShow.setText(st);
                    runOnUiThread(()->{
//                        activityMainBinding.tvShow.setText(myAidlInterface.sendMessage(););
                    });
                } catch (Exception e) {
                    Log.i(TAG,"onServiceConnected:" + e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };  //end connection

        Intent intent = new Intent();   //调用远端的服务
//        intent.setComponent(new ComponentName("com.example.service", "com.example.service.MyService"));
        intent.setPackage("com.example.aidlserver");
        intent.setAction("com.example.service.action");
        bindService(intent,connection,BIND_AUTO_CREATE);

//        点击刷新按钮
        activityMainBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                String st= myAidlInterface.sendMessage();
                                Log.i(TAG,"st:" + st);
//                                activityMainBinding.tvShow.setText(st); //显示随机数
                            } catch (RemoteException e) {
                                Log.i(TAG,"time exception:" + e);
                                throw new RuntimeException(e);
                            }
                        }
                    }, 0, 2 * 1000);
                } catch (Exception e) {
                    Log.i(TAG,"onclick error:" + e);
                    e.printStackTrace();
                }
            }
        });

    }//end onCreate
}
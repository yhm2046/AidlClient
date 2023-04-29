package com.example.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
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
import com.example.aidlserver.ITimerService;

public class TestMSGActivity extends AppCompatActivity {
    private static final String TAG = "TestMSGActivity:xwg";
    private ActivityMainBinding activityMainBinding;
    private TimerClient mTimerClient;
    ITimerService timerService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            activityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
            setContentView(activityMainBinding.getRoot());

        Log.i(TAG,"TestMSGActivity onCreate");

        //绑定远端服务
        Intent intent = new Intent();
        intent.setPackage("com.example.aidlserver");
        intent.setAction("com.example.timerservice.action");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        activityMainBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.i(TAG,"click");

                } catch (Exception e) {
                    Log.i(TAG,"onclick error:" + e);
                    e.printStackTrace();
                }
            }
        });
    }//onCreate
    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            Log.i(TAG,"onServiceConnected...");
            try {
                timerService = ITimerService.Stub.asInterface(service);
                String receiveMsg = timerService.sendMsg();
                Log.i(TAG,"receiveMsg:" + receiveMsg);
            } catch (Exception e) {
                Log.i(TAG,"onServiceConnected exception:" + e);
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG,"onServiceDisconnected...");
//            mTimerClient.stopTimer(); // 停止定时器
//            mTimerClient = null;
        }
    };


}
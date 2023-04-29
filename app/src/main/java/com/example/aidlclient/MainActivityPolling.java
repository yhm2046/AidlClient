package com.example.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aidlserver.IDataService;
import com.example.aidlserver.IDataServiceCallback;

/**
 * 客户端主动轮询,发送数据
 */
public class MainActivityPolling extends AppCompatActivity {
    private static final String TAG = "client-MainActivityPolling:xwg";
    private IDataService mService;

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mService != null) {
                try {
                    String message = "485 date-" + System.currentTimeMillis();
                    mService.sendMessage(message);
                    Log.i(TAG,"client send to server:" + message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            mHandler.postDelayed(this, 2 * 1000);
        }
    };

    private IDataServiceCallback mCallback = new IDataServiceCallback.Stub() {
        @Override
        public void onMessageReceived(String message) {
            Log.d(TAG, "Received message from server: " + message);
        }
    };



    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            String message = "onServiceConnected success!";
            mService = IDataService.Stub.asInterface(service);
            try {
                mService.registerCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            String message = "onServiceDisconnected success!";
            mService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setClassName("com.example.aidlserver", "com.example.aidlserver.DataServiceNoPolling");
        bindService(intent, mConnection, BIND_AUTO_CREATE);

        mHandler.postDelayed(mRunnable, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            try {
                mService.unregisterCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            unbindService(mConnection);
        }
        mHandler.removeCallbacks(mRunnable);
    }
}


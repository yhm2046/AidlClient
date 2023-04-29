package com.example.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aidlserver.IDataService;
import com.example.aidlserver.IDataServiceCallback;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "client-MainActivity:xwg";
    private IDataService mService;
    private IDataServiceCallback mCallback = new IDataServiceCallback.Stub() {
        @Override
        public void onMessageReceived(String message) { //收到客户端的回调
            Log.d(TAG, "Received from server: " + message);
            try {
                mService.sendMessage("send to server: " + message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected! " );
            mService = IDataService.Stub.asInterface(service);
            try {
                mService.registerCallback(mCallback);
            } catch (RemoteException e) {
                Log.d(TAG, "onServiceConnected exception: " + e );
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected! " );
            mService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setClassName("com.example.aidlserver", "com.example.aidlserver.DataService");
        bindService(intent, mConnection, BIND_AUTO_CREATE);
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
    }
}

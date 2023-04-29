package com.example.aidlclient;

import android.os.RemoteException;

import com.example.aidlserver.ITimerService;

// TimerClient.java
public class TimerClient {
    private final ITimerService mService;

    public TimerClient(ITimerService service) {
        mService = service;
    }

    public void startTimer(int interval, int count) {
        try {
            mService.startTimer(interval, count);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stopTimer() {
        try {
            mService.stopTimer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        try {
            return mService.getCount();
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

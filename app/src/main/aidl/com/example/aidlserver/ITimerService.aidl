// ITimerService.aidl
package com.example.aidlserver;

// Declare any non-default types here with import statements

interface ITimerService {
   void startTimer(int interval, int count);
       void stopTimer();
       int getCount();
       String sendMsg();  //发送数据
}
// IDataService.aidl
package com.example.aidlserver;
import com.example.aidlserver.IDataServiceCallback; //引用另外一个aidl
interface IDataService {
    void sendMessage(String message);
    void registerCallback(IDataServiceCallback callback);
    void unregisterCallback(IDataServiceCallback callback);
}


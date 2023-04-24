// IMyAidlInterface.aidl
package com.example.aidlserver;

//注意adil文件不可以定义重载方法!

interface IMyAidlInterface {
     String get15RandomString();    //生成随机数的方法
     String get15RandomStringPhrase(int length,String chaStr);    //生成length位随机数的方法,字符的取值
     String getStr();   //获取一个Test20230323的值
     List<String> getAirWindSpeed(int port, int address, int index, String exp); //kaili 代码
}
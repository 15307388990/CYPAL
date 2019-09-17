package com.cypal.ming.cypal.utils;

public enum MessageEnum {
    LOGIN,//新连接，登录
    LOGINOUT,//强制离线
    SYSTEM,//系统消息,通知栏通知一下
    OTCHAND,//抢单消息，抢单页，推单
    OTCAUTO,//自动接单接到单了，通知栏通知一下
    TRANSFER,//转账消息,通知栏通知一下
    RECHARGE,//充值成功消息，通知栏通知一下
    IM,//IM消息
    PING;//心跳测试
}
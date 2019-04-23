package com.cypal.ming.cypal.vm;

public interface IWsManager {
    void onTextMessage(String text);

    void onDisconnected();

    void onConnected();

}

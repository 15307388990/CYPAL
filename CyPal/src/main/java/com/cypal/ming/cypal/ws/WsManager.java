package com.cypal.ming.cypal.ws;

import android.text.TextUtils;
import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WsManager {

    private static WsManager mInstance;
    private final String TAG = this.getClass().getSimpleName();

    /**
     * WebSocket config
     */
    private static final int FRAME_QUEUE_SIZE = 5;
    private static final int CONNECT_TIMEOUT = 5000;
    private static final String DEF_TEST_URL = "测试服地址";//测试服默认地址
    private static final String DEF_RELEASE_URL = "ws:111.230.242.115:88/token=MTg1NjY2MjIwNjgsMTIzNDU2";//正式服默认地址
    private String url;

    private WsStatus mStatus;
    private WebSocket ws;
    private WsListener mListener;

    public void init(String token) {
        try {
            /**
             * configUrl其实是缓存在本地的连接地址
             * 这个缓存本地连接地址是app启动的时候通过http请求去服务端获取的,
             * 每次app启动的时候会拿当前时间与缓存时间比较,超过6小时就再次去服务端获取新的连接地址更新本地缓存
             */
            String configUrl = DEF_RELEASE_URL;
            url = configUrl;
            ws = new WebSocketFactory().createSocket( url, CONNECT_TIMEOUT )
                    .setFrameQueueSize( FRAME_QUEUE_SIZE )//设置帧队列最大值为5
                    .setMissingCloseFrameAllowed( false )//设置不允许服务端关闭连接却未发送关闭帧
                    .addListener( mListener = new WsListener() )//添加回调监听
                    .connectAsynchronously();//异步连接
            setStatus( WsStatus.CONNECTING );
            Log.d( TAG, "第一次连接" );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 继承默认的监听空实现WebSocketAdapter,重写我们需要的方法
     * onTextMessage 收到文字信息
     * onConnected 连接成功
     * onConnectError 连接失败
     * onDisconnected 连接关闭
     */
    class WsListener extends WebSocketAdapter {
        @Override
        public void onTextMessage(WebSocket websocket, String text) throws Exception {
            super.onTextMessage( websocket, text );
            Log.d( TAG, text );
        }


        @Override
        public void onConnected(WebSocket websocket, Map<String, List<String>> headers)
                throws Exception {
            super.onConnected( websocket, headers );
            Log.d( TAG, "连接成功" );
            setStatus( WsStatus.CONNECT_SUCCESS );
        }


        @Override
        public void onConnectError(WebSocket websocket, WebSocketException exception)
                throws Exception {
            super.onConnectError( websocket, exception );
            Log.d( TAG, "连接错误" );
            setStatus( WsStatus.CONNECT_FAIL );
        }


        @Override
        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer)
                throws Exception {
            super.onDisconnected( websocket, serverCloseFrame, clientCloseFrame, closedByServer );
            Log.d( TAG, "断开连接" );
            setStatus( WsStatus.CONNECT_FAIL );
        }
    }

    private void setStatus(WsStatus status) {
        this.mStatus = status;
    }

    private WsStatus getStatus() {
        return mStatus;
    }

    public void disconnect() {
        if (ws != null)
            ws.disconnect();
    }
}

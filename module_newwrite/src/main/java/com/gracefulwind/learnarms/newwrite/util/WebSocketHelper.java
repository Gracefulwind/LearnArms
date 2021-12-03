package com.gracefulwind.learnarms.newwrite.util;

import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;

import javax.annotation.Nullable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * @ClassName: WebSocketHelper
 * @Author: Gracefulwind
 * @CreateDate: 2021/12/2
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/12/2
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class WebSocketHelper extends WebSocketListener {
    public static final String TAG = "WebSocketHelper";

    private String webUrl;
    private WebSocket webSocket;
    private ConnectStatus status;
    private WebSocketCallBack mSocketIOCallBack;
    private OkHttpClient client = new OkHttpClient.Builder()
            .build();

    private WebSocketHelper(String url) {
        this.webUrl = url;
    }

    public static WebSocketHelper connect(String url){
        WebSocketHelper webSocketHelper = new WebSocketHelper(url);
        //构造request对象
        Request request = new Request.Builder()
                .url(url)
                .build();
        webSocketHelper.webSocket = webSocketHelper.client.newWebSocket(request, webSocketHelper);
        webSocketHelper.status = ConnectStatus.Connecting;
        return webSocketHelper;
    }

    public ConnectStatus getStatus() {
        return status;
    }

    public void reConnect() {
        if (webSocket != null) {
            webSocket = client.newWebSocket(webSocket.request(), this);
        }
    }

    public void send(String text) {
        if (webSocket != null) {
            LogUtil.d(TAG,"send： " + text);
            webSocket.send(text);
        }
    }

    public void cancel() {
        if (webSocket != null) {
            webSocket.cancel();
        }
    }

    public void close() {
        if (webSocket != null) {
            webSocket.close(1000, null);
        }
    }

//===========================================================
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
//        super.onOpen(webSocket, response);
        LogUtil.d(TAG, "onOpen");
        this.status = ConnectStatus.Open;
        if (mSocketIOCallBack != null) {
            mSocketIOCallBack.onOpen();
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
//        super.onMessage(webSocket, text);
        LogUtil.d(TAG, "onMessage: " + text);
        if (mSocketIOCallBack != null) {
            mSocketIOCallBack.onMessage(text);
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
//        super.onClosing(webSocket, code, reason);
        this.status = ConnectStatus.Closing;
        LogUtil.d(TAG, "onClosing");
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
//        super.onClosed(webSocket, code, reason);
        LogUtil.d(TAG, "onClosed");
        this.status = ConnectStatus.Closed;
        if (mSocketIOCallBack != null) {
            mSocketIOCallBack.onClose();
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
//        super.onMessage(webSocket, bytes);
    }

    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
//        super.onFailure(webSocket, t, response);
        LogUtil.d(TAG, "onFailure");
        this.status = ConnectStatus.Canceled;
        if (mSocketIOCallBack != null) {
            mSocketIOCallBack.onFailure(t, response);
        }
    }

//===========================================================

    public void setSocketIOCallBack(WebSocketCallBack callBack) {
        mSocketIOCallBack = callBack;
    }

    public void removeSocketIOCallBack() {
        mSocketIOCallBack = null;
    }


    public static abstract class WebSocketCallBack{

        public void onOpen() {
        }

        public void onMessage(String text) {
        }

        public void onClose() {
        }

        public void onFailure(Throwable t, @Nullable Response response){

        }
    }
}

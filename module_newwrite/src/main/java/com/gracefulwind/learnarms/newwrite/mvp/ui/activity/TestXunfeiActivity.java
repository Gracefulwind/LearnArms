package com.gracefulwind.learnarms.newwrite.mvp.ui.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.JsonObject;
import com.gracefulwind.learnarms.commonsdk.core.Constants;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.FileUtil;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.commonsdk.utils.XunfeiUtil;
import com.gracefulwind.learnarms.newwrite.R;
import com.gracefulwind.learnarms.newwrite.R2;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * @ClassName: TestXunfeiActivity
 * @Author: Gracefulwind
 * @CreateDate: 2021/12/2
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/12/2
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.NewWrite.TEST_XUNFEI_ACTIVITY)
public class TestXunfeiActivity extends BaseActivity {
    public static final String TAG = "TestXunfeiActivity";

    @BindView(R2.id.natx_btn_test_voice)
    Button natxBtnVoice;
    @BindView(R2.id.natx_btn_test_write)
    Button natxBtnWrite;
    @BindView(R2.id.natx_et_input)
    EditText natxEtInput;
    @BindView(R2.id.natx_tv_result)
    TextView natxTvResult;
    private WebSocket webSocket;
    private MediaRecorder mMediaRecorder;
    private String fileName;
    private Context mContext;
    private File filePath;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        return R.layout.newwrite_activity_test_xunfei;
    }

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
//        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .pingInterval(40, TimeUnit.SECONDS)
//                .build();
//        httpClient.newWebSocket()
        mContext = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //test不管了，反正有权限的
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R2.id.natx_btn_connect_voice, R2.id.natx_btn_test_voice, R2.id.natx_btn_test_write
            , R2.id.natx_btn_test_click1, R2.id.natx_btn_test_click
            })
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.natx_btn_connect_voice == id){
            //连接语音
            try{
//                String s = initParams();
                connectVoice();
                System.out.println("=================");
                System.out.println("=================");
                System.out.println("=================");
            }catch (Exception e){
                e.printStackTrace();
            }

        }else if(R.id.natx_btn_test_voice == id){
            //测试录音
            doRecord();
        }else if(R.id.natx_btn_test_write == id){
            //测试手写
            try {
                connectWord();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else if(R.id.natx_btn_test_click == id){
            //======
            System.out.println("=========");
            System.out.println("=========");
            System.out.println("=========");
        }else if(R.id.natx_btn_test_click1 == id){
            //======测试角色分离
            connectLongFormASR();
        }else if(R.id.natx_btn_test_write == id){
            //======
        }
    }

    private void connectLongFormASR() {

    }

    int writeFlag = 6;
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void connectWord() throws UnsupportedEncodingException {
        AssetManager assets = getAssets();
        InputStream open = null;
        byte[] bytes;
        try {
            String add = "";
            if(writeFlag <= 0){
                add = "ocr.jpg";
            }else {
                add = "test" + writeFlag + ".png";
            }
            open = assets.open(add);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while ((n = open.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            bytes = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Request wordRequest = XunfeiUtil.getWordRequest(bytes);
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        client.newCall(wordRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e(TAG, "onFailure");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.e(TAG, "onResponse, response = " + response.body().string());
            }
        });

    }

    //    private static final String appid = "a2d1f0bf"; //在控制台-我的应用获取
//    private static final String apiSecret = "NGYzNDZhOGNlOThiNzdlNDhkYjdhYTQ1"; //在控制台-我的应用-语音听写（流式版）获取
//    private static final String apiKey = "99a7c5355ea23cd0462ed2cca0b33ac6"; //在控制台-我的应用-语音听写（流式版）获取
    private static String baseUrl = "http://iat-api.xfyun.cn/v2/iat?authorization=%s&date=%s&host=%s";
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void connectVoice() throws Exception {
        HttpUrl authUrl = XunfeiUtil.getVoiceUrl();
        String realUrl = authUrl.toString().replace("http://", "ws://").replace("https://", "wss://");
//        String realUrl = String.format(baseUrl, "");
        //构造request对象
        Request request = new Request.Builder()
                .url(realUrl)
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                LogUtil.e(TAG, "onOpen, response = " + (null != response ? response.message() : null));
//                if(null != filePath){
//                    webSocket.send();
//                }
                if(null != filePath){
                    testDemo();
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                LogUtil.e(TAG, "onMessage String = " + text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                //几乎没用
                LogUtil.e(TAG, "onMessage ByteString = " + bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                LogUtil.e(TAG, "onClosing = ");
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                LogUtil.e(TAG, "onClosed = ");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, @org.jetbrains.annotations.Nullable Response response) {
                super.onFailure(webSocket, t, response);
                LogUtil.e(TAG, "onFailure: response = " + (null != response ? response.message() : null));
//                t.printStackTrace();
                LogUtil.e(TAG, t.toString() + "\r\n" + t.getMessage() + "\r\n" + t.getCause());
            }
        });
    }

//    private static final String file = "resource\\iat\\16k_10.pcm";
    public static final int StatusFirstFrame = 0;
    public static final int StatusContinueFrame = 1;
    public static final int StatusLastFrame = 2;
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void testDemo() {
        new Thread(()->{
            //连接成功，开始发送数据
            int frameSize = 1280; //每一帧音频的大小,建议每 40ms 发送 122B
            int intervel = 40;
            int status = 0;  // 音频的状态
//            try (FileInputStream fs = new FileInputStream(file)) {
//            try (FileInputStream fs = new FileInputStream(filePath)) {
            //------
            AssetManager assets = getAssets();
            InputStream open = null;
            try {
                if(null != filePath){
                    open = new FileInputStream(filePath);
                }else {
                    open = assets.open("16k_10.pcm");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            try /*(FileInputStream fs = new FileInputStream(filePath))*/ {
            //------
                byte[] buffer = new byte[frameSize];
                // 发送音频
                end:
                while (true) {
                    int len = open.read(buffer);
//                    int len = fs.read(buffer);
                    if (len == -1) {
                        status = StatusLastFrame;  //文件读完，改变status 为 2
                    }
                    switch (status) {
                        case StatusFirstFrame:   // 第一帧音频status = 0
                            JsonObject frame = new JsonObject();
                            JsonObject business = new JsonObject();  //第一帧必须发送
                            JsonObject common = new JsonObject();  //第一帧必须发送
                            JsonObject data = new JsonObject();  //每一帧都要发送
                            // 填充common
                            common.addProperty("app_id", Constants.XunFei.APPID);
                            //填充business
                            business.addProperty("language", "zh_cn");
                            //business.addProperty("language", "en_us");//英文
                            //business.addProperty("language", "ja_jp");//日语，在控制台可添加试用或购买
                            //business.addProperty("language", "ko_kr");//韩语，在控制台可添加试用或购买
                            //business.addProperty("language", "ru-ru");//俄语，在控制台可添加试用或购买
                            business.addProperty("domain", "iat");
                            business.addProperty("accent", "mandarin");//中文方言请在控制台添加试用，添加后即展示相应参数值
                            //business.addProperty("nunum", 0);
                            //business.addProperty("ptt", 0);//标点符号
                            //business.addProperty("rlang", "zh-hk"); // zh-cn :简体中文（默认值）zh-hk :繁体香港(若未授权不生效，在控制台可免费开通)
                            //business.addProperty("vinfo", 1);
                            business.addProperty("dwa", "wpgs");//动态修正(若未授权不生效，在控制台可免费开通)
                            //business.addProperty("nbest", 5);// 句子多候选(若未授权不生效，在控制台可免费开通)
                            //business.addProperty("wbest", 3);// 词级多候选(若未授权不生效，在控制台可免费开通)
                            //填充data
                            data.addProperty("status", StatusFirstFrame);
                            data.addProperty("format", "audio/L16;rate=16000");
                            data.addProperty("encoding", "raw");
                            data.addProperty("audio", Base64.getEncoder().encodeToString(Arrays.copyOf(buffer, len)));
                            //填充frame
                            frame.add("common", common);
                            frame.add("business", business);
                            frame.add("data", data);
                            webSocket.send(frame.toString());
                            status = StatusContinueFrame;  // 发送完第一帧改变status 为 1
                            break;
                        case StatusContinueFrame:  //中间帧status = 1
                            JsonObject frame1 = new JsonObject();
                            JsonObject data1 = new JsonObject();
                            data1.addProperty("status", StatusContinueFrame);
                            data1.addProperty("format", "audio/L16;rate=16000");
                            data1.addProperty("encoding", "raw");
                            data1.addProperty("audio", Base64.getEncoder().encodeToString(Arrays.copyOf(buffer, len)));
                            frame1.add("data", data1);
                            webSocket.send(frame1.toString());
                            // System.out.println("send continue");
                            break;
                        case StatusLastFrame:    // 最后一帧音频status = 2 ，标志音频发送结束
                            JsonObject frame2 = new JsonObject();
                            JsonObject data2 = new JsonObject();
                            data2.addProperty("status", StatusLastFrame);
                            data2.addProperty("audio", "");
                            data2.addProperty("format", "audio/L16;rate=16000");
                            data2.addProperty("encoding", "raw");
                            frame2.add("data", data2);
                            webSocket.send(frame2.toString());
                            System.out.println("sendlast");
                            break end;
                    }
                    Thread.sleep(intervel); //模拟音频采样延时
                }
                System.out.println("all data is send");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    boolean isRecording = false;
    String audioSaveDir = "MediaRecorder";
    private void doRecord() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(isRecording){
                Toast.makeText(mContext, "结束录音", Toast.LENGTH_SHORT).show();
                stopRecord();
            }else {
                Toast.makeText(mContext, "开始录音", Toast.LENGTH_SHORT).show();
                startRecord();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startRecord() {
        // 开始录音
        /* ①Initial：实例化MediaRecorder对象 */
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();
        try {
            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            fileName = DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance(Locale.CHINA)) + ".m4a";
            File folderName = FileUtil.getFolderName(getApplicationContext(), audioSaveDir);
            if (!FileUtil.isFolderExist(folderName)) {
//                FileUtil.makeFolders(getApplicationContext(), audioSaveDir);
                return;
            }
            filePath = new File(folderName, fileName);
            /* ③准备 */
            mMediaRecorder.setOutputFile(filePath);
//            mMediaRecorder.setOutputFile(filePath.toString());
            mMediaRecorder.prepare();
            /* ④开始 */
            isRecording = true;
            mMediaRecorder.start();
        } catch (IllegalStateException e) {
            isRecording = false;
            LogUtil.i(TAG,"call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        } catch (IOException e) {
            isRecording = false;
            LogUtil.i(TAG,"call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        }
    }

    private void stopRecord() {
        try {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
            sendToXunfei();
            isRecording = false;
//            filePath = null;
        } catch (RuntimeException e) {
            LogUtil.e(TAG, e.toString());
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            if (null != filePath && filePath.exists()){
                filePath.delete();
            }
            filePath = null;
        }
    }

    private void sendToXunfei() {
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                connectVoice();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//===========================================================================

}

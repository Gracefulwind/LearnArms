package com.gracefulwind.learnarms.newwrite.mvp.ui.activity;

import android.content.Context;
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
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.FileUtil;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.newwrite.R;
import com.gracefulwind.learnarms.newwrite.R2;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.OnClick;
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
            , R2.id.natx_btn_test_click1
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
            
        }else if(R.id.natx_btn_test_click1 == id){
            //======

        }else if(R.id.natx_btn_test_write == id){
            //======
        }
    }

    private static final String appid = "a2d1f0bf"; //在控制台-我的应用获取
    private static final String apiSecret = "NGYzNDZhOGNlOThiNzdlNDhkYjdhYTQ1"; //在控制台-我的应用-语音听写（流式版）获取
    private static final String apiKey = "99a7c5355ea23cd0462ed2cca0b33ac6"; //在控制台-我的应用-语音听写（流式版）获取
    private static String baseUrl = "ws://iat-api.xfyun.cn/v2/iat?authorization=%s&date=%s&host=%s";
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void connectVoice() throws Exception {
//        LogUtil.e(TAG, "=========testVoice");
//        WebSocketHelper webSocketHelper = new WebSocketHelper(url);
//        natxTvResult.setText("");
//        HmacWithShaTobase64()
        String authUrl = initParams();
        String url = authUrl.toString().replace("http://", "ws://").replace("https://", "wss://");
//        String url = String.format(baseUrl, "");
        //构造request对象
        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                LogUtil.e(TAG, "onOpen = ");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                LogUtil.e(TAG, "onMessage String = " + text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
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
                t.printStackTrace();
            }
        });
    }
    String hostUrl = "http://iat-api.xfyun.cn/v2/iat";
    String shaKey = "api_key=\"$api_key\",algorithm=\"hmac-sha256\",headers=\"host date request-line\",signature=\"$signature\"";
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String initParams() throws NoSuchAlgorithmException, InvalidKeyException, MalformedURLException {
        URL url = new URL(hostUrl);
        //date
//        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.CHINA);
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        //author
        StringBuilder builder = new StringBuilder("host: ").append(url.getHost()).append("\n").//
                append("date: ").append(date).append("\n").//
                append("GET ").append(url.getPath()).append(" HTTP/1.1");

        Charset charset = Charset.forName("UTF-8");
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
        mac.init(spec);
        byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));

        String sha = Base64.getEncoder().encodeToString(hexDigits);
        //================
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);

        HttpUrl httpUrl = HttpUrl.parse("http://" + url.getHost() + url.getPath()).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(charset))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();

        return httpUrl.toString();
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
//===========================================================================

}

package com.gracefulwind.learnarms.commonsdk.utils.xunfei;

import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.gracefulwind.learnarms.commonsdk.core.Constants;
import com.gracefulwind.learnarms.commonsdk.utils.EncryptUtil;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.commonsdk.utils.xunfei.entity.AsrBaseEntity;
import com.gracefulwind.learnarms.commonsdk.utils.xunfei.entity.AsrProgressData;

import org.apache.commons.codec.digest.DigestUtils;

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
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;

/**
 * @ClassName: XunfeiUtil
 * @Author: Gracefulwind
 * @CreateDate: 2021/12/2
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/12/2
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class XunfeiUtil {
    public static final String TAG = "XunfeiUtil";

    /**
     *
     * 流式语音听写，PCM
     *
     * */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static HttpUrl getVoiceUrl() throws MalformedURLException, NoSuchAlgorithmException, InvalidKeyException {
        String baseUrl = Constants.XunFei.Voice.BaseUrl;
        String apiKey = Constants.XunFei.Voice.APIKey;
        String apiSecret = Constants.XunFei.Voice.APISecret;
        //url不支持ws，只能先用http的，写完后再替换,这里获取的是ws://iat-api.xfyun.cn
        URL url = new URL(baseUrl);
        //date
//        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.CHINA);
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        //author
        StringBuilder builder = new StringBuilder("host: ").append(url.getHost()).append("\n")
                .append("date: ").append(date).append("\n")
                .append("GET ").append(url.getPath()).append(" HTTP/1.1");
        Charset charset = Charset.forName("UTF-8");
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
        mac.init(spec);
        byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));

        String sha = Base64.getEncoder().encodeToString(hexDigits);
        //================
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        //这里也只能解析http/https的。。。
        HttpUrl httpUrl = HttpUrl.parse("http://" + url.getHost() + url.getPath()).newBuilder()
                .addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(charset)))
                .addQueryParameter("date", date)
                .addQueryParameter("host", url.getHost())
                .build();
        return httpUrl;
    }

    /**
     * OCR识别
     * */
    public static String language = "cn|en";
    public static String location = "false";
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Request getWordRequest(byte[] bitmapBytes) throws UnsupportedEncodingException {
        // 系统当前时间戳
        String X_CurTime = System.currentTimeMillis() / 1000L + "";
        // 业务参数
        String param = "{\"language\":\"" + language + "\"" + ",\"location\":\"" + location + "\"}";
        //一样
        String X_Param = Base64.getEncoder().encodeToString(param.getBytes("UTF-8"));
//        String X_Param1 = new String(Base64.getEncoder().encode(param.getBytes("UTF-8")));
        // 接口密钥
        String apiKey = Constants.XunFei.Word.APIKey;
        // 讯飞开放平台应用ID
        String X_Appid = Constants.XunFei.APPID;
        // 生成令牌
        String X_CheckSum = DigestUtils.md5Hex(apiKey + X_CurTime + X_Param);
        //一样
//        String imageBase64 = new String(org.apache.commons.codec.binary.Base64.encodeBase64(bitmapBytes), "UTF-8");
        String imageBase64 = Base64.getEncoder().encodeToString(bitmapBytes);
        RequestBody formBody = new FormBody.Builder()
                .add("image", imageBase64)
                .build();
        Request request = new Request.Builder()
                .url(Constants.XunFei.Word.BaseUrl)
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("X-Param", X_Param)
                .addHeader("X-CurTime", X_CurTime)
                .addHeader("X-CheckSum", X_CheckSum)
                .addHeader("X-Appid", X_Appid)
                .post(formBody)
                .build();
        return request;
    }

//==角色分离==================
    Handler handler = new Handler(){};

    /**
     * 文件分片大小,可根据实际情况调整
     */
    public static final int SLICE_SICE = 10 * 1024 * 1024;// 10M
    public static String prepare(File audio) throws SignatureException, IOException {
        MultipartBody.Builder params = getBaseAuthParam(null);
        params.addFormDataPart("has_seperate", "true");
        long fileLength = audio.length();
        params.addFormDataPart("file_len", fileLength + "");
        params.addFormDataPart("file_name", audio.getName());
        params.addFormDataPart("slice_num", (fileLength / SLICE_SICE) + (fileLength % SLICE_SICE == 0 ? 0 : 1) + "");

//        FormBody.Builder params = getBaseAuthFormParam(null);
//        params.add("has_seperate", "true");
//        long fileLength = audio.length();
//        params.add("file_len", fileLength + "");
//        params.add("file_name", audio.getName());
//        params.add("slice_num", (fileLength / SLICE_SICE) + (fileLength % SLICE_SICE == 0 ? 0 : 1) + "");

        /********************TODO 可配置参数********************/
        // 转写类型,已取消
//        params.put("lfasr_type", "0");
        // 开启分词
//        params.put("has_participle", "true");
        // 说话人分离
//        params.put("has_seperate", "true");
        // 设置多候选词个数
//        params.put("max_alternatives", "2");
        /****************************************************/
        String url = Constants.XunFei.ASR.BaseUrl + Constants.XunFei.ASR.PREPARE;
        //构造request对象
        Request request = new Request.Builder()
                .url(url)
                .post(params.build())
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Response response = client.newCall(request).execute();
        String message = response.body().string();
        AsrBaseEntity responseEntity = new Gson().fromJson(message, AsrBaseEntity.class);
        if (response == null) {
            throw new RuntimeException("预处理接口请求失败！");
        }
        System.out.println("============");
        String taskId = (String) responseEntity.data;
        if(0 == responseEntity.ok && taskId != null){
            //消息队列，发送taskId
//                    uploadAudioFile(audio, taskId);
            LogUtil.e(TAG, "taskId = " + taskId);
            return taskId;
        }else{
            //发送失败，根据错误原因判断
            //最好别自动重试，免得死循环
            throw new RuntimeException("预处理失败！" + response);
        }

//        client.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String message = response.body().string();
//                XunfeiAsrPrepareEntity responseEntity = new Gson().fromJson(message, XunfeiAsrPrepareEntity.class);
//                if(0 == responseEntity.ok){
//                    //消息队列，发送taskId
//                    String taskId = responseEntity.data;
////                    uploadAudioFile(audio, taskId);
//                }else{
//                    //发送失败，根据错误原因判断
//                    //最好别自动重试，免得死循环
//                }
////                System.out.println("=================");
////                System.out.println("response : " + message);
////                System.out.println("=================");
////                String taskId = null;
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                LogUtil.e(TAG, "error : " + e.getMessage());
//            }
//
//        });
    }

    private static void uploadAudioFile(File audio, String taskId) {
        //=====================================
//        直接创建个子线程做得了。。。
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileInputStream fis = new FileInputStream(audio);
                    // 分片上传文件
                    int len = 0;
                    byte[] slice = new byte[SLICE_SICE];
                    SliceIdGenerator generator = new SliceIdGenerator();
                    while ((len =fis.read(slice)) > 0) {
                        // 上传分片
                        if (fis.available() == 0) {
                            slice = Arrays.copyOfRange(slice, 0, len);
                        }

                        uploadSlice(taskId, generator.getNextSliceId(), slice);
                    }
                    } catch (SignatureException e) {
                        e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 合并文件
//                merge(taskId);
            }
        }).start();

//        FileInputStream fis = null;
//        try{
//            fis = new FileInputStream(audio);
//            // 分片上传文件
//            int len = 0;
//            byte[] slice = new byte[SLICE_SICE];
//            SliceIdGenerator generator = new SliceIdGenerator();
//            uploadSliceRecursive(taskId, fis, len, slice, generator);
//        }catch (IOException e){
//            FileUtil.closeIO(fis);
//        } catch (SignatureException e) {
//            e.printStackTrace();
//        }

    }

    private static void uploadSliceRecursive(String taskId, FileInputStream fis, int len, byte[] slice, SliceIdGenerator generator) throws IOException, SignatureException {
        if ((len = fis.read(slice)) > 0) {
            // 上传分片
            if (fis.available() == 0) {
                slice = Arrays.copyOfRange(slice, 0, len);
            }
            uploadSlice(taskId, generator.getNextSliceId(), slice);
        }
    }

    public static AsrBaseEntity uploadSlice(String taskId, String nextSliceId, byte[] slice) throws SignatureException, IOException {
        MultipartBody.Builder params = getBaseAuthParam(taskId);

//        MultipartBody.Builder params = new MultipartBody.Builder();
//        String ts = String.valueOf(System.currentTimeMillis() / 1000L);
//        params.addFormDataPart("app_id", Constants.XunFei.APPID);
//        params.addFormDataPart("ts", ts);
//
//        params.addFormDataPart("signa", EncryptUtil.HmacSHA1Encrypt(EncryptUtil.MD5(Constants.XunFei.APPID + ts), Constants.XunFei.ASR.SecretKey));
//        if (taskId != null) {
//            params.addFormDataPart("task_id", taskId);
//        }


        params.setType(MultipartBody.FORM);
        params.addFormDataPart("slice_id", nextSliceId);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), slice);
        params.addFormDataPart("content", nextSliceId, requestBody);

//        RequestBody requestBody = new RequestBody() {
//
//            @Override
//            public long contentLength() throws IOException {
//                return slice.length;
//            }
//
//            @Override
//            public MediaType contentType() {
//                return MediaType.parse("application/octet-stream");
//            }
//
//            @Override
//            public void writeTo(BufferedSink sink) throws IOException {
//                //写byte数组
//                Source source;
//                try {
//                    if (slice != null){
//                        source = Okio.source(new ByteArrayInputStream(slice));
//                        Buffer buf = new Buffer();
//                        long remaining = contentLength();
//                        for (long readCount; (readCount = source.read(buf, 1024 * 4)) != -1; ) {
//                            sink.write(buf, readCount);
//                            //callback  进度通知
//                        }
//                    } else {
////                        source = Okio.source(file);
////                        Buffer buf = new Buffer();
////                        long remaining = contentLength();
////                        long current = 0;
////                        for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
////                            sink.write(buf, readCount);
////                            current += readCount;
////                            //callback 进度通知
////                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        params.addFormDataPart("content", nextSliceId, requestBody);

        String url = Constants.XunFei.ASR.BaseUrl + Constants.XunFei.ASR.UPLOAD;
        //构造request对象
        Request request = new Request.Builder()
                .url(url)
                .post(params.build())
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Response response = client.newCall(request).execute();
        String msg = response.body().string();
        AsrBaseEntity asrResponseBean = new Gson().fromJson(msg, AsrBaseEntity.class);
        LogUtil.e(TAG, "response : " + msg);
        System.out.println("==========");
        System.out.println("==========");
        return asrResponseBean;
    }

    /**
     * 文件合并
     *
     * @param taskId        任务id
     * @throws SignatureException
     * @return
     */
    public static AsrBaseEntity merge(String taskId) throws SignatureException, IOException {
        String url = Constants.XunFei.ASR.BaseUrl + Constants.XunFei.ASR.MERGE;
        //构造request对象
        Request request = new Request.Builder()
                .url(url)
                .post(getBaseAuthParam(taskId).build())
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Response response = client.newCall(request).execute();
        String responseStr = response.body().string();
        AsrBaseEntity result = new Gson().fromJson(responseStr, AsrBaseEntity.class);
        if (result == null) {
            throw new RuntimeException("文件合并接口请求失败！");
        }
        return result;
    }

    public static AsrBaseEntity getProgress(String taskId) throws SignatureException, IOException {
        String url = Constants.XunFei.ASR.BaseUrl + Constants.XunFei.ASR.GET_PROGRESS;
        //构造request对象
        Request request = new Request.Builder()
                .url(url)
                .post(getBaseAuthParam(taskId).build())
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Response response = client.newCall(request).execute();
        String responseStr = response.body().string();
        AsrBaseEntity result = new Gson().fromJson(responseStr, AsrBaseEntity.class);
        if (result == null) {
            throw new RuntimeException("文件合并接口请求失败！");
        }
        return result;
    }

    public static AsrBaseEntity getResult(String taskId) throws SignatureException, IOException {
        String url = Constants.XunFei.ASR.BaseUrl + Constants.XunFei.ASR.GET_RESULT;
        //构造request对象
        Request request = new Request.Builder()
                .url(url)
                .post(getBaseAuthParam(taskId).build())
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Response response = client.newCall(request).execute();
        String responseStr = response.body().string();
        AsrBaseEntity result = new Gson().fromJson(responseStr, AsrBaseEntity.class);
        if (result == null) {
            throw new RuntimeException("文件合并接口请求失败！");
        }
        return result;
    }

    public static MultipartBody.Builder  getBaseAuthParam(String taskId) throws SignatureException {
        MultipartBody.Builder params = new MultipartBody.Builder();
        String ts = String.valueOf(System.currentTimeMillis() / 1000L);
        params.addFormDataPart("app_id", Constants.XunFei.APPID);
        params.addFormDataPart("ts", ts);

        params.addFormDataPart("signa", EncryptUtil.HmacSHA1Encrypt(EncryptUtil.MD5(Constants.XunFei.APPID + ts), Constants.XunFei.ASR.SecretKey));
        if (taskId != null) {
            params.addFormDataPart("task_id", taskId);
        }
        return params;
    }

    public static FormBody.Builder  getBaseAuthFormParam(String taskId) throws SignatureException {
        FormBody.Builder params = new FormBody.Builder();
        String ts = String.valueOf(System.currentTimeMillis() / 1000L);
        params.add("app_id", Constants.XunFei.APPID);
        params.add("ts", ts);

        params.add("signa", EncryptUtil.HmacSHA1Encrypt(EncryptUtil.MD5(Constants.XunFei.APPID + ts), Constants.XunFei.ASR.SecretKey));
        if (taskId != null) {
            params.add("task_id", taskId);
        }
        return params;
    }

    public static final int StatusFirstFrame = 0;
    public static final int StatusContinueFrame = 1;
    public static final int StatusLastFrame = 2;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void sendSpeakPcm(WebSocket webSocket, InputStream open){
        new Thread(()->{
            //连接成功，开始发送数据
            int frameSize = 1280; //每一帧音频的大小,建议每 40ms 发送 122B
            int intervel = 40;
            int status = 0;  // 音频的状态
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
                            JsonObject common = new JsonObject();  //第一帧必须发送
                            JsonObject business = new JsonObject();  //第一帧必须发送
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


}

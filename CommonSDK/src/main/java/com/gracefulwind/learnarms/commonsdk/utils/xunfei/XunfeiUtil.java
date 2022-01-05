package com.gracefulwind.learnarms.commonsdk.utils.xunfei;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.text.Html;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonObject;
import com.gracefulwind.learnarms.commonsdk.bean.ApiResultDto;
import com.gracefulwind.learnarms.commonsdk.core.Constants;
import com.gracefulwind.learnarms.commonsdk.utils.EncryptUtil;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;
import okio.BufferedSink;

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

    /**
     * 文件分片大小,可根据实际情况调整
     */
    public static final int SLICE_SICE = 10 * 1024 *1024;// 10M
    public static String prepare(File audio) throws SignatureException, IOException {
        Map<String, String> prepareParam = getBaseAuthParam(null);
        long fileLength = audio.length();

        prepareParam.put("file_len", fileLength + "");
        prepareParam.put("file_name", audio.getName());
        prepareParam.put("slice_num", (fileLength / SLICE_SICE) + (fileLength % SLICE_SICE == 0 ? 0 : 1) + "");

        /********************TODO 可配置参数********************/
        // 转写类型,已取消
//        prepareParam.put("lfasr_type", "0");
        // 开启分词
//        prepareParam.put("has_participle", "true");
        // 说话人分离
//        prepareParam.put("has_seperate", "true");
        // 设置多候选词个数
//        prepareParam.put("max_alternatives", "2");
        /****************************************************/
        String url = Constants.XunFei.ASR.BaseUrl + Constants.XunFei.ASR.PREPARE;
        //构造request对象
        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
//        Response response = client.newCall(request).execute();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String message = response.body().string();
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                LogUtil.e(TAG, "error : " + e.getMessage());
            }

        });
//
//        {
//            String response = HttpUtil.post(url, prepareParam);
//            if (response == null) {
//                throw new RuntimeException("预处理接口请求失败！");
//            }
//            ApiResultDto resultDto = JSON.parseObject(response, ApiResultDto.class);
//            String taskId = resultDto.getData();
//            if (resultDto.getOk() != 0 || taskId == null) {
//                throw new RuntimeException("预处理失败！" + response);
//            }
//
//            System.out.println("预处理成功, taskid：" + taskId);
//        }




        return null;
    }

    public static Map<String, String> getBaseAuthParam(String taskId) throws SignatureException {
        Map<String, String> baseParam = new HashMap<String, String>();
        String ts = String.valueOf(System.currentTimeMillis() / 1000L);
        baseParam.put("app_id", Constants.XunFei.APPID);
        baseParam.put("ts", ts);
        baseParam.put("signa", EncryptUtil.HmacSHA1Encrypt(EncryptUtil.MD5(Constants.XunFei.APPID + ts), Constants.XunFei.ASR.SecretKey));
        if (taskId != null) {
            baseParam.put("task_id", taskId);
        }
        return baseParam;
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

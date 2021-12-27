package com.gracefulwind.learnarms.commonsdk.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.text.Html;

import androidx.annotation.RequiresApi;

import com.gracefulwind.learnarms.commonsdk.bean.ApiResultDto;
import com.gracefulwind.learnarms.commonsdk.core.Constants;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
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
    public static final int SLICE_SICE = 10485760;// 10M
    public static String prepare(File audio) throws SignatureException, IOException {
        Map<String, String> prepareParam = getBaseAuthParam(null);
        long fileLength = audio.length();

        prepareParam.put("file_len", fileLength + "");
        prepareParam.put("file_name", audio.getName());
        prepareParam.put("slice_num", (fileLength / SLICE_SICE) + (fileLength % SLICE_SICE == 0 ? 0 : 1) + "");

        /********************TODO 可配置参数********************/
        // 转写类型
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

}

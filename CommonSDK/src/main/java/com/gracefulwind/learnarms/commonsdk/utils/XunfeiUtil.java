package com.gracefulwind.learnarms.commonsdk.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.gracefulwind.learnarms.commonsdk.core.Constants;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
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

}

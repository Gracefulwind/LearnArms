package com.gracefulwind.learnarms.commonsdk.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

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

import okhttp3.HttpUrl;

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static HttpUrl getVoiceUrl(String baseUrl, String apiKey, String apiSecret) throws MalformedURLException, NoSuchAlgorithmException, InvalidKeyException {
        //url不支持ws，只能先用http的，写完后再替换
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
}

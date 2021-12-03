package com.gracefulwind.learnarms.commonsdk.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtil {
    public static final String TAG = BitmapUtil.class.getName();

    public static byte[] getMergedPic(Context context, String picPath, String assetFileName){
        byte[] byteData = null;
        AssetManager assetsManager = context.getAssets();
        FileInputStream input1 = null;
        InputStream input2 = null;
        try{
            input1 = new FileInputStream(picPath);
            input2 = assetsManager.open(assetFileName);
            byteData = getMergedPic(input1, input2);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            FileUtil.closeIO(input1);
            FileUtil.closeIO(input2);
        }
        return byteData;
    }

    //todo:wd 注意放在异步线程
    public static byte[] getMergedPicWithDefaultIcon(Context context, String picPath){
        //todo:wd 后续问美工要图,或者提供IO
        return getMergedPic(context, picPath, "news_list_live_author_bg.png");
    }

    public static byte[] getMergedPic(InputStream input1, InputStream input2){
        byte[] byteData = null;
        Bitmap bitmap1 = BitmapFactory.decodeStream(input1);
        Bitmap bitmap2 = BitmapFactory.decodeStream(input2);
        Bitmap mergedBitmap = newBitmap(bitmap1, bitmap2);
        bitmap1.recycle();
        bitmap2.recycle();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        boolean ret = mergedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        if (!mergedBitmap.isRecycled()){
            mergedBitmap.recycle();
        }
        byteData = output.toByteArray();
        return byteData;
    }

    public static Bitmap newBitmap(Bitmap bmp1, Bitmap bmp2) {
        Bitmap retBmp;
        int width = bmp1.getWidth();
        if (bmp2.getWidth() != width) {
            //以第一张图片的宽度为标准，对第二张图片进行缩放。

            int h2 = bmp2.getHeight() * width / bmp2.getWidth();
            retBmp = Bitmap.createBitmap(width, bmp1.getHeight() + h2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(retBmp);
            Bitmap newSizeBmp2 = resizeBitmap(bmp2, width, h2);
            canvas.drawBitmap(bmp1, 0, 0, null);
            canvas.drawBitmap(newSizeBmp2, 0, bmp1.getHeight(), null);
            newSizeBmp2.recycle();
        } else {
            //两张图片宽度相等，则直接拼接。
            retBmp = Bitmap.createBitmap(width, bmp1.getHeight() + bmp2.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(retBmp);
            canvas.drawBitmap(bmp1, 0, 0, null);
            canvas.drawBitmap(bmp2, 0, bmp1.getHeight(), null);
        }
        return retBmp;
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        float scaleWidth = ((float) newWidth) / bitmap.getWidth();
        float scaleHeight = ((float) newHeight) / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bmpScale = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bmpScale;
    }


//    /**
//     *
//     * 保存bitmap到手机
//     * */
//    public static String saveBitmap(Context context, Bitmap bitmap){
//        String savedPath = null;
//        LogUtil.d(TAG, "saveBitmap");
//        File externalCacheDir = context.getExternalCacheDir();
//        File cacheDir = null;
//        cacheDir = externalCacheDir.exists() ? externalCacheDir : context.getCacheDir();
//        if(!cacheDir.exists()){
//            //throw or toast?
//        }
//        String bitmapName =System.currentTimeMillis() + ".jpg";
//        File savedFile = new File(cacheDir, bitmapName);
//        FileOutputStream output = null;
//        try {
//            output = new FileOutputStream(savedFile);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
//            output.flush();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            FileUtil.closeIO(output);
//        }
//        savedPath = savedFile.getAbsolutePath();
//        return savedPath;
//    }

    /**
     *
     * 保存bitmap到手机
     * */
    public static String saveBitmap(Context context, Bitmap bitmap){
        String savedPath = null;
        LogUtil.d(TAG, "saveBitmap");

        File cacheDir = null;
//        //存在picture文件夹下,google推荐的方式怎么说呢。。。至少vivo手机的图库里拿不到
        cacheDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        //外部路径的自定义名字是不行的
//        cacheDir = Environment.getExternalStoragePublicDirectory("ceshide");
        //存在项目路径下,也可以在图册中看到，建议不用PublicDirectory了
        cacheDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        cacheDir = context.getExternalFilesDir("ceshide");
        //缓存路径
//        File externalCacheDir = context.getExternalCacheDir();
//        cacheDir = externalCacheDir.exists() ? externalCacheDir : context.getCacheDir();
        if(!cacheDir.exists()){
            //throw or toast?
            return null;
        }
        String bitmapName =System.currentTimeMillis() + ".png";
        File savedFile = new File(cacheDir, bitmapName);
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(savedFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            //
            return null;
        }finally {
            FileUtil.closeIO(output);
        }
        savedPath = savedFile.getAbsolutePath();
        MediaScannerConnection.scanFile(context, new String[]{savedPath}, null, null);
        return savedPath;
    }

}

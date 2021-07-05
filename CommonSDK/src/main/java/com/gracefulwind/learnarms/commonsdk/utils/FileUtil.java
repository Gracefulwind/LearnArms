package com.gracefulwind.learnarms.commonsdk.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    /**
     * 关闭io流
     * */
    public static void closeIO(Closeable io){
        try {
            if (null != io) {
                io.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * 根据路径获取图片的uri
     *
     * */
    public static Uri getImageContentUri(Context context, String path) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=? ",
                new String[] { path }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            // 如果图片不在手机的共享图片数据库，就先把它插入。
            if (new File(path).exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, path);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     *
     * 通过uri，获取bitmap
     *
     * */
    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    context.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * 通过uri，获取bitmap
     *
     * */
    public static Bitmap getBitmapFromFileString(Context context, String filePath) {
        Uri uri = getImageContentUri(context, filePath);
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    context.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * 根据路径文件，并将其转为byte数组
     * @param path 文件的绝对路径
     *
     * */
    public static byte[] getFileBytesByPath(String path) {
        byte[] byteData = null;
        FileInputStream input = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            input = new FileInputStream(path);
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byteData = output.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeIO(input);
            closeIO(output);
        }
        return byteData;
    }

    /**
     *
     * 根据路径文件，并将其转为base64
     * @param path 文件的绝对路径
     *
     * */
    public static String getFileBase64ByPath(String path) {
        if(TextUtils.isEmpty(path)){
            return null;
        }
        File file = new File(path);
        if(!file.exists()){
            return null;
        }
        String base64 = null;
        FileInputStream input = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            input = new FileInputStream(path);
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] byteData = output.toByteArray();
            base64 = Base64.encodeToString(byteData, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeIO(input);
            closeIO(output);
        }
        return base64;
    }

    /**
     *
     * 获取asset文件，并将其转为byte数组
     * @param assetFileName asset文件的路径
     *
     * */
    public static byte[] getBytesByAsset(Context context, String assetFileName) {
        byte[] byteData = null;
        AssetManager assetsManager = context.getAssets();
        InputStream input = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            input = assetsManager.open(assetFileName);

            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byteData = output.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeIO(input);
            closeIO(output);
        }
        return byteData;
    }

    /**
     *
     * 获取asset文件，并将其转为BASE64
     * @param assetFileName asset文件的路径
     *
     * */
    public static String getBase65ByAsset(Context context, String assetFileName) {
        String base64 = null;
        AssetManager assetsManager = context.getAssets();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        InputStream input = null;
        try {
            input = assetsManager.open(assetFileName);
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] bytes = output.toByteArray();
            base64 = Base64.encodeToString(bytes, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeIO(input);
            closeIO(output);
        }
        return base64;
    }


}

package com.gracefulwind.learnarms.commonsdk.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;

import com.gracefulwind.learnarms.commonsdk.core.MyApplication;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
//===================================================================================================
    public static boolean isFolderExist(String fileDir){
        return new File(fileDir).exists();
    }

    public static boolean isFolderExist(File fileDir){
//        new File(fileDir, "123");
        return new File(fileDir.getAbsolutePath()).exists();
    }

    /**
     * 根据folderName获取项目下的文件夹路径
     * */
    public static File getExternalFolder(String folderName){
        return getExternalFolder(MyApplication.getContext(), folderName);
    }

    /**
     * 根据folderName获取项目下的文件夹路径
     * */
    public static File getExternalFolder(Context context, String folderName){
        File folderDir = null;
        folderDir = context.getExternalFilesDir(folderName);
        return folderDir;
    }

    /**
     * 根据folderName生成项目下的文件夹路径
     * */
    public static File makeExternalFolder(String folderName){
        return makeExternalFolder(MyApplication.getContext(), folderName);
    }

    /**
     * 根据folderName生成项目下的文件夹路径
     * */
    public static File makeExternalFolder(Context context, String folderName){
        File folderDir = null;
        folderDir = context.getExternalFilesDir(folderName);
        if(!folderDir.exists()){
            folderDir.mkdirs();
        }
        return folderDir;
    }

    /**
     *获取二级文件夹路径
     * */
    public static File getSubFolder(File file, String folderName){
        File folderDir = new File(file, folderName);
        return folderDir;
    }

    /**
     *生成二级文件夹路径
     * */
    public static File makeSubFolder(File file, String folderName){
        File folderDir = null;
        folderDir = new File(file, folderName);
        if(!folderDir.exists()){
            folderDir.mkdirs();
        }
        return folderDir;
    }




//===================================================================================================
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

//==pcm相关=================================================================================================
    private static String rootPath = "AudioRecord";
    //原始文件(不能播放)
    private final static String AUDIO_PCM_FOLDER_NAME = "/pcm/";
    //可播放的高质量音频文件
    private final static String AUDIO_WAV_FOLDER_NAME = "/wav/";

//    private static void setRootPath(String rootPath) {
//        FileUtil.rootPath = rootPath;
//    }

    public static String getPcmFileAbsolutePath(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            throw new NullPointerException("fileName isEmpty");
        }
        if (!isSdcardExit()) {
            throw new IllegalStateException("sd card no found");
        }
        String mAudioRawPath = "";
        if (isSdcardExit()) {
            if (!fileName.endsWith(".pcm")) {
                fileName = fileName + ".pcm";
            }
//            String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + AUDIO_PCM_BASEPATH;
//            File baseFolder = getAudioBaseFolder();
            String fileBasePath = getPcmFolder();

            File file = new File(fileBasePath);
            //创建目录
            if (!file.exists()) {
                file.mkdirs();
            }
//            mAudioRawPath = fileBasePath + fileName;
            mAudioRawPath = new File(fileBasePath, fileName).toString();
        }
        return mAudioRawPath;
    }

    public static String getWavFileAbsolutePath(String fileName) {
        if (fileName == null) {
            throw new NullPointerException("fileName can't be null");
        }
        if (!isSdcardExit()) {
            throw new IllegalStateException("sd card no found");
        }

        String mAudioWavPath = "";
        if (isSdcardExit()) {
            if (!fileName.endsWith(".wav")) {
                fileName = fileName + ".wav";
            }
//            String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + AUDIO_WAV_BASEPATH;
//            File file1 = getAudioBaseFolder();
            String fileBasePath = getWavFolder();

            File file = new File(fileBasePath);
            //创建目录
            if (!file.exists()) {
                file.mkdirs();
            }
            mAudioWavPath = fileBasePath + fileName;
        }
        return mAudioWavPath;
    }

    /**
     * 判断是否有外部存储设备sdcard
     *
     * @return true | false
     */
    public static boolean isSdcardExit() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取全部pcm文件列表
     *
     * @return
     */
    public static List<File> getPcmFiles() {
        List<File> list = new ArrayList<>();
//        String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + AUDIO_PCM_BASEPATH;
//        File baseFolder = getAudioBaseFolder();
        String fileBasePath = getPcmFolder();

        File rootFile = new File(fileBasePath);
        if (rootFile.exists()) {
            File[] files = rootFile.listFiles();
            for (File file : files) {
                list.add(file);
            }
        }
        return list;

    }

    /**
     * 获取全部wav文件列表
     *
     * @return
     */
    public static List<File> getWavFiles() {
        List<File> list = new ArrayList<>();
//        String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + AUDIO_WAV_BASEPATH;
//        File baseFolder = getAudioBaseFolder();
        String fileBasePath = getWavFolder();

        File rootFile = new File(fileBasePath);
        if (!rootFile.exists()) {
        } else {
            File[] files = rootFile.listFiles();
            for (File file : files) {
                list.add(file);
            }

        }
        return list;
    }

    //--------------
    @NotNull
    private static File getAudioBaseFolder() {
        return FileUtil.makeExternalFolder(rootPath);
    }

    public static String getPcmFolder(){
        File baseFolder = getAudioBaseFolder();
        return baseFolder + AUDIO_PCM_FOLDER_NAME;
    }

    public static String getWavFolder(){
        File baseFolder = getAudioBaseFolder();
        return baseFolder + AUDIO_WAV_FOLDER_NAME;
    }



//===================================================================================================

}

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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    //采用自己的格式去设置文件，防止文件被系统文件查询到 todo:read 后面改成我的
    public static final String SUFFIX_NB = ".nb";
    public static final String SUFFIX_TXT = ".txt";
    public static final String SUFFIX_EPUB = ".epub";
    public static final String SUFFIX_PDF = ".pdf";

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
//        folderDir = context.getExternalFilesDirs(folderName);
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

//==todo:read 记得整理下==============
    //获取Cache文件夹
    public static String getCachePath(){
        if (isSdCardExist()){
            return MyApplication.getContext()
                    .getExternalCacheDir()
                    .getAbsolutePath();
        }else{
            return MyApplication.getContext()
                    .getCacheDir()
                    .getAbsolutePath();
        }
    }

    //判断是否挂载了SD卡
    public static boolean isSdCardExist(){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            return true;
        }
        return false;
    }

    //获取文件的编码格式
    public static Charset getCharset(String fileName) {
        BufferedInputStream bis = null;
        Charset charset = Charset.GBK;
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            bis = new BufferedInputStream(new FileInputStream(fileName));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = Charset.UTF8;
                checked = true;
            }
            /*
             * 不支持 UTF16LE 和 UTF16BE
            else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = Charset.UTF16LE;
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = Charset.UTF16BE;
                checked = true;
            } else */

            bis.mark(0);
            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80 - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = Charset.UTF8;
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtil.closeIO(bis);
        }
        return charset;
    }

    /**
     * 本来是获取File的内容的。但是为了解决文本缩进、换行的问题
     * 这个方法就是专门用来获取书籍的...
     *
     * 应该放在BookRepository中。。。
     * @param file
     * @return
     */
    public static String getFileContent(File file){
        Reader reader = null;
        String str = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            while ((str = br.readLine()) != null){
                //过滤空语句
                if (!str.equals("")){
                    //由于sb会自动过滤\n,所以需要加上去
                    sb.append("    "+str+"\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            FileUtil.closeIO(reader);
        }
        return sb.toString();
    }

    //获取文件
    public static synchronized File getFile(String filePath){
        File file = new File(filePath);
        try {
            if (!file.exists()){
                //创建父类文件夹
                getFolder(file.getParent());
                //创建文件
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    //获取文件夹
    public static File getFolder(String filePath){
        File file = new File(filePath);
        //如果文件夹不存在，就创建它
        if (!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    public static long getDirSize(File file){
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                long size = 0;
                for (File f : children){
                    size += getDirSize(f);
                }
                return size;
            } else {
                return file.length();
            }
        } else {
            return 0;
        }
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

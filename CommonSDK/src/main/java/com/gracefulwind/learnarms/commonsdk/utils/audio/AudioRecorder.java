package com.gracefulwind.learnarms.commonsdk.utils.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.text.TextUtils;
//import android.provider.VoicemailContract.Status;

import com.gracefulwind.learnarms.commonsdk.utils.FileUtil;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: RecordHelper
 * @Author: Gracefulwind
 * @CreateDate: 2021/12/27
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/12/27
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class AudioRecorder {
    public static final String TAG = "RecordHelper";

//==default setting ================================================================================================
    //音频源 MIC指的是麦克风
    private final static int AUDIO_INPUT = MediaRecorder.AudioSource.MIC;
    //（MediaRecoder 的采样率通常是8000Hz AAC的通常是44100Hz。 设置采样率为44100，讯飞要求的是16K的，官方文档表示这个值可以兼容所有的设置）
    private final static int AUDIO_SAMPLE_RATE = 16000; //采样率
    //输入声道
    private final static int AUDIO_CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    //指定音频量化位数 ,在AudioFormaat类中指定了以下各种可能的常量。通常我们选择ENCODING_PCM_16BIT和ENCODING_PCM_8BIT PCM代表的是脉冲编码调制，它实际上是原始音频样本。
    //因此可以设置每个样本的分辨率为16位或者8位，16位将占用更多的空间和处理能力,表示的音频也更加接近真实
    private final static int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

//==fields ================================================================================================
    // 缓冲区字节大小
    private int bufferSizeInBytes = 0;
    //录音对象
    private AudioRecord audioRecord;
    //录音状态
    private Status status = Status.STATUS_NO_READY;
    //文件名
    private String fileName;
    //录音文件
    private List<String> fileNameList = new ArrayList<>();

    //==============================================================================================

    private static AudioRecorder instance = null;
    public static AudioRecorder getInstance() {
        if(null == instance) {
            synchronized (AudioRecorder.class){
                if(null == instance){
                    instance = new AudioRecorder();
                }
            }
        }

        return instance;
    }

    private AudioRecorder() {
        //考虑用builder创建
    }

    //==============================================================================================
    /**
     * 创建录音对象
     */
    public void createAudio(String fileName, int audioSource, int sampleRateInHz, int channelConfig, int audioFormat) {
        checkInitStatus();
        // 获得缓冲区字节大小
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,
                channelConfig, channelConfig);
        audioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes);
        this.fileName = fileName;
    }

    /**
     * 创建默认的录音对象
     *
     * @param fileName 文件名
     */
    public void createDefaultAudio(String fileName) {
        checkInitStatus();
        // 获得缓冲区字节大小
        bufferSizeInBytes = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE,
                AUDIO_CHANNEL, AUDIO_ENCODING);
        audioRecord = new AudioRecord(AUDIO_INPUT, AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING, bufferSizeInBytes);
        this.fileName = fileName;
        status = Status.STATUS_READY;
    }

    private void checkInitStatus() {
        if (status != Status.STATUS_NO_READY) {
            throw new IllegalArgumentException("请释放当前录音后再初始化");
        }
    }

    /**
     * 开始录音
     *
     * @param listener 音频流的监听
     */
    public synchronized void start(final RecordStreamListener listener) {
        if (status == Status.STATUS_NO_READY || TextUtils.isEmpty(fileName)) {
            throw new IllegalStateException("录音尚未初始化,请检查是否禁止了录音权限~");
        }
        if (status == Status.STATUS_START) {
            throw new IllegalStateException("正在录音");
        }
        LogUtil.d(TAG, "===startRecord===" + audioRecord.getState());
        audioRecord.startRecording();

        new Thread(new Runnable() {
            @Override
            public void run() {
                writeDataToFile(listener);
            }
        }).start();
    }

    /**
     * 暂停录音
     */
    public synchronized void pause() {
        LogUtil.d(TAG, "===pause===");
        if (status != Status.STATUS_START) {
            throw new IllegalStateException("没有在录音");
        } else {
            audioRecord.stop();
            status = Status.STATUS_PAUSE;
        }
    }

    /**
     * 停止录音
     */
    public synchronized List<String> stop() {
        List<String> result;
        LogUtil.d(TAG, "===stop===");
        if (status == Status.STATUS_NO_READY || status == Status.STATUS_READY) {
            throw new IllegalStateException("录音尚未开始");
        } else {
            audioRecord.stop();
            status = Status.STATUS_STOP;
            result = release();
        }
        return result;
    }

    /**
     * 释放资源
     */
    public List<String> release() {
        LogUtil.d(TAG, "===release===");
        List<String> filePaths = new ArrayList<>();
        //假如有暂停录音
        try {
            if (fileNameList.size() > 0) {
                for (String fileName : fileNameList) {
                    filePaths.add(FileUtil.getPcmFileAbsolutePath(fileName));
                }
                //清除
                fileNameList.clear();
//                //将多个pcm文件转化为wav文件
                mergePCMFilesToWAVFile(filePaths);
            } else {
                //这里由于只要录音过filesName.size都会大于0,没录音时fileName为null
                //会报空指针 NullPointerException
                // 将单个pcm文件转化为wav文件
                //LogUtil.d("AudioRecorder", "=====makePCMFileToWAVFile======");
                //makePCMFileToWAVFile();
            }
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }

        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }

        status = Status.STATUS_NO_READY;
        return filePaths;
    }

    /**
     * 取消录音
     */
    public void cancel() {
        fileNameList.clear();
        fileName = null;
        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }
        status = Status.STATUS_NO_READY;
    }

    /**
     * 将音频信息写入文件
     *
     * @param listener 音频流的监听
     */
    private void writeDataToFile(RecordStreamListener listener) {
        // new一个byte数组用来存一些字节数据，大小为缓冲区大小
        byte[] audioData = new byte[bufferSizeInBytes];

        FileOutputStream fos = null;
        int readSize = 0;
        try {
            String currentFileName = fileName;
            if (status == Status.STATUS_PAUSE) {
                //假如是暂停录音 将文件名后面加个数字,防止重名文件内容被覆盖
                currentFileName += fileNameList.size();
            }
            fileNameList.add(currentFileName);
            File file = new File(FileUtil.getPcmFileAbsolutePath(currentFileName));
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);// 建立一个可存取字节的文件
        } catch (IllegalStateException e) {
            LogUtil.d(TAG, e.getMessage());
            throw new IllegalStateException(e.getMessage());
        } catch (FileNotFoundException e) {
            LogUtil.d(TAG, e.getMessage());
        }
        //将录音状态设置成正在录音状态
        status = Status.STATUS_START;
        while (status == Status.STATUS_START) {
            readSize = audioRecord.read(audioData, 0, bufferSizeInBytes);
            if (AudioRecord.ERROR_INVALID_OPERATION != readSize && fos != null) {
                try {
                    fos.write(audioData);
                    fos.flush();
                    if (listener != null) {
                        //用于拓展业务
                        listener.recordOfByte(audioData, 0, audioData.length);
                    }
                } catch (IOException e) {
                    LogUtil.d(TAG, e.getMessage());
                }
            }
        }
        try {
            if (fos != null) {
                fos.close();// 关闭写入流
            }
        } catch (IOException e) {
            LogUtil.d(TAG, e.getMessage());
        }
    }

    /**
     * 将pcm合并成wav
     *
     * @param filePaths
     */
    private void mergePCMFilesToWAVFile(final List<String> filePaths) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (PcmToWav.mergePCMFilesToWAVFile(filePaths, FileUtil.getWavFileAbsolutePath(fileName))) {
                    //操作成功
                } else {
                    //操作失败
                    LogUtil.d(TAG, "mergePCMFilesToWAVFile fail");
                    throw new IllegalStateException("mergePCMFilesToWAVFile fail");
                }
                fileName = null;
            }
        }).start();
    }

    /**
     * 将单个pcm文件转化为wav文件
     */
    private void makePCMFileToWAVFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (PcmToWav.makePCMFileToWAVFile(FileUtil.getPcmFileAbsolutePath(fileName), FileUtil.getWavFileAbsolutePath(fileName), true)) {
                    //操作成功
                } else {
                    //操作失败
                    LogUtil.d(TAG, "makePCMFileToWAVFile fail");
                    throw new IllegalStateException("makePCMFileToWAVFile fail");
                }
                fileName = null;
            }
        }).start();
    }

    /**
     * 获取录音对象的状态
     *
     * @return
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 获取本次录音文件的个数
     *
     * @return
     */
    public int getPcmFilesCount() {
        return fileNameList.size();
    }

//==================================================================================================
    /**
     * 录音对象的状态
     */
    public enum Status {
        //未开始
        STATUS_NO_READY,
        //预备
        STATUS_READY,
        //录音
        STATUS_START,
        //暂停
        STATUS_PAUSE,
        //停止
        STATUS_STOP
    }

//    private volatile RecordState state = RecordState.IDLE;
//    private AudioRecordThread audioRecordThread;
//
//    private File recordFile = null;
//    private File tmpFile = null;
//    private List<File> files = new ArrayList<>();
//
//    public void start(String filePath, RecordConfig config) {
//        this.currentConfig = config;
//        if (state != RecordState.IDLE) {
//            Logger.e(TAG, "状态异常当前状态： %s", state.name());
//            return;
//        }
//        recordFile = new File(filePath);
//        String tempFilePath = getTempFilePath();
//        Logger.i(TAG, "tmpPCM File: %s", tempFilePath);
//        tmpFile = new File(tempFilePath);
//        audioRecordThread = new AudioRecordThread();
//        audioRecordThread.start();
//    }
//
//    public void stop() {
//        if (state == RecordState.IDLE) {
//            Logger.e(TAG, "状态异常当前状态： %s", state.name());
//            return;
//        }
//
//        //若在暂停中直接停止，则直接合并文件即可
//        if (state == RecordState.PAUSE) {
//            makeFile();
//            state = RecordState.IDLE;
//        } else {
//            state = RecordState.STOP;
//        }
//    }
//
//    public void pause() {
//        if (state != RecordState.RECORDING) {
//            Logger.e(TAG, "状态异常当前状态： %s", state.name());
//            return;
//        }
//        state = RecordState.PAUSE;
//    }
//
//    public void resume() {
//        if (state != RecordState.PAUSE) {
//            Logger.e(TAG, "状态异常当前状态： %s", state.name());
//            return;
//        }
//        String tempFilePath = getTempFilePath();
//        Logger.i(TAG, "tmpPCM File: %s", tempFilePath);
//        tmpFile = new File(tempFilePath);
//        audioRecordThread = new AudioRecordThread();
//        audioRecordThread.start();
//    }
//
//    private class AudioRecordThread extends Thread {
//        private AudioRecord audioRecord;
//        private int bufferSize;
//
//        AudioRecordThread() {
//            bufferSize = AudioRecord.getMinBufferSize(currentConfig.getFrequency(),
//                    currentConfig.getChannel(), currentConfig.getEncoding()) * RECORD_AUDIO_BUFFER_TIMES;
//            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, currentConfig.getFrequency(),
//                    currentConfig.getChannel(), currentConfig.getEncoding(), bufferSize);
//        }
//
//        @Override
//        public void run() {
//            super.run();
//            state = RecordState.RECORDING;
//            notifyState();
//            Logger.d(TAG, "开始录制");
//            FileOutputStream fos = null;
//            try {
//                fos = new FileOutputStream(tmpFile);
//                audioRecord.startRecording();
//                byte[] byteBuffer = new byte[bufferSize];
//
//                while (state == RecordState.RECORDING) {
//                    int end = audioRecord.read(byteBuffer, 0, byteBuffer.length);
//                    fos.write(byteBuffer, 0, end);
//                    fos.flush();
//                }
//                audioRecord.stop();
//                //1. 将本次录音的文件暂存下来，用于合并
//                files.add(tmpFile);
//                //2. 再此判断终止循环的状态是暂停还是停止，并做相应处理
//                if (state == RecordState.STOP) {
//                    makeFile();
//                } else {
//                    Logger.i(TAG, "暂停！");
//                }
//            } catch (Exception e) {
//                Logger.e(e, TAG, e.getMessage());
//            } finally {
//                try {
//                    if (fos != null) {
//                        fos.close();
//                    }
//                } catch (IOException e) {
//                    Logger.e(e, TAG, e.getMessage());
//                }
//            }
//            if (state != RecordState.PAUSE) {
//                state = RecordState.IDLE;
//                notifyState();
//                Logger.d(TAG, "录音结束");
//            }
//        }
//    }
//
//    private void makeFile() {
//        //合并文件
//        boolean mergeSuccess = mergePcmFiles(recordFile, files);
//
//        //TODO:转换wav
//        Logger.i(TAG, "录音完成！ path: %s ； 大小：%s", recordFile.getAbsoluteFile(), recordFile.length());
//    }
//
//    /**
//     * 合并Pcm文件
//     *
//     * @param recordFile 输出文件
//     * @param files      多个文件源
//     * @return 是否成功
//     */
//    private boolean mergePcmFiles(File recordFile, List<File> files) {
//        if (recordFile == null || files == null || files.size() <= 0) {
//            return false;
//        }
//
//        FileOutputStream fos = null;
//        BufferedOutputStream outputStream = null;
//        byte[] buffer = new byte[1024];
//        try {
//            fos = new FileOutputStream(recordFile);
//            outputStream = new BufferedOutputStream(fos);
//
//            for (int i = 0; i < files.size(); i++) {
//                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(files.get(i)));
//                int readCount;
//                while ((readCount = inputStream.read(buffer)) > 0) {
//                    outputStream.write(buffer, 0, readCount);
//                }
//                inputStream.close();
//            }
//        } catch (Exception e) {
//            Logger.e(e, TAG, e.getMessage());
//            return false;
//        } finally {
//            try {
//                if (fos != null) {
//                    fos.close();
//                }
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        //3. 合并后记得删除缓存文件并清除list
//        for (int i = 0; i < files.size(); i++) {
//            files.get(i).delete();
//        }
//        files.clear();
//        return true;
//    }

}

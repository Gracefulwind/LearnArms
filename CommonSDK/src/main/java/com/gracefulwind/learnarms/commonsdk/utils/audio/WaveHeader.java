package com.gracefulwind.learnarms.commonsdk.utils.audio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @ClassName: WaveHeader
 * @Author: Gracefulwind
 * @CreateDate: 2021/12/28
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/12/28
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class WaveHeader {
    //‘RIFF‘文件标志, 4, String
    public final char fileID[] = {'R', 'I', 'F', 'F'};
    //文件总长, 4, Integer
    public int fileLength;
    //‘WAVE‘文件标志, 4, String
    public char wavTag[] = {'W', 'A', 'V', 'E'};;
    //‘fmt‘标志, 4, String
    public char fmtHdrID[] = {'f', 'm', 't', ' '};
    //块长度, 4 Integer  ???
    public int fmtHdrLength;
    //PCM格式类别, 2, Short
    public short formatTag;
    //声道数目, 2,
    public short channels;
    //采样率, 4,
    public int samplesPerSec;
    //码率, 4, 采样率x采样深度x通道数/8 比如双通道的44.1K 16位采样的码率为176400
    public int avgBytesPerSec;
    //数据块对齐, 2, 采样一次，占内存大小 ： 采样深度x通道数/8
    public short blockAlign;
    //每样本bit数, 2, 采样深度 ? 16bit?
    public short bitsPerSample;

    //‘data‘文件标志, 4, 表述payload数据开头
    public char dataHdrID[] = {'d','a','t','a'};
    //数据块总长, 4
    public int dataHdrLength;

    public byte[] getHeader() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WriteChar(bos, fileID);
        WriteInt(bos, fileLength);
        WriteChar(bos, wavTag);
        WriteChar(bos, fmtHdrID);
        WriteInt(bos, fmtHdrLength);
        WriteShort(bos, formatTag);
        WriteShort(bos, channels);
        WriteInt(bos, samplesPerSec);
        WriteInt(bos, avgBytesPerSec);
        WriteShort(bos, blockAlign);
        WriteShort(bos, bitsPerSample);
        WriteChar(bos, dataHdrID);
        WriteInt(bos, dataHdrLength);
        bos.flush();
        byte[] r = bos.toByteArray();
        bos.close();
        return r;
    }

    private void WriteShort(ByteArrayOutputStream bos, int s) throws IOException {
        byte[] mybyte = new byte[2];
        mybyte[1] =(byte)( (s << 16) >> 24 );
        mybyte[0] =(byte)( (s << 24) >> 24 );
        bos.write(mybyte);
    }


    private void WriteInt(ByteArrayOutputStream bos, int n) throws IOException {
        byte[] buf = new byte[4];
        buf[3] =(byte)( n >> 24 );
        buf[2] =(byte)( (n << 8) >> 24 );
        buf[1] =(byte)( (n << 16) >> 24 );
        buf[0] =(byte)( (n << 24) >> 24 );
        bos.write(buf);
    }

    private void WriteChar(ByteArrayOutputStream bos, char[] id) {
        for (int i=0; i<id.length; i++) {
            char c = id[i];
            bos.write(c);
        }
    }
}

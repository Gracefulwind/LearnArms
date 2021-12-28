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
    public final char fileID[] = {'R', 'I', 'F', 'F'};
    public int fileLength;
    public char wavTag[] = {'W', 'A', 'V', 'E'};;
    public char fmtHdrID[] = {'f', 'm', 't', ' '};
    public int fmtHdrLength;
    public short formatTag;
    public short channels;
    public int samplesPerSec;
    public int avgBytesPerSec;
    public short blockAlign;
    public short bitsPerSample;
    public char dataHdrID[] = {'d','a','t','a'};
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

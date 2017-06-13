package ir.atitec.lib;

import ir.atitec.lib.models.GoCompressMode;
import ir.atitec.lib.models.GoDataType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by white on 2016-08-06.
 */
public class GoStreamReader {

    public GoStreamReader() {

    }

    public byte[] readBlockToEnd(InputStream inputStream) throws Exception {
        int length = readSize(inputStream);
        byte[] data = read(inputStream, length);
        return data;

    }

    public boolean onTypeAuthenticationResponse(InputStream inputStream) throws Exception {
        byte[] response = read(inputStream, 2);
        return true;
    }

    private int readSize(InputStream inputStream) throws Exception {
        byte[] size = read(inputStream, 4);
        return ByteBuffer.wrap(size).order(ByteOrder.nativeOrder()).getInt();
    }

    private byte readByte(InputStream inputStream) throws Exception {
        return read(inputStream, 1)[0];
    }

    public GoDataType readType(InputStream inputStream) throws Exception {
        byte type = readByte(inputStream);
        return GoDataType.getInstance(type);
    }

    public GoCompressMode readCompressMode(InputStream inputStream) throws Exception {
        byte mode = readByte(inputStream);
        return GoCompressMode.getInstance(mode);
    }

    private byte[] read(InputStream inputStream, int size) throws Exception {
        byte[] bytes = new byte[size];
        int readedLength = 0;
        int remainingLength = size;
        int lastRead = 0;

        while (readedLength < size) {
            int countToRead = Math.min(remainingLength, 2048);
            byte[] byteBuffer = new byte[countToRead];
            lastRead = inputStream.read(byteBuffer, 0, countToRead);
            if (lastRead <= 0) {
                Thread.currentThread().interrupt();
                throw new IOException("readed stream size is " + lastRead);
            }
            bytes = byteAppendHelper(bytes, byteBuffer, lastRead, readedLength);
            readedLength += lastRead;
            remainingLength -= lastRead;
        }
        return bytes;
    }


    private byte[] byteAppendHelper(byte[] dest, byte[] src, int lenght, int pos) {

        System.arraycopy(src, 0, dest, pos, lenght);

        return dest;
    }

    private byte[] readHelper(InputStream inputStream, int len) throws IOException {
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            int t = inputStream.read(result, 0, len);
            result[i] = (byte) t;
        }
        return result;
    }
}

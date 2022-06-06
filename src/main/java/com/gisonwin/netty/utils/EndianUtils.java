package com.gisonwin.netty.utils;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2021/7/15 17:42
 */
public final class EndianUtils {

    /**
     * 将大端int值转为小端int值。
     */

    public static int bigEndianIntToLittleDianInt(int bigEndianInt) {
        byte[] bytes = intToByteBigEndian(bigEndianInt);
        return bytes2IntLittleEndian(bytes);
    }

    /**
     * 将大端short值转为小端short值。
     */

    public static short bigEndianShortToLittleDianShort(short bigEndianShort) {
        byte[] bytes = shortToByteBigEndian(bigEndianShort);
        return byteToShortLittleEndian(bytes);
    }

    /**
     * 将大端long值转为小端long值。
     */

    public static long bigEndianLongToLittleDianLong(long bigEndianLong) {
        byte[] bytes = longToBytesBigEndian(bigEndianLong);
        return bytesToLongLittleEndian(bytes);
    }


    /**
     * 将int转为高字节在前，低字节在后的byte数组（大端）
     *
     * @param n int
     * @return byte[]
     */
    public static byte[] intToByteBigEndian(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * 将int转为低字节在前，高字节在后的byte数组（小端）
     *
     * @param n int
     * @return byte[]
     */
    public static byte[] intToByteLittleEndian(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * byte数组到int的转换(小端)
     *
     * @param bytes
     * @return
     */
    public static int bytes2IntLittleEndian(byte[] bytes) {
        int int1 = bytes[0] & 0xff;
        int int2 = (bytes[1] & 0xff) << 8;
        int int3 = (bytes[2] & 0xff) << 16;
        int int4 = (bytes[3] & 0xff) << 24;

        return int1 | int2 | int3 | int4;
    }

    /**
     * byte数组到int的转换(大端)
     *
     * @param bytes
     * @return
     */
    public static int bytes2IntBigEndian(byte[] bytes) {
        int int1 = bytes[3] & 0xff;
        int int2 = (bytes[2] & 0xff) << 8;
        int int3 = (bytes[1] & 0xff) << 16;
        int int4 = (bytes[0] & 0xff) << 24;

        return int1 | int2 | int3 | int4;
    }

    /**
     * 将short转为高字节在前，低字节在后的byte数组（大端）
     *
     * @param n short
     * @return byte[]
     */
    public static byte[] shortToByteBigEndian(short n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 0xff);
        b[0] = (byte) (n >> 8 & 0xff);
        return b;
    }

    /**
     * 将short转为低字节在前，高字节在后的byte数组(小端)
     *
     * @param n short
     * @return byte[]
     */
    public static byte[] shortToByteLittleEndian(short n) {
        byte[] b = new byte[2];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        return b;
    }

    /**
     * 读取小端byte数组为short
     *
     * @param b
     * @return
     */
    public static short byteToShortLittleEndian(byte[] b) {
        return (short) (((b[1] << 8) | b[0] & 0xff));
    }

    /**
     * 读取大端byte数组为short
     *
     * @param b
     * @return
     */
    public static short byteToShortBigEndian(byte[] b) {
        return (short) (((b[0] << 8) | b[1] & 0xff));
    }

    /**
     * long类型转byte[] (大端)
     *
     * @param n
     * @return
     */
    public static byte[] longToBytesBigEndian(long n) {
        byte[] b = new byte[8];
        b[7] = (byte) (n & 0xff);
        b[6] = (byte) (n >> 8 & 0xff);
        b[5] = (byte) (n >> 16 & 0xff);
        b[4] = (byte) (n >> 24 & 0xff);
        b[3] = (byte) (n >> 32 & 0xff);
        b[2] = (byte) (n >> 40 & 0xff);
        b[1] = (byte) (n >> 48 & 0xff);
        b[0] = (byte) (n >> 56 & 0xff);
        return b;
    }

    /**
     * long类型转byte[] (小端)
     *
     * @param n
     * @return
     */
    public static byte[] longToBytesLittleEndian(long n) {
        byte[] b = new byte[8];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        b[4] = (byte) (n >> 32 & 0xff);
        b[5] = (byte) (n >> 40 & 0xff);
        b[6] = (byte) (n >> 48 & 0xff);
        b[7] = (byte) (n >> 56 & 0xff);
        return b;
    }

    public static byte[] BigEndian16BytesToLittleEndianBytes(byte[] data) {
        if (data.length != 16) return null;
        byte[] b = new byte[16];
        b[0] = (byte) (data[15] & 0xff);
        b[1] = (byte) ((data[14] & 0xff));
        b[2] = (byte) ((data[13] & 0xff));
        b[3] = (byte) ((data[12] & 0xff));
        b[4] = (byte) ((data[11] & 0xff));
        b[5] = (byte) ((data[10] & 0xff));
        b[6] = (byte) ((data[9] & 0xff));
        b[7] = (byte) ((data[8] & 0xff));
        b[8] = (byte) ((data[7] & 0xff));
        b[9] = (byte) ((data[6] & 0xff));
        b[10] = (byte) ((data[5] & 0xff));
        b[11] = (byte) ((data[4] & 0xff));
        b[12] = (byte) ((data[3] & 0xff));
        b[13] = (byte) ((data[2] & 0xff));
        b[14] = (byte) ((data[1] & 0xff));
        b[15] = (byte) ((data[0] & 0xff));
        return b;
    }


    /**
     * byte[]转long类型(小端)
     *
     * @param array
     * @return
     */
    public static long bytesToLongLittleEndian(byte[] array) {
        return ((((long) array[0] & 0xff) << 0)
                | (((long) array[1] & 0xff) << 8)
                | (((long) array[2] & 0xff) << 16)
                | (((long) array[3] & 0xff) << 24)
                | (((long) array[4] & 0xff) << 32)
                | (((long) array[5] & 0xff) << 40)
                | (((long) array[6] & 0xff) << 48)
                | (((long) array[7] & 0xff) << 56));
    }

    /**
     * byte[]转long类型(大端)
     *
     * @param array
     * @return
     */
    public static long bytesToLongBigEndian(byte[] array) {
        return ((((long) array[0] & 0xff) << 56)
                | (((long) array[1] & 0xff) << 48)
                | (((long) array[2] & 0xff) << 40)
                | (((long) array[3] & 0xff) << 32)
                | (((long) array[4] & 0xff) << 24)
                | (((long) array[5] & 0xff) << 16)
                | (((long) array[6] & 0xff) << 8)
                | (((long) array[7] & 0xff) << 0));
    }

}


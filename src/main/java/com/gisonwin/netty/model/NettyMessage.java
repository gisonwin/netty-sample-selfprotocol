package com.gisonwin.netty.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description 序号    字段名称	字节数	备注 <br>
 * 1.	同步字	无符号整数（4字节）	0xCBA98765 <br>
 * 2.	数据包长度	无符号整数（4字节）	包含所有字段的字节长度 <br>
 * 3.   主版本号 无符号整数 (2字节)<br>
 * 4.   次版本号 无符号整数 (2字节)<br>
 * 5.	数据包类型	无符号整数（1字节）	一个类型对应一个ProtoBuf消息，ProtoBuf消息根据业务定义，编号从1开始 <br>
 * 6.	序列化类型	无符号整数（1字节） 1 protobuf 2 thrift 3.json 4 xml 5 java serializable etc..可以扩展<br>
 * 7.	数据包唯一标识	16字节	GUID表示的数据块，用于数据包追踪 <br>
 * 8.	消息体(消息序列化数据块)	数据块（N字节）	长度由数据包类型决定 <br>
 * @Date 2022/6/5 13:50
 */
public class NettyMessage implements Serializable {

    //包头 header
    private int magicNumber;//magic number 4
    private int length; //4
    private short majorVersion;//主版本号 2
    private short minorVersion;//次版本号 2
    private byte kind; //数据包类型 1
    private byte serialize;//序列化类型 1
    private byte[] guid; //16 bytes ,数据包唯一标识
    //以下为包体部分 body
    private byte[] byteData;//包体 n bytes


    public int getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    public short getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(short majorVersion) {
        this.majorVersion = majorVersion;
    }

    public short getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(short minorVersion) {
        this.minorVersion = minorVersion;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte getKind() {
        return kind;
    }

    public void setKind(byte kind) {
        this.kind = kind;
    }

    public byte getSerialize() {
        return serialize;
    }

    public void setSerialize(byte serialize) {
        this.serialize = serialize;
    }

    public byte[] getGuid() {
        return guid;
    }

    public void setGuid(byte[] guid) {
        this.guid = guid;
    }

    public byte[] getByteData() {
        return byteData;
    }

    public void setByteData(byte[] byteData) {
        this.byteData = byteData;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "magicNumber=" + magicNumber +
                ", length=" + length +
                ", majorVersion=" + majorVersion +
                ", minorVersion=" + minorVersion +
                ", kind=" + kind +
                ", serialize=" + serialize +
                ", guid=" + Arrays.toString(guid) +
                ", guid string =" + new String(guid) +
                ", byteData=" + Arrays.toString(byteData) +
                ", byteData string =" + new String(byteData) +
                '}';
    }
}

package com.gisonwin.netty.codec;

import com.gisonwin.netty.model.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.gisonwin.netty.CONSTANTS.MAGIC_NUMBER;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/5 16:30
 */
public class MessageDecoder extends ReplayingDecoder<Void> {
    static final Logger log = LoggerFactory.getLogger(MessageDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        int BASE_LENGTH = 4 + 4; //HEADER+length 4+4
        int readableBytes = in.readableBytes();
        if (readableBytes > BASE_LENGTH) {  //HEADER+length 4+4 ,可读字节要大于header和包体长度才会解码
            //读取Header
            int beginReader;
            //一直读数据,直到讲到的数据是header以后,再向后读取数据
            int header;
            while (true) {
                beginReader = in.readerIndex();
                in.markReaderIndex();//标记包头开始的位置
                int readInt = in.readIntLE();

                if ((header = readInt) == MAGIC_NUMBER) {
                    //读到了header, 开始读后面的数据
                    break;
                }
                in.resetReaderIndex();//重置reader index
                in.readByte();
                // 未读到包头，略过一个字节
                // 每次略过，一个字节，去读取，包头信息的开始标记
                // 当略过，一个字节之后，
                // 数据包的长度，又变得不满足
                // 此时，应该结束。等待后面的数据到达
                if (in.readableBytes() < BASE_LENGTH) {
                    return;
                }
            }
            //判断header是否正确 读取了同步字
            log.info("read HEADER is " + header);
            //读包体长度 LE是小端 little endian
            int length = in.readIntLE();
            log.info("package length =" + length);
            //读取主版本号
            short majorVersion = in.readShortLE();
            //读取次版本号
            short minorVersion = in.readShortLE();
            //读包类型
            byte kind = in.readByte();
            //读取序列化类型,默认为protobuf
            byte serialize = in.readByte();

            //guid 16 bytes
            byte[] guid = new byte[16];
            in.readBytes(guid);

            int dataLength = length - BASE_LENGTH;//后续数据的长度
            // 判断请求数据包数据是否到齐
            int readableBytes1 = in.readableBytes();
            if (readableBytes1 < dataLength) {
                // 还原读指针
                in.readerIndex(beginReader);
                log.error("数据包不完整,{},{}", readableBytes1, dataLength);
                return;
            }
            //读取数据
            NettyMessage protocol = new NettyMessage();
            protocol.setMagicNumber(MAGIC_NUMBER);
            protocol.setKind(kind);
            protocol.setLength(length);
            protocol.setMajorVersion(majorVersion);
            protocol.setMinorVersion(minorVersion);
            protocol.setSerialize(serialize);
            protocol.setGuid(guid);
            //data
            byte[] data = new byte[dataLength - 22];
            in.readBytes(data);//将后续内容读入content 数组中
            log.info("decoder content = " + new String(data, CharsetUtil.UTF_8));
            protocol.setByteData(data);

            out.add(protocol);
        }
    }
}

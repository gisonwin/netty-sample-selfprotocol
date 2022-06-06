package com.gisonwin.netty.codec;

import com.gisonwin.netty.model.NettyMessage;
import com.gisonwin.netty.utils.EndianUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/5 17:28
 */
public class MessageEncoder extends MessageToByteEncoder<NettyMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMessage msg, ByteBuf out) throws Exception {
        out.writeIntLE(msg.getMagicNumber());//同步字
        out.writeIntLE(msg.getLength());//长度
        out.writeShortLE(msg.getMajorVersion());//主版本号
        out.writeShortLE(msg.getMinorVersion());//次版本号
        out.writeByte(msg.getKind());//包类型
        out.writeByte(msg.getSerialize());//序列化方式
        //将guid byte array 修改为小端 写出去
        byte[] data = EndianUtils.BigEndian16BytesToLittleEndianBytes(msg.getGuid());
        out.writeBytes(data);//guid little endian
        out.writeBytes(msg.getByteData());//写的proto buff的字节流
    }
}

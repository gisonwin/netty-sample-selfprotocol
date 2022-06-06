package com.gisonwin.netty.strategy.impl;

import com.gisonwin.netty.CONSTANTS;
import com.gisonwin.netty.model.NettyMessage;
import com.gisonwin.netty.strategy.MsgKindStrategy;
import com.gisonwin.protobuf.CommonQuery;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.gisonwin.netty.CONSTANTS.*;
import static com.gisonwin.netty.utils.EndianUtils.BigEndian16BytesToLittleEndianBytes;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/5 23:24
 */
@Service("" + VOLTAGE_REQ)
public class VoltageQuery implements MsgKindStrategy {
    @Override
    public void handle(ChannelHandlerContext ctx, NettyMessage nm) throws IOException {

        CommonQuery.voltageQueryReq req = CommonQuery.voltageQueryReq.parseFrom(nm.getByteData());
        String sequenceID = req.getSequenceID();
        String deviceID = req.getDeviceID();
        logger.debug("get sequence id== {} device id== {}", sequenceID, deviceID);
        // mock handle logic
        CommonQuery.voltageQueryResp build = CommonQuery.voltageQueryResp.newBuilder().setSequenceID(sequenceID).setDeviceID(deviceID)
                .setVoltage("370").build();
        byte[] data = build.toByteArray();
        NettyMessage msg = new NettyMessage();
        msg.setMagicNumber(CONSTANTS.MAGIC_NUMBER);
        msg.setMajorVersion((short) 1);
        msg.setMinorVersion((short) 0);
        msg.setSerialize((byte) 1);//指明序列化为protobuf
        msg.setKind(VOLTAGE_RESP);
        msg.setGuid(BigEndian16BytesToLittleEndianBytes(sequenceID.getBytes(StandardCharsets.UTF_8)));
        msg.setLength(data.length + 30);
        msg.setByteData(data);
        ctx.writeAndFlush(msg);
        logger.debug("voltage query send success");
    }
}

package com.gisonwin.netty.strategy.impl;

import com.gisonwin.netty.CONSTANTS;
import com.gisonwin.netty.model.NettyMessage;
import com.gisonwin.netty.strategy.MsgKindStrategy;
import com.gisonwin.protobuf.CommonQuery;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.gisonwin.netty.CONSTANTS.TEMPERATURE_REQ;
import static com.gisonwin.netty.CONSTANTS.TEMPERATURE_RESP;
import static com.gisonwin.netty.utils.EndianUtils.BigEndian16BytesToLittleEndianBytes;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/5 23:24
 */
@Service("" + TEMPERATURE_REQ)
public class TemperaturQuery implements MsgKindStrategy {
    @Override
    public void handle(ChannelHandlerContext ctx, NettyMessage nm) throws IOException {

        CommonQuery.temperatureQueryReq req = CommonQuery.temperatureQueryReq.parseFrom(nm.getByteData());
        String sequenceID = req.getSequenceID();
        String deviceID = req.getDeviceID();
        logger.debug("get sequence id== {} device id== {}", sequenceID, deviceID);
        // mock handle logic
        CommonQuery.temperatureQueryResp build = CommonQuery.temperatureQueryResp.newBuilder().setSequenceID(sequenceID).setDeviceID(deviceID)
                .setTemperature("-11.5").build();
        byte[] data = build.toByteArray();
        NettyMessage msg = new NettyMessage();
        msg.setMagicNumber(CONSTANTS.MAGIC_NUMBER);
        msg.setMajorVersion((short) 1);
        msg.setMinorVersion((short) 0);
        msg.setSerialize((byte) 1);//指明序列化为protobuf
        msg.setKind(TEMPERATURE_RESP);
        msg.setGuid(BigEndian16BytesToLittleEndianBytes(sequenceID.getBytes(StandardCharsets.UTF_8)));
        msg.setLength(data.length + 30);
        msg.setByteData(data);
        ctx.writeAndFlush(msg);
        logger.debug("temperature query send success");
    }
}

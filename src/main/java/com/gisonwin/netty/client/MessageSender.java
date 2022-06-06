package com.gisonwin.netty.client;


import com.gisonwin.netty.model.NettyMessage;
import com.gisonwin.netty.utils.EndianUtils;
import com.gisonwin.protobuf.CommonQuery;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.gisonwin.netty.CONSTANTS.*;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/5 11:18
 */
public class MessageSender implements Runnable {
    static final Logger logger = LoggerFactory.getLogger(MessageSender.class);
    AtomicLong counter = new AtomicLong(1);
    Channel channel;

    public MessageSender(Channel ch) {
        this.channel = ch;
    }

    private static void testTemperatureQuery(String guid, Channel channel) {
        NettyMessage nm = new NettyMessage();
        nm.setMagicNumber(MAGIC_NUMBER);
        nm.setMajorVersion((short) 1);
        nm.setMinorVersion((short) 0);
        nm.setKind(TEMPERATURE_REQ);
        nm.setSerialize((byte) 1);
        nm.setGuid(EndianUtils.BigEndian16BytesToLittleEndianBytes(guid.getBytes()));
        CommonQuery.temperatureQueryReq build = CommonQuery.temperatureQueryReq.newBuilder().setSequenceID(guid).setDeviceID("X-Power").build();
        byte[] data = build.toByteArray();
        nm.setLength(30 + data.length);
        nm.setByteData(data);
        channel.writeAndFlush(nm);
    }

    private static void testCurrentQuery(String guid, Channel channel) {
        NettyMessage nm = new NettyMessage();
        nm.setMagicNumber(MAGIC_NUMBER);
        nm.setMajorVersion((short) 1);
        nm.setMinorVersion((short) 0);
        nm.setKind(CURRENT_REQ);
        nm.setSerialize((byte) 1);
        nm.setGuid(EndianUtils.BigEndian16BytesToLittleEndianBytes(guid.getBytes()));
        CommonQuery.currentQueryReq build = CommonQuery.currentQueryReq.newBuilder().setSequenceID(guid).setDeviceID("X-Power").build();
        byte[] data = build.toByteArray();
        nm.setLength(30 + data.length);
        nm.setByteData(data);
        channel.writeAndFlush(nm);
    }

    private static void testVoltageQuery(String guid, Channel channel) {
        NettyMessage nm = new NettyMessage();
        nm.setMagicNumber(MAGIC_NUMBER);
        nm.setMajorVersion((short) 1);
        nm.setMinorVersion((short) 0);
        nm.setKind(VOLTAGE_REQ);
        nm.setSerialize((byte) 1);
        nm.setGuid(EndianUtils.BigEndian16BytesToLittleEndianBytes(guid.getBytes()));
        CommonQuery.voltageQueryReq build = CommonQuery.voltageQueryReq.newBuilder().setSequenceID(guid).setDeviceID("X-Power").build();
        byte[] data = build.toByteArray();
        nm.setLength(30 + data.length);
        nm.setByteData(data);
        channel.writeAndFlush(nm);
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        String guid = "123456789ABCDEF0";
        while (true) {
            try {
                //每发送100次 随机sleep 0~3间秒
                if (counter.get() % 100 == 0) TimeUnit.SECONDS.sleep(new Random().nextInt(3));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //测试温度查询
            testTemperatureQuery(guid, channel);
            testCurrentQuery(guid, channel);
            testVoltageQuery(guid, channel);
            if (counter.get() == 3) break;
        }
    }
}

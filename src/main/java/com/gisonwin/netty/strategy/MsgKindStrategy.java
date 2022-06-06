package com.gisonwin.netty.strategy;

import com.gisonwin.netty.model.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 根据消息类型的策略接口定义。
 *
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/5 13:54
 */
public interface MsgKindStrategy {
    static final Logger logger = LoggerFactory.getLogger(MsgKindStrategy.class);
    void handle(ChannelHandlerContext ctx, NettyMessage nm) throws IOException;
}

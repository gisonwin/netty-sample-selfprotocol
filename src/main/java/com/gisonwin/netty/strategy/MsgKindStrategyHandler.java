package com.gisonwin.netty.strategy;

import com.gisonwin.netty.model.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/5 14:52
 */
@Service
public class MsgKindStrategyHandler {
    static final Logger logger = LoggerFactory.getLogger(MsgKindStrategyHandler.class);
    @Autowired
    MsgKindStrategyFactory factory;
    MsgKindStrategy msgKindStrategy;
    public void setMsgKindStrategy(byte kind) {
        logger.info("get msg kind == {}", kind);
        msgKindStrategy = factory.getMsgKindStrategy(String.valueOf(kind));
    }

    public void handle(ChannelHandlerContext ctx, NettyMessage nm) throws IOException {
        if (Objects.isNull(msgKindStrategy)) {
            //TODO 如果是不支持的消息，还没定是否要返回给客户端提示
            logger.error("not support msg kind == {} ", nm.getKind());
            return;
        }
        msgKindStrategy.handle(ctx, nm);
    }
}

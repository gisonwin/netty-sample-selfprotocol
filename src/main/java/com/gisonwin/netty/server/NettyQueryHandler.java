package com.gisonwin.netty.server;

import com.gisonwin.netty.model.NettyMessage;
import com.gisonwin.netty.strategy.MsgKindStrategyHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/5 22:52
 */
@ChannelHandler.Sharable
@Service
public class NettyQueryHandler extends SimpleChannelInboundHandler<NettyMessage> {
    static final Logger logger = LoggerFactory.getLogger(NettyQueryHandler.class);

    @Autowired
    MsgKindStrategyHandler handler;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage nm) throws Exception {
        logger.debug("服务端接收到客户端的消息:{}", nm);
        byte kind = nm.getKind();
        handler.setMsgKindStrategy(kind);
        handler.handle(ctx, nm);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        logger.error(cause.getMessage());
        ctx.fireChannelActive();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        SocketChannel channel = (SocketChannel) ctx.channel();
        String clientIP = channel.remoteAddress().toString().substring(1);
        logger.debug("{} connected by remote {}", channel.localAddress().toString().substring(1), clientIP);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ctx.fireChannelActive();
        ctx.close();
    }
}

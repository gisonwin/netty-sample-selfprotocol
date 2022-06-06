package com.gisonwin.netty.client;

import com.gisonwin.netty.model.NettyMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/5 17:49
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<NettyMessage> {
    static final Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        logger.debug("连接到服务器{}成功,channel id=={}", channel.remoteAddress(), channel.id());
//        executor.execute(new MessageSender(channel));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //TODO 实现心跳
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {

        NettyMessage nm = msg;
        int kind = nm.getKind();
        logger.debug(" 服务器返回消息kind == {},msg== {},ctx=={}", kind, nm, ctx.channel());

    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress address = ((InetSocketAddress) ctx.channel().remoteAddress());
        logger.warn("客户端[ {} ]被服务端[ {} ]断开 @ {}", ctx.channel().localAddress(), address, LocalDateTime.now());
        ctx.fireChannelInactive();
        ctx.close();
    }
}

package com.gisonwin.netty.client;

import com.gisonwin.netty.codec.MessageDecoder;
import com.gisonwin.netty.codec.MessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/5 20:24
 */
public class ClientInitializeHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        //此处日志跟随每次消息收 发时 显示 的日志级别
        p.addLast(new LoggingHandler(LogLevel.DEBUG));
        //编码器
        p.addLast(new MessageEncoder());
        //解码器
        p.addLast(new MessageDecoder());
//      p.addLast(new IdleStateHandler(0, 0, 10, TimeUnit.SECONDS));
        //客户端处理器
        p.addLast(new NettyClientHandler());
    }
}

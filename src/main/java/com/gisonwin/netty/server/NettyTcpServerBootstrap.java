package com.gisonwin.netty.server;

import com.gisonwin.netty.codec.MessageDecoder;
import com.gisonwin.netty.codec.MessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/5 22:42
 */
@Component
public class NettyTcpServerBootstrap {
    static final Logger logger = LoggerFactory.getLogger(NettyTcpServerBootstrap.class);
    @Value("${tcp.server.port}")
    int port;
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    @Resource
    NettyQueryHandler nettyQueryHandler;

    @PostConstruct
    public void start() {

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    //设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //设置NoDelay禁用Nagel,消息会立即发送出去,不用等到一定数量才发送出去
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    //这里的log是boss group的日志级别
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            //这里log handler是跟随worker group的日志级别
                            p.addLast(new LoggingHandler(LogLevel.DEBUG));
                            //解码器
                            p.addLast(new MessageEncoder());
                            //编码器
                            p.addLast(new MessageDecoder());
                            //心跳包
//                            p.addLast(new IdleStateHandler(0, 0, 6, TimeUnit.SECONDS));
                            //处理器
                            p.addLast(nettyQueryHandler);
                        }
                    });
            //启动服务器并绑定一个端口且同步生成一个ChannelFuture对象
            ChannelFuture future = bootstrap.bind(port).sync();
            if (future.isSuccess()) {
                logger.debug("Netty Server 启动并监听端口 {}", port);
            }
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
    }

    @PreDestroy
    public void destroy() {
        bossGroup.shutdownGracefully().syncUninterruptibly();
        workerGroup.shutdownGracefully().syncUninterruptibly();
    }
}

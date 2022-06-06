package com.gisonwin.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:gisonwin@qq.com">Gison.Win</a>
 * @Description
 * @Date 2022/6/5 18:04
 */
public class NettyClientBootstrap {
    static final Logger logger = LoggerFactory.getLogger(NettyClientBootstrap.class);
    private int port;
    private String host;
    public SocketChannel socketChannel;
    EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    public NettyClientBootstrap(int port, String host) throws Exception {
        this.port = port;
        this.host = host;
    }

    private ChannelFuture start() throws Exception {

        Bootstrap bootstrap = new Bootstrap();
        InetSocketAddress address = new InetSocketAddress(host, port);
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .handler(new ClientInitializeHandler());
        ChannelFuture future = bootstrap.connect(address).sync();
        if (future.isSuccess()) {
            socketChannel = (SocketChannel) future.channel();
            logger.info("connect to server {}:{} success", host, port);
            return future;
        }
        return null;
    }

    public void stop() {
        eventLoopGroup.shutdownGracefully();
        if (Objects.nonNull(socketChannel))
            socketChannel.closeFuture();
    }

    public static void main(String[] args) throws Exception {
        int port = 8300;
        String host = "127.0.0.1";
        for (int i = 0; i < 1; i++) {
            new NettyClientBootstrap(port, host).send();
        }
    }

    private void send() throws Exception {
        NettyClientBootstrap bootstrap = new NettyClientBootstrap(port, host);
        ChannelFuture future = null;
        try {
            future = bootstrap.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assert future != null;
        SocketChannel socketChannel = (SocketChannel) future.channel();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new MessageSender(socketChannel));
    }
}

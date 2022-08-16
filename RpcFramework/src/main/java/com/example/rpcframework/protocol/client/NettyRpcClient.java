package com.example.rpcframework.protocol.client;

import com.alibaba.fastjson.JSON;
import com.example.rpcframework.protocol.channel.ChannelProvider;
import com.example.rpcframework.dto.Invocation;
import com.example.rpcframework.dto.Response;
import com.example.rpcframework.register.RegisterService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Shan Cheng
 * @since 2022/8/15
 */

@Slf4j
public class NettyRpcClient {

    private Bootstrap bootstrap;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private ChannelProvider channelProvider;

    public NettyRpcClient() {
        this.start();
    }

    public void start() {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                //  The timeout period of the connection.
                //  If this time is exceeded or the connection cannot be established, the connection fails.
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        // If no data is sent to the server within 15 seconds, a heartbeat request is sent
                        pipeline.addLast(new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS));
                        pipeline.addLast(new NettyClientHandler());
                    }
                });

    }

    public Object invoke(Invocation invocation) {
        InetSocketAddress inetSocketAddress = registerService.findService(invocation);

        Channel channel = getChannel(inetSocketAddress);

        CompletableFuture<Response<Object>> resultFuture = new CompletableFuture<>();
        if (channel.isActive()) {
            channel.writeAndFlush(invocation).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    log.info("invoke success, invocation={}", JSON.toJSONString(invocation));

                } else {
                    future.channel().close();
                    resultFuture.completeExceptionally(future.cause());
                    log.error("invoke failed, invocation={}", JSON.toJSONString(invocation), future.cause());
                }
            });
        } else {
            throw new IllegalStateException();
        }

        return resultFuture;
    }

    @SneakyThrows
    public Channel getChannel(InetSocketAddress inetSocketAddress) {
        Channel channel = channelProvider.getChannel(inetSocketAddress);
        if (channel == null) {
            channel = doConnect(inetSocketAddress);
            channelProvider.setChannel(inetSocketAddress, channel);
        }
        return channel;
    }

    public Channel doConnect(InetSocketAddress inetSocketAddress) throws ExecutionException, InterruptedException {
        CompletableFuture<Channel> completableFuture = new CompletableFuture<>();
        bootstrap.connect(inetSocketAddress).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                log.info("client connected, inetSocketAddress={}", inetSocketAddress.toString());
                completableFuture.complete(future.channel());

            } else {
                throw new IllegalStateException();
            }
        });

        return completableFuture.get();
    }
}
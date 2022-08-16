package com.example.rpcframework.protocol.channel;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;

/**
 * @author Shan Cheng
 * @since 2022/8/16
 */
public interface ChannelProvider {

    Channel getChannel(InetSocketAddress inetSocketAddress);

    void setChannel(InetSocketAddress inetSocketAddress, Channel channel);
}

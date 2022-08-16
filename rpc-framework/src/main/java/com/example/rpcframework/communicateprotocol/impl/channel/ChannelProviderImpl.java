package com.example.rpcframework.communicateprotocol.impl.channel;

import com.example.rpcframework.communicateprotocol.impl.channel.ChannelProvider;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shan Cheng
 * @since 2022/8/16
 */
public class ChannelProviderImpl implements ChannelProvider {
    private final Map<String, Channel> channelMap = new HashMap<>();

    @Override
    public Channel getChannel(InetSocketAddress inetSocketAddress) {
        String key = inetSocketAddress.toString();

        if (channelMap.containsKey(key)) {
            Channel channel = channelMap.get(key);
            if (channel != null && channel.isActive()) {
                return channel;
            } else {
                channelMap.remove(key);
            }
        }

        return null;
    }

    @Override
    public void setChannel(InetSocketAddress inetSocketAddress, Channel channel) {
        channelMap.put(inetSocketAddress.toString(), channel);
    }
}

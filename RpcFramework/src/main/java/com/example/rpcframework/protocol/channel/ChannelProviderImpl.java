package com.example.rpcframework.protocol.channel;

import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shan Cheng
 * @since 2022/8/16
 */

@Component
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

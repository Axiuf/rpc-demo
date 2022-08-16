package com.example.rpcframework.register;

import com.example.rpcframework.dto.Invocation;

import java.net.InetSocketAddress;

/**
 * @author Shan Cheng
 * @since 2022/8/15
 */
public interface RegisterService {

    InetSocketAddress findService(Invocation invocation);

    void register(String ServiceName, InetSocketAddress inetSocketAddress);
}

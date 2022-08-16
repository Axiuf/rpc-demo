package com.example.rpcframework.communicateprotocol;

import com.example.rpcframework.dto.Invocation;

/**
 * @author Shan Cheng
 * @since 2022/8/16
 */
public interface CommunicateProtocol {

    void start();

    Object invoke(Invocation invocation);
}

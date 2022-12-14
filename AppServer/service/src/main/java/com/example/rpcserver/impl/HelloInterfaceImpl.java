package com.example.rpcserver.impl;

import com.example.rpcframework.annotaion.RpcService;
import com.example.rpcserver.iface.HelloInterface;
import org.springframework.stereotype.Service;

/**
 * @author Shan Cheng
 * @since 2022/8/15
 */

@Service
@RpcService
public class HelloInterfaceImpl implements HelloInterface {

    @Override
    public String sayHello(String message) {
        return "I've got your message: " + message;
    }
}

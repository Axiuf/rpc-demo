package com.examplerpcclient.controller;

import com.example.rpcframework.annotaion.RpcReference;
import com.example.rpcserver.iface.HelloInterface;
import org.springframework.stereotype.Component;

/**
 * @author Shan Cheng
 * @since 2022/8/16
 */
@Component
public class HelloController {

    @RpcReference
    private HelloInterface helloInterface;

    public void sayHello(String msg) {
        System.out.println(helloInterface.sayHello(msg));
    }
}

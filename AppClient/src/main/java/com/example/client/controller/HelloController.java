package com.example.client.controller;

import com.example.rpcframework.annotaion.RpcReference;
import com.example.rpcserver.iface.HelloInterface;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cheng Shan
 */

@RestController
public class HelloController {

    @RpcReference
    private HelloInterface helloInterface;

    @PutMapping("/hello")
    public String sayHello(@RequestParam String msg) {

        return helloInterface.sayHello(msg);
    }
}

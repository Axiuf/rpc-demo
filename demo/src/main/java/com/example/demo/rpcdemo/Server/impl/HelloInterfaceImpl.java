package com.example.demo.rpcdemo.Server.impl;

import com.example.demo.rpcdemo.Server.HelloInterface;
import org.springframework.stereotype.Service;

/**
 * @author Shan Cheng
 * @since 2022/8/15
 */

@Service
public class HelloInterfaceImpl implements HelloInterface {

    @Override
    public String sayHello(String message) {
        return "I've got your message: " + message;
    }
}

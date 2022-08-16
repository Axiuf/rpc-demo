package com.example.rpcserver;

import com.example.rpcframework.protocol.server.NettyRpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Shan Cheng
 * @since 2022/8/16
 */

@SpringBootApplication
@ComponentScan("com.example")
public class ServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ServerApplication.class, args);
        SpringUtils.setAppContext(run);

        new NettyRpcServer().start();
    }
}

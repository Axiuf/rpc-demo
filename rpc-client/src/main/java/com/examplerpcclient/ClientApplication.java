package com.examplerpcclient;

import com.example.rpcframework.communicateprotocol.impl.client.NettyRpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author Shan Cheng
 * @since 2022/8/16
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.example")
public class ClientApplication {
    @Bean
    NettyRpcClient startClient() {
        return new NettyRpcClient();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}

package com.example.demo.rpcdemo.client;

import com.example.demo.rpcdemo.framework.Invocation;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Shan Cheng
 * @since 2022/8/14
 */

@Slf4j
public class  SocketClient {

    public static void main(String[] args) {

        // 建立连接：ip + port
        try (Socket socket = new Socket("localhost", 8080)){
            // 指定方法：类名 + 方法名 +参数列表
            Invocation invocation = new Invocation();
            invocation.setInterfaceName("HelloInterface");
            invocation.setMethodName("sayHello");
            invocation.setParamType(new Class[]{String.class});
            // 指定入参数据
            invocation.setParams(new Object[]{"helloRpc"});

            // 发送信息
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(invocation);


            // 获取并解析返回值
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            String result = (String) inputStream.readObject();

            log.info(result);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

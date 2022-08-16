package com.example.demo.rpcdemo.Server;

import com.example.rpcdemo.Server.impl.HelloInterfaceImpl;
import com.example.demo.rpcdemo.framework.Invocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Shan Cheng
 * @since 2022/8/14
 */

@Slf4j
public class SocketServer {

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        // 启动服务
        try (ServerSocket server = new ServerSocket(8080)) {
            ExecutorService threadPool = Executors.newCachedThreadPool();
            log.info("Server starts successfully!");

            while (true) {
                // 监听请求
                Socket accept = server.accept();
                log.info("Connected!");
                threadPool.execute(() -> handle(accept));
            }

        } catch (Exception e) {
            log.error("Server starts failed!");
        }
    }

    private static void handle(Socket socket) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {

            // 读取请求的数据
            Invocation invocation = (Invocation) objectInputStream.readObject();

            // 根据invocation的信息定位到服务中具体的方法
            HelloInterface helloInterface = new HelloInterfaceImpl();

            // 方法调用
            String result = helloInterface.sayHello((String) invocation.getParams()[0]);

            // 结果回传
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

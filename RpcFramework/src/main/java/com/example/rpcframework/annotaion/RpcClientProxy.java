package com.example.rpcframework.annotaion;

import com.example.rpcframework.dto.Invocation;
import com.example.rpcframework.dto.Response;
import com.example.rpcframework.protocol.client.NettyRpcClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author Shan Cheng
 * @since 2022/8/16
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {

    private static final String INTERFACE_NAME = "interfaceName";

    private final NettyRpcClient client;

    public RpcClientProxy(NettyRpcClient client) {
        this.client = client;
    }

    /**
     * get the proxy object
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    /**
     * This method is actually called when you use a proxy object to call a method.
     * The proxy object is the object you get through the getProxy method.
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Invocation invocation = new Invocation().setInterfaceName(method.getDeclaringClass().getName())
                .setMethodName(method.getName())
                .setParamTypes(method.getParameterTypes())
                .setParams(args)
                .setRequestId(UUID.randomUUID().toString());

        Response<Object> rpcResponse = null;
        CompletableFuture<Response<Object>> completableFuture = (CompletableFuture<Response<Object>>) client.invoke(invocation);
        rpcResponse = completableFuture.get();

        return rpcResponse.getData();
    }
}
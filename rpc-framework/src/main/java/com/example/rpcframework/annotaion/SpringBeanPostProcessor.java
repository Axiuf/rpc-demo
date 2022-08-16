package com.example.rpcframework.annotaion;

import com.example.rpcframework.communicateprotocol.impl.client.NettyRpcClient;
import com.example.rpcframework.register.RegisterService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author Shan Cheng
 * @since 2022/8/16
 */

@Component
@Slf4j
@AllArgsConstructor
public class SpringBeanPostProcessor implements BeanPostProcessor {

    private final RegisterService registerService;

    private final NettyRpcClient client;

    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, @NonNull String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(RpcService.class)) {
            String host = InetAddress.getLocalHost().getHostAddress();
            registerService.register(beanName, new InetSocketAddress(host, 8080));
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, @NonNull String beanName) throws BeansException {

        Class<?> targetClass = bean.getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            RpcReference rpcReference = declaredField.getAnnotation(RpcReference.class);
            if (rpcReference != null) {

                RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
                Object clientProxy = rpcClientProxy.getProxy(declaredField.getType());
                declaredField.setAccessible(true);
                try {
                    declaredField.set(bean, clientProxy);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}

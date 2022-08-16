package com.example.rpcframework.protocol.server;

import com.example.rpcframework.dto.Invocation;
import com.example.rpcframework.register.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Cheng Shan
 */
@AllArgsConstructor
@Component
public class RpcRequestHandler {

    private final RegisterService registerService;

    public Object handle(Invocation invocation) {
        Object service = registerService.getServiceImpl(invocation.getInterfaceName());
        return invokeTargetMethod(invocation, service);
    }

    private Object invokeTargetMethod(Invocation invocation, Object service) {
        Object result;
        try {
            Method method = service.getClass().getMethod(invocation.getMethodName(), invocation.getParamTypes());
            result = method.invoke(service, invocation.getParams());

        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return result;
    }
}

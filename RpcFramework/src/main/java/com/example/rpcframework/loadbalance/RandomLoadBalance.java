package com.example.rpcframework.loadbalance;

import com.example.rpcframework.dto.Invocation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Shan Cheng
 * @since 2022/8/16
 */

@Component
public class RandomLoadBalance implements LoadBalance{
    @Override
    public String selectService(List<String> serviceList, Invocation invocation) {
        return serviceList.get(ThreadLocalRandom.current().nextInt(serviceList.size()));
    }
}

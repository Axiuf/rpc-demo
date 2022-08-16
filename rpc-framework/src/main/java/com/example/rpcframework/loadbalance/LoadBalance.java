package com.example.rpcframework.loadbalance;

import com.example.rpcframework.dto.Invocation;

import java.util.List;

/**
 * @author Shan Cheng
 * @since 2022/8/16
 */
public interface LoadBalance {

    String selectService(List<String> serviceList, Invocation invocation);
}

package com.example.rpcframework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Shan Cheng
 * @since 2022/8/15
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Invocation implements Serializable {

    private String interfaceName;

    private String methodName;

    private Class<?>[] paramTypes;

    private Object[] params;

    private String requestId;
}
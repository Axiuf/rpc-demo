package com.example.demo.rpcdemo.framework;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shan Cheng
 * @since 2022/8/14
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invocation implements Serializable {
    private String interfaceName;

    private String methodName;

    private Class[] paramType;

    private Object[] params;
}
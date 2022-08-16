package com.example.rpcframework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shan Cheng
 * @since 2022/8/15
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;

    private String requestId;
}

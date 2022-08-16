package com.example.rpcframework.annotaion;

import java.lang.annotation.*;

/**
 * @author Shan Cheng
 * @since 2022/8/16
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface RpcService {
}

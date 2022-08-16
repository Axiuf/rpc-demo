package com.examplerpcclient.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Shan Cheng
 * @since 2022/8/16
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class HelloControllerTest {

    @Autowired
    private HelloController helloController;

    @Test
    public void testSayHello() {
        String msg = "This is a rpc test message.";
        helloController.sayHello(msg);
    }

}
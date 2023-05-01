package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;

/**
 * mono-spring
 * <p>
 * created by https://github.com/MonoSirius 11:42
 * copyright (c) USTC. All rights reserved.
 * 2023/4/30
 */
public class SimpleBeanContainerTest {
    @Test
    public void testGetBean() {

    }
    class HelloService {
        public String sayHello() {
            System.out.println("hello");
            return "hello";
        }
    }
}


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
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.registerBean("helloService", new HelloService());
        HelloService helloService = (HelloService) beanFactory.getBean("helloService");
        helloService.sayHello();
    }
    class HelloService {
        public String sayHello() {
            System.out.println("hello");
            return "hello";
        }
    }
}


package org.springframework.test.ioc.service;

/**
 * mono-spring
 *
 * @author MonoSirius
 * @date 2023/5/1
 */
public class HelloService {
    public String sayHello() {
        System.out.println("hello");
        return "hello";
    }
}
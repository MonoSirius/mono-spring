package org.springframework.test.aop;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.service.WorldService;

/**
 * @author MonoSirius
 * @date 2023/5/5
 */
public class AutoProxyTests {
    @Test
    public void testAutoProxy() {
        ClassPathXmlApplicationContext applicationContext = new  ClassPathXmlApplicationContext("classpath:auto-proxy.xml");

        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.explode();
    }
}

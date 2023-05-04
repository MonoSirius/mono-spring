package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.service.HelloService;

/**
 * @author MonoSirius
 * @date 2023/5/3
 */
public class AwareInterfaceTest {

    @Test
    public void testAwareInterface() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        HelloService helloService = applicationContext.getBean("helloService", HelloService.class);
        System.out.println(helloService.getBeanFactory());
        System.out.println(helloService.getApplicationContext());
    }
}

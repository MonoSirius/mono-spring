package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.ioc.common.event.CustomEvent;

/**
 * @author MonoSirius
 * @date 2023/5/3
 */
public class EventAndEventListenerTest {
    @Test
    public void testEventAndEventListener() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:event-and-event-listener.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext));
        applicationContext.registerShutdownHook();
    }
}

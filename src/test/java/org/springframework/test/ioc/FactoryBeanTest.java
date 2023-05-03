package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.ioc.bean.Car;

/**
 * @author MonoSirius
 * @date 2023/5/3
 */
public class FactoryBeanTest {

    @Test
    public void testFactoryBean() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:factorybean.xml");
        Car car = (Car) applicationContext.getBean("car", Car.class);
        System.out.println(car);

    }
}

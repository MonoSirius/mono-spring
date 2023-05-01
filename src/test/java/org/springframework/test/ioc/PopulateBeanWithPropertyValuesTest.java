package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.test.ioc.bean.Car;
import org.springframework.test.ioc.bean.Person;

/**
 * mono-spring
 *
 * @author MonoSirius
 * @date 2023/5/1
 */
public class PopulateBeanWithPropertyValuesTest {
    @Test
    public void testPopulateBeanWithPropertyValues() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 注册Car
        PropertyValues propertyValuesOfCar = new PropertyValues();
        propertyValuesOfCar.addPropertyValue(new PropertyValue("brand", "prosche"));
        BeanDefinition beanDefinitionOfCar = new BeanDefinition(Car.class, propertyValuesOfCar);
        beanFactory.registerBeanDefinition("car", beanDefinitionOfCar);
        // 注册person
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", "mono"));
        propertyValues.addPropertyValue(new PropertyValue("age", 23));
        propertyValues.addPropertyValue(new PropertyValue("car", new BeanReference("car")));
        BeanDefinition beanDefinition = new BeanDefinition(Person.class, propertyValues);
        beanFactory.registerBeanDefinition("person", beanDefinition);

        // 为person
        Person person = (Person) beanFactory.getBean("person");
        System.out.println(person);
    }
}

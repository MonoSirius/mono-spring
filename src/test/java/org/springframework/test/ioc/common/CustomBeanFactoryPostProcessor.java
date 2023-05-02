package org.springframework.test.ioc.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 *
 * @author MonoSirius
 * @date 2023/5/2
 */
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    // 实例化前修改 BeanDefinition
    @Override
    public void postProcessorBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("CustomBeanFactoryPostProcessor#postProcessBeanFactory");
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("person");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        // 修改属性
        propertyValues.addPropertyValue(new PropertyValue("name", "cmy"));
    }
}

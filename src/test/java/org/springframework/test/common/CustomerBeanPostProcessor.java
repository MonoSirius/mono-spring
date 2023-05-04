package org.springframework.test.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.test.bean.Car;

/**
 * @author MonoSirius
 * @date 2023/5/2
 */
public class CustomerBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + "初始化前#postProcessBeforeInitialization");
        //
        if ("car".equals(beanName)) {
            ((Car) bean).setBrand("lamborghini");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + "初始化后#postProcessAfterInitialization");
        return bean;
    }
}

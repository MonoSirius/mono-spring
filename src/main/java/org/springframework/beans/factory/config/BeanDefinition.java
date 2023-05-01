package org.springframework.beans.factory.config;

/**
 * mono-spring
 * <p>
 * BeanDefinition实例保存bean的信息，包括class类型、方法构造参数、是否为单例等，此处简化只包含class类型
 *
 * created by https://github.com/MonoSirius 13:43
 * copyright (c) USTC. All rights reserved.
 * 2023/4/30
 */
public class BeanDefinition {
    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}

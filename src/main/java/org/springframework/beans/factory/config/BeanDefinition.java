package org.springframework.beans.factory.config;

import org.springframework.beans.PropertyValues;

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

    private PropertyValues propertyValues;
    public BeanDefinition(Class beanClass) {
        this(beanClass, null);
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues == null ? new PropertyValues() : propertyValues;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}

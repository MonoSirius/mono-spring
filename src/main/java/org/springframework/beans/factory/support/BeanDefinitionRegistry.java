package org.springframework.beans.factory.support;

import org.springframework.beans.factory.config.BeanDefinition;

/**
 * mono-spring
 *
 * BeanDefinition 注册表接口
 * created by https://github.com/MonoSirius 13:46
 * copyright (c) USTC. All rights reserved.
 * 2023/4/30
 */
public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注册beanDefinition
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}

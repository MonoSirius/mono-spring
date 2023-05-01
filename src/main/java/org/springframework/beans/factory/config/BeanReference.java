package org.springframework.beans.factory.config;

/**
 * mono-spring
 * <p>
 * created by https://github.com/MonoSirius 11:19
 * copyright (c) USTC. All rights reserved.
 * 2023/5/1
 */
    public class BeanReference {
    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}

package org.springframework.beans.factory.config;

/**
 * mono-spring
 * 单例注册表
 * @author MonoSirius
 * @date 2023/4/30
 */
public interface SingletonBeanRegistry {
    Object getSingleton(String beanName);

    void addSingleton(String beanName, Object singletonObject);
}

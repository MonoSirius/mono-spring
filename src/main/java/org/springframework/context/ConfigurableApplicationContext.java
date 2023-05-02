package org.springframework.context;

import org.springframework.beans.BeansException;

/**
 * 可配置应用容器
 * 1. 容器刷新
 * @author MonoSirius
 * @date 2023/5/2
 */
public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * 刷新容器
     * @throws BeansException
     */
    void refresh() throws BeansException;
}

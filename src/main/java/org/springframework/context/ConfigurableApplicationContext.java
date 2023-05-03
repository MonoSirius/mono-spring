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

    /**
     * 关闭应用上下文
     */
    void close();

    /**
     * 向虚拟机中注册一个钩子方法，在虚拟机关闭之前执行关闭容器等操作
     */
    void registerShutdownHook();
}

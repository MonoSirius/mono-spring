package org.springframework.context.event;


import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author MonoSirius
 * @date 2023/5/3
 */
public interface ApplicationEventMulticaster {

    /**
     * 添加监听器
     * @param listener
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 移除监听器
     * @param listener
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 发布事件
     * @param event
     */
    void multicastEvent(ApplicationEvent event);
}

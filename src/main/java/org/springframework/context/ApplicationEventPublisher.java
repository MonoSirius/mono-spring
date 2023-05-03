package org.springframework.context;

/**
 * 事件发布接口
 * @author MonoSirius
 * @date 2023/5/3
 */
public interface ApplicationEventPublisher {
    /**
     * 发布事件
     * @param event
     */
    void publishEvent(ApplicationEvent event);
}

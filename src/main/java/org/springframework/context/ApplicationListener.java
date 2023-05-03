package org.springframework.context;

import java.util.EventListener;

/**
 * 监听器接口
 * E 的上限为ApplicationEvent
 * @author MonoSirius
 * @date 2023/5/3
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    void onApplicationEvent(E event);
}

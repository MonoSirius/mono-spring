package org.springframework.context.event;

/**
 * @author MonoSirius
 * @date 2023/5/3
 */
public class ContextClosedEvent extends ApplicationContextEvent{
    public ContextClosedEvent(Object source) {
        super(source);
    }
}

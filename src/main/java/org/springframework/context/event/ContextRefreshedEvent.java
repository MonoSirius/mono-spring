package org.springframework.context.event;

/**
 * @author MonoSirius
 * @date 2023/5/3
 */
public class ContextRefreshedEvent extends ApplicationContextEvent{
    public ContextRefreshedEvent(Object source) {
        super(source);
    }

}

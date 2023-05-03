package org.springframework.test.ioc.common.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author MonoSirius
 * @date 2023/5/3
 */
public class CustomEvent extends ApplicationEvent {

    public CustomEvent(Object source) {
        super(source);
    }
}

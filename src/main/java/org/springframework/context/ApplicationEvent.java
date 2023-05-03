package org.springframework.context;

import java.util.EventObject;

/**
 * 事件抽象类
 * @author MonoSirius
 * @date 2023/5/3
 */
public abstract class ApplicationEvent extends EventObject {

    // 事件源
    public ApplicationEvent(Object source) {
        super(source);
    }
}

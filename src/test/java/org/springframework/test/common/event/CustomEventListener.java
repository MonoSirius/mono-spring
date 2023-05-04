package org.springframework.test.common.event;

import org.springframework.context.ApplicationListener;

/**
 * @author MonoSirius
 * @date 2023/5/3
 */
public class CustomEventListener implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println(this.getClass().getName());
    }
}

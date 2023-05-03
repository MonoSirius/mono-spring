package org.springframework.context.event;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationEvent;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author MonoSirius
 * @date 2023/5/3
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{


    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    /**
     * 对感兴趣的事件进行处理
     * @param event
     */
    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener<ApplicationEvent> applicationListener : applicationListeners) {
            if (supportsEvent(applicationListener, event)) {
                applicationListener.onApplicationEvent(event);
            }
        }
    }

    /**
     * 判断此listener是否对event感兴趣
     * @param applicationListener
     * @param event
     * @return
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        // 1. 拿到applicationListener 泛型 接口 类名
        Type type = applicationListener.getClass().getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();

        Class<?> eventClass;
        try {
            eventClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        // 判断 event 是不是listener所支持的 event 的子类或者子接口
        return eventClass.isAssignableFrom(event.getClass());
    }
}

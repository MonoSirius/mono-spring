package org.springframework.beans.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * mono-spring
 * <p>
 * created by https://github.com/MonoSirius 11:39
 * copyright (c) USTC. All rights reserved.
 * 2023/4/30
 */
public class BeanFactory {
    private Map<String, Object> beanMap= new HashMap<>();

    public void registerBean(String beanName, Object bean) {
        beanMap.put(beanName, bean);
    }

    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }
}

package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

import java.util.HashMap;
import java.util.Map;

/**
 * mono-spring
 * <p>
 * created by https://github.com/MonoSirius 11:39
 * copyright (c) USTC. All rights reserved.
 * 2023/4/30
 */
public interface BeanFactory {
    /**
     * 获取bean
     * @param name
     * @return
     * @throws BeansException
     */
    Object getBean(String name) throws BeansException;

    /**
     * 根据名称和类型查找bean
     *
     * @param name
     * @param requiredType
     * @param <T>
     * @return
     * @throws BeansException
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;
}

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
    Object getBean(String name) throws BeansException;
}

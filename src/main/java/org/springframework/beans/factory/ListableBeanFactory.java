package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

import java.util.Map;

/**
 * @author MonoSirius
 * @date 2023/5/1
 */
public interface ListableBeanFactory extends BeanFactory{

    /**
     * 返回指定类型的所有bean
     * @param type
     * @return
     * @param <T>
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回所有定义bean的beanName
     * @return
     */
    String[] getBeanDefinitionNames();
}

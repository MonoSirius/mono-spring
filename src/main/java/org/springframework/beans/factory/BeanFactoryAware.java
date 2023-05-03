package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * 感知所属的BeanFactory容器
 *
 * @author MonoSirius
 * @date 2023/5/3
 */
public interface BeanFactoryAware extends Aware{

    /**
     * 为当前类设置所属容器BeanFactory
     * @param beanFactory
     * @throws BeansException
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}

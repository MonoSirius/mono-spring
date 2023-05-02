package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.beans.Beans;

/**
 * 1. 实现了容器创建
 * 2. 抽象 BeanDefinition 加载方法
 *
 * @author MonoSirius
 * @date 2023/5/2
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{
    private DefaultListableBeanFactory beanFactory;

    /**
     * 创建beanFactory并加载BeanDefinition
     * @throws BeansException
     */
    @Override
    protected final void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * 创建BeanFactory
     * @return
     */
    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    /**
     * 加载BeanDefinition
     * @param beanFactory
     * @throws BeansException
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException;

    @Override
    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}

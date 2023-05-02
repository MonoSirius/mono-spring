package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * 1. 定义了容器刷新的步骤
 * 2. 抽象了 创建BeanFactory,加载BeanDefinition
 * @author MonoSirius
 * @date 2023/5/2
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    @Override
    public void refresh() throws BeansException {
        // 1. 创建BeanFactory,加载BeanDefinition
        refreshBeanFactory();
        // 1.2 获取beanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 2. 实例化前执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessor(beanFactory);

        // 3. 实例化前注册BeanPostProcessor
        registerBeanPostProcessor(beanFactory);

        // 4. 提前实例化单例Bean
        beanFactory.preInstantiateSingletons();
    }

    /**
     * 注册beanPostProcessor
     * @param beanFactory
     */
    protected void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        // 1. 获取所有beanPostProcessor
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);

        // 2. 注册(执行add方法)
        for (BeanPostProcessor processor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(processor);
        }

    }

    /**
     * 在bean实例化之前，执行BeanFactoryPostProcessor
     * @param beanFactory
     */
    protected void invokeBeanFactoryPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        // 1. 获取所有的BeanFactoryPostProcessor
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);

        // 2. 执行处理器方法
        for (BeanFactoryPostProcessor processor : beanFactoryPostProcessorMap.values()) {
            processor.postProcessorBeanFactory(beanFactory);
        }
    }

    /**
     * 创建BeanFactory，并加载BeanDefinition
     *
     * @throws BeansException
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    public abstract ConfigurableListableBeanFactory getBeanFactory();

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }
}

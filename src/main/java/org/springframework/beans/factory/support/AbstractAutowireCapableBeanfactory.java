package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * mono-spring
 *
 * @author MonoSirius
 * @date 2023/4/30
 */
public abstract class AbstractAutowireCapableBeanfactory extends AbstractBeanfactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();
    @Override
    protected Object creatBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return doCreatBean(beanName, beanDefinition);
    }

    protected Object doCreatBean(String beanName, BeanDefinition beanDefinition) {
        Class beanClass = beanDefinition.getBeanClass();
        Object bean = null;
        try {
            bean = creatBeanInstance(beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        // 放到单例池中
        addSingleton(beanName,bean);
        return bean;
    }

    // 策略模式
    private Object creatBeanInstance(BeanDefinition beanDefinition) {
        return instantiationStrategy.instantiate(beanDefinition);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}

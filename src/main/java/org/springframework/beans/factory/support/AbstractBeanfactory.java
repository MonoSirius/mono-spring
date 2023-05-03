package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mono-spring
 *
 * @author MonoSirius
 * @date 2023/4/30
 */
public abstract class AbstractBeanfactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    // bean初始化处理器
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    // FactoryBean缓存
    private final Map<String, Object> factoryBeanObjectCache = new HashMap<>();

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object sharedInstance = getSingleton(beanName);

        if (sharedInstance != null) {
            // 如果是FactoryBean，从FactoryBean#getObject中创建bean
            return getObjectForBeanInstance(sharedInstance, beanName);
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = creatBean(beanName, beanDefinition);
        return getObjectForBeanInstance(bean, beanName);
    }

    /**
     * 如果是FactoryBean，从FactoryBean#getObject中创建bean
     *
     * @param beanInstance
     * @param beanName
     * @return
     */
    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        // 不是FactoryBean 直接返回
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        // 从FactoryBean#getObject中创建bean
        Object object = beanInstance;
        FactoryBean factoryBean = (FactoryBean) beanInstance;

        try {
            // 不是单例bean
            if (!factoryBean.isSingleton()) {
                object = factoryBean.getObject();
                return object;
            }

            // 从缓存中拿或创建后放到缓存
            object = factoryBeanObjectCache.get(beanName);
            if (object == null) {
                // 创建
                object = factoryBean.getObject();
                // 放到缓存
                factoryBeanObjectCache.put(beanName, object);
            }
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
        return object;

    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return ((T) getBean(name));
    }

    protected abstract Object creatBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        //有则覆盖
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }
}

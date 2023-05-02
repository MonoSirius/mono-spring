package org.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.AutowireCapableBeanfactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;

/**
 * mono-spring
 *
 * @author MonoSirius
 * @date 2023/4/30
 */
public abstract class AbstractAutowireCapableBeanfactory extends AbstractBeanfactory implements AutowireCapableBeanfactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object creatBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return doCreatBean(beanName, beanDefinition);
    }

    protected Object doCreatBean(String beanName, BeanDefinition beanDefinition) {
        Class beanClass = beanDefinition.getBeanClass();
        Object bean = null;
        try {
            // 实例化bean
            bean = creatBeanInstance(beanDefinition);

            // 为bean填充属性
            applyPropertyValues(beanName, bean, beanDefinition);

        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        // 放到单例池中
        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * 属性填充
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    private void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 通过beanDefinition 获取 propertyValues
        try {
            for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                // 如果属性是一个bean,则需要先实例化该bean
                // TODO: 解决循环依赖
                if (value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) value;
                    //
                    value = getBean(beanReference.getBeanName());
                }
                // 通过反射设置属性
                BeanUtil.setFieldValue(bean, name, value);

            }
        } catch (Exception e) {
            throw new BeansException("error setting property values for bean [" + beanName + "]", e);
        }
    }

    /**
     * 策略模式
     * 实例化bean
     *
     * @param beanDefinition
     * @return
     */
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

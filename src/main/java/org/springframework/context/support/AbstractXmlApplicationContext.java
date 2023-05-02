package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 实现 BeanDefinition 加载
 * @author MonoSirius
 * @date 2023/5/2
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException {
        // 1. 获取XmlBeanDefinitionReader
        // Application 实现了资源加载器
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        // 2. 获取所有配置位置
        String[] configLocations = getConfigLocations();

        // 3. 加载
        if (configLocations != null) {
            xmlBeanDefinitionReader.loadBeanDefinitions(configLocations);
        }

    }

    /**
     * 获取配置位置
     * @return
     */
    protected abstract String[] getConfigLocations();
}

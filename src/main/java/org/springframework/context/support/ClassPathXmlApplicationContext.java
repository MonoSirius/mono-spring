package org.springframework.context.support;

import org.springframework.beans.BeansException;

/**
 * @author MonoSirius
 * @date 2023/5/2
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{
    private String[] configLocations;

    /**
     * 从xml文件加载BeanDefinition，并且自动刷新上下文
     * @param configLocations
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;
        // 从xml文件加载BeanDefinition, 刷新上下文
        refresh();
    }

    public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
        this(new String[]{configLocation});
    }


    @Override
    protected String[] getConfigLocations() {
        return this.configLocations;
    }
}

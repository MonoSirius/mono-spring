package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * 拥有自定义初始化方法的Bean
 * @author MonoSirius
 * @date 2023/5/2
 */
public interface InitializingBean {

    /**
     * 填充属性后执行
     * @throws BeansException
     */
    void afterPropertiesSet() throws BeansException;
}

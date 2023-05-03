package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * 需要执行销毁方法的bean
 * @author MonoSirius
 * @date 2023/5/2
 */
public interface DisposableBean {

    /**
     * 销毁方法
     * @throws BeansException
     */
    void destroy() throws Exception;
}

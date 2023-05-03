package org.springframework.beans.factory;

/**
 * @author MonoSirius
 * @date 2023/5/3
 */
public interface FactoryBean<T> {
    /**
     * 判断是否是单例bean,如果是,将最终bean放进缓存中，下次从缓存中获取
     * @return
     */
    boolean isSingleton();

    /**
     * 向容器获取该bean时，容器不是返回其本身，而是返回其FactoryBean#getObject方法的返回值
     * 当容器发现bean为FactoryBean类型时，调用其getObject方法返回最终bean
     * @return
     * @throws Exception
     */
    T getObject() throws Exception;
}

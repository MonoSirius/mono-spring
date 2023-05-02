package org.springframework.context;

import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.io.ResourceLoader;

/**
 * 应用上下文接口
 * 1. 能够列举出容器中所有bean
 * 2. 实现层级功能
 * 3. 具备资源加载器功能,能够读取资源
 * @author MonoSirius
 * @date 2023/5/2
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader {
}

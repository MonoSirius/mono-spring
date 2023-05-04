package org.springframework.aop.framework;

import org.springframework.aop.AdvisedSupport;

/**
 * @author MonoSirius
 * @date 2023/5/4
 */
public class ProxyFactory {
    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy() {
        return creatAopProxy().getProxy();
    }
    private AopProxy creatAopProxy() {
        // 根据proxyTargetClass决定使用什么代理
        if (advisedSupport.isProxyTargetClass()) {
            return new CglibAopProxy(advisedSupport);
        }
        return new JdkDynamicAopProxy(advisedSupport);
    }
}

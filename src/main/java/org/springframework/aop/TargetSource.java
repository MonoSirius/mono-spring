package org.springframework.aop;

/**
 * 被代理的目标对象
 * @author MonoSirius
 * @date 2023/5/4
 */
public class TargetSource {
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 返回代理对象所实现的接口
     * @return
     */
    public Class<?>[] getTargetClass() {
        return this.target.getClass().getInterfaces();
    }

    public Object getTarget() {
        return target;
    }
}

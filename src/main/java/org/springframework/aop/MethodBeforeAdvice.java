package org.springframework.aop;

import java.lang.reflect.Method;

/**
 * @author MonoSirius
 * @date 2023/5/4
 */
public interface MethodBeforeAdvice extends BeforeAdvice{

    void before(Method method, Object[] args, Object target) throws Throwable;
}

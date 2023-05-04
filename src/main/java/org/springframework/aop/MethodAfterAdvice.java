package org.springframework.aop;

import java.lang.reflect.Method;

/**
 * @author MonoSirius
 * @date 2023/5/4
 */
public interface MethodAfterAdvice extends AfterAdvice{
    void after(Method method, Object[] args, Object target) throws Throwable;
}

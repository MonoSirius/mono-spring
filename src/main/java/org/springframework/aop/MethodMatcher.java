package org.springframework.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配接口
 * @author MonoSirius
 * @date 2023/5/3
 */
public interface MethodMatcher {
    boolean matches(Method method, Class<?> targetClass);
}

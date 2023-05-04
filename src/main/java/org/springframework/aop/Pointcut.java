package org.springframework.aop;

/**
 * @author MonoSirius
 * @date 2023/5/3
 */
public interface Pointcut {
    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}

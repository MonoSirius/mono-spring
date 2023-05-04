package org.springframework.aop;

/**
 * 匹配类接口
 * @author MonoSirius
 * @date 2023/5/3
 */
public interface ClassFilter {
    boolean matches(Class<?> clazz);
}

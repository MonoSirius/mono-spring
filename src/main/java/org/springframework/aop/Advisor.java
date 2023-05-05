package org.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * @author MonoSirius
 * @date 2023/5/5
 */
public interface Advisor {
    Advice getAdvice();
}

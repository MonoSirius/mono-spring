package org.springframework.aop;

/**
 * @author MonoSirius
 * @date 2023/5/5
 */
public interface PointcutAdvisor extends Advisor{
    Pointcut getPointcut();
}

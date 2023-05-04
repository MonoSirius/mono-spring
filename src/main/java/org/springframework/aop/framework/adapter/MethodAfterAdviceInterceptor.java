package org.springframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.MethodAfterAdvice;

/**
 * @author MonoSirius
 * @date 2023/5/4
 */
public class MethodAfterAdviceInterceptor implements MethodInterceptor {
    private MethodAfterAdvice advice;

    public MethodAfterAdviceInterceptor(MethodAfterAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = methodInvocation.proceed();
        // 后置通知
        advice.after(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return result;
    }
}

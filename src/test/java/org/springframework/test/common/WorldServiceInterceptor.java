package org.springframework.test.common;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author MonoSirius
 * @date 2023/5/4
 */
public class WorldServiceInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("Do something before the earth explodes");
        Object result = methodInvocation.proceed();
        System.out.println("Do something after the earth explodes");
        return result;
    }
}

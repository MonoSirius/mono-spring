package org.springframework.test.aop;

import org.junit.Test;
import org.springframework.aop.AdvisedSupport;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.TargetSource;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.JdkDynamicAopProxy;
import org.springframework.test.common.WorldServiceInterceptor;
import org.springframework.test.service.WorldService;
import org.springframework.test.service.impl.WorldServiceImpl;

/**
 * @author MonoSirius
 * @date 2023/5/4
 */
public class JdkDynamicProxyTest {
    @Test
    public void testJdkDynamicProxy() {
        WorldService worldService = new WorldServiceImpl();

        AdvisedSupport advisedSupport = new AdvisedSupport();

        TargetSource targetSource = new TargetSource(worldService);
        WorldServiceInterceptor worldServiceInterceptor = new WorldServiceInterceptor();

        String expression = "execution(* org.springframework.test.service.WorldService.explode(..))";
        MethodMatcher methodMatcher = new AspectJExpressionPointcut(expression).getMethodMatcher();

        advisedSupport.setMethodInterceptor(worldServiceInterceptor);
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodMatcher(methodMatcher);

        WorldService proxy = (WorldService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }
}

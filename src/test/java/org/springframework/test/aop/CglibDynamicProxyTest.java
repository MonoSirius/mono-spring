package org.springframework.test.aop;

import org.junit.Test;
import org.springframework.aop.AdvisedSupport;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.TargetSource;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.CglibAopProxy;
import org.springframework.test.common.WorldServiceInterceptor;
import org.springframework.test.service.WorldService;
import org.springframework.test.service.impl.WorldServiceImpl;

/**
 * @author MonoSirius
 * @date 2023/5/4
 */
public class CglibDynamicProxyTest {

    @Test
    public void testCglibDynamicProxy() {
        WorldService worldService = new WorldServiceImpl();

        AdvisedSupport advised = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(worldService);
        String expression = "execution(* org.springframework.test.service.WorldService.explode(..))";
        MethodMatcher methodMatcher = new AspectJExpressionPointcut(expression).getMethodMatcher();
        WorldServiceInterceptor worldServiceInterceptor = new WorldServiceInterceptor();
        advised.setTargetSource(targetSource);
        advised.setMethodMatcher(methodMatcher);
        advised.setMethodInterceptor(worldServiceInterceptor);

        WorldService proxy = (WorldService) new CglibAopProxy(advised).getProxy();
        proxy.explode();
    }
}

package org.springframework.test.aop;

import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.test.service.HelloService;

import java.lang.reflect.Method;

/**
 * @author MonoSirius
 * @date 2023/5/3
 */
public class PointcutExpressionTest {
    @Test
    public void testPointcutExpression() throws NoSuchMethodException {
        // 匹配HelloService类的所有方法
        String expression = "execution(* org.springframework.test.service.HelloService.*(..))";
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(expression);
        Class<HelloService> clazz = HelloService.class;
        Method sayHello = clazz.getDeclaredMethod("sayHello");

        System.out.println(pointcut.matches(sayHello, clazz));
        System.out.println(pointcut.matches(clazz));
    }
}

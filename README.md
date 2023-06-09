# mono-spring
参考[mini-spring](https://github.com/DerekYRC/mini-spring)， 实现最简单的spring功能
## 功能
### IOC
- [x] [实现一个简单容器](#实现一个简单容器)
- [x] [BeanDefinition & BeanDefinitionRegistry]()
- [x] [Bean实例化策略]()
- [x] [为bean填充属性](#为bean填充属性)
- [x] [资源和资源加载器](#资源和资源加载器)
- [x] [在xml文件中定义bean](#在xml文件中定义bean)
- [x] [容器扩展机制BeanFactoryPostProcess和BeanPostProcessor]()
- [x] [应用上下文ApplicationContext](#应用上下文applicationcontext)
- [x] [bean的初始化和销毁方法](#bean的初始化和销毁方法)
- [x] [Aware接口](#Aware接口)
- [x] [prototype支持](#prototype支持)
- [x] [FactoryBean](#FactoryBean)
- [x] [容器事件和事件监听器](#容器事件和事件监听器)
### AOP
- [X] [切点表达式](#切点表达式)
- [X] [基于JDK的动态代理](#基于jdk动态代理)
- [X] [基于CGLIB的动态代理](#基于CGLIB的动态代理)
- [X] [AOP代理工厂ProxyFactory](#AOP代理工厂ProxyFactory)
- [X] [几种常用的Advice](#几种常用的Advice)
- [x] [PointcutAdvisor：Pointcut和Advice的组合](#Pointcutadvisorpointcut和advice的组合)
- [X] [动态代理融入bean生命周期](#动态代理融入bean生命周期)
- [x] [bug fix: 没有为代理类设置属性的短路问题](#bug fix: 没有为代理类设置属性的短路问题)
### 扩展篇
- [ ] [PropertyPlaceholderConfigurer]
- [ ] [包扫描]
- [ ] [@Value注解]
- [ ] [基于注解@Autowired的依赖注入]
- [ ] [类型转换 I]
- [ ] [类型转换 II]

## 复现记录
### [为bean填充属性](#为bean填充属性)
> 代码分支：populate-bean-with-property-values

在BeanDefinition中增加和bean属性对应的PropertyValues，实例化bean之后，为bean填充属性(AbstractAutowireCapableBeanFactory#applyPropertyValues)。

> Tip: JavaBean在申明有参构造后无参构造会被覆盖,需要显示申明无参构造,否则在
> 通过'getDeclaredConstructor()' 获取构造器会报错 'NoSuchMethodException'


### [资源和资源加载器](#资源和资源加载器)
> 代码分支：resource-and-resource-loader
Resource是资源的抽象和访问接口，简单写了三个实现类
- FileSystemResource，文件系统资源的实现类
- ClassPathResource，classpath下资源的实现类
- UrlResource，对java.net.URL进行资源定位的实现类

默认策略:
```java
public Resource getResource(String location) {
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            // classpath下的资源
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            try {
                // 尝试当成url处理
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                // FileSystem处理
                return new FileSystemResource(location);
            }
        }
    }
```
### [在XML文件中定义Bean](#在XML文件中定义Bean)
> 代码分支：xml-file-define-bean

- 由于从xml文件中读取的内容是String类型，所以属性仅支持String类型和引用其他Bean。
```java
// String
String valueAttribute = property.getAttribute(VALUE_ATTRIBUTE);
//  引用类型
if (StrUtil.isNotEmpty(refAttribute)) {
    value = new BeanReference(refAttribute);
}
```
- BeanDefinitionReader是读取bean定义信息的抽象接口，XmlBeanDefinitionReader是从xml文件中读取的实现类。
- BeanDefinitionReader需要有获取资源的能力，且读取bean定义信息后需要往容器中注册BeanDefinition，因此BeanDefinitionReader的抽象实现类AbstractBeanDefinitionReader拥有ResourceLoader和BeanDefinitionRegistry两个属性。
![asserts/xmlReader.png](./asserts/xmlReader.png)

测试:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	         http://www.springframework.org/schema/beans/spring-beans.xsd
		 http://www.springframework.org/schema/context
		 http://www.springframework.org/schema/context/spring-context-4.0.xsd">

    <bean id="person" class="org.springframework.test.bean.Person">
        <property name="name" value="derek"/>
        <property name="car" ref="car"/>
    </bean>

    <bean id="car" class="org.springframework.test.bean.Car">
        <property name="brand" value="porsche"/>
    </bean>

</beans>
```

### [应用上下文ApplicationContext](#应用上下文ApplicationContext)
> 代码分支：application-context

- BeanFactory是spring的基础设施，面向spring本身
- 而ApplicationContext面向spring的使用者，应用场合使用ApplicationContext。

```java
public void refresh() throws BeansException {
        // 1. 创建BeanFactory,加载BeanDefinition
        refreshBeanFactory();
        // 1.2 获取beanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 2. 实例化前执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessor(beanFactory);

        // 3. 实例化前注册BeanPostProcessor
        registerBeanPostProcessor(beanFactory);

        // 4. 提前实例化单例Bean
        beanFactory.preInstantiateSingletons();
    }
```

![](./asserts/ApplicationContext.png)

![](./asserts/bean加载流程.png)

### [bean的初始化和销毁方法](#bean的初始化和销毁方法)
> 代码分支：init-and-destroy-method

#### 在spring中，定义bean的初始化和销毁方法有三种方法：

- [x] 在xml文件中制定init-method和destroy-method
- [x] 继承自InitializingBean和DisposableBean
- [ ] 在方法上加注解PostConstruct和PreDestroy

#### 实现
1. 在`BeanDefinition`中增加属性`initMethodName`和`destroyMethodName`
2. 增加两个接口 `DisposableBean` 和 `InitializingBean`
```java
public class Person implements DisposableBean, InitializingBean {
    private String name;
    private Integer age;

    private Car car;
    public Person() {
    }

    public Person(String name, Integer age, Car car) {
        this.name = name;
        this.age = age;
        this.car = car;
    }

    public void customInitMethod() {
        System.out.println("I was born in the method named customInitMethod");
    }

    public void customDestroyMethod() {
        System.out.println("I died in the method named customDestroyMethod");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("I died in the method named destroy");
    }

    @Override
    public void afterPropertiesSet() throws BeansException {
        System.out.println("I was born in the method named afterPropertiesSet");
    }
}
```
3. 在xml文件中指定初始化和销毁方法的方式, 通过xmlReader读取
```xml
<bean id="person"
          class="org.springframework.test.bean.Person"
          init-method="customInitMethod"
          destroy-method="customDestroyMethod"
    >
        <property name="name" value="derek"/>
        <property name="car" ref="car"/>
    </bean>
```

3. 初始化方法在AbstractAutowireCapableBeanFactory#invokeInitMethods执行
4. DefaultSingletonBeanRegistry中增加属性disposableBeans保存拥有销毁方法的bean,
拥有销毁方法的bean在AbstractAutowireCapableBeanFactory#registerDisposableBeanIfNecessary中注册到disposableBeans中。
5. 为了确保销毁方法在虚拟机关闭之前执行，向虚拟机中注册一个钩子方法，查看AbstractApplicationContext#registerShutdownHook

执行顺序:
![](./asserts/init%20&%20destroy.png)

![](./asserts/bean生命周期-init和destory%20.png)

### [Aware接口](#Aware接口)
> 代码分支：aware-interface

Aware是感知、意识的意思，Aware接口是标记性接口，其实现子类能感知容器相关的对象。
常用的Aware接口有`BeanFactoryAware`和`ApplicationContextAware`，分别能让其实现者感知所属的`BeanFactory`和`ApplicationContext`。

#### 实现BeanFactoryAware
在AbstractAutowireCapableBeanFactory#initializeBean
初始化bean的时候为bean注入BeanFactory

```java
// 为当前bean设置所属容器 (实现BeanFactoryAware感知)
if (bean instanceof BeanFactoryAware) {
    ((BeanFactoryAware) bean).setBeanFactory(this);
}
```

#### 实现ApplicationContextAware
1. 定义`BeanPostProcessor`实现类 `ApplicationContextAwareProcessor`
2. 在 AbstractApplicationContext#refresh 时将处理器加入到容器中
```java
public void refresh() throws BeansException {
    // 1. 创建BeanFactory,加载BeanDefinition
    refreshBeanFactory();
    // 1.2 获取beanFactory
    ConfigurableListableBeanFactory beanFactory = getBeanFactory();

    // 2. 添加ApplicationContextAwareProcessor 实现ApplicationContext感知
    beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
}
```
3. 在执行bean初始化前置方法时会为实现了ApplicationContextAware的类注入ApplicationContext
```java
@Override
public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof ApplicationContextAware) {
        ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
    }
    return bean;
}
```

至此,当前bean生命周期:
![](./asserts/bean生命周期-Aware.png)

### prototype支持
> 代码分支：prototype-bean

每次向容器获取prototype作用域bean时，容器都会创建一个新的实例。

#### 实现
1. beanDefinition中定义scope字段
```java
// 作用域 -> 默认单例
private String scope = SCOPE_SINGLETON;

private boolean singleton = true;
private boolean prototype = false;
```
2. xmlReader中增加读取scope字段到definition的功能
```java
// 4.2 设置beanScope
if (StrUtil.isNotEmpty(beanScope)) {
    beanDefinition.setScope(beanScope);
}
```
3. 增加加入单例池的判断(AbstractAutowireCapableBeanFactory#doCreateBean)
```java
// 将单例bean 放到单例池中
if (beanDefinition.isSingleton()) {
    addSingleton(beanName, bean);
}
```
4. 注册销毁方法判断 -> prototype没有销毁方法(AbstractAutowireCapableBeanFactory#registerDisposableBeanIfNecessary)
```java
// prototype不执行销毁方法(prototype支持)
if (! beanDefinition.isSingleton()) {
    return;
}
```
5. 实例化单例bean前进行判断
```java
// DefaultListableBeanFactory#preInstantiateSingletons
public void preInstantiateSingletons() throws BeansException {
    // 执行getBean方法进行实例化
    beanDefinitionMap.forEach((beanName, beanDefinition) -> {
        // 只对单例bean进行实例化
        if (beanDefinition.isSingleton()) {
            getBean(beanName);
        }
    });
}
```

至此,bean的生命周期:
![](./asserts/bean生命周期-prototype.png)

### [FactoryBean](#FactoryBean)
> 代码分支：factory-bean

FactoryBean是一种特殊的bean，当向容器获取该bean时，容器不是返回其本身，而是返回其FactoryBean#getObject方法的返回值，可通过编码方式定义复杂的bean。

#### 实现
- 定义`FactoryBean`接口
- 增加`FactoryBeanCache`缓存 用于存储单例FactoryBean
- 修改`getBean`(AbstractBeanFactory类)方法,在实例化是判断是否是`FactoryBean`,如果是,则通过 `getObject` 返回
```java
// 不是单例bean
if (!factoryBean.isSingleton()) {
    object = factoryBean.getObject();
    return object;
}

// 从缓存中拿或创建后放到缓存
object = factoryBeanObjectCache.get(beanName);
if (object == null) {
    // 创建
    object = factoryBean.getObject();
    // 放到缓存
    factoryBeanObjectCache.put(beanName, object);
}
```

### [容器事件和事件监听器](#容器事件和事件监听器)
> 代码分支：event-and-event-listener

#### 实现
1. 定义 `ApplicationEvent` 事件抽象类, 事件监听器 `ApplicationListener` 和 事件发布器`ApplicationEventPublisher`
2. 定义事件体系
   - `ApplicationContextEvent` ApplicationContext容器事件抽象类
   - `ContextRefreshedEvent` 容器刷新事件
   - `ContextClosedEvent` 容器关闭事件
![](./asserts/Event体系.png)
3. 定义 ApplicationEventMulticaster 体系
   - `ApplicationEventMulticaster` 接口 添加删除监听器, 发布事件
   - `AbstractApplicationEventMulticaster` 定义了监听器set
   - `SimpleApplicationEventMulticaster` 实现 multicastEvent , 遍历监听器, 对感兴趣的事件进行处理
![](./asserts/ApplicationEventMulticaster体系.png)
4. 在 AbstractApplicationContext 中增加属性 ApplicationEventMulticaster,并在`refresh`中实例化
   - ApplicationContext 实现 publisher 接口, 作为事件发布者角色
   - 初始化事件发布者
   - 注册监听器
   - 发布容器刷新完成事件
   - 在doClose中发布容器关闭事件
```java
public void refresh() throws BeansException {
   // 1. 创建BeanFactory,加载BeanDefinition
   refreshBeanFactory();
   // 1.2 获取beanFactory
   ConfigurableListableBeanFactory beanFactory = getBeanFactory();
   
   // 2. 添加ApplicationContextAwareProcessor 实现ApplicationContext感知
   beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
   
   // 3. 实例化前执行BeanFactoryPostProcessor
   invokeBeanFactoryPostProcessor(beanFactory);
   
   // 4. 实例化前注册BeanPostProcessor
   registerBeanPostProcessor(beanFactory);
   
   // 5. 初始化事件发布者
   initApplicationEventMulticaster();
   
   // 6. 注册事件监听器
   registerListeners();
   
   // 7. 提前实例化单例Bean
   beanFactory.preInstantiateSingletons();
   
   // 8. 发布容器刷新完成事件
   finishRefresh();
}
```
### [切点表达式](#切点表达式)
> 代码分支：pointcut-expression

- Joinpoint，织入点，指需要执行代理操作的某个类的某个方法(仅支持方法级别的JoinPoint)
- Pointcut是JoinPoint的表述方式，能捕获JoinPoint。

最常用的切点表达式是AspectJ的切点表达式。

#### 实现
1. 匹配类:`ClassFilter`接口
2. 匹配方法:`MethodMatcher`接口
3. `PointCut`需要同时匹配类和方法，包含`ClassFilter`和`MethodMatcher`
4. `AspectJExpressionPointcut`是支持AspectJ切点表达式的`PointCut`实现，简单实现仅支持`execution`函数。

### [基于JDK动态代理](#基于JDK动态代理)
> 代码分支：jdk-dynamic-proxy

AopProxy是获取代理对象的抽象接口，JdkDynamicAopProxy的基于JDK动态代理的具体实现。
TargetSource，被代理对象的封装。

#### 实现
1. 通过方法拦截器MethodInterceptor 去对方法进行增强
```java
public class WorldServiceInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("Do something before the earth explodes");
        Object result = methodInvocation.proceed();
        System.out.println("Do something after the earth explodes");
        return result;
    }
}
```
2. 通过MethodInvocation对方法进行调用
```java
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getClass())) {
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(), method, args));
        }
        return method.invoke(advised.getTargetSource().getTarget(), args);
    }
}
```
3. 代理类
```java
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    private final AdvisedSupport advised;

    /**
     * 返回代理对象
     * @return
     */
    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(
                getClass().getClassLoader(),
                advised.getTargetSource().getTargetClass(),
                this);
    }
}
```
### [基于CGLIB的动态代理](#基于CGLIB的动态代理)
> 代码分支：cglib-dynamic-proxy

基于CGLIB的动态代理能在运行期间动态构建字节码的class文件，为类生成子类，因此被代理类不需要继承自任何接口。

#### 实现
1. 创建代理类
```java
public Object getProxy() {
  Enhancer enhancer = new Enhancer();
  // 设置父类(即目标类)
  enhancer.setSuperclass(advised.getTargetSource().getTarget().getClass());
  // 目标类实现的接口
  enhancer.setInterfaces(advised.getTargetSource().getTargetClass());
  //
  enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
  return enhancer.create();
}
```
2. 创建适配静态内部类 `DynamicAdvisedInterceptor`
```java
private static class DynamicAdvisedInterceptor implements MethodInterceptor {
  private final AdvisedSupport advised;

  private DynamicAdvisedInterceptor(AdvisedSupport advised) {
      this.advised = advised;
  }

  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
      CglibMethodInvocation methodInvocation = new CglibMethodInvocation(advised.getTargetSource().getTarget(), method, objects, methodProxy);
      if (advised.getMethodMatcher().matches(method,advised.getTargetSource().getTarget().getClass())) {
          // 执行代理方法
          return advised.getMethodInterceptor().invoke(methodInvocation);
      }
      return methodInvocation.proceed();
  }
}
```
### [AOP代理工厂ProxyFactory](#AOP代理工厂ProxyFactory)
> 代码分支：proxy-factory

增加AOP代理工厂ProxyFactory，由`AdvisedSupport#proxyTargetClass`属性决定使用JDK动态代理还是CGLIB动态代理。
```java
public class ProxyFactory {
    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy() {
        return creatAopProxy().getProxy();
    }
    private AopProxy creatAopProxy() {
        // 根据proxyTargetClass决定使用什么代理
        if (advisedSupport.isProxyTargetClass()) {
            return new CglibAopProxy(advisedSupport);
        }
        return new JdkDynamicAopProxy(advisedSupport);
    }
}
```

### [几种常用的Advice](#几种常用的Advice)
> 代码分支： common-advice

Spring将AOP联盟中的Advice细化出各种类型的Advice，常用的有BeforeAdvice/AfterAdvice/AfterReturningAdvice/ThrowsAdvice
我们可以通过扩展MethodInterceptor来实现

- [x] BeforeAdvice
- [x] AfterAdvice
- [ ] AfterReturningAdvice
- [ ] ThrowsAdvice

#### 实现
1. 定义 BeforeAdvice/AfterAdvice 接口继承 AOP联盟 Advice 接口
2. 在MethodBeforeAdvice接口中定义 Before 通知方法
```java
public interface MethodBeforeAdvice extends BeforeAdvice{

    void before(Method method, Object[] args, Object target) throws Throwable;
}
```
3. 定义MethodBeforeAdviceInterceptor拦截器，在执行被代理方法之前，先执行BeforeAdvice的方法。
```java
 @Override
 public Object invoke(MethodInvocation methodInvocation) throws Throwable {
     //
     this.advice.before(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
     return methodInvocation.proceed();
 }
```

### [PointcutAdvisor：Pointcut和Advice的组合](#Pointcutadvisorpointcut和advice的组合)
> 代码分支：pointcut-advisor

- Advisor是包含一个Pointcut和一个Advice的组合，Pointcut用于捕获JoinPoint，Advice决定在JoinPoint执行某种操作。
- 实现了一个支持aspectj表达式的AspectJExpressionPointcutAdvisor。

#### 实现
1. 定义Advisor接口和PointcutAdvisor接口
2. 定义AspectJExpressionPointcutAdvisor 实现 PointcutAdvisor接口

> Tip: AOP联盟的 MethodInterceptor 接口 是 Advice 的子接口, 可以对Advice进行增强

#### 测试
- 从 Advisor中拿到 Advice 和 Pointcut
```java
class DynamicProxyTests {
   @Test
   public void testPointcutAdvisor() {
      WorldService worldService = new WorldServiceImpl();
      String expression = "execution(* org.springframework.test.service.WorldService.explode(..))";
      AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor(expression);
      MethodBeforeAdviceInterceptor mbai = new MethodBeforeAdviceInterceptor(new WorldServiceBeforeAdvice());
      advisor.setAdvice(mbai);
      ClassFilter classFilter = advisor.getPointcut().getClassFilter();
      if (classFilter.matches(worldService.getClass())) {
         AdvisedSupport advisedSupport = new AdvisedSupport();

         TargetSource targetSource = new TargetSource(worldService);
         advisedSupport.setTargetSource(targetSource);
         advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
         advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

         WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
         proxy.explode();
      }
   }
}
```

### [动态代理融入bean生命周期](#动态代理融入bean生命周期)
> 代码分支: auto-proxy

BeanPostProcessor处理阶段可以修改和替换bean，正好可以在此阶段返回代理对象替换原对象.
引入一种特殊的BeanPostProcessor——`InstantiationAwareBeanPostProcessor`.

#### 实现
1. 定义InstantiationAwareBeanPostProcessor接口
```java
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    /**
     * 在bean实例化之前执行
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}
```
2. 接口实现类 `DefaultAdvisorAutoProxyCreator` 实例化bean并生成代理对象
```java
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;
    /**
     * 对bean进行实例化,生成代理对象后返回
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        // 避免死循环
        if (isInfrastructureClass(beanClass)) {
            return null;
        }
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        try {
            for (AspectJExpressionPointcutAdvisor advisor : advisors) {
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                if (classFilter.matches(beanClass)) {
                    // 1. 实例化bean
                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                    Object bean = beanFactory.getInstantiationStrategy().instantiate(beanDefinition);

                    // 2. 代理
                    AdvisedSupport advisedSupport = new AdvisedSupport();
                    advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
                    advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                    advisedSupport.setTargetSource(new TargetSource(bean));

                    // 3. 返回代理对象
                    return new ProxyFactory(advisedSupport).getProxy();
                }
            }
        } catch (BeansException e) {
            throw new BeansException("Error create proxy bean for: " + beanName, e);
        }
        return null;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }
}
```
3. 在bean实例化前(执行`doCreat`方法前) 执行 `postProcessBeforeInstantiation`
```java
public abstract class AbstractAutowireCapableBeanfactory extends AbstractBeanfactory implements AutowireCapableBeanfactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object creatBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        // 如果bean需要代理,直接返回代理对象
        Object bean = resolveBeforeInstantiation(beanName, beanDefinition);
        if (bean != null) {
            return bean;
        }

        return doCreatBean(beanName, beanDefinition);
    }

    /**
     * 执行InstantiationAwareBeanPostProcessor的方法，如果bean需要代理，直接返回代理对象
     * 会导致短路,不会继续执行原来的bean的初始化流程
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (bean != null) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    /**
     * 执行实例化前的PostProcess
     * @param beanClass
     * @param beanName
     * @return
     */
    protected Object applyBeanPostProcessorBeforeInstantiation(Class beanClass, String beanName) {
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            if (processor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) processor).postProcessBeforeInstantiation(beanClass, beanName);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}
```

### [bug fix: 没有为代理类设置属性的短路问题](#bug fix: 没有为代理类设置属性的短路问题)
> 代码分支: populate-proxy-bean-with-property-values

问题现象：没有为代理bean设置属性 在实例化前直接短路 后面的处理器也没有执行

修复方案：跟spring保持一致，将织入逻辑迁移到BeanPostProcessor#postProcessAfterInitialization,
即将DefaultAdvisorAutoProxyCreator#postProcessBeforeInstantiation的内容迁移到
DefaultAdvisorAutoProxyCreator#postProcessAfterInitialization中。

即: 代理逻辑织入到Bean初始化后执行

> ⚠️:删除原来在代理前实例化的逻辑,否则会重复实例化

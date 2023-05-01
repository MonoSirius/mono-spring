# mono-spring
参考mini-spring， 实现最简单的spring功能

## [为bean填充属性](#为bean填充属性)
> 代码分支：populate-bean-with-property-values

在BeanDefinition中增加和bean属性对应的PropertyValues，实例化bean之后，为bean填充属性(AbstractAutowireCapableBeanFactory#applyPropertyValues)。

> Tip: JavaBean在申明有参构造后无参构造会被覆盖,需要显示申明无参构造,否则在
> 通过'getDeclaredConstructor()' 获取构造器会报错 'NoSuchMethodException'


## [资源和资源加载器](#资源和资源加载器)
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
# FeignClient

## 知识前提

Feign调用实现的核心：

（1）ImportBeanDefinitionRegistrar：使用import的方式注册bean定义，在这里会获取到所有注解了@FeignClient注解的接口，获取构建bean定义信息，使用动态代理进行实现类的实现。

（2）FactoryBean：具体的实现类实现了接口FactoryBean，在这里会返回构造的对象，这个对象是代理对象。

## 代码核心

### 注解EnableFeignClients

启用FeignClient以及指定要扫描的@MyFeignClient的包路径。通过注解@Import导入FeignClientsRegistrar。

### 注解FeignMethod

用于方法上，需输入请求的接口信息，包括请求的路径和方式。

### 注解MyFeignClient

用在接口上，以确定该接口是FeignClient客户端

### FeignClientsRegistrar

实现接口ImportBeanDefinitionRegistrar，主要是获取注解EnableFeignClients的包信息，然后扫描该包下注解了@MyFeignClient的接口信息，
并且添加bean定义以及指定具体的实现类FeignFactoryBean。实现流程如下：

（1）首先需要获取到@EnableFeignClients的注解属性信息，获取到设置的包路径，比如：com.practice.bom.feign_client。

（2）使用Spring提供的ClassPathScanning进行扫描到BeanDefinition，主要是通过设置的包路径和注解@MyFeignClient进行过滤。

（3）对获取到的BeanDefinition信息，进行属性赋值，核心的代码是指定bean class为FeignFactoryBean。(对应58~60行)

### FeignFactoryBean
@MyFeignClient注解的接口具体的实现类。 

（1）FeignFactoryBean实现了两个接口FactoryBean以及InvocationHandler。
实现FactoryBean主要是为了能够返回一个通过动态代理实现的对象。实现接口InvocationHandler是JDK的动态代理。 

（2）getObject()：使用jdk的Proxy构造了一个代理对象。【注】这个方法在Spring启动的过程中就执行了，并不是每次访问的时候才执行，所以只执行一次。 

（3）invoke()：InvocationHandler的回调方法，此方法核心就是获取注解上的信息，构造url，然后使用相应的网络请求工具发起请求。 

（4）对于服务名称的解析，使用了最简单的Map存储方式。
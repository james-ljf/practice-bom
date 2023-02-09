# RestTemplate

## 负载均衡实现
（1）调用之前处理，在调用RestTemplate的请求的方法传入url之前，就对于url进行处理，根据不同的算法返回url。

（2）RestTemplate的方法setInterceptors，可以设置拦截器，也就是添加一个拦截器拦截请求，对拦截到的请求进行处理，比如替换url，然后返回新的构造的请求。
这里替换url，一方面可以根据不同的算法返回不同的url，也可以对于某些url进行拦截不执行，或者某些url直接转到新的地址上。

本例子的核心就是添加拦截器，添加拦截器，有两种常见的思路：
①在构建RestTemplate的时候，使用RestTemplate提供的setInterceptors进行添加。
②使用注解，然后在利用Spring提供的扩展点注入Interceptor。

综上所述，就有了3种方案：
（1）根据不同的算法获得url，然后使用RestTemplate进行请求。
（2）在构造RestTemplate的时候，注入拦截器拦截请求，根据不同的算法重新构建请求。
（3）使用注解和Spring的扩展点，RestTemplate创建的时候，注入拦截器拦截请求，根据不同的算法重新构建请求。

本例子使用的是第3种方案，通过自定义注解实现，大致思路如下：
（1）自定义注解@MyLoadBalanced
（2）利用Spring的扩展点SmartInitializingSingleton注入拦截器
（3）在RestTemplate Bean添加注解@MyLoadBalanced
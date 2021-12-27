
# SpringCloud-Alibaba Consumer 示例

参考：<https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html>  
关键点：
- 配置
- 给 RestTemplate 实例添加 @LoadBalanced 注解，开启 @LoadBalanced 与 Ribbon 的集成

注意：
- SpringCloud 从 Edgware 版本（对应 SpringBoot 1.5.x）开始,可以不加`@EnableDiscoveryClient`注解,只要配置好注册中心的相关配置即可自动开启服务注册功能

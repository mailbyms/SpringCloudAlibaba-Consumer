package com.gyjian.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class NacosController{

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String appName;

    /**
     * 方式一：使用 LoadBalanceClient 和 RestTemolate 结合的方式来访问
     * @return
     */
    @GetMapping("/echo/app-name")
    public String echoAppName(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-provider");
        String url = String.format("http://%s:%s/echo/%s",serviceInstance.getHost(),serviceInstance.getPort(),appName);
        System.out.println("request url:"+url);
        return restTemplate.getForObject(url,String.class);
    }

    /**
     * 方式二：使用负载均衡直接调用
     * @param str
     * @return
     */
    @RequestMapping(value = "/echo2/{str}", method = RequestMethod.GET)
    public String echo(@PathVariable String str) {
        return restTemplate.getForObject("http://nacos-provider/echo/" + str, String.class);
    }

    /**
     * 使用 @LoadBalanced 注解，开启 @LoadBalanced 与 Ribbon 的集成
     * 使用 @Bean 实例化 RestTemplate 实例，允许 Autowired
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
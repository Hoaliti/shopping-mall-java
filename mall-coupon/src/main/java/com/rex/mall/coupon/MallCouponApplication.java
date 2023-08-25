package com.rex.mall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * Nacos
 * namespace: 配置隔离
 * 默认 public(保留空间)
 * 1. Dev Prod Test 可以隔离
 * 2. 在bootstrap.properties : spring.cloud.nacos.config.namespace=553fdc02-9f41-4aba-b03c-b51e57c54b24
 * 3. 基于每个微服务可以互相隔离配置，每个微服务都有自己的namespace
 * <p>
 * 配置集
 * <p>
 * 配置集ID: 类似配置文件名
 * Data Id: 类似文件名
 * <p>
 * 4）配置分组 Group
 * 默认所有的配置集都属于：DEFAULT_GROUP：
 * 1111,618, 1212
 * dev prod test
 * <p>
 * 3. 同时加载多个配置集
 * 1)只需要在bootstrap.properties中说明加载配置中心中那些配置文件即可
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallCouponApplication.class, args);
    }

}

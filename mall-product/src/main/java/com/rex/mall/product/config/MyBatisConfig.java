package com.rex.mall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement //开启使用
@MapperScan("com.rex.mall.product.dao")

public class MyBatisConfig {

    //引入分页插件
     @Bean
    public PaginationInterceptor paginationInterceptor(){
         PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
         paginationInterceptor.setOverflow(true);
         paginationInterceptor.setLimit(1000);
         return  paginationInterceptor;
     }
}

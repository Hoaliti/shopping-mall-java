package com.rex.mall.ware.feign;

import com.rex.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mall-product")
public interface ProductFeignService {

    /**
     * 获取sku info
     *
     * 1. 让所有的请求过网关
     *    1）@FeignClient("mall-gateway") 给gateway服务器发请求
     *    2） /api/product/skuinfo/info/{skuId}
     *
     * 2. 给微服务集齐发请求
     *
     */
    @RequestMapping("/product/skuinfo/info/{skuId}")
    public R info(@PathVariable("skuId") Long skuId);
}

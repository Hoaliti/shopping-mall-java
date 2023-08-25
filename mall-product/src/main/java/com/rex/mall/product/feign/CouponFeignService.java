package com.rex.mall.product.feign;

import com.rex.common.to.SkuReductionTo;
import com.rex.common.to.SpuBoundTo;
import com.rex.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("mall-coupon")
public interface CouponFeignService {

    /**
     * 远程调用逻辑
     * 1. CouponFeignService.saveSpuBounds(spuBoundTo)
     *  1) @RequestBody将这个对象转为Json
     *  2) 找到mall-coupon服务，给/coupon/spubounds/save发送请求，将上一步转的Json放在请求体的位置
     *  3) 对方服务收到请求，请求体里有JSON数据
     *      (@RequestBody SpuBoundsEntity spuBoundTo) 将请求体的json转为SpuBoundsEntity
     *   只要JSON数据模型是兼容的，双方服务无需使用同一个TO
     */
    @PostMapping("coupon/spubounds/save")
    R saveSpuBounds(SpuBoundTo spuBoundTo);

    @PostMapping("coupon/skufullreduction/saveinfo")
    R saveSpuReduction(SkuReductionTo skuReductionTo);
}

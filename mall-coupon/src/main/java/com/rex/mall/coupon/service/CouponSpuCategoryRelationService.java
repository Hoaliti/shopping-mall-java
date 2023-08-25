package com.rex.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rex.common.utils.PageUtils;
import com.rex.mall.coupon.entity.CouponSpuCategoryRelationEntity;

import java.util.Map;

/**
 * 优惠券分类关联
 *
 * @author rex
 * @email mydearzh@outlook.com
 * @date 2023-05-08 16:59:46
 */
public interface CouponSpuCategoryRelationService extends IService<CouponSpuCategoryRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


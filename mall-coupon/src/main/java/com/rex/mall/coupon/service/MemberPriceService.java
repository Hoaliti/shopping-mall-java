package com.rex.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rex.common.utils.PageUtils;
import com.rex.mall.coupon.entity.MemberPriceEntity;

import java.util.Map;

/**
 * 商品会员价格
 *
 * @author rex
 * @email mydearzh@outlook.com
 * @date 2023-05-08 16:59:46
 */
public interface MemberPriceService extends IService<MemberPriceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


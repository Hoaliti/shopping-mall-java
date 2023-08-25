package com.rex.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rex.common.utils.PageUtils;
import com.rex.mall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author rex
 * @email mydearzh@outlook.com
 * @date 2023-05-08 17:10:17
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


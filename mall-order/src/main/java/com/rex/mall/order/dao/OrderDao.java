package com.rex.mall.order.dao;

import com.rex.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 *
 * @author rex
 * @email mydearzh@outlook.com
 * @date 2023-05-08 17:10:17
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

}

package com.rex.mall.product.dao;

import com.rex.mall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 *
 * @author rex
 * @email mydearzh@outlook.com
 * @date 2023-04-19 16:49:30
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

}

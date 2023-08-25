package com.rex.mall.product.service.impl;

import com.rex.mall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rex.common.utils.PageUtils;
import com.rex.common.utils.Query;

import com.rex.mall.product.dao.BrandDao;
import com.rex.mall.product.entity.BrandEntity;
import com.rex.mall.product.service.BrandService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                new QueryWrapper<BrandEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void updateDetail(BrandEntity brand) {
        // 保证冗余字段的一致性
        this.updateById(brand);

        if(StringUtils.hasLength(brand.getName())){
            categoryBrandRelationService.updateBrand(brand.getBrandId(),brand.getName());

            // TODO 更新其他关联

        }
    }

}

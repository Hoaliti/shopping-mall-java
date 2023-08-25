package com.rex.mall.product.service.impl;

import com.rex.mall.product.entity.SpuInfoEntity;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rex.common.utils.PageUtils;
import com.rex.common.utils.Query;

import com.rex.mall.product.dao.SkuInfoDao;
import com.rex.mall.product.entity.SkuInfoEntity;
import com.rex.mall.product.service.SkuInfoService;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();

        String key = (String) params.get("key");

        if(StringUtils.hasLength(key)){
            wrapper.and((w) ->{
                w.eq("sku_id",key).or().like("sku_name",key);
            });
        }

        String min = (String) params.get("min");

        if(StringUtils.hasLength(min)){
            wrapper.ge("price",min);
        }


        String max = (String) params.get("max");

        if(StringUtils.hasLength(max)){
            try {
                BigDecimal bigDecimal = new BigDecimal(max);
                if(bigDecimal.compareTo(new BigDecimal("0")) > 0){
                    wrapper.le("price",max);
                }
            }catch (Exception e){

            }

        }


        String brandId = (String) params.get("brandId");

        if(StringUtils.hasLength(brandId) && !"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id",brandId);
        }

        String catelogId = (String) params.get("catelogId");

        if(StringUtils.hasLength(catelogId) && !"0".equalsIgnoreCase(catelogId)){
            wrapper.eq("catalog_id",catelogId);
        }

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);

    }

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        List<SkuInfoEntity> spuId1 = this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
        return spuId1;
    }

}

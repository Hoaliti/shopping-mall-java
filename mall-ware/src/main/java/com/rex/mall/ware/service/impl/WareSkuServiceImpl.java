package com.rex.mall.ware.service.impl;

import com.rex.common.utils.R;
import com.rex.mall.ware.feign.ProductFeignService;
import com.rex.mall.ware.vo.SkuHasStockVo;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rex.common.utils.PageUtils;
import com.rex.common.utils.Query;

import com.rex.mall.ware.dao.WareSkuDao;
import com.rex.mall.ware.entity.WareSkuEntity;
import com.rex.mall.ware.service.WareSkuService;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    WareSkuDao wareSkuDao;

    @Autowired
    ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();

        String skuId = (String) params.get("skuId");
        if(StringUtils.hasLength(skuId)){
            wrapper.eq("sku_id",skuId);
        }

        String wareId = (String) params.get("wareId");
        if(StringUtils.hasLength(skuId)){
            wrapper.eq("ware_id",wareId);
        }

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        // 如果没有库存 就新增
        List<WareSkuEntity> wareSkuEntities = wareSkuDao.selectList(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if(wareSkuEntities == null || wareSkuEntities.size() == 0){
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setStockLocked(0);

            // 远程查询sku的名字 如果失败 整个事务无需回滚
            // 如何不会滚
            // 1. catch异常
            // 2.TODO 如何不会滚呢
            try {
                R info = productFeignService.info(skuId);
                Map<String,Object> key = (Map<String, Object>) info.get("skuInfo");

                if(info.getCode() == 0){
                    wareSkuEntity.setSkuName((String) key.get("skuName"));
                }
            }catch (Exception e){

            }


            wareSkuDao.insert(wareSkuEntity);
        }
        wareSkuDao.addStock(skuId,wareId,skuNum);
    }

    @Override
    public List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds) {
        List<SkuHasStockVo> collect = skuIds.stream().map(skuId -> {
            SkuHasStockVo skuHasStockVo = new SkuHasStockVo();
            // 查询库存
            Long count = baseMapper.getSkuStock(skuId);
            skuHasStockVo.setSkuId(skuId);
            skuHasStockVo.setHasStock(count != null && count > 0);
            return skuHasStockVo;
        }).collect(Collectors.toList());
        return collect;

    }

}

package com.rex.mall.ware.service.impl;

import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rex.common.utils.PageUtils;
import com.rex.common.utils.Query;

import com.rex.mall.ware.dao.PurchaseDetailDao;
import com.rex.mall.ware.entity.PurchaseDetailEntity;
import com.rex.mall.ware.service.PurchaseDetailService;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurchaseDetailEntity> wrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if(StringUtils.hasLength(key)){
            wrapper.and( (w) -> {
                w.eq("purchase_id",key).or().eq("sku_id",key);
            });
        }

        String status = (String) params.get("status");
        if(StringUtils.hasLength(status)){
            wrapper.eq("status",status);
        }

        String wareId = (String) params.get("wareId");
        if(StringUtils.hasLength(wareId)){
            wrapper.eq("ware_id",wareId);
        }



        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<PurchaseDetailEntity> listDetailByPurchaseId(Long id) {
        QueryWrapper<PurchaseDetailEntity> wrapper = new QueryWrapper<>();

        return this.list(wrapper.eq("purchase_id", id));
    }


}

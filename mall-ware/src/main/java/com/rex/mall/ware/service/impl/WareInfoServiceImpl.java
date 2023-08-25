package com.rex.mall.ware.service.impl;

import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rex.common.utils.PageUtils;
import com.rex.common.utils.Query;

import com.rex.mall.ware.dao.WareInfoDao;
import com.rex.mall.ware.entity.WareInfoEntity;
import com.rex.mall.ware.service.WareInfoService;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareInfoEntity> wrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if(StringUtils.hasLength(key)){
            wrapper.and((w) ->{
                w.eq("id",key).or().like("name",key).or()
                        .like("address",key).or()
                        .like("areacode",key);
            });
        }


        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),wrapper

        );

        return new PageUtils(page);
    }

}

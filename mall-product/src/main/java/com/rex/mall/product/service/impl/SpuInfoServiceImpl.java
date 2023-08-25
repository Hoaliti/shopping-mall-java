package com.rex.mall.product.service.impl;

import com.rex.common.constant.ProductConstant;
import com.rex.common.to.SkuReductionTo;
import com.rex.common.to.SpuBoundTo;
import com.rex.common.to.es.SkuEsModel;
import com.rex.common.utils.R;
import com.rex.mall.product.entity.*;
import com.rex.mall.product.feign.CouponFeignService;
import com.rex.mall.product.feign.SearchFeignService;
import com.rex.mall.product.feign.WareFeignService;
import com.rex.mall.product.service.*;
import com.rex.mall.product.vo.*;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rex.common.utils.PageUtils;
import com.rex.common.utils.Query;

import com.rex.mall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    SpuInfoDescService spuInfoDescService;

    @Autowired
    SpuImagesService spuImagesService;

    @Autowired
    AttrService attrService;

    @Autowired
    ProductAttrValueService productAttrValueService;

    @Autowired
    SkuInfoService skuInfoService;

    @Autowired
    SkuImagesService skuImagesService;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    CouponFeignService couponFeignService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    WareFeignService wareFeignService;

    @Autowired
    SearchFeignService searchFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo spuInfo) {
        // 1.保存SPU基本信息 mall_pms.pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuInfo,spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.saveBaseSpuInfo(spuInfoEntity);

        // 2.保存Spu的描述图片 mall_pms.pms_spu_info_desc
        List<String> decript = spuInfo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();

        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(String.join(",",decript));

        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);

        // 3. 保存Spu的图片集  mall_pms.pms_spu_images
        List<String> images = spuInfo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(),images);


        // 4. 保存SPu的规格参数 pms_product_attr_value
        List<BaseAttrs> baseAttrs = spuInfo.getBaseAttrs();
        List<ProductAttrValueEntity> productAttrValueEntities = baseAttrs.stream().map(attr -> {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            productAttrValueEntity.setAttrId(attr.getAttrId());
            // 查询属性名
            AttrEntity attrEntity = attrService.getById(attr.getAttrId());
            productAttrValueEntity.setAttrName(attrEntity.getAttrName());
            productAttrValueEntity.setAttrValue(attr.getAttrValues());
            productAttrValueEntity.setQuickShow(attr.getShowDesc());
            productAttrValueEntity.setSpuId(spuInfoEntity.getId());
            return productAttrValueEntity;
        }).collect(Collectors.toList());

        productAttrValueService.saveProductAttr(productAttrValueEntities);


        // 5. 保存Spu的积分信息 mall_sms.sms_spu_bounds
        Bounds bounds = spuInfo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds,spuBoundTo);
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        R r = couponFeignService.saveSpuBounds(spuBoundTo);

        if(r.getCode() != 0){
            log.error("远程保存Spu积分信息失败");
        }

        // 5. 保存当前Spu对应的所有SKU信息
        // 1) sku基本信息 pms_sku_info
        List<Skus> skus = spuInfo.getSkus();
        if(skus != null && skus.size() > 0){
            for (Skus item : skus) {
                String defaultImg = "";
                for (Images image : item.getImages()) {
                    if (image.getDefaultImg() == 1) {
                        defaultImg = image.getImgUrl();
                    }
                }

                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item, skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);

                skuInfoService.saveSkuInfo(skuInfoEntity);

                Long skuId = skuInfoEntity.getSkuId();

                List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).filter(entity -> {
                    return StringUtils.hasLength(entity.getImgUrl());
                }).collect(Collectors.toList());

                // 2) sku的图片信息 mall_pms.pms_sku_images
                skuImagesService.saveBatch(imagesEntities);
                // TODO 没有图片的 路径无需保存

                // 3) sku的销售属性 mall_pms.pms_sku_sale_attr_value
                List<Attr> attr = item.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(attr1 -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(attr1, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuId);
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());

                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

                // 4) sku的优惠，满减信息 mall_sms.sms_sku_ladder , mall_sms.sms_member_price, mall_sms.sms_sku_full_reduction,
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item,skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if(skuReductionTo.getFullCount() >0 && skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) > 0){
                    R r1 = couponFeignService.saveSpuReduction(skuReductionTo);
                    if(r1.getCode() != 0){
                        log.error("远程保存Spu优惠信息失败");
                    }
                }



            }
        }

    }

    @Override
    public void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity) {
        this.baseMapper.insert(spuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<SpuInfoEntity>();

        String key = (String) params.get("key");

        if(StringUtils.hasLength(key)){
            wrapper.and((w) ->{
                w.eq("id",key).or().like("spu_name",key);
            });
        }

        String status = (String) params.get("status");

        if(StringUtils.hasLength(status)){
            wrapper.eq("publish_status",status);
        }

        String brandId = (String) params.get("brandId");

        if(StringUtils.hasLength(brandId) && !"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id",brandId);
        }

        String catelogId = (String) params.get("catelogId");

        if(StringUtils.hasLength(catelogId) && !"0".equalsIgnoreCase(catelogId)){
            wrapper.eq("catalog_id",catelogId);
        }


        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);

    }

    @Override
    public void up(Long spuId) {
        List<SkuInfoEntity> skus1 = skuInfoService.getSkusBySpuId(spuId);
        List<Long> skuIdList = skus1.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());


        // TODO 4. 查询当前sku 可以用来被检索的规格属性
        List<ProductAttrValueEntity> baseAttrs = productAttrValueService.baseAttrlistforspu(spuId);
        List<Long> attrIds = baseAttrs.stream().map(ProductAttrValueEntity::getAttrId).collect(Collectors.toList());
        List<Long> searchAttrIds = attrService.selectSearchAttrs(attrIds);
        Set<Long> idSet = new HashSet<>(searchAttrIds);

        List<SkuEsModel.Attrs> attrList = baseAttrs.stream().filter(item -> {
            return idSet.contains(item.getAttrId());
        }).map(item -> {
            SkuEsModel.Attrs attr = new SkuEsModel.Attrs();
            BeanUtils.copyProperties(item, attr);
            return attr;
        }).collect(Collectors.toList());


        // TODO 1. 远程调用，库存系统查询是否有库存
        Map<Long, Boolean> stockMap = null;
        try{
            R<List<SkuHasStockVo>> skusHasStock = wareFeignService.getSkusHasStock(skuIdList);
            stockMap = skusHasStock.getData().stream().collect(Collectors.toMap(
                    SkuHasStockVo::getSkuId, SkuHasStockVo::isHasStock
            ));

        }catch (Exception e){
            log.error("库存查询异常:原因{}",e);
        }



        // 1. 组装需要的数据
        SkuEsModel skuEsModel = new SkuEsModel();
        // 查出当前spuId对应的spu信息
        List<SkuInfoEntity> skus = skuInfoService.getSkusBySpuId(spuId);
        // 2.封装每个sku的信息
        Map<Long, Boolean> finalStockMap = stockMap;
        List<SkuEsModel> esModelList = skus.stream().map(sku -> {
            SkuEsModel esModel = new SkuEsModel();
            BeanUtils.copyProperties(sku, esModel);

            esModel.setSkuPrice(sku.getPrice());
            esModel.setSkuImg(sku.getSkuDefaultImg());

            // 判断库存
            // 设置库存信息
            if(finalStockMap == null){
                esModel.setHasStock(true);
            }else{
                esModel.setHasStock(finalStockMap.get(sku.getSkuId()));
            }

            // TODO 2. 热度评分，0;
            esModel.setHotScore(0L);

            // TODO 3. 查询品牌和分类的名字信息，0;
            BrandEntity brand = brandService.getById(esModel.getBrandId());
            esModel.setBrandName(brand.getName());
            esModel.setBrandImg(brand.getLogo());

            CategoryEntity category = categoryService.getById(esModel.getCatalogId());
            esModel.setCatalogName(category.getName());

            // 设置检索属性
            esModel.setAttrs(attrList);

            return esModel;
        }).collect(Collectors.toList());

        // TODO 5. 将数据发给ES进行保存 mall-search
        R r = searchFeignService.productStatusUp(esModelList);
        if(r.getCode() == 0){
            // 成功了
            // TODO 6. 修改当前Spu状态
            baseMapper.updateSpuStatus(spuId, ProductConstant.StatusEnum.SPU_UP.getCode());
        }else{
            // invoke failed
            // TODO 7. retry
        }

    }


}

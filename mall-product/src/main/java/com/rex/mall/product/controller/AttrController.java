package com.rex.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.rex.mall.product.entity.ProductAttrValueEntity;
import com.rex.mall.product.service.ProductAttrValueService;
import com.rex.mall.product.vo.AttrGroupRelationVo;
import com.rex.mall.product.vo.AttrRespVo;
import com.rex.mall.product.vo.AttrVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rex.mall.product.entity.AttrEntity;
import com.rex.mall.product.service.AttrService;
import com.rex.common.utils.PageUtils;
import com.rex.common.utils.R;


/**
 * 商品属性
 *
 * @author rex
 * @email mydearzh@outlook.com
 * @date 2023-04-19 16:49:30
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    ProductAttrValueService productAttrValueService;

    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrList(@PathVariable("spuId") Long spuId){

        List<ProductAttrValueEntity> list = productAttrValueService.baseAttrlistforspu(spuId);
        return R.ok().put("data",list);
    }

    /**
     * 列表
     */
    @RequestMapping("/{attrType}/list/{catelogId}")
    @RequiresPermissions("product:attrgroup:list")
    public R baseAttrList(@RequestParam Map<String, Object> params,
                  @PathVariable("catelogId") Long catelogId,
                        @PathVariable("attrType") String type) {
        PageUtils page = attrService.queryBaseAttrPage(params,catelogId,type);

        return R.ok().put("page", page);
    }





    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    @RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId) {
        //AttrEntity attr = attrService.getById(attrId);
        AttrRespVo attrRespVo = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", attrRespVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr) {
        attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attr) {
        attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update/{spuId}")
    //@RequiresPermissions("product:attr:update")
    public R updateSpuAttr(@PathVariable("spuId") Long spuId,@RequestBody  List<ProductAttrValueEntity> entities) {

        productAttrValueService.updateSpuAttr(spuId,entities);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}

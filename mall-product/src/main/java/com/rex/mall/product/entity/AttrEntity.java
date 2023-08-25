package com.rex.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 商品属性
 *
 * @author rex
 * @email mydearzh@outlook.com
 * @date 2023-04-19 16:49:30
 */
@Data
@TableName("pms_attr")
public class AttrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ÊôÐÔid
     */
    @TableId
    private Long attrId;
    /**
     * ÊôÐÔÃû
     */
    private String attrName;
    /**
     * ÊÇ·ñÐèÒª¼ìË÷[0-²»ÐèÒª£¬1-ÐèÒª]
     */
    private Integer searchType;
    /**
     * ÊôÐÔÍ¼±ê
     */
    private String icon;
    /**
     * ¿ÉÑ¡ÖµÁÐ±í[ÓÃ¶ººÅ·Ö¸ô]
     */
    private String valueSelect;
    /**
     * ÊôÐÔÀàÐÍ[0-ÏúÊÛÊôÐÔ£¬1-»ù±¾ÊôÐÔ£¬2-¼ÈÊÇÏúÊÛÊôÐÔÓÖÊÇ»ù±¾ÊôÐÔ]
     */
    private Integer attrType;
    /**
     * ÆôÓÃ×´Ì¬[0 - ½ûÓÃ£¬1 - ÆôÓÃ]
     */
    private Long enable;
    /**
     * 所属分类
     */
    private Long catelogId;
    /**
     * 快速展示
     */
    private Integer showDesc;

//    @TableField(exist = false)
//    private Long attrGroupId;



}

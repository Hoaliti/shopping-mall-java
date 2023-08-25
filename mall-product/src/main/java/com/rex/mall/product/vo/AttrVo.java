package com.rex.mall.product.vo;

import lombok.Data;

@Data
public class AttrVo {

    /**
     * 属性ID
     */
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


    private Long attrGroupId;


}

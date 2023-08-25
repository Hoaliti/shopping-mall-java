package com.rex.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.rex.common.valid.AddGroup;
import com.rex.common.valid.ListValue;
import com.rex.common.valid.UpdateGroup;
import com.rex.common.valid.UpdateStatusGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 *
 * @author rex
 * @email mydearzh@outlook.com
 * @date 2023-04-19 16:49:30
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @NotNull(message = "Edit Brand must have brand id",groups = {UpdateGroup.class})
    @Null(message = "Add Brand can not have brand id",groups = {AddGroup.class})
    @TableId
    private Long brandId;
    /**
     * 品牌名
     */
    @NotBlank(message = "Brand Name can not be blank",groups = {AddGroup.class, UpdateGroup.class})
    private String name;
    /**
     * 品牌logo地址
     */
    @NotBlank(groups = {AddGroup.class})
    @URL(message = "Logo is must valid URL",groups = {AddGroup.class, UpdateGroup.class})
    private String logo;
    /**
     * 介绍
     */
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    @NotNull(groups = {AddGroup.class, UpdateStatusGroup.class})
    @ListValue(vals = {0,1},groups = {AddGroup.class, UpdateStatusGroup.class})
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @NotNull(groups = {AddGroup.class})
    @Pattern(regexp = "^[a-zA-Z]$",message = "First Letter must be a letter",groups = {AddGroup.class, UpdateGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull(groups = {AddGroup.class})
    @Min(value = 0,message = "sort must be larger or equal to 0",groups = {AddGroup.class, UpdateGroup.class})
    private Integer sort;

}

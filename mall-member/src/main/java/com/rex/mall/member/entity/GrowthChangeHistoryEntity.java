package com.rex.mall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 成长值变化历史记录
 *
 * @author rex
 * @email mydearzh@outlook.com
 * @date 2023-05-08 17:06:24
 */
@Data
@TableName("ums_growth_change_history")
public class GrowthChangeHistoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * member_id
     */
    private Long memberId;
    /**
     * create_time
     */
    private Date createTime;
    /**
     * ¸Ä±äµÄÖµ£¨Õý¸º¼ÆÊý£©
     */
    private Integer changeCount;
    /**
     * ±¸×¢
     */
    private String note;
    /**
     * »ý·ÖÀ´Ô´[0-¹ºÎï£¬1-¹ÜÀíÔ±ÐÞ¸Ä]
     */
    private Integer sourceType;

}

package com.rex.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rex.common.utils.PageUtils;
import com.rex.mall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author rex
 * @email mydearzh@outlook.com
 * @date 2023-05-08 17:06:24
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


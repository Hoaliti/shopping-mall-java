package com.rex.mall.member.dao;

import com.rex.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 *
 * @author rex
 * @email mydearzh@outlook.com
 * @date 2023-05-08 17:06:24
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {

}

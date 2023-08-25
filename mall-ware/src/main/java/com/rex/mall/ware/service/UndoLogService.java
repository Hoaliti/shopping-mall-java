package com.rex.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rex.common.utils.PageUtils;
import com.rex.mall.ware.entity.UndoLogEntity;

import java.util.Map;

/**
 * @author rex
 * @email mydearzh@outlook.com
 * @date 2023-05-08 17:11:17
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


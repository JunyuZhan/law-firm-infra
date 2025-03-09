package com.lawfirm.model.system.mapper.upgrade;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.upgrade.Upgrade;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统升级Mapper接口
 */
@Mapper
public interface UpgradeMapper extends BaseMapper<Upgrade> {
} 
package com.lawfirm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.system.model.entity.SysUpgradeLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统升级日志Mapper接口
 */
@Mapper
public interface SysUpgradeLogMapper extends BaseMapper<SysUpgradeLog> {
} 
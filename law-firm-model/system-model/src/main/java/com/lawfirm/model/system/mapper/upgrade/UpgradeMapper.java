package com.lawfirm.model.system.mapper.upgrade;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.upgrade.Upgrade;
import com.lawfirm.model.system.constant.SystemSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 系统升级Mapper接口
 */
@Mapper
public interface UpgradeMapper extends BaseMapper<Upgrade> {
    
    /**
     * 查询最新的系统版本
     *
     * @return 最新系统版本
     */
    @Select(SystemSqlConstants.Upgrade.SELECT_LATEST_VERSION)
    Upgrade selectLatestVersion();
} 
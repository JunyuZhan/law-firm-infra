package com.lawfirm.model.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lawfirm.model.system.entity.SysConfig;
import com.lawfirm.model.system.constant.SystemSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统配置Mapper接口
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {
    
    /**
     * 根据配置键查询配置
     *
     * @param configKey 配置键
     * @return 配置信息
     */
    @Select(SystemSqlConstants.Config.SELECT_BY_KEY)
    SysConfig selectByKey(@Param("configKey") String configKey);
    
    /**
     * 根据配置分组查询配置列表
     *
     * @param configGroup 配置分组
     * @return 配置列表
     */
    @Select(SystemSqlConstants.Config.SELECT_BY_GROUP)
    List<SysConfig> selectByGroup(@Param("configGroup") String configGroup);

    /**
     * 查询配置列表
     * 
     * @param wrapper 查询条件
     * @return 配置列表
     */
    @Select("SELECT * FROM sys_config ${ew.customSqlSegment}")
    List<SysConfig> selectList(@Param(Constants.WRAPPER) Wrapper<SysConfig> wrapper);
} 
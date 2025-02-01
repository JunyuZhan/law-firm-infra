package com.lawfirm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统配置数据访问接口
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {
    
    /**
     * 根据配置键查询配置
     */
    @Select("SELECT * FROM sys_config WHERE config_key = #{key} AND deleted = 0")
    SysConfig selectByKey(@Param("key") String key);
    
    /**
     * 根据分组查询配置列表
     */
    @Select("SELECT * FROM sys_config WHERE group_name = #{group} AND deleted = 0")
    List<SysConfig> selectByGroup(@Param("group") String group);
    
    /**
     * 查询所有分组
     */
    @Select("SELECT DISTINCT group_name FROM sys_config WHERE deleted = 0")
    List<String> selectAllGroups();
    
    /**
     * 判断配置键是否存在
     */
    @Select("SELECT COUNT(*) FROM sys_config WHERE config_key = #{key} AND deleted = 0")
    boolean existsByKey(@Param("key") String key);
} 
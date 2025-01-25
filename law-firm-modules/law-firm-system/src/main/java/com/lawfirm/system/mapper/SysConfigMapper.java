package com.lawfirm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统参数配置Mapper接口
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 根据参数键名查询参数配置
     */
    @Select("SELECT * FROM sys_config WHERE config_key = #{configKey} AND deleted = 0")
    SysConfig selectByKey(String configKey);

    /**
     * 根据参数分组查询参数配置列表
     */
    @Select("SELECT * FROM sys_config WHERE group_name = #{groupName} AND deleted = 0 ORDER BY order_num")
    List<SysConfig> selectByGroup(String groupName);

    /**
     * 查询所有参数分组
     */
    @Select("SELECT DISTINCT group_name FROM sys_config WHERE deleted = 0")
    List<String> selectAllGroups();
} 
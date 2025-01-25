package com.lawfirm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统字典Mapper接口
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 根据类型查询字典列表
     */
    @Select("SELECT * FROM sys_dict WHERE type = #{type} AND deleted = 0 ORDER BY order_num")
    List<SysDict> selectByType(String type);

    /**
     * 根据类型和值查询字典
     */
    @Select("SELECT * FROM sys_dict WHERE type = #{type} AND value = #{value} AND deleted = 0")
    SysDict selectByTypeAndValue(String type, String value);

    /**
     * 查询所有字典类型
     */
    @Select("SELECT DISTINCT type FROM sys_dict WHERE deleted = 0")
    List<String> selectAllTypes();
} 
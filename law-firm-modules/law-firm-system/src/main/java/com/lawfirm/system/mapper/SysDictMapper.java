package com.lawfirm.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
    @Select("SELECT * FROM sys_dict WHERE dict_type = #{type} AND del_flag = 0 ORDER BY sort")
    List<SysDict> selectByType(@Param("type") String type);
    
    /**
     * 根据类型和值查询字典
     */
    @Select("SELECT * FROM sys_dict WHERE dict_type = #{type} AND dict_value = #{value} AND del_flag = 0 LIMIT 1")
    SysDict selectByTypeAndValue(@Param("type") String type, @Param("value") String value);
    
    /**
     * 查询所有字典类型
     */
    @Select("SELECT DISTINCT dict_type FROM sys_dict WHERE del_flag = 0")
    List<String> selectAllTypes();
} 
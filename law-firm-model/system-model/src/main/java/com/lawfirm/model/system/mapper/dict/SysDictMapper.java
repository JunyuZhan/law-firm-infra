package com.lawfirm.model.system.mapper.dict;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.dict.SysDict;
import com.lawfirm.model.system.constant.SystemSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 系统字典Mapper接口
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {
    
    /**
     * 根据字典类型查询字典
     *
     * @param dictType 字典类型
     * @return 字典信息
     */
    @Select(SystemSqlConstants.Dict.SELECT_BY_TYPE)
    SysDict selectByType(@Param("dictType") String dictType);
} 
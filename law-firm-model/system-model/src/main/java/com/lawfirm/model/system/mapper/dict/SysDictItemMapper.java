package com.lawfirm.model.system.mapper.dict;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.dict.SysDictItem;
import com.lawfirm.model.system.constant.SystemSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统字典项Mapper接口
 */
@Mapper
public interface SysDictItemMapper extends BaseMapper<SysDictItem> {
    
    /**
     * 根据字典类型查询字典项
     *
     * @param dictType 字典类型
     * @return 字典项列表
     */
    @Select(SystemSqlConstants.Dict.SELECT_ITEMS_BY_DICT_TYPE)
    List<SysDictItem> selectItemsByDictType(@Param("dictType") String dictType);
    
    /**
     * 根据字典ID查询字典项
     *
     * @param dictId 字典ID
     * @return 字典项列表
     */
    @Select(SystemSqlConstants.Dict.SELECT_ITEMS_BY_DICT_ID)
    List<SysDictItem> selectItemsByDictId(@Param("dictId") Long dictId);
} 
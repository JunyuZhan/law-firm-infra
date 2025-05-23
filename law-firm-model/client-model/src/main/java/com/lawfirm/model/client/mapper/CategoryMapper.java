package com.lawfirm.model.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.client.entity.common.ClientCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 分类Mapper接口
 */
@Mapper
public interface CategoryMapper extends BaseMapper<ClientCategory> {
    /**
     * 获取子分类列表
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    @Select("SELECT * FROM client_category WHERE parent_id = #{parentId} AND deleted = 0 ORDER BY sort_order ASC")
    List<ClientCategory> selectByParentId(@Param("parentId") Long parentId);

    /**
     * 统计某分类下客户数量
     * @param categoryId 分类ID
     * @return 客户数量
     */
    @org.apache.ibatis.annotations.Select("SELECT COUNT(*) FROM client_info WHERE category_id = #{categoryId} AND deleted = 0")
    Long countClientsByCategoryId(@org.apache.ibatis.annotations.Param("categoryId") Long categoryId);
} 
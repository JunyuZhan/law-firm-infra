package com.lawfirm.model.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.knowledge.entity.KnowledgeCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 知识分类Mapper接口
 */
@Mapper
public interface KnowledgeCategoryMapper extends BaseMapper<KnowledgeCategory> {

    /**
     * 根据父分类ID查询子分类列表
     */
    @Select("SELECT * FROM knowledge_category WHERE parent_id = #{parentId} ORDER BY sort")
    List<KnowledgeCategory> selectByParentId(@Param("parentId") Long parentId);

    /**
     * 根据分类路径查询分类列表
     */
    @Select("SELECT * FROM knowledge_category WHERE path LIKE CONCAT(#{path}, '%') ORDER BY sort")
    List<KnowledgeCategory> selectByPath(@Param("path") String path);

    /**
     * 根据分类编码查询分类
     */
    @Select("SELECT * FROM knowledge_category WHERE code = #{code}")
    KnowledgeCategory selectByCode(@Param("code") String code);
} 
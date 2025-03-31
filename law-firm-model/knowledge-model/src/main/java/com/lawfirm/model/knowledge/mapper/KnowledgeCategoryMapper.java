package com.lawfirm.model.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.knowledge.entity.KnowledgeCategory;
import com.lawfirm.model.knowledge.constant.KnowledgeSqlConstants;
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
     * 根据父ID查询分类列表
     */
    @Select(KnowledgeSqlConstants.Category.SELECT_BY_PARENT_ID)
    List<KnowledgeCategory> selectByParentId(@Param("parentId") Long parentId);

    /**
     * 根据路径查询子分类
     */
    @Select(KnowledgeSqlConstants.Category.SELECT_BY_PATH)
    List<KnowledgeCategory> selectByPath(@Param("path") String path);

    /**
     * 根据编码查询分类
     */
    @Select(KnowledgeSqlConstants.Category.SELECT_BY_CODE)
    KnowledgeCategory selectByCode(@Param("code") String code);
} 
package com.lawfirm.model.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.knowledge.entity.Knowledge;
import com.lawfirm.model.knowledge.constant.KnowledgeSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 知识库Mapper接口
 */
@Mapper
public interface KnowledgeMapper extends BaseMapper<Knowledge> {

    /**
     * 根据分类ID查询知识列表
     */
    @Select(KnowledgeSqlConstants.Knowledge.SELECT_BY_CATEGORY_ID)
    List<Knowledge> selectByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 根据标签ID查询知识列表
     */
    @Select(KnowledgeSqlConstants.Knowledge.SELECT_BY_TAG_ID)
    List<Knowledge> selectByTagId(@Param("tagId") Long tagId);

    /**
     * 根据关键词搜索知识
     */
    @Select(KnowledgeSqlConstants.Knowledge.SEARCH_BY_KEYWORD)
    List<Knowledge> searchByKeyword(@Param("keyword") String keyword);

    /**
     * 获取最新知识
     */
    @Select(KnowledgeSqlConstants.Knowledge.SELECT_LATEST_KNOWLEDGE)
    List<Knowledge> selectLatestKnowledge(@Param("limit") Integer limit);

    /**
     * 查询相关知识
     */
    @Select(KnowledgeSqlConstants.Knowledge.SELECT_RELATED)
    List<Knowledge> selectRelated(
            @Param("categoryId") Long categoryId,
            @Param("excludeId") Long excludeId,
            @Param("limit") Integer limit);

    /**
     * 统计分类的知识数量
     */
    @Select(KnowledgeSqlConstants.Knowledge.COUNT_BY_CATEGORY_ID)
    Integer countByCategoryId(@Param("categoryId") Long categoryId);
} 
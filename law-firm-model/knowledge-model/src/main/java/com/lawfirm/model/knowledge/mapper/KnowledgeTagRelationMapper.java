package com.lawfirm.model.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.knowledge.entity.KnowledgeTagRelation;
import com.lawfirm.model.knowledge.constant.KnowledgeSqlConstants;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 知识标签关系Mapper接口
 */
@Mapper
public interface KnowledgeTagRelationMapper extends BaseMapper<KnowledgeTagRelation> {

    /**
     * 查询知识对应的标签ID列表
     */
    @Select(KnowledgeSqlConstants.TagRelation.SELECT_TAG_IDS_BY_KNOWLEDGE_ID)
    List<Long> selectTagIdsByKnowledgeId(@Param("knowledgeId") Long knowledgeId);

    /**
     * 查询标签对应的知识ID列表
     */
    @Select(KnowledgeSqlConstants.TagRelation.SELECT_KNOWLEDGE_IDS_BY_TAG_ID)
    List<Long> selectKnowledgeIdsByTagId(@Param("tagId") Long tagId);

    /**
     * 删除知识的所有标签关系
     */
    @Delete(KnowledgeSqlConstants.TagRelation.DELETE_BY_KNOWLEDGE_ID)
    int deleteByKnowledgeId(@Param("knowledgeId") Long knowledgeId);

    /**
     * 删除标签的所有知识关系
     */
    @Delete(KnowledgeSqlConstants.TagRelation.DELETE_BY_TAG_ID)
    int deleteByTagId(@Param("tagId") Long tagId);
} 
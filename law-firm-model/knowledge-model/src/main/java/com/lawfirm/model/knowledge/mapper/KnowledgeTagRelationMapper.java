package com.lawfirm.model.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.knowledge.entity.KnowledgeTagRelation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 知识标签关联Mapper接口
 */
@Mapper
public interface KnowledgeTagRelationMapper extends BaseMapper<KnowledgeTagRelation> {

    /**
     * 获取知识的标签ID列表
     */
    @Select("SELECT tag_id FROM knowledge_tag_relation WHERE knowledge_id = #{knowledgeId}")
    List<Long> selectTagIdsByKnowledgeId(@Param("knowledgeId") Long knowledgeId);

    /**
     * 获取标签关联的知识ID列表
     */
    @Select("SELECT knowledge_id FROM knowledge_tag_relation WHERE tag_id = #{tagId}")
    List<Long> selectKnowledgeIdsByTagId(@Param("tagId") Long tagId);
    
    /**
     * 删除知识的所有标签关联
     */
    @Delete("DELETE FROM knowledge_tag_relation WHERE knowledge_id = #{knowledgeId}")
    int deleteByKnowledgeId(@Param("knowledgeId") Long knowledgeId);
    
    /**
     * 删除标签的所有知识关联
     */
    @Delete("DELETE FROM knowledge_tag_relation WHERE tag_id = #{tagId}")
    int deleteByTagId(@Param("tagId") Long tagId);
} 
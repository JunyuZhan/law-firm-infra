package com.lawfirm.model.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.knowledge.entity.Knowledge;
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
    @Select("SELECT * FROM knowledge WHERE category_id = #{categoryId}")
    List<Knowledge> selectByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 根据标签ID查询知识列表
     */
    @Select("SELECT k.* FROM knowledge k " +
            "JOIN knowledge_tag_relation r ON k.id = r.knowledge_id " +
            "WHERE r.tag_id = #{tagId} " +
            "ORDER BY k.create_time DESC")
    List<Knowledge> selectByTagId(@Param("tagId") Long tagId);

    /**
     * 根据关键词搜索知识
     */
    @Select("SELECT * FROM knowledge WHERE title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%')")
    List<Knowledge> searchByKeyword(@Param("keyword") String keyword);

    /**
     * 获取最新知识
     */
    @Select("SELECT * FROM knowledge ORDER BY create_time DESC LIMIT #{limit}")
    List<Knowledge> selectLatestKnowledge(@Param("limit") Integer limit);

    /**
     * 查询相关知识
     */
    @Select("SELECT * FROM knowledge " +
            "WHERE category_id = #{categoryId} " +
            "AND id != #{excludeId} " +
            "ORDER BY create_time DESC " +
            "LIMIT #{limit}")
    List<Knowledge> selectRelated(
            @Param("categoryId") Long categoryId,
            @Param("excludeId") Long excludeId,
            @Param("limit") Integer limit);

    /**
     * 统计分类的知识数量
     */
    @Select("SELECT COUNT(*) FROM knowledge WHERE category_id = #{categoryId}")
    Integer countByCategoryId(@Param("categoryId") Long categoryId);
} 
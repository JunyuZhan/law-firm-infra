package com.lawfirm.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.knowledge.entity.KnowledgeLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 知识点赞Mapper接口
 */
@Mapper
public interface KnowledgeLikeMapper extends BaseMapper<KnowledgeLike> {

    /**
     * 获取用户点赞列表
     */
    @Select("SELECT * FROM knowledge_like WHERE user_id = #{userId} AND deleted = 0 ORDER BY create_time DESC")
    List<KnowledgeLike> listByUser(Long userId);

    /**
     * 获取知识点赞列表
     */
    @Select("SELECT * FROM knowledge_like WHERE knowledge_id = #{knowledgeId} AND deleted = 0 ORDER BY create_time DESC")
    List<KnowledgeLike> listByKnowledge(Long knowledgeId);

    /**
     * 检查是否已点赞
     */
    @Select("SELECT COUNT(*) FROM knowledge_like WHERE knowledge_id = #{knowledgeId} AND user_id = #{userId} AND deleted = 0")
    Integer checkLike(Long knowledgeId, Long userId);
} 
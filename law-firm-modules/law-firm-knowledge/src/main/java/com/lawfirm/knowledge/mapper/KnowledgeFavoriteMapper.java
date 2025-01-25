package com.lawfirm.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.knowledge.entity.KnowledgeFavorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 知识收藏Mapper接口
 */
@Mapper
public interface KnowledgeFavoriteMapper extends BaseMapper<KnowledgeFavorite> {

    /**
     * 获取用户收藏列表
     */
    @Select("SELECT * FROM knowledge_favorite WHERE user_id = #{userId} AND deleted = 0 ORDER BY create_time DESC")
    List<KnowledgeFavorite> listByUser(Long userId);

    /**
     * 获取知识收藏列表
     */
    @Select("SELECT * FROM knowledge_favorite WHERE knowledge_id = #{knowledgeId} AND deleted = 0 ORDER BY create_time DESC")
    List<KnowledgeFavorite> listByKnowledge(Long knowledgeId);

    /**
     * 检查是否已收藏
     */
    @Select("SELECT COUNT(*) FROM knowledge_favorite WHERE knowledge_id = #{knowledgeId} AND user_id = #{userId} AND deleted = 0")
    Integer checkFavorite(Long knowledgeId, Long userId);
} 
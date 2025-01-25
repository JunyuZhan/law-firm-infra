package com.lawfirm.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.knowledge.entity.KnowledgeView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识浏览记录Mapper接口
 */
@Mapper
public interface KnowledgeViewMapper extends BaseMapper<KnowledgeView> {

    /**
     * 获取用户浏览记录
     */
    @Select("SELECT * FROM knowledge_view WHERE user_id = #{userId} AND deleted = 0 ORDER BY view_time DESC")
    List<KnowledgeView> listByUser(Long userId);

    /**
     * 获取知识浏览记录
     */
    @Select("SELECT * FROM knowledge_view WHERE knowledge_id = #{knowledgeId} AND deleted = 0 ORDER BY view_time DESC")
    List<KnowledgeView> listByKnowledge(Long knowledgeId);

    /**
     * 统计时间段内的浏览次数
     */
    @Select("SELECT COUNT(*) FROM knowledge_view WHERE knowledge_id = #{knowledgeId} AND deleted = 0 " +
            "AND view_time BETWEEN #{startTime} AND #{endTime}")
    Integer countViews(Long knowledgeId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计用户浏览次数
     */
    @Select("SELECT COUNT(*) FROM knowledge_view WHERE knowledge_id = #{knowledgeId} AND user_id = #{userId} AND deleted = 0")
    Integer countUserViews(Long knowledgeId, Long userId);
} 
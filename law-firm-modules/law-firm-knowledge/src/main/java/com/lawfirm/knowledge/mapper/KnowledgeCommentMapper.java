package com.lawfirm.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.knowledge.entity.KnowledgeComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 知识评论Mapper接口
 */
@Mapper
public interface KnowledgeCommentMapper extends BaseMapper<KnowledgeComment> {

    /**
     * 获取子评论列表
     */
    @Select("SELECT * FROM knowledge_comment WHERE parent_id = #{parentId} AND deleted = 0 ORDER BY create_time DESC")
    List<KnowledgeComment> listChildren(Long parentId);

    /**
     * 增加点赞数
     */
    @Update("UPDATE knowledge_comment SET like_count = like_count + 1 WHERE id = #{id}")
    void incrementLikeCount(Long id);

    /**
     * 减少点赞数
     */
    @Update("UPDATE knowledge_comment SET like_count = like_count - 1 WHERE id = #{id} AND like_count > 0")
    void decrementLikeCount(Long id);
} 
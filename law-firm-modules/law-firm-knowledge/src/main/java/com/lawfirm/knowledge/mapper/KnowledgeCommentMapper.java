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
    List<KnowledgeComment> selectChildren(Long parentId);

    /**
     * 获取评论树
     */
    @Select("WITH RECURSIVE comment_tree AS (" +
            "SELECT * FROM knowledge_comment WHERE knowledge_id = #{knowledgeId} AND parent_id IS NULL AND deleted = 0 " +
            "UNION ALL " +
            "SELECT c.* FROM knowledge_comment c " +
            "INNER JOIN comment_tree ct ON c.parent_id = ct.id " +
            "WHERE c.deleted = 0" +
            ") " +
            "SELECT * FROM comment_tree")
    List<KnowledgeComment> selectCommentTree(Long knowledgeId);

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
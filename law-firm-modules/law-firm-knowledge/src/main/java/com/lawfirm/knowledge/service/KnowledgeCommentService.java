package com.lawfirm.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.knowledge.entity.KnowledgeComment;

import java.util.List;

/**
 * 知识评论服务接口
 */
public interface KnowledgeCommentService extends IService<KnowledgeComment> {

    /**
     * 创建评论
     *
     * @param comment 评论信息
     * @return 评论ID
     */
    Long createComment(KnowledgeComment comment);

    /**
     * 删除评论
     *
     * @param id 评论ID
     */
    void deleteComment(Long id);

    /**
     * 查询知识的评论树
     *
     * @param knowledgeId 知识ID
     * @return 评论树
     */
    List<KnowledgeComment> getCommentTree(Long knowledgeId);

    /**
     * 查询子评论列表
     *
     * @param parentId 父评论ID
     * @return 子评论列表
     */
    List<KnowledgeComment> listChildren(Long parentId);

    /**
     * 点赞评论
     *
     * @param id     评论ID
     * @param userId 用户ID
     */
    void likeComment(Long id, Long userId);

    /**
     * 取消点赞
     *
     * @param id     评论ID
     * @param userId 用户ID
     */
    void unlikeComment(Long id, Long userId);
} 
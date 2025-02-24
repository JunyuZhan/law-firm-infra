package com.lawfirm.model.knowledge.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.knowledge.entity.Comment;
import com.lawfirm.model.knowledge.enums.CommentStatusEnum;
import com.lawfirm.model.knowledge.vo.CommentVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 评论服务接口
 */
public interface CommentService extends BaseService<Comment> {

    /**
     * 创建评论
     */
    CommentVO create(Long articleId, String content, Long parentId);

    /**
     * 更新评论
     */
    CommentVO update(Long id, String content);

    /**
     * 分页查询评论
     */
    Page<CommentVO> query(Long articleId, int page, int size);

    /**
     * 获取评论详情
     */
    CommentVO getDetail(Long id);

    /**
     * 获取文章的评论树
     */
    List<CommentVO> getCommentTree(Long articleId);

    /**
     * 获取评论的回复列表
     */
    List<CommentVO> getReplies(Long commentId);

    /**
     * 审核评论
     */
    void audit(Long id, CommentStatusEnum status, String remark);

    /**
     * 批量审核评论
     */
    void batchAudit(List<Long> ids, CommentStatusEnum status, String remark);

    /**
     * 点赞评论
     */
    void like(Long id);

    /**
     * 获取最新评论
     */
    List<CommentVO> getLatestComments(int limit);

    /**
     * 获取热门评论
     */
    List<CommentVO> getHotComments(int limit);

    /**
     * 获取用户的评论
     */
    Page<CommentVO> getUserComments(Long userId, int page, int size);

    /**
     * 统计文章评论数
     */
    void updateArticleCommentCount(Long articleId);

    /**
     * 批量统计文章评论数
     */
    void batchUpdateArticleCommentCount(List<Long> articleIds);

    /**
     * 检查评论是否合法（敏感词过滤等）
     */
    boolean checkCommentValid(String content);
} 
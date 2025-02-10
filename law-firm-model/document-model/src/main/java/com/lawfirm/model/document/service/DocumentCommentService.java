package com.lawfirm.model.document.service;

import com.lawfirm.model.document.entity.DocumentComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 文档评论服务接口
 */
public interface DocumentCommentService {
    
    /**
     * 添加评论
     */
    DocumentComment addComment(DocumentComment comment);
    
    /**
     * 回复评论
     */
    DocumentComment replyComment(Long parentCommentId, DocumentComment reply);
    
    /**
     * 更新评论
     */
    DocumentComment updateComment(DocumentComment comment);
    
    /**
     * 删除评论
     */
    void deleteComment(Long commentId);
    
    /**
     * 获取评论详情
     */
    DocumentComment getComment(Long commentId);
    
    /**
     * 获取文档的所有评论
     */
    List<DocumentComment> getDocumentComments(Long documentId);
    
    /**
     * 获取评论的所有回复
     */
    List<DocumentComment> getCommentReplies(Long commentId);
    
    /**
     * 分页查询评论
     */
    Page<DocumentComment> listComments(Long documentId, Pageable pageable);
    
    /**
     * 标记评论为已解决
     */
    void resolveComment(Long commentId, String resolution);
    
    /**
     * 获取未解决的评论
     */
    List<DocumentComment> getUnresolvedComments(Long documentId);
    
    /**
     * 获取指定页面的评论
     */
    List<DocumentComment> getPageComments(Long documentId, Integer pageNumber);
    
    /**
     * 获取评论数量
     */
    long countComments(Long documentId);
} 
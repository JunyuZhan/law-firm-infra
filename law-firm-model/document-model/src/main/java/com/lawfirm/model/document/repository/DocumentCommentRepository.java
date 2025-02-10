package com.lawfirm.model.document.repository;

import com.lawfirm.model.document.entity.DocumentComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文档评论Repository接口
 */
@Repository
public interface DocumentCommentRepository extends JpaRepository<DocumentComment, Long> {

    /**
     * 查询文档的所有评论
     */
    List<DocumentComment> findByDocumentIdOrderByCreateTimeDesc(Long documentId);
    
    /**
     * 分页查询文档评论
     */
    Page<DocumentComment> findByDocumentIdOrderByCreateTimeDesc(Long documentId, Pageable pageable);
    
    /**
     * 查询用户的所有评论
     */
    List<DocumentComment> findByUserIdOrderByCreateTimeDesc(Long userId);
    
    /**
     * 分页查询用户评论
     */
    Page<DocumentComment> findByUserIdOrderByCreateTimeDesc(Long userId, Pageable pageable);
    
    /**
     * 查询指定时间范围内的评论
     */
    List<DocumentComment> findByDocumentIdAndCreateTimeBetweenOrderByCreateTimeDesc(
            Long documentId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 查询评论回复
     */
    List<DocumentComment> findByParentIdOrderByCreateTimeAsc(Long parentId);
    
    /**
     * 统计文档评论数
     */
    long countByDocumentId(Long documentId);
    
    /**
     * 删除文档的所有评论
     */
    void deleteByDocumentId(Long documentId);
    
    /**
     * 查询需要审核的评论
     */
    @Query("SELECT c FROM DocumentComment c WHERE c.status = 'PENDING_REVIEW' ORDER BY c.createTime")
    List<DocumentComment> findPendingReviewComments();
} 
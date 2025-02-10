package com.lawfirm.modules.document.service.impl;

import com.lawfirm.model.document.repository.DocumentCommentRepository;
import com.lawfirm.modules.document.entity.DocumentComment;
import com.lawfirm.modules.document.service.DocumentCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档评论服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentCommentServiceImpl implements DocumentCommentService {

    private final DocumentCommentRepository documentCommentRepository;

    @Override
    @Transactional
    public DocumentComment addComment(DocumentComment comment) {
        // 设置初始状态
        comment.setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setIsDeleted(false);

        // 如果是回复评论，设置父评论ID
        if (comment.getParentId() != null) {
            DocumentComment parentComment = getComment(comment.getParentId());
            if (parentComment == null) {
                throw new IllegalArgumentException("父评论不存在");
            }
            // 如果父评论已经有父评论，则设置为父评论的父评论ID（保证最多两级评论）
            if (parentComment.getParentId() != null) {
                comment.setParentId(parentComment.getParentId());
            }
        }

        return documentCommentRepository.save(comment);
    }

    @Override
    @Transactional
    public DocumentComment replyComment(Long parentCommentId, DocumentComment reply) {
        DocumentComment parentComment = getComment(parentCommentId);
        if (parentComment == null) {
            throw new IllegalArgumentException("父评论不存在");
        }

        // 设置父评论ID
        reply.setParentId(parentCommentId)
                .setDocumentId(parentComment.getDocumentId());

        return addComment(reply);
    }

    @Override
    @Transactional
    public DocumentComment updateComment(DocumentComment comment) {
        DocumentComment existingComment = getComment(comment.getId());
        if (existingComment == null) {
            throw new IllegalArgumentException("评论不存在");
        }

        // 只允许更新评论内容和状态
        existingComment.setContent(comment.getContent())
                .setStatus(comment.getStatus())
                .setUpdateTime(LocalDateTime.now());

        return documentCommentRepository.save(existingComment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        DocumentComment comment = getComment(commentId);
        if (comment == null) {
            return;
        }

        // 逻辑删除评论
        comment.setIsDeleted(true)
                .setDeleteTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        documentCommentRepository.save(comment);

        // 删除所有回复
        List<DocumentComment> replies = getCommentReplies(commentId);
        for (DocumentComment reply : replies) {
            reply.setIsDeleted(true)
                    .setDeleteTime(LocalDateTime.now())
                    .setUpdateTime(LocalDateTime.now());
            documentCommentRepository.save(reply);
        }
    }

    @Override
    public DocumentComment getComment(Long commentId) {
        return documentCommentRepository.findById(commentId)
                .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                .orElse(null);
    }

    @Override
    public List<DocumentComment> getDocumentComments(Long documentId) {
        return documentCommentRepository.findByDocumentIdOrderByCreateTimeDesc(documentId).stream()
                .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                .filter(c -> c.getParentId() == null) // 只返回顶级评论
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentComment> getCommentReplies(Long commentId) {
        return documentCommentRepository.findByParentIdOrderByCreateTimeAsc(commentId).stream()
                .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<DocumentComment> listComments(Long documentId, Pageable pageable) {
        return documentCommentRepository.findByDocumentIdOrderByCreateTimeDesc(documentId, pageable);
    }

    @Override
    @Transactional
    public void resolveComment(Long commentId, String resolution) {
        DocumentComment comment = getComment(commentId);
        if (comment == null) {
            throw new IllegalArgumentException("评论不存在");
        }

        comment.setResolution(resolution)
                .setResolved(true)
                .setResolveTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        documentCommentRepository.save(comment);
    }

    @Override
    public List<DocumentComment> getUnresolvedComments(Long documentId) {
        return documentCommentRepository.findByDocumentIdOrderByCreateTimeDesc(documentId).stream()
                .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                .filter(c -> !Boolean.TRUE.equals(c.getResolved()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentComment> getPageComments(Long documentId, Integer pageNumber) {
        return documentCommentRepository.findByDocumentIdOrderByCreateTimeDesc(documentId).stream()
                .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                .filter(c -> pageNumber.equals(c.getPageNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public long countComments(Long documentId) {
        return documentCommentRepository.countByDocumentId(documentId);
    }
} 
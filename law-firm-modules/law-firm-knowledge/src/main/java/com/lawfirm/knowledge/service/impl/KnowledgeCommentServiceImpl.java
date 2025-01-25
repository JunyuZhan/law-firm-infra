package com.lawfirm.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.knowledge.entity.KnowledgeComment;
import com.lawfirm.knowledge.mapper.KnowledgeCommentMapper;
import com.lawfirm.knowledge.service.KnowledgeCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识评论服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KnowledgeCommentServiceImpl extends ServiceImpl<KnowledgeCommentMapper, KnowledgeComment> implements KnowledgeCommentService {

    private final KnowledgeCommentMapper commentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createComment(KnowledgeComment comment) {
        comment.setLikeCount(0);
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        save(comment);
        return comment.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long id) {
        // 递归删除子评论
        List<KnowledgeComment> children = commentMapper.selectChildren(id);
        if (!children.isEmpty()) {
            children.forEach(child -> deleteComment(child.getId()));
        }
        removeById(id);
    }

    @Override
    public List<KnowledgeComment> getCommentTree(Long knowledgeId) {
        return commentMapper.selectCommentTree(knowledgeId);
    }

    @Override
    public List<KnowledgeComment> listChildren(Long parentId) {
        return commentMapper.selectChildren(parentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void likeComment(Long id, Long userId) {
        commentMapper.incrementLikeCount(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlikeComment(Long id, Long userId) {
        commentMapper.decrementLikeCount(id);
    }
} 
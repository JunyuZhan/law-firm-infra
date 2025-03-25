package com.lawfirm.knowledge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.knowledge.entity.KnowledgeLike;
import com.lawfirm.knowledge.mapper.KnowledgeLikeMapper;
import com.lawfirm.knowledge.service.KnowledgeLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识库点赞服务实现类
 */
@Slf4j
@Service("knowledgeLikeServiceImpl")
@RequiredArgsConstructor
public class KnowledgeLikeServiceImpl extends ServiceImpl<KnowledgeLikeMapper, KnowledgeLike> implements KnowledgeLikeService {

    private final KnowledgeLikeMapper likeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createLike(Long knowledgeId, Long userId) {
        // 检查是否已点赞
        if (checkLike(knowledgeId, userId)) {
            return null;
        }

        // 创建点赞记录
        KnowledgeLike like = new KnowledgeLike();
        like.setKnowledgeId(knowledgeId);
        like.setUserId(userId);
        like.setCreateTime(LocalDateTime.now());
        like.setUpdateTime(LocalDateTime.now());
        save(like);
        return like.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLike(Long knowledgeId, Long userId) {
        LambdaQueryWrapper<KnowledgeLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KnowledgeLike::getKnowledgeId, knowledgeId)
                .eq(KnowledgeLike::getUserId, userId);
        remove(wrapper);
    }

    @Override
    public List<KnowledgeLike> listByUser(Long userId) {
        return likeMapper.selectByUserId(userId);
    }

    @Override
    public List<KnowledgeLike> listByKnowledge(Long knowledgeId) {
        return likeMapper.selectByKnowledgeId(knowledgeId);
    }

    @Override
    public boolean checkLike(Long knowledgeId, Long userId) {
        Integer count = likeMapper.checkLike(knowledgeId, userId);
        return count != null && count > 0;
    }
} 
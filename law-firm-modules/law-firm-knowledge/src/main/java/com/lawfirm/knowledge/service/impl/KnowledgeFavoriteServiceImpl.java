package com.lawfirm.knowledge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.knowledge.entity.KnowledgeFavorite;
import com.lawfirm.knowledge.mapper.KnowledgeFavoriteMapper;
import com.lawfirm.knowledge.service.KnowledgeFavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识收藏服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KnowledgeFavoriteServiceImpl extends ServiceImpl<KnowledgeFavoriteMapper, KnowledgeFavorite> implements KnowledgeFavoriteService {

    private final KnowledgeFavoriteMapper favoriteMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createFavorite(Long knowledgeId, Long userId) {
        // 检查是否已收藏
        if (checkFavorite(knowledgeId, userId)) {
            return null;
        }

        // 创建收藏记录
        KnowledgeFavorite favorite = new KnowledgeFavorite();
        favorite.setKnowledgeId(knowledgeId);
        favorite.setUserId(userId);
        favorite.setCreateTime(LocalDateTime.now());
        favorite.setUpdateTime(LocalDateTime.now());
        save(favorite);
        return favorite.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFavorite(Long knowledgeId, Long userId) {
        LambdaQueryWrapper<KnowledgeFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KnowledgeFavorite::getKnowledgeId, knowledgeId)
                .eq(KnowledgeFavorite::getUserId, userId);
        remove(wrapper);
    }

    @Override
    public List<KnowledgeFavorite> listByUser(Long userId) {
        return favoriteMapper.selectByUserId(userId);
    }

    @Override
    public List<KnowledgeFavorite> listByKnowledge(Long knowledgeId) {
        return favoriteMapper.selectByKnowledgeId(knowledgeId);
    }

    @Override
    public boolean checkFavorite(Long knowledgeId, Long userId) {
        Integer count = favoriteMapper.checkFavorite(knowledgeId, userId);
        return count != null && count > 0;
    }
} 
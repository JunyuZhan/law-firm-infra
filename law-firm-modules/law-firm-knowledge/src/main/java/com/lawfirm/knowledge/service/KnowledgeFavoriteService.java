package com.lawfirm.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.knowledge.entity.KnowledgeFavorite;

import java.util.List;

/**
 * 知识收藏服务接口
 */
public interface KnowledgeFavoriteService extends IService<KnowledgeFavorite> {

    /**
     * 收藏知识
     *
     * @param knowledgeId 知识ID
     * @param userId      用户ID
     * @return 收藏ID
     */
    Long createFavorite(Long knowledgeId, Long userId);

    /**
     * 取消收藏
     *
     * @param knowledgeId 知识ID
     * @param userId      用户ID
     */
    void deleteFavorite(Long knowledgeId, Long userId);

    /**
     * 查询用户收藏的知识列表
     *
     * @param userId 用户ID
     * @return 收藏列表
     */
    List<KnowledgeFavorite> listByUser(Long userId);

    /**
     * 查询知识的收藏用户列表
     *
     * @param knowledgeId 知识ID
     * @return 收藏列表
     */
    List<KnowledgeFavorite> listByKnowledge(Long knowledgeId);

    /**
     * 检查用户是否收藏了知识
     *
     * @param knowledgeId 知识ID
     * @param userId      用户ID
     * @return 是否收藏
     */
    boolean checkFavorite(Long knowledgeId, Long userId);
} 
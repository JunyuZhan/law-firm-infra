package com.lawfirm.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.knowledge.entity.KnowledgeLike;

import java.util.List;

/**
 * 知识点赞服务接口
 */
public interface KnowledgeLikeService extends IService<KnowledgeLike> {

    /**
     * 点赞知识
     *
     * @param knowledgeId 知识ID
     * @param userId      用户ID
     * @return 点赞ID
     */
    Long createLike(Long knowledgeId, Long userId);

    /**
     * 取消点赞
     *
     * @param knowledgeId 知识ID
     * @param userId      用户ID
     */
    void deleteLike(Long knowledgeId, Long userId);

    /**
     * 查询用户点赞的知识列表
     *
     * @param userId 用户ID
     * @return 点赞列表
     */
    List<KnowledgeLike> listByUser(Long userId);

    /**
     * 查询知识的点赞用户列表
     *
     * @param knowledgeId 知识ID
     * @return 点赞列表
     */
    List<KnowledgeLike> listByKnowledge(Long knowledgeId);

    /**
     * 检查用户是否点赞了知识
     *
     * @param knowledgeId 知识ID
     * @param userId      用户ID
     * @return 是否点赞
     */
    boolean checkLike(Long knowledgeId, Long userId);
} 
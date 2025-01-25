package com.lawfirm.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.knowledge.entity.KnowledgeView;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 知识浏览记录服务接口
 */
public interface KnowledgeViewService extends IService<KnowledgeView> {

    /**
     * 创建浏览记录
     *
     * @param knowledgeId 知识ID
     * @param userId      用户ID
     * @param ip         IP地址
     * @return 浏览记录ID
     */
    Long createView(Long knowledgeId, Long userId, String ip);

    /**
     * 查询用户的浏览记录
     *
     * @param userId 用户ID
     * @return 浏览记录列表
     */
    List<KnowledgeView> listByUser(Long userId);

    /**
     * 查询知识的浏览记录
     *
     * @param knowledgeId 知识ID
     * @return 浏览记录列表
     */
    List<KnowledgeView> listByKnowledge(Long knowledgeId);

    /**
     * 统计时间段内的浏览量
     *
     * @param knowledgeId 知识ID
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return 浏览量
     */
    Integer countViews(Long knowledgeId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计用户浏览次数
     *
     * @param knowledgeId 知识ID
     * @param userId      用户ID
     * @return 浏览次数
     */
    Integer countUserViews(Long knowledgeId, Long userId);
} 
package com.lawfirm.knowledge.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.knowledge.entity.Knowledge;

import java.util.List;

/**
 * 知识库服务接口
 */
public interface KnowledgeService extends IService<Knowledge> {

    /**
     * 创建知识
     *
     * @param knowledge 知识信息
     * @return 知识ID
     */
    Long createKnowledge(Knowledge knowledge);

    /**
     * 更新知识
     *
     * @param knowledge 知识信息
     */
    void updateKnowledge(Knowledge knowledge);

    /**
     * 删除知识
     *
     * @param id 知识ID
     */
    void deleteKnowledge(Long id);

    /**
     * 发布知识
     *
     * @param id 知识ID
     */
    void publishKnowledge(Long id);

    /**
     * 分页查询知识列表
     *
     * @param page     页码
     * @param size     每页大小
     * @param type     类型
     * @param status   状态
     * @param keyword  关键字
     * @return 知识列表
     */
    IPage<Knowledge> pageKnowledge(Integer page, Integer size, Integer type, Integer status, String keyword);

    /**
     * 根据分类查询知识列表
     *
     * @param categoryId 分类ID
     * @return 知识列表
     */
    List<Knowledge> listByCategory(Long categoryId);

    /**
     * 根据标签查询知识列表
     *
     * @param tag 标签
     * @return 知识列表
     */
    List<Knowledge> listByTag(String tag);

    /**
     * 根据作者查询知识列表
     *
     * @param authorId 作者ID
     * @return 知识列表
     */
    List<Knowledge> listByAuthor(Long authorId);

    /**
     * 浏览知识
     *
     * @param id     知识ID
     * @param userId 用户ID
     * @param ip     IP地址
     */
    void viewKnowledge(Long id, Long userId, String ip);

    /**
     * 点赞知识
     *
     * @param id     知识ID
     * @param userId 用户ID
     */
    void likeKnowledge(Long id, Long userId);

    /**
     * 取消点赞
     *
     * @param id     知识ID
     * @param userId 用户ID
     */
    void unlikeKnowledge(Long id, Long userId);

    /**
     * 收藏知识
     *
     * @param id     知识ID
     * @param userId 用户ID
     */
    void favoriteKnowledge(Long id, Long userId);

    /**
     * 取消收藏
     *
     * @param id     知识ID
     * @param userId 用户ID
     */
    void unfavoriteKnowledge(Long id, Long userId);
} 
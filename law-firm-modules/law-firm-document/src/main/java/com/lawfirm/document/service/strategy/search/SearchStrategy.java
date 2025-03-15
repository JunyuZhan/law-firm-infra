package com.lawfirm.document.service.strategy.search;

import com.lawfirm.model.document.entity.base.BaseDocument;

import java.util.List;
import java.util.Map;

/**
 * 文档搜索策略接口，定义文档搜索的方法
 */
public interface SearchStrategy {

    /**
     * 根据关键词搜索文档
     *
     * @param keyword 关键词
     * @param page 页码
     * @param size 每页大小
     * @return 文档搜索结果
     */
    List<BaseDocument> searchByKeyword(String keyword, int page, int size);

    /**
     * 根据条件搜索文档
     *
     * @param conditions 搜索条件
     * @param page 页码
     * @param size 每页大小
     * @return 文档搜索结果
     */
    List<BaseDocument> searchByConditions(Map<String, Object> conditions, int page, int size);

    /**
     * 获取搜索结果总数
     *
     * @param keyword 关键词
     * @return 搜索结果总数
     */
    long countByKeyword(String keyword);

    /**
     * 获取搜索结果总数
     *
     * @param conditions 搜索条件
     * @return 搜索结果总数
     */
    long countByConditions(Map<String, Object> conditions);

    /**
     * 获取搜索策略名称
     *
     * @return 搜索策略名称
     */
    String getStrategyName();
}

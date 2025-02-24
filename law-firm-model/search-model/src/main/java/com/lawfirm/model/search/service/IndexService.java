package com.lawfirm.model.search.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.search.entity.SearchIndex;
import com.lawfirm.model.search.vo.IndexVO;

import java.util.List;

/**
 * 索引服务接口
 */
public interface IndexService extends BaseService<SearchIndex> {

    /**
     * 创建索引
     */
    void createIndex(SearchIndex index);

    /**
     * 删除索引
     */
    void deleteIndex(String indexName);

    /**
     * 更新索引配置
     */
    void updateSettings(String indexName, String settings);

    /**
     * 更新索引映射
     */
    void updateMappings(String indexName, String mappings);

    /**
     * 获取索引信息
     */
    IndexVO getIndexInfo(String indexName);

    /**
     * 获取所有索引
     */
    List<IndexVO> listAllIndices();

    /**
     * 打开索引
     */
    void openIndex(String indexName);

    /**
     * 关闭索引
     */
    void closeIndex(String indexName);

    /**
     * 刷新索引
     */
    void refreshIndex(String indexName);

    /**
     * 获取索引统计信息
     */
    IndexVO getIndexStats(String indexName);

    /**
     * 检查索引是否存在
     */
    boolean existsIndex(String indexName);

    /**
     * 创建索引别名
     */
    void createAlias(String indexName, String alias);

    /**
     * 删除索引别名
     */
    void deleteAlias(String indexName, String alias);
} 
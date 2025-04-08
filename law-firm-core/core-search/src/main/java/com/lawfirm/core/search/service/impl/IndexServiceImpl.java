package com.lawfirm.core.search.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.core.search.handler.IndexHandler;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.search.entity.SearchIndex;
import com.lawfirm.model.search.mapper.SearchIndexMapper;
import com.lawfirm.model.search.service.IndexService;
import com.lawfirm.model.search.vo.IndexVO;
import com.lawfirm.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.index.IndexWriterConfig;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 索引服务实现
 */
@Slf4j
@Component("searchIndexServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class IndexServiceImpl extends BaseServiceImpl<SearchIndexMapper, SearchIndex> implements IndexService {

    /**
     * 批量操作的默认大小
     */
    private static final int DEFAULT_BATCH_SIZE = 1000;

    private final IndexHandler indexHandler;

    public IndexServiceImpl(SearchIndexMapper baseMapper, IndexHandler indexHandler) {
        super();
        this.indexHandler = indexHandler;
    }

    @Override
    public void createIndex(SearchIndex index) {
        try {
            String indexName = index.getIndexName();
            // 使用Lucene创建索引
            indexHandler.createIndex(indexName, null);
            
            // 创建别名
            String alias = index.getAlias();
            if (alias != null) {
                indexHandler.createAlias(indexName, alias);
            }
            
            // 保存索引信息到数据库
            save(index);
        } catch (IOException e) {
            log.error("创建索引失败", e);
            throw new RuntimeException("创建索引失败", e);
        }
    }

    @Override
    public void deleteIndex(String indexName) {
        try {
            indexHandler.deleteIndex(indexName);
            // 删除数据库中的索引信息
            baseMapper.delete(new QueryWrapper<SearchIndex>().eq("index_name", indexName));
        } catch (IOException e) {
            log.error("删除索引失败", e);
            throw new RuntimeException("删除索引失败", e);
        }
    }

    @Override
    public void updateSettings(String indexName, String settings) {
        try {
            // Lucene不支持直接更新settings
            log.warn("Lucene不支持直接更新settings，需要重建索引");
        } catch (Exception e) {
            log.error("更新索引设置失败", e);
            throw new RuntimeException("更新索引设置失败", e);
        }
    }

    @Override
    public void updateMappings(String indexName, String mappings) {
        try {
            // Lucene不支持直接更新mappings
            log.warn("Lucene不支持直接更新mappings，需要重建索引");
        } catch (Exception e) {
            log.error("更新索引映射失败", e);
            throw new RuntimeException("更新索引映射失败", e);
        }
    }

    @Override
    public IndexVO getIndexInfo(String indexName) {
        try {
            // 从数据库获取索引信息
            SearchIndex index = baseMapper.selectOne(
                new QueryWrapper<SearchIndex>().eq("index_name", indexName)
            );
            
            if (index == null) {
                throw new RuntimeException("索引不存在: " + indexName);
            }
            
            return convertToIndexVO(index);
        } catch (Exception e) {
            log.error("获取索引信息失败", e);
            throw new RuntimeException("获取索引信息失败", e);
        }
    }

    @Override
    public List<IndexVO> listAllIndices() {
        try {
            List<IndexVO> result = new ArrayList<>();
            List<SearchIndex> indices = baseMapper.selectList(null);
            for (SearchIndex index : indices) {
                result.add(convertToIndexVO(index));
            }
            return result;
        } catch (Exception e) {
            log.error("获取所有索引信息失败", e);
            throw new RuntimeException("获取所有索引信息失败", e);
        }
    }

    @Override
    public void openIndex(String indexName) {
        try {
            // Lucene索引无需显式打开
            log.info("Lucene索引无需显式打开: {}", indexName);
        } catch (Exception e) {
            log.error("打开索引失败", e);
            throw new RuntimeException("打开索引失败", e);
        }
    }

    @Override
    public void closeIndex(String indexName) {
        try {
            // 关闭Lucene索引
            indexHandler.closeIndex(indexName);
        } catch (IOException e) {
            log.error("关闭索引失败", e);
            throw new RuntimeException("关闭索引失败", e);
        }
    }

    @Override
    public void refreshIndex(String indexName) {
        try {
            indexHandler.refreshIndex(indexName);
        } catch (IOException e) {
            log.error("刷新索引失败", e);
            throw new RuntimeException("刷新索引失败", e);
        }
    }

    @Override
    public IndexVO getIndexStats(String indexName) {
        return getIndexInfo(indexName);
    }

    @Override
    public boolean existsIndex(String indexName) {
        try {
            return indexHandler.existsIndex(indexName);
        } catch (IOException e) {
            log.error("检查索引是否存在失败", e);
            throw new RuntimeException("检查索引是否存在失败", e);
        }
    }

    @Override
    public void createAlias(String indexName, String alias) {
        try {
            indexHandler.createAlias(indexName, alias);
            // 更新数据库中的别名信息
            SearchIndex updateEntity = new SearchIndex().setAlias(alias);
            baseMapper.update(updateEntity, new QueryWrapper<SearchIndex>().eq("index_name", indexName));
        } catch (IOException e) {
            log.error("创建别名失败", e);
            throw new RuntimeException("创建别名失败", e);
        }
    }

    @Override
    public void deleteAlias(String indexName, String alias) {
        try {
            indexHandler.deleteAlias(indexName, alias);
            // 更新数据库中的别名信息
            SearchIndex updateEntity = new SearchIndex().setAlias(null);
            baseMapper.update(updateEntity, new QueryWrapper<SearchIndex>().eq("index_name", indexName));
        } catch (IOException e) {
            log.error("删除别名失败", e);
            throw new RuntimeException("删除别名失败", e);
        }
    }

    /**
     * 转换为IndexVO
     */
    private IndexVO convertToIndexVO(SearchIndex index) {
        IndexVO vo = new IndexVO();
        vo.setIndexName(index.getIndexName());
        vo.setMappings("{}");  // Lucene没有固定映射
        vo.setSettings("{}");  // Lucene没有固定设置
        return vo;
    }

    // 实现BaseService接口的抽象方法
    @Override
    public boolean exists(QueryWrapper<SearchIndex> wrapper) {
        return baseMapper.exists(wrapper);
    }

    @Override
    public SearchIndex getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Page<SearchIndex> page(Page<SearchIndex> page, QueryWrapper<SearchIndex> wrapper) {
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public boolean saveBatch(List<SearchIndex> entities) {
        int size = entities.size();
        int batchSize = DEFAULT_BATCH_SIZE;
        for (int i = 0; i < size; i += batchSize) {
            int end = Math.min(i + batchSize, size);
            List<SearchIndex> batch = entities.subList(i, end);
            for (SearchIndex entity : batch) {
                save(entity);
            }
        }
        return true;
    }

    @Override
    public List<SearchIndex> list(QueryWrapper<SearchIndex> wrapper) {
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean updateBatch(List<SearchIndex> entities) {
        int size = entities.size();
        int batchSize = DEFAULT_BATCH_SIZE;
        for (int i = 0; i < size; i += batchSize) {
            int end = Math.min(i + batchSize, size);
            List<SearchIndex> batch = entities.subList(i, end);
            for (SearchIndex entity : batch) {
                update(entity);
            }
        }
        return true;
    }

    @Override
    public boolean update(SearchIndex entity) {
        return baseMapper.updateById(entity) > 0;
    }

    @Override
    public long count(QueryWrapper<SearchIndex> wrapper) {
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public boolean save(SearchIndex entity) {
        return baseMapper.insert(entity) > 0;
    }

    @Override
    public boolean remove(Long id) {
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public String getCurrentUsername() {
        return SecurityUtils.getUsername();
    }

    @Override
    public Long getCurrentUserId() {
        return SecurityUtils.getUserId();
    }

    @Override
    public Long getCurrentTenantId() {
        return 1L; // TODO: 从租户上下文获取
    }
} 
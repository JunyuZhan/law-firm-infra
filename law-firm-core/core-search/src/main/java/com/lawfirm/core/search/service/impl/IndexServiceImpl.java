package com.lawfirm.core.search.service.impl;

import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.core.search.handler.IndexHandler;
import com.lawfirm.core.search.utils.ElasticsearchUtils;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.search.entity.SearchIndex;
import com.lawfirm.model.search.mapper.SearchIndexMapper;
import com.lawfirm.model.search.service.IndexService;
import com.lawfirm.model.search.vo.IndexVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.StringReader;
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
            TypeMapping mapping = ElasticsearchUtils.generateMapping(index.getClass());
            indexHandler.createIndex(indexName, mapping);
            
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
            indexHandler.updateSettings(indexName, settings);
        } catch (IOException e) {
            log.error("更新索引设置失败", e);
            throw new RuntimeException("更新索引设置失败", e);
        }
    }

    @Override
    public void updateMappings(String indexName, String mappings) {
        try {
            indexHandler.updateMapping(indexName, TypeMapping.of(builder -> 
                builder.withJson(new StringReader(mappings))));
        } catch (IOException e) {
            log.error("更新索引映射失败", e);
            throw new RuntimeException("更新索引映射失败", e);
        }
    }

    @Override
    public IndexVO getIndexInfo(String indexName) {
        try {
            GetIndexResponse response = indexHandler.getIndex(indexName);
            return convertToIndexVO(indexName, response);
        } catch (IOException e) {
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
                GetIndexResponse response = indexHandler.getIndex(index.getIndexName());
                result.add(convertToIndexVO(index.getIndexName(), response));
            }
            return result;
        } catch (IOException e) {
            log.error("获取所有索引信息失败", e);
            throw new RuntimeException("获取所有索引信息失败", e);
        }
    }

    @Override
    public void openIndex(String indexName) {
        try {
            indexHandler.openIndex(indexName);
        } catch (IOException e) {
            log.error("打开索引失败", e);
            throw new RuntimeException("打开索引失败", e);
        }
    }

    @Override
    public void closeIndex(String indexName) {
        try {
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
    private IndexVO convertToIndexVO(String indexName, GetIndexResponse response) {
        IndexVO vo = new IndexVO();
        vo.setIndexName(indexName);
        vo.setMappings(response.get(indexName).mappings().toString());
        vo.setSettings(response.get(indexName).settings().toString());
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
        int size = ids.size();
        int batchSize = DEFAULT_BATCH_SIZE;
        for (int i = 0; i < size; i += batchSize) {
            int end = Math.min(i + batchSize, size);
            List<Long> batch = ids.subList(i, end);
            for (Long id : batch) {
                remove(id);
            }
        }
        return true;
    }
} 
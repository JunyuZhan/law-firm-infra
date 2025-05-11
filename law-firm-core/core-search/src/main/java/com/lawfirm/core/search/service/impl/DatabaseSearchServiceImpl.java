package com.lawfirm.core.search.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.search.dto.search.SearchRequestDTO;
import com.lawfirm.model.search.entity.SearchDoc;
import com.lawfirm.model.search.mapper.SearchDocMapper;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.search.vo.SearchVO;
import com.lawfirm.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 基于数据库的搜索服务实现
 */
@Slf4j
@Service("databaseSearchService")
@ConditionalOnProperty(prefix = "law-firm.search", name = "searchEngineType", havingValue = "database", matchIfMissing = true)
@Transactional(rollbackFor = Exception.class)
public class DatabaseSearchServiceImpl extends BaseServiceImpl<SearchDocMapper, SearchDoc> implements SearchService {

    @PostConstruct
    public void init() {
        log.info("初始化数据库搜索服务");
    }

    @Override
    public SearchVO search(SearchRequestDTO request) {
        log.info("数据库执行搜索: {}", request);
        
        // 构建查询条件
        QueryWrapper<SearchDoc> wrapper = buildSearchQuery(request);
        
        // 执行分页查询
        Page<SearchDoc> page = new Page<>(request.getPageNum(), request.getPageSize());
        Page<SearchDoc> resultPage = baseMapper.selectPage(page, wrapper);
        
        // 构建响应结果
        SearchVO result = new SearchVO();
        result.setTotal(resultPage.getTotal());
        result.setTook(resultPage.getSize());
        
        // 转换查询结果为Hit对象列表
        List<SearchVO.Hit> hits = resultPage.getRecords().stream()
                .map(doc -> {
                    SearchVO.Hit hit = new SearchVO.Hit();
                    hit.setId(doc.getId().toString());
                    hit.setSource(entityToMap(doc));
                    return hit;
                })
                .collect(Collectors.toList());
        
        result.setHits(hits);
        
        return result;
    }
    
    /**
     * 构建搜索查询条件
     */
    private QueryWrapper<SearchDoc> buildSearchQuery(SearchRequestDTO request) {
        QueryWrapper<SearchDoc> wrapper = new QueryWrapper<>();
        
        // 处理过滤条件
        if (request.getFilters() != null) {
            Map<String, Object> filters = request.getFilters();
            
            if (filters.containsKey("bizType")) {
                wrapper.eq("biz_type", filters.get("bizType"));
            }
            
            if (filters.containsKey("docType")) {
                wrapper.eq("doc_type", filters.get("docType"));
            }
            
            if (filters.containsKey("status")) {
                wrapper.eq("status", filters.get("status"));
            }
        }
        
        // 添加关键字搜索
        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            wrapper.and(w -> w
                .like("title", request.getKeyword())
                .or()
                .like("content", request.getKeyword())
                .or()
                .like("keywords", request.getKeyword())
            );
        }
        
        // 添加排序
        if (request.getSorts() != null && !request.getSorts().isEmpty()) {
            for (Map.Entry<String, String> entry : request.getSorts().entrySet()) {
                String field = entry.getKey();
                String order = entry.getValue();
                
                if ("asc".equalsIgnoreCase(order)) {
                    wrapper.orderByAsc(field);
                } else {
                    wrapper.orderByDesc(field);
                }
            }
        } else {
            // 默认按更新时间降序排序
            wrapper.orderByDesc("update_time");
        }
        
        return wrapper;
    }

    @Override
    public void bulkIndex(String indexName, List<Map<String, Object>> documents) {
        log.info("数据库批量索引文档: {}, 数量: {}", indexName, documents.size());
        documents.forEach(doc -> {
            SearchDoc searchDoc = mapToEntity(doc);
            baseMapper.insert(searchDoc);
        });
    }

    @Override
    public void indexDoc(String indexName, String id, Map<String, Object> document) {
        log.info("数据库索引文档: {}, ID: {}", indexName, id);
        SearchDoc searchDoc = mapToEntity(document);
        if (id != null) {
            try {
                searchDoc.setId(Long.parseLong(id));
            } catch (NumberFormatException e) {
                searchDoc.setDocId(id);
            }
        }
        baseMapper.insert(searchDoc);
    }

    @Override
    public void updateDoc(String indexName, String id, Map<String, Object> document) {
        log.info("数据库更新文档: {}, ID: {}", indexName, id);
        
        try {
            Long longId = Long.parseLong(id);
            SearchDoc searchDoc = mapToEntity(document);
            searchDoc.setId(longId);
            baseMapper.updateById(searchDoc);
        } catch (NumberFormatException e) {
            // 如果ID不是数字，则按docId查询
            QueryWrapper<SearchDoc> wrapper = new QueryWrapper<>();
            wrapper.eq("doc_id", id);
            SearchDoc existingDoc = baseMapper.selectOne(wrapper);
            
            if (existingDoc != null) {
                SearchDoc searchDoc = mapToEntity(document);
                searchDoc.setId(existingDoc.getId());
                baseMapper.updateById(searchDoc);
            } else {
                log.warn("未找到要更新的文档: {}", id);
            }
        }
    }

    @Override
    public void deleteDoc(String indexName, String id) {
        log.info("数据库删除文档: {}, ID: {}", indexName, id);
        
        try {
            Long longId = Long.parseLong(id);
            baseMapper.deleteById(longId);
        } catch (NumberFormatException e) {
            // 如果ID不是数字，则按docId删除
            QueryWrapper<SearchDoc> wrapper = new QueryWrapper<>();
            wrapper.eq("doc_id", id);
            baseMapper.delete(wrapper);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void bulkDelete(String indexName, List<String> ids) {
        log.info("数据库批量删除文档: {}, 数量: {}", indexName, ids.size());
        
        // 分离数字ID和非数字ID
        List<Long> numericIds = new ArrayList<>();
        List<String> nonNumericIds = new ArrayList<>();
        
        for (String id : ids) {
            try {
                numericIds.add(Long.parseLong(id));
            } catch (NumberFormatException e) {
                nonNumericIds.add(id);
            }
        }
        
        // 按主键ID批量删除 - 使用新API
        if (!numericIds.isEmpty()) {
            // 在MyBatis-Plus 3.5.9中，应该仍然可以使用deleteBatchIds，但标记为过时
            // 可以在未来版本中替换为对应的新API
            baseMapper.deleteBatchIds(numericIds);
        }
        
        // 按docId批量删除
        if (!nonNumericIds.isEmpty()) {
            QueryWrapper<SearchDoc> wrapper = new QueryWrapper<>();
            wrapper.in("doc_id", nonNumericIds);
            baseMapper.delete(wrapper);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<Map<String, Object>> multiGet(String indexName, List<String> ids) {
        log.info("数据库批量获取文档: {}, 数量: {}", indexName, ids.size());
        
        // 分离数字ID和非数字ID
        List<Long> numericIds = new ArrayList<>();
        List<String> nonNumericIds = new ArrayList<>();
        
        for (String id : ids) {
            try {
                numericIds.add(Long.parseLong(id));
            } catch (NumberFormatException e) {
                nonNumericIds.add(id);
            }
        }
        
        List<SearchDoc> docs = new ArrayList<>();
        
        // 按主键ID批量查询 - 使用新API
        if (!numericIds.isEmpty()) {
            // 在MyBatis-Plus 3.5.9中，应该仍然可以使用selectBatchIds，但标记为过时
            // 可以在未来版本中替换为对应的新API
            docs.addAll(baseMapper.selectBatchIds(numericIds));
        }
        
        // 按docId批量查询
        if (!nonNumericIds.isEmpty()) {
            QueryWrapper<SearchDoc> wrapper = new QueryWrapper<>();
            wrapper.in("doc_id", nonNumericIds);
            docs.addAll(baseMapper.selectList(wrapper));
        }
        
        return docs.stream()
                .map(this::entityToMap)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getDoc(String indexName, String id) {
        log.info("数据库获取文档: {}, ID: {}", indexName, id);
        
        SearchDoc doc = null;
        try {
            Long longId = Long.parseLong(id);
            doc = baseMapper.selectById(longId);
        } catch (NumberFormatException e) {
            // 如果ID不是数字，则按docId查询
            QueryWrapper<SearchDoc> wrapper = new QueryWrapper<>();
            wrapper.eq("doc_id", id);
            doc = baseMapper.selectOne(wrapper);
        }
        
        return doc != null ? entityToMap(doc) : null;
    }

    @Override
    public boolean existsDoc(String indexName, String id) {
        log.info("数据库检查文档是否存在: {}, ID: {}", indexName, id);
        
        try {
            Long longId = Long.parseLong(id);
            return baseMapper.selectById(longId) != null;
        } catch (NumberFormatException e) {
            // 如果ID不是数字，则按docId查询
            QueryWrapper<SearchDoc> wrapper = new QueryWrapper<>();
            wrapper.eq("doc_id", id);
            return baseMapper.selectCount(wrapper) > 0;
        }
    }

    @Override
    public long count(String indexName, Map<String, Object> query) {
        log.info("数据库统计文档数量: {}", indexName);
        QueryWrapper<SearchDoc> wrapper = new QueryWrapper<>();
        
        if (query != null && !query.isEmpty()) {
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                String field = entry.getKey();
                Object value = entry.getValue();
                
                if (value != null) {
                    if ("bizType".equals(field)) {
                        wrapper.eq("biz_type", value);
                    } else if ("status".equals(field)) {
                        wrapper.eq("status", value);
                    } else if ("docId".equals(field)) {
                        wrapper.eq("doc_id", value);
                    }
                }
            }
        }
        
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public void clearIndex(String indexName) {
        log.info("数据库清空索引: {}", indexName);
        baseMapper.delete(new QueryWrapper<>());
    }

    @Override
    public List<String> analyze(String indexName, String analyzer, String text) {
        log.warn("数据库不支持analyze操作");
        return Collections.emptyList();
    }

    @Override
    public List<String> suggest(String indexName, String field, String text) {
        log.warn("数据库不支持suggest操作");
        return Collections.emptyList();
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
        // 默认返回租户ID 1，可以在未来扩展实现从安全上下文获取
        return 1L;
    }

    /**
     * 将Map转换为SearchDoc实体
     */
    private SearchDoc mapToEntity(Map<String, Object> source) {
        SearchDoc doc = new SearchDoc();
        
        if (source.containsKey("id")) {
            Object id = source.get("id");
            if (id instanceof Number) {
                doc.setId(((Number) id).longValue());
            } else if (id instanceof String) {
                try {
                    doc.setId(Long.parseLong((String) id));
                } catch (NumberFormatException e) {
                    // 忽略无效ID
                }
            }
        }
        
        if (source.containsKey("indexId")) {
            Object indexId = source.get("indexId");
            if (indexId instanceof Number) {
                doc.setIndexId(((Number) indexId).longValue());
            } else if (indexId instanceof String) {
                try {
                    doc.setIndexId(Long.parseLong((String) indexId));
                } catch (NumberFormatException e) {
                    // 忽略无效ID
                }
            }
        }
        
        if (source.containsKey("docId")) {
            doc.setDocId((String) source.get("docId"));
        }
        
        if (source.containsKey("bizId")) {
            doc.setBizId((String) source.get("bizId"));
        }
        
        if (source.containsKey("bizType")) {
            doc.setBizType((String) source.get("bizType"));
        }
        
        if (source.containsKey("content")) {
            doc.setContent((String) source.get("content"));
        }
        
        if (source.containsKey("status")) {
            Object status = source.get("status");
            if (status instanceof Number) {
                doc.setStatus(((Number) status).intValue());
            } else if (status instanceof String) {
                try {
                    doc.setStatus(Integer.parseInt((String) status));
                } catch (NumberFormatException e) {
                    // 忽略无效状态
                }
            }
        }
        
        if (source.containsKey("errorMsg")) {
            doc.setErrorMsg((String) source.get("errorMsg"));
        }
        
        if (source.containsKey("retryCount")) {
            Object retryCount = source.get("retryCount");
            if (retryCount instanceof Number) {
                doc.setRetryCount(((Number) retryCount).intValue());
            }
        }
        
        if (source.containsKey("lastRetryTime")) {
            Object lastRetryTime = source.get("lastRetryTime");
            if (lastRetryTime instanceof Number) {
                doc.setLastRetryTime(((Number) lastRetryTime).longValue());
            }
        }
        
        if (source.containsKey("indexTime")) {
            Object indexTime = source.get("indexTime");
            if (indexTime instanceof Number) {
                doc.setIndexTime(((Number) indexTime).longValue());
            }
        }
        
        return doc;
    }
    
    /**
     * 将SearchDoc实体转换为Map
     */
    private Map<String, Object> entityToMap(SearchDoc doc) {
        Map<String, Object> map = new HashMap<>();
        
        map.put("id", doc.getId());
        map.put("indexId", doc.getIndexId());
        map.put("docId", doc.getDocId());
        map.put("bizId", doc.getBizId());
        map.put("bizType", doc.getBizType());
        map.put("content", doc.getContent());
        map.put("status", doc.getStatus());
        map.put("errorMsg", doc.getErrorMsg());
        map.put("retryCount", doc.getRetryCount());
        map.put("lastRetryTime", doc.getLastRetryTime());
        map.put("indexTime", doc.getIndexTime());
        map.put("createTime", doc.getCreateTime());
        map.put("updateTime", doc.getUpdateTime());
        
        return map;
    }
} 
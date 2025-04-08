package com.lawfirm.core.search.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.core.search.handler.DocumentHandler;
import com.lawfirm.core.search.handler.SearchHandler;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.search.dto.search.SearchRequestDTO;
import com.lawfirm.model.search.entity.SearchDoc;
import com.lawfirm.model.search.mapper.SearchDocMapper;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.search.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import com.lawfirm.common.security.utils.SecurityUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 搜索服务实现
 */
@Slf4j
@Component
@Primary
@Transactional(rollbackFor = Exception.class)
public class SearchServiceImpl extends BaseServiceImpl<SearchDocMapper, SearchDoc> implements SearchService {

    @Autowired
    private SearchHandler searchHandler;

    @Autowired
    private DocumentHandler documentHandler;

    @Override
    public SearchVO search(SearchRequestDTO request) {
        try {
            return searchHandler.search(request);
        } catch (IOException e) {
            log.error("搜索失败", e);
            SearchVO result = new SearchVO();
            result.setTotal(0L);
            result.setMaxScore(0f);
            result.setTook(0L);
            result.setTimedOut(true);
            result.setHits(Collections.emptyList());
            return result;
        }
    }

    @Override
    public void bulkIndex(String indexName, List<Map<String, Object>> documents) {
        try {
            documentHandler.bulkIndex(indexName, documents);
        } catch (IOException e) {
            log.error("批量索引失败", e);
            throw new RuntimeException("批量索引失败", e);
        }
    }

    @Override
    public void indexDoc(String indexName, String id, Map<String, Object> document) {
        try {
            documentHandler.indexDoc(indexName, id, document);
        } catch (IOException e) {
            log.error("索引文档失败", e);
            throw new RuntimeException("索引文档失败", e);
        }
    }

    @Override
    public void updateDoc(String indexName, String id, Map<String, Object> document) {
        try {
            documentHandler.updateDoc(indexName, id, document);
        } catch (IOException e) {
            log.error("更新文档失败", e);
            throw new RuntimeException("更新文档失败", e);
        }
    }

    @Override
    public void deleteDoc(String indexName, String id) {
        try {
            documentHandler.deleteDoc(indexName, id);
        } catch (IOException e) {
            log.error("删除文档失败", e);
            throw new RuntimeException("删除文档失败", e);
        }
    }

    @Override
    public void bulkDelete(String indexName, List<String> ids) {
        try {
            documentHandler.bulkDelete(indexName, ids);
        } catch (IOException e) {
            log.error("批量删除失败", e);
            throw new RuntimeException("批量删除失败", e);
        }
    }

    @Override
    public Map<String, Object> getDoc(String indexName, String id) {
        try {
            return documentHandler.getDoc(indexName, id);
        } catch (IOException e) {
            log.error("获取文档失败", e);
            throw new RuntimeException("获取文档失败", e);
        }
    }

    @Override
    public List<Map<String, Object>> multiGet(String indexName, List<String> ids) {
        try {
            return documentHandler.multiGet(indexName, ids);
        } catch (IOException e) {
            log.error("批量获取文档失败", e);
            throw new RuntimeException("批量获取文档失败", e);
        }
    }

    @Override
    public boolean existsDoc(String indexName, String id) {
        try {
            return documentHandler.existsDoc(indexName, id);
        } catch (IOException e) {
            log.error("检查文档是否存在失败", e);
            throw new RuntimeException("检查文档是否存在失败", e);
        }
    }

    @Override
    public long count(String indexName, Map<String, Object> query) {
        try {
            SearchRequestDTO request = new SearchRequestDTO();
            request.setIndexName(indexName);
            request.setFilters(query);
            request.setPageNum(0);
            request.setPageSize(0);
            SearchVO result = searchHandler.search(request);
            return result.getTotal();
        } catch (IOException e) {
            log.error("获取文档数量失败", e);
            throw new RuntimeException("获取文档数量失败", e);
        }
    }

    @Override
    public void clearIndex(String indexName) {
        try {
            SearchRequestDTO request = new SearchRequestDTO();
            request.setIndexName(indexName);
            request.setPageNum(0);
            request.setPageSize(1000);
            
            SearchVO result = searchHandler.search(request);
            List<String> ids = result.getHits().stream()
                .map(SearchVO.Hit::getId)
                .collect(java.util.stream.Collectors.toList());
            
            if (!ids.isEmpty()) {
                bulkDelete(indexName, ids);
            }
        } catch (IOException e) {
            log.error("清空索引失败", e);
            throw new RuntimeException("清空索引失败", e);
        }
    }

    @Override
    public List<String> analyze(String indexName, String analyzer, String text) {
        try {
            return searchHandler.suggest(indexName, analyzer, text);
        } catch (IOException e) {
            log.error("分析文本失败", e);
            throw new RuntimeException("分析文本失败", e);
        }
    }

    @Override
    public List<String> suggest(String indexName, String field, String text) {
        try {
            return searchHandler.suggest(indexName, field, text);
        } catch (IOException e) {
            log.error("获取建议失败", e);
            throw new RuntimeException("获取建议失败", e);
        }
    }

    @Override
    public long count(QueryWrapper<SearchDoc> wrapper) {
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public boolean save(SearchDoc entity) {
        return baseMapper.insert(entity) > 0;
    }

    @Override
    public boolean remove(Long id) {
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        int size = ids.size();
        int batchSize = 1000;
        for (int i = 0; i < size; i += batchSize) {
            int end = Math.min(i + batchSize, size);
            List<Long> batch = ids.subList(i, end);
            for (Long id : batch) {
                remove(id);
            }
        }
        return true;
    }

    @Override
    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
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
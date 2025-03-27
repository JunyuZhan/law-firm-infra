package com.lawfirm.document.service.strategy.search;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.mapper.DocumentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 数据库搜索策略实现，当Elasticsearch不可用时使用数据库实现搜索
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "search", name = "enabled", havingValue = "true", matchIfMissing = false)
public class DatabaseSearchStrategy implements SearchStrategy {

    private final DocumentMapper documentMapper;

    @Override
    public List<BaseDocument> searchByKeyword(String keyword, int page, int size) {
        log.debug("使用数据库搜索文档，关键词：{}, 页码：{}, 页大小：{}", keyword, page, size);
        
        LambdaQueryWrapper<BaseDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(BaseDocument::getTitle, keyword)
               .or()
               .like(BaseDocument::getKeywords, keyword)
               .or()
               .like(BaseDocument::getDescription, keyword);
        
        Page<BaseDocument> pageParam = new Page<>(page, size);
        Page<BaseDocument> resultPage = documentMapper.selectPage(pageParam, wrapper);
        
        return resultPage.getRecords();
    }

    @Override
    public List<BaseDocument> searchByConditions(Map<String, Object> conditions, int page, int size) {
        log.debug("使用数据库搜索文档，条件：{}, 页码：{}, 页大小：{}", conditions, page, size);
        
        LambdaQueryWrapper<BaseDocument> wrapper = new LambdaQueryWrapper<>();
        
        // 处理不同的查询条件
        if (conditions.containsKey("title")) {
            wrapper.like(BaseDocument::getTitle, conditions.get("title"));
        }
        
        if (conditions.containsKey("docType")) {
            wrapper.eq(BaseDocument::getDocType, conditions.get("docType"));
        }
        
        if (conditions.containsKey("businessType")) {
            wrapper.eq(BaseDocument::getBusinessType, conditions.get("businessType"));
        }
        
        if (conditions.containsKey("businessId")) {
            wrapper.eq(BaseDocument::getBusinessId, conditions.get("businessId"));
        }
        
        if (conditions.containsKey("keywords")) {
            wrapper.like(BaseDocument::getKeywords, conditions.get("keywords"));
        }
        
        Page<BaseDocument> pageParam = new Page<>(page, size);
        Page<BaseDocument> resultPage = documentMapper.selectPage(pageParam, wrapper);
        
        return resultPage.getRecords();
    }

    @Override
    public long countByKeyword(String keyword) {
        log.debug("使用数据库计算搜索结果数量，关键词：{}", keyword);
        
        LambdaQueryWrapper<BaseDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(BaseDocument::getTitle, keyword)
               .or()
               .like(BaseDocument::getKeywords, keyword)
               .or()
               .like(BaseDocument::getDescription, keyword);
        
        return documentMapper.selectCount(wrapper);
    }

    @Override
    public long countByConditions(Map<String, Object> conditions) {
        log.debug("使用数据库计算搜索结果数量，条件：{}", conditions);
        
        LambdaQueryWrapper<BaseDocument> wrapper = new LambdaQueryWrapper<>();
        
        // 处理不同的查询条件
        if (conditions.containsKey("title")) {
            wrapper.like(BaseDocument::getTitle, conditions.get("title"));
        }
        
        if (conditions.containsKey("docType")) {
            wrapper.eq(BaseDocument::getDocType, conditions.get("docType"));
        }
        
        if (conditions.containsKey("businessType")) {
            wrapper.eq(BaseDocument::getBusinessType, conditions.get("businessType"));
        }
        
        if (conditions.containsKey("businessId")) {
            wrapper.eq(BaseDocument::getBusinessId, conditions.get("businessId"));
        }
        
        if (conditions.containsKey("keywords")) {
            wrapper.like(BaseDocument::getKeywords, conditions.get("keywords"));
        }
        
        return documentMapper.selectCount(wrapper);
    }

    @Override
    public String getStrategyName() {
        return "database";
    }
} 
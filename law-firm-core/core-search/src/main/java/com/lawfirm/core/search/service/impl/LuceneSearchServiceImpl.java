package com.lawfirm.core.search.service.impl;

import com.lawfirm.core.search.config.SearchProperties;
import com.lawfirm.core.search.handler.LuceneManager;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.search.dto.search.SearchRequestDTO;
import com.lawfirm.model.search.entity.SearchDoc;
import com.lawfirm.model.search.enums.SearchTypeEnum;
import com.lawfirm.model.search.mapper.SearchDocMapper;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.search.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.security.utils.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基于Lucene的搜索服务实现
 */
@Slf4j
@Service("luceneSearchService")
@ConditionalOnProperty(prefix = "law-firm.search", name = "searchEngineType", havingValue = "lucene")
public class LuceneSearchServiceImpl extends BaseServiceImpl<SearchDocMapper, SearchDoc> implements SearchService {

    @Autowired
    private LuceneManager luceneManager;
    
    @Autowired
    private SearchProperties searchProperties;
    
    @PostConstruct
    public void init() {
        log.info("初始化Lucene搜索服务");
    }

    @Override
    public SearchVO search(SearchRequestDTO request) {
        long startTime = System.currentTimeMillis();
        SearchVO result = new SearchVO();
        
        try {
            String indexName = request.getIndexName();
            Query query = buildQuery(request);
            
            Sort sort = null;
            if (request.getSorts() != null && !request.getSorts().isEmpty()) {
                sort = buildSort(request.getSorts());
            }
            
            // 执行搜索
            TopDocs topDocs;
            if (sort != null) {
                topDocs = luceneManager.search(indexName, query, sort, request.getPageSize());
            } else {
                topDocs = luceneManager.search(indexName, query, request.getPageSize());
            }
            
            // 处理结果
            List<SearchVO.Hit> hits = processSearchResults(indexName, topDocs, request);
            
            result.setTotal(topDocs.totalHits.value);
            result.setMaxScore(topDocs.scoreDocs.length > 0 ? topDocs.scoreDocs[0].score : 0f);
            result.setTook(System.currentTimeMillis() - startTime);
            result.setTimedOut(false);
            result.setHits(hits);
            
            return result;
            
        } catch (Exception e) {
            log.error("搜索失败", e);
            result.setTotal(0L);
            result.setTimedOut(true);
            result.setTook(System.currentTimeMillis() - startTime);
            result.setHits(Collections.emptyList());
            return result;
        }
    }
    
    /**
     * 构建查询条件
     */
    private Query buildQuery(SearchRequestDTO request) {
        try {
            BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();
            
            // 添加关键词查询
            if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
                String[] fields = request.getFields() != null ? 
                    request.getFields().toArray(new String[0]) : 
                    new String[]{"content"};
                
                MultiFieldQueryParser parser = new MultiFieldQueryParser(
                    fields, 
                    luceneManager.getAnalyzer()
                );
                
                Query keywordQuery = parser.parse(request.getKeyword());
                booleanQueryBuilder.add(keywordQuery, BooleanClause.Occur.MUST);
            }
            
            // 添加过滤条件
            if (request.getFilters() != null) {
                for (Map.Entry<String, Object> entry : request.getFilters().entrySet()) {
                    String field = entry.getKey();
                    Object value = entry.getValue();
                    
                    if (value != null) {
                        Term term = new Term(field, value.toString());
                        TermQuery termQuery = new TermQuery(term);
                        booleanQueryBuilder.add(termQuery, BooleanClause.Occur.FILTER);
                    }
                }
            }
            
            return booleanQueryBuilder.build();
        } catch (ParseException e) {
            log.error("解析查询条件失败", e);
            return new MatchAllDocsQuery();
        }
    }

    /**
     * 构建排序
     */
    private Sort buildSort(Map<String, String> sorts) {
        List<SortField> sortFields = new ArrayList<>();
        
        for (Map.Entry<String, String> entry : sorts.entrySet()) {
            String field = entry.getKey();
            String order = entry.getValue();
            boolean reverse = "desc".equalsIgnoreCase(order);
            
            // 根据字段类型创建不同的排序字段
            SortField sortField;
            if ("_score".equals(field)) {
                sortField = SortField.FIELD_SCORE;
            } else {
                sortField = new SortField(field, SortField.Type.STRING, reverse);
            }
            
            sortFields.add(sortField);
        }
        
        if (sortFields.isEmpty()) {
            // 默认按评分排序
            sortFields.add(SortField.FIELD_SCORE);
        }
        
        return new Sort(sortFields.toArray(new SortField[0]));
    }
    
    /**
     * 处理搜索结果
     */
    private List<SearchVO.Hit> processSearchResults(String indexName, TopDocs topDocs, SearchRequestDTO request) {
        List<SearchVO.Hit> hits = new ArrayList<>();
        
        try {
            IndexSearcher searcher = luceneManager.getIndexSearcher(indexName);
            
            // 是否需要高亮
            Highlighter highlighter = null;
            if (searchProperties.getLucene().isHighlightEnabled() && request.getHighlights() != null && !request.getHighlights().isEmpty()) {
                Query query = buildQuery(request);
                QueryScorer scorer = new QueryScorer(query);
                
                String preTag = searchProperties.getLucene().getHighlightPreTag();
                String postTag = searchProperties.getLucene().getHighlightPostTag();
                
                org.apache.lucene.search.highlight.Formatter formatter = new SimpleHTMLFormatter(preTag, postTag);
                highlighter = new Highlighter(formatter, scorer);
                highlighter.setTextFragmenter(new SimpleFragmenter(150));
            }
            
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc = searcher.storedFields().document(scoreDoc.doc);
                SearchVO.Hit hit = new SearchVO.Hit();
                
                hit.setIndex(indexName);
                hit.setId(doc.get("id"));
                hit.setScore(scoreDoc.score);
                
                // 处理源文档
                Map<String, Object> source = new HashMap<>();
                for (IndexableField field : doc.getFields()) {
                    String name = field.name();
                    
                    // 排除特定字段
                    if (request.getExcludes() != null && request.getExcludes().contains(name)) {
                        continue;
                    }
                    
                    // 只包含特定字段
                    if (request.getIncludes() != null && !request.getIncludes().isEmpty() 
                            && !request.getIncludes().contains(name)) {
                        continue;
                    }
                    
                    // 获取字段值
                    if (field.stringValue() != null) {
                        source.put(name, field.stringValue());
                    } else if (field.numericValue() != null) {
                        source.put(name, field.numericValue());
                    }
                }
                
                hit.setSource(source);
                
                // 处理高亮
                if (highlighter != null) {
                    Map<String, List<String>> highlightMap = new HashMap<>();
                    
                    for (String fieldName : request.getHighlights()) {
                        String text = doc.get(fieldName);
                        if (text != null) {
                            try {
                                String[] fragments = highlighter.getBestFragments(
                                    luceneManager.getAnalyzer(),
                                    fieldName,
                                    text,
                                    3 // 最多返回3个片段
                                );
                                
                                if (fragments.length > 0) {
                                    highlightMap.put(fieldName, Arrays.asList(fragments));
                                }
                            } catch (InvalidTokenOffsetsException e) {
                                log.warn("高亮处理出错: {}", e.getMessage());
                            }
                        }
                    }
                    
                    if (!highlightMap.isEmpty()) {
                        hit.setHighlight(highlightMap);
                    }
                }
                
                hits.add(hit);
            }
        } catch (IOException e) {
            log.error("处理搜索结果失败: {}", e.getMessage());
        }
        
        return hits;
    }

    @Override
    public void bulkIndex(String indexName, List<Map<String, Object>> documents) {
        try {
            List<Document> luceneDocuments = new ArrayList<>();
            for (Map<String, Object> document : documents) {
                // 确保文档有ID
                if (!document.containsKey("id")) {
                    log.warn("文档缺少ID字段，跳过索引");
                    continue;
                }
                luceneDocuments.add(mapToDocument(document));
            }
            
            luceneManager.addDocuments(indexName, luceneDocuments);
            log.info("批量索引文档成功，索引: {}, 文档数: {}", indexName, luceneDocuments.size());
        } catch (IOException e) {
            log.error("批量索引文档失败", e);
            throw new RuntimeException("批量索引文档失败", e);
        }
    }

    @Override
    public void indexDoc(String indexName, String id, Map<String, Object> document) {
        try {
            // 确保文档包含ID
            if (!document.containsKey("id")) {
                document = new HashMap<>(document);
                document.put("id", id);
            }
            
            Document doc = mapToDocument(document);
            luceneManager.addDocument(indexName, doc);
            log.info("索引文档成功，索引: {}, ID: {}", indexName, id);
        } catch (IOException e) {
            log.error("索引文档失败", e);
            throw new RuntimeException("索引文档失败", e);
        }
    }

    @Override
    public void updateDoc(String indexName, String id, Map<String, Object> document) {
        try {
            // 确保文档包含ID
            if (!document.containsKey("id")) {
                document = new HashMap<>(document);
                document.put("id", id);
            }
            
            Document doc = mapToDocument(document);
            Term term = new Term("id", id);
            luceneManager.updateDocument(indexName, term, doc);
            log.info("更新文档成功，索引: {}, ID: {}", indexName, id);
        } catch (IOException e) {
            log.error("更新文档失败", e);
            throw new RuntimeException("更新文档失败", e);
        }
    }

    @Override
    public void deleteDoc(String indexName, String id) {
        try {
            Term term = new Term("id", id);
            luceneManager.deleteDocument(indexName, new TermQuery(term));
            log.info("删除文档成功，索引: {}, ID: {}", indexName, id);
        } catch (IOException e) {
            log.error("删除文档失败", e);
            throw new RuntimeException("删除文档失败", e);
        }
    }

    @Override
    public void bulkDelete(String indexName, List<String> ids) {
        try {
            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            for (String id : ids) {
                Term term = new Term("id", id);
                builder.add(new TermQuery(term), BooleanClause.Occur.SHOULD);
            }
            
            luceneManager.deleteDocument(indexName, builder.build());
            log.info("批量删除文档成功，索引: {}, ID数量: {}", indexName, ids.size());
        } catch (IOException e) {
            log.error("批量删除文档失败", e);
            throw new RuntimeException("批量删除文档失败", e);
        }
    }

    @Override
    public Map<String, Object> getDoc(String indexName, String id) {
        try {
            Term term = new Term("id", id);
            Query query = new TermQuery(term);
            List<Document> docs = luceneManager.searchDocuments(indexName, query, 1);
            
            if (docs.isEmpty()) {
                return null;
            }
            
            Document doc = docs.get(0);
            return documentToMap(doc);
        } catch (IOException e) {
            log.error("获取文档失败", e);
            throw new RuntimeException("获取文档失败", e);
        }
    }

    @Override
    public List<Map<String, Object>> multiGet(String indexName, List<String> ids) {
        try {
            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            for (String id : ids) {
                Term term = new Term("id", id);
                builder.add(new TermQuery(term), BooleanClause.Occur.SHOULD);
            }
            
            List<Document> docs = luceneManager.searchDocuments(indexName, builder.build(), ids.size());
            return docs.stream().map(this::documentToMap).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("批量获取文档失败", e);
            throw new RuntimeException("批量获取文档失败", e);
        }
    }

    @Override
    public boolean existsDoc(String indexName, String id) {
        try {
            Term term = new Term("id", id);
            Query query = new TermQuery(term);
            TopDocs topDocs = luceneManager.search(indexName, query, 1);
            return topDocs.totalHits.value > 0;
        } catch (IOException e) {
            log.error("检查文档是否存在失败", e);
            throw new RuntimeException("检查文档是否存在失败", e);
        }
    }

    @Override
    public long count(String indexName, Map<String, Object> queryMap) {
        try {
            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            if (queryMap != null && !queryMap.isEmpty()) {
                for (Map.Entry<String, Object> entry : queryMap.entrySet()) {
                    String field = entry.getKey();
                    Object value = entry.getValue();
                    
                    if (value != null) {
                        Query filterQuery;
                        if (value instanceof String) {
                            filterQuery = new TermQuery(new Term(field, (String) value));
                        } else {
                            filterQuery = new TermQuery(new Term(field, value.toString()));
                        }
                        builder.add(filterQuery, BooleanClause.Occur.MUST);
                    }
                }
            }
            
            TopDocs topDocs = luceneManager.search(indexName, builder.build(), 0);
            return topDocs.totalHits.value;
        } catch (IOException e) {
            log.error("获取文档数量失败", e);
            throw new RuntimeException("获取文档数量失败", e);
        }
    }

    @Override
    public void clearIndex(String indexName) {
        try {
            luceneManager.deleteAllDocuments(indexName);
            log.info("清空索引成功，索引: {}", indexName);
        } catch (IOException e) {
            log.error("清空索引失败", e);
            throw new RuntimeException("清空索引失败", e);
        }
    }

    @Override
    public List<String> analyze(String indexName, String analyzer, String text) {
        // 简单实现，返回空列表
        log.warn("Lucene不支持analyze操作");
        return Collections.emptyList();
    }

    @Override
    public List<String> suggest(String indexName, String field, String text) {
        // 简单实现，返回空列表
        log.warn("Lucene不支持suggest操作");
        return Collections.emptyList();
    }

    /**
     * 将Map转换为Lucene Document
     */
    private Document mapToDocument(Map<String, Object> source) {
        Document document = new Document();
        
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            
            if (value == null) {
                continue;
            }
            
            if ("id".equals(name)) {
                // ID字段必须使用StringField确保精确匹配
                document.add(new StringField(name, value.toString(), Field.Store.YES));
            } else if (value instanceof String) {
                // 存储和索引文本
                document.add(new TextField(name, (String) value, Field.Store.YES));
            } else if (value instanceof Number) {
                if (value instanceof Integer) {
                    // 存储整数
                    int intValue = ((Number) value).intValue();
                    document.add(new IntPoint(name, intValue));
                    document.add(new StoredField(name, intValue));
                } else if (value instanceof Long) {
                    // 存储长整数
                    long longValue = ((Number) value).longValue();
                    document.add(new LongPoint(name, longValue));
                    document.add(new StoredField(name, longValue));
                } else if (value instanceof Double || value instanceof Float) {
                    // 存储浮点数
                    double doubleValue = ((Number) value).doubleValue();
                    document.add(new DoublePoint(name, doubleValue));
                    document.add(new StoredField(name, doubleValue));
                }
            } else if (value instanceof Boolean) {
                // 存储布尔值
                document.add(new StringField(name, value.toString(), Field.Store.YES));
            } else if (value instanceof Date) {
                // 存储日期
                long time = ((Date) value).getTime();
                document.add(new LongPoint(name, time));
                document.add(new StoredField(name, time));
            } else {
                // 其他类型转为字符串
                document.add(new StringField(name, value.toString(), Field.Store.YES));
            }
        }
        
        return document;
    }

    /**
     * 将Lucene Document转换为Map
     */
    private Map<String, Object> documentToMap(Document document) {
        Map<String, Object> result = new HashMap<>();
        
        for (IndexableField field : document.getFields()) {
            String name = field.name();
            
            if (field.stringValue() != null) {
                result.put(name, field.stringValue());
            } else if (field.numericValue() != null) {
                result.put(name, field.numericValue());
            }
        }
        
        return result;
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
}

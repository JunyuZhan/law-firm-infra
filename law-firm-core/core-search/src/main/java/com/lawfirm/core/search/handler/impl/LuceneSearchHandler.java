package com.lawfirm.core.search.handler.impl;

import com.lawfirm.core.search.handler.LuceneManager;
import com.lawfirm.core.search.handler.SearchHandler;
import com.lawfirm.model.search.dto.search.SearchRequestDTO;
import com.lawfirm.model.search.enums.SearchTypeEnum;
import com.lawfirm.model.search.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Lucene搜索处理实现
 */
@Slf4j
@Component
public class LuceneSearchHandler implements SearchHandler {

    private final LuceneManager luceneManager;
    private static final int DEFAULT_MAX_RESULTS = 1000;

    public LuceneSearchHandler(LuceneManager luceneManager) {
        this.luceneManager = luceneManager;
    }

    @Override
    public SearchVO search(SearchRequestDTO request) throws IOException {
        String indexName = request.getIndexName();
        String keyword = request.getKeyword();
        List<String> fields = request.getFields();
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        
        // 如果未指定字段，使用默认字段
        if (fields == null || fields.isEmpty()) {
            fields = Collections.singletonList("content");
        }
        
        // 构建查询
        Query query;
        
        if (keyword == null || keyword.isEmpty()) {
            // 空查询返回所有文档
            query = new MatchAllDocsQuery();
        } else {
            // 根据搜索类型创建查询
            SearchTypeEnum searchType = request.getSearchType();
            if (searchType == null) {
                searchType = SearchTypeEnum.MATCH;
            }
            
            query = createQuery(keyword, fields, searchType);
        }
        
        // 添加过滤条件
        if (request.getFilters() != null && !request.getFilters().isEmpty()) {
            BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();
            booleanQueryBuilder.add(query, BooleanClause.Occur.MUST);
            
            // 添加过滤条件
            for (Map.Entry<String, Object> entry : request.getFilters().entrySet()) {
                String field = entry.getKey();
                Object value = entry.getValue();
                
                if (value != null) {
                    Term term = new Term(field, value.toString());
                    TermQuery termQuery = new TermQuery(term);
                    booleanQueryBuilder.add(termQuery, BooleanClause.Occur.FILTER);
                }
            }
            
            query = booleanQueryBuilder.build();
        }
        
        // 准备排序
        Sort sort = null;
        if (request.getSorts() != null && !request.getSorts().isEmpty()) {
            List<SortField> sortFields = new ArrayList<>();
            for (Map.Entry<String, String> entry : request.getSorts().entrySet()) {
                String field = entry.getKey();
                String order = entry.getValue();
                
                boolean reverse = "desc".equalsIgnoreCase(order);
                sortFields.add(new SortField(field, SortField.Type.STRING, reverse));
            }
            sort = new Sort(sortFields.toArray(new SortField[0]));
        }
        
        // 执行查询
        IndexSearcher searcher = luceneManager.getIndexSearcher(indexName);
        
        // 计算起始位置和查询数量
        int start = pageNum * pageSize;
        int maxResults = (pageSize == 0) ? DEFAULT_MAX_RESULTS : pageSize;
        
        // 获取总数
        TopDocs allDocs = searcher.search(query, 1);
        long total = allDocs.totalHits.value;
        
        // 查询结果
        TopDocs topDocs;
        if (sort != null) {
            topDocs = searcher.search(query, start + maxResults, sort);
        } else {
            topDocs = searcher.search(query, start + maxResults);
        }
        
        // 处理结果
        List<SearchVO.Hit> hits = new ArrayList<>();
        
        // 创建高亮器
        Highlighter highlighter = null;
        if (request.getHighlights() != null && !request.getHighlights().isEmpty()) {
            SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<em>", "</em>");
            QueryScorer scorer = new QueryScorer(query);
            highlighter = new Highlighter(htmlFormatter, scorer);
        }
        
        // 处理结果
        int end = Math.min(start + maxResults, topDocs.scoreDocs.length);
        for (int i = start; i < end; i++) {
            ScoreDoc scoreDoc = topDocs.scoreDocs[i];
            Document doc = searcher.storedFields().document(scoreDoc.doc);
            
            Map<String, Object> source = new HashMap<>();
            Map<String, List<String>> highlights = new HashMap<>();
            
            // 处理文档字段
            for (IndexableField field : doc.getFields()) {
                String name = field.name();
                String value = field.stringValue();
                
                // 处理高亮
                if (highlighter != null && request.getHighlights().contains(name) && value != null) {
                    try {
                        Analyzer analyzer = luceneManager.getAnalyzer();
                        String highlightedText = highlighter.getBestFragment(analyzer, name, value);
                        
                        if (highlightedText != null) {
                            List<String> fragments = highlights.computeIfAbsent(name, k -> new ArrayList<>());
                            fragments.add(highlightedText);
                        }
                    } catch (Exception e) {
                        log.warn("高亮处理失败: {}", e.getMessage());
                    }
                }
                
                source.put(name, value);
            }
            
            // 创建命中结果
            SearchVO.Hit hit = new SearchVO.Hit()
                .setIndex(indexName)
                .setId(doc.get("id"))
                .setScore(scoreDoc.score)
                .setSource(source);
            
            if (!highlights.isEmpty()) {
                hit.setHighlight(highlights);
            }
            
            hits.add(hit);
        }
        
        // 构建搜索结果
        SearchVO searchVO = new SearchVO()
            .setTotal(total)
            .setMaxScore(topDocs.scoreDocs.length > 0 ? topDocs.scoreDocs[0].score : 0.0f)
            .setHits(hits)
            .setTook((long) 0); // Lucene不提供查询耗时，可设置为0
        
        return searchVO;
    }

    @Override
    public List<String> suggest(String indexName, String field, String text) throws IOException {
        if (text == null || text.isEmpty()) {
            return Collections.emptyList();
        }
        
        // 创建前缀查询
        PrefixQuery prefixQuery = new PrefixQuery(new Term(field, text.toLowerCase()));
        
        // 执行查询
        List<Document> documents = luceneManager.searchDocuments(indexName, prefixQuery, 10);
        
        // 提取建议
        return documents.stream()
            .map(doc -> doc.get(field))
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());
    }
    
    /**
     * 根据搜索类型创建查询
     */
    private Query createQuery(String keyword, List<String> fields, SearchTypeEnum searchType) {
        switch (searchType) {
            case TERM:
                // 精确查询
                if (fields.size() == 1) {
                    return new TermQuery(new Term(fields.get(0), keyword));
                } else {
                    BooleanQuery.Builder builder = new BooleanQuery.Builder();
                    for (String field : fields) {
                        builder.add(new TermQuery(new Term(field, keyword)), BooleanClause.Occur.SHOULD);
                    }
                    return builder.build();
                }
                
            case PREFIX:
                // 前缀查询
                if (fields.size() == 1) {
                    return new PrefixQuery(new Term(fields.get(0), keyword));
                } else {
                    BooleanQuery.Builder builder = new BooleanQuery.Builder();
                    for (String field : fields) {
                        builder.add(new PrefixQuery(new Term(field, keyword)), BooleanClause.Occur.SHOULD);
                    }
                    return builder.build();
                }
                
            case WILDCARD:
                // 通配符查询
                if (fields.size() == 1) {
                    return new WildcardQuery(new Term(fields.get(0), keyword));
                } else {
                    BooleanQuery.Builder builder = new BooleanQuery.Builder();
                    for (String field : fields) {
                        builder.add(new WildcardQuery(new Term(field, keyword)), BooleanClause.Occur.SHOULD);
                    }
                    return builder.build();
                }
                
            case FUZZY:
                // 模糊查询
                if (fields.size() == 1) {
                    return new FuzzyQuery(new Term(fields.get(0), keyword));
                } else {
                    BooleanQuery.Builder builder = new BooleanQuery.Builder();
                    for (String field : fields) {
                        builder.add(new FuzzyQuery(new Term(field, keyword)), BooleanClause.Occur.SHOULD);
                    }
                    return builder.build();
                }
                
            case MATCH:
            default:
                // 默认使用多字段查询
                BooleanQuery.Builder builder = new BooleanQuery.Builder();
                for (String field : fields) {
                    builder.add(new TermQuery(new Term(field, keyword)), BooleanClause.Occur.SHOULD);
                }
                return builder.build();
        }
    }
} 
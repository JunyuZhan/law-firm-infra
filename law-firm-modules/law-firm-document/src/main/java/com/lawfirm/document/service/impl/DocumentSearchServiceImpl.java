package com.lawfirm.document.service.impl;

import com.lawfirm.core.search.repository.BaseSearchRepository;
import com.lawfirm.core.search.service.impl.BaseSearchServiceImpl;
import com.lawfirm.document.service.IDocumentSearchService;
import com.lawfirm.model.document.index.DocumentIndex;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文档搜索服务实现类
 */
@Slf4j
@Service
public class DocumentSearchServiceImpl extends BaseSearchServiceImpl<DocumentIndex, Long> implements IDocumentSearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public DocumentSearchServiceImpl(BaseSearchRepository<DocumentIndex, Long> searchRepository,
            ElasticsearchOperations elasticsearchOperations) {
        super(searchRepository);
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public Page<DocumentIndex> search(String keyword, Pageable pageable) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("documentName", keyword))
                .should(QueryBuilders.matchQuery("content", keyword))
                .should(QueryBuilders.matchQuery("summary", keyword))
                .minimumShouldMatch(1);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .build();

        SearchHits<DocumentIndex> searchHits = elasticsearchOperations.search(searchQuery, DocumentIndex.class);
        return convertSearchHitsToPage(searchHits, pageable);
    }

    @Override
    public Page<DocumentIndex> advancedSearch(Map<String, Object> searchParams, Pageable pageable) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        // 处理各种搜索条件
        searchParams.forEach((key, value) -> {
            switch (key) {
                case "documentName":
                case "content":
                case "summary":
                    queryBuilder.must(QueryBuilders.matchQuery(key, value));
                    break;
                case "documentNumber":
                case "documentType":
                case "status":
                    queryBuilder.must(QueryBuilders.termQuery(key, value));
                    break;
                case "categoryId":
                case "caseId":
                case "contractId":
                case "clientId":
                    queryBuilder.must(QueryBuilders.termQuery(key, value));
                    break;
                case "tags":
                    if (value instanceof List) {
                        queryBuilder.must(QueryBuilders.termsQuery(key, (List<?>) value));
                    }
                    break;
                case "createTimeStart":
                    queryBuilder.must(QueryBuilders.rangeQuery("createTime").gte(value));
                    break;
                case "createTimeEnd":
                    queryBuilder.must(QueryBuilders.rangeQuery("createTime").lte(value));
                    break;
            }
        });

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .build();

        SearchHits<DocumentIndex> searchHits = elasticsearchOperations.search(searchQuery, DocumentIndex.class);
        return convertSearchHitsToPage(searchHits, pageable);
    }

    @Override
    public List<DocumentIndex> findSimilar(Long documentId, int maxResults) {
        DocumentIndex document = findById(documentId);
        if (document == null) {
            return List.of();
        }

        // 使用文档名称、内容和摘要进行相似度匹配
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.moreLikeThisQuery(
                        new String[]{"documentName", "content", "summary"},
                        new String[]{document.getDocumentName(), document.getContent(), document.getSummary()},
                        null
                ).minTermFreq(1).maxQueryTerms(12))
                .mustNot(QueryBuilders.termQuery("id", documentId));

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withMaxResults(maxResults)
                .build();

        SearchHits<DocumentIndex> searchHits = elasticsearchOperations.search(searchQuery, DocumentIndex.class);
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DocumentIndex> searchByTags(List<String> tags, Pageable pageable) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termsQuery("tags", tags));

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .build();

        SearchHits<DocumentIndex> searchHits = elasticsearchOperations.search(searchQuery, DocumentIndex.class);
        return convertSearchHitsToPage(searchHits, pageable);
    }

    @Override
    public List<String> suggest(String prefix, int maxSuggestions) {
        // TODO: 实现搜索建议功能
        return List.of();
    }

    @Override
    public Map<String, Object> getIndexStats() {
        // TODO: 实现索引统计功能
        return Map.of();
    }

    private Page<DocumentIndex> convertSearchHitsToPage(SearchHits<DocumentIndex> searchHits, Pageable pageable) {
        List<DocumentIndex> content = searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        return new org.springframework.data.domain.PageImpl<>(
                content,
                pageable,
                searchHits.getTotalHits()
        );
    }
} 
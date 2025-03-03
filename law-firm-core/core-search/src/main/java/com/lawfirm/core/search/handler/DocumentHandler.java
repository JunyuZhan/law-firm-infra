package com.lawfirm.core.search.handler;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.mget.MultiGetResponseItem;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ES文档处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentHandler {

    private final ElasticsearchClient client;

    /**
     * 索引文档
     */
    public void indexDoc(String indexName, String id, Map<String, Object> source) throws IOException {
        IndexRequest<Map<String, Object>> request = IndexRequest.of(builder ->
            builder.index(indexName)
                   .id(id)
                   .document(source)
        );
        
        IndexResponse response = client.index(request);
        log.debug("索引文档结果: {}", response.result());
    }

    /**
     * 批量索引文档
     */
    public void bulkIndex(String indexName, List<Map<String, Object>> documents) throws IOException {
        List<BulkOperation> operations = documents.stream()
            .map(doc -> BulkOperation.of(op -> op.index(idx -> 
                idx.index(indexName)
                   .id(doc.get("id").toString())
                   .document(doc)
            )))
            .collect(Collectors.toList());

        BulkRequest request = BulkRequest.of(builder -> builder.operations(operations));
        BulkResponse response = client.bulk(request);
        
        if (response.errors()) {
            log.error("批量索引文档出现错误: {}", response.items());
            throw new RuntimeException("批量索引文档失败");
        }
    }

    /**
     * 更新文档
     */
    public void updateDoc(String indexName, String id, Map<String, Object> source) throws IOException {
        UpdateRequest<Map<String, Object>, Map<String, Object>> request = UpdateRequest.of(builder ->
            builder.index(indexName)
                   .id(id)
                   .doc(source)
        );
        
        UpdateResponse<Map<String, Object>> response = client.update(request, Map.class);
        log.debug("更新文档结果: {}", response.result());
    }

    /**
     * 删除文档
     */
    public void deleteDoc(String indexName, String id) throws IOException {
        DeleteRequest request = DeleteRequest.of(builder ->
            builder.index(indexName)
                   .id(id)
        );
        
        DeleteResponse response = client.delete(request);
        log.debug("删除文档结果: {}", response.result());
    }

    /**
     * 批量删除文档
     */
    public void bulkDelete(String indexName, List<String> ids) throws IOException {
        List<BulkOperation> operations = ids.stream()
            .map(id -> BulkOperation.of(op -> op.delete(del -> 
                del.index(indexName)
                   .id(id)
            )))
            .collect(Collectors.toList());

        BulkRequest request = BulkRequest.of(builder -> builder.operations(operations));
        BulkResponse response = client.bulk(request);
        
        if (response.errors()) {
            log.error("批量删除文档出现错误: {}", response.items());
            throw new RuntimeException("批量删除文档失败");
        }
    }

    /**
     * 获取文档
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getDoc(String indexName, String id) throws IOException {
        GetRequest request = GetRequest.of(builder ->
            builder.index(indexName)
                   .id(id)
        );
        
        GetResponse<JsonData> response = client.get(request, JsonData.class);
        return response.source() != null ? response.source().to(Map.class) : null;
    }

    /**
     * 批量获取文档
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> multiGet(String indexName, List<String> ids) throws IOException {
        MgetRequest request = MgetRequest.of(builder ->
            builder.index(indexName)
                   .ids(ids)
        );
        
        MgetResponse<JsonData> response = client.mget(request, JsonData.class);
        return response.docs().stream()
            .filter(doc -> doc.failure() == null && doc.result() != null && doc.result().source() != null)
            .map(doc -> {
                Map<String, Object> source = doc.result().source().to(Map.class);
                return source;
            })
            .collect(Collectors.toList());
    }

    /**
     * 文档是否存在
     */
    public boolean existsDoc(String indexName, String id) throws IOException {
        ExistsRequest request = ExistsRequest.of(builder ->
            builder.index(indexName)
                   .id(id)
        );
        
        return client.exists(request).value();
    }
} 
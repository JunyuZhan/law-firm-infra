package com.lawfirm.core.search.handler;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.indices.*;
import co.elastic.clients.json.JsonData;
import com.lawfirm.core.search.config.ElasticsearchProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;

/**
 * 索引处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class IndexHandler {

    private final ElasticsearchClient client;
    private final ElasticsearchProperties properties;

    /**
     * 创建索引
     *
     * @param indexName 索引名称
     * @param mapping   映射
     * @throws IOException IO异常
     */
    public void createIndex(String indexName, TypeMapping mapping) throws IOException {
        CreateIndexRequest request = CreateIndexRequest.of(builder -> builder
                .index(indexName)
                .mappings(mapping)
                .settings(s -> s
                        .numberOfShards(String.valueOf(properties.getIndex().getNumberOfShards()))
                        .numberOfReplicas(String.valueOf(properties.getIndex().getNumberOfReplicas()))
                )
        );

        CreateIndexResponse response = client.indices().create(request);
        if (!response.acknowledged()) {
            throw new IOException("创建索引失败: " + indexName);
        }
        log.info("创建索引成功: {}", indexName);
    }

    /**
     * 删除索引
     *
     * @param indexName 索引名称
     * @throws IOException IO异常
     */
    public void deleteIndex(String indexName) throws IOException {
        DeleteIndexRequest request = DeleteIndexRequest.of(builder -> builder.index(indexName));
        DeleteIndexResponse response = client.indices().delete(request);
        if (!response.acknowledged()) {
            throw new IOException("删除索引失败: " + indexName);
        }
        log.info("删除索引成功: {}", indexName);
    }

    /**
     * 更新索引设置
     *
     * @param indexName 索引名称
     * @param settings 设置
     * @throws IOException IO异常
     */
    public void updateSettings(String indexName, String settings) throws IOException {
        PutIndicesSettingsRequest request = PutIndicesSettingsRequest.of(builder -> builder
                .index(indexName)
                .settings(s -> s.withJson(new StringReader(settings)))
        );

        PutIndicesSettingsResponse response = client.indices().putSettings(request);
        if (!response.acknowledged()) {
            throw new IOException("更新索引设置失败: " + indexName);
        }
        log.info("更新索引设置成功: {}", indexName);
    }

    /**
     * 更新索引映射
     *
     * @param indexName 索引名称
     * @param mapping   映射
     * @throws IOException IO异常
     */
    public void updateMapping(String indexName, TypeMapping mapping) throws IOException {
        PutMappingRequest request = PutMappingRequest.of(builder -> builder
                .index(indexName)
                .withJson(new StringReader(JsonData.of(mapping).toJson().toString()))
        );

        PutMappingResponse response = client.indices().putMapping(request);
        if (!response.acknowledged()) {
            throw new IOException("更新索引映射失败: " + indexName);
        }
        log.info("更新索引映射成功: {}", indexName);
    }

    /**
     * 获取索引信息
     *
     * @param indexName 索引名称
     * @return 索引信息
     * @throws IOException IO异常
     */
    public GetIndexResponse getIndex(String indexName) throws IOException {
        GetIndexRequest request = GetIndexRequest.of(builder -> builder.index(indexName));
        return client.indices().get(request);
    }

    /**
     * 检查索引是否存在
     *
     * @param indexName 索引名称
     * @return 是否存在
     * @throws IOException IO异常
     */
    public boolean existsIndex(String indexName) throws IOException {
        ExistsRequest request = ExistsRequest.of(builder -> builder.index(indexName));
        return client.indices().exists(request).value();
    }

    /**
     * 打开索引
     *
     * @param indexName 索引名称
     * @throws IOException IO异常
     */
    public void openIndex(String indexName) throws IOException {
        OpenRequest request = OpenRequest.of(builder -> builder.index(indexName));
        OpenResponse response = client.indices().open(request);
        if (!response.acknowledged()) {
            throw new IOException("打开索引失败: " + indexName);
        }
        log.info("打开索引成功: {}", indexName);
    }

    /**
     * 关闭索引
     *
     * @param indexName 索引名称
     * @throws IOException IO异常
     */
    public void closeIndex(String indexName) throws IOException {
        CloseIndexRequest request = CloseIndexRequest.of(builder -> builder.index(indexName));
        CloseIndexResponse response = client.indices().close(request);
        if (!response.acknowledged()) {
            throw new IOException("关闭索引失败: " + indexName);
        }
        log.info("关闭索引成功: {}", indexName);
    }

    /**
     * 刷新索引
     *
     * @param indexName 索引名称
     * @throws IOException IO异常
     */
    public void refreshIndex(String indexName) throws IOException {
        RefreshRequest request = RefreshRequest.of(builder -> builder.index(indexName));
        client.indices().refresh(request);
        log.info("刷新索引成功: {}", indexName);
    }

    /**
     * 创建别名
     *
     * @param indexName 索引名称
     * @param alias     别名
     * @throws IOException IO异常
     */
    public void createAlias(String indexName, String alias) throws IOException {
        PutAliasRequest request = PutAliasRequest.of(builder -> builder
                .index(indexName)
                .name(alias)
        );

        PutAliasResponse response = client.indices().putAlias(request);
        if (!response.acknowledged()) {
            throw new IOException("创建别名失败: " + indexName + " -> " + alias);
        }
        log.info("创建别名成功: {} -> {}", indexName, alias);
    }

    /**
     * 删除别名
     *
     * @param indexName 索引名称
     * @param alias     别名
     * @throws IOException IO异常
     */
    public void deleteAlias(String indexName, String alias) throws IOException {
        DeleteAliasRequest request = DeleteAliasRequest.of(builder -> builder
                .index(indexName)
                .name(alias)
        );

        DeleteAliasResponse response = client.indices().deleteAlias(request);
        if (!response.acknowledged()) {
            throw new IOException("删除别名失败: " + indexName + " -> " + alias);
        }
        log.info("删除别名成功: {} -> {}", indexName, alias);
    }
} 
package com.lawfirm.core.search.handler.impl;

import com.lawfirm.core.search.handler.DocumentHandler;
import com.lawfirm.core.search.handler.LuceneManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lucene文档处理实现
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "law-firm.search", name = "searchEngineType", havingValue = "lucene")
public class LuceneDocumentHandler implements DocumentHandler {

    private final LuceneManager luceneManager;
    
    @Autowired
    public LuceneDocumentHandler(LuceneManager luceneManager) {
        this.luceneManager = luceneManager;
        log.info("初始化Lucene文档处理器");
    }

    @Override
    public void indexDoc(String indexName, String id, Map<String, Object> document) throws IOException {
        Document doc = convertToDocument(id, document);
        luceneManager.addDocument(indexName, doc);
        log.debug("索引文档成功: {}:{}", indexName, id);
    }

    @Override
    public void bulkIndex(String indexName, List<Map<String, Object>> documents) throws IOException {
        List<Document> docs = new ArrayList<>();
            for (Map<String, Object> document : documents) {
            String id = document.get("id") != null ? document.get("id").toString() : null;
                if (id == null) {
                log.warn("文档无ID字段，跳过: {}", document);
                    continue;
            }
            Document doc = convertToDocument(id, document);
            docs.add(doc);
        }
        
        luceneManager.addDocuments(indexName, docs);
        log.debug("批量索引文档成功: {}, 数量: {}", indexName, docs.size());
    }

    @Override
    public void updateDoc(String indexName, String id, Map<String, Object> document) throws IOException {
        Document doc = convertToDocument(id, document);
        Term term = new Term("id", id);
        luceneManager.updateDocument(indexName, term, doc);
        log.debug("更新文档成功: {}:{}", indexName, id);
    }

    @Override
    public void deleteDoc(String indexName, String id) throws IOException {
        Term term = new Term("id", id);
        luceneManager.deleteDocuments(indexName, new TermQuery(term));
        log.debug("删除文档成功: {}:{}", indexName, id);
    }

    @Override
    public void bulkDelete(String indexName, List<String> ids) throws IOException {
            for (String id : ids) {
            deleteDoc(indexName, id);
            }
        log.debug("批量删除文档成功: {}, 数量: {}", indexName, ids.size());
    }

    @Override
    public Map<String, Object> getDoc(String indexName, String id) throws IOException {
        Term term = new Term("id", id);
        List<Document> docs = luceneManager.searchDocuments(indexName, new TermQuery(term), 1);
        
        if (docs.isEmpty()) {
            return null;
        }
        
        return convertToMap(docs.get(0));
    }

    @Override
    public List<Map<String, Object>> multiGet(String indexName, List<String> ids) throws IOException {
        List<Map<String, Object>> results = new ArrayList<>();
        for (String id : ids) {
            Map<String, Object> doc = getDoc(indexName, id);
            if (doc != null) {
                results.add(doc);
            }
        }
        return results;
    }

    @Override
    public boolean existsDoc(String indexName, String id) throws IOException {
        Term term = new Term("id", id);
        List<Document> docs = luceneManager.searchDocuments(indexName, new TermQuery(term), 1);
        return !docs.isEmpty();
    }
    
    /**
     * 将Map转换为Lucene Document
     */
    private Document convertToDocument(String id, Map<String, Object> source) {
        Document document = new Document();
        
        // 添加ID字段，不分词，存储
        document.add(new StringField("id", id, Field.Store.YES));
        
        // 添加其他字段
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            if (value == null) {
                continue;
            }
            
            if ("id".equals(key)) {
                continue; // ID已经添加
            }
            
            if (value instanceof String) {
                // 字符串字段，默认使用TextField进行分词
                document.add(new TextField(key, (String) value, Field.Store.YES));
            } else if (value instanceof Integer) {
                // 整数字段
                document.add(new IntPoint(key, (Integer) value));
                document.add(new StoredField(key, (Integer) value));
                document.add(new NumericDocValuesField(key, (Integer) value));
            } else if (value instanceof Long) {
                // 长整数字段
                document.add(new LongPoint(key, (Long) value));
                document.add(new StoredField(key, (Long) value));
                document.add(new NumericDocValuesField(key, (Long) value));
            } else if (value instanceof Float) {
                // 浮点数字段
                document.add(new FloatPoint(key, (Float) value));
                document.add(new StoredField(key, (Float) value));
                document.add(new NumericDocValuesField(key, Float.floatToRawIntBits((Float) value)));
            } else if (value instanceof Double) {
                // 双精度浮点数字段
                document.add(new DoublePoint(key, (Double) value));
                document.add(new StoredField(key, (Double) value));
                document.add(new NumericDocValuesField(key, Double.doubleToRawLongBits((Double) value)));
            } else if (value instanceof Boolean) {
                // 布尔字段
                document.add(new StringField(key, value.toString(), Field.Store.YES));
            } else {
                // 其他类型转为字符串
                document.add(new StringField(key, value.toString(), Field.Store.YES));
            }
        }
        
        return document;
    }
    
    /**
     * 将Lucene Document转换为Map
     */
    private Map<String, Object> convertToMap(Document document) {
        Map<String, Object> result = new HashMap<>();
        
        for (IndexableField field : document.getFields()) {
            String name = field.name();
            
            // 已经添加过的字段跳过
            if (result.containsKey(name)) {
                continue;
            }
            
            // 根据字段类型获取值
            if (field.numericValue() != null) {
                result.put(name, field.numericValue());
            } else {
                result.put(name, field.stringValue());
            }
        }
        
        return result;
    }
} 
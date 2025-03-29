package com.lawfirm.core.search.handler.impl;

import com.lawfirm.core.search.handler.DocumentHandler;
import com.lawfirm.core.search.handler.LuceneManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文档处理接口的Lucene实现
 */
@Slf4j
@Component
public class LuceneDocumentHandler implements DocumentHandler {

    private final LuceneManager luceneManager;
    
    private static final String ID_FIELD = "id";

    public LuceneDocumentHandler(LuceneManager luceneManager) {
        this.luceneManager = luceneManager;
    }

    @Override
    public void indexDoc(String indexName, String id, Map<String, Object> document) throws IOException {
        Document doc = mapToDocument(id, document);
        
        IndexWriter writer = luceneManager.getIndexWriter(indexName);
        try {
            // 如果文档已存在，先删除
            writer.deleteDocuments(new Term(ID_FIELD, id));
            writer.addDocument(doc);
            writer.commit();
        } finally {
            // 不关闭IndexWriter，由LuceneManager管理
        }
    }

    @Override
    public void bulkIndex(String indexName, List<Map<String, Object>> documents) throws IOException {
        IndexWriter writer = luceneManager.getIndexWriter(indexName);
        try {
            for (Map<String, Object> document : documents) {
                String id = (String) document.get(ID_FIELD);
                if (id == null) {
                    log.warn("文档缺少ID字段，跳过索引");
                    continue;
                }
                
                Document doc = mapToDocument(id, document);
                writer.deleteDocuments(new Term(ID_FIELD, id));
                writer.addDocument(doc);
            }
            writer.commit();
        } finally {
            // 不关闭IndexWriter，由LuceneManager管理
        }
    }

    @Override
    public void updateDoc(String indexName, String id, Map<String, Object> document) throws IOException {
        Document doc = mapToDocument(id, document);
        
        IndexWriter writer = luceneManager.getIndexWriter(indexName);
        try {
            writer.updateDocument(new Term(ID_FIELD, id), doc);
            writer.commit();
        } finally {
            // 不关闭IndexWriter，由LuceneManager管理
        }
    }

    @Override
    public void deleteDoc(String indexName, String id) throws IOException {
        IndexWriter writer = luceneManager.getIndexWriter(indexName);
        try {
            writer.deleteDocuments(new Term(ID_FIELD, id));
            writer.commit();
        } finally {
            // 不关闭IndexWriter，由LuceneManager管理
        }
    }

    @Override
    public void bulkDelete(String indexName, List<String> ids) throws IOException {
        IndexWriter writer = luceneManager.getIndexWriter(indexName);
        try {
            for (String id : ids) {
                writer.deleteDocuments(new Term(ID_FIELD, id));
            }
            writer.commit();
        } finally {
            // 不关闭IndexWriter，由LuceneManager管理
        }
    }

    @Override
    public Map<String, Object> getDoc(String indexName, String id) throws IOException {
        IndexSearcher searcher = luceneManager.getIndexSearcher(indexName);
        TermQuery query = new TermQuery(new Term(ID_FIELD, id));
        
        TopDocs topDocs = searcher.search(query, 1);
        if (topDocs.totalHits.value == 0) {
            return null;
        }
        
        Document doc = searcher.storedFields().document(topDocs.scoreDocs[0].doc);
        return documentToMap(doc);
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
        IndexSearcher searcher = luceneManager.getIndexSearcher(indexName);
        TermQuery query = new TermQuery(new Term(ID_FIELD, id));
        
        TopDocs topDocs = searcher.search(query, 1);
        return topDocs.totalHits.value > 0;
    }
    
    /**
     * 将Map转换为Lucene Document
     */
    private Document mapToDocument(String id, Map<String, Object> map) {
        Document doc = new Document();
        
        // 添加ID字段
        doc.add(new StringField(ID_FIELD, id, Field.Store.YES));
        
        // 添加其他字段
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            if (value == null) {
                continue;
            }
            
            String stringValue = value.toString();
            
            // 根据字段类型选择不同的Field类型
            if (key.equals(ID_FIELD)) {
                continue;  // ID已经添加过了
            } else if (key.endsWith("_keyword")) {
                // 精确匹配字段
                doc.add(new StringField(key, stringValue, Field.Store.YES));
            } else {
                // 默认为全文搜索字段
                doc.add(new TextField(key, stringValue, Field.Store.YES));
            }
        }
        
        return doc;
    }
    
    /**
     * 将Lucene Document转换为Map
     */
    private Map<String, Object> documentToMap(Document doc) {
        Map<String, Object> map = new HashMap<>();
        
        for (IndexableField field : doc.getFields()) {
            String name = field.name();
            String value = field.stringValue();
            
            map.put(name, value);
        }
        
        return map;
    }
} 
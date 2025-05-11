package com.lawfirm.core.search.handler;

import com.lawfirm.core.search.config.SearchProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lucene索引管理器
 * 负责创建、更新、删除索引以及执行搜索操作
 */
@Slf4j
@ConditionalOnProperty(prefix = "law-firm.search", name = "type", havingValue = "lucene")
public class LuceneManager {

    private final SearchProperties searchProperties;

    /**
     * 索引写入器缓存
     */
    private final Map<String, IndexWriter> indexWriterMap = new ConcurrentHashMap<>();

    /**
     * 索引搜索器缓存
     */
    private final Map<String, IndexSearcher> indexSearcherMap = new ConcurrentHashMap<>();

    /**
     * 索引读取器缓存
     */
    private final Map<String, DirectoryReader> directoryReaderMap = new ConcurrentHashMap<>();

    /**
     * 构造函数，注入 SearchProperties
     */
    @Autowired
    public LuceneManager(SearchProperties searchProperties) {
        this.searchProperties = searchProperties;
    }

    /**
     * 获取分析器
     */
    public Analyzer getAnalyzer() {
        String analyzer = searchProperties.getLucene().getAnalyzer();
        if ("ik".equals(analyzer)) {
            return new IKAnalyzer(true);
        } else if ("chinese".equals(analyzer)) {
            return new StandardAnalyzer();
        } else {
            return new StandardAnalyzer();
        }
    }

    /**
     * 获取索引目录
     */
    private Directory getDirectory(String indexName) throws IOException {
        String indexDir = searchProperties.getLucene().getIndexDir();
        File file = new File(indexDir + File.separator + indexName);
        if (!file.exists()) {
            boolean created = file.mkdirs();
            if (created) {
                log.info("创建索引目录成功: {}", file.getAbsolutePath());
            }
        }
        return FSDirectory.open(Paths.get(file.getAbsolutePath()));
    }

    /**
     * 获取索引写入器
     */
    public synchronized IndexWriter getIndexWriter(String indexName) throws IOException {
        if (indexWriterMap.containsKey(indexName)) {
            return indexWriterMap.get(indexName);
        }

        Directory directory = getDirectory(indexName);
        IndexWriterConfig config = new IndexWriterConfig(getAnalyzer());
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        config.setRAMBufferSizeMB(searchProperties.getLucene().getRamBufferSizeMb());
        
        IndexWriter indexWriter = new IndexWriter(directory, config);
        indexWriterMap.put(indexName, indexWriter);
        return indexWriter;
    }

    /**
     * 获取索引搜索器
     */
    public synchronized IndexSearcher getIndexSearcher(String indexName) throws IOException {
        // 检查是否需要刷新索引搜索器
        if (directoryReaderMap.containsKey(indexName)) {
            DirectoryReader oldReader = directoryReaderMap.get(indexName);
            DirectoryReader newReader = DirectoryReader.openIfChanged(oldReader);
            if (newReader != null && newReader != oldReader) {
                try {
                    oldReader.close();
                } catch (Exception e) {
                    log.error("关闭旧的DirectoryReader失败", e);
                }
                directoryReaderMap.put(indexName, newReader);
                IndexSearcher indexSearcher = new IndexSearcher(newReader);
                indexSearcherMap.put(indexName, indexSearcher);
                return indexSearcher;
            }
        }

        // 首次创建索引搜索器
        if (!indexSearcherMap.containsKey(indexName)) {
            Directory directory = getDirectory(indexName);
            DirectoryReader reader;
            try {
                reader = DirectoryReader.open(directory);
            } catch (IndexNotFoundException e) {
                // 索引不存在，先创建空索引
                getIndexWriter(indexName).commit();
                reader = DirectoryReader.open(directory);
            }
            
            directoryReaderMap.put(indexName, reader);
            IndexSearcher searcher = new IndexSearcher(reader);
            indexSearcherMap.put(indexName, searcher);
            return searcher;
        }

        return indexSearcherMap.get(indexName);
    }

    /**
     * 添加文档到索引
     */
    public void addDocument(String indexName, Document document) throws IOException {
        IndexWriter writer = getIndexWriter(indexName);
        writer.addDocument(document);
        writer.commit();
    }

    /**
     * 批量添加文档到索引
     */
    public void addDocuments(String indexName, List<Document> documents) throws IOException {
        IndexWriter writer = getIndexWriter(indexName);
        writer.addDocuments(documents);
        writer.commit();
    }

    /**
     * 更新文档
     */
    public void updateDocument(String indexName, Term term, Document document) throws IOException {
        IndexWriter writer = getIndexWriter(indexName);
        writer.updateDocument(term, document);
        writer.commit();
    }

    /**
     * 删除文档
     */
    public void deleteDocument(String indexName, Query query) throws IOException {
        IndexWriter writer = getIndexWriter(indexName);
        writer.deleteDocuments(query);
        writer.commit();
    }

    /**
     * 删除所有文档
     */
    public void deleteAllDocuments(String indexName) throws IOException {
        IndexWriter writer = getIndexWriter(indexName);
        writer.deleteAll();
        writer.commit();
    }

    /**
     * 搜索文档
     */
    public List<Document> searchDocuments(String indexName, Query query, int maxResults) throws IOException {
        IndexSearcher searcher = getIndexSearcher(indexName);
        TopDocs topDocs = searcher.search(query, maxResults == 0 ? searchProperties.getLucene().getMaxResults() : maxResults);
        List<Document> documentList = new ArrayList<>();
        
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = searcher.storedFields().document(scoreDoc.doc);
            documentList.add(doc);
        }
        
        return documentList;
    }

    /**
     * 搜索文档并返回TopDocs
     */
    public TopDocs search(String indexName, Query query, int maxResults) throws IOException {
        IndexSearcher searcher = getIndexSearcher(indexName);
        return searcher.search(query, maxResults == 0 ? searchProperties.getLucene().getMaxResults() : maxResults);
    }

    /**
     * 搜索文档并返回TopDocs，支持排序
     */
    public TopDocs search(String indexName, Query query, Sort sort, int maxResults) throws IOException {
        IndexSearcher searcher = getIndexSearcher(indexName);
        return searcher.search(query, maxResults == 0 ? searchProperties.getLucene().getMaxResults() : maxResults, sort);
    }

    /**
     * 优化索引
     */
    public void optimizeIndex(String indexName) throws IOException {
        IndexWriter writer = getIndexWriter(indexName);
        writer.forceMerge(1);
        writer.commit();
    }

    /**
     * 关闭索引
     */
    public void close() {
        indexWriterMap.forEach((indexName, writer) -> {
            try {
                writer.close();
            } catch (IOException e) {
                log.error("关闭IndexWriter失败: {}", indexName, e);
            }
        });
        
        directoryReaderMap.forEach((indexName, reader) -> {
            try {
                reader.close();
            } catch (IOException e) {
                log.error("关闭DirectoryReader失败: {}", indexName, e);
            }
        });
        
        indexWriterMap.clear();
        directoryReaderMap.clear();
        indexSearcherMap.clear();
    }
} 
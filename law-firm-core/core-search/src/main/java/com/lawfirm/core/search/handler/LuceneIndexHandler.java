package com.lawfirm.core.search.handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.lawfirm.core.search.config.SearchProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * Lucene索引处理实现
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "law-firm.search", name = "searchEngineType", havingValue = "lucene")
public class LuceneIndexHandler implements IndexHandler {

    private final SearchProperties searchProperties;
    private final Analyzer analyzer;
    
    private final Map<String, Directory> directoryMap = new ConcurrentHashMap<>();
    private final Map<String, IndexWriter> writerMap = new ConcurrentHashMap<>();
    private final Map<String, IndexReader> readerMap = new ConcurrentHashMap<>();
    private final Map<String, IndexSearcher> searcherMap = new ConcurrentHashMap<>();
    
    @Autowired
    public LuceneIndexHandler(SearchProperties searchProperties, @Qualifier("ikAnalyzer") Analyzer analyzer) {
        this.searchProperties = searchProperties;
        this.analyzer = analyzer;
    }
    
    @PostConstruct
    public void init() {
        log.info("初始化Lucene索引处理器");
    }
    
    @PreDestroy
    public void destroy() {
        log.info("关闭Lucene索引处理器");
        try {
            for (IndexWriter writer : writerMap.values()) {
                writer.close();
            }
            for (IndexReader reader : readerMap.values()) {
                reader.close();
            }
            for (Directory directory : directoryMap.values()) {
                directory.close();
            }
        } catch (IOException e) {
            log.error("关闭Lucene索引处理器失败", e);
        }
    }
    
    /**
     * 获取索引目录
     */
    private Directory getDirectory(String indexName) throws IOException {
        Directory directory = directoryMap.get(indexName);
        if (directory == null) {
            String indexDir = searchProperties.getLucene().getIndexDir();
            Path path = Paths.get(indexDir, indexName);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            directory = FSDirectory.open(path);
            directoryMap.put(indexName, directory);
        }
        return directory;
    }

    @Override
    public void createIndex(String indexName, Object mapping) throws IOException {
        log.info("创建索引: {}", indexName);
        getIndexWriter(indexName);
    }

    @Override
    public void deleteIndex(String indexName) throws IOException {
        log.info("删除索引: {}", indexName);
        closeIndex(indexName);
        
        // 关闭并移除所有资源
        IndexWriter writer = writerMap.remove(indexName);
        if (writer != null) {
            writer.close();
        }
        
        IndexReader reader = readerMap.remove(indexName);
        if (reader != null) {
            reader.close();
        }
        
        searcherMap.remove(indexName);
        
        Directory directory = directoryMap.remove(indexName);
        if (directory != null) {
            directory.close();
        }
        
        // 删除索引目录
        String indexDir = searchProperties.getLucene().getIndexDir();
        Path path = Paths.get(indexDir, indexName);
        if (Files.exists(path)) {
            Files.walk(path)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(p -> {
                    try {
                        Files.delete(p);
                    } catch (IOException e) {
                        log.error("删除索引文件失败: {}", p, e);
                    }
                });
        }
    }

    @Override
    public void updateSettings(String indexName, String settings) throws IOException {
        log.info("更新索引设置: {}", indexName);
        // Lucene没有单独的设置更新机制，忽略此操作
    }

    @Override
    public void updateMapping(String indexName, Object mapping) throws IOException {
        log.info("更新索引映射: {}", indexName);
        // Lucene没有单独的映射更新机制，忽略此操作
    }

    @Override
    public Object getIndex(String indexName) throws IOException {
        log.info("获取索引信息: {}", indexName);
        Map<String, Object> result = new HashMap<>();
        if (existsIndex(indexName)) {
            result.put("name", indexName);
            result.put("exists", true);
            
            // 获取文档数量
            IndexReader reader = getIndexReader(indexName);
            result.put("docs", reader.numDocs());
            result.put("deleted_docs", reader.numDeletedDocs());
            
            // 获取索引目录信息
            String indexDir = searchProperties.getLucene().getIndexDir();
            Path path = Paths.get(indexDir, indexName);
            result.put("path", path.toString());
            
            // 获取索引文件大小
            long size = 0;
            if (Files.exists(path)) {
                size = Files.walk(path)
                    .filter(Files::isRegularFile)
                    .mapToLong(p -> {
                        try {
                            return Files.size(p);
                        } catch (IOException e) {
                            return 0;
                        }
                    })
                    .sum();
            }
            result.put("size", size);
        } else {
            result.put("name", indexName);
            result.put("exists", false);
        }
        return result;
    }

    @Override
    public boolean existsIndex(String indexName) throws IOException {
        log.debug("检查索引是否存在: {}", indexName);
        String indexDir = searchProperties.getLucene().getIndexDir();
        Path path = Paths.get(indexDir, indexName);
        return Files.exists(path) && Files.list(path).findAny().isPresent();
    }

    @Override
    public void openIndex(String indexName) throws IOException {
        log.info("打开索引: {}", indexName);
        getIndexReader(indexName);
    }

    @Override
    public void closeIndex(String indexName) throws IOException {
        log.info("关闭索引: {}", indexName);
        
        // 关闭并移除读取器和搜索器
        IndexReader reader = readerMap.remove(indexName);
        if (reader != null) {
            reader.close();
        }
        
        searcherMap.remove(indexName);
        
        // 提交并保留写入器
        IndexWriter writer = writerMap.get(indexName);
        if (writer != null) {
            writer.commit();
        }
    }

    @Override
    public void refreshIndex(String indexName) throws IOException {
        log.debug("刷新索引: {}", indexName);
        
        // 提交更改
        IndexWriter writer = writerMap.get(indexName);
        if (writer != null) {
            writer.commit();
        }
        
        // 重新打开读取器
        IndexReader oldReader = readerMap.remove(indexName);
        if (oldReader != null) {
            IndexReader newReader = DirectoryReader.openIfChanged((DirectoryReader) oldReader);
            if (newReader != null) {
                oldReader.close();
                readerMap.put(indexName, newReader);
                
                // 更新搜索器
                searcherMap.put(indexName, new IndexSearcher(newReader));
            } else {
                // 没有变化，保留原读取器
                readerMap.put(indexName, oldReader);
            }
        }
    }

    @Override
    public void createAlias(String indexName, String alias) throws IOException {
        log.info("创建索引别名: {} -> {}", indexName, alias);
        // Lucene不支持别名，忽略此操作
    }

    @Override
    public void deleteAlias(String indexName, String alias) throws IOException {
        log.info("删除索引别名: {} -> {}", indexName, alias);
        // Lucene不支持别名，忽略此操作
    }

    @Override
    public void addDocument(String indexName, Document document) throws IOException {
        IndexWriter writer = getIndexWriter(indexName);
        writer.addDocument(document);
        refreshIndex(indexName);
    }

    @Override
    public void addDocuments(String indexName, List<Document> documents) throws IOException {
        IndexWriter writer = getIndexWriter(indexName);
        writer.addDocuments(documents);
        refreshIndex(indexName);
    }

    @Override
    public void updateDocument(String indexName, Object term, Document document) throws IOException {
        IndexWriter writer = getIndexWriter(indexName);
        writer.updateDocument((Term) term, document);
        refreshIndex(indexName);
    }

    @Override
    public void deleteDocument(String indexName, Object query) throws IOException {
        IndexWriter writer = getIndexWriter(indexName);
        writer.deleteDocuments((Query) query);
        refreshIndex(indexName);
    }

    @Override
    public void deleteAllDocuments(String indexName) throws IOException {
        IndexWriter writer = getIndexWriter(indexName);
        writer.deleteAll();
        refreshIndex(indexName);
    }

    @Override
    public Object search(String indexName, Object query, int limit) throws IOException {
        IndexSearcher searcher = getIndexSearcher(indexName);
        return searcher.search((Query) query, limit);
    }

    @Override
    public Object search(String indexName, Object query, Object sort, int limit) throws IOException {
        IndexSearcher searcher = getIndexSearcher(indexName);
        return searcher.search((Query) query, limit, (Sort) sort);
    }

    @Override
    public List<Document> searchDocuments(String indexName, Object query, int limit) throws IOException {
        IndexSearcher searcher = getIndexSearcher(indexName);
        TopDocs topDocs = searcher.search((Query) query, limit);
        
        List<Document> documents = new ArrayList<>();
        for (int i = 0; i < topDocs.scoreDocs.length; i++) {
            Document doc = searcher.storedFields().document(topDocs.scoreDocs[i].doc);
            documents.add(doc);
        }
        
        return documents;
    }

    @Override
    public Object getAnalyzer() {
        return analyzer;
    }
    
    public IndexSearcher getIndexSearcher(String indexName) throws IOException {
        IndexSearcher searcher = searcherMap.get(indexName);
        if (searcher == null) {
            IndexReader reader = getIndexReader(indexName);
            searcher = new IndexSearcher(reader);
            searcherMap.put(indexName, searcher);
        }
        return searcher;
    }
    
    private IndexReader getIndexReader(String indexName) throws IOException {
        IndexReader reader = readerMap.get(indexName);
        if (reader == null) {
            Directory directory = getDirectory(indexName);
            try {
                reader = DirectoryReader.open(directory);
                readerMap.put(indexName, reader);
            } catch (IOException e) {
                // 如果目录是空的，先创建一个空的索引
                if (e.getMessage() != null && e.getMessage().contains("no segments* file found")) {
                    IndexWriter writer = getIndexWriter(indexName);
                    writer.commit();
                    reader = DirectoryReader.open(directory);
                    readerMap.put(indexName, reader);
                } else {
                    throw e;
                }
            }
        }
        return reader;
    }
    
    private IndexWriter getIndexWriter(String indexName) throws IOException {
        IndexWriter writer = writerMap.get(indexName);
        if (writer == null) {
            Directory directory = getDirectory(indexName);
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            config.setRAMBufferSizeMB(searchProperties.getLucene().getRamBufferSizeMb());
            writer = new IndexWriter(directory, config);
            writerMap.put(indexName, writer);
        }
        return writer;
    }
}
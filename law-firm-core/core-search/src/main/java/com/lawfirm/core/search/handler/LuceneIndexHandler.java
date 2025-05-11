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

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
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

    @Override
    public void createIndex(String indexName, Object mapping) throws IOException {
        Path indexPath = getIndexPath(indexName);
        Directory directory = getDirectory(indexName);
        
        log.info("创建Lucene索引: {}", indexName);
        
        // 确保索引目录存在
        Files.createDirectories(indexPath);
        
        // 配置索引写入器
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        config.setRAMBufferSizeMB(searchProperties.getLucene().getRamBufferSizeMb());
        
        // 创建索引写入器
        IndexWriter writer = new IndexWriter(directory, config);
        writerMap.put(indexName, writer);
        
        // 提交变更
        writer.commit();
        
        log.info("Lucene索引创建成功: {}", indexName);
    }

    @Override
    public void deleteIndex(String indexName) throws IOException {
        log.info("删除Lucene索引: {}", indexName);
        
        // 关闭相关资源
        closeResources(indexName);
        
        // 获取索引路径
        Path indexPath = getIndexPath(indexName);
        
        // 删除索引目录
        if (Files.exists(indexPath)) {
            Files.walk(indexPath)
                .sorted((a, b) -> -a.compareTo(b))
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        log.error("删除索引文件失败: {}", path, e);
                    }
                });
            
            log.info("Lucene索引删除成功: {}", indexName);
        } else {
            log.warn("Lucene索引不存在: {}", indexName);
        }
    }

    @Override
    public void updateSettings(String indexName, String settings) throws IOException {
        log.info("更新Lucene索引设置: {}", indexName);
        // Lucene没有直接的索引设置更新机制，暂不实现
    }

    @Override
    public void updateMapping(String indexName, Object mapping) throws IOException {
        log.info("更新Lucene索引映射: {}", indexName);
        // Lucene没有直接的映射更新机制，暂不实现
    }

    @Override
    public Object getIndex(String indexName) throws IOException {
        log.info("获取Lucene索引信息: {}", indexName);
        
        // 获取索引目录
        Path indexPath = getIndexPath(indexName);
        
        // 构建返回对象
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> indexInfo = new HashMap<>();
        
        // 添加基本信息
        indexInfo.put("name", indexName);
        indexInfo.put("path", indexPath.toString());
        
        // 添加索引统计信息
        try {
            IndexReader reader = getIndexReader(indexName);
            indexInfo.put("numDocs", reader.numDocs());
            indexInfo.put("maxDoc", reader.maxDoc());
            indexInfo.put("hasDeletions", reader.hasDeletions());
            indexInfo.put("deletedDocs", reader.maxDoc() - reader.numDocs());
        } catch (Exception e) {
            log.warn("获取索引统计信息失败: {}", indexName, e);
            indexInfo.put("error", e.getMessage());
        }
        
        // 添加设置和映射信息
        Map<String, Object> settings = new HashMap<>();
        settings.put("index.directory", indexPath.toString());
        settings.put("index.analyzer", analyzer.getClass().getSimpleName());
        
        Map<String, Object> mappings = new HashMap<>();
        
        indexInfo.put("settings", settings);
        indexInfo.put("mappings", mappings);
        
        result.put(indexName, indexInfo);
        
        return result;
    }

    @Override
    public boolean existsIndex(String indexName) throws IOException {
        Path indexPath = getIndexPath(indexName);
        boolean exists = Files.exists(indexPath) && Files.isDirectory(indexPath);
        return exists;
    }

    @Override
    public void openIndex(String indexName) throws IOException {
        log.info("打开Lucene索引: {}", indexName);
        
        if (!existsIndex(indexName)) {
            createIndex(indexName, null);
        }
        
        // 确保资源已初始化
        getDirectory(indexName);
        getIndexWriter(indexName);
        getIndexReader(indexName);
        getIndexSearcher(indexName);
    }

    @Override
    public void closeIndex(String indexName) throws IOException {
        log.info("关闭Lucene索引: {}", indexName);
        closeResources(indexName);
    }

    @Override
    public void refreshIndex(String indexName) throws IOException {
        log.info("刷新Lucene索引: {}", indexName);
        
        // 提交写入器
        IndexWriter writer = getIndexWriter(indexName);
        writer.commit();
        
        // 刷新读取器和搜索器
        refreshReaderAndSearcher(indexName);
    }

    @Override
    public void createAlias(String indexName, String alias) throws IOException {
        log.info("创建Lucene索引别名: {} -> {}", indexName, alias);
        
        // Lucene没有直接的索引别名机制，我们创建一个软链接
        Path indexPath = getIndexPath(indexName);
        Path aliasPath = getIndexPath(alias);
        
        // 如果别名已经存在，先删除
        if (Files.exists(aliasPath)) {
            if (Files.isSymbolicLink(aliasPath)) {
                Files.delete(aliasPath);
            } else {
                log.warn("别名路径已存在且不是符号链接: {}", aliasPath);
                return;
            }
        }
        
        // 创建符号链接
        Files.createSymbolicLink(aliasPath, indexPath);
        log.info("Lucene索引别名创建成功: {} -> {}", indexName, alias);
    }

    @Override
    public void deleteAlias(String indexName, String alias) throws IOException {
        log.info("删除Lucene索引别名: {} -> {}", indexName, alias);
        
        Path aliasPath = getIndexPath(alias);
        
        // 如果别名存在且是符号链接，则删除
        if (Files.exists(aliasPath) && Files.isSymbolicLink(aliasPath)) {
            Files.delete(aliasPath);
            log.info("Lucene索引别名删除成功: {}", alias);
        } else {
            log.warn("Lucene索引别名不存在或不是符号链接: {}", alias);
        }
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
                if (e.getMessage().contains("no segments* file found")) {
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
    
    private Directory getDirectory(String indexName) throws IOException {
        Directory directory = directoryMap.get(indexName);
        if (directory == null) {
            Path indexPath = getIndexPath(indexName);
            Files.createDirectories(indexPath);
            directory = FSDirectory.open(indexPath);
            directoryMap.put(indexName, directory);
        }
        return directory;
    }
    
    private Path getIndexPath(String indexName) {
        String basePath = searchProperties.getLucene().getIndexDir();
        return Paths.get(basePath, indexName);
    }
    
    private void refreshReaderAndSearcher(String indexName) throws IOException {
        // 关闭旧的读取器
        IndexReader oldReader = readerMap.remove(indexName);
        if (oldReader != null) {
            oldReader.close();
        }
        
        // 关闭旧的搜索器
        searcherMap.remove(indexName);
        
        // 重新初始化读取器和搜索器
        getIndexReader(indexName);
        getIndexSearcher(indexName);
    }
    
    private void closeResources(String indexName) throws IOException {
        // 关闭搜索器
        searcherMap.remove(indexName);
        
        // 关闭读取器
        IndexReader reader = readerMap.remove(indexName);
        if (reader != null) {
            reader.close();
        }
        
        // 关闭写入器
        IndexWriter writer = writerMap.remove(indexName);
        if (writer != null) {
            writer.close();
        }
        
        // 关闭目录
        Directory directory = directoryMap.remove(indexName);
        if (directory != null) {
            directory.close();
        }
    }
} 
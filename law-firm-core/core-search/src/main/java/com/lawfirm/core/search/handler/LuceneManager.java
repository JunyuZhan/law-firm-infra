package com.lawfirm.core.search.handler;

import com.lawfirm.core.search.config.SearchProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lucene索引管理器
 * 负责管理Lucene索引的底层操作
 */
@Slf4j
public class LuceneManager {

    private final SearchProperties searchProperties;
    
    @Getter
    private final Analyzer analyzer;
    
    private final Map<String, Directory> directoryMap = new ConcurrentHashMap<>();
    private final Map<String, IndexWriter> writerMap = new ConcurrentHashMap<>();
    private final Map<String, DirectoryReader> readerMap = new ConcurrentHashMap<>();
    private final Map<String, IndexSearcher> searcherMap = new ConcurrentHashMap<>();
    
    public LuceneManager(SearchProperties searchProperties) {
        this.searchProperties = searchProperties;
        this.analyzer = new IKAnalyzer(true);
        log.info("创建Lucene管理器");
    }
    
    /**
     * 获取或创建索引目录
     */
    public Directory getDirectory(String indexName) throws IOException {
        Directory directory = directoryMap.get(indexName);
        if (directory == null) {
            Path indexPath = getIndexPath(indexName);
            Files.createDirectories(indexPath);
            directory = FSDirectory.open(indexPath);
            directoryMap.put(indexName, directory);
        }
        return directory;
    }
    
    /**
     * 获取索引路径
     */
    public Path getIndexPath(String indexName) {
        String basePath = searchProperties.getLucene().getIndexDir();
        return Paths.get(basePath, indexName);
    }
    
    /**
     * If directory exists as non-empty, read the segments file to determine if directory is valid.
     * @param indexName the index name
     * @return true if directory exists and has segments file
     */
    public boolean validateIndexDirectory(String indexName) {
        Path indexPath = getIndexPath(indexName);
        if (!Files.exists(indexPath)) {
            return false;
        }
        
        try {
            Directory dir = getDirectory(indexName);
            return DirectoryReader.indexExists(dir);
        } catch (IOException e) {
            log.warn("检查索引目录时发生异常: {}", indexName, e);
            return false;
        }
    }
    
    /**
     * 获取IndexWriter实例
     */
    public IndexWriter getIndexWriter(String indexName) throws IOException {
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
    
    /**
     * 获取DirectoryReader实例
     */
    public DirectoryReader getIndexReader(String indexName) throws IOException {
        DirectoryReader reader = readerMap.get(indexName);
        if (reader == null) {
            Directory directory = getDirectory(indexName);
            try {
                reader = DirectoryReader.open(directory);
            } catch (IOException e) {
                // 如果索引不存在，创建一个空索引
                if (e.getMessage() != null && e.getMessage().contains("no segments")) {
                    IndexWriter writer = getIndexWriter(indexName);
                    writer.commit();
                    reader = DirectoryReader.open(directory);
                } else {
                    throw e;
                }
            }
            readerMap.put(indexName, reader);
        } else {
            // 检查索引是否有更新
            DirectoryReader newReader = DirectoryReader.openIfChanged(reader);
            if (newReader != null) {
                reader.close();
                reader = newReader;
                readerMap.put(indexName, reader);
            }
        }
        return reader;
    }
    
    /**
     * 获取IndexSearcher实例
     */
    public IndexSearcher getIndexSearcher(String indexName) throws IOException {
        IndexSearcher searcher = searcherMap.get(indexName);
        if (searcher == null) {
            DirectoryReader reader = getIndexReader(indexName);
            searcher = new IndexSearcher(reader);
            searcherMap.put(indexName, searcher);
        } else {
            // 检查reader是否有更新
            DirectoryReader currentReader = (DirectoryReader) searcher.getIndexReader();
            DirectoryReader newReader = DirectoryReader.openIfChanged(currentReader);
            if (newReader != null) {
                searcher = new IndexSearcher(newReader);
                searcherMap.put(indexName, searcher);
                currentReader.close();
            }
        }
        return searcher;
    }
    
    /**
     * 关闭所有资源
     */
    public void close() {
        closeReaders();
        closeWriters();
        closeDirectories();
    }
    
    /**
     * 关闭指定索引的所有资源
     */
    public void closeIndex(String indexName) throws IOException {
        // 关闭读取器
        DirectoryReader reader = readerMap.remove(indexName);
        if (reader != null) {
            reader.close();
        }
        
        // 移除搜索器
        searcherMap.remove(indexName);
        
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
    
    /**
     * 关闭所有读取器
     */
    private void closeReaders() {
        for (Map.Entry<String, DirectoryReader> entry : readerMap.entrySet()) {
            try {
                entry.getValue().close();
            } catch (IOException e) {
                log.error("关闭读取器失败: {}", entry.getKey(), e);
            }
        }
        readerMap.clear();
        searcherMap.clear();
    }
    
    /**
     * 关闭所有写入器
     */
    private void closeWriters() {
        for (Map.Entry<String, IndexWriter> entry : writerMap.entrySet()) {
            try {
                entry.getValue().close();
            } catch (IOException e) {
                log.error("关闭写入器失败: {}", entry.getKey(), e);
            }
        }
        writerMap.clear();
    }
    
    /**
     * 关闭所有目录
     */
    private void closeDirectories() {
        for (Map.Entry<String, Directory> entry : directoryMap.entrySet()) {
            try {
                entry.getValue().close();
            } catch (IOException e) {
                log.error("关闭目录失败: {}", entry.getKey(), e);
            }
        }
        directoryMap.clear();
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
    public void deleteDocuments(String indexName, Query query) throws IOException {
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
     * 搜索文档并返回TopDocs
     */
    public TopDocs search(String indexName, Query query, int limit) throws IOException {
        IndexSearcher searcher = getIndexSearcher(indexName);
        return searcher.search(query, limit);
    }

    /**
     * 搜索文档并返回TopDocs（带排序）
     */
    public TopDocs search(String indexName, Query query, Sort sort, int limit) throws IOException {
        IndexSearcher searcher = getIndexSearcher(indexName);
        return searcher.search(query, limit, sort);
    }

    /**
     * 搜索文档
     */
    public List<Document> searchDocuments(String indexName, Query query, int limit) throws IOException {
        IndexSearcher searcher = getIndexSearcher(indexName);
        TopDocs topDocs = searcher.search(query, limit);
        
        List<Document> documents = new ArrayList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = searcher.storedFields().document(scoreDoc.doc);
            documents.add(doc);
        }
        
        return documents;
    }

    /**
     * 搜索文档（带排序）
     */
    public List<Document> searchDocuments(String indexName, Query query, Sort sort, int limit) throws IOException {
        IndexSearcher searcher = getIndexSearcher(indexName);
        TopDocs topDocs = searcher.search(query, limit, sort);
        
        List<Document> documents = new ArrayList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = searcher.storedFields().document(scoreDoc.doc);
            documents.add(doc);
        }
        
        return documents;
    }
} 
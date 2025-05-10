package com.lawfirm.knowledge.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

/**
 * 存储服务配置
 */
@Slf4j
@Configuration
public class StorageServiceConfig {

    private final KnowledgeProperties knowledgeProperties;

    public StorageServiceConfig(KnowledgeProperties knowledgeProperties) {
        this.knowledgeProperties = knowledgeProperties;
    }

    /**
     * 文件服务实现
     */
    @Bean(name = "knowledgeFileServiceImpl")
    @ConditionalOnMissingBean(name = "fileServiceImpl")
    public Object fileServiceImpl() {
        log.warn("未检测到文件服务实现，使用默认本地文件系统实现");
        return new FileServiceImpl(knowledgeProperties.getStorage().getBasePath());
    }

    /**
     * 存储桶服务实现
     */
    @Bean(name = "knowledgeBucketServiceImpl")
    @ConditionalOnMissingBean(name = "bucketServiceImpl")
    public Object bucketServiceImpl() {
        log.warn("未检测到存储桶服务实现，使用默认空实现");
        return new BucketServiceImpl();
    }
    
    /**
     * 文件服务本地实现
     */
    public static class FileServiceImpl {
        private final String basePath;
        
        public FileServiceImpl(String basePath) {
            this.basePath = basePath;
        }
        
            public Object uploadFile(Long bucketId, MultipartFile file, Map<String, String> metadata) throws IOException {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(basePath, fileName);
                
                // 创建目录（如果不存在）
                File directory = new File(basePath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                
                // 保存文件
                Files.copy(file.getInputStream(), filePath);
                log.info("文件上传到本地文件系统: {}", filePath);
                
            return new FileInfo(
                fileName,
                file.getOriginalFilename(),
                filePath.toString(),
                file.getSize(),
                file.getContentType()
            );
        }
        
        public boolean deleteFile(Long fileId) {
            log.info("删除文件: {}", fileId);
            return true;
        }
    }
    
    /**
     * 文件信息
     */
    public static class FileInfo {
        private final String id;
        private final String fileName;
        private final String filePath;
        private final long fileSize;
        private final String fileType;
        
        public FileInfo(String id, String fileName, String filePath, long fileSize, String fileType) {
            this.id = id;
            this.fileName = fileName;
            this.filePath = filePath;
            this.fileSize = fileSize;
            this.fileType = fileType;
        }
        
                    public String getId() {
            return id;
                    }
                    
                    public String getFileName() {
            return fileName;
                    }
                    
                    public String getFilePath() {
            return filePath;
                    }
                    
                    public long getFileSize() {
            return fileSize;
                    }
                    
                    public String getFileType() {
            return fileType;
                    }
                    
                    @Override
                    public String toString() {
                        return "FileInfo{" +
                    "fileName='" + fileName + '\'' +
                                ", filePath='" + filePath + '\'' +
                    ", fileSize=" + fileSize +
                    ", fileType='" + fileType + '\'' +
                                '}';
                    }
    }

    /**
     * 存储桶服务实现
     */
    public static class BucketServiceImpl {
            public Object getBucketByName(String name) {
                log.info("获取存储桶: {}", name);
                // 如果请求的是知识文档专用存储桶，返回一个默认桶
                if ("knowledge-documents".equals(name)) {
                return new BucketInfo(1L, "knowledge-documents", "LOCAL");
                }
                return null;
            }
            
            public Object createBucket(Object bucket) {
                log.info("创建存储桶: {}", bucket);
            return new BucketInfo(1L, "new-bucket", "LOCAL");
        }
    }
    
    /**
     * 存储桶信息
     */
    public static class BucketInfo {
        private final Long id;
        private final String bucketName;
        private final String storageType;
        
        public BucketInfo(Long id, String bucketName, String storageType) {
            this.id = id;
            this.bucketName = bucketName;
            this.storageType = storageType;
        }
        
                    public Long getId() {
            return id;
                    }
        
        public String getBucketName() {
            return bucketName;
            }
        
        public String getStorageType() {
            return storageType;
        }
    }
} 
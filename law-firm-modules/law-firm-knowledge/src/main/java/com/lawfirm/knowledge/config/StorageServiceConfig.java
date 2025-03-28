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
     * 文件服务空实现
     */
    @Bean
    @ConditionalOnMissingBean(name = "fileServiceImpl")
    public Object fileServiceImpl() {
        log.warn("未检测到文件服务实现，使用默认本地文件系统实现");
        // 由于FileService接口可能有多个方法，这里返回一个基本实现
        return new Object() {
            public Object uploadFile(Long bucketId, MultipartFile file, Map<String, String> metadata) throws IOException {
                // 使用配置的本地存储路径
                String basePath = knowledgeProperties.getStorage().getBasePath();
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
                
                // 返回文件信息
                return new Object() {
                    public String getId() {
                        return fileName;
                    }
                    
                    public String getFileName() {
                        return file.getOriginalFilename();
                    }
                    
                    public String getFilePath() {
                        return filePath.toString();
                    }
                    
                    public long getFileSize() {
                        return file.getSize();
                    }
                    
                    public String getFileType() {
                        return file.getContentType();
                    }
                    
                    @Override
                    public String toString() {
                        return "FileInfo{" +
                                "fileName='" + file.getOriginalFilename() + '\'' +
                                ", filePath='" + filePath + '\'' +
                                ", fileSize=" + file.getSize() +
                                ", fileType='" + file.getContentType() + '\'' +
                                '}';
                    }
                };
            }
            
            public boolean deleteFile(Long fileId) {
                log.info("删除文件: {}", fileId);
                return true;
            }
        };
    }

    /**
     * 存储桶服务空实现
     */
    @Bean
    @ConditionalOnMissingBean(name = "bucketServiceImpl")
    public Object bucketServiceImpl() {
        log.warn("未检测到存储桶服务实现，使用默认空实现");
        // 由于BucketService接口可能有多个方法，这里返回一个基本实现
        return new Object() {
            public Object getBucketByName(String name) {
                log.info("获取存储桶: {}", name);
                // 如果请求的是知识文档专用存储桶，返回一个默认桶
                if ("knowledge-documents".equals(name)) {
                    return new Object() {
                        public Long getId() {
                            return 1L;
                        }
                        
                        public String getBucketName() {
                            return "knowledge-documents";
                        }
                        
                        public String getStorageType() {
                            return "LOCAL";
                        }
                    };
                }
                return null;
            }
            
            public Object createBucket(Object bucket) {
                log.info("创建存储桶: {}", bucket);
                return new Object() {
                    public Long getId() {
                        return 1L;
                    }
                };
            }
        };
    }
} 
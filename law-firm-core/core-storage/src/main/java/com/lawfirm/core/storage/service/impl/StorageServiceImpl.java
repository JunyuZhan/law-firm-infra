package com.lawfirm.core.storage.service.impl;

import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.model.FileMetadata;
import com.lawfirm.core.storage.service.StorageService;
import com.lawfirm.core.storage.strategy.AbstractStorageStrategy;
import com.lawfirm.core.storage.strategy.MinioStorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 存储服务实现类
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {
    
    private final StorageProperties storageProperties;
    private final MongoTemplate mongoTemplate;
    private final AbstractStorageStrategy storageStrategy;
    
    public StorageServiceImpl(StorageProperties storageProperties, MongoTemplate mongoTemplate) {
        this.storageProperties = storageProperties;
        this.mongoTemplate = mongoTemplate;
        
        // 根据配置创建存储策略
        this.storageStrategy = switch (storageProperties.getType().toLowerCase()) {
            case "minio" -> new MinioStorageStrategy(storageProperties);
            // TODO: 实现其他存储策略
            default -> throw new IllegalArgumentException("Unsupported storage type: " + storageProperties.getType());
        };
        
        log.info("Using storage strategy: {}", storageProperties.getType());
    }
    
    @Override
    public FileMetadata upload(MultipartFile file, String businessType, String businessId) {
        FileMetadata metadata = storageStrategy.upload(file, businessType, businessId);
        return saveMetadata(metadata);
    }
    
    @Override
    public FileMetadata upload(InputStream inputStream, String filename, String contentType, String businessType, String businessId) {
        FileMetadata metadata = storageStrategy.upload(inputStream, filename, contentType, businessType, businessId);
        return saveMetadata(metadata);
    }
    
    @Override
    public void delete(String id) {
        FileMetadata metadata = getMetadata(id);
        if (metadata != null) {
            storageStrategy.doDelete(metadata.getPath());
            mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), FileMetadata.class);
        }
    }
    
    @Override
    public void deleteBatch(List<String> ids) {
        for (String id : ids) {
            delete(id);
        }
    }
    
    @Override
    public FileMetadata getMetadata(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where("id").is(id)), FileMetadata.class);
    }
    
    @Override
    public String getUrl(String id) {
        FileMetadata metadata = getMetadata(id);
        return metadata != null ? storageStrategy.doGetUrl(metadata.getPath()) : null;
    }
    
    @Override
    public String getUrl(String id, long expireSeconds) {
        FileMetadata metadata = getMetadata(id);
        return metadata != null ? storageStrategy.doGetUrl(metadata.getPath(), expireSeconds) : null;
    }
    
    @Override
    public InputStream download(String id) {
        FileMetadata metadata = getMetadata(id);
        return metadata != null ? storageStrategy.doDownload(metadata.getPath()) : null;
    }
    
    @Override
    public List<FileMetadata> listByBusiness(String businessType, String businessId) {
        Query query = Query.query(Criteria.where("businessType").is(businessType)
                .and("businessId").is(businessId));
        return mongoTemplate.find(query, FileMetadata.class);
    }
    
    /**
     * 保存文件元数据
     */
    private FileMetadata saveMetadata(FileMetadata metadata) {
        return mongoTemplate.save(metadata, storageProperties.getMongo().getCollection());
    }
} 
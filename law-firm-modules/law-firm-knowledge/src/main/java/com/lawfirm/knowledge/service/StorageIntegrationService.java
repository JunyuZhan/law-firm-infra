package com.lawfirm.knowledge.service;

import com.lawfirm.model.knowledge.entity.KnowledgeAttachment;
import com.lawfirm.model.knowledge.service.KnowledgeAttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 存储服务集成
 * 示例如何集成core-storage模块
 */
@Slf4j
@Service("knowledgeStorageService")
public class StorageIntegrationService {

    @Autowired
    private KnowledgeAttachmentService knowledgeAttachmentService;

    @Autowired
    @Qualifier("storageFileServiceImpl")
    private Object fileService;

    @Autowired
    @Qualifier("storageBucketServiceImpl")
    private Object bucketService;

    /**
     * 上传知识文档附件
     *
     * @param file 文件
     * @param knowledgeId 知识ID
     * @return 附件ID
     */
    public Long uploadKnowledgeAttachment(MultipartFile file, Long knowledgeId) throws IOException {
        if (file == null || file.isEmpty() || knowledgeId == null) {
            log.error("文件或知识ID为空");
            return null;
        }

        try {
            // 获取知识文档存储桶
            Long bucketId = getOrCreateKnowledgeBucket();
            
            // 设置附件元数据
            Map<String, String> metadata = new HashMap<>();
            metadata.put("knowledgeId", knowledgeId.toString());
            metadata.put("docType", "knowledge_attachment");
            
            // 上传文件
            Object fileInfo = ((UploadFileFunction) fileService).uploadFile(bucketId, file, metadata);
            
            // 保存附件关联信息
            KnowledgeAttachment attachment = new KnowledgeAttachment();
            attachment.setKnowledgeId(knowledgeId);
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFileSize(file.getSize());
            attachment.setFileType(file.getContentType());
            attachment.setStorageId(Long.valueOf(getFileId(fileInfo)));
            knowledgeAttachmentService.save(attachment);
            
            log.info("知识附件上传成功: knowledgeId={}, fileName={}", knowledgeId, file.getOriginalFilename());
            return attachment.getId();
        } catch (Exception e) {
            log.error("知识附件上传失败: knowledgeId={}, fileName={}, error={}", 
                    knowledgeId, file.getOriginalFilename(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 获取或创建知识文档存储桶
     */
    private Long getOrCreateKnowledgeBucket() {
        // 查询或创建知识文档专用存储桶
        Object bucket = ((GetBucketFunction) bucketService).getBucketByName("knowledge-documents");
        if (bucket == null) {
            bucket = new BucketInfo();
            ((CreateBucketFunction) bucketService).createBucket(bucket);
        }
        return getBucketId(bucket);
    }

    /**
     * 获取文件ID
     */
    private String getFileId(Object fileInfo) {
        try {
            return ((IdGetter) fileInfo).getId();
        } catch (Exception e) {
            // 反射获取ID
            return fileInfo.toString();
        }
    }

    /**
     * 获取存储桶ID
     */
    private Long getBucketId(Object bucket) {
        try {
            String id = ((IdGetter) bucket).getId();
            return Long.valueOf(id);
        } catch (Exception e) {
            // 默认ID
            return 1L;
        }
    }

    /**
     * 存储桶信息
     */
    private static class BucketInfo {
        public Long getId() {
            return 1L;
        }
        
        public String getBucketName() {
            return "knowledge-documents";
        }
        
        public String getStorageType() {
            return "LOCAL";
        }
    }

    /**
     * 上传文件函数接口
     */
    private interface UploadFileFunction {
        Object uploadFile(Long bucketId, MultipartFile file, Map<String, String> metadata) throws IOException;
    }

    /**
     * 获取存储桶函数接口
     */
    private interface GetBucketFunction {
        Object getBucketByName(String name);
    }

    /**
     * 创建存储桶函数接口
     */
    private interface CreateBucketFunction {
        Object createBucket(Object bucket);
    }

    /**
     * ID获取接口
     */
    private interface IdGetter {
        String getId();
    }
} 
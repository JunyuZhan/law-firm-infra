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
 * 集成core-storage模块
 */
@Slf4j
@Service("knowledgeStorageService")
public class StorageIntegrationService {

    private static final String BUCKET_NAME = "knowledge-documents";

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
            Object fileObj = ((UploadFileFunction) fileService).uploadFile(bucketId, file, metadata);
            
            // 保存附件关联信息
            KnowledgeAttachment attachment = new KnowledgeAttachment();
            attachment.setKnowledgeId(knowledgeId);
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFileSize(file.getSize());
            attachment.setFileType(file.getContentType());
            attachment.setStorageId(Long.valueOf(getFileId(fileObj)));
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
        try {
            // 查询或创建知识文档专用存储桶
            Object bucket = ((GetBucketFunction) bucketService).getBucketByName(BUCKET_NAME);
            if (bucket == null) {
                bucket = ((CreateBucketFunction) bucketService).createBucket(BUCKET_NAME);
            }
            
            if (bucket != null) {
                try {
                    // 尝试通过反射获取ID
                    return (Long) bucket.getClass().getMethod("getId").invoke(bucket);
                } catch (Exception e) {
                    // 如果反射失败，返回默认ID
                    log.warn("无法获取存储桶ID，使用默认值: 1", e);
                    return 1L;
                }
            }
            
            return 1L;
        } catch (Exception e) {
            log.error("获取或创建存储桶失败", e);
            return 1L;
        }
    }
    
    /**
     * 获取文件ID
     */
    private String getFileId(Object fileObj) {
        if (fileObj == null) {
            return "1";
        }
        
        try {
            // 尝试通过反射获取ID
            return fileObj.getClass().getMethod("getId").invoke(fileObj).toString();
        } catch (Exception e) {
            // 如果反射失败，返回对象的toString
            log.warn("无法获取文件ID，使用对象字符串表示", e);
            return fileObj.toString();
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
        Object createBucket(String bucketName);
    }
} 
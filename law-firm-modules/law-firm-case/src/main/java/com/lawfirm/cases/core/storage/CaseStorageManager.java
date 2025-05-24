package com.lawfirm.cases.core.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.lawfirm.model.storage.service.StorageService;
import com.lawfirm.model.storage.service.FileService;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 案件存储管理器
 * <p>
 * 负责与core-storage模块集成，实现案件相关文件的存储管理功能。
 * 包括文件上传、下载、删除和管理等。
 * </p>
 *
 * @author JunyuZhan
 */
@Component
public class CaseStorageManager {
    
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CaseStorageManager.class);
    
    private final StorageService storageService;
    private final FileService fileService;
    
    private static final String CASE_BUCKET = "case-documents";
    private static final String CASE_FILE_PREFIX = "case/";
    
    public CaseStorageManager(
        @Qualifier("storageServiceImpl") StorageService storageService,
        @Qualifier("storageFileServiceImpl") FileService fileService
    ) {
        this.storageService = storageService;
        this.fileService = fileService;
    }
    
    /**
     * 上传案件文件
     *
     * @param caseId 案件ID
     * @param file 文件
     * @param fileType 文件类型 (CONTRACT/EVIDENCE/REPORT/NOTE/OTHER)
     * @param metadata 元数据
     * @return 文件ID
     */
    public String uploadCaseFile(Long caseId, MultipartFile file, String fileType, Map<String, String> metadata) {
        log.info("上传案件文件, 案件ID: {}, 文件类型: {}, 文件名: {}", caseId, fileType, file.getOriginalFilename());
        
        try {
            // 构建文件存储路径
            String filePath = buildFilePath(caseId, fileType, file.getOriginalFilename());
            
            // 添加案件相关元数据
            Map<String, String> fullMetadata = enrichMetadata(caseId, fileType, metadata);
            
            // 调用core-storage上传文件
            String fileId = storageService.uploadFile(CASE_BUCKET, filePath, file.getInputStream(), 
                    file.getSize(), file.getContentType(), fullMetadata);
            
            log.info("案件文件上传成功, 文件ID: {}", fileId);
            return fileId;
        } catch (Exception e) {
            log.error("案件文件上传失败", e);
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
    }
    
    /**
     * 下载案件文件
     *
     * @param fileId 文件ID
     * @return 文件输入流
     */
    public InputStream downloadCaseFile(String fileId) {
        log.info("下载案件文件, 文件ID: {}", fileId);
        
        try {
            // 调用core-storage下载文件
            return storageService.downloadFile(CASE_BUCKET, fileId);
        } catch (Exception e) {
            log.error("案件文件下载失败", e);
            throw new RuntimeException("文件下载失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取案件文件元数据
     *
     * @param fileId 文件ID
     * @return 文件元数据
     */
    public Map<String, String> getCaseFileMetadata(String fileId) {
        log.info("获取案件文件元数据, 文件ID: {}", fileId);
        
        try {
            // 调用core-storage获取文件元数据
            return storageService.getFileMetadata(CASE_BUCKET, fileId);
        } catch (Exception e) {
            log.error("获取案件文件元数据失败", e);
            throw new RuntimeException("获取文件元数据失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除案件文件
     *
     * @param fileId 文件ID
     */
    public void deleteCaseFile(String fileId) {
        log.info("删除案件文件, 文件ID: {}", fileId);
        
        try {
            // 调用core-storage删除文件
            storageService.deleteFile(CASE_BUCKET, fileId);
            
            log.info("案件文件删除成功, 文件ID: {}", fileId);
        } catch (Exception e) {
            log.error("案件文件删除失败", e);
            throw new RuntimeException("文件删除失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取案件所有文件
     *
     * @param caseId 案件ID
     * @return 文件列表
     */
    public List<Map<String, Object>> getCaseFiles(Long caseId) {
        log.info("获取案件所有文件, 案件ID: {}", caseId);
        
        try {
            String prefix = CASE_FILE_PREFIX + caseId + "/";
            
            // 调用core-storage获取文件列表
            return storageService.listFiles(CASE_BUCKET, prefix);
        } catch (Exception e) {
            log.error("获取案件文件列表失败", e);
            throw new RuntimeException("获取文件列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取案件特定类型的文件
     *
     * @param caseId 案件ID
     * @param fileType 文件类型
     * @return 文件列表
     */
    public List<Map<String, Object>> getCaseFilesByType(Long caseId, String fileType) {
        log.info("获取案件特定类型的文件, 案件ID: {}, 文件类型: {}", caseId, fileType);
        
        try {
            String prefix = CASE_FILE_PREFIX + caseId + "/" + fileType + "/";
            
            // 调用core-storage获取文件列表
            return storageService.listFiles(CASE_BUCKET, prefix);
        } catch (Exception e) {
            log.error("获取案件特定类型文件列表失败", e);
            throw new RuntimeException("获取文件列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取文件访问URL
     *
     * @param fileId 文件ID
     * @param expirationInMinutes URL有效期(分钟)
     * @return 文件访问URL
     */
    public String getFileUrl(String fileId, int expirationInMinutes) {
        log.info("获取文件访问URL, 文件ID: {}, 有效期: {}分钟", fileId, expirationInMinutes);
        
        try {
            // 调用core-storage获取文件URL
            return storageService.getPresignedUrl(CASE_BUCKET, fileId, expirationInMinutes);
        } catch (Exception e) {
            log.error("获取文件访问URL失败", e);
            throw new RuntimeException("获取文件URL失败: " + e.getMessage());
        }
    }
    
    // 构建文件路径
    private String buildFilePath(Long caseId, String fileType, String fileName) {
        return CASE_FILE_PREFIX + caseId + "/" + fileType + "/" + fileName;
    }
    
    // 丰富元数据
    private Map<String, String> enrichMetadata(Long caseId, String fileType, Map<String, String> metadata) {
        Map<String, String> fullMetadata = metadata != null ? new HashMap<>(metadata) : new HashMap<>();
        fullMetadata.put("caseId", caseId.toString());
        fullMetadata.put("fileType", fileType);
        fullMetadata.put("uploadTime", String.valueOf(System.currentTimeMillis()));
        return fullMetadata;
    }
} 
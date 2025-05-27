package com.lawfirm.core.storage.service.support;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.util.FileTypeUtils;
import com.lawfirm.model.storage.dto.file.StorageFileUploadDTO;
import com.lawfirm.model.storage.entity.file.FileInfo;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.mapper.StorageBucketMapper;
import com.lawfirm.model.storage.mapper.FileObjectMapper;
import com.lawfirm.model.storage.enums.FileTypeEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件上传器，支持多种上传方式
 * 复用common-util模块中的工具类
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "law-firm.core.storage.enabled", havingValue = "true", matchIfMissing = false)
public class FileUploader {
    
    private final StorageProperties storageProperties;
    private final FileOperator fileOperator;
    private final StorageBucketMapper bucketMapper;
    private final FileObjectMapper fileObjectMapper;
    
    /**
     * 上传文件 - 从MultipartFile
     * 
     * @param file 上传的文件
     * @param bucketId 存储桶ID
     * @param description 文件描述
     * @param tags 文件标签
     * @return 文件对象
     */
    public FileObject upload(MultipartFile file, Long bucketId, String description, String tags) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }
        
        // 检查文件大小
        if (file.getSize() > storageProperties.getUpload().getMaxSize()) {
            throw new IllegalArgumentException("文件大小超过限制: " + 
                    file.getSize() + " > " + storageProperties.getUpload().getMaxSize());
        }
        
        // 检查文件类型
        String fileName = file.getOriginalFilename();
        validateFileName(fileName);
        
        // 创建文件对象
        FileObject fileObject = createFileObject(bucketId, fileName, file.getSize(), description, tags);
        
        // 上传文件
        boolean success = fileOperator.uploadFile(bucketId, fileObject, file);
        if (!success) {
            throw new IOException("文件上传失败");
        }
        
        return fileObject;
    }
    
    /**
     * 上传文件 - 从DTO (Base64编码内容)
     * 
     * @param uploadDTO 上传DTO
     * @return 文件对象
     */
    public FileObject upload(StorageFileUploadDTO uploadDTO) throws IOException {
        if (uploadDTO == null || !StringUtils.hasText(uploadDTO.getFileName()) || 
                !StringUtils.hasText(uploadDTO.getContent())) {
            throw new IllegalArgumentException("上传信息不完整");
        }
        
        // 解码Base64内容
        byte[] fileContent;
        try {
            fileContent = Base64.getDecoder().decode(uploadDTO.getContent());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Base64内容解码失败", e);
        }
        
        // 检查文件大小
        if (fileContent.length > storageProperties.getUpload().getMaxSize()) {
            throw new IllegalArgumentException("文件大小超过限制: " + 
                    fileContent.length + " > " + storageProperties.getUpload().getMaxSize());
        }
        
        // 检查文件类型
        validateFileName(uploadDTO.getFileName());
        
        // 创建文件对象
        FileObject fileObject = createFileObject(
                uploadDTO.getBucketId(), 
                uploadDTO.getFileName(), 
                fileContent.length, 
                uploadDTO.getDescription(), 
                uploadDTO.getTags()
        );
        
        // 设置元数据，如果有的话
        if (uploadDTO.getMetadata() != null) {
            fileObject.setMetadata(uploadDTO.getMetadata().toString());
        }
        
        // 上传文件
        boolean success = fileOperator.uploadFile(uploadDTO.getBucketId(), fileObject, fileContent);
        if (!success) {
            throw new IOException("文件上传失败");
        }
        
        return fileObject;
    }
    
    /**
     * 上传文件 - 从文本内容
     * 
     * @param fileName 文件名
     * @param content 文本内容
     * @param bucketId 存储桶ID
     * @return 文件对象
     */
    public FileObject uploadText(String fileName, String content, Long bucketId) throws IOException {
        if (!StringUtils.hasText(fileName) || !StringUtils.hasText(content)) {
            throw new IllegalArgumentException("文件名或内容不能为空");
        }
        
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        
        // 检查文件大小
        if (contentBytes.length > storageProperties.getUpload().getMaxSize()) {
            throw new IllegalArgumentException("文件大小超过限制");
        }
        
        // 检查文件类型
        validateFileName(fileName);
        
        // 创建文件对象
        FileObject fileObject = createFileObject(bucketId, fileName, contentBytes.length, null, null);
        
        // 上传文件
        boolean success = fileOperator.uploadFile(bucketId, fileObject, contentBytes);
        if (!success) {
            throw new IOException("文件上传失败");
        }
        
        return fileObject;
    }
    
    /**
     * 上传文件 - 从输入流
     * 
     * @param fileName 文件名
     * @param inputStream 输入流
     * @param size 文件大小
     * @param bucketId 存储桶ID
     * @return 文件对象
     */
    public FileObject upload(String fileName, InputStream inputStream, long size, Long bucketId) throws IOException {
        if (!StringUtils.hasText(fileName) || inputStream == null) {
            throw new IllegalArgumentException("文件名或输入流不能为空");
        }
        
        // 检查文件大小
        if (size > storageProperties.getUpload().getMaxSize()) {
            throw new IllegalArgumentException("文件大小超过限制");
        }
        
        // 检查文件类型
        validateFileName(fileName);
        
        // 创建文件对象
        FileObject fileObject = createFileObject(bucketId, fileName, size, null, null);
        
        // 获取存储桶
        StorageBucket bucket = bucketMapper.selectById(bucketId);
        if (bucket == null) {
            throw new IllegalArgumentException("存储桶不存在: " + bucketId);
        }
        
        // 上传文件
        boolean success = fileOperator.uploadFile(bucket, fileObject, inputStream);
        if (!success) {
            throw new IOException("文件上传失败");
        }
        
        return fileObject;
    }
    
    /**
     * 创建文件对象
     */
    private FileObject createFileObject(Long bucketId, String fileName, long size, String description, String tags) {
        // 获取存储桶
        StorageBucket bucket = bucketMapper.selectById(bucketId);
        if (bucket == null) {
            throw new IllegalArgumentException("存储桶不存在: " + bucketId);
        }
        
        // 创建文件对象
        FileObject fileObject = new FileObject();
        fileObject.setFileName(fileName);
        fileObject.setStorageSize(size);
        fileObject.setBucketId(bucketId);
        
        // 设置文件类型
        String extension = FileTypeUtils.getExtension(fileName);
        fileObject.setExtension(extension);
        fileObject.setContentType(FileTypeUtils.getContentType(extension));
        
        // 设置文件信息
        if (StringUtils.hasText(description) || StringUtils.hasText(tags)) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setDescription(description);
            fileInfo.setTags(tags);
            fileObject.setFileInfo(fileInfo);
        }
        
        return fileObject;
    }
    
    /**
     * 获取文件类型
     * 在实际项目中，应该使用common-util中的工具类来实现
     */
    private String getFileType(String fileName) {
        // TODO: 复用common-util中的FileUtils.getFileType
        String extension = getFileExtension(fileName);
        if (extension == null) {
            return "application/octet-stream";
        }
        
        // 简单判断常见类型，实际项目中应该更完善
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "pdf":
                return "application/pdf";
            case "doc":
                return "application/msword";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls":
                return "application/vnd.ms-excel";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "txt":
                return "text/plain";
            case "html":
                return "text/html";
            default:
                return "application/octet-stream";
        }
    }
    
    /**
     * 获取文件扩展名
     * 在实际项目中，应该使用common-util中的工具类来实现
     */
    private String getFileExtension(String fileName) {
        // TODO: 复用common-util中的FileUtils.getExtension
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    
    /**
     * 验证文件名是否合法
     */
    private void validateFileName(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            throw new IllegalArgumentException("文件名不能为空");
        }
        
        // 判断是否是禁止的文件类型
        String extension = getFileExtension(fileName).toLowerCase();
        if (StringUtils.hasText(extension)) {
            String deniedTypes = storageProperties.getUpload().getDeniedTypes();
            if (deniedTypes != null && !deniedTypes.equals("*")) {
                for (String type : deniedTypes.split(",")) {
                    if (extension.equals(type.trim().toLowerCase())) {
                        throw new IllegalArgumentException("不支持的文件类型: " + extension);
                    }
                }
            }
        }
    }
} 
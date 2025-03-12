package com.lawfirm.document.manager.storage.impl;

import com.lawfirm.document.manager.storage.DocumentStorageManager;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.storage.vo.FileVO;
import com.lawfirm.model.storage.vo.BucketVO;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.mapper.FileObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * 文档存储管理器实现
 */
@Component
@RequiredArgsConstructor
public class DocumentStorageManagerImpl implements DocumentStorageManager {

    private final FileService fileService;
    private final BucketService bucketService;
    private final FileObjectMapper fileObjectMapper;

    @Override
    public String uploadDocument(MultipartFile file, String path) {
        // 获取默认存储桶
        BucketVO bucket = getDefaultBucket();
        if (bucket == null) {
            throw new RuntimeException("默认存储桶不存在");
        }

        // 上传文件
        FileVO fileVO = fileService.upload(file, bucket.getId(), path, null);
        return fileService.getAccessUrl(fileVO.getId());
    }

    @Override
    public void deleteDocument(String path) {
        // 获取文件ID并删除
        Long fileId = getFileIdByPath(path);
        if (fileId != null) {
            fileService.delete(fileId);
        }
    }

    @Override
    public InputStream getDocument(String path) {
        // 获取文件ID并下载
        Long fileId = getFileIdByPath(path);
        if (fileId != null) {
            byte[] content = fileService.download(fileId);
            return new ByteArrayInputStream(content);
        }
        return null;
    }

    @Override
    public String getDocumentUrl(String path) {
        return getDocumentUrl(path, null);
    }

    @Override
    public String getDocumentUrl(String path, Long expireTime) {
        // 获取文件ID并生成访问URL
        Long fileId = getFileIdByPath(path);
        if (fileId != null) {
            return expireTime != null ? 
                fileService.getAccessUrl(fileId, expireTime.intValue()) : 
                fileService.getAccessUrl(fileId);
        }
        return null;
    }

    @Override
    public boolean exists(String path) {
        return getFileIdByPath(path) != null;
    }

    @Override
    public void copyDocument(String sourcePath, String targetPath) {
        // 获取源文件ID
        Long sourceFileId = getFileIdByPath(sourcePath);
        if (sourceFileId != null) {
            // 获取默认存储桶
            BucketVO bucket = getDefaultBucket();
            if (bucket != null) {
                // 复制文件到目标路径
                fileService.copyToBucket(sourceFileId, bucket.getId());
            }
        }
    }

    @Override
    public void moveDocument(String sourcePath, String targetPath) {
        // 获取源文件ID
        Long sourceFileId = getFileIdByPath(sourcePath);
        if (sourceFileId != null) {
            // 获取默认存储桶
            BucketVO bucket = getDefaultBucket();
            if (bucket != null) {
                // 移动文件到目标路径
                fileService.moveToBucket(sourceFileId, bucket.getId());
            }
        }
    }

    @Override
    public long getSize(String path) {
        // 获取文件ID并返回大小
        Long fileId = getFileIdByPath(path);
        if (fileId != null) {
            FileVO fileVO = fileService.getInfo(fileId);
            return fileVO != null ? fileVO.getStorageSize() : 0;
        }
        return 0;
    }

    @Override
    public long getLastModified(String path) {
        // 获取文件ID并返回最后修改时间
        Long fileId = getFileIdByPath(path);
        if (fileId != null) {
            FileVO fileVO = fileService.getInfo(fileId);
            return fileVO != null ? fileVO.getLastAccessTime() : 0;
        }
        return 0;
    }

    /**
     * 获取默认存储桶
     */
    private BucketVO getDefaultBucket() {
        // 获取所有存储桶
        List<BucketVO> buckets = bucketService.listAll();
        // 返回第一个有效的存储桶
        return buckets.stream()
            .filter(bucket -> bucket.getStatus() == 1)
            .findFirst()
            .orElse(null);
    }

    /**
     * 根据路径获取文件ID
     */
    private Long getFileIdByPath(String path) {
        BucketVO bucket = getDefaultBucket();
        if (bucket != null) {
            FileObject fileObject = fileObjectMapper.findByBucketIdAndFilePath(bucket.getId(), path);
            return fileObject != null ? fileObject.getId() : null;
        }
        return null;
    }
} 
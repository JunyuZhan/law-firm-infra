package com.lawfirm.document.service.strategy.storage;

import com.lawfirm.core.storage.strategy.StorageStrategy;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.enums.StorageTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * 本地存储策略实现
 */
@Slf4j
@Component
public class LocalStorageStrategy implements StorageStrategy {

    @Value("${storage.local.base-dir:/tmp/law-firm/storage}")
    private String baseDir;

    @Override
    public boolean uploadFile(StorageBucket bucket, FileObject fileObject, InputStream inputStream) {
        try {
            // 确保目录存在
            String dirPath = baseDir + "/" + bucket.getBucketName();
            File dir = new File(dirPath);
            if (!dir.exists() && !dir.mkdirs()) {
                log.error("创建存储目录失败: {}", dirPath);
                return false;
            }

            // 生成文件路径
            String filePath = dirPath + "/" + fileObject.getStoragePath();
            File targetFile = new File(filePath);

            // 确保父目录存在
            File parentDir = targetFile.getParentFile();
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                log.error("创建父目录失败: {}", parentDir.getAbsolutePath());
                return false;
            }

            // 写入文件
            try (FileOutputStream out = new FileOutputStream(targetFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            
            log.info("文件上传成功: {}", filePath);
            return true;
        } catch (IOException e) {
            log.error("文件上传失败", e);
            return false;
        }
    }

    @Override
    public InputStream getObject(StorageBucket bucket, String objectName) {
        try {
            String filePath = baseDir + "/" + bucket.getBucketName() + "/" + objectName;
            File file = new File(filePath);
            
            if (!file.exists() || !file.isFile()) {
                log.error("文件不存在: {}", filePath);
                return null;
            }
            
            return new FileInputStream(file);
        } catch (IOException e) {
            log.error("获取文件失败", e);
            return null;
        }
    }

    @Override
    public boolean removeObject(StorageBucket bucket, String objectName) {
        String filePath = baseDir + "/" + bucket.getBucketName() + "/" + objectName;
        File file = new File(filePath);
        
        if (!file.exists() || !file.isFile()) {
            log.warn("文件不存在，无法删除: {}", filePath);
            return false;
        }
        
        boolean result = file.delete();
        if (result) {
            log.info("文件删除成功: {}", filePath);
        } else {
            log.error("文件删除失败: {}", filePath);
        }
        
        return result;
    }

    @Override
    public String generatePresignedUrl(StorageBucket bucket, String objectName, Integer expireSeconds) {
        // 本地存储策略通常通过Web服务器暴露文件
        // 这里简单返回一个相对路径，实际项目中应该生成完整的URL
        return "/api/document/file/" + bucket.getBucketName() + "/" + objectName;
    }

    @Override
    public StorageTypeEnum getStorageType() {
        return StorageTypeEnum.LOCAL;
    }
    
    @Override
    public void initialize() {
        // 确保基础目录存在
        File baseDirectory = new File(baseDir);
        if (!baseDirectory.exists() && !baseDirectory.mkdirs()) {
            log.error("无法创建基础存储目录: {}", baseDir);
        }
    }
    
    @Override
    public boolean createBucket(String bucketName, boolean isPublic) {
        String bucketPath = baseDir + "/" + bucketName;
        File bucketDir = new File(bucketPath);
        
        if (bucketDir.exists()) {
            return true;
        }
        
        boolean created = bucketDir.mkdirs();
        if (created) {
            log.info("创建本地存储桶成功: {}", bucketPath);
        } else {
            log.error("创建本地存储桶失败: {}", bucketPath);
        }
        
        return created;
    }
    
    @Override
    public boolean bucketExists(String bucketName) {
        String bucketPath = baseDir + "/" + bucketName;
        File bucketDir = new File(bucketPath);
        return bucketDir.exists() && bucketDir.isDirectory();
    }
    
    @Override
    public boolean removeBucket(String bucketName) {
        String bucketPath = baseDir + "/" + bucketName;
        File bucketDir = new File(bucketPath);
        
        if (!bucketDir.exists()) {
            return true;
        }
        
        try {
            Files.walk(bucketDir.toPath())
                    .sorted((a, b) -> -a.compareTo(b))
                    .map(Path::toFile)
                    .forEach(File::delete);
            
            log.info("删除本地存储桶成功: {}", bucketPath);
            return true;
        } catch (IOException e) {
            log.error("删除本地存储桶失败: {}", bucketPath, e);
            return false;
        }
    }
    
    @Override
    public Object getObjectMetadata(StorageBucket bucket, String objectName) {
        String filePath = baseDir + "/" + bucket.getBucketName() + "/" + objectName;
        File file = new File(filePath);
        
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        
        return Map.of(
            "fileName", file.getName(),
            "fileSize", file.length(),
            "lastModified", file.lastModified()
        );
    }
} 
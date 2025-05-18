package com.lawfirm.core.storage.strategy.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.strategy.AbstractStorageStrategy;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.entity.file.FileInfo;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.enums.StorageTypeEnum;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.BucketVersioningConfiguration;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.SetBucketVersioningConfigurationRequest;
import com.qcloud.cos.region.Region;

import lombok.extern.slf4j.Slf4j;

/**
 * 腾讯云COS存储策略实现类
 */
@Component
@Slf4j
public class TencentCosStorageStrategy extends AbstractStorageStrategy {

    private COSClient cosClient;
    private final StorageProperties storageProperties;
    private String appId;
    private String region;
    
    public TencentCosStorageStrategy(StorageProperties storageProperties) {
        super(StorageTypeEnum.TENCENT_COS);
        this.storageProperties = storageProperties;
    }
    
    @Override
    public void initialize() {
        if (initialized) {
            return;
        }
        
        try {
            log.info("初始化腾讯云COS存储客户端...");
            StorageProperties.TencentCosConfig cosConfig = storageProperties.getTencentCos();
            
            // 初始化配置
            this.appId = cosConfig.getAppId();
            this.region = cosConfig.getRegion();
            
            // 创建腾讯云COS客户端
            COSCredentials cred = new BasicCOSCredentials(cosConfig.getSecretId(), cosConfig.getSecretKey());
            ClientConfig clientConfig = new ClientConfig(new Region(region));
            
            cosClient = new COSClient(cred, clientConfig);
            
            log.info("腾讯云COS存储客户端初始化成功");
            initialized = true;
        } catch (Exception e) {
            log.error("初始化腾讯云COS存储客户端失败", e);
            throw new RuntimeException("初始化腾讯云COS存储客户端失败: " + e.getMessage());
        }
    }
    
    @Override
    public boolean isEnabled() {
        StorageProperties.TencentCosConfig cosConfig = storageProperties.getTencentCos();
        return cosConfig != null && cosConfig.isEnabled() && 
                StringUtils.hasText(cosConfig.getRegion()) && 
                StringUtils.hasText(cosConfig.getAppId()) && 
                StringUtils.hasText(cosConfig.getSecretId()) && 
                StringUtils.hasText(cosConfig.getSecretKey());
    }

    @Override
    public boolean createBucket(String bucketName, boolean isPublic) {
        ensureInitialized();
        try {
            // 检查存储桶是否已存在
            boolean exists = bucketExists(bucketName);
            if (exists) {
                log.info("存储桶 {} 已存在，无需创建", bucketName);
                return true;
            }
            
            // 腾讯云COS的存储桶名称格式为：bucketname-appid
            String fullBucketName = getFormalBucketName(bucketName);
            
            // 创建存储桶
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(fullBucketName);
            
            // 设置存储桶访问权限
            if (isPublic) {
                createBucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
                log.info("创建公开访问的存储桶 {}", fullBucketName);
            } else {
                createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
                log.info("创建私有访问的存储桶 {}", fullBucketName);
            }
            
            Bucket bucket = cosClient.createBucket(createBucketRequest);
            
            // 开启版本控制
            BucketVersioningConfiguration configuration = new BucketVersioningConfiguration();
            configuration.setStatus(BucketVersioningConfiguration.ENABLED);
            SetBucketVersioningConfigurationRequest request = 
                    new SetBucketVersioningConfigurationRequest(fullBucketName, configuration);
            cosClient.setBucketVersioningConfiguration(request);
            
            return bucket != null;
        } catch (Exception e) {
            log.error("创建存储桶 {} 失败", bucketName, e);
            return false;
        }
    }

    @Override
    public boolean bucketExists(String bucketName) {
        ensureInitialized();
        try {
            String fullBucketName = getFormalBucketName(bucketName);
            return cosClient.doesBucketExist(fullBucketName);
        } catch (Exception e) {
            log.error("检查存储桶 {} 是否存在时发生错误", bucketName, e);
            return false;
        }
    }

    @Override
    public boolean removeBucket(String bucketName) {
        ensureInitialized();
        try {
            String fullBucketName = getFormalBucketName(bucketName);
            cosClient.deleteBucket(fullBucketName);
            return true;
        } catch (Exception e) {
            log.error("删除存储桶 {} 失败", bucketName, e);
            return false;
        }
    }

    @Override
    public boolean uploadFile(StorageBucket bucket, FileObject fileObject, InputStream inputStream) {
        ensureInitialized();
        
        try {
            String bucketName = getFormalBucketName(bucket.getBucketName());
            String objectName = fileObject.getStoragePath();
            
            // 准备文件元数据
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(fileObject.getStorageSize());
            
            // 从文件扩展名获取内容类型
            String contentType = getContentType(fileObject.getExtension());
            metadata.setContentType(contentType);
            
            // 设置用户自定义元数据
            FileInfo fileInfo = fileObject.getFileInfo();
            if (fileInfo != null) {
                if (StringUtils.hasText(fileInfo.getDescription())) {
                    metadata.addUserMetadata("Description", fileInfo.getDescription());
                }
                if (StringUtils.hasText(fileInfo.getTags())) {
                    metadata.addUserMetadata("Tags", fileInfo.getTags());
                }
            }
            
            // 添加基本元数据
            metadata.addUserMetadata("FileName", fileObject.getFileName());
            metadata.addUserMetadata("FileType", fileObject.getFileType() != null ? 
                    fileObject.getFileType().name() : "OTHER");
            
            // 上传文件
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream, metadata);
            cosClient.putObject(putObjectRequest);
            
            return true;
        } catch (Exception e) {
            log.error("上传文件 {} 到存储桶 {} 失败", fileObject.getFileName(), bucket.getBucketName(), e);
            return false;
        }
    }

    @Override
    public Object getObjectMetadata(StorageBucket bucket, String objectName) {
        ensureInitialized();
        
        try {
            String bucketName = getFormalBucketName(bucket.getBucketName());
            objectName = formatObjectName(objectName);
            
            // 获取对象元数据
            return cosClient.getObjectMetadata(bucketName, objectName);
        } catch (CosClientException e) {
            log.error("获取对象 {} 元数据失败", objectName, e);
            return null;
        }
    }

    @Override
    public InputStream getObject(StorageBucket bucket, String objectName) {
        ensureInitialized();
        
        try {
            String bucketName = getFormalBucketName(bucket.getBucketName());
            objectName = formatObjectName(objectName);
            
            // 获取对象
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, objectName);
            COSObject cosObject = cosClient.getObject(getObjectRequest);
            
            return cosObject.getObjectContent();
        } catch (Exception e) {
            log.error("获取对象 {} 失败", objectName, e);
            return new ByteArrayInputStream(new byte[0]); // 返回空流
        }
    }

    @Override
    public boolean removeObject(StorageBucket bucket, String objectName) {
        ensureInitialized();
        
        try {
            String bucketName = getFormalBucketName(bucket.getBucketName());
            objectName = formatObjectName(objectName);
            
            // 删除对象
            cosClient.deleteObject(bucketName, objectName);
            return true;
        } catch (Exception e) {
            log.error("删除对象 {} 失败", objectName, e);
            return false;
        }
    }

    @Override
    public String generatePresignedUrl(StorageBucket bucket, String objectName, Integer expireSeconds) {
        ensureInitialized();
        
        try {
            String bucketName = getFormalBucketName(bucket.getBucketName());
            objectName = formatObjectName(objectName);
            
            // 处理过期时间，-1表示永不过期，使用默认的1小时
            int expirationSeconds = (expireSeconds == null || expireSeconds < 0) ? 3600 : expireSeconds;
            
            // 设置请求参数
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethodName.GET);
            
            // 设置过期时间
            Date expirationDate = new Date(System.currentTimeMillis() + expirationSeconds * 1000L);
            req.setExpiration(expirationDate);
            
            // 生成预签名URL
            URL url = cosClient.generatePresignedUrl(req);
            return url.toString();
        } catch (Exception e) {
            log.error("生成预签名URL失败: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 上传文本内容到腾讯云COS
     *
     * @param bucket 存储桶
     * @param objectName 对象名称
     * @param content 文本内容
     * @return 是否上传成功
     */
    @Override
    public boolean uploadText(StorageBucket bucket, String objectName, String content) {
        ensureInitialized();
        
        try {
            String bucketName = getFormalBucketName(bucket.getBucketName());
            objectName = formatObjectName(objectName);
            
            // 准备文件元数据
            ObjectMetadata metadata = new ObjectMetadata();
            byte[] contentBytes = content.getBytes("UTF-8");
            metadata.setContentLength(contentBytes.length);
            metadata.setContentType("text/plain; charset=utf-8");
            
            // 添加基本元数据
            metadata.addUserMetadata("ContentType", "Text");
            
            // 上传文本内容
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(contentBytes)) {
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream, metadata);
                cosClient.putObject(putObjectRequest);
            }
            
            log.info("成功上传文本内容到腾讯云COS: bucket={}, object={}", bucketName, objectName);
            return true;
        } catch (Exception e) {
            log.error("上传文本内容到腾讯云COS失败: {}", objectName, e);
            return false;
        }
    }
    
    /**
     * 获取腾讯云COS规范的存储桶名称
     * 腾讯云COS的存储桶命名格式为：bucketname-appid
     */
    private String getFormalBucketName(String bucketName) {
        // 如果已经包含了appId后缀，则直接返回
        if (bucketName.contains("-") && bucketName.endsWith("-" + appId)) {
            return bucketName;
        }
        // 否则添加appId后缀
        return bucketName + "-" + appId;
    }
    
    /**
     * 根据文件扩展名获取内容类型
     */
    private String getContentType(String extension) {
        if (extension == null) {
            return "application/octet-stream";
        }
        
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
            case "ppt":
                return "application/vnd.ms-powerpoint";
            case "pptx":
                return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "txt":
                return "text/plain";
            case "html":
                return "text/html";
            case "xml":
                return "application/xml";
            case "json":
                return "application/json";
            default:
                return "application/octet-stream";
        }
    }
} 
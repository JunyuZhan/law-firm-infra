# 存储服务模块

## 模块说明

存储服务模块(`core-storage`)是核心功能层的重要组成部分，提供统一的文件存储抽象，支持多种存储策略。本模块**仅作为功能封装层**提供文件存储能力，不直接对外暴露REST接口，不负责数据持久化和配置管理。

## 功能特性

### 1. 多种存储策略支持
- 封装多种存储策略接口
- 支持本地文件系统存储
- 支持MinIO对象存储
- 支持阿里云OSS对象存储
- 支持腾讯云COS对象存储

### 2. 文件操作功能
- 文件上传接口
- 文件下载接口
- 文件删除接口
- 生成文件访问URL接口
- 文件元数据管理接口

### 3. 存储桶管理
- 存储桶创建接口
- 存储桶权限设置接口
- 存储桶统计信息接口

### 4. 高级功能
- 分片上传支持
- 断点续传接口
- 文件版本控制接口
- 文件访问控制接口

## 使用方式

### 1. 添加依赖
在业务模块的pom.xml中添加：
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>law-firm-core-storage</artifactId>
</dependency>
```

### 2. 定义数据存储和配置
- **重要**：业务模块负责定义和管理存储服务相关的数据库表
- **重要**：业务模块负责提供存储服务所需的配置

业务模块应在自己的资源目录中定义数据库表：
```sql
-- 在业务模块中定义存储相关表(示例)
CREATE TABLE file_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(512) NOT NULL,
    file_size BIGINT NOT NULL,
    content_type VARCHAR(128),
    storage_type VARCHAR(32) NOT NULL,
    bucket_id BIGINT NOT NULL,
    -- 其他业务字段
);

CREATE TABLE storage_bucket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    bucket_name VARCHAR(64) NOT NULL,
    storage_type VARCHAR(32) NOT NULL,
    -- 其他业务字段
);
```

### 3. 服务注入
使用`@Autowired`和`@Qualifier`注入服务：
```java
@Autowired
@Qualifier("storageFileServiceImpl")
private FileService fileService;

@Autowired
@Qualifier("storageBucketServiceImpl")
private BucketService bucketService;
```

### 4. 服务调用示例
```java
// 上传文件
FileInfo fileInfo = fileService.uploadFile(bucketId, multipartFile);

// 获取文件访问URL
String fileUrl = fileService.generateFileUrl(fileId, 3600);  // 有效期1小时

// 下载文件
InputStream fileStream = fileService.downloadFile(fileId);

// 创建存储桶
StorageBucket bucket = new StorageBucket();
bucket.setBucketName("contracts");
bucket.setStorageType(StorageTypeEnum.MINIO);
Long bucketId = bucketService.createBucket(bucket);
```

### 5. 业务层封装建议
建议在业务层对存储服务进行封装，增加业务逻辑：
```java
@Service
public class DocumentService {
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private BucketService bucketService;
    
    /**
     * 上传合同文档
     */
    public Long uploadContractDocument(MultipartFile file, Long contractId) {
        // 获取或创建合同文档存储桶
        Long bucketId = getBucketForContracts();
        
        // 构建文件元数据
        Map<String, String> metadata = new HashMap<>();
        metadata.put("contractId", contractId.toString());
        metadata.put("docType", "contract");
        
        // 上传文件并记录关联
        FileInfo fileInfo = fileService.uploadFile(bucketId, file, metadata);
        
        // 业务关联处理
        saveContractDocumentRelation(contractId, fileInfo.getId());
        
        return fileInfo.getId();
    }
    
    /**
     * 获取合同文档存储桶
     */
    private Long getBucketForContracts() {
        // 业务逻辑实现
    }
    
    /**
     * 保存合同与文档关系
     */
    private void saveContractDocumentRelation(Long contractId, Long fileId) {
        // 业务逻辑实现
    }
}
```

## 配置说明

本模块作为功能封装层，**不直接管理配置**。业务模块负责提供以下配置：

1. 存储类型配置
2. 存储路径配置
3. 对象存储服务配置
4. 文件访问URL配置

业务模块提供配置示例：
```yaml
# 在业务模块的application.yml中配置
storage:
  default-type: LOCAL
  local:
    base-path: /data/files
    url-prefix: http://example.com/files/
  minio:
    endpoint: http://minio.example.com
    access-key: ${MINIO_ACCESS_KEY}
    secret-key: ${MINIO_SECRET_KEY}
  aliyun-oss:
    endpoint: oss-cn-beijing.aliyuncs.com
    access-key: ${ALIYUN_ACCESS_KEY}
    secret-key: ${ALIYUN_SECRET_KEY}
```

## 注意事项

1. 本模块仅提供功能封装，不负责数据持久化
2. 存储服务相关的数据表应由业务模块定义和管理
3. 存储策略配置应由业务模块提供
4. 文件存储路径配置应由业务模块提供
5. 业务模块应处理好文件与业务对象的关联关系 
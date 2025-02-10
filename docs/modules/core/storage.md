# Core Storage Module

## 1. 模块介绍

`core-storage` 是律所管理系统的核心存储模块，提供统一的文件存储服务。该模块采用策略模式实现，支持多种存储方式，当前默认使用MinIO作为存储介质。

## 2. 核心功能

### 2.1 基础存储功能
- 文件上传
- 文件下载
- 文件删除
- 文件URL获取
- 文件元数据管理

### 2.2 分片上传
- 分片初始化
- 分片上传
- 分片合并
- 分片终止
- 断点续传

### 2.3 文件预览
- 文档预览（PDF、Word、Excel等）
- 图片预览（缩略图生成）
- 视频预览（封面截取）
- 在线预览

### 2.4 异步处理
- 异步上传
- 异步下载
- 异步预览生成
- 任务状态跟踪
- 任务取消

## 3. 技术架构

### 3.1 核心组件
```
com.lawfirm.core.storage/
├── config/                 # 配置类
│   ├── StorageProperties  # 存储配置属性
│   └── MinioConfig       # MinIO配置
├── strategy/              # 存储策略
│   ├── StorageStrategy   # 策略接口
│   └── MinioStrategy    # MinIO实现
├── service/              # 服务层
│   ├── StorageService    # 存储服务
│   ├── ChunkService      # 分片服务
│   ├── PreviewService    # 预览服务
│   └── AsyncService      # 异步服务
└── model/                # 数据模型
    ├── entity/          # 实体类
    └── dto/             # 传输对象
```

### 3.2 数据库设计
```sql
-- 文件存储表
storage_file
  - id            # 主键
  - file_name     # 文件名
  - file_path     # 存储路径
  - file_size     # 文件大小
  - file_type     # 文件类型
  - md5           # 文件MD5
  - status        # 状态

-- 分片上传表
storage_multipart_upload
  - id            # 主键
  - upload_id     # 上传ID
  - file_name     # 文件名
  - total_size    # 总大小
  - chunk_size    # 分片大小
  - total_chunks  # 总分片数
  - status        # 状态

-- 分片记录表
storage_chunk
  - id            # 主键
  - upload_id     # 上传ID
  - chunk_number  # 分片序号
  - chunk_size    # 分片大小
  - etag          # 分片标识
  - status        # 状态

-- 预览记录表
storage_preview
  - id            # 主键
  - file_id       # 文件ID
  - preview_path  # 预览路径
  - preview_type  # 预览类型
  - status        # 状态
```

## 4. 接口设计

### 4.1 存储服务接口
```java
public interface StorageService {
    String uploadFile(MultipartFile file);
    byte[] downloadFile(String filePath);
    void deleteFile(String filePath);
    String getFileUrl(String filePath);
}
```

### 4.2 分片服务接口
```java
public interface ChunkService {
    String initUpload(String fileName);
    String uploadChunk(String uploadId, String fileName, int chunkNumber, MultipartFile chunk);
    String completeUpload(String uploadId, String fileName);
    void abortUpload(String uploadId);
}
```

### 4.3 预览服务接口
```java
public interface PreviewService {
    String generatePreview(MultipartFile file);
    String getPreviewUrl(String filePath);
    void deletePreview(String filePath);
    boolean isPreviewable(String fileName);
}
```

### 4.4 异步服务接口
```java
public interface AsyncStorageService {
    CompletableFuture<String> asyncUpload(MultipartFile file);
    CompletableFuture<byte[]> asyncDownload(String filePath);
    CompletableFuture<Void> asyncDelete(String filePath);
    boolean getUploadStatus(String taskId);
    boolean cancelUpload(String taskId);
}
```

## 5. 配置说明

### 5.1 MinIO配置
```yaml
storage:
  endpoint: http://localhost:9000
  access-key: minioadmin
  secret-key: minioadmin
  bucket-name: law-firm
  base-path: storage
  preview-path: preview
  chunk-size: 5242880  # 5MB
```

### 5.2 异步任务配置
```yaml
storage:
  async:
    core-pool-size: 5
    max-pool-size: 10
    queue-capacity: 25
    keep-alive-seconds: 60
```

## 6. 使用示例

### 6.1 文件上传
```java
@Autowired
private StorageService storageService;

public void upload() {
    MultipartFile file = ...;
    String filePath = storageService.uploadFile(file);
}
```

### 6.2 分片上传
```java
@Autowired
private ChunkService chunkService;

public void uploadLargeFile() {
    String uploadId = chunkService.initUpload("large-file.zip");
    for (int i = 1; i <= totalChunks; i++) {
        MultipartFile chunk = ...;
        chunkService.uploadChunk(uploadId, "large-file.zip", i, chunk);
    }
    String filePath = chunkService.completeUpload(uploadId, "large-file.zip");
}
```

### 6.3 异步上传
```java
@Autowired
private AsyncStorageService asyncStorageService;

public void asyncUpload() {
    MultipartFile file = ...;
    CompletableFuture<String> future = asyncStorageService.asyncUpload(file);
    future.thenAccept(filePath -> {
        // 处理上传完成后的逻辑
    });
}
```

## 7. 测试说明

### 7.1 单元测试
- `StorageServiceTest`: 存储服务测试
- `ChunkServiceTest`: 分片服务测试
- `PreviewServiceTest`: 预览服务测试
- `AsyncServiceTest`: 异步服务测试

### 7.2 集成测试
使用TestContainers进行集成测试：
```java
@Testcontainers
class StorageIntegrationTest {
    @Container
    static MinioContainer minioContainer = new MinioContainer();
    
    @Test
    void testFullStorageFlow() {
        // 完整存储流程测试
    }
}
```

## 8. 性能优化

### 8.1 上传优化
- 使用分片上传提高大文件处理能力
- 异步处理减少响应时间
- 文件秒传（MD5判断）

### 8.2 下载优化
- 支持断点续传
- 异步下载大文件
- URL直接下载

### 8.3 预览优化
- 异步生成预览文件
- 缓存预览结果
- 支持在线预览

## 9. 扩展性设计

### 9.1 存储策略扩展
实现 `StorageStrategy` 接口：
```java
public interface StorageStrategy {
    String uploadFile(String objectName, MultipartFile file);
    byte[] downloadFile(String objectName);
    void deleteFile(String objectName);
    String getFileUrl(String objectName);
}
```

### 9.2 预览处理扩展
实现 `PreviewProcessor` 接口：
```java
public interface PreviewProcessor {
    boolean support(String fileType);
    String process(MultipartFile file);
}
```

## 10. 最佳实践

### 10.1 文件命名
- 使用UUID作为文件名
- 保留原始文件扩展名
- 按日期组织目录结构

### 10.2 安全考虑
- 文件类型校验
- 文件大小限制
- 防盗链措施
- 访问权限控制

### 10.3 异常处理
- 存储空间不足
- 网络超时
- 并发冲突
- 文件损坏

## 11. 监控告警

### 11.1 监控指标
- 存储空间使用率
- 上传/下载速率
- 请求成功率
- 响应时间

### 11.2 告警规则
- 存储空间告警
- 错误率告警
- 响应时间告警
- 并发数告警 
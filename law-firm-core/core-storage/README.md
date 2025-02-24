# 存储模块 (Core Storage)

## 模块说明
存储模块是律师事务所管理系统的核心存储模块，提供统一的文件存储服务。该模块支持多种存储方式，包括本地存储、对象存储(OSS)、分布式文件系统等，并提供统一的接口进行文件操作。

## 核心功能

### 1. 文件上传
- 单文件上传
- 批量文件上传
- 分片上传
- 断点续传
- 秒传功能

### 2. 文件下载
- 单文件下载
- 批量文件下载
- 断点续传
- 限速下载
- 防盗链处理

### 3. 文件管理
- 文件元数据管理
- 文件目录管理
- 文件权限控制
- 文件版本控制
- 文件生命周期

### 4. 存储策略
- 多存储源支持
- 存储策略配置
- 存储容量管理
- 存储监控告警
- 存储备份恢复

## 核心组件

### 1. 存储服务
- StorageService：存储服务接口
- LocalStorageService：本地存储实现
- OssStorageService：对象存储实现
- FastDFSStorageService：分布式存储实现

### 2. 文件操作
- FileOperator：文件操作接口
- FileUploader：文件上传器
- FileDownloader：文件下载器
- FileManager：文件管理器

### 3. 存储策略
- StorageStrategy：存储策略接口
- StorageRouter：存储路由器
- StorageSelector：存储选择器
- StorageLoadBalancer：存储负载均衡器

### 4. 监控管理
- StorageMonitor：存储监控器
- StorageMetrics：存储指标收集
- StorageAlarm：存储告警
- StorageLogger：存储日志记录

## 使用示例

### 1. 文件上传
```java
@Autowired
private StorageService storageService;

public void uploadFile(MultipartFile file) {
    StorageConfig config = new StorageConfig()
        .setPath("/documents/2024/")
        .setAllowedTypes("pdf,doc,docx")
        .setMaxSize(10 * 1024 * 1024L);
    
    StorageResult result = storageService.upload(file, config);
    log.info("文件上传成功: {}", result.getUrl());
}
```

### 2. 文件下载
```java
@Autowired
private StorageService storageService;

public void downloadFile(String fileId, HttpServletResponse response) {
    DownloadConfig config = new DownloadConfig()
        .setSpeedLimit(1024 * 1024L) // 1MB/s
        .setCheckPermission(true);
    
    storageService.download(fileId, response, config);
}
```

### 3. 文件管理
```java
@Autowired
private FileManager fileManager;

public void manageFile(String fileId) {
    // 获取文件信息
    FileInfo fileInfo = fileManager.getFileInfo(fileId);
    
    // 更新文件属性
    fileManager.updateAttributes(fileId, new FileAttributes()
        .setExpireTime(LocalDateTime.now().plusDays(7))
        .setAccessControl(AccessControl.PRIVATE));
}
```

## 配置说明

### 1. 存储配置
```yaml
storage:
  # 默认存储配置
  default:
    type: oss
    
  # 本地存储配置
  local:
    base-path: /data/files
    temp-path: /data/temp
    
  # OSS存储配置
  oss:
    endpoint: oss-cn-beijing.aliyuncs.com
    bucket: law-firm-docs
    access-key: ${OSS_ACCESS_KEY}
    secret-key: ${OSS_SECRET_KEY}
    
  # FastDFS配置
  fastdfs:
    tracker-servers: 192.168.1.100:22122
    connect-timeout: 5000
    network-timeout: 30000
```

### 2. 上传配置
```yaml
upload:
  # 上传限制
  limit:
    max-file-size: 100MB
    max-request-size: 100MB
    
  # 文件类型
  content-types:
    - application/pdf
    - application/msword
    - application/vnd.openxmlformats-officedocument.wordprocessingml.document
```

## 注意事项
1. 文件安全性保护
2. 存储空间管理
3. 文件备份策略
4. 性能优化考虑
5. 监控告警机制 
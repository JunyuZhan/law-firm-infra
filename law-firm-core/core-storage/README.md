# 存储模块 (Core Storage)

## 模块说明
存储模块是律师事务所管理系统的核心存储模块，为系统提供统一的文件存储服务。该模块基于 `storage-model` 数据模型层，实现了对文件与存储桶的管理，支持多种存储方式，包括本地存储、MinIO对象存储、阿里云OSS、腾讯云COS等，并提供统一的接口进行文件操作。

## 模块依赖关系

本模块依赖以下核心模块：

1. `storage-model`: 提供文件和存储桶的数据模型、DTO、VO和服务接口定义
2. `common-data`: 提供存储配置、数据访问支持和序列化工具
3. `common-cache`: 提供缓存支持、防重提交和限流机制
4. `common-util`: 提供通用工具类，包括文件操作、加密、编解码等功能
5. `common-core`: 提供异常处理、通用API响应等基础功能
6. `common-web`: 提供Web层面的支持，如请求处理、响应包装等
7. `common-security`: 提供安全、权限和审计相关功能
8. `common-log`: 提供日志记录和操作审计功能

### 与common-data的集成
- 复用 `StorageConfig` 中的 MinIO、阿里云OSS 等客户端配置
- 利用 JPA、MyBatis-Plus 等数据访问配置实现数据持久化
- 使用序列化工具处理文件元数据

### 与common-cache的集成
- ✅ 使用 `@SimpleCache` 注解缓存频繁访问的文件数据和URL
- ✅ 使用 `@RepeatSubmit` 注解防止重复上传文件
- ✅ 使用 `@RateLimiter` 注解实现上传下载限流

### 与common-util的集成
- 使用 `FileUtils` 处理文件操作，如创建目录、获取文件类型、文件复制等
- 使用 `CryptoUtils` 实现文件加密和解密功能
- 使用 `CodecUtils` 处理Base64编解码等操作
- 使用 `ImageUtils` 实现图片压缩、缩放、水印等处理
- 使用 `CompressUtils` 实现文件压缩和解压功能

### 与common-core的集成
- 使用 `CommonResult<T>` 作为统一的API响应体，保持接口一致性
- 复用 `BaseException`、`FrameworkException` 等异常类进行异常处理
- 使用异常常量定义，如 `ResultCode` 进行错误码统一管理

### 与common-web的集成
- 复用 `GlobalExceptionHandler` 进行全局异常处理
- 利用请求和响应工具类处理HTTP交互
- 整合API文档配置，提供Swagger/SpringDoc接口文档

### 与common-security的集成
- ✅ 使用 `@RequiresPermissions` 注解实现方法级权限控制
  ```java
  @RequiresPermissions("storage:file:upload")
  public FileVO uploadFile(...) { ... }
  
  @RequiresPermissions("storage:file:download")
  public byte[] downloadFile(...) { ... }
  ```
- 使用 `SecurityAudit` 接口记录文件访问和操作审计事件
  ```java
  securityAudit.logOperationEvent(
      SecurityUtils.getCurrentUser(),
      "FILE_UPLOAD",
      fileObject.getId().toString()
  );
  ```
- 使用安全上下文获取当前用户信息，实现细粒度权限控制

### 与common-log的集成
- ✅ 使用 `FileOperationLogAspect` 切面自动记录文件操作的详细日志
- 利用MDC机制在日志中添加上下文信息
- 对敏感操作进行额外的日志记录，如文件删除、权限变更等

## 模块架构

### 目录结构
```
core-storage/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── lawfirm/
│       │           └── core/
│       │               └── storage/
│       │                   ├── aspect/                  # 切面类 ✅
│       │                   │   └── FileOperationLogAspect.java   # 文件操作日志切面 ✅
│       │                   ├── config/                  # 配置类 ✅
│       │                   │   ├── StorageProperties.java       # 存储配置属性 ✅
│       │                   │   └── StorageConfiguration.java    # 存储配置类 ✅
│       │                   ├── controller/              # 控制器 ✅
│       │                   │   ├── FileController.java          # 文件控制器 ✅
│       │                   │   └── BucketController.java        # 存储桶控制器 ✅
│       │                   ├── strategy/                # 存储策略 ✅
│       │                   │   ├── StorageContext.java          # 存储上下文 ✅
│       │                   │   ├── StorageStrategy.java         # 存储策略接口 ✅
│       │                   │   ├── AbstractStorageStrategy.java # 抽象存储策略 ✅
│       │                   │   └── impl/                        # 策略实现 ✅
│       │                   │       ├── LocalStorageStrategy.java    # 本地存储策略 ✅
│       │                   │       ├── MinioStorageStrategy.java    # MinIO存储策略 ✅
│       │                   │       ├── AliyunOssStorageStrategy.java # 阿里云OSS策略 ✅
│       │                   │       └── TencentCosStorageStrategy.java # 腾讯云COS策略 ✅
│       │                   ├── service/                 # 服务实现 ✅
│       │                   │   ├── impl/                        # 服务实现 ✅
│       │                   │   │   ├── FileServiceImpl.java        # 文件服务实现 ✅
│       │                   │   │   └── BucketServiceImpl.java      # 存储桶服务实现 ✅
│       │                   │   └── support/                    # 支持类 ✅
│       │                   │       ├── FileOperator.java          # 文件操作类 ✅
│       │                   │       ├── FileUploader.java          # 文件上传器 ✅
│       │                   │       ├── FileDownloader.java        # 文件下载器 ✅
│       │                   │       └── ChunkedUploader.java       # 分片上传器 ✅
│       │                   ├── task/                    # 定时任务 ✅
│       │                   │   └── ExpiredChunkCleanTask.java    # 过期分片清理任务 ✅
│       │                   └── util/                    # 工具类 ✅
│       │                       ├── FileTypeUtils.java           # 文件类型工具 ✅
│       │                       ├── FilePathUtils.java           # 文件路径工具 ✅
│       │                       └── FileContentUtils.java        # 文件内容工具 ✅
│       └── resources/
│           └── application-storage.yml                  # 存储配置文件 ✅
└── pom.xml                                              # 项目依赖 ✅
```

## 核心功能

### 1. 文件管理
- ✅ **文件上传**：支持单文件上传、批量上传、分片上传、Base64上传
- ✅ **文件下载**：支持单文件下载、批量下载、断点续传
- ✅ **文件预览**：支持图片、文档、音视频等预览
- ✅ **文件操作**：支持复制、移动、删除等基本操作
- ✅ **元数据管理**：支持添加、更新和查询文件的元数据

### 2. 存储桶管理
- ✅ **桶管理**：创建、配置、查询、删除存储桶
- ✅ **存储策略**：支持不同存储桶使用不同的存储策略
- ✅ **密钥管理**：安全管理各个存储源的访问密钥
- ✅ **统计监控**：存储桶的容量和使用情况监控

### 3. 存储策略
- ✅ **多存储源**：支持本地存储、MinIO、阿里云OSS、腾讯云COS等
- ✅ **策略动态切换**：根据配置动态选择存储策略
- ✅ **扩展性设计**：易于扩展新的存储源支持

### 4. 性能优化
- ✅ **分片上传**：支持大文件分片上传
- ✅ **断点续传**：支持上传、下载断点续传
- ✅ **文件缓存**：频繁访问的文件进行缓存（集成common-cache）
- ✅ **异步处理**：上传、转换等耗时操作异步处理

### 5. 安全特性
- ✅ **访问控制**：基于角色的文件访问控制
- ✅ **防盗链机制**：URL签名和有效期控制
- ✅ **文件加密**：支持存储加密
- ✅ **水印功能**：图片、文档水印
- ✅ **防重提交**：防止文件重复上传（集成common-cache）
- ✅ **限流控制**：限制上传下载频率（集成common-cache）

## 核心组件

### 1. 存储服务
- ✅ **FileServiceImpl**: 实现 FileService 接口，提供文件操作服务
- ✅ **BucketServiceImpl**: 实现 BucketService 接口，提供存储桶管理服务

### 2. 存储策略
- ✅ **StorageStrategy**: 存储策略接口，定义存储操作方法
- ✅ **AbstractStorageStrategy**: 抽象存储策略，提供通用实现
- ✅ **具体策略实现**:
  - LocalStorageStrategy: 本地文件系统存储实现
  - MinioStorageStrategy: MinIO对象存储实现（集成common-data的MinioClient）
  - AliyunOssStorageStrategy: 阿里云OSS实现（集成common-data的OSSClient）
  - TencentCosStorageStrategy: 腾讯云COS实现

### 3. 文件操作工具
- ✅ **FileOperator**: 文件基本操作，提供统一的文件操作API
- ✅ **FileUploader**: 文件上传处理，支持多种上传方式
- ✅ **FileDownloader**: 文件下载处理，支持断点续传
- ✅ **ChunkedUploader**: 分片上传处理，支持大文件上传

### 4. 切面和定时任务
- ✅ **FileOperationLogAspect**: 文件操作日志切面，记录文件操作日志
- ✅ **ExpiredChunkCleanTask**: 过期分片文件清理定时任务，定期清理临时目录

### 5. 工具类集成
- ✅ **FileTypeUtils**: 文件类型识别和处理工具
- ✅ **FilePathUtils**: 文件路径处理工具
- ✅ **FileContentUtils**: 文件内容处理工具
- 复用common-util中的其他工具进行文件操作

## 配置说明

### 1. 全局存储配置
```yaml
storage:
  # 默认存储类型
  default-type: LOCAL
  # 默认存储桶
  default-bucket-id: 1
  
  # 路径配置
  path:
    # 本地存储根路径
    base-dir: /data/files
    # 临时目录
    temp-dir: /data/temp
    # URL前缀
    url-prefix: http://localhost:8080/api/files
    
  # 上传配置
  upload:
    # 最大文件大小 (100MB)
    max-size: 104857600
    # 分片大小 (5MB)
    chunk-size: 5242880
    # 允许的文件类型 (全部允许使用*)
    allowed-types: '*'
    # 禁止的文件类型
    denied-types: exe,bat,sh,dll
```

### 2. 存储源配置
```yaml
storage:
  # 本地存储配置
  local:
    enabled: true
    base-path: /data/files
    url-prefix: http://localhost:8080/api/files
    
  # MinIO配置
  minio:
    enabled: true
    endpoint: http://minio.example.com
    port: 9000
    access-key: ${MINIO_ACCESS_KEY}
    secret-key: ${MINIO_SECRET_KEY}
    use-ssl: false
    
  # 阿里云OSS配置
  aliyun-oss:
    enabled: false
    endpoint: oss-cn-beijing.aliyuncs.com
    access-key: ${ALIYUN_ACCESS_KEY}
    secret-key: ${ALIYUN_SECRET_KEY}
    
  # 腾讯云COS配置
  tencent-cos:
    enabled: false
    region: ap-beijing
    app-id: ${TENCENT_APP_ID}
    secret-id: ${TENCENT_SECRET_ID}
    secret-key: ${TENCENT_SECRET_KEY}
```

## 使用示例

### 1. 文件上传
```java
@Autowired
private FileService fileService;

// 上传文件
// 集成common-cache的防重提交和限流功能
@RepeatSubmit(interval = 5000)
@RateLimiter(limit = 10, timeout = 60)
public FileVO uploadFile(MultipartFile file, String description, String tags) {
    FileUploadDTO uploadDTO = new FileUploadDTO()
        .setFileName(file.getOriginalFilename())
        .setBucketId(1L)  // 默认存储桶
        .setDescription(description)
        .setTags(tags)
        .setContent(Base64.getEncoder().encodeToString(file.getBytes()));
    
    return fileService.upload(uploadDTO);
}
```

### 2. 文件下载
```java
@Autowired
private FileService fileService;

// 下载文件
@RateLimiter(limit = 30, timeout = 60)
public void downloadFile(Long fileId, HttpServletResponse response) {
    byte[] fileData = fileService.download(fileId);
    
    // 设置响应头
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment; filename=file.dat");
    
    // 写入响应
    response.getOutputStream().write(fileData);
}
```

### 3. 存储桶管理
```java
@Autowired
private BucketService bucketService;

// 创建存储桶
@RepeatSubmit(interval = 5000)
public BucketVO createBucket(String bucketName, String description, String storageType) {
    BucketCreateDTO createDTO = new BucketCreateDTO()
        .setBucketName(bucketName)
        .setDescription(description)
        .setStorageType(storageType);
    
    return bucketService.create(createDTO);
}
```

### 4. 使用缓存示例
```java
@Autowired
private FileService fileService;

// 获取文件访问URL，应用缓存减少重复计算
@SimpleCache(timeout = 300, timeUnit = TimeUnit.SECONDS)
public String getFileAccessUrl(Long fileId) {
    return fileService.getAccessUrl(fileId);
}
```

### 5. 使用通用工具示例
```java
@Autowired
private FileService fileService;

// 添加水印
public void addWatermarkToImage(Long fileId, String watermarkText) throws IOException {
    // 获取文件内容
    byte[] fileData = fileService.download(fileId);
    
    // 创建临时文件
    File tempSource = File.createTempFile("source_", ".tmp");
    FileUtils.writeFile(tempSource.getAbsolutePath(), new String(fileData));
    
    File tempDest = File.createTempFile("watermark_", ".tmp");
    
    // 添加水印
    ImageUtils.addWatermark(
        tempSource, 
        tempDest, 
        watermarkText, 
        Color.WHITE, 
        0.5f
    );
    
    // 读取处理后的文件并重新上传
    FileUploadDTO uploadDTO = new FileUploadDTO()
        .setFileName("watermarked_" + fileId)
        .setBucketId(1L)
        .setContent(CodecUtils.encodeBase64(Files.readAllBytes(tempDest.toPath())));
    
    fileService.upload(uploadDTO);
    
    // 清理临时文件
    tempSource.delete();
    tempDest.delete();
}

// 文件加密与解密
public String encryptAndStoreFile(Long fileId, String secretKey) throws IOException {
    // 获取文件内容
    byte[] fileData = fileService.download(fileId);
    
    // 加密文件内容
    String encryptedContent = CryptoUtils.encryptAES(
        CodecUtils.encodeBase64(fileData), 
        secretKey
    );
    
    // 存储加密文件
    String encryptedFilePath = "/secure/" + UUID.randomUUID().toString();
    FileUtils.writeFile(encryptedFilePath, encryptedContent);
    
    return encryptedFilePath;
}

// 解压文件
public void unzipFile(Long fileId, String destPath) throws IOException {
    // 获取文件内容
    byte[] fileData = fileService.download(fileId);
    
    // 创建临时文件
    File tempFile = File.createTempFile("archive_", ".zip");
    FileUtils.writeFile(tempFile.getAbsolutePath(), new String(fileData));
    
    // 解压文件
    CompressUtils.unzip(tempFile.getAbsolutePath(), destPath);
    
    // 清理临时文件
    tempFile.delete();
}
```

### 6. 异常处理与API响应示例
```java
@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传文件，使用统一响应体
     */
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('storage:file:upload')")
    public CommonResult<FileVO> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileVO result = fileService.upload(file);
            return CommonResult.success(result);
        } catch (IOException e) {
            // 使用common-core的异常和结果码
            throw new BaseException(ResultCode.FILE_UPLOAD_ERROR, "文件上传失败", e);
        }
    }
    
    /**
     * 删除文件，使用统一异常处理
     */
    @DeleteMapping("/{fileId}")
    @PreAuthorize("hasAuthority('storage:file:delete')")
    public CommonResult<Boolean> deleteFile(@PathVariable Long fileId) {
        boolean result = fileService.delete(fileId);
        return CommonResult.success(result);
    }
    
    /**
     * 全局异常处理会自动处理未捕获的异常
     * 例如，当文件不存在时抛出的业务异常将被GlobalExceptionHandler处理
     */
    @ExceptionHandler(FileNotFoundException.class)
    public CommonResult<String> handleFileNotFoundException(FileNotFoundException e) {
        return CommonResult.failed(ResultCode.FILE_NOT_FOUND, e.getMessage());
    }
}
```

### 7. 安全与审计示例
```java
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileObjectRepository fileObjectRepository;
    private final SecurityAudit securityAudit;
    private final StorageContext storageContext;
    
    @Override
    @Transactional
    public FileVO upload(MultipartFile file) {
        try {
            // 获取存储策略
            StorageStrategy strategy = storageContext.getStrategy();
            
            // 上传文件
            String storedPath = strategy.store(file);
            
            // 保存元数据
            FileObject fileObject = new FileObject();
            // ... 设置属性
            fileObjectRepository.save(fileObject);
            
            // 记录审计事件
            securityAudit.logOperationEvent(
                SecurityUtils.getCurrentUser(),
                "FILE_UPLOAD",
                fileObject.getId().toString()
            );
            
            return convert(fileObject);
        } catch (IOException e) {
            throw new BaseException("文件上传失败", e);
        }
    }
    
    @Override
    public boolean delete(Long fileId) {
        // 权限检查：验证当前用户是否有权限删除此文件
        FileObject fileObject = fileObjectRepository.findById(fileId)
            .orElseThrow(() -> new FileNotFoundException("文件不存在: " + fileId));
            
        // 使用SecurityUtils检查权限
        if (!SecurityUtils.isAdmin() && !isFileOwner(fileObject)) {
            securityAudit.logAuthorizationEvent(
                SecurityUtils.getCurrentUser(),
                "FILE_DELETE", 
                false
            );
            throw new UnauthorizedException("无权删除此文件");
        }
        
        // 删除文件
        StorageStrategy strategy = storageContext.getStrategy(fileObject.getBucketType());
        boolean result = strategy.delete(fileObject.getStoragePath());
        
        if (result) {
            fileObjectRepository.delete(fileObject);
            
            // 记录审计事件
            securityAudit.logOperationEvent(
                SecurityUtils.getCurrentUser(),
                "FILE_DELETE",
                fileId.toString()
            );
        }
        
        return result;
    }
    
    private boolean isFileOwner(FileObject fileObject) {
        String currentUser = SecurityUtils.getCurrentUsername();
        return currentUser.equals(fileObject.getCreateBy());
    }
}
```

### 8. 日志记录示例
```java
@Aspect
@Component
@RequiredArgsConstructor
public class FileOperationLogAspect {

    private final LogUtils logUtils;

    /**
     * 拦截所有文件删除操作
     */
    @Around("execution(* com.lawfirm.core.storage.service.*.*.delete*(..))")
    public Object logFileDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        Long fileId = args.length > 0 ? (Long) args[0] : null;
        
        // 操作前记录
        MDC.put("operation", "FILE_DELETE");
        MDC.put("fileId", String.valueOf(fileId));
        logUtils.info("开始删除文件, ID: {}", fileId);
        
        try {
            // 执行原方法
            Object result = joinPoint.proceed();
            
            // 操作成功记录
            logUtils.info("文件删除成功, ID: {}, 结果: {}", fileId, result);
            return result;
        } catch (Exception e) {
            // 操作失败记录
            logUtils.error("文件删除失败, ID: {}, 原因: {}", fileId, e.getMessage(), e);
            throw e;
        } finally {
            MDC.remove("operation");
            MDC.remove("fileId");
        }
    }
}
```

## 开发总结

本模块按照设计需求完成了以下内容：

### 1. 工具类实现

1. **文件类型工具类 (FileTypeUtils)**：
   - 使用Apache Tika实现文件类型检测
   - 支持根据MIME类型获取文件扩展名
   - 提供判断图片、视频、音频、文档等文件类型的方法

2. **文件路径工具类 (FilePathUtils)**：
   - 提供生成存储路径和唯一文件名的方法
   - 实现路径规范化和路径连接功能
   - 提供目录创建、获取父目录等功能

3. **文件内容工具类 (FileContentUtils)**：
   - 实现Base64与文件互转功能
   - 提供各种数据流相互转换的方法
   - 支持文件内容读写功能

### 2. 存储策略实现

1. **MinIO存储策略 (MinIOStorageStrategy)**：
   - 完整实现MinIO对象存储接口
   - 支持创建、删除存储桶
   - 实现文件上传、下载、删除等功能
   - 支持生成预签名URL访问

2. **本地存储策略 (LocalStorageStrategy)**：
   - 实现本地文件系统存储
   - 对接文件系统操作

3. **阿里云和腾讯云策略**：
   - 已完成阿里云OSS和腾讯云COS的支持

### 3. 核心服务支持

1. **分片上传处理器 (ChunkedUploader)**：
   - 实现大文件分片上传
   - 提供分片合并功能
   - 支持清理过期分片

2. **文件上传器和下载器**：
   - 实现FileUploader和FileDownloader功能
   - 支持各种类型的文件处理

### 4. 核心配置

1. **存储配置 (StorageProperties)**：
   - 定义存储类型、路径、大小等配置
   - 支持多种存储方式配置

2. **应用配置文件 (application-storage.yml)**：
   - 完整的存储配置
   - 对接缓存、上传配置等

### 特性实现

1. **多存储源支持**：通过策略模式实现多种存储方式的无缝切换
2. **分片上传**：支持大文件分片上传与合并
3. **文件类型检测**：自动识别文件类型
4. **安全特性**：防盗链机制与访问控制

### 技术亮点

1. **策略模式**：通过策略模式实现存储策略的动态切换和扩展
2. **流式处理**：高效的数据流处理，减少内存占用
3. **缓存设计**：合理使用缓存提高访问速度
4. **异常处理**：完善的异常捕获和日志记录

### 后续优化方向

1. **性能优化**：进一步优化大文件处理性能
2. **安全加强**：加强文件访问控制和防盗链机制
3. **监控告警**：完善存储监控和告警机制
4. **容灾备份**：实现存储备份和容灾方案
5. **CDN集成**：整合CDN实现资源加速 
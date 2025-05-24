# 律所档案管理模块

## 1. 模块定位

档案管理模块是连接案件管理与外部档案系统的中间集成层，主要职责：
- 接收并存储已归档案件数据
- 为外部档案系统提供数据同步接口
- 管理归档状态与可见性控制

## 1.1 与其他模块的关系

- **案件管理模块**：
  - 档案管理模块通过API接收案件管理模块推送的结案归档数据，实现案件归档的落地存储。
  - 归档后，案件管理模块中的案件状态会被更新为"已归档"，并限制后续操作。

- **文件管理模块**：
  - 档案管理模块通过文件管理模块实现归档文件的上传、下载、存储与关联。
  - 归档文件的元数据与实际文件分离，便于统一管理和权限控制。

- **外部档案系统**：
  - 档案管理模块为外部档案系统提供数据同步接口，支持增量同步、同步确认等功能。
  - 支持多系统对接和同步日志记录。

- **权限与认证模块**：
  - 归档相关接口会集成统一的权限认证、API密钥校验、IP白名单等安全机制。

- **基础数据/组织/人员模块**（如有）：
  - 归档记录中的部门、经办人等信息会与组织、人员等基础数据模块关联。

## 2. 业务流程

### 2.1 归档流转流程

[案件管理模块] → [档案管理模块] → [外部档案系统]
    归档触发       数据转存           数据同步

1. **归档触发**：
   - 用户在案件管理模块点击"结案归档"按钮
   - 系统调用档案模块API，传输案件数据
   - 案件状态更新为"已归档"，设置为不可操作/不可见

2. **档案存储**：
   - 档案模块接收案件数据并转换为档案记录
   - 生成档案编号、归档时间等元数据
   - 存储至档案数据库表

3. **数据同步**：
   - 外部档案系统通过API拉取档案数据
   - 档案模块提供增量同步接口
   - 记录同步状态与时间戳

## 3. 数据库设计

### 3.1 主要表结构

```sql
-- 档案主表
CREATE TABLE archive_main (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '档案主键',
  archive_no VARCHAR(32) NOT NULL COMMENT '档案编号',
  source_type VARCHAR(32) NOT NULL COMMENT '来源类型(CASE/CONTRACT/DOCUMENT)',
  source_id BIGINT NOT NULL COMMENT '来源ID',
  title VARCHAR(255) NOT NULL COMMENT '档案标题',
  archive_time DATETIME NOT NULL COMMENT '归档时间',
  department_id BIGINT COMMENT '归档部门ID',
  handler_id BIGINT COMMENT '经办人ID',
  handler_name VARCHAR(64) COMMENT '经办人姓名',
  status TINYINT DEFAULT 1 COMMENT '状态(1:已归档 2:已同步 3:已销毁)',
  sync_time DATETIME COMMENT '同步时间',
  is_synced TINYINT DEFAULT 0 COMMENT '是否已同步(0:未同步 1:已同步)',
  archive_data JSON COMMENT '归档数据JSON',
  keywords VARCHAR(512) COMMENT '关键词',
  remark VARCHAR(512) COMMENT '备注',
  created_time DATETIME NOT NULL COMMENT '创建时间',
  updated_time DATETIME COMMENT '更新时间',
  
  KEY idx_source (source_type, source_id),
  KEY idx_archive_no (archive_no),
  KEY idx_sync (is_synced, sync_time)
);

-- 档案文件关联表
CREATE TABLE archive_file (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  archive_id BIGINT NOT NULL COMMENT '档案ID',
  file_id BIGINT NOT NULL COMMENT '文件ID',
  file_name VARCHAR(255) NOT NULL COMMENT '文件名称',
  file_path VARCHAR(512) NOT NULL COMMENT '文件路径',
  file_size BIGINT COMMENT '文件大小(字节)',
  file_type VARCHAR(32) COMMENT '文件类型',
  created_time DATETIME NOT NULL COMMENT '创建时间',
  
  KEY idx_archive_id (archive_id)
);

-- 档案同步日志表
CREATE TABLE archive_sync_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
  batch_no VARCHAR(64) NOT NULL COMMENT '同步批次号',
  sync_time DATETIME NOT NULL COMMENT '同步时间',
  sync_user VARCHAR(64) COMMENT '同步用户',
  target_system VARCHAR(64) NOT NULL COMMENT '目标系统',
  archive_count INT DEFAULT 0 COMMENT '同步档案数量',
  status TINYINT NOT NULL COMMENT '状态(1:成功 0:失败)',
  error_msg VARCHAR(512) COMMENT '错误消息',
  created_time DATETIME NOT NULL COMMENT '创建时间'
);
```

## 4. 模块结构

```
law-firm-archive/
├── controller/         # 控制器层
│   ├── ArchiveController.java       # 档案管理控制器
│   ├── ArchiveSyncController.java   # 档案同步控制器
│   └── ArchiveFileController.java   # 档案文件控制器（文件上传/下载/关联）
├── service/            # 服务层
│   ├── ArchiveService.java          # 档案服务接口
│   ├── ArchiveSyncService.java      # 同步服务接口
│   └── impl/
│       ├── ArchiveServiceImpl.java      # 档案服务实现
│       └── ArchiveSyncServiceImpl.java # 同步服务实现
├── config/             # 配置类
│   └── ArchiveConfig.java           # 档案配置类
├── constant/           # 常量类
│   └── ArchiveBusinessConstants.java    # 业务常量
```

## 5. 核心功能设计

### 5.1 案件归档接收

```java
@Service
@Slf4j
public class ArchiveServiceImpl implements ArchiveService {
    
    @Autowired
    private ArchiveMainMapper archiveMainMapper;
    
    @Autowired
    private ArchiveFileMapper archiveFileMapper;
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ArchiveResult archiveCase(CaseArchiveDTO archiveDTO) {
        log.info("接收案件归档请求，caseId={}", archiveDTO.getCaseId());
        
        // 1. 检查是否已归档
        ArchiveMain existArchive = archiveMainMapper.findBySourceId(
            SourceTypeEnum.CASE.getValue(), archiveDTO.getCaseId());
        if (existArchive != null) {
            return ArchiveResult.fail("该案件已归档");
        }
        
        // 2. 生成档案记录
        ArchiveMain archive = new ArchiveMain();
        archive.setArchiveNo(generateArchiveNo("C"));
        archive.setSourceType(SourceTypeEnum.CASE.getValue());
        archive.setSourceId(archiveDTO.getCaseId());
        archive.setTitle(archiveDTO.getCaseTitle());
        archive.setArchiveTime(LocalDateTime.now());
        archive.setDepartmentId(archiveDTO.getDepartmentId());
        archive.setHandlerId(archiveDTO.getHandlerId());
        archive.setHandlerName(archiveDTO.getHandlerName());
        archive.setStatus(ArchiveStatusEnum.ARCHIVED.getValue());
        archive.setIsSynced(false);
        archive.setArchiveData(JSON.toJSONString(archiveDTO));
        archive.setKeywords(extractKeywords(archiveDTO));
        archive.setCreatedTime(LocalDateTime.now());
        
        // 3. 保存档案主记录
        archiveMainMapper.insert(archive);
        
        // 4. 保存关联文件
        saveArchiveFiles(archive.getId(), archiveDTO.getFileList());
        
        log.info("案件归档成功，caseId={}，档案号={}", 
                archiveDTO.getCaseId(), archive.getArchiveNo());
                
        return ArchiveResult.success(archive.getArchiveNo());
    }
    
    // 其他实现方法...
}
```

### 5.2 对外数据同步接口

```java
@RestController
@RequestMapping("/api/archive/sync")
@Slf4j
public class ArchiveSyncController {

    @Autowired
    private ArchiveService archiveService;
    
    @Autowired
    private ArchiveSyncLogService syncLogService;
    
    /**
     * 获取增量同步数据
     */
    @GetMapping("/increment")
    public Result<List<ArchiveSyncDTO>> getIncrementArchives(
            @RequestParam(required = false) LocalDateTime lastSyncTime,
            @RequestParam(defaultValue = "100") Integer limit) {
        
        // 认证检查（略）
        
        // 获取增量数据
        List<ArchiveSyncDTO> archives = archiveService.getIncrementArchives(lastSyncTime, limit);
        
        // 记录同步日志
        if (!archives.isEmpty()) {
            syncLogService.recordSyncRequest("EXTERNAL_SYSTEM", archives.size());
        }
        
        return Result.success(archives);
    }
    
    /**
     * 确认同步完成
     */
    @PostMapping("/confirm")
    public Result<Boolean> confirmSync(@RequestBody ArchiveSyncConfirmDTO confirmDTO) {
        // 认证检查（略）
        
        // 更新同步状态
        boolean success = archiveService.updateSyncStatus(
            confirmDTO.getArchiveIds(), confirmDTO.getBatchNo());
            
        return Result.success(success);
    }
}
```

## 6. 安全配置

```yaml
# 档案管理模块配置
archive:
  # 安全配置
  security:
    # API密钥(用于外部系统访问)
    api-key: ${ARCHIVE_API_KEY}
    # 允许的IP白名单
    ip-whitelist: ${ARCHIVE_IP_WHITELIST:127.0.0.1,192.168.1.100}
    # 是否启用IP白名单
    enable-ip-filter: true
    # 接口调用日志
    enable-access-log: true
    
  # 归档配置
  archive:
    # 档案编号前缀
    no-prefix: "ACLF"
    # 档案编号日期格式
    no-date-format: "yyyyMMdd"
    
  # 同步配置
  sync:
    # 批次大小
    batch-size: 50
    # 重试次数
    retry-count: 3
    # 重试间隔(秒)
    retry-interval: 60
```

## 7. 模块验证方式

为确保档案管理模块正常运行，建议以下验证方式：

1. **归档流程测试**：
   - 在案件系统中模拟案件结案归档
   - 检查档案系统是否正确接收数据
   - 验证案件在源系统中不可见/不可操作

2. **同步接口测试**：
   - 使用API工具模拟外部系统调用同步接口
   - 验证数据完整性和正确性
   - 测试并发同步请求处理能力

3. **安全机制测试**：
   - 尝试未授权访问API接口
   - 验证IP白名单限制
   - 确认日志记录完整性

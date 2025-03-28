# 律师事务所知识管理模块

## 1. 模块说明

知识管理模块是律师事务所管理系统的核心业务模块之一，负责事务所各类知识资产的创建、存储、分类、检索和共享。本模块基于知识模型层（knowledge-model）实现业务逻辑和接口功能，提供完整的知识库管理解决方案。

## 2. 功能特性

### 2.1 核心功能

- **知识文档管理**：支持创建、编辑、审核、归档各类知识文档
- **分类管理**：多层级分类体系，灵活组织知识结构
- **标签管理**：支持标签创建、归并和聚合分析
- **全文检索**：基于内容和元数据的快速检索能力
- **版本控制**：关键文档的版本历史追踪
- **权限管理**：基于角色和用户的精细化访问控制
- **知识统计**：提供知识使用和贡献的统计分析

### 2.2 知识类型

模块支持以下核心知识类型：

1. **文档模板**
   - 起诉状、答辩状模板
   - 各类合同标准文本
   - 法律意见书模板
   - 尽职调查报告模板
   - 律师函模板

2. **工作指南**
   - 案件办理流程指南
   - 证据收集和整理方法
   - 常见法律程序操作指南
   - 客户沟通指引
   - 庭审准备和技巧

3. **业务规范**
   - 案件管理制度
   - 利益冲突审查规范
   - 文件命名和管理标准
   - 质量控制和复核标准
   - 收费标准和财务规范

4. **培训资料**
   - 新员工入职培训
   - 专业技能培训
   - 法律法规更新培训
   - 管理能力培训
   - 职业道德培训

5. **经验总结**
   - 典型案例分析
   - 疑难问题解决方案
   - 项目经验总结
   - 风险防控经验
   - 行业实践经验总结

## 3. 模块结构

```
law-firm-knowledge/
├── controller/                # 控制器层
│   ├── KnowledgeController.java         # 知识文档控制器
│   ├── KnowledgeCategoryController.java # 知识分类控制器
│   ├── KnowledgeTagController.java      # 知识标签控制器
│   └── KnowledgeAttachmentController.java # 知识附件控制器
├── service/                   # 服务实现层
│   ├── impl/                  # 接口实现
│   │   ├── KnowledgeServiceImpl.java
│   │   ├── KnowledgeCategoryServiceImpl.java
│   │   ├── KnowledgeTagServiceImpl.java
│   │   └── KnowledgeAttachmentServiceImpl.java
│   └── convert/               # 对象转换器
│       └── KnowledgeConvert.java
├── config/                    # 模块配置
│   ├── KnowledgeConfig.java   # 知识模块配置
│   └── ResourceConfig.java    # 资源配置
├── utils/                     # 工具类
│   └── KnowledgeUtils.java    # 知识相关工具
└── job/                       # 定时任务
    └── KnowledgeStatisticJob.java # 知识统计任务
```

## 4. 核心功能实现

### 4.1 知识文档管理

- 支持富文本和Markdown编辑
- 支持模板变量定义和使用
- 文档内容版本控制和差异对比
- 支持批量导入和导出
- 提供文档访问权限控制

### 4.2 分类体系

- 支持多级分类树结构
- 分类可配置访问权限
- 支持分类合并和拆分
- 提供分类快速筛选
- 支持分类统计和热度分析

### 4.3 标签系统

- 支持自定义标签和系统标签
- 提供标签关联分析
- 智能标签推荐
- 标签云可视化
- 基于标签的文档聚合

### 4.4 搜索引擎

- 全文检索支持
- 基于标题、内容、标签的组合搜索
- 支持高级搜索语法
- 搜索结果权重排序
- 搜索历史记录和热搜榜

### 4.5 知识审核流程

- 新增知识审核流程
- 修改知识审核流程
- 知识废止和归档流程
- 审核意见反馈
- 审核历史记录

## 5. 使用示例

### 5.1 添加知识文档

```java
// 控制器示例
@PostMapping("/add")
public ResponseEntity<KnowledgeVO> addKnowledge(@RequestBody @Valid KnowledgeDTO knowledgeDTO) {
    Knowledge knowledge = knowledgeService.addKnowledge(knowledgeDTO);
    return ResponseEntity.ok(knowledgeConvert.toVO(knowledge));
}

// 服务实现示例
@Override
@Transactional(rollbackFor = Exception.class)
public Knowledge addKnowledge(KnowledgeDTO dto) {
    // 转换DTO为实体
    Knowledge knowledge = knowledgeConvert.fromDTO(dto);
    
    // 设置创建者信息
    Long userId = SecurityUtils.getCurrentUserId();
    String userName = SecurityUtils.getCurrentUsername();
    knowledge.setAuthorId(userId);
    knowledge.setAuthorName(userName);
    
    // 保存知识文档
    boolean success = save(knowledge);
    if (!success) {
        throw new BusinessException("知识文档保存失败");
    }
    
    // 处理标签关联
    if (CollUtil.isNotEmpty(dto.getTagIds())) {
        knowledgeTagRelationService.createRelations(knowledge.getId(), dto.getTagIds());
    }
    
    // 处理附件
    if (CollUtil.isNotEmpty(dto.getAttachments())) {
        knowledgeAttachmentService.batchSaveAttachments(knowledge.getId(), dto.getAttachments());
    }
    
    return knowledge;
}
```

### 5.2 查询知识分类树

```java
// 控制器示例
@GetMapping("/category/tree")
public ResponseEntity<List<KnowledgeCategoryVO>> getCategoryTree() {
    List<KnowledgeCategory> categoryTree = knowledgeCategoryService.getCategoryTree();
    return ResponseEntity.ok(knowledgeCategoryConvert.toVOList(categoryTree));
}

// 服务实现示例
@Override
public List<KnowledgeCategory> getCategoryTree() {
    // 查询所有分类
    List<KnowledgeCategory> allCategories = list();
    
    // 构建分类树
    List<KnowledgeCategory> rootCategories = allCategories.stream()
            .filter(category -> category.getParentId() == 0)
            .collect(Collectors.toList());
            
    rootCategories.forEach(root -> {
        buildChildrenTree(root, allCategories);
    });
    
    return rootCategories;
}

private void buildChildrenTree(KnowledgeCategory parent, List<KnowledgeCategory> allCategories) {
    List<KnowledgeCategory> children = allCategories.stream()
            .filter(category -> Objects.equals(category.getParentId(), parent.getId()))
            .collect(Collectors.toList());
            
    parent.setChildren(children);
    
    children.forEach(child -> {
        buildChildrenTree(child, allCategories);
    });
}
```

## 6. 配置说明

```yaml
# application-knowledge.yml
knowledge:
  # 存储配置
  storage:
    base-path: /data/knowledge
    temp-path: /data/knowledge/temp
    
  # 文档配置
  document:
    max-size: 10MB
    allowed-extensions: .doc,.docx,.pdf,.md,.txt
    
  # 附件配置
  attachment:
    max-size: 20MB
    max-count-per-knowledge: 10
    allowed-extensions: .doc,.docx,.pdf,.jpg,.png,.zip
    
  # 分类配置
  category:
    max-depth: 3
    
  # 搜索配置
  search:
    enable-highlight: true
    max-result-count: 100
    min-keyword-length: 2
    
  # 权限配置
  permission:
    public-categories: [1, 2, 3]  # 公开分类ID列表
    
  # 统计任务
  statistic:
    cron: "0 0 1 * * ?"  # 每天凌晨1点执行
```

## 7. 模块集成说明

本模块作为业务层的一部分，不需要独立部署。它与其他业务模块一起，由上层的API层统一集成和对外提供服务。

### 7.1 模块定位

- 位于业务层(Modules Layer)，实现具体业务逻辑
- 依赖知识模型层(knowledge-model)提供的实体和接口定义
- 被API层引用，不直接对外提供服务
- 遵循项目整体的DDD分层架构设计

### 7.2 与核心模块集成

#### 7.2.1 存储服务集成 (core-storage)

本模块利用`core-storage`模块处理知识文档的附件管理，主要集成点：

1. **添加依赖**
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>law-firm-core-storage</artifactId>
</dependency>
```

2. **注入存储服务**
```java
@Autowired
@Qualifier("storageFileServiceImpl")
private FileService fileService;

@Autowired
@Qualifier("storageBucketServiceImpl")
private BucketService bucketService;
```

3. **知识附件处理**
```java
// 上传知识附件
public Long uploadKnowledgeAttachment(MultipartFile file, Long knowledgeId) {
    // 获取知识文档存储桶
    Long bucketId = getOrCreateKnowledgeBucket();
    
    // 设置附件元数据
    Map<String, String> metadata = new HashMap<>();
    metadata.put("knowledgeId", knowledgeId.toString());
    metadata.put("docType", "knowledge_attachment");
    
    // 上传文件
    FileInfo fileInfo = fileService.uploadFile(bucketId, file, metadata);
    
    // 保存附件关联信息
    KnowledgeAttachment attachment = new KnowledgeAttachment();
    attachment.setKnowledgeId(knowledgeId);
    attachment.setFileName(file.getOriginalFilename());
    attachment.setFileSize(file.getSize());
    attachment.setFileType(file.getContentType());
    attachment.setStorageId(fileInfo.getId());
    knowledgeAttachmentService.save(attachment);
    
    return attachment.getId();
}

// 获取知识文档存储桶
private Long getOrCreateKnowledgeBucket() {
    // 查询或创建知识文档专用存储桶
    StorageBucket bucket = bucketService.getBucketByName("knowledge-documents");
    if (bucket == null) {
        bucket = new StorageBucket();
        bucket.setBucketName("knowledge-documents");
        bucket.setStorageType(StorageTypeEnum.MINIO);
        bucketService.createBucket(bucket);
    }
    return bucket.getId();
}
```

#### 7.2.2 搜索服务集成 (core-search)

本模块利用`core-search`模块实现知识内容的全文检索，主要集成点：

1. **添加依赖**
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>law-firm-core-search</artifactId>
</dependency>
```

2. **注入搜索服务**
```java
@Autowired
@Qualifier("searchServiceImpl")
private SearchService searchService;

@Autowired
@Qualifier("searchIndexServiceImpl")
private IndexService indexService;
```

3. **知识内容索引**
```java
// 索引知识文档
public void indexKnowledgeDocument(Knowledge knowledge) {
    // 构建索引文档
    Map<String, Object> document = new HashMap<>();
    document.put("id", knowledge.getId());
    document.put("title", knowledge.getTitle());
    document.put("content", knowledge.getContent());
    document.put("summary", knowledge.getSummary());
    document.put("type", knowledge.getKnowledgeType().getDesc());
    document.put("keywords", knowledge.getKeywords());
    document.put("categoryId", knowledge.getCategoryId());
    document.put("authorName", knowledge.getAuthorName());
    document.put("createTime", knowledge.getCreateTime());
    
    // 索引文档
    indexService.indexDocument("knowledge", knowledge.getId().toString(), document);
}
```

4. **知识内容搜索**
```java
// 搜索知识文档
public Page<KnowledgeVO> searchKnowledge(String keyword, Integer page, Integer size) {
    // 设置分页
    Pageable pageable = PageRequest.of(page - 1, size);
    
    // 执行搜索
    SearchResult<Map<String, Object>> result = searchService.search(keyword, "knowledge", pageable);
    
    // 处理搜索结果
    List<KnowledgeVO> knowledgeList = result.getDocuments().stream()
        .map(this::convertToKnowledgeVO)
        .collect(Collectors.toList());
    
    // 返回分页结果
    return new PageImpl<>(knowledgeList, pageable, result.getTotalHits());
}

// 将搜索结果转换为知识VO
private KnowledgeVO convertToKnowledgeVO(Map<String, Object> document) {
    KnowledgeVO vo = new KnowledgeVO();
    vo.setId(Long.valueOf(document.get("id").toString()));
    vo.setTitle((String) document.get("title"));
    vo.setSummary((String) document.get("summary"));
    // 设置其他属性
    return vo;
}
```

#### 7.2.3 审计服务集成 (core-audit)

本模块利用`core-audit`模块记录知识管理相关的操作日志和数据变更，主要集成点：

1. **添加依赖**
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>law-firm-core-audit</artifactId>
</dependency>
```

2. **注入审计服务**
```java
@Autowired
@Qualifier("coreAuditServiceImpl")
private AuditService auditService;

@Autowired
@Qualifier("auditQueryServiceImpl")
private AuditQueryService auditQueryService;
```

3. **知识操作审计**
```java
// 记录知识创建审计
public Knowledge createKnowledge(KnowledgeDTO dto) {
    Knowledge knowledge = doCreateKnowledge(dto);
    
    // 记录审计日志
    auditService.recordLog(
        "知识管理", 
        "创建知识", 
        String.format("创建知识文档：[%s]，分类：[%s]", knowledge.getTitle(), knowledge.getCategoryId())
    );
    
    return knowledge;
}

// 记录知识删除审计
public boolean deleteKnowledge(Long id) {
    // 获取知识信息用于日志记录
    Knowledge knowledge = getById(id);
    if (knowledge == null) {
        throw new BusinessException("知识文档不存在");
    }
    
    boolean result = removeById(id);
    
    // 异步记录审计日志
    auditService.recordLogAsync(
        "知识管理", 
        "删除知识", 
        String.format("删除知识文档：[%s]，ID：[%d]", knowledge.getTitle(), knowledge.getId())
    );
    
    return result;
}
```

4. **知识审计日志查询**
```java
// 查询知识文档操作日志
public Page<AuditLogVO> queryKnowledgeAuditLogs(Long knowledgeId, Integer page, Integer size) {
    // 构建查询条件
    AuditLogQueryDTO queryDTO = AuditLogQueryDTO.builder()
        .module("知识管理")
        .businessId(knowledgeId.toString())
        .pageNum(page)
        .pageSize(size)
        .build();
    
    // 查询审计日志
    return auditQueryService.queryAuditLogs(queryDTO);
}
```

#### 7.2.4 消息服务集成 (core-message)

本模块利用`core-message`模块处理知识相关的消息通知，主要集成点：

1. **添加依赖**
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>law-firm-core-message</artifactId>
</dependency>
```

2. **注入消息服务**
```java
@Autowired
@Qualifier("messageServiceImpl")
private MessageService messageService;

@Autowired
@Qualifier("messageTemplateServiceImpl")
private MessageTemplateService templateService;
```

3. **知识更新通知**
```java
// 知识文档更新后发送通知
public void sendKnowledgeUpdateNotification(Knowledge knowledge, List<Long> subscriberIds) {
    // 构建消息变量
    Map<String, Object> variables = new HashMap<>();
    variables.put("knowledgeTitle", knowledge.getTitle());
    variables.put("knowledgeType", knowledge.getKnowledgeType().getDesc());
    variables.put("updateTime", DateTimeUtils.formatDateTime(knowledge.getUpdateTime()));
    variables.put("authorName", knowledge.getAuthorName());
    
    // 发送系统消息
    messageService.sendSystemMessage(
        "KNOWLEDGE_UPDATE",  // 消息模板代码
        variables,
        subscriberIds
    );
}
```

4. **知识审核流程消息**
```java
// 发送知识审核请求通知
public void sendKnowledgeReviewNotification(Knowledge knowledge, List<Long> reviewerIds) {
    // 构建消息变量
    Map<String, Object> variables = new HashMap<>();
    variables.put("knowledgeId", knowledge.getId());
    variables.put("knowledgeTitle", knowledge.getTitle());
    variables.put("knowledgeType", knowledge.getKnowledgeType().getDesc());
    variables.put("requestTime", DateTimeUtils.formatDateTime(LocalDateTime.now()));
    variables.put("authorName", knowledge.getAuthorName());
    
    // 审核链接
    String reviewUrl = String.format("/knowledge/review?id=%d", knowledge.getId());
    variables.put("reviewUrl", reviewUrl);
    
    // 发送系统消息
    messageService.sendSystemMessage(
        "KNOWLEDGE_REVIEW_REQUEST",  // 消息模板代码
        variables,
        reviewerIds
    );
    
    // 可选：同时发送邮件通知
    messageService.sendEmailMessage(
        "KNOWLEDGE_REVIEW_EMAIL",  // 邮件模板代码
        variables,
        reviewerIds
    );
}
```

### 7.3 集成要点

1. 模块间依赖配置正确，特别是与其他业务模块的交互
2. 权限体系与主系统集成，复用统一的权限管理
3. 用户体系保持一致，使用系统统一的用户管理
4. 文件存储与core-storage模块集成
5. 搜索功能与core-search模块集成
6. 为存储和搜索服务提供必要的数据表和配置

## 8. 注意事项

1. 知识创建和编辑需要考虑并发控制，避免内容覆盖
2. 大型附件处理应考虑断点续传和分片上传
3. 敏感知识内容需要配置访问权限和水印
4. 知识搜索应优化索引，提高检索效率
5. 定期备份知识库内容，防止数据丢失
6. 注意知识文档的知识产权保护

## 9. 常见问题

### Q1: 如何处理大量知识文档的性能问题?
A1: 采用分页加载、懒加载和缓存策略，对于大型知识库考虑使用专业搜索引擎如Elasticsearch。

### Q2: 如何保证知识内容的安全?
A2: 实施严格的访问控制、操作审计和内容加密，关键内容可以考虑水印和防止复制。

### Q3: 如何促进知识共享和利用?
A3: 实现知识推荐、热门排行和订阅功能，可以考虑积分激励机制鼓励知识贡献。
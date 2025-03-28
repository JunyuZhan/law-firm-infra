# 知识管理模块

## 模块说明
知识管理模块提供律师事务所的知识库管理功能，支持知识文档、分类、标签等内容的管理，实现知识的沉淀和共享。

## 功能特性
1. 知识文档管理
   - 知识内容创建与编辑
   - 多种知识类型支持
   - 知识分类管理
   - 知识标签管理
   - 知识附件管理
   
2. 分类管理
   - 分类创建与编辑
   - 分类层级管理
   - 分类统计分析
   
3. 标签管理
   - 标签创建与编辑
   - 标签关联管理
   - 热门标签支持
   
4. 附件管理
   - 附件上传与管理
   - 附件与知识关联

## 目录结构
```
knowledge-model/
├── entity/       # 实体类
│   ├── Knowledge.java           # 知识实体
│   ├── KnowledgeCategory.java   # 知识分类实体
│   ├── KnowledgeTag.java        # 知识标签实体
│   └── KnowledgeAttachment.java # 知识附件实体
├── enums/        # 枚举类
│   └── KnowledgeTypeEnum.java   # 知识类型枚举
├── dto/          # 数据传输对象
│   ├── KnowledgeDTO.java           # 知识DTO
│   ├── KnowledgeCategoryDTO.java   # 知识分类DTO
│   ├── KnowledgeTagDTO.java        # 知识标签DTO
│   └── KnowledgeAttachmentDTO.java # 知识附件DTO
├── vo/           # 视图对象
│   ├── KnowledgeVO.java           # 知识视图对象
│   ├── KnowledgeCategoryVO.java   # 知识分类视图对象
│   ├── KnowledgeTagVO.java        # 知识标签视图对象
│   └── KnowledgeAttachmentVO.java # 知识附件视图对象
├── mapper/       # MyBatis映射接口
│   ├── KnowledgeMapper.java           # 知识Mapper
│   ├── KnowledgeCategoryMapper.java   # 知识分类Mapper
│   ├── KnowledgeTagMapper.java        # 知识标签Mapper
│   └── KnowledgeAttachmentMapper.java # 知识附件Mapper
└── service/      # 服务接口
    ├── KnowledgeService.java           # 知识服务
    ├── KnowledgeCategoryService.java   # 知识分类服务
    ├── KnowledgeTagService.java        # 知识标签服务
    └── KnowledgeAttachmentService.java # 知识附件服务
```

## 核心接口
1. KnowledgeService（继承BaseService<Knowledge>）
   - 知识基础CRUD操作
   - 根据分类ID查询知识列表
   - 根据标签ID查询知识列表
   - 关键词搜索知识
   - 获取最新知识
   - 获取相关知识推荐
   
2. KnowledgeCategoryService（继承BaseService<KnowledgeCategory>）
   - 分类基础CRUD操作
   - 获取完整的分类树
   - 获取指定分类下的子分类
   - 获取分类路径
   
3. KnowledgeTagService（继承BaseService<KnowledgeTag>）
   - 标签基础CRUD操作
   - 根据知识ID查询标签列表
   - 获取热门标签
   
4. KnowledgeAttachmentService（继承BaseService<KnowledgeAttachment>）
   - 附件基础CRUD操作
   - 根据知识ID查询附件列表
   - 统计知识附件数量

## 实体关系
1. Knowledge（知识）
   - 一个知识属于一个分类（KnowledgeCategory）
   - 一个知识可以有多个标签（KnowledgeTag）
   - 一个知识可以有多个附件（KnowledgeAttachment）

2. KnowledgeCategory（分类）
   - 一个分类可以有多个知识（Knowledge）
   - 一个分类可以有多个子分类（KnowledgeCategory）
   - 一个分类只能有一个父分类（KnowledgeCategory）

3. KnowledgeTag（标签）
   - 一个标签可以关联多个知识（Knowledge）
   - 一个知识可以有多个标签（KnowledgeTag）

4. KnowledgeAttachment（附件）
   - 一个附件属于一个知识（Knowledge）
   - 一个知识可以有多个附件（KnowledgeAttachment）

## 使用示例
```java
// 创建知识
KnowledgeDTO knowledgeDTO = new KnowledgeDTO()
    .setTitle("法律研究报告")
    .setContent("报告内容...")
    .setKnowledgeType(KnowledgeTypeEnum.EXPERIENCE)
    .setSummary("这是一份法律研究报告的摘要")
    .setCategoryId(1L)
    .setTagIds(Arrays.asList(1L, 2L))
    .setKeywords("法律,研究,报告");
knowledgeService.save(BeanConverter.convert(knowledgeDTO, Knowledge.class));

// 查询分类下的知识
List<Knowledge> knowledgeList = knowledgeService.listByCategoryId(1L);

// 获取知识分类树
List<KnowledgeCategory> categoryTree = knowledgeCategoryService.getCategoryTree();

// 获取标签列表
List<KnowledgeTag> tags = knowledgeTagService.listByKnowledgeId(1L);
```

## 配置说明
```yaml
knowledge:
  # 知识配置
  max-title-length: 100
  max-content-length: 50000
  
  # 附件配置
  attachment:
    max-size: 10485760  # 10MB
    allowed-types: [".doc", ".docx", ".pdf", ".jpg", ".png"]
```

## 依赖说明
- Spring Boot 3.2.x
- MyBatis-Plus（通过base-model）
- 内部依赖：common-core, common-data, base-model
- Lombok

## 注意事项
1. 知识内容支持多种类型，包括文档模板、工作指南、业务规范、培训资料和经验总结
2. 知识分类支持多级分类结构
3. 所有Service接口都继承自BaseService，实现类应继承BaseServiceImpl
4. 对于复杂的业务逻辑，应在modules层的实现类中完成，model层只定义接口
5. 附件上传功能需要与core-storage模块集成
6. 知识搜索功能可以与core-search模块集成以提升搜索效率 
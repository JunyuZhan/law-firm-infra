# 文档管理模块 (Document Model)

## 模块说明
文档管理模块是律师事务所管理系统的文档数据模型定义模块，负责定义系统中所有文档相关的实体类、数据传输对象、视图对象和服务接口。该模块是系统文档管理的数据基础。

## 目录结构
```
document-model/
├── entity/
│   ├── base/              # 基础文档
│   │   ├── BaseDocument.java     # 文档基类
│   │   └── DocumentInfo.java     # 文档信息
│   ├── business/          # 业务文档
│   │   ├── CaseDocument.java     # 案件文档
│   │   ├── ContractDocument.java # 合同文档
│   │   └── ArticleDocument.java  # 知识文章
│   └── system/           # 系统文档
│       └── TemplateDocument.java # 模板文档
├── storage/             # 存储管理
│   ├── StorageConfig.java       # 存储配置
│   └── StorageProvider.java     # 存储提供者
├── enums/
│   ├── DocumentTypeEnum.java  # 文档类型
│   ├── DocumentStatusEnum.java # 文档状态
│   └── TemplateTypeEnum.java  # 模板类型
├── dto/
│   ├── document/
│   │   ├── DocumentCreateDTO.java # 文档创建
│   │   ├── DocumentUpdateDTO.java # 文档更新
│   │   └── DocumentQueryDTO.java  # 文档查询
│   └── template/
│       ├── TemplateCreateDTO.java # 模板创建
│       └── TemplateUpdateDTO.java # 模板更新
├── vo/
│   ├── DocumentVO.java     # 文档视图
│   └── TemplateVO.java     # 模板视图
└── service/
    ├── DocumentService.java # 文档服务
    └── TemplateService.java # 模板服务
```

## 核心功能

### 1. 基础文档管理
- 文档基类（BaseDocument）
  - 文档标题
  - 文档类型
  - 文件名
  - 文件大小
  - 存储路径
  - 存储类型
  - 文档状态
  - 文档版本
  - 关键词
  - 访问权限
  - 下载/查看次数

- 文档信息（DocumentInfo）
  - 文档ID
  - 作者
  - 所有者
  - 所属部门
  - 文档分类
  - 标签
  - 源文件路径
  - 语言
  - 页数/字数
  - 访问时间
  - 修改时间
  - 文档来源
  - 格式/编码
  - 文档摘要
  - 相关文档
  - 审核信息

### 2. 业务文档管理
- 案件文档（CaseDocument）
  - 案件ID/编号
  - 文档阶段
  - 文档分类
  - 机密等级
  - 签名状态
  - 相关方信息

- 合同文档（ContractDocument）
  - 合同ID/编号
  - 合同类型
  - 合同状态
  - 签约方信息
  - 签约日期
  - 生效日期
  - 到期日期
  - 合同金额
  - 盖章状态
  - 审批信息

- 知识文章（ArticleDocument）
  - 文章分类
  - 文章类型
  - 作者信息
  - 来源
  - 摘要
  - 封面图片
  - 阅读统计
  - 推荐设置
  - 审核状态

### 3. 系统文档管理
- 模板文档（TemplateDocument）
  - 模板编码
  - 模板类型
  - 业务类型
  - 适用范围
  - 模板参数
  - 模板内容
  - 系统设置
  - 使用统计
  - 版本管理
  - 审核信息

### 4. 存储管理
- 存储配置（StorageConfig）
  - 存储类型
  - 存储区域
  - 访问密钥
  - 存储桶
  - 基础路径
  - 默认配置

- 存储提供者（StorageProvider）
  - 存储接口定义
  - 存储实现策略
  - 文件操作
  - 权限控制

## 核心类说明

### 1. 实体类
- BaseDocument：文档基类
  - 基本属性
  - 存储属性
  - 状态管理
  - 统计信息

- DocumentInfo：文档信息
  - 元数据管理
  - 分类标签
  - 统计信息
  - 审核信息

### 2. 数据传输对象
- document/：文档相关DTO
  - DocumentCreateDTO：文档创建
  - DocumentUpdateDTO：文档更新
  - DocumentQueryDTO：文档查询

- template/：模板相关DTO
  - TemplateCreateDTO：模板创建
  - TemplateUpdateDTO：模板更新

### 3. 视图对象
- DocumentVO：文档视图
  - 基本信息
  - 存储信息
  - 状态信息
  - 统计信息

- TemplateVO：模板视图
  - 模板信息
  - 业务信息
  - 使用统计
  - 版本信息

### 4. 服务接口
- DocumentService：文档服务
  - 文档CRUD
  - 文档上传下载
  - 文档预览
  - 状态管理
  - 分类查询
  - 缓存管理

- TemplateService：模板服务
  - 模板CRUD
  - 模板应用
  - 参数管理
  - 版本控制
  - 默认设置
  - 缓存管理

## 使用示例

### 1. 创建文档
```java
DocumentCreateDTO createDTO = new DocumentCreateDTO()
    .setTitle("合同审查报告")
    .setDocType(DocumentTypeEnum.CONTRACT.getValue())
    .setFileName("合同审查报告.docx")
    .setKeywords("合同,审查,报告")
    .setDescription("合同审查报告文档");

Long docId = documentService.createDocument(createDTO, inputStream);
```

### 2. 创建模板
```java
TemplateCreateDTO createDTO = new TemplateCreateDTO()
    .setTemplateCode("CONTRACT_REVIEW")
    .setTemplateName("合同审查模板")
    .setTemplateType("CONTRACT")
    .setBusinessType("REVIEW")
    .setContent("模板内容")
    .setParameters("{\"company\":\"公司名称\",\"date\":\"日期\"}");

Long templateId = templateService.createTemplate(createDTO);
```

## 注意事项
1. 文档标题和文件名必须规范
2. 注意文档类型和状态的正确性
3. 及时更新文档状态和版本
4. 重要操作需要记录日志
5. 关注文档安全和权限控制
6. 模板参数必须符合规范
7. 注意存储配置的正确性 
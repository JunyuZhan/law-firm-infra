# 文档管理数据模型模块 (document-model)

## 模块概述

document-model模块是律师事务所管理系统的文档管理数据模型层，负责定义所有与文档相关的数据结构、传输对象和数据访问接口。该模块作为系统文档管理功能的基础，为上层业务模块提供数据模型支持。

## 功能特性

- 支持多种类型文档的数据模型定义（基础文档、案件文档、合同文档、文章文档等）
- 提供文档分类、标签和权限管理的数据结构
- 支持文档模板管理
- 提供完善的数据传输对象(DTO)和视图对象(VO)
- 包含文档相关的枚举定义（文档格式、状态、类型等）
- 基于MyBatis-Plus的数据访问接口

## 模块结构

```
document-model/
├── src/main/java/com/lawfirm/model/document/
│   ├── entity/           # 实体类定义
│   │   ├── base/         # 基础实体
│   │   ├── business/     # 业务文档实体
│   │   └── template/     # 模板实体
│   ├── dto/              # 数据传输对象
│   │   ├── document/     # 文档DTO
│   │   └── template/     # 模板DTO
│   ├── vo/               # 视图对象
│   ├── enums/            # 枚举类
│   ├── mapper/           # 数据访问接口
│   └── service/          # 服务接口定义
└── pom.xml               # 模块依赖配置
```

## 核心实体类

### 基础实体

- **BaseDocument**: 所有文档实体的基类，包含文档的基本属性
  - 文档标题、文件名、文件大小、存储路径等通用属性
  - 文档格式、访问权限、加密状态等业务属性
  - 查看次数、下载次数等统计信息

- **DocumentInfo**: 文档元数据信息
  - 作者、所有者、所属部门等归属信息
  - 页数、字数、语言等内容特性
  - 最后访问时间、最后修改时间等时间属性

- **DocumentCategory**: 文档分类实体
- **DocumentTag**: 文档标签实体
- **DocumentPermission**: 文档权限实体

### 业务文档实体

- **CaseDocument**: 案件文档
  - 关联案件ID、案件编号
  - 文档阶段（立案、庭审、结案等）
  - 文档分类（起诉书、判决书、证据等）

- **ContractDocument**: 合同文档
  - 关联合同ID、合同编号
  - 合同类型、合同状态
  - 生效日期、到期日期等

- **ArticleDocument**: 文章文档
  - 文章类型、关键词
  - 发布状态、发布渠道

### 模板文档

- **TemplateDocument**: 文档模板实体

## 数据传输对象(DTO)

- **DocumentCreateDTO**: 文档创建数据传输对象
- **DocumentUpdateDTO**: 文档更新数据传输对象
- **DocumentQueryDTO**: 文档查询条件对象

## 视图对象(VO)

- **DocumentVO**: 文档视图对象，用于前端展示
- **TemplateVO**: 模板视图对象

## 枚举类

- **DocumentFormatEnum**: 文档格式枚举（PDF、DOCX、DOC等）
- **DocumentTypeEnum**: 文档类型枚举
- **DocumentStatusEnum**: 文档状态枚举（草稿、已发布、已归档等）
- **DocumentAccessLevelEnum**: 文档访问级别枚举（公开、私有、受限等）
- **DocumentOperationEnum**: 文档操作类型枚举
- **StorageTypeEnum**: 存储类型枚举（本地、OSS、S3等）
- **TemplateTypeEnum**: 模板类型枚举

## 数据访问接口

所有Mapper接口继承自MyBatis-Plus的BaseMapper，提供基础的CRUD操作：

- **DocumentMapper**: 基础文档数据访问接口
- **DocumentInfoMapper**: 文档信息数据访问接口
- **DocumentCategoryMapper**: 文档分类数据访问接口
- **DocumentTagMapper**: 文档标签数据访问接口
- **DocumentPermissionMapper**: 文档权限数据访问接口
- **CaseDocumentMapper**: 案件文档数据访问接口
- **ContractDocumentMapper**: 合同文档数据访问接口
- **ArticleDocumentMapper**: 文章文档数据访问接口
- **TemplateDocumentMapper**: 模板文档数据访问接口

## 使用示例

### 实体类使用

```java
// 创建案件文档实体
CaseDocument document = new CaseDocument()
    .setTitle("某公司侵权案起诉书")
    .setDocType("LEGAL_DOCUMENT")
    .setFileName("起诉书.docx")
    .setFileSize(1024L)
    .setFileType("docx")
    .setDocumentFormat(DocumentFormatEnum.DOCX)
    .setStorageType(StorageTypeEnum.OSS.getValue())
    .setDocStatus(DocumentStatusEnum.DRAFT.getValue())
    .setCaseId(1001L)
    .setCaseCode("CASE-2023001")
    .setDocStage("FILING");
```

### DTO使用

```java
// 创建文档DTO
DocumentCreateDTO createDTO = new DocumentCreateDTO()
    .setTitle("合同审查报告")
    .setDocType("REPORT")
    .setFileName("合同审查报告.pdf")
    .setFileSize(2048L)
    .setFileType("pdf")
    .setStorageType("OSS")
    .setDocStatus("PUBLISHED")
    .setAccessLevel("RESTRICTED")
    .setBusinessId(2001L)
    .setBusinessType("CONTRACT");
```

## 依赖关系

本模块依赖：
- base-model：提供基础模型支持
- common-data：提供数据访问基础设施

上层模块依赖：
- law-firm-document：文档管理业务模块
- law-firm-case：案件管理模块（使用案件文档）
- law-firm-contract：合同管理模块（使用合同文档）

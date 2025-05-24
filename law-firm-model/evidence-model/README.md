# evidence-model 证据领域模型模块

## 模块定位
本模块为律所系统的证据管理领域模型层，专注于证据相关的数据结构、DTO/VO、MyBatis-Plus Mapper 及 Service 接口定义，不包含具体业务实现。

## 主要结构
- **entity**：证据主表与各子表（如 Evidence、EvidenceAttachment、EvidencePersonnel、EvidenceReview、EvidenceChallenge、EvidenceTrace、EvidenceTagRelation、EvidenceFactRelation、EvidenceCatalog、EvidenceTag、EvidenceDocumentRelation 等）
- **dto/vo**：每个表均配套 DTO/VO，便于数据传输与前后端解耦
- **mapper**：每个表均有 MyBatis-Plus Mapper 接口
- **service**：每个表均有 Service 接口，定义标准的增删改查方法
- **enums/constant**：证据相关枚举与常量

## 依赖说明
- 依赖 base-model，复用基础实体与通用字段
- 推荐与 law-firm-evidence 业务实现模块配合使用

## 扩展与规范
- entity、DTO、VO 字段一一对应，便于自动转换
- Mapper 命名规范：表名+Mapper
- Service 命名规范：表名+Service，接口仅定义方法，不做实现
- 严格分层，禁止引入业务逻辑

## 典型目录结构
```
com.lawfirm.model.evidence
├── entity
├── dto
├── vo
├── mapper
├── service
├── enums
├── constant
```

## 典型表说明
- evidence：证据主表
- evidence_attachment：证据附件
- evidence_personnel：证据人员
- evidence_review：证据审核
- evidence_challenge：证据质证
- evidence_trace：证据流转
- evidence_tag_relation：证据标签关联
- evidence_fact_relation：证据事实关联
- evidence_catalog：证据目录
- evidence_tag：证据标签
- evidence_document_relation：证据与文档关联

## 适用场景
- 仅做数据结构和接口定义，业务逻辑请在 law-firm-evidence 等业务模块实现 
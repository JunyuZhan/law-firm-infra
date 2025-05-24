# law-firm-evidence 证据业务实现模块

## 模块定位
本模块为律所系统的证据管理业务实现层，依赖 evidence-model，负责证据主表及各子表的业务逻辑、ServiceImpl、Controller、Converter 实现。

## 分层结构
- **service/impl**：各表 Service 实现，命名唯一，依赖 model 层接口
- **controller**：RESTful 控制器，路径常量统一，bean 名称唯一
- **converter**：DTO/VO/entity 转换器，便于分层解耦
- **resources**：application-evidence.yml 配置、数据库迁移脚本（V13001__init_evidence_tables.sql）

## 依赖说明
- 依赖 evidence-model，复用 entity/mapper/dto/vo/service
- 需配合 law-firm-case、law-firm-document 等模块协作

## 数据库脚本
- 所有表结构见 `src/main/resources/META-INF/db/migration/V13001__init_evidence_tables.sql`
- 编号段独立，避免与其他模块冲突

## 主要功能
- 证据主表及子表（附件、人员、审核、质证、流转、标签、事实等）全量增删改查
- Controller 层接口全部标准 RESTful，路径如 `/api/v1/evidences/{evidenceId}/attachments`
- 所有 bean 均指定唯一名称，便于多模块协作
- 不包含 application 启动类和冗余 mapper/xml

## 代码规范与扩展
- 严格分层，禁止业务逻辑下沉到 model 层
- Controller 仅转调 service，不含业务逻辑
- Converter 负责 DTO/VO/entity 转换
- 支持后续扩展更多证据子表和接口

## 典型接口示例
```http
POST   /api/v1/evidences/{evidenceId}/attachments
GET    /api/v1/evidences/{evidenceId}/attachments
PUT    /api/v1/evidences/{evidenceId}/attachments
DELETE /api/v1/evidences/{evidenceId}/attachments/{id}
```

## 适用场景
- 证据管理业务开发、接口对接、分层扩展 
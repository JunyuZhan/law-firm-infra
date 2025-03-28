# 知识管理模块数据库迁移说明

## 迁移脚本概述

本目录包含知识管理模块的数据库迁移脚本，使用Flyway进行版本控制和管理。脚本按照版本号顺序执行，确保数据库结构和初始数据的一致性。

## 脚本命名规范

Flyway迁移脚本遵循以下命名规范：`V{版本号}__{描述}.sql`

例如：
- `V1.0.0__create_knowledge_tables.sql` - 创建表结构
- `V1.0.1__insert_initial_data.sql` - 插入初始数据
- `V1.0.2__insert_example_knowledge.sql` - 插入示例知识文档

## 迁移脚本内容

### V1.0.0__create_knowledge_tables.sql
创建知识管理模块所需的数据库表：
- `knowledge` - 知识文档表
- `knowledge_category` - 知识分类表
- `knowledge_tag` - 知识标签表
- `knowledge_tag_relation` - 知识-标签关联表
- `knowledge_attachment` - 知识附件表

### V1.0.1__insert_initial_data.sql
插入基础数据：
- 知识分类初始数据
- 知识标签初始数据

### V1.0.2__insert_example_knowledge.sql
插入示例知识文档数据：
- 示例知识文档
- 示例标签关联
- 示例附件数据

## 数据库迁移执行

系统启动时会自动执行Flyway迁移，也可以通过以下方式手动执行：

```bash
# 使用Maven执行Flyway迁移
mvn flyway:migrate -Dflyway.configFiles=flyway.conf

# 使用Flyway命令行工具执行
flyway -configFiles=flyway.conf migrate
```

## 开发注意事项

1. 新增表结构或修改现有表结构时，应创建新的迁移脚本，版本号递增
2. 不要修改已提交到版本控制系统的迁移脚本
3. 新版本的迁移脚本应考虑向后兼容性
4. 建议在本地测试环境验证迁移脚本后再提交
5. 脚本应包含适当的注释，说明变更目的和内容 
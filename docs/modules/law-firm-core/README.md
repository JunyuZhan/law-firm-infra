# law-firm-core 核心模块

## 模块结构
```
law-firm-core/
├── core-message    // 消息服务模块
├── core-workflow   // 工作流模块
├── core-storage    // 存储服务模块
└── core-search     // 搜索服务模块
```

## 功能说明

### 1. core-message
消息服务模块提供统一的消息处理和分发功能。

主要功能：
- 多渠道消息发送（站内信、邮件、WebSocket）
- 消息模板管理
- 消息限流控制
- 实时消息推送
- 消息历史记录

详细文档：[core-message模块说明](core-message/README.md)

### 2. core-workflow
工作流模块提供业务流程的定义、执行和管理功能。

主要功能：
- 流程定义和部署
- 流程实例管理
- 任务分配和处理
- 流程监控和统计
- 流程历史记录

详细文档：[core-workflow模块说明](core-workflow/README.md)

### 3. core-storage
存储服务模块提供统一的文件存储和管理功能。

主要功能：
- 文件上传和下载
- 文件元数据管理
- 存储策略配置
- 文件访问控制
- 文件版本管理

详细文档：[core-storage模块说明](core-storage/README.md)

### 4. core-search
搜索服务模块提供全文检索和数据索引功能。

主要功能：
- 全文检索
- 索引管理
- 搜索优化
- 结果高亮
- 聚合分析

详细文档：[core-search模块说明](core-search/README.md)

## 技术架构
- Spring Boot 2.7.x
- Spring Cloud Alibaba
- MySQL 8.0
- Redis 6.x
- Elasticsearch 7.x
- RocketMQ 4.x

## 依赖关系
- law-firm-common：基础组件依赖
- law-firm-model：数据模型依赖

## 配置说明
各模块的配置文件位于各自的resources目录下：
```
resources/
├── application.yml          // 基础配置
├── application-dev.yml      // 开发环境配置
└── application-prod.yml     // 生产环境配置
``` 
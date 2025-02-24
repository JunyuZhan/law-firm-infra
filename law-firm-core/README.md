# 核心功能层 (Core Layer)

## 模块说明
核心功能层是律师事务所管理系统的核心业务功能实现层，提供了消息处理、存储管理、工作流引擎和搜索引擎等基础功能支持。该层为上层应用提供了统一的功能接口和服务支持。

## 模块结构
```
law-firm-core/
├── core-message/      # 消息处理模块
├── core-storage/      # 存储模块
├── core-workflow/     # 工作流模块
├── core-search/       # 搜索模块
└── pom.xml           # 父模块POM
```

## 子模块说明

### core-message
消息处理模块，提供统一的消息处理机制：
- 消息发送（同步/异步/延时/批量）
- 消息接收（监听/过滤/路由）
- 消息处理（解析/验证/转换）
- 消息管理（存储/查询/监控）

### core-storage
存储模块，提供统一的文件存储服务：
- 文件上传（单文件/批量/分片）
- 文件下载（单文件/批量/断点续传）
- 文件管理（元数据/目录/权限）
- 存储策略（多存储源/容量管理）

### core-workflow
工作流模块，基于 Flowable 的工作流引擎：
- 流程定义（模型/变量/表单/规则）
- 流程执行（实例/任务/变量）
- 流程管理（部署/监控/历史）
- 流程集成（业务/消息/权限）

### core-search
搜索模块，基于 Elasticsearch 的搜索引擎：
- 索引管理（创建/映射/别名）
- 数据管理（索引/更新/同步）
- 搜索功能（全文/精确/聚合）
- 高级特性（分词/同义词/高亮）

## 技术架构

### 核心依赖
- Spring Boot：应用基础框架
- Spring Cloud：微服务支持
- Flowable：工作流引擎
- Elasticsearch：搜索引擎
- Redis：缓存支持
- RocketMQ：消息队列
- MyBatis Plus：数据访问

### 设计规范

#### 1. 接口设计
- 遵循 RESTful 规范
- 统一的接口格式
- 完整的接口文档
- 版本控制支持
- 错误码规范

#### 2. 服务设计
- 单一职责原则
- 高内聚低耦合
- 接口幂等性
- 服务可测试性
- 性能可伸缩

#### 3. 安全规范
- 访问权限控制
- 数据加密传输
- 敏感信息脱敏
- 操作日志记录
- 安全审计支持

## 开发指南

### 1. 环境准备
```bash
# 安装必要的中间件
docker-compose up -d elasticsearch redis rocketmq
```

### 2. 添加依赖
```xml
<!-- 核心模块依赖 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>core-message</artifactId>
    <version>${project.version}</version>
</dependency>

<!-- 按需引入其他核心模块 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>core-storage</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 3. 配置示例
```yaml
# 应用配置
spring:
  application:
    name: law-firm-core
  
  # 数据源配置
  datasource:
    url: jdbc:mysql://localhost:3306/law_firm
    username: root
    password: root
    
  # Redis配置
  redis:
    host: localhost
    port: 6379
    
  # Elasticsearch配置
  elasticsearch:
    uris: http://localhost:9200
    
# RocketMQ配置
rocketmq:
  name-server: localhost:9876
  producer:
    group: law-firm-producer
```

## 版本说明

### 当前版本
- 版本号：1.0.0
- 发布日期：2024-02-09
- 主要特性：
  - 完整的核心功能支持
  - 统一的接口规范
  - 完善的文档说明

## 注意事项
1. 遵循接口设计规范
2. 注意性能优化
3. 做好容错处理
4. 保证数据安全
5. 完善监控告警

## 模块依赖关系

### 基础依赖
- common-core：核心工具支持
- common-data：数据访问支持
- common-redis：缓存支持
- common-security：安全支持

### 模块间依赖
- core-message → common-message
- core-storage → common-storage
- core-workflow → core-message
- core-search → common-data

## 开发流程

### 1. 功能开发
1. 需求分析
2. 接口设计
3. 编码实现
4. 单元测试
5. 文档更新

### 2. 代码提交
1. 代码审查
2. 测试验证
3. 文档完善
4. 版本发布

## 测试规范

### 单元测试要求
1. 接口测试
   - 参数验证
   - 业务逻辑
   - 异常处理
   
2. 服务测试
   - 功能测试
   - 性能测试
   - 并发测试

3. 集成测试
   - 模块集成
   - 系统集成
   - 性能验证 
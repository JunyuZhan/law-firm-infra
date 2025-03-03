# 律师事务所管理系统 (Law Firm Management System)

## 项目说明
律师事务所管理系统是一套现代化的律所业务管理解决方案，采用分层架构设计，提供了全面的律所业务管理功能。系统采用统一的技术栈和规范，通过清晰的分层结构实现了高内聚低耦合的设计目标。

## 项目架构
系统采用经典的分层架构，主要分为以下几层：
1.依赖版本统一管理层（law-firm-dependencies）
   - 负责依赖版本的统一管理，保证各层的所有模块依赖版本的统一。

2. 基础设施层（law-firm-common）
   - 提供各个层共用的基础设施
   - 包含缓存、安全、日志等通用功能
   - 确保系统的可靠性和性能

3. 数据模型层（law-firm-model）
   - 定义领域模型和数据传输对象
   - 规范数据结构和验证规则
   - 实现领域驱动设计

4. 核心功能层（law-firm-core）
   - 实现核心业务逻辑
   - 提供基础服务支持
   - 处理复杂业务流程

5. 业务模块层（law-firm-modules）
   - 实现具体业务功能
   - 遵循单一职责原则
   - 保持业务逻辑独立

6. API接口层（law-firm-api）
   - 提供统一的接口规范
   - 处理请求响应逻辑
   - 实现接口安全控制

## 项目结构
```
law-firm-infra
├── law-firm-dependencies    # 依赖管理
├── law-firm-common          # 基础设施层
│   ├── common-core         # 核心功能
│   ├── common-cache       # 缓存处理
│   ├── common-data        # 数据访问
│   ├── common-log         # 日志处理
│   ├── common-message     # 消息服务
│   ├── common-security    # 安全框架
│   ├── common-util        # 通用工具
│   ├── common-web         # Web功能
│   └── common-test        # 测试支持
├── law-firm-model          # 数据模型层
│   ├── base-model         # 基础模型
│   ├── auth-model         # 认证模型
│   ├── case-model         # 案件模型
│   └── client-model       # 客户模型
├── law-firm-core           # 核心功能层
│   ├── core-message       # 消息处理
│   ├── core-storage       # 存储管理
│   ├── core-workflow      # 工作流引擎
│   └── core-search        # 搜索引擎
├── law-firm-modules        # 业务模块层
│   ├── law-firm-auth      # 认证授权
│   ├── law-firm-case      # 案件管理
│   ├── law-firm-client    # 客户管理
│   └── law-firm-contract  # 合同管理
├── law-firm-api            # API接口层
│   ├── api-admin          # 管理端API
│   ├── api-lawyer         # 律师端API
│   ├── api-client         # 客户端API
│   └── api-mobile         # 移动端API
└── docs                    # 项目文档
```

## 核心功能

### 1. 基础设施功能
- **缓存处理**：
  * 简单缓存（@SimpleCache）
  * 防重复提交（@RepeatSubmit）
  * 限流控制（@RateLimiter）
  * 缓存预热（@CacheWarmUp）
- **数据访问**：
  * 多数据源配置
  * MyBatis增强
  * 数据审计
  * 多租户支持
- **安全框架**：
  * 认证授权
  * 数据权限控制
  * 操作审计
  * 安全过滤
- **日志处理**：
  * 操作日志
  * 审计日志
  * 性能日志
  * 异常日志

### 2. 业务功能
- 案件管理
- 客户管理
- 合同管理
- 财务管理
- 知识管理
- 档案管理

## 技术栈

### 核心依赖
- Spring Boot 3.2.x
- Spring Cloud 2023.x
- MyBatis Plus 3.5.x - ORM框架，负责数据库交互和数据映射
- Redis 6.x
- Spring Security 6.x
- Swagger 3.0.0
- Elasticsearch 8.0
- RocketMQ 5.0
- Flowable 6.8

### 数据访问层
- **MyBatis Plus**：
  * 默认CRUD操作无需编写SQL
  * 强大的条件构造器
  * 自动分页插件
  * 数据权限控制
  * 多租户支持
  * 代码生成器
- **数据源管理**：
  * 动态数据源配置
  * 主从读写分离
  * 多租户数据隔离
  * 分库分表策略

### 开发环境
- JDK 1.8+
- Maven 3.6+
- Redis 6.x
- MySQL 5.7+
- Docker 20.10+

## 快速开始

### 1. 环境准备
```bash
# 克隆项目
git clone https://github.com/your-org/law-firm-infra.git

# 安装依赖
cd law-firm-infra
mvn clean install

# 启动基础服务（需要Docker环境）
docker-compose -f docker/docker-compose.yml up -d
```

### 2. 配置示例
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/lawfirm
    username: root
    password: root
  
  redis:
    host: localhost
    port: 6379
    
  elasticsearch:
    uris: http://localhost:9200
```

### 3. 使用示例
```java
@Service
public class UserService {
    
    @SimpleCache(timeout = 60, timeUnit = TimeUnit.MINUTES)
    public UserVO getUser(Long id) {
        return userMapper.selectById(id);
    }
    
    @RepeatSubmit(interval = 5)
    public void submitForm(FormDTO form) {
        // 业务处理
    }
}
```

## 开发规范

### 代码规范
1. 遵循阿里巴巴Java开发手册
2. 统一的代码格式化配置
3. 必要的注释和文档

### 提交规范
- feat: 新功能
- fix: 修复问题
- docs: 文档修改
- style: 代码格式修改
- refactor: 代码重构

## 版本说明

### 当前版本
- 版本号：1.1.0
- 发布日期：2024-02-14
- 主要特性：
  - 基础设施层功能完善
  - 业务模块初步实现
  - 微服务架构搭建
  - 完整的测试覆盖

### 版本规划
- 1.2.0：
  - 微服务治理增强
  - 分布式事务支持
  - 更多业务模块接入
- 2.0.0：
  - 云原生架构升级
  - 容器化部署支持
  - AI能力集成

## 文档

### 接口文档
- 员工端：http://localhost:8081/staff/swagger-ui.html
- 管理端：http://localhost:8082/admin/swagger-ui.html
- 移动端：http://localhost:8083/mobile/swagger-ui.html

### 开发文档
详细文档请参考 `docs` 目录：
- 部署指南：docs/deploy-guide.md
- API示例：docs/api-examples.md
- 架构说明：docs/architecture/
- 开发指南：docs/development/
- 运维手册：docs/operation/

## 贡献指南

详细的贡献指南请参考 `CONTRIBUTING.md`。主要步骤：
1. Fork 项目
2. 创建特性分支
3. 提交代码
4. 创建 Pull Request

## 许可证

本项目采用 MIT 许可证 

## 迁移进度

### JPA到MyBatis Plus迁移
- [x] auth-model模块 - 2024-04-26完成 
- [x] system-model模块 - 2024-04-27完成
- [x] document-model模块 - 2024-04-27完成  
- [x] storage-model模块 - 2024-04-28完成
- [x] organization-model模块 - 2024-04-28完成
- [x] personnel-model模块 - 2024-04-28完成
- [x] log-model模块 - 2024-04-28完成
- [x] client-model模块 - 2024-04-28完成
- [x] contract-model模块 - 2024-04-28完成
- [x] case-model模块 - 2024-04-28完成
- [x] finance-model模块 - 2024-04-28完成
- [ ] workflow-model模块
- [ ] search-model模块
- [ ] message-model模块
- [ ] knowledge-model模块
- [ ] ai-model模块 
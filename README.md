# 律师事务所管理系统

## 项目介绍
本项目是一个现代化的律师事务所管理系统，采用微服务架构设计，提供全方位的律所业务管理功能。

## 技术栈
- Spring Boot
- Spring Cloud
- Spring Cloud Alibaba
- Maven

## 项目结构
```
law-firm-infra
├── law-firm-api             # API服务层
│   ├── api-admin           # 管理端API服务
│   ├── api-client          # Feign客户端
│   ├── api-common          # API公共模块
│   ├── api-model           # API数据模型
│   ├── api-lawyer          # 律师端API服务
│   ├── api-clerk           # 文员端API服务
│   ├── api-finance         # 财务端API服务
│   └── api-mini           # 客户端API服务
├── law-firm-common         # 公共组件层
│   ├── common-core         # 核心组件
│   ├── common-web          # Web组件
│   ├── common-log          # 日志组件
│   └── common-security     # 安全组件
└── law-firm-modules        # 业务模块层
    ├── law-firm-auth       # 认证授权
    ├── law-firm-system     # 系统管理
    ├── law-firm-case       # 案件管理
    ├── law-firm-client     # 客户管理
    ├── law-firm-document   # 文档管理
    ├── law-firm-archive    # 档案管理
    ├── law-firm-finance    # 财务管理
    ├── law-firm-analysis   # 数据分析
    ├── law-firm-asset      # 资产管理
    ├── law-firm-conflict   # 利益冲突
    ├── law-firm-message    # 消息通知
    ├── law-firm-meeting    # 会议管理
    ├── law-firm-seal       # 印章管理
    ├── law-firm-schedule   # 日程管理
    ├── law-firm-supplies   # 办公用品
    ├── law-firm-task       # 任务管理
    ├── law-firm-workflow   # 工作流程
    ├── law-firm-personnel  # 人事管理
    ├── law-firm-contract   # 合同管理
    └── law-firm-knowledge  # 知识库
```

## 模块说明

### API服务层 (law-firm-api)
- api-admin：管理端API服务，提供后台管理功能
- api-client：Feign客户端，处理服务间调用
- api-common：API公共模块，包含公共配置和工具
- api-model：API数据模型，定义数据传输对象
- api-lawyer：律师端API服务，提供律师业务功能
- api-clerk：文员端API服务，提供文员业务功能
- api-finance：财务端API服务，提供财务业务功能
- api-mini：客户端API服务，提供客户端功能

### 公共组件层 (law-firm-common)
- common-core：核心组件，包含基础工具类和通用功能
- common-web：Web组件，提供Web相关功能
- common-log：日志组件，统一日志处理
- common-security：安全组件，处理认证授权

### 业务模块层 (law-firm-modules)
- law-firm-auth：认证授权模块，处理用户认证和权限管理
- law-firm-system：系统管理模块，处理系统基础配置
- law-firm-case：案件管理模块，处理案件相关业务
- law-firm-client：客户管理模块，处理客户信息管理
- law-firm-document：文档管理模块，处理文档存储和管理
- law-firm-archive：档案管理模块，处理档案归档和管理
- law-firm-finance：财务管理模块，处理财务相关业务
- law-firm-analysis：数据分析模块，提供数据统计和分析
- law-firm-asset：资产管理模块，处理律所资产管理
- law-firm-conflict：利益冲突模块，处理利益冲突检查
- law-firm-message：消息通知模块，处理系统消息和通知
- law-firm-meeting：会议管理模块，处理会议室预订和管理
- law-firm-seal：印章管理模块，处理印章使用和管理
- law-firm-schedule：日程管理模块，处理日程安排
- law-firm-supplies：办公用品模块，处理办公用品管理
- law-firm-task：任务管理模块，处理任务分配和跟踪
- law-firm-workflow：工作流程模块，处理业务流程管理
- law-firm-personnel：人事管理模块，处理人员信息管理
- law-firm-contract：合同管理模块，处理合同文件管理
- law-firm-knowledge：知识库模块，处理法律知识管理

## 构建说明

### 环境要求
- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+
- Nacos 2.2+

### 构建步骤
1. 克隆项目
```bash
git clone https://github.com/your-username/law-firm-infra.git
```

2. 进入项目目录
```bash
cd law-firm-infra
```

3. 编译项目
```bash
mvn clean install -DskipTests
```

4. 启动服务
- 启动Nacos服务
- 启动Redis服务
- 启动MySQL服务
- 依次启动各个微服务模块

## 开发规范
1. 代码规范遵循阿里巴巴Java开发规范
2. 接口遵循RESTful设计规范
3. 提交代码前需要进行代码格式化
4. 新功能需要编写单元测试
5. 遵循统一的版本管理规范

## 系统架构
- 基于Spring Cloud微服务架构
- 使用Nacos作为注册中心和配置中心
- 采用Spring Security + JWT实现认证授权
- 使用MyBatis-Plus作为ORM框架
- Redis用于缓存和会话管理
- OpenAPI 3.0规范的API文档

## 快速开始

### 环境准备
1. 安装JDK 17
2. 安装Maven 3.8+
3. 安装MySQL 8.0
4. 安装Redis 6.0
5. 安装Nacos 2.2.0

### 本地开发
1. 克隆代码
```bash
git clone https://github.com/your-username/law-firm-infra.git
```

2. 配置数据库
- 创建数据库law_firm
- 执行sql目录下的建表脚本

3. 配置Nacos
- 启动Nacos服务
- 导入配置文件

4. 启动服务
```bash
cd law-firm-api/staff-api
mvn spring-boot:run
```

## API文档访问

### Swagger UI
- 员工端API文档：http://localhost:8081/staff/swagger-ui.html
- 管理端API文档：http://localhost:8082/admin/swagger-ui.html
- 移动端API文档：http://localhost:8083/mobile/swagger-ui.html
- 小程序API文档：http://localhost:8084/mini/swagger-ui.html

### OpenAPI规范
- 员工端OpenAPI文档：http://localhost:8081/staff/v3/api-docs
- 管理端OpenAPI文档：http://localhost:8082/admin/v3/api-docs
- 移动端OpenAPI文档：http://localhost:8083/mobile/v3/api-docs
- 小程序OpenAPI文档：http://localhost:8084/mini/v3/api-docs

### API文档说明
1. API文档按模块分组：
   - 律师端：案件、客户、文档等管理
   - 文员端：任务、日程、档案等管理
   - 财务端：合同、资产、费用等管理

2. 接口说明：
   - 接口描述：说明接口的功能和用途
   - 请求参数：包含参数名称、类型、是否必填、示例值等
   - 响应结果：包含状态码、响应数据结构等
   - 错误码：统一的错误码和错误信息

3. 权限控制：
   - 接口都需要登录认证
   - 根据用户角色进行权限控制
   - 详细的权限说明请参考权限管理文档

## 部署说明

### 环境要求
- CentOS 7.x
- JDK 17
- MySQL 8.0
- Redis 6.0
- Nacos 2.2.0

### 部署步骤
1. 准备环境
2. 配置数据库
3. 配置Nacos
4. 部署服务
5. 配置Nginx

详细部署文档请参考 [部署指南](docs/deploy-guide.md)

## 版本说明
请查看 [CHANGELOG.md](CHANGELOG.md)

## 贡献指南
请查看 [CONTRIBUTING.md](CONTRIBUTING.md)

## 许可证
MIT License 
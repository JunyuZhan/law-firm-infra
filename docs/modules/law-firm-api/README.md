# law-firm-api 接口模块

## 模块结构
```
law-firm-api/
├── api-client     // 客户端API
├── api-admin      // 管理端API
└── api-common     // 公共API定义
```

## 功能说明

### 1. api-client
客户端API提供面向用户的接口服务。

主要功能：
- 用户认证
- 案件管理
- 文档管理
- 消息通知
- 个人中心

详细文档：[api-client模块说明](api-client/README.md)

### 2. api-admin
管理端API提供面向管理员的接口服务。

主要功能：
- 系统管理
- 用户管理
- 权限管理
- 日志管理
- 配置管理

详细文档：[api-admin模块说明](api-admin/README.md)

### 3. api-common
公共API定义提供共享的接口定义和模型。

主要功能：
- 公共接口定义
- 数据传输对象
- 接口响应封装
- 接口文档配置
- 接口安全配置

详细文档：[api-common模块说明](api-common/README.md)

## 技术架构
- Spring Boot 2.7.x
- Spring Cloud OpenFeign
- Swagger/Knife4j
- Spring Security
- JWT

## 依赖关系
- law-firm-common：基础组件依赖
- law-firm-core：核心功能依赖
- law-firm-model：数据模型依赖

## 配置说明
各模块的配置文件位于各自的resources目录下：
```
resources/
├── application.yml           // 基础配置
├── application-dev.yml       // 开发环境配置
├── application-prod.yml      // 生产环境配置
└── swagger.properties       // 接口文档配置
```

## API文档
- 开发环境：http://localhost:8080/doc.html
- 测试环境：http://test-api.lawfirm.com/doc.html
- 生产环境：http://api.lawfirm.com/doc.html 
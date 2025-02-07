# 律所管理系统

## 项目介绍
律所管理系统是一个专门为律师事务所开发的综合管理平台，提供案件管理、客户管理、文档管理、财务管理等功能。

## 系统架构
- 基于Spring Cloud微服务架构
- 使用Nacos作为注册中心和配置中心
- 采用Spring Security + JWT实现认证授权
- 使用MyBatis-Plus作为ORM框架
- Redis用于缓存和会话管理
- OpenAPI 3.0规范的API文档

## 模块说明
- law-firm-api：API服务模块
  - staff-api：员工端API服务
  - admin-api：管理端API服务
  - mobile-api：移动端API服务
  - mini-api：小程序API服务

## 开发环境
- JDK 17
- Maven 3.8+
- MySQL 8.0
- Redis 6.0
- Nacos 2.2.0

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
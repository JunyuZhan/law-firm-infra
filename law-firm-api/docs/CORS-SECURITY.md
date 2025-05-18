# API层跨域安全配置指南

## 1. 跨域安全配置概述

跨域资源共享(CORS)是一种安全机制，允许不同域的网页访问受限资源。在律所管理系统中，我们实现了严格的CORS配置，以确保API接口的安全性。

## 2. 安全配置特点

当前CORS配置具有以下安全特点：

1. **严格限制允许的源域名**：
   - 默认只允许特定的前端域名访问API
   - 生产环境通过环境变量配置允许的域名列表
   - 开发环境允许本地开发服务器域名

2. **限制允许的HTTP方法**：
   - 只允许GET、POST、PUT、DELETE和OPTIONS方法
   - 禁止其他可能导致安全问题的HTTP方法

3. **限制允许的请求头**：
   - 只允许必要的请求头，如Authorization、Content-Type等
   - 减少潜在的攻击面

4. **预检请求缓存**：
   - 设置预检请求的有效期为3600秒，减少OPTIONS请求数量
   - 提高性能并减轻服务器负担

## 3. 环境配置说明

### 3.1 开发环境配置

开发环境允许本地开发服务器域名：

```yaml
cors:
  allowed-origins: ${CORS_ALLOWED_ORIGINS:https://localhost:5666,http://localhost:5666,https://localhost:8000,https://localhost:3000,https://127.0.0.1:5666,http://127.0.0.1:5666,https://127.0.0.1:8000,https://127.0.0.1:3000}
```

这些配置包括：
- 5666端口：vue-vben-admin开发服务器端口
- 8000端口：常见的前端开发服务器端口
- 3000端口：React/Next.js等常见前端框架开发服务器端口

### 3.2 生产环境配置

生产环境只允许特定的前端应用域名：

```yaml
cors:
  allowed-origins: ${CORS_ALLOWED_ORIGINS:https://admin.lawfirm.com,https://app.lawfirm.com}
```

### 3.3 环境变量配置

可以通过环境变量`CORS_ALLOWED_ORIGINS`配置允许的域名列表，多个域名用逗号分隔：

```bash
export CORS_ALLOWED_ORIGINS=https://admin.example.com,https://app.example.com
```

## 4. 安全建议

1. **生产环境不要使用通配符**：
   - 避免使用`*`作为允许的源，这会允许任何域名访问API
   - 始终明确指定允许的前端域名

2. **定期审查CORS配置**：
   - 确保只有必要的域名被允许访问
   - 移除不再使用的域名配置

3. **监控异常请求**：
   - 记录并监控被CORS策略拒绝的请求
   - 对可疑的跨域请求进行调查

4. **结合其他安全措施**：
   - CORS只是安全防御的一部分，应与认证、授权等措施结合使用
   - 考虑实施内容安全策略(CSP)等其他安全标头

## 5. 配置文件位置

- 全局CORS配置：`law-firm-api/src/main/resources/application.yml`
- 开发环境配置：`law-firm-api/src/main/resources/application-dev.yml`
- 生产环境配置：`law-firm-api/src/main/resources/application-prod.yml`
- CORS过滤器实现：`law-firm-api/src/main/java/com/lawfirm/api/config/CorsConfig.java`

## 6. 问题排查

如果遇到CORS相关问题，请检查：

1. 前端请求的Origin是否在允许列表中
2. 请求是否包含自定义头，这些头是否在允许的头列表中
3. 是否使用了不被允许的HTTP方法
4. 浏览器控制台中的CORS错误信息

对于本地开发，如需临时允许更多域名，可修改开发环境配置文件或设置环境变量。 
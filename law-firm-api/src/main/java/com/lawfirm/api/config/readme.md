# 律师事务所管理系统 - 配置目录

本目录包含律师事务所管理系统的配置类，特别是生产环境安全增强配置。

## 生产环境安全配置说明

### 1. 安全配置结构

系统的安全配置分为多个层次：

- **基础安全配置**：由`law-firm-common/common-security`模块提供的通用安全配置
- **认证模块安全配置**：由`law-firm-auth`模块提供的JWT认证/授权配置
- **API模块安全配置**：由API模块提供的环境特定配置和增强

### 2. 重要的安全配置类

| 配置类 | 职责 | 位置 |
|--------|------|------|
| BaseSecurityConfig | 提供基础安全配置 | common-security模块 |
| SecurityConfig | 提供认证模块JWT配置 | law-firm-auth模块 |
| DisableSecurityConfig | 开发环境临时禁用安全 | law-firm-api模块 |
| ApiDocSecurityConfig | API文档安全配置 | law-firm-api模块 |
| SecurityEnhancementConfig | 生产环境安全增强 | law-firm-api模块 |
| ProductionSecurityConfig | 生产环境CORS和安全检查 | law-firm-api模块 |
| DeploymentCheckConfig | 生产环境部署验证 | law-firm-api模块 |

### 3. 生产环境安全增强措施

在生产环境中，系统采取了以下安全增强措施：

#### 3.1 增强的HTTP安全头

- Content-Security-Policy - 限制资源加载
- X-XSS-Protection - 防止XSS攻击
- X-Content-Type-Options - 防止MIME类型嗅探
- X-Frame-Options - 防止点击劫持
- Referrer-Policy - 控制HTTP引用传递

#### 3.2 CORS安全配置

- 限制允许的源(Origins)
- 限制允许的HTTP方法
- 限制允许的HTTP头
- 控制凭据传递

#### 3.3 CSRF保护

- 启用CSRF令牌验证
- 对API文档等特定路径豁免

#### 3.4 部署安全检查

- 验证激活的配置文件
- 检查敏感配置是否通过环境变量设置
- 检查数据库凭据安全性
- 验证日志目录权限
- 记录部署事件

### 4. 环境变量配置

| 环境变量 | 说明 | 示例值 |
|----------|------|--------|
| JWT_SECRET | JWT签名密钥 | 长度至少32字符的随机字符串 |
| DB_USERNAME | 数据库用户名 | law_firm_app |
| DB_PASSWORD | 数据库密码 | 复杂密码 |
| CORS_ALLOWED_ORIGINS | 允许的源 | https://admin.lawfirm.com,https://client.lawfirm.com |
| SERVER_PORT | 服务器端口 | 8443 |
| JASYPT_PASSWORD | 配置加密密钥 | 复杂密钥 |

### 5. 生产环境配置检查清单

- [ ] 使用`prod`配置文件启动应用
- [ ] 通过环境变量注入敏感配置
- [ ] 配置HTTPS/TLS证书
- [ ] 设置非默认端口
- [ ] 配置日志目录权限
- [ ] 限制CORS源为特定域名
- [ ] 启用生产环境审计日志
- [ ] 禁用API文档或配置访问限制

## 开发环境配置

开发环境中可以通过以下设置简化安全配置：

```yaml
dev:
  auth:
    simplified-security: true
```

此设置将激活`DevSecurityFilterConfig`，允许所有请求通过而无需认证。**请勿在生产环境中使用此设置。** 
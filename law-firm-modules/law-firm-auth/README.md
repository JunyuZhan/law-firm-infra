# 认证授权模块 (Law Firm Auth)

## 简介
认证授权模块是律所管理系统的核心安全模块，提供用户认证、授权、权限管理等功能。

## 功能特性
- 多种认证方式支持（用户名密码、短信验证码、邮箱验证码、LDAP）
- 基于 JWT 的无状态会话管理
- 细粒度的权限控制
- 多租户支持
- 完整的用户、角色、权限管理
- 部门和职位管理
- 登录历史记录

## 目录结构
```
law-firm-auth/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── lawfirm/
│       │           └── auth/
│       │               ├── config/           # 配置类
│       │               │   └── SecurityConfig.java    # 安全配置
│       │               ├── controller/       # 控制器
│       │               │   ├── AuthController.java    # 认证相关接口
│       │               │   ├── UserController.java    # 用户管理接口
│       │               │   ├── RoleController.java    # 角色管理接口
│       │               │   ├── PermissionController.java  # 权限管理接口
│       │               │   ├── DepartmentController.java  # 部门管理接口
│       │               │   ├── PositionController.java    # 职位管理接口
│       │               │   └── UserGroupController.java   # 用户组管理接口
│       │               ├── exception/        # 异常处理
│       │               │   └── AuthExceptionHandler.java  # 认证异常处理器
│       │               ├── mapper/          # MyBatis Mapper接口
│       │               ├── security/        # 安全相关实现
│       │               │   ├── provider/    # 认证提供者
│       │               │   │   ├── UsernamePasswordProvider.java
│       │               │   │   ├── SmsCodeProvider.java
│       │               │   │   ├── EmailCodeProvider.java
│       │               │   │   └── LdapProvider.java
│       │               │   ├── handler/     # 处理器
│       │               │   │   ├── LoginSuccessHandler.java
│       │               │   │   ├── LoginFailureHandler.java
│       │               │   │   ├── LogoutSuccessHandlerImpl.java
│       │               │   │   ├── AccessDeniedHandlerImpl.java
│       │               │   │   └── AuthenticationEntryPointImpl.java
│       │               │   ├── filter/      # 过滤器
│       │               │   │   ├── JwtAuthenticationFilter.java
│       │               │   │   ├── CustomLoginFilter.java
│       │               │   │   └── CaptchaFilter.java
│       │               │   ├── token/       # Token相关
│       │               │   │   ├── JwtTokenProvider.java
│       │               │   │   └── TokenStore.java
│       │               │   └── details/     # 用户详情
│       │               │       ├── SecurityUserDetails.java
│       │               │       └── CustomUserDetailsService.java
│       │               └── service/         # 服务实现
│       │                   └── impl/        # 服务实现类
│       └── resources/
│           ├── mapper/           # MyBatis XML映射文件
│           │   ├── UserMapper.xml
│           │   ├── RoleMapper.xml
│           │   ├── PermissionMapper.xml
│           │   ├── DepartmentMapper.xml
│           │   ├── PositionMapper.xml
│           │   ├── UserRoleMapper.xml
│           │   ├── RolePermissionMapper.xml
│           │   ├── UserGroupMapper.xml
│           │   └── LoginHistoryMapper.xml
│           └── application.yml   # 应用配置文件
└── pom.xml                      # 项目依赖管理
```

## 核心功能说明

### 1. 认证功能
- 用户名密码认证
- 短信验证码认证
- 邮箱验证码认证
- LDAP认证
- JWT token管理
- 验证码功能
- 登录历史记录

### 2. 权限管理
- RBAC权限模型
- 角色管理
- 权限管理
- 用户-角色关联
- 角色-权限关联

### 3. 组织架构
- 部门管理
- 职位管理
- 用户组管理

### 4. 安全特性
- 密码加密存储
- 登录失败处理
- 会话管理
- 权限拦截
- XSS防护
- CSRF防护
- SQL注入防护

## 依赖说明
- Spring Boot
- Spring Security
- JWT
- MyBatis Plus
- Redis
- MySQL

## 配置说明
主要配置项在 application.yml 中：
```yaml
law:
  firm:
    security:
      # JWT配置
      jwt:
        secret: your-secret-key
        expiration: 86400000  # 24小时
      # 验证码配置
      captcha:
        enabled: true
        expire: 300  # 5分钟
      # 登录配置
      login:
        retry-limit: 5
        lock-duration: 30  # 30分钟
```

## 使用示例

### 1. 登录认证
```http
POST /api/v1/auth/login
Content-Type: application/json

{
    "username": "admin",
    "password": "password",
    "captcha": "1234",
    "captchaKey": "xxx"
}
```

### 2. 获取用户信息
```http
GET /api/v1/users/current
Authorization: Bearer your-token
```

### 3. 创建角色
```http
POST /api/v1/roles
Authorization: Bearer your-token
Content-Type: application/json

{
    "name": "管理员",
    "code": "ADMIN",
    "permissions": [1, 2, 3]
}
```

## 开发指南

### 1. 添加新的认证方式
1. 创建新的认证提供者类，实现 AuthenticationProvider 接口
2. 在 SecurityConfig 中注册新的认证提供者
3. 添加相应的认证过滤器（如果需要）

### 2. 添加新的权限
1. 在数据库中添加权限记录
2. 更新角色-权限关联
3. 在代码中使用 @PreAuthorize 注解或其他方式进行权限控制

### 3. 自定义认证逻辑
1. 继承或修改相应的认证处理器
2. 在 SecurityConfig 中配置自定义的处理器

## 注意事项
1. 所有密码必须加密存储
2. 注意防止SQL注入和XSS攻击
3. 敏感操作需要进行权限校验
4. 注意多租户数据隔离
5. 定期清理过期的登录历史记录

## 更新日志
- 2024-03-06: 完善认证授权功能，添加多种认证方式支持
- 2024-03-05: 初始化项目结构，实现基础认证功能
# 认证授权模块 (Law Firm Auth)

## 模块概述
律师事务所管理系统认证授权模块 (Law Firm Auth) 是系统的安全核心，负责实现用户认证、权限验证和安全控制。本模块基于 auth-model 定义的数据模型和接口，提供完整的安全实现。作为系统安全的门户，law-firm-auth 采用严格的松耦合设计，确保职责清晰分离。

### 与 auth-model 的关系
law-firm-auth 与 auth-model 的关系是实现与定义的关系：
- **auth-model**：提供认证授权的核心数据模型、实体定义和服务接口
- **law-firm-auth**：基于 auth-model 实现具体的认证授权流程和安全控制

本模块严格遵循 auth-model 定义的模型和接口，不扩展额外的数据结构，保持数据层与实现层的清晰分离。

## 核心功能

### 1. 认证体系
- **用户名密码认证**：传统登录方式，支持多种密码策略
- **登录安全**：多次失败锁定、验证码防护
- **会话管理**：基于JWT的无状态会话，支持会话过期策略
- **登录历史记录**：完整记录用户的登录历史，支持审计和异常检测

### 2. 授权体系
- **RBAC权限模型**：基于角色的访问控制
- **团队权限管理**：针对团队级别的权限控制和资源访问
- **业务权限管理**：针对具体业务场景的细粒度权限管理
- **权限请求流程**：支持用户申请权限的审批流程
- **数据权限**：支持全部数据、团队数据、个人数据和自定义数据范围
- **操作权限**：完全权限、只读权限、个人权限、审批权限和申请权限
- **权限控制**：实现 auth-model 中定义的 PermissionChecker 接口

### 3. 安全特性
- **防XSS攻击**：对请求参数和响应内容进行XSS过滤
- **防CSRF攻击**：支持CSRF Token验证
- **审计日志**：记录安全操作日志
- **敏感数据脱敏**：手机号、邮箱等敏感信息自动脱敏

## 技术架构
本模块采用简洁的分层架构：
```
┌─────────────────────────────────────────────────────────┐
│                     控制器层 (Controller)                 │
│  认证控制器、用户控制器、角色控制器、权限控制器            │
└───────────────────────────┬─────────────────────────────┘
                            │
┌───────────────────────────┼─────────────────────────────┐
│                     服务层 (Service)                      │
│   认证服务、用户服务、角色服务、权限服务                  │
│   团队权限服务、业务权限服务、权限请求服务、登录历史服务  │
└───────────────────────────┼─────────────────────────────┘
                            │
┌───────────────────────────┼─────────────────────────────┐
│                    安全层 (Security)                      │
│     安全过滤器、认证处理器、JWT提供者、安全用户详情       │
└─────────────────────────────────────────────────────────┘
```

## 目录结构（简化且保留文件名）

```plaintext
law-firm-auth/
├── config/
│   ├── SecurityConfig.java
│   ├── SimpleSecurityConfig.java
│   ├── LoginHistoryServiceConfig.java
│   ├── AuthMybatisConfig.java
│   └── AuthAutoConfiguration.java
├── controller/
│   ├── AuthController.java
│   ├── UserController.java
│   ├── RoleController.java
│   └── PermissionController.java
├── entity/
│   └── LoginHistory.java
├── exception/
│   ├── AuthExceptionHandler.java
│   └── AuthException.java
├── security/
│   ├── JwtAuthenticationFilter.java
│   ├── JsonLoginFilter.java
│   ├── LoginSuccessHandler.java
│   ├── LoginFailureHandler.java
│   ├── LogoutHandler.java
│   ├── JwtTokenProvider.java
│   ├── CustomAuthenticationProvider.java
│   ├── SecurityUserDetails.java
│   ├── AuthorizationDaoImpl.java
│   ├── AuthorizationConfig.java
│   └── （如有 context 相关类也放这里）
├── service/
│   ├── AuthServiceImpl.java
│   ├── UserServiceImpl.java
│   ├── RoleServiceImpl.java
│   ├── PermissionServiceImpl.java
│   ├── TeamPermissionServiceImpl.java
│   ├── BusinessPermissionServiceImpl.java
│   ├── PermissionRequestServiceImpl.java
│   ├── LoginHistoryServiceImpl.java
│   ├── UserPersonnelServiceImpl.java
│   └── PermissionCheckerImpl.java
├── utils/
│   ├── SecurityUtils.java
│   └── PasswordUtils.java
└── resources/
    ├── application.yml
    ├── application-auth.yml
    └── META-INF/db/migration/
        ├── V2001__init_auth_tables.sql
        ├── V2002__init_auth_data.sql
        └── V2000__add_auth_module_responsibility_notes.sql
```

## 数据库迁移脚本
- 所有表结构、初始数据、权限数据等均通过 Flyway/Liquibase 规范化管理，脚本位于 `src/main/resources/META-INF/db/migration/`。
- 主要脚本：
  - `V2001__init_auth_tables.sql`：认证、用户、角色、权限等核心表结构
  - `V2002__init_auth_data.sql`：初始化基础数据、字典、权限等
  - `V2000__add_auth_module_responsibility_notes.sql`：模块责任说明

## 关键组件说明

### 1. 服务实现
- **AuthServiceImpl**：实现 auth-model 中定义的 AuthService 接口，处理登录、登出、令牌刷新等功能
- **UserServiceImpl**：实现 auth-model 中定义的 UserService 接口，处理用户管理功能
- **RoleServiceImpl**：实现 auth-model 中定义的 RoleService 接口，处理角色管理功能
- **PermissionServiceImpl**：实现 auth-model 中定义的 PermissionService 接口，处理权限管理功能
- **TeamPermissionServiceImpl**：处理团队级别的权限分配和管理
- **BusinessPermissionServiceImpl**：处理特定业务场景的权限管理
- **PermissionRequestServiceImpl**：处理权限申请和审批流程
- **LoginHistoryServiceImpl**：记录和管理用户登录历史
- **UserPersonnelServiceImpl**：实现 auth-model 中定义的 UserPersonnelService 接口，处理用户与人员的关联

### 2. 安全组件
- **JwtAuthenticationFilter**：JWT认证过滤器，拦截请求并验证JWT令牌
- **JsonLoginFilter**：处理JSON格式的登录请求
- **JwtTokenProvider**：JWT令牌提供者，负责令牌的创建、验证和解析
- **SecurityUserDetails**：安全用户详情，实现 Spring Security 的 UserDetails 接口

### 3. 权限检查
- **PermissionCheckerImpl**：实现 auth-model 中定义的 PermissionChecker 接口，提供权限检查功能

## 与其他模块的交互

### 与 auth-model 的交互
- 直接使用 auth-model 中定义的实体类和接口
- 实现 AuthService、UserService、RoleService、PermissionService 等接口
- 通过 AuthConstants 使用统一定义的常量

### 与 personnel-model 的交互
- 通过实现 UserPersonnelService 接口与人事模块交互
- 处理用户与员工的绑定关系

### 与 organization-model 的交互
- 通过组织模块提供的接口获取组织架构信息
- 处理基于组织结构的数据权限

### 与 common 通用层的交互
本模块大量复用了 common 通用层提供的功能，避免重复实现，保持系统一致性：

- **common-util**：使用通用工具类处理字符串、日期、加密解密等操作
  ```java
  // 示例：使用 StringUtils 处理字符串
  if (StringUtils.isBlank(username)) {
      throw new IllegalArgumentException("用户名不能为空");
  }
  ```

- **common-core**：使用核心功能如基础异常定义、通用响应对象
  ```java
  // 示例：使用通用响应对象
  return Result.success(userVO);
  ```

- **common-cache**：使用缓存功能存储令牌、验证码等临时数据
  ```java
  // 示例：使用缓存存储验证码
  cacheService.set(CacheKeys.CAPTCHA_KEY + captchaKey, captcha, Duration.ofMinutes(5));
  ```

- **common-security**：使用安全相关功能如密码加密、安全工具类
  ```java
  // 示例：使用密码编码器加密密码
  String encodedPassword = passwordEncoder.encode(rawPassword);
  ```

- **common-web**：使用Web相关功能如请求处理、响应封装
  ```java
  // 示例：使用请求上下文获取当前用户
  Long currentUserId = RequestContext.getCurrentUserId();
  ```

## 安全配置说明

### JWT配置
```yaml
law:
  firm:
    security:
      jwt:
        secret: ${JWT_SECRET:HmacSHA256SecretKey}
        expiration: ${JWT_EXPIRATION:86400000}
        refresh-expiration: ${JWT_REFRESH_EXPIRATION:604800000}
        issuer: law-firm-auth
        audience: law-firm-web
```

### 密码策略配置
```yaml
law:
  firm:
    security:
      password:
        min-length: 8
        require-digit: true
        require-lowercase: true
        require-uppercase: true
        require-special-char: true
        max-age-days: 90
        history-count: 5
```

### 登录安全配置
```yaml
law:
  firm:
    security:
      login:
        max-fail-times: 5
        lock-minutes: 30
        captcha-enabled: true
        captcha-expire-minutes: 5
```

## 开发指南

### 1. 实现认证服务
```java
@Service
public class AuthServiceImpl implements AuthService {
    
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 实现登录逻辑
    }
    
    @Override
    public void logout(String username) {
        // 实现登出逻辑
    }
    
    @Override
    public TokenDTO refreshToken(String refreshToken) {
        // 实现令牌刷新逻辑
    }
}
```

### 2. 实现权限检查
```java
@Service
public class PermissionCheckerImpl implements PermissionChecker {
    
    @Override
    public boolean hasPermission(Long userId, String moduleCode, OperationTypeEnum operationType) {
        // 实现权限检查逻辑
    }
    
    @Override
    public DataScopeEnum getDataScope(Long userId, String moduleCode) {
        // 实现数据范围获取逻辑
    }
}
```

### 3. 使用权限检查
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private PermissionChecker permissionChecker;
    
    @GetMapping("/{id}")
    public ResponseEntity<UserVO> getUser(@PathVariable Long id) {
        // 检查权限
        if (!permissionChecker.hasPermission(getCurrentUserId(), "user", OperationTypeEnum.READ_ONLY)) {
            throw new AccessDeniedException("没有查看用户的权限");
        }
        
        // 获取数据范围
        DataScopeEnum dataScope = permissionChecker.getDataScope(getCurrentUserId(), "user");
        
        // 根据数据范围处理业务逻辑
        // ...
    }
}
```

### 4. 配置安全过滤器
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/auth/login", "/auth/captcha").permitAll()
                .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
}
```

## 与业务模块的关系

law-firm-auth 模块作为律所系统的安全与权限中枢，与所有业务模块（如案件、合同、档案、财务、知识等）存在紧密关联，主要体现在：

- **统一认证**：所有业务模块的接口访问、页面操作均需通过auth模块的认证（如JWT），实现单点登录和用户身份识别。
- **权限校验**：业务模块在数据操作、流程审批、页面展示等环节，均通过auth模块的权限体系（如RBAC、数据范围、操作权限）进行细粒度控制。
- **数据隔离**：auth模块提供的数据权限能力，确保不同用户、团队、部门只能访问和操作授权范围内的数据。
- **用户与角色信息**：业务模块通过auth模块获取当前用户、角色、组织等基础信息，用于业务流转、审批、归档等场景。
- **安全审计与消息通知**：业务模块的关键操作（如审批、归档、借阅、删除等）通过core层的审计、消息服务进行日志记录和通知，auth模块提供用户、权限等基础支撑。

### 典型集成方式
- 业务模块通过依赖`auth-model`、`common-security`等，直接调用auth模块的接口或服务。
- 通过JWT等token机制，业务模块在每次请求时都能获取当前用户身份和权限信息。
- 通过Spring Security的全局拦截和方法级注解（如@PreAuthorize），实现细粒度的权限控制。

### 示例
- 案件归档、合同审批、档案借阅等业务操作前，先通过auth模块校验当前用户是否有相应操作权限和数据访问范围。
- 业务模块的"创建人"、"经办人"、"审批人"等字段，均通过auth模块获取用户信息。
- 业务模块的操作日志、通知推送等，均依赖auth模块的用户、角色、权限等基础能力。
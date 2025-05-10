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

## 目录结构
```plaintext
law-firm-auth/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── lawfirm/
│       │           └── auth/
│       │               ├── config/            # 配置类
│       │               │   ├── SecurityConfig.java        # 安全配置
│       │               │   ├── CorsConfig.java            # 跨域配置
│       │               │   └── RedisConfig.java           # Redis配置
│       │               ├── controller/        # 控制器
│       │               │   ├── AuthController.java        # 认证控制器
│       │               │   ├── UserController.java        # 用户控制器
│       │               │   ├── RoleController.java        # 角色控制器
│       │               │   └── PermissionController.java  # 权限控制器
│       │               ├── exception/         # 异常处理
│       │               │   ├── GlobalExceptionHandler.java  # 全局异常处理器
│       │               │   └── AuthException.java           # 认证授权异常
│       │               ├── security/          # 安全相关
│       │               │   ├── filter/          # 过滤器
│       │               │   │   ├── JwtAuthenticationFilter.java   # JWT认证过滤器
│       │               │   │   └── JsonLoginFilter.java           # JSON登录过滤器
│       │               │   ├── handler/         # 处理器
│       │               │   │   ├── LoginSuccessHandler.java       # 登录成功处理器
│       │               │   │   ├── LoginFailureHandler.java       # 登录失败处理器
│       │               │   │   └── LogoutHandler.java             # 登出处理器
│       │               │   ├── provider/        # 提供者
│       │               │   │   ├── JwtTokenProvider.java          # JWT令牌提供者
│       │               │   │   └── CustomAuthenticationProvider.java  # 自定义认证提供者
│       │               │   └── details/         # 用户详情
│       │               │       └── SecurityUserDetails.java       # 安全用户详情
│       │               ├── service/           # 服务层
│       │               │   ├── impl/            # 服务实现
│       │               │   │   ├── AuthServiceImpl.java         # 认证服务实现
│       │               │   │   ├── UserServiceImpl.java         # 用户服务实现
│       │               │   │   ├── RoleServiceImpl.java         # 角色服务实现
│       │               │   │   ├── PermissionServiceImpl.java   # 权限服务实现
│       │               │   │   ├── TeamPermissionServiceImpl.java # 团队权限服务实现
│       │               │   │   ├── BusinessPermissionServiceImpl.java # 业务权限服务实现
│       │               │   │   ├── PermissionRequestServiceImpl.java # 权限请求服务实现
│       │               │   │   ├── LoginHistoryServiceImpl.java  # 登录历史服务实现
│       │               │   │   └── UserPersonnelServiceImpl.java # 用户人员关联服务实现
│       │               │   └── support/         # 支持服务
│       │               │       └── PermissionCheckerImpl.java   # 权限检查器实现
│       │               └── utils/             # 工具类
│       │                   ├── SecurityUtils.java              # 安全工具类
│       │                   └── PasswordUtils.java              # 密码工具类
│       └── resources/
│           └── application.yml     # 应用配置
```

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
# 认证授权模块 (Auth Module)

## 模块说明
认证授权模块是律师事务所管理系统的用户认证和权限管理模块，负责系统的用户管理、角色管理、权限管理、认证授权等核心安全功能。该模块基于 Spring Security 和 JWT 实现，提供了完整的认证授权解决方案。

## 核心功能

### 1. 用户认证
- 账号密码登录
- 手机验证码登录
- 第三方登录集成
- 单点登录(SSO)
- 会话管理

### 2. 权限管理
- 基于角色的访问控制(RBAC)
- 权限分配与回收
- 权限继承与传递
- 动态权限控制
- 数据权限过滤

### 3. 安全管理
- 密码安全策略
- 登录失败处理
- 账号锁定策略
- 操作日志审计
- 安全事件监控

### 4. 系统集成
- JWT令牌管理
- OAuth2.0支持
- 统一认证中心
- API权限控制
- 跨域安全配置

## 核心组件

### 1. 认证服务
- AuthenticationService：认证服务接口
- UserDetailsService：用户详情服务
- TokenService：令牌服务
- SessionService：会话服务
- SecurityContextHolder：安全上下文

### 2. 权限服务
- AuthorizationService：授权服务接口
- RoleService：角色服务
- PermissionService：权限服务
- ResourceService：资源服务
- DataScopeService：数据范围服务

### 3. 安全组件
- SecurityConfig：安全配置
- JwtAuthenticationFilter：JWT认证过滤器
- CustomAuthenticationProvider：自定义认证提供者
- SecurityUtils：安全工具类
- PasswordEncoder：密码编码器

### 4. 监控审计
- SecurityMonitor：安全监控
- AuditLogger：审计日志
- LoginAttemptService：登录尝试服务
- SecurityMetrics：安全指标
- AlertNotifier：告警通知

## 使用示例

### 1. 用户登录
```java
@Autowired
private AuthenticationService authService;

public LoginResult login(LoginRequest request) {
    // 创建认证令牌
    UsernamePasswordToken token = new UsernamePasswordToken(
        request.getUsername(),
        request.getPassword()
    );
    
    // 执行认证
    Authentication auth = authService.authenticate(token);
    
    // 生成JWT令牌
    String jwt = tokenService.generateToken(auth);
    
    return new LoginResult(jwt);
}
```

### 2. 权限检查
```java
@PreAuthorize("hasPermission('case', 'read')")
public CaseDTO getCase(Long caseId) {
    // 获取当前用户
    User currentUser = SecurityUtils.getCurrentUser();
    
    // 检查数据权限
    dataScopeService.checkPermission(caseId, currentUser);
    
    return caseService.getCase(caseId);
}
```

### 3. 角色管理
```java
@Autowired
private RoleService roleService;

public void assignRole(Long userId, String roleCode) {
    // 创建角色分配
    RoleAssignment assignment = new RoleAssignment()
        .setUserId(userId)
        .setRoleCode(roleCode)
        .setGrantedBy(SecurityUtils.getCurrentUser().getId());
    
    // 分配角色
    roleService.assignRole(assignment);
}
```

## 配置说明

### 1. 安全配置
```yaml
security:
  # JWT配置
  jwt:
    secret: ${JWT_SECRET}
    expiration: 86400
    
  # 认证配置
  auth:
    login-url: /api/auth/login
    logout-url: /api/auth/logout
    permit-urls:
      - /api/auth/captcha
      - /api/auth/register
      
  # 密码策略
  password:
    min-length: 8
    require-special-char: true
    expire-days: 90
```

### 2. 权限配置
```yaml
authorization:
  # 角色配置
  role:
    super-admin: ROLE_SUPER_ADMIN
    default-role: ROLE_USER
    
  # 权限配置
  permission:
    case-manage: case:*
    client-manage: client:*
    contract-manage: contract:*
```

## 注意事项
1. 密码安全存储
2. 令牌有效期管理
3. 敏感操作授权
4. 权限粒度控制
5. 安全日志审计 
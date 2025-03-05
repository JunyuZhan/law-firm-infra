# 认证授权模块 (Law Firm Auth)

## 一、模块说明
认证授权模块(law-firm-auth)是整个系统的基础支撑模块，负责用户认证、权限管理、安全控制等核心功能。本模块基于auth-model模块定义的接口和实体，提供完整的认证授权实现。

## 二、技术选型
- 核心框架：Spring Security 6.x + JWT
- 数据访问：MyBatis Plus 3.5.x
- 数据存储：MySQL + Redis
- 第三方集成：
  - OAuth2
  - LDAP
  - 短信服务
  - 邮件服务

## 三、依赖关系

```
law-firm-auth
    ├── law-firm-model/auth-model  # 依赖认证授权模型定义
    ├── law-firm-core              # 依赖核心功能模块
    └── law-firm-common            # 依赖通用功能模块
```

## 四、目录结构设计

```
law-firm-auth/
├── src/main/java/com/lawfirm/auth/
│   ├── config/                   # 配置类
│   │   ├── SecurityConfig.java   # 安全配置
│   │   ├── MybatisPlusConfig.java # MyBatis Plus配置
│   │   ├── AuthAutoConfiguration.java # 自动配置类
│   │   └── CorsConfig.java       # 跨域配置
│   │
│   ├── controller/               # 控制层
│   │   ├── AuthController.java   # 认证控制器
│   │   ├── UserController.java   # 用户控制器
│   │   ├── RoleController.java   # 角色控制器
│   │   ├── PermissionController.java # 权限控制器
│   │   ├── DepartmentController.java # 部门控制器
│   │   ├── PositionController.java # 职位控制器
│   │   └── UserGroupController.java # 用户组控制器
│   │
│   ├── mapper/                   # 数据访问层实现
│   │   ├── UserMapperImpl.java   # 用户数据访问实现
│   │   ├── RoleMapperImpl.java   # 角色数据访问实现
│   │   ├── PermissionMapperImpl.java # 权限数据访问实现
│   │   ├── UserRoleMapperImpl.java   # 用户角色关联数据访问实现
│   │   ├── RolePermissionMapperImpl.java # 角色权限关联数据访问实现
│   │   ├── DepartmentMapperImpl.java # 部门数据访问实现
│   │   ├── PositionMapperImpl.java # 职位数据访问实现
│   │   ├── UserGroupMapperImpl.java # 用户组数据访问实现
│   │   └── LoginHistoryMapperImpl.java # 登录历史数据访问实现
│   │
│   ├── service/                  # 服务层实现
│   │   ├── impl/                # 服务实现
│   │   │   ├── UserServiceImpl.java     # 用户服务实现
│   │   │   ├── RoleServiceImpl.java     # 角色服务实现
│   │   │   └── PermissionServiceImpl.java # 权限服务实现
│   │   └── support/               # 服务支持类
│   │       ├── UserSupport.java
│   │       ├── RoleSupport.java
│   │       └── PermissionSupport.java
│   │
│   ├── security/                 # 安全组件
│   │   ├── filter/               # 安全过滤器
│   │   │   ├── JwtAuthenticationFilter.java
│   │   │   ├── CaptchaFilter.java
│   │   │   └── CustomLoginFilter.java
│   │   ├── handler/              # 安全处理器
│   │   │   ├── LoginSuccessHandler.java
│   │   │   └── LoginFailureHandler.java
│   │   ├── token/                # 令牌相关
│   │   │   ├── JwtTokenProvider.java
│   │   │   └── TokenStore.java
│   │   ├── details/              # 安全详情实现
│   │   │   ├── CustomUserDetailsService.java
│   │   │   └── SecurityUser.java
│   │   └── provider/            # 认证提供者
│   │       ├── UsernamePasswordProvider.java
│   │       ├── SmsCodeProvider.java
│   │       ├── EmailCodeProvider.java
│   │       └── LdapProvider.java
│   │
│   ├── exception/                # 异常处理
│   │   ├── AuthExceptionHandler.java
│   │   └── InvalidTokenException.java
│   │
│   └── util/                     # 工具类
│       ├── SecurityUtils.java
│       └── PasswordUtils.java
└── src/main/resources/
    ├── mapper/                   # MyBatis XML映射文件
    │   ├── UserMapper.xml
    │   ├── RoleMapper.xml
    │   ├── PermissionMapper.xml
    │   ├── UserRoleMapper.xml
    │   ├── RolePermissionMapper.xml
    │   ├── DepartmentMapper.xml
    │   ├── PositionMapper.xml
    │   ├── UserGroupMapper.xml
    │   └── LoginHistoryMapper.xml
    └── application.yml           # 应用配置
```

## 五、功能模块设计

### 1. 用户认证模块

#### 1.1 认证流程
1. 用户提交登录凭证（用户名密码/短信验证码/邮箱验证码/LDAP等）
2. 系统根据登录类型选择对应的认证提供者进行验证
3. 验证通过后生成JWT令牌
4. 记录登录历史
5. 返回令牌和用户信息

#### 1.2 多因素认证
1. 支持基础认证+二次验证的多因素认证
2. 高风险操作要求二次验证
3. 支持MFA配置，可按用户类型指定认证要求

#### 1.3 令牌管理
1. 令牌生成与验证
2. 令牌刷新机制
3. 令牌撤销与黑名单

### 2. 权限管理模块

#### 2.1 RBAC模型实现
1. 用户-角色-权限三层模型
2. 支持角色继承
3. 支持权限分类（菜单权限、操作权限、数据权限）

#### 2.2 权限验证
1. URL访问控制
2. 方法级别权限控制（@PreAuthorize）
3. 前端菜单和按钮权限控制

#### 2.3 数据权限
1. 基于SQL过滤的数据权限实现
2. 支持按部门、职位、用户组等维度控制数据可见性
3. 支持自定义数据权限规则

### 3. 组织架构模块

#### 3.1 部门管理
1. 树形部门结构管理
2. 部门人员管理
3. 部门领导设置

#### 3.2 职位管理
1. 职位定义与管理
2. 职位与部门关联
3. 职位等级体系

#### 3.3 用户组管理
1. 用户组创建与管理
2. 用户组成员管理
3. 用户组权限设置

## 六、API接口设计

### 1. 认证接口
```
POST /auth/login            # 用户登录
POST /auth/logout           # 用户登出
POST /auth/refresh          # 刷新Token
POST /auth/captcha          # 获取图形验证码
POST /auth/sms/send         # 发送短信验证码
POST /auth/email/verify     # 发送邮箱验证链接
POST /auth/password/reset   # 重置密码
```

### 2. 用户管理接口
```
POST   /users               # 创建用户
PUT    /users/{id}          # 更新用户
DELETE /users/{id}          # 删除用户
GET    /users/{id}          # 获取用户详情
GET    /users               # 查询用户列表(分页)
PUT    /users/{id}/status   # 修改用户状态
PUT    /users/{id}/password # 修改用户密码
PUT    /users/{id}/roles    # 分配用户角色
```

### 3. 角色管理接口
```
POST   /roles               # 创建角色
PUT    /roles/{id}          # 更新角色
DELETE /roles/{id}          # 删除角色
GET    /roles/{id}          # 获取角色详情
GET    /roles               # 查询角色列表
PUT    /roles/{id}/permissions  # 设置角色权限
GET    /roles/{id}/permissions  # 获取角色权限
```

### 4. 权限管理接口
```
POST   /permissions         # 创建权限
PUT    /permissions/{id}    # 更新权限
DELETE /permissions/{id}    # 删除权限
GET    /permissions/{id}    # 获取权限详情
GET    /permissions         # 查询权限列表
GET    /permissions/tree    # 获取权限树结构
```

### 5. 部门管理接口
```
POST   /departments         # 创建部门
PUT    /departments/{id}    # 更新部门
DELETE /departments/{id}    # 删除部门
GET    /departments/{id}    # 获取部门详情
GET    /departments         # 查询部门列表
GET    /departments/tree    # 获取部门树结构
```

### 6. 职位管理接口
```
POST   /positions           # 创建职位
PUT    /positions/{id}      # 更新职位
DELETE /positions/{id}      # 删除职位
GET    /positions/{id}      # 获取职位详情
GET    /positions           # 查询职位列表
```

### 7. 用户组管理接口
```
POST   /user-groups         # 创建用户组
PUT    /user-groups/{id}    # 更新用户组
DELETE /user-groups/{id}    # 删除用户组
GET    /user-groups/{id}    # 获取用户组详情
GET    /user-groups         # 查询用户组列表
PUT    /user-groups/{id}/users # 设置用户组成员
GET    /user-groups/{id}/users # 获取用户组成员
```

## 七、安全配置

### 1. Spring Security配置
```java
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final AuthProperties authProperties;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/login", "/auth/captcha", "/auth/refresh").permitAll()
                .requestMatchers("/users/**").hasRole("ADMIN")
                .requestMatchers("/roles/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

### 2. 防护措施
1. 防XSS攻击
2. 防CSRF攻击
3. 防SQL注入
4. 防暴力破解
5. 接口频率限制

## 八、性能优化

### 1. 缓存策略
1. 用户信息缓存
2. 权限数据缓存
3. 令牌缓存

### 2. 查询优化
1. 合理使用索引
2. 复杂查询优化
3. 分页查询性能优化

### 3. 并发控制
1. 分布式锁
2. 幂等设计

## 九、集成测试

### 1. 单元测试
```java
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
    
    @Autowired
    private UserService userService;
    
    @Test
    public void testCreateUser() {
        // 测试创建用户
    }
}
```

### 2. 接口测试
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void testLoginSuccess() {
        // 测试用户登录成功
    }
}
```

## 十、部署与配置

### 1. 配置项
```yaml
lawfirm:
auth:
    jwt:
      secret: your-jwt-secret
      access-token-expire-minutes: 30
      refresh-token-expire-days: 7
  security:
      max-login-failure-times: 5
      password-expire-days: 90
      captcha-enabled: true
    ldap:
      enabled: false
      url: ldap://your-ldap-server:389
      base-dn: dc=example,dc=com
    sms:
      enabled: true
      expire-minutes: 5
```

### 2. 部署要求
1. JDK 17+
2. MySQL 8.0+
3. Redis 6.x+
4. 配置文件加密保护

## 十一、常见问题

1. **Q: 如何处理密码安全存储?**
   A: 使用BCrypt加密算法，保证即使数据库被泄露，密码也不会明文暴露。

2. **Q: 如何防止JWT Token被盗用?**
   A: 设置较短的过期时间，实现黑名单机制，结合IP和设备指纹进行二次校验。

3. **Q: 权限粒度如何控制?**
   A: 系统采用"角色-权限"二级模型，可以灵活配置到按钮级别的权限控制。
   
4. **Q: 如何实现多租户隔离?**
   A: 实体继承TenantEntity，所有数据访问自动增加租户过滤条件。

5. **Q: 如何防止暴力破解?**
   A: 实现登录失败次数限制，超过阈值后账号临时锁定或要求二次验证。
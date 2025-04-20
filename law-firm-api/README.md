# Law Firm API 层设计文档

## 1. 设计目标

API 层作为系统的统一入口，主要承担以下职责：
- 统一接口规范
- 统一响应格式
- 统一异常处理
- 统一安全控制
- 统一接口文档
- 支持 vue-vben-admin 前端框架

## 2. 技术架构

### 2.1 核心组件
- Spring Boot Web：提供 RESTful API 支持
- SpringDoc OpenAPI：API 文档生成
- Spring Validation：参数校验
- Lombok：简化代码
- MyBatis Plus：ORM框架
- Redis：缓存支持
- JWT：认证支持

### 2.2 依赖关系
- 依赖所有业务模块（law-firm-modules）
- 依赖通用组件（law-firm-common）
  - common-core：核心工具类、常量、异常等
  - common-web：Web相关通用组件
  - common-security：安全相关通用组件
  - common-data：数据处理相关通用组件
- 不依赖基础设施层
- 不依赖持久层

### 2.3 代码复用策略

API 层应尽量复用底层通用组件，避免重复实现。主要复用以下内容：

1. **通用工具类**：
   - 日期处理
   - 字符串处理
   - 加密解密
   - JSON 转换等

2. **公共常量定义**：
   - 错误码
   - 业务状态码
   - 系统参数等

3. **基础数据模型**：
   - 实体类定义
   - DTO/VO 对象
   - 枚举类型等

4. **公共异常类**：
   - 业务异常
   - 系统异常
   - 安全异常等

5. **公共组件**：
   - 分页组件
   - 缓存组件
   - 日志组件等

## 3. 包结构设计

```
com.lawfirm.api
├── advice                    // 通知组件
│   └── ResponseAdvice.java   // 响应格式转换通知
├── config                    // 配置类
│   ├── SecurityConfig.java   // 安全配置
│   ├── WebMvcConfig.java     // MVC配置
│   ├── CorsConfig.java       // 跨域配置
│   ├── RedisConfig.java      // Redis配置
│   └── SwaggerConfig.java    // API文档配置
├── controller                // 特定控制器
│   ├── LoginController.java  // 登录适配控制器
│   └── MenuController.java   // 菜单转换控制器
├── filter                    // 过滤器
│   ├── JwtAuthenticationFilter.java // JWT认证过滤器
│   └── XssFilter.java        // XSS防护过滤器
├── handler                   // 处理器
│   └── GlobalExceptionHandler.java // 全局异常处理器
├── interceptor               // 拦截器
│   ├── ResponseWrapperInterceptor.java // 响应包装拦截器
│   └── SecurityInterceptor.java        // 安全拦截器
└── LawFirmApiApplication.java
```

### 3.1 复用与扩展
- **复用组件**：直接使用 common 层提供的工具类、异常类和常量定义
- **扩展组件**：当 common 层不满足需求时，在 API 层进行扩展
- **适配组件**：主要在 adaptor 层实现，将业务模块的数据转换为前端所需的格式

## 4. 接口规范

### 4.1 URL 规范
- 使用 RESTful 风格
- 使用小写字母
- 使用连字符（-）分隔单词
- 版本号放在 URL 中：/api/v1/...
- 支持 vue-vben-admin 的接口规范

### 4.2 请求规范
- GET：查询操作
- POST：创建操作
- PUT：更新操作
- DELETE：删除操作
- PATCH：部分更新操作
- 支持 vue-vben-admin 的请求格式

### 4.3 响应规范
```json
{
    "code": 200,           // 状态码
    "message": "success",  // 消息
    "data": {},           // 数据
    "success": true,      // 兼容 vue-vben-admin
    "result": {}          // 兼容 vue-vben-admin
}
```

### 4.4 状态码规范
- 200：成功
- 400：请求参数错误
- 401：未授权
- 403：禁止访问
- 404：资源不存在
- 500：服务器错误

## 5. 安全设计

### 5.1 认证
- 基于 JWT 的认证机制
- Token 过期机制
- 刷新 Token 机制
- 支持 vue-vben-admin 的认证流程

### 5.2 授权
- 基于 RBAC 的权限控制
- 接口级别的权限控制
- 数据级别的权限控制
- 支持 vue-vben-admin 的权限管理

### 5.3 安全防护
- XSS 防护
- CSRF 防护
- SQL 注入防护
- 请求限流

## 6. 接口文档

### 6.1 文档生成
- 使用 SpringDoc OpenAPI 自动生成
- 支持在线调试
- 支持导出离线文档
- 支持 vue-vben-admin 的接口文档格式

### 6.2 文档内容
- 接口描述
- 请求参数
- 响应结果
- 错误码说明
- 调用示例

## 7. 开发规范

### 7.1 代码规范
- 遵循阿里巴巴 Java 开发规范
- 使用统一的代码格式化模板
- 必要的注释和文档
- 完整的单元测试

### 7.2 接口规范
- 统一的命名规范
- 统一的参数校验
- 统一的异常处理
- 统一的日志记录
- 兼容 vue-vben-admin 的开发规范

## 8. 部署说明

### 8.1 环境要求
- JDK 17+
- Maven 3.8+
- Spring Boot 3.2+
- Redis 7.0+
- MySQL 8.0+

### 8.2 配置说明
- 应用配置
- 数据库配置
- 缓存配置
- 消息队列配置
- 跨域配置（支持 vue-vben-admin）

### 8.3 部署步骤
1. 编译打包
2. 配置文件修改
3. 服务启动
4. 健康检查

## 9. API 对接模式设计

API 层作为系统的统一入口，需要对接业务模块和前端。以下设计了几种对接模式：

### 9.1 API 网关模式

由于控制器已在业务模块中实现，API 层作为网关，主要职责是：

1. **请求转发和路由**：将请求转发到对应的业务模块
2. **统一响应格式**：包装业务模块的响应，适配前端需求
3. **统一异常处理**：捕获并处理业务模块的异常
4. **统一安全控制**：提供统一的认证和授权机制
5. **跨域处理**：处理跨域请求
6. **API 文档**：提供统一的 API 文档

#### 9.1.1 请求转发和路由设计

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加响应包装拦截器
        registry.addInterceptor(new ResponseWrapperInterceptor())
                .addPathPatterns("/api/**");
        
        // 添加安全拦截器
        registry.addInterceptor(new SecurityInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/login", "/api/auth/logout");
    }
}
```

#### 9.1.2 统一响应格式设计

```java
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                 Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                 ServerHttpRequest request, ServerHttpResponse response) {
        // 如果已经是 ApiResponse 类型，直接返回
        if (body instanceof ApiResponse) {
            return body;
        }
        
        // 包装为 vue-vben-admin 支持的格式
        return ApiResponse.success(body);
    }
}
```

### 9.2 服务组合模式

API 层作为一个独立的应用，将各业务模块作为依赖引入：

```yaml
# application.yml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  application:
    name: law-firm-api
  profiles:
    include:
      - system
      - auth
      - document
      - personnel
      - client
      - case
      - contract
      - finance
```

各模块配置单独文件：

```yaml
# application-system.yml, application-auth.yml 等
```

## 10. 与 vue-vben-admin 对接

vue-vben-admin 是一个基于 Vue 3、Vite 2、TypeScript、Ant Design Vue 等主流技术栈的中后台架构。为了与其对接，需要进行以下配置：

### 10.1 认证对接

#### 10.1.1 登录接口适配

vue-vben-admin 登录接口期望的请求格式：

```json
// POST /api/login
{
  "username": "admin",
  "password": "123456",
  "captcha": "code",
  "rememberMe": true
}
```

期望的响应格式：

```json
{
  "code": 200,
  "success": true,
  "message": "ok",
  "result": {
    "userId": "1",
    "token": "token123",
    "realName": "管理员",
    "username": "admin",
    "roles": [
      { "roleName": "管理员", "value": "admin" }
    ]
  }
}
```

#### 10.1.2 获取用户信息接口

vue-vben-admin 获取用户信息接口期望的响应格式：

```json
// GET /api/getUserInfo
{
  "code": 200,
  "success": true,
  "message": "ok",
  "result": {
    "userId": "1",
    "username": "admin",
    "realName": "管理员",
    "avatar": "https://...",
    "desc": "系统管理员",
    "roles": [
      { "roleName": "管理员", "value": "admin" }
    ],
    "permissions": [
      "sys:user:list",
      "sys:user:create"
    ]
  }
}
```

#### 10.1.3 JWT Token 配置

```java
@Component
public class JwtTokenUtil {

    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000; // 24小时
    private static final String JWT_SECRET = "law-firm-secret";  // 实际应用中需要配置在配置文件中

    // 生成 token
    public String generateToken(String username, String userId, List<String> roles) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRE_TIME);
        
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .claim("userId", userId)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }
    
    // 验证 token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    // 从 token 中获取用户名
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        
        return claims.getSubject();
    }
}
```

### 10.2 权限对接

vue-vben-admin 使用基于角色和权限的访问控制。

#### 10.2.1 菜单权限接口

vue-vben-admin 菜单权限接口期望的响应格式：

```json
// GET /api/getMenuList
{
  "code": 200,
  "success": true,
  "message": "ok",
  "result": [
    {
      "path": "/dashboard",
      "name": "Dashboard",
      "component": "LAYOUT",
      "redirect": "/dashboard/analysis",
      "meta": {
        "title": "仪表盘",
        "icon": "DashboardOutlined",
        "sort": 1
      },
      "children": [
        {
          "path": "analysis",
          "name": "Analysis",
          "component": "/dashboard/analysis/index",
          "meta": {
            "title": "分析页",
            "icon": "ChartLineOutlined"
          }
        }
      ]
    }
  ]
}
```

#### 10.2.2 权限编码适配

```java
@Service
public class PermissionService {

    /**
     * 将系统权限编码转换为前端权限格式
     */
    public List<String> convertToFrontendPermissions(List<String> sysPermissions) {
        // 示例转换逻辑
        return sysPermissions.stream()
                .map(p -> {
                    // 例如：将 system:user:view 转换为 sys:user:view
                    if (p.startsWith("system:")) {
                        return "sys:" + p.substring(7);
                    }
                    return p;
                })
                .collect(Collectors.toList());
    }
    
    /**
     * 将系统角色转换为前端角色格式
     */
    public List<Map<String, String>> convertToFrontendRoles(List<SysRole> roles) {
        return roles.stream()
                .map(role -> {
                    Map<String, String> roleMap = new HashMap<>();
                    roleMap.put("roleName", role.getRoleName());
                    roleMap.put("value", role.getRoleCode());
                    return roleMap;
                })
                .collect(Collectors.toList());
    }
}
```

### 10.3 响应格式适配

为了适配 vue-vben-admin 的响应格式，需要修改 ApiResponse 类：

```java
@Data
@Accessors(chain = true)
public class ApiResponse<T> {
    
    private int code;
    private String message;
    private T data;
    
    // 兼容 vue-vben-admin
    private boolean success;
    private T result;
    
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>()
                .setCode(200)
                .setMessage("success")
                .setData(data)
                .setSuccess(true)
                .setResult(data);
    }
    
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<T>()
                .setCode(code)
                .setMessage(message)
                .setSuccess(false);
    }
}
```

### 10.4 跨域配置

vue-vben-admin 需要进行跨域配置：

```java
@Configuration
public class CorsConfig {
    
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 允许的域，实际生产中可以指定具体的域名
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}
```

## 11. 具体实现示例

### 11.1 主应用类

```java
@SpringBootApplication
@ComponentScan(basePackages = {"com.lawfirm"})
public class LawFirmApiApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(LawFirmApiApplication.class, args);
    }
    
    /**
     * 消息转换器，处理字符串返回值
     */
    @Bean
    public HttpMessageConverter<String> stringConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }
    
    /**
     * Jackson 配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
            builder.timeZone(TimeZone.getTimeZone("GMT+8"));
            builder.dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            builder.modules(new JavaTimeModule());
        };
    }
}
```

### 11.2 全局异常处理

```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage());
        return ApiResponse.error(e.getCode(), e.getMessage());
    }
    
    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("参数校验异常: {}", message);
        return ApiResponse.error(400, message);
    }
    
    /**
     * 处理认证异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public ApiResponse<Void> handleAuthenticationException(AuthenticationException e) {
        log.error("认证异常: {}", e.getMessage());
        return ApiResponse.error(401, "认证失败: " + e.getMessage());
    }
    
    /**
     * 处理授权异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse<Void> handleAccessDeniedException(AccessDeniedException e) {
        log.error("授权异常: {}", e.getMessage());
        return ApiResponse.error(403, "权限不足: " + e.getMessage());
    }
    
    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return ApiResponse.error(500, "系统繁忙，请稍后再试");
    }
}
```

### 11.3 JWT 认证过滤器

```java
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtTokenUtil jwtTokenUtil;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        
        String path = request.getRequestURI();
        
        // 放行不需要认证的路径
        if (isExcludedPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // 获取 Authorization 头
        String authHeader = request.getHeader("Authorization");
        
        // 如果没有 Authorization 头或者不是以 Bearer 开头，则直接放行（后续会被拦截器拦截）
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // 提取 token
        String token = authHeader.substring(7);
        
        try {
            // 验证 token
            if (jwtTokenUtil.validateToken(token)) {
                // 从 token 中获取用户名
                String username = jwtTokenUtil.getUsernameFromToken(token);
                
                // 设置认证信息到上下文中
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // token 验证失败，不设置认证信息
        }
        
        filterChain.doFilter(request, response);
    }
    
    /**
     * 判断是否是排除认证的路径
     */
    private boolean isExcludedPath(String path) {
        return path.startsWith("/api/auth/login") || 
               path.startsWith("/api/auth/logout") ||
               path.startsWith("/swagger-ui") ||
               path.startsWith("/v3/api-docs");
    }
}
```

### 11.4 SwaggerConfig 配置

```java
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    
    @Bean
    public OpenAPI lawFirmOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("律师事务所管理系统 API")
                        .description("律师事务所管理系统 API 文档")
                        .version("1.0.0")
                        .contact(new Contact().name("Law Firm Team").email("support@lawfirm.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("API 规范文档")
                        .url("https://docs.lawfirm.com"));
    }
}
```

### 11.5 配置文件示例

```yaml
# application.yml
server:
  port: 8080
  servlet:
    context-path: /api
  tomcat:
    max-threads: 200
    min-spare-threads: 10

spring:
  application:
    name: law-firm-api
  profiles:
    active: dev
    include:
      - system
      - auth
      - document
      - personnel
      - client
      - case
      - contract
      - finance
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
    default-property-inclusion: non_null
  cache:
    type: redis
  redis:
    host: localhost
    port: 6379
    database: 0
    timeout: 10000
    lettuce:
      pool:
        max-active: 8
        max-idle: 8
        min-idle: 0
        max-wait: -1ms

# 日志配置
logging:
  level:
    root: INFO
    com.lawfirm: DEBUG
  file:
    name: logs/law-firm-api.log
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"

# OpenAPI 配置
springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
    disable-swagger-default-url: true
    operations-sorter: method
    tags-sorter: alpha
    
# 自定义配置
law-firm:
  security:
    jwt:
      secret: law-firm-secret-key
      expiration: 86400000  # 24小时
      issuer: law-firm
  cors:
    allowed-origins: http://localhost:3100
    allowed-methods: GET,POST,PUT,DELETE,OPTIONS
    allowed-headers: Content-Type,Authorization
  file:
    upload-dir: /data/upload
    max-size: 10MB
    allowed-types: .jpg,.jpeg,.png,.pdf,.doc,.docx,.xls,.xlsx
```

## 12. 注意事项与最佳实践

### 12.1 接口设计注意事项

1. **合理定义接口**：根据业务需求设计接口，避免过度设计
2. **保持统一**：接口命名、参数格式、返回格式等要保持统一
3. **版本控制**：接口版本控制要遵循统一规范
4. **幂等性**：GET、PUT、DELETE 等操作应保证幂等性
5. **安全性**：敏感操作需要做好验证和授权

### 12.2 对接 vue-vben-admin 最佳实践

1. **响应格式**：确保响应格式符合 vue-vben-admin 的要求
2. **权限控制**：权限编码格式要与前端对应
3. **菜单数据**：菜单数据结构要符合前端要求
4. **用户信息**：用户信息包含前端所需的所有字段
5. **Token 处理**：按照前端要求处理 Token

### 12.3 性能优化建议

1. **合理使用缓存**：对于频繁访问的数据使用 Redis 缓存
2. **请求合并**：避免前端多次请求，合并接口减少请求次数
3. **分页和懒加载**：大量数据要分页加载，避免一次性返回过多数据
4. **异步处理**：耗时操作使用异步处理，避免阻塞
5. **减少不必要的数据传输**：只返回前端需要的数据

### 12.4 安全最佳实践

1. **输入验证**：对所有输入进行严格验证
2. **防止 SQL 注入**：使用参数绑定，避免拼接 SQL
3. **防止 XSS**：对输入输出进行安全过滤
4. **防止 CSRF**：使用 CSRF Token
5. **敏感数据加密**：敏感数据要加密存储和传输
6. **日志记录**：记录关键操作和安全事件的日志

## Redis配置说明

系统支持以下Redis配置方式：

### 1. 不使用密码的Redis (本地开发)

本地开发环境通常使用无密码的Redis，可以通过以下方式启动应用：

```bash
# Linux/Mac
./start-local.sh

# Windows
start-local.bat
```

这些脚本会自动设置正确的环境变量，并使用local配置文件。

### 2. 使用密码的Redis (生产环境)

生产环境通常需要Redis密码，可以通过以下环境变量配置：

```bash
export REDIS_HOST=your-redis-host
export REDIS_PORT=6379
export REDIS_PASSWORD=your-redis-password
```

然后使用标准启动脚本：

```bash
./start.sh
```

### 3. 禁用Redis缓存

如果不想使用Redis，可以在配置中禁用Redis缓存，在application.yml或本地配置文件中设置：

```yaml
law:
  firm:
    cache:
      enabled: false
      type: LOCAL
```

## 常见问题排查

1. Redis认证错误: "ERR Client sent AUTH, but no password is set"
   - 原因：应用尝试使用密码连接没有密码的Redis服务
   - 解决：使用start-local.sh/bat脚本启动，或确保REDIS_PASSWORD环境变量为空

2. Redis连接失败
   - 检查Redis服务是否运行: `redis-cli ping` 应返回PONG
   - 检查Redis配置信息: `redis-cli info` 查看详细信息

## 兼容性问题分析

在将系统升级到Spring Boot 3.2.3和MyBatis-Plus 3.5.3.1后，我们遇到了以下兼容性问题：

### 1. MyBatis-Plus与Spring Boot 3.2.3集成问题

**问题现象**：
1. 项目中同时存在`mybatis-plus-boot-starter`(3.5.3.1)和`mybatis-plus-spring-boot3-starter`(3.5.9)
2. 自动配置加载混乱，导致部分Bean注册失败
3. 启动时出现ddlApplicationRunner Bean找不到的错误

**问题分析**：
1. `pom.xml`中存在版本不一致的MyBatis-Plus依赖
2. `mybatis-plus-boot-starter`不完全兼容Spring Boot 3.2.3
3. 自动配置优先级和加载顺序出现问题

### 2. 模块间配置冲突

**问题现象**：
1. 多个模块各自定义MyBatis相关配置，可能存在覆盖
2. 模块间组件扫描范围重叠，导致Bean定义冲突
3. 缓存配置分散在不同模块，造成使用混乱

### 3. 自动配置导入问题

**问题现象**：
1. `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`文件中手动指定了配置类
2. 某些自动配置项在`@SpringBootApplication`注解中被排除，但在imports文件中又被导入

## 解决方案

### 1. 依赖管理优化

1. 统一使用`mybatis-plus-spring-boot3-starter` 3.5.9，而不是混用多个starter
2. 在`law-firm-dependencies/pom.xml`中明确定义所有相关依赖版本
3. 添加核心MyBatis依赖，确保版本兼容性：
   - mybatis 3.5.11
   - mybatis-spring 2.1.1

### 2. 配置类统一

1. 创建`DdlConfig`类提供`ddlApplicationRunner` Bean，解决自动配置依赖问题
2. 通过自动配置导入清单(`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`)明确声明配置加载顺序
3. 避免在不同模块中重复定义相同功能的配置类

### 3. 组件扫描优化

在`LawFirmApiApplication`中明确定义组件扫描顺序：
1. Common模块（最先编译）
2. Model模块（其次编译）
3. Core模块（再次编译）
4. 业务模块和API（最后编译）

## 后续建议

1. 构建依赖图，明确模块间依赖关系
2. 对每个模块进行独立的兼容性测试
3. 定期检查依赖更新，确保版本兼容性
4. 减少配置重叠，明确每个模块的职责边界

## 法律事务管理系统API模块

## 项目介绍
本模块是法律事务管理系统的API服务入口，集成了系统所有子模块功能，提供完整的业务API接口。

## 技术架构
- Spring Boot 3.2.3
- MyBatis-Plus 3.5.3.1
- MySQL 8.0
- Redis
- SpringDoc (OpenAPI 3)

## 兼容性说明
由于MyBatis-Plus 3.5.3.1版本与Spring Boot 3.2.3的兼容性问题，本项目做出以下处理方案：

1. 在`DdlConfig`类中提供了`ddlApplicationRunner` Bean，用于解决MyBatis-Plus启动兼容性问题
2. 在`application.yml`中配置了`default-enum-type-handler: org.apache.ibatis.type.EnumOrdinalTypeHandler`，用于处理枚举类型
3. 使用标准的MyBatis-Plus配置，避免使用已废弃的API

以上方案确保了系统能够在Spring Boot 3.2.3环境下正常运行，当MyBatis-Plus发布完全兼容的版本后，可以考虑移除兼容性处理代码。

## 配置说明
系统支持通过环境变量或配置文件进行配置，主要配置项包括：

- 数据库连接配置
- 安全认证配置
- 缓存配置
- 存储服务配置
- 各业务模块配置

详细配置项见`application.yml`文件。

## 模块依赖
本模块依赖于：
1. 公共基础模块（common-*）
2. 核心功能模块（core-*）
3. 业务模块（law-firm-*）
4. 数据模型模块（*-model）

## 启动说明
1. 确保MySQL数据库已启动
2. 配置正确的数据库连接信息
3. 启动应用：`mvn spring-boot:run`或直接运行`LawFirmApiApplication`类

## API文档
应用启动后，可通过以下地址访问API文档：
- OpenAPI文档：`http://localhost:8080/api/v3/api-docs`
- Swagger UI：`http://localhost:8080/api/swagger-ui.html`

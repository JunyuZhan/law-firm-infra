# Common Core 模块

核心功能模块，提供最基础的工具和通用功能。

## 功能特性

### 基础实体与领域模型
- BaseEntity：包含 id、创建时间、更新时间等通用字段
- BaseDTO：数据传输对象基类
- BaseVO：视图对象基类
- BaseQuery：查询对象基类

### 通用枚举和常量
- CommonStatus：通用状态枚举
- YesNoEnum：是否枚举
- DeletedEnum：删除状态枚举
- CommonConstants：通用常量定义

### 异常处理机制
- BusinessException：业务异常
- SystemException：系统异常
- ValidationException：参数校验异常
- GlobalExceptionHandler：全局异常处理器

### 多租户支持
- TenantContext：租户上下文
- TenantEntity：租户实体基类
- TenantInterceptor：租户拦截器
- TenantProperties：租户配置属性

### 统一响应结果
- R<T>：统一响应对象
- PageResult<T>：分页结果对象
- ResultCode：响应状态码
- ResultMsg：响应消息

### 工具类库
- StringUtils：字符串工具类
- DateUtils：日期工具类
- JsonUtils：JSON工具类
- BeanUtils：对象工具类
- SecurityUtils：安全工具类

## 使用说明

### Maven 依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-core</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 配置示例
```yaml
lawfirm:
  core:
    # 租户配置
    tenant:
      enabled: true
      ignore-tables:
        - sys_tenant
        - sys_user
      
    # 异常处理配置
    exception:
      include-stack-trace: true
      log-level: warn
```

### 代码示例

#### 1. 实体类定义
```java
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private CommonStatus status;
}
```

#### 2. 异常处理
```java
public void createUser(UserDTO dto) {
    if (userExists(dto.getUsername())) {
        throw new BusinessException("用户名已存在");
    }
    // 业务处理
}
```

#### 3. 统一响应
```java
@GetMapping("/list")
public R<List<UserVO>> list() {
    List<UserVO> users = userService.list();
    return R.ok(users);
}
```

## 注意事项

1. 所有业务实体都应继承 BaseEntity
2. 异常应使用框架定义的异常类
3. 接口返回值应使用统一响应对象
4. 多租户场景注意使用 TenantContext
5. 工具类优先使用框架提供的 
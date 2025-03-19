# 核心模块 (Common Core)

## 模块说明
核心模块提供了一系列基础功能支持，包括统一响应处理、统一异常处理、基础配置支持、基础实体支持、上下文管理和常量定义等。

## 功能特性

### 1. 统一响应处理
```java
// 成功响应
CommonResult.success(data);

// 失败响应
CommonResult.error(message);

// 自定义响应
CommonResult.build(code, message, data);
```

### 2. 统一异常处理
```java
// 业务异常
throw new BusinessException(message);

// 验证异常
throw new ValidationException(message);

// 框架异常
throw new FrameworkException(message);
```

### 3. 基础配置支持
```java
@Configuration
public class CustomConfig extends BaseConfig {
    // 自定义配置
}
```

### 4. 基础实体支持
```java
public class CustomEntity extends BaseEntity {
    // 自定义字段
}
```

### 5. 上下文管理
```java
// 设置上下文
BaseContextHandler.setContext(key, value);

// 获取上下文
BaseContextHandler.getContext(key);

// 清除上下文
BaseContextHandler.clear();
```

### 6. 常量定义
```java
// 使用常量
CommonConstants.SUCCESS_CODE;
CommonConstants.ERROR_CODE;
```

## 使用示例

### 1. 统一响应示例
```java
@RestController
@RequestMapping("/api")
public class DemoController {
    
    @GetMapping("/data")
    public CommonResult getData() {
        try {
            // 业务处理
            return CommonResult.success(data);
        } catch (Exception e) {
            return CommonResult.error(e.getMessage());
        }
    }
}
```

### 2. 统一异常示例
```java
@Service
public class DemoService {
    
    public void process() {
        // 业务验证
        if (!valid) {
            throw new ValidationException("数据验证失败");
        }
        
        // 业务处理
        if (error) {
            throw new BusinessException("业务处理失败");
        }
    }
}
```

### 3. 基础实体示例
```java
@Entity
@Table(name = "demo")
public class DemoEntity extends BaseEntity {
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "code")
    private String code;
    
    // getter and setter
}
```

### 4. 上下文管理示例
```java
@Service
public class DemoService {
    
    public void process() {
        // 设置上下文
        BaseContextHandler.setContext("userId", "123");
        
        try {
            // 业务处理
            doProcess();
        } finally {
            // 清除上下文
            BaseContextHandler.clear();
        }
    }
}
```

## 配置说明

### 1. 自动配置
```yaml
# application.yml
law-firm:
  common:
    core:
      enabled: true
      response:
        enabled: true
      exception:
        enabled: true
      context:
        enabled: true
```

### 2. 自定义配置
```java
@Configuration
public class CustomCoreConfig extends CoreAutoConfiguration {
    
    @Bean
    public CustomBean customBean() {
        return new CustomBean();
    }
}
```

## 最佳实践

### 1. 响应处理
- 使用统一的响应格式
- 合理使用响应码
- 规范响应消息

### 2. 异常处理
- 使用统一的异常类型
- 合理使用异常层级
- 规范异常消息

### 3. 实体设计
- 继承基础实体类
- 规范字段命名
- 添加字段注释

### 4. 上下文使用
- 及时清理上下文
- 避免上下文泄露
- 合理使用上下文

## 注意事项

### 1. 响应处理
- 不要直接返回原始数据
- 不要使用自定义响应格式
- 不要忽略异常处理

### 2. 异常处理
- 不要吞掉异常
- 不要使用自定义异常
- 不要忽略异常堆栈

### 3. 实体设计
- 不要修改基础字段
- 不要忽略字段验证
- 不要忽略字段注释

### 4. 上下文使用
- 不要存储敏感数据
- 不要存储大量数据
- 不要忽略上下文清理

## 常见问题

### 1. 响应问题
Q: 如何自定义响应格式？
A: 继承CommonResult类，重写相关方法。

### 2. 异常问题
Q: 如何处理自定义异常？
A: 继承BaseException类，实现自定义异常。

### 3. 实体问题
Q: 如何添加自定义字段？
A: 继承BaseEntity类，添加自定义字段。

### 4. 上下文问题
Q: 如何处理上下文并发？
A: 使用ThreadLocal存储上下文数据。

## 更新日志
- 2024-03-18: 初始版本发布

## 核心类说明

### 1. 配置类 (config)
- **CoreAutoConfiguration**: 核心自动配置类，负责模块的自动配置
  - 自动扫描相关组件
  - Spring Boot 自动配置支持

### 2. 异常处理 (exception)
- **BaseException**: 异常基类，所有自定义异常的父类
  - 提供基础的异常属性和行为
  - 支持错误码和消息传递
- **FrameworkException**: 框架级异常，用于处理框架内部异常
  - 继承自 BaseException
  - 处理框架内部的异常情况
- **ValidationException**: 参数验证异常
  - 继承自 FrameworkException
  - 专门用于处理参数验证失败的情况
- **GlobalExceptionHandler**: 全局异常处理器
  - 统一的异常处理机制
  - 异常转换为标准响应格式
  - 支持多种异常类型的处理

### 3. API 响应 (api)
- **CommonResult**: 统一响应结果封装
  - 标准的响应格式定义
  - 支持泛型数据返回
  - 提供多种静态工厂方法
  - 支持成功/失败状态判断

### 4. 常量定义 (constant)
- **CommonConstants**: 框架级通用常量定义
  - 基础状态常量
  - 通用数值常量
  - 时间格式常量
  - 字符集常量
  - 基础符号常量
- **ResultCode**: 响应状态码定义
  - 基础响应码（2xx, 4xx, 5xx）
  - 框架级错误码（6xx）
  - 包含码值和描述信息

### 5. 上下文管理 (context)
- **BaseContextHandler**: 基础上下文处理器
  - 基于 ThreadLocal 的线程安全实现
  - 支持多种数据类型的存取
  - 提供上下文生命周期管理
  - 支持默认值机制

### 6. 基础实体 (entity)
- **BaseEntity**: 实体基类
  - 提供基础字段（ID、创建时间、更新时间）
  - 实现序列化接口
  - 使用 Lombok 简化代码
  - 支持链式调用

## 目录结构
```
com.lawfirm.common.core
├── api
│   └── CommonResult.java
├── config
│   └── CoreAutoConfiguration.java
├── constant
│   ├── CommonConstants.java
│   └── ResultCode.java
├── context
│   └── BaseContextHandler.java
├── entity
│   └── BaseEntity.java
└── exception
    ├── BaseException.java
    ├── FrameworkException.java
    ├── ValidationException.java
    └── GlobalExceptionHandler.java
```

## 主要功能

### 1. 基础配置
- **CoreAutoConfiguration**: 核心自动配置类，负责模块的自动配置

### 2. 异常处理框架
- **BaseException**: 异常基类，所有自定义异常的父类
- **FrameworkException**: 框架级异常，用于处理框架内部异常
- **ValidationException**: 参数验证异常
- **GlobalExceptionHandler**: 全局异常处理器，统一异常处理

### 3. 通用响应格式
- **CommonResult**: 统一响应结果封装
- **ResultCode**: 响应状态码定义，只包含框架级的基础状态码

### 4. 基础常量
- **CommonConstants**: 框架级通用常量定义，只包含最基础的常量

### 5. 上下文管理
- **BaseContextHandler**: 基础上下文处理器，提供线程安全的上下文存储

### 6. 基础实体
- **BaseEntity**: 实体基类，提供最基础的实体属性（ID、创建时间、更新时间），不包含业务字段

## 依赖说明

### 1. Spring Boot 基础依赖
```xml
<!-- Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

### 2. 工具类库
```xml
<!-- Commons -->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>${commons-lang3.version}</version>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-collections4</artifactId>
    <version>${commons-collections4.version}</version>
</dependency>
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>${commons-io.version}</version>
</dependency>

<!-- Hutool -->
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>${hutool.version}</version>
</dependency>
```

### 3. 开发工具
```xml
<!-- MapStruct -->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>${mapstruct.version}</version>
</dependency>
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>${mapstruct.version}</version>
    <scope>provided</scope>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>${lombok.version}</version>
    <scope>provided</scope>
</dependency>
```

## 使用说明

### 1. 引入依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-core</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 异常处理
```java
// 抛出框架异常
throw new FrameworkException(ResultCode.INTERNAL_ERROR, "系统错误");

// 抛出验证异常
throw new ValidationException("参数错误");
```

### 3. 响应处理
```java
// 返回成功结果
return CommonResult.success(data);

// 返回错误结果
return CommonResult.error(ResultCode.VALIDATION_ERROR);
```

### 4. 上下文使用
```java
// 设置上下文
BaseContextHandler.set("userId", 123L);

// 获取上下文
Long userId = BaseContextHandler.get("userId");

// 清理上下文
BaseContextHandler.clear();
```

### 5. 实体继承
```java
public class User extends BaseEntity {
    // 只添加业务字段，基础字段由 BaseEntity 提供
    private String username;
    private String email;
}
```

## 注意事项

1. 本模块仅提供基础设施支持，不应包含：
   - 具体的业务模型定义
   - 具体的接口定义
   - 具体的枚举定义
   - 具体的实体定义（BaseEntity 除外，因为它是框架级的）

2. 具体的模型定义应该由上层业务模块完成

3. 保持模块的纯粹性：
   - 只包含最基础的框架支持
   - 不引入非必要的依赖
   - 不包含具体的业务逻辑
   - 包含基础功能的单元测试和集成测试
   - 不包含业务相关的测试代码

## 测试说明

### 1. 测试范围
- API 响应测试 (CommonResultTest)
  - 成功响应测试
  - 错误响应测试
  - 自定义消息测试
  - 验证失败测试
  - 未授权测试
  - 禁止访问测试

- 配置测试 (CoreAutoConfigurationTest)
  - 自动配置加载测试
  - 组件扫描测试

- 常量测试
  - CommonConstantsTest: 通用常量验证
  - ResultCodeTest: 响应码验证

- 上下文测试 (BaseContextHandlerTest)
  - 上下文数据存取测试
  - 类型转换测试
  - 线程安全测试

- 实体测试 (BaseEntityTest)
  - 基础字段测试
  - 序列化测试

- 异常处理测试 (GlobalExceptionHandlerTest)
  - 参数验证异常测试
  - 未知异常测试
  - 文件上传异常测试
  - 请求方法不支持异常测试

### 2. 测试执行
```bash
# 执行所有测试
mvn test

# 跳过测试进行构建
mvn clean install -DskipTests
```

### 3. 测试覆盖率
- 总测试用例数：41
- 测试覆盖模块：
  - API 响应模块
  - 配置模块
  - 常量模块
  - 上下文模块
  - 实体模块
  - 异常处理模块

### 4. 注意事项
1. 在使用 `CommonResult` 的 `success` 方法时，需要注意方法重载：
   ```java
   // 推荐使用明确的方法调用
   CommonResult<String> result = CommonResult.success(data, ResultCode.SUCCESS.getMessage());
   
   // 或者使用带消息的成功响应
   CommonResult<String> result = CommonResult.success(data, "自定义成功消息");
   ```

2. 异常处理测试可能会产生预期的警告日志，这是正常的测试行为：
   ```
   WARN com.lawfirm.common.core.exception.GlobalExceptionHandler -- 参数验证异常
   ERROR com.lawfirm.common.core.exception.GlobalExceptionHandler -- 未知异常
   ```

## 维护者

- 维护团队：基础架构组
- 联系邮箱：xxx@xxx.com 
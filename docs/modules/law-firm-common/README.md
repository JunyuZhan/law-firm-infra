# law-firm-common 公共模块

## 模块说明
本模块作为项目的公共基础模块，提供各个业务模块所需的通用功能和工具支持。包含消息处理、工具类库、安全组件和Web公共组件等基础设施。

## 目录结构
```
law-firm-common/
├── common-message    // 消息公共组件
├── common-util      // 工具类库
├── common-security  // 安全组件
└── common-web      // Web公共组件
```

## 主要功能

### 1. common-message
消息公共组件提供基础的消息定义和处理接口。

主要功能：
- 消息接口定义：统一的消息处理接口规范
- 消息类型枚举：系统消息类型定义
- 消息工具类：消息处理工具集
- 消息异常定义：统一的异常处理机制

详细文档：[common-message模块说明](common-message/README.md)

### 2. common-util
工具类库提供项目通用的工具类集合。

主要功能：
- 日期时间工具：日期格式化、计算、转换
- 字符串处理：字符串操作、验证、转换
- 加密解密：常用加密算法实现
- JSON处理：JSON序列化与反序列化
- 文件操作：文件读写、上传下载

详细文档：[common-util模块说明](common-util/README.md)

### 3. common-security
安全组件提供统一的安全相关功能。

主要功能：
- 认证授权：用户认证、权限控制
- 密码管理：密码加密、验证
- 安全过滤：请求过滤、防XSS
- 访问控制：接口访问控制
- 安全工具类：安全相关工具集

详细文档：[common-security模块说明](common-security/README.md)

### 4. common-web
Web公共组件提供Web应用相关的通用功能。

主要功能：
- 统一响应处理：标准响应格式
- 统一异常处理：全局异常处理
- 请求日志记录：访问日志记录
- 参数验证：请求参数校验
- 跨域配置：跨域访问控制

详细文档：[common-web模块说明](common-web/README.md)

## 技术架构
- Spring Boot 2.7.x：基础框架
- Spring Security：安全框架
- Apache Commons：工具库
- Hutool：工具集
- Jackson：JSON处理

## 依赖关系
- law-firm-dependencies：依赖管理
- law-firm-model：数据模型依赖

## 使用指南

### 1. 引入依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>law-firm-common</artifactId>
    <version>${revision}</version>
</dependency>
```

### 2. 配置说明
各模块的配置文件位于各自的resources目录下：
```
resources/
├── application.yml          // 基础配置
└── application-common.yml   // 公共组件配置
```

### 3. 使用示例

#### 消息处理
```java
@Autowired
private MessageService messageService;

// 发送消息
messageService.sendMessage(new SystemMessage("测试消息"));
```

#### 工具类使用
```java
// 日期处理
String formattedDate = DateUtil.format(new Date(), "yyyy-MM-dd");

// 加密
String encrypted = SecurityUtil.encrypt("待加密内容", "密钥");
```

#### Web响应处理
```java
@RestController
public class DemoController {
    @GetMapping("/demo")
    public Result<String> demo() {
        return Result.success("操作成功");
    }
}
```

## 规范要求

### 1. 代码规范
- 遵循Java代码规范
- 使用统一的异常处理机制
- 保持工具类的无状态性
- 合理使用设计模式

### 2. 安全规范
- 敏感信息必须加密
- 遵循最小权限原则
- 防止XSS和SQL注入
- 日志脱敏处理

### 3. 性能规范
- 避免重复造轮子
- 优化工具类性能
- 合理使用缓存
- 注意内存使用

## 常见问题

1. Q: 如何处理跨域问题？
   A: 使用common-web模块中的跨域配置。

2. Q: 如何统一处理异常？
   A: 继承GlobalExceptionHandler并自定义异常处理。

3. Q: 如何使用安全组件？
   A: 参考common-security模块的使用说明。

## 注意事项

1. 工具类设计要考虑通用性
2. 注意并发安全问题
3. 定期更新安全组件
4. 保持文档的及时更新
5. 关注性能优化

## 相关文档

- [开发规范](../../development/coding-standards.md)
- [安全规范](../../development/security-guidelines.md)
- [接口规范](../../development/api-guidelines.md) 
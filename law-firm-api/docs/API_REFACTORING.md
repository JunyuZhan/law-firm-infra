# API层重构说明

## 重构目标

本次API层重构的主要目标是：

1. 统一API路径命名规范
2. 消除API路径冲突
3. 提高API层的可维护性
4. 简化API开发流程
5. 规范化API响应格式

## 主要改进

### 1. 统一API常量管理

创建了`ApiConstants`类，集中管理所有API相关常量：

- API基础路径
- API版本
- 模块路径前缀
- 操作路径后缀

### 2. 版本化API支持

- 通过`ApiVersionConfig`统一管理API版本
- 支持版本化API路径（如`/api/v1/client`）
- 在HTTP响应头中添加API版本信息

### 3. 统一响应格式

- 所有API响应使用`CommonResult<T>`包装
- 通过`GlobalResponseAdvice`自动包装非标准响应
- 统一异常处理和错误码

### 4. 规范化API文档

- 创建了API路径规范指南
- 提供了API模块常量模板
- 规范了控制器命名和路径定义

### 5. 模块化API结构

- 每个业务模块定义自己的API常量类
- 模块内API路径保持一致的命名风格
- 避免跨模块的路径冲突

## 使用指南

### 定义模块API常量

每个模块应创建自己的常量类，参考`ApiModuleTemplate`：

```java
public class ClientConstants {
    // API前缀
    public static final String API_PREFIX = ApiConstants.Module.CLIENT;
    
    // 子资源路径
    public static final String API_CONTACT_PREFIX = API_PREFIX + "/contact";
    public static final String API_FOLLOW_UP_PREFIX = API_PREFIX + "/follow-up";
}
```

### 创建控制器

控制器应继承`BaseApiController`并使用常量定义路径：

```java
@RestController("clientController")
@RequestMapping(ClientConstants.API_PREFIX)
public class ClientController extends BaseApiController {
    
    @GetMapping(ApiConstants.Operation.LIST)
    public CommonResult<List<ClientDTO>> list() {
        // ...
    }
}
```

### 使用统一响应格式

```java
// 成功响应
return success(data);

// 带消息的成功响应
return success(data, "操作成功");

// 错误响应
return error("操作失败");

// 带错误码的错误响应
return error(ResultCode.VALIDATION_ERROR.getCode(), "参数验证失败");
```

## 迁移指南

1. 更新现有控制器，使用新的API常量
2. 检查并修复可能的路径冲突
3. 确保所有响应使用统一格式
4. 更新API文档，反映新的路径结构

## 后续计划

1. 完善API文档自动生成
2. 增强API安全性控制
3. 实现API限流和监控
4. 支持API版本迁移和兼容性

## 相关文档

- [API路径规范指南](./API_PATH_GUIDE.md)
- [API安全指南](./API_SECURITY_GUIDE.md)
- [API版本控制指南](./API_VERSIONING_GUIDE.md) 
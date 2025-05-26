[返回主项目说明](../README.md)

# 律师事务所管理系统 API 模块

## 简介

API模块是律师事务所管理系统的核心接口层，提供统一的REST API服务，连接前端应用与后端业务模块。

## 主要功能

- 统一API入口和路由
- API版本控制
- 统一响应格式
- 全局异常处理
- API限流和防重复提交
- API文档生成

## API层结构

```
law-firm-api/
├── src/main/java/com/lawfirm/api/
│   ├── advice/          # 全局通知和异常处理
│   ├── annotation/      # 自定义注解
│   ├── aspect/          # AOP切面
│   ├── config/          # 配置类
│   ├── constant/        # 常量定义
│   ├── controller/      # API控制器
│   ├── interceptor/     # 拦截器
│   └── service/         # API服务
└── docs/                # API文档
    ├── API_PATH_GUIDE.md        # API路径规范指南
    └── API_REFACTORING.md       # API层重构说明

```

## API路径规范

所有API路径遵循以下结构：

```
/api/{module}/{resource}/{operation}
```

版本化API路径结构：

```
/api/v1/{module}/{resource}/{operation}
```

详细规范请参考 [API路径规范指南](./docs/API_PATH_GUIDE.md)。

## API响应格式

所有API响应使用统一的`CommonResult<T>`格式：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": { ... }
}
```

## 最近更新

### API层重构

我们对API层进行了全面重构，主要改进包括：

1. 统一API常量管理
2. 版本化API支持
3. 统一响应格式
4. 规范化API文档
5. 模块化API结构

详细信息请参考 [API层重构说明](./docs/API_REFACTORING.md)。

## 开发指南

### 创建新API控制器

1. 继承`BaseApiController`
2. 使用`ApiConstants`定义路径
3. 遵循API路径规范

示例：

```java
@RestController("exampleController")
@RequestMapping(ExampleConstants.API_PREFIX)
public class ExampleController extends BaseApiController {
    
    @GetMapping(ApiConstants.Operation.LIST)
    public CommonResult<List<ExampleDTO>> list() {
        // ...
        return success(data);
    }
}
```

### 模块API常量定义

每个模块应创建自己的常量类：

```java
public class ExampleConstants {
    // API前缀
    public static final String API_PREFIX = ApiConstants.Module.EXAMPLE;
    
    // 子资源路径
    public static final String API_SUB_RESOURCE_PREFIX = API_PREFIX + "/sub-resource";
}
```

## 相关文档

- [API路径规范指南](./docs/API_PATH_GUIDE.md)
- [API层重构说明](./docs/API_REFACTORING.md)

# API路径规范指南

## 概述

本文档提供了律师事务所管理系统API路径的规范和最佳实践，以确保API设计的一致性和可维护性。

## API路径结构

所有API路径应遵循以下结构：

```
/api/{module}/{resource}/{operation}
```

例如：
- `/api/client/list` - 获取客户列表
- `/api/case/1/detail` - 获取案件详情
- `/api/contract/create` - 创建合同

## API版本控制

当前API版本为`v1`，版本化的API路径结构如下：

```
/api/v1/{module}/{resource}/{operation}
```

例如：
- `/api/v1/client/list` - 获取客户列表（带版本）
- `/api/v1/case/1/detail` - 获取案件详情（带版本）

## 模块命名规范

系统中的API模块应使用`ApiConstants.Module`中定义的常量：

| 模块名称 | 常量 | 路径 |
|---------|------|-----|
| 认证模块 | `AUTH` | `/api/auth` |
| 系统管理 | `SYSTEM` | `/api/system` |
| 客户管理 | `CLIENT` | `/api/client` |
| 案件管理 | `CASE` | `/api/case` |
| 合同管理 | `CONTRACT` | `/api/contract` |
| 文档管理 | `DOCUMENT` | `/api/document` |
| 任务管理 | `TASK` | `/api/task` |
| 日程管理 | `SCHEDULE` | `/api/schedule` |
| 财务管理 | `FINANCE` | `/api/finance` |
| 知识库管理 | `KNOWLEDGE` | `/api/knowledge` |
| 归档管理 | `ARCHIVE` | `/api/archive` |
| 人事管理 | `PERSONNEL` | `/api/personnel` |
| 统计分析 | `ANALYSIS` | `/api/analysis` |

## 操作命名规范

API操作应使用`ApiConstants.Operation`中定义的常量：

| 操作类型 | 常量 | 路径后缀 |
|---------|------|---------|
| 查询 | `QUERY` | `/query` |
| 列表 | `LIST` | `/list` |
| 分页 | `PAGE` | `/page` |
| 详情 | `DETAIL` | `/detail` |
| 创建 | `CREATE` | `/create` |
| 更新 | `UPDATE` | `/update` |
| 删除 | `DELETE` | `/delete` |
| 批量操作 | `BATCH` | `/batch` |
| 导出 | `EXPORT` | `/export` |
| 导入 | `IMPORT` | `/import` |

## 控制器命名规范

1. 所有API控制器类应继承`BaseApiController`
2. 控制器类名应以`Controller`结尾
3. REST控制器应使用`@RestController`注解
4. 控制器应使用`@RequestMapping`指定基础路径
5. 控制器Bean名称应指定，避免冲突

示例：
```java
@RestController("clientController")
@RequestMapping(ClientConstants.API_PREFIX)
public class ClientController extends BaseApiController {
    // ...
}
```

## 路径常量定义规范

每个模块应在其常量类中定义API路径常量：

```java
public class ClientConstants {
    // API前缀
    public static final String API_PREFIX = ApiConstants.Module.CLIENT;
    
    // 子资源路径
    public static final String API_CONTACT_PREFIX = API_PREFIX + "/contact";
    public static final String API_FOLLOW_UP_PREFIX = API_PREFIX + "/follow-up";
}
```

## 最佳实践

1. 使用常量而非硬编码字符串定义API路径
2. 遵循RESTful API设计原则
3. 使用复数形式命名资源集合（如`/clients`而非`/client`）
4. 使用连字符（`-`）连接多个单词（如`/follow-up`而非`/followUp`）
5. 使用小写字母
6. 避免在URL中包含文件扩展名
7. 在控制器方法上使用合适的HTTP方法注解（`@GetMapping`, `@PostMapping`等）

## 示例

### 客户管理API

```java
@RestController("clientController")
@RequestMapping(ClientConstants.API_PREFIX)
public class ClientController extends BaseApiController {
    
    @GetMapping(Operation.LIST)
    public CommonResult<List<ClientDTO>> list() {
        // ...
    }
    
    @GetMapping("/{id}" + Operation.DETAIL)
    public CommonResult<ClientDetailDTO> detail(@PathVariable Long id) {
        // ...
    }
    
    @PostMapping(Operation.CREATE)
    public CommonResult<Long> create(@RequestBody @Valid ClientCreateDTO dto) {
        // ...
    }
}
```

### 案件管理API

```java
@RestController("caseController")
@RequestMapping(CaseBusinessConstants.Controller.API_PREFIX)
public class CaseController extends BaseApiController {
    
    @GetMapping(Operation.PAGE)
    public CommonResult<Page<CaseDTO>> page(CaseQueryDTO query) {
        // ...
    }
    
    @GetMapping("/{id}" + Operation.DETAIL)
    public CommonResult<CaseDetailDTO> detail(@PathVariable Long id) {
        // ...
    }
} 
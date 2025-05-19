# API文档使用指南

## 1. 简介

本系统使用OpenAPI 3.0 (Swagger)规范进行API文档化，提供了直观的API交互界面。API文档在开发和测试环境中自动启用，生产环境中禁用。

## 2. 访问API文档

- 开发环境: http://localhost:8080/swagger-ui/index.html
- 测试环境: http://localhost:8081/swagger-ui/index.html
- 简化路径: /swagger-ui 或 /api

## 3. API分组

API文档按照以下分组组织：

- **系统管理**: 包含系统和认证相关API (`/api/system/**`, `/api/auth/**`)
- **业务管理**: 包含业务功能相关API (`/api/v1/**`)
- **公共接口**: 包含健康检查等公共API (`/api/health`, `/api/version`)

## 4. API注解使用指南

### 4.1 控制器类注解

在Controller类上添加如下注解：

```java
@Tag(name = "模块名称", description = "模块描述")
@RestController
@RequestMapping("/api/路径")
```

### 4.2 方法注解

在API方法上添加如下注解：

```java
@Operation(
    summary = "接口名称", 
    description = "接口详细描述",
    security = @SecurityRequirement(name = "bearerAuth") // 需要认证的接口
)
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "成功"),
    @ApiResponse(responseCode = "401", description = "未认证"),
    @ApiResponse(responseCode = "500", description = "服务器错误")
})
```

### 4.3 参数注解

对于请求参数的注解：

```java
// URL参数
@Parameter(description = "参数描述", required = true) @RequestParam String param

// 路径参数
@Parameter(name = "id", description = "记录ID", in = ParameterIn.PATH) @PathVariable Long id

// 请求体
@io.swagger.v3.oas.annotations.parameters.RequestBody(
    description = "请求体描述",
    required = true,
    content = @Content(schema = @Schema(implementation = 类名.class))
) @RequestBody 类名 对象
```

### 4.4 模型注解

对于DTO/VO等模型类：

```java
@Schema(description = "模型描述")
public class ExampleDTO {
    @Schema(description = "字段描述", example = "示例值")
    private String fieldName;
}
```

## 5. 模板示例

参考 `TemplateApiController` 类，这是一个完整的API文档注解演示。

## 6. 生产环境配置

在生产环境中，API文档默认禁用。若需在特定生产环境启用，请修改 `application-prod.yml` 中的配置：

```yaml
springdoc:
  api-docs:
    enabled: true
  swagger-ui:
    enabled: true
```

## 7. 安全说明

- API文档会暴露系统API结构，请勿在无安全保护的生产环境启用
- 敏感参数如密码等应使用 `@Schema(hidden = true)` 在文档中隐藏
- 生产环境可使用Basic认证保护API文档入口 
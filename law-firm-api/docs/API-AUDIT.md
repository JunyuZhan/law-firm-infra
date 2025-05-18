# API访问审计日志功能说明

## 1. 功能概述

API访问审计日志功能用于记录系统中所有API接口的访问情况，包括请求参数、响应结果、执行时间、操作人信息等，便于问题排查和安全审计。该功能集成了core-audit模块，统一使用系统的审计日志服务。

## 2. 功能特点

1. **完整的请求记录**：记录API请求的URI、方法、参数、请求头等信息
2. **完整的响应记录**：记录API响应结果、执行时间、状态码等信息
3. **敏感信息脱敏**：自动对密码、令牌等敏感信息进行脱敏处理
4. **异常信息记录**：记录API执行过程中的异常信息，包括异常堆栈
5. **操作人信息**：记录操作人ID、账号、IP地址等信息
6. **异步记录**：使用异步方式记录日志，不影响API响应性能
7. **统一存储**：与系统其他审计日志统一存储，便于集中查询和分析

## 3. 配置说明

在`application.yml`中配置：

```yaml
law-firm:
  api:
    audit:
      enabled: true                # 是否启用API访问审计，默认true
      exclude-paths: /static/**,/webjars/**,/error/**,/favicon.ico,/actuator/**  # 排除的路径
      sensitive-fields: password,secret,token,creditCard,idCard  # 敏感字段列表
```

可以通过环境变量进行配置：

- `API_AUDIT_ENABLED`: 是否启用API访问审计
- `API_AUDIT_EXCLUDE_PATHS`: 排除的路径
- `API_AUDIT_SENSITIVE_FIELDS`: 敏感字段列表

## 4. 实现原理

API访问审计日志功能通过Spring AOP实现，主要组件包括：

1. **ApiAuditAspect**：API访问审计切面，拦截所有API控制器方法的调用
2. **AuditService**：审计服务接口，提供日志记录功能
3. **AuditLogDTO**：审计日志数据传输对象

执行流程：

1. 拦截API请求
2. 收集请求信息（URI、方法、参数等）
3. 执行目标方法
4. 收集响应信息（结果、执行时间、异常等）
5. 构建审计日志对象
6. 异步保存审计日志

## 5. 记录的信息

API访问审计日志记录的信息包括：

| 字段 | 说明 | 示例 |
|------|------|------|
| module | 操作模块 | User |
| description | 操作描述 | UserController#getById |
| operateType | 操作类型 | QUERY |
| businessType | 业务类型 | USER |
| operatorId | 操作人ID | 1 |
| operatorName | 操作人名称 | admin |
| operatorIp | 操作人IP | 192.168.1.100 |
| status | 操作状态 | 0（成功）或 1（失败） |
| errorMsg | 错误消息 | 用户不存在 |
| operationTime | 操作时间 | 2023-08-01 12:00:00 |
| beforeData | 操作前数据（请求参数） | {"id":1} |
| afterData | 操作后数据（响应结果） | {"id":1,"name":"张三"} |
| changedFields | 变更项 | executeTime:125ms |

## 6. 查询审计日志

可以通过系统的审计日志查询功能查看API访问审计日志，支持按模块、操作类型、操作人、时间范围等条件进行查询。

## 7. 注意事项

1. API访问审计日志功能默认启用，如需禁用，可在配置中设置`law-firm.api.audit.enabled=false`
2. 敏感信息（如密码、令牌等）会自动进行脱敏处理，不会明文记录
3. 文件上传等二进制参数不会记录具体内容，只会记录`[文件上传]`标记
4. 审计日志采用异步方式记录，不会影响API响应性能 
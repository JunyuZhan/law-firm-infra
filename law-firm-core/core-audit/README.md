# Core Audit Module (核心审计模块)

## 1. 模块概述
- 业务层审计功能实现
- 支持操作/数据/安全/合规审计
- 从基础设施层到业务层的演进

## 2. 项目结构
```
core-audit/
├── src/main/java/com/lawfirm/core/audit/
│   ├── annotation/                    # 审计注解定义
│   │   ├── AuditField.java           # 字段级审计注解
│   │   ├── AuditIgnore.java          # 审计忽略注解
│   │   ├── AuditLog.java             # 方法级审计注解（已经移动到common-log中）
│   │   └── AuditModule.java          # 模块级审计注解
│   │
│   ├── aspect/                       # AOP切面实现
│   │   ├── AuditFieldAspect.java     # 字段变更切面
│   │   ├── AuditLogAspect.java       # 审计日志切面
│   │   └── AuditPointcut.java        # 切点定义
│   │
│   ├── config/                       # 配置类
│   │   ├── AsyncConfig.java          # 异步配置类
│   │   ├── AuditAutoConfiguration.java # 自动配置类
│   │   └── AuditProperties.java      # 配置属性类
│   │
│   ├── context/                      # 上下文
│   │   └── AuditContext.java         # 审计上下文
│   │
│   ├── event/                        # 事件相关
│   │   ├── AuditEvent.java           # 审计事件定义
│   │   └── AuditEventListener.java   # 事件监听器
│   │
│   ├── service/                      # 服务实现
│   │   └── impl/                     # 接口实现
│   │       ├── AuditLogServiceImpl.java
│   │       ├── AuditQueryServiceImpl.java
│   │       └── AuditServiceImpl.java
│   │
│   └── util/                         # 工具类
│       ├── AuditUtils.java           # 审计工具类
│       └── FieldChangeUtils.java     # 字段变更工具类
│
└── src/test/                         # 测试目录
    └── java/com/lawfirm/core/audit/
        ├── annotation/               # 注解测试
        ├── aspect/                   # 切面测试
        └── service/                  # 服务测试
```

## 3. 核心功能
- **操作审计**：完整操作记录
- **数据审计**：字段级变更追踪
- **安全审计**：认证授权监控
- **合规审计**：满足监管要求

## 4. 技术架构
### 4.1 模块依赖
```xml
<dependencies>
    <dependency>
        <groupId>com.lawfirm</groupId>
        <artifactId>common-log</artifactId>
    </dependency>
    <dependency>
        <groupId>com.lawfirm</groupId>
        <artifactId>common-security</artifactId>
    </dependency>
    <dependency>
        <groupId>com.lawfirm</groupId>
        <artifactId>log-model</artifactId>
    </dependency>
</dependencies>
```

### 4.2 核心组件
| 组件类型       | 实现类/接口                  | 功能说明                 |
|----------------|-----------------------------|-------------------------|
| 审计注解       | @AuditField                 | 字段级审计标记          |
| 审计注解       | @AuditLog                   | 方法级审计标记          |
| 切面处理       | AuditFieldAspect            | 字段变更切面            |
| 切面处理       | AuditLogAspect              | 审计日志切面            |
| 服务实现       | AuditServiceImpl            | 审计服务核心实现        |
| 存储适配器     | AuditLogRepository          | 多存储方式支持          |

## 5. 快速开始
### 5.1 添加依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>core-audit</artifactId>
    <version>${revision}</version>
</dependency>
```

### 5.2 使用审计注解
```java
@AuditLog(
    module = "案件管理",
    operateType = "CREATE",
    businessType = "LEGAL_CASE",
    description = "创建新案件",
    async = true
)
public Case createCase(CaseDTO dto) {
    // 业务逻辑
}
```

### 5.3 配置示例
```yaml
lawfirm:
  audit:
    enabled: true                # 启用审计功能
    storage-type: db            # 存储方式：db/es/file
    async:
      enabled: true            # 启用异步处理
      pool-size: 4             # 线程池大小
      queue-capacity: 1000     # 队列容量
    retention:
      enabled: true           # 启用数据保留策略
      days: 365              # 数据保留天数
```

## 6. 开发指南
### 6.1 审计注解参数说明
| 参数名 | 类型 | 默认值 | 说明 |
|--------|------|--------|------|
| module | String | - | 模块名称 |
| operateType | String | - | 操作类型 |
| businessType | String | - | 业务类型 |
| description | String | - | 操作描述 |
| async | boolean | true | 是否异步 |
| logParams | boolean | true | 记录参数 |
| logResult | boolean | true | 记录结果 |
| excludeFields | String[] | {} | 排除字段 |

### 6.2 服务接口说明
#### 审计服务 (AuditService)
```java
public interface AuditService {
    // 同步记录审计日志
    void log(AuditLog auditLog);
    
    // 同步记录审计记录
    void record(AuditRecord auditRecord);
    
    // 异步记录审计日志
    void logAsync(AuditLog auditLog);
    
    // 异步记录审计记录
    void recordAsync(AuditRecord auditRecord);
}
```

#### 查询服务 (AuditQueryService)
```java
public interface AuditQueryService {
    // 分页查询审计日志
    Page<AuditLog> queryAuditLogs(AuditLogQuery query);
    
    // 查询审计记录
    List<AuditRecord> queryAuditRecords(Long targetId, String targetType);
    
    // 获取单条审计日志
    AuditLog getAuditLog(Long id);
    
    // 获取单条审计记录
    AuditRecord getAuditRecord(Long id);
}
```

## 7. 高级功能
### 7.1 自定义存储
```java
@Configuration
public class CustomAuditConfig {
    @Bean
    public AuditLogRepository esRepository() {
        return new ESAuditRepository();
    }
}
```

### 7.2 安全策略
- 敏感字段自动脱敏（身份证/银行卡号等）
- 审计日志访问权限控制
- 数据加密存储（AES-256）

### 7.3 扩展点
1. 存储方式扩展
   - 实现 `AuditLogRepository` 接口
   - 在配置中指定使用的实现类

2. 审计处理扩展
   - 实现 `AuditLogProcessor` 接口
   - 通过 `@Order` 注解控制处理顺序

## 8. 安全与合规
### 8.1 数据安全
- 敏感信息自动脱敏
- 支持字段级别的访问控制
- 数据加密传输和存储

### 8.2 合规支持
- 符合等级保护要求
- 支持审计日志留痕
- 数据访问权限控制

### 8.3 审计策略
- 重要操作必须审计
- 敏感数据变更记录
- 异常行为跟踪

## 9. 运维支持
### 9.1 监控指标
| 指标名称 | 说明 | 告警阈值 |
|---------|------|---------|
| audit_log_total | 审计日志总量 | - |
| async_queue_size | 异步队列积压量 | >500 |
| process_delay | 处理延迟时间 | >5s |
| storage_usage | 存储空间使用率 | >90% |

### 9.2 告警配置
```yaml
alerts:
  - name: AuditQueueBacklog
    condition: async_queue_size > 500
    severity: WARNING
  - name: StorageCritical
    condition: storage_usage > 90%
    severity: CRITICAL
```

### 9.3 日志清理
- 自动清理过期数据
- 支持数据归档
- 清理任务可配置

## 10. 最佳实践
1. **注解使用规范**
   - 仅标注业务入口方法
   - 避免在循环内部使用
   - 合理设置异步开关

2. **性能优化建议**
   - 批量操作启用异步模式
   - 定期清理历史数据
   - 使用ES存储时配置分片策略

3. **安全建议**
   - 定期更新加密密钥
   - 控制审计日志访问权限
   - 配置敏感字段脱敏规则

## 11. 后续规划
1. 支持更多存储方式
2. 优化查询性能
3. 增强数据分析能力
4. 提供审计大屏展示

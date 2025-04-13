# common-message 消息通用模块

## 模块说明
common-message 模块提供了统一的消息处理功能，主要包含消息路由、重试机制、消息过滤等核心功能。该模块采用责任链模式实现，为上层应用提供灵活的消息处理能力。

## 功能特性

### 1. 消息路由
- 支持多种消息路由策略
- 动态路由配置
- 路由规则管理
- 路由优先级控制

### 2. 消息重试
- 可配置的重试策略
- 重试次数限制
- 重试间隔控制
- 重试状态追踪

### 3. 消息过滤
- 消息内容过滤
- 消息类型过滤
- 消息来源过滤
- 自定义过滤规则

### 4. 消息发送
- 多通道消息发送
- 消息模板支持
- 异步发送处理
- 发送状态追踪

## 核心组件

### 1. 消息处理器 (handler)
- **MessageRouteHandler**: 消息路由处理器
  - 负责消息的路由分发
  - 支持多种路由策略
  - 可配置路由规则
- **MessageRetryHandler**: 消息重试处理器
  - 处理消息重试逻辑
  - 管理重试状态
  - 控制重试间隔
- **MessageFilterHandler**: 消息过滤处理器
  - 实现消息过滤逻辑
  - 支持多种过滤规则
  - 可扩展过滤策略

### 2. 消息支持 (support)
- **MessageTemplateSupport**: 消息模板支持
  - 模板管理
  - 模板渲染
  - 变量替换
- **MessageSenderSupport**: 消息发送支持
  - 发送通道管理
  - 发送状态追踪
  - 发送结果处理
- **MessageChannelSupport**: 消息通道支持
  - 通道配置管理
  - 通道状态监控
  - 通道异常处理

## 使用说明

### 1. 添加依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-message</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 配置示例
```yaml
message:
  route:
    enabled: true
    strategy: round-robin
    rules:
      - type: email
        priority: 1
        channels: [smtp, api]
      - type: sms
        priority: 2
        channels: [aliyun, tencent]
  
  retry:
    enabled: true
    max-attempts: 3
    initial-interval: 1000
    multiplier: 2.0
    max-interval: 10000
  
  filter:
    enabled: true
    rules:
      - type: content
        pattern: .*test.*
        action: reject
      - type: source
        pattern: .*internal.*
        action: accept
```

### 3. 使用示例
```java
@Service
public class MessageService {
    
    @Autowired
    private MessageRouteHandler routeHandler;
    
    @Autowired
    private MessageRetryHandler retryHandler;
    
    @Autowired
    private MessageFilterHandler filterHandler;
    
    public void processMessage(Message message) {
        // 消息过滤
        if (!filterHandler.filter(message)) {
            return;
        }
        
        // 消息路由
        MessageChannel channel = routeHandler.route(message);
        
        // 消息发送（带重试）
        retryHandler.handle(() -> {
            channel.send(message);
        });
    }
}
```

## 注意事项

1. 消息路由
   - 合理配置路由规则
   - 注意路由优先级
   - 避免路由循环

2. 消息重试
   - 设置合理的重试次数
   - 控制重试间隔
   - 处理重试异常

3. 消息过滤
   - 注意过滤规则顺序
   - 避免过度过滤
   - 及时更新过滤规则

## 常见问题

1. Q: 如何处理消息路由失败？
   A: 系统会自动选择备用通道，如果所有通道都失败，会进入重试流程。

2. Q: 如何配置消息重试策略？
   A: 可以通过配置文件设置重试次数、间隔等参数，支持动态调整。

3. Q: 如何添加自定义过滤规则？
   A: 实现 MessageFilter 接口，并在配置中注册即可。

## 相关文档
- [消息路由设计文档](../../docs/message-route-design.md)
- [消息重试配置说明](../../docs/message-retry-config.md)
- [消息过滤规则说明](../../docs/message-filter-rules.md) 
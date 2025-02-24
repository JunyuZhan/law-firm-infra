# common-message 消息通用模块

## 模块说明

common-message 模块提供了统一的消息处理功能，支持多种消息类型和发送渠道，包括站内信、邮件、短信、微信和WebSocket等。该模块实现了消息的发送、存储、查询和管理等核心功能。

## 功能特性

### 1. 消息发送
- 多渠道消息发送(站内信、邮件、短信、微信、WebSocket)
- 消息模板支持
- 异步发送处理
- 延迟消息支持
- 消息优先级

### 2. 消息处理
- 消息幂等处理
- 消息限流控制
- 消息重试机制
- 消息队列支持
- 死信队列处理

### 3. 消息存储
- 消息持久化存储
- 消息缓存处理
- 消息状态管理
- 消息历史记录
- 消息清理策略

### 4. 消息监控
- 消息发送统计
- 消息处理耗时
- 成功/失败统计
- 重试次数统计
- 消息状态监控

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
  processing:
    # 幂等处理配置
    idempotent:
      enabled: true
      expire-hours: 24
    
    # 限流配置
    rate-limit:
      enabled: true
      capacity: 1000
      rate: 100
    
    # 批处理配置
    batch:
      enabled: true
      max-size: 100
      wait-timeout: 1000
    
    # 重试配置
    retry:
      enabled: true
      max-attempts: 3
      initial-interval: 1000
      multiplier: 2.0
      max-interval: 10000
```

### 3. 消息发送示例

```java
// 发送邮件
emailService.send("user@example.com", "主题", "内容", true);

// 发送短信
smsService.send("13800138000", "内容", "template_code", params);

// 发送微信消息
wechatService.send("openid", "template_id", "url", data);

// 发送WebSocket消息
webSocketService.send("userId", message);
```

### 4. 消息模板使用

```java
// 处理模板消息
String messageId = messageService.processTemplateMessage(
    template,
    params,
    receiverId,
    businessType,
    businessId
);
```

## 核心组件

### 1. 消息发送器
- MessageSender: 消息发送策略接口
- EmailService: 邮件发送服务
- SmsService: 短信发送服务
- WechatService: 微信消息服务
- WebSocketService: WebSocket消息服务

### 2. 消息处理器
- MessageIdempotentHandler: 消息幂等处理器
- MessageRateLimiter: 消息限流处理器
- MessageRetryHandler: 消息重试处理器
- MessageQueueService: 消息队列服务
- MessageCacheService: 消息缓存服务

### 3. 消息监控
- MessageMetrics: 消息监控指标
  - 总消息数统计
  - 成功消息统计
  - 失败消息统计
  - 重试消息统计
  - 消息处理耗时

## 注意事项

1. 消息发送建议使用异步方式
2. 重要消息需要启用重试机制
3. 注意消息幂等性处理
4. 合理配置限流参数
5. 定期清理历史消息

## 常见问题

1. Q: 如何处理消息发送失败？
   A: 系统会自动重试，也可以通过死信队列处理失败消息。

2. Q: 如何避免消息重复发送？
   A: 启用幂等处理功能，系统会自动处理重复消息。

3. Q: 如何处理大量消息发送？
   A: 使用批量处理和限流功能，避免系统过载。

## 相关文档

- [消息发送规范](../../development/message-spec.md)
- [消息模板配置](../../development/template-config.md)
- [消息监控说明](../../operation/message-monitor.md) 
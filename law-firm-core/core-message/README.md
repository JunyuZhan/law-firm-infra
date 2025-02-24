# 消息处理模块 (Core Message)

## 模块说明
消息处理模块是律师事务所管理系统的核心消息处理模块，负责系统内所有消息的发送、接收、路由和处理。该模块提供了统一的消息处理机制，支持多种消息类型和处理方式。

## 核心功能

### 1. 消息发送
- 同步消息发送
- 异步消息发送
- 延时消息发送
- 批量消息发送
- 消息重试机制

### 2. 消息接收
- 消息监听
- 消息过滤
- 消息路由
- 消息确认
- 消息重投

### 3. 消息处理
- 消息解析
- 消息验证
- 消息转换
- 消息处理
- 消息响应

### 4. 消息管理
- 消息存储
- 消息查询
- 消息统计
- 消息监控
- 消息追踪

## 核心组件

### 1. 消息发送器
- MessageSender：消息发送接口
- MessageSenderImpl：默认发送实现
- AsyncMessageSender：异步发送实现
- BatchMessageSender：批量发送实现

### 2. 消息监听器
- MessageListener：消息监听接口
- MessageHandler：消息处理接口
- MessageFilter：消息过滤接口
- MessageRouter：消息路由接口

### 3. 消息处理器
- MessageProcessor：消息处理器接口
- MessageConverter：消息转换器
- MessageValidator：消息验证器
- MessageResolver：消息解析器

### 4. 消息管理器
- MessageManager：消息管理接口
- MessageRepository：消息存储接口
- MessageTracer：消息追踪器
- MessageMonitor：消息监控器

## 使用示例

### 1. 发送消息
```java
@Autowired
private MessageSender messageSender;

public void sendMessage() {
    Message message = new Message()
        .setType(MessageType.NOTIFICATION)
        .setContent("消息内容")
        .setPriority(MessagePriority.HIGH);
    
    messageSender.send(message);
}
```

### 2. 处理消息
```java
@Component
@MessageHandler(type = MessageType.NOTIFICATION)
public class NotificationHandler implements MessageProcessor {
    
    @Override
    public void process(Message message) {
        // 处理消息
        log.info("处理消息: {}", message);
    }
}
```

### 3. 监听消息
```java
@Component
public class CustomMessageListener implements MessageListener {
    
    @Override
    public void onMessage(Message message) {
        // 监听到消息
        log.info("收到消息: {}", message);
    }
}
```

## 配置说明

### 1. 消息配置
```yaml
message:
  # 消息发送配置
  sender:
    retry-times: 3
    retry-interval: 1000
    async-pool-size: 5
    
  # 消息接收配置
  receiver:
    pool-size: 10
    max-concurrent: 100
    
  # 消息存储配置
  storage:
    type: redis
    ttl: 7d
```

### 2. 监控配置
```yaml
monitor:
  # 监控配置
  message:
    enabled: true
    metrics-prefix: message
    sample-rate: 100
```

## 注意事项
1. 消息幂等性处理
2. 消息顺序性保证
3. 消息可靠性投递
4. 消息处理性能
5. 消息监控告警 
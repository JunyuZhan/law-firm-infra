# Core Message Module

## 1. 模块介绍

`core-message` 是律所管理系统的核心消息模块，提供统一的消息处理服务。该模块支持多种消息类型，包括系统通知、业务消息、实时通讯等。

## 2. 核心功能

### 2.1 消息发送
- 系统通知
- 业务消息
- 实时通讯
- 批量发送
- 定时发送

### 2.2 消息接收
- 消息订阅
- 消息推送
- 消息确认
- 消息过滤
- 消息分发

### 2.3 消息管理
- 消息模板
- 消息状态
- 消息历史
- 消息统计
- 消息追踪

### 2.4 消息通道
- WebSocket
- RabbitMQ
- 邮件
- 短信
- 站内信

## 3. 技术架构

### 3.1 核心组件
```
com.lawfirm.core.message/
├── config/                 # 配置类
│   ├── MessageProperties  # 消息配置属性
│   ├── RabbitConfig      # RabbitMQ配置
│   └── WebSocketConfig   # WebSocket配置
├── service/               # 服务层
│   ├── MessageService    # 消息服务
│   ├── TemplateService   # 模板服务
│   └── ChannelService    # 通道服务
├── model/                # 数据模型
│   ├── entity/          # 实体类
│   ├── dto/             # 传输对象
│   └── enums/           # 枚举类型
└── handler/             # 消息处理器
```

### 3.2 数据库设计
```sql
-- 消息记录表
message_record
  - id            # 主键
  - type          # 消息类型
  - title         # 消息标题
  - content       # 消息内容
  - sender_id     # 发送者ID
  - receiver_id   # 接收者ID
  - status        # 消息状态
  - send_time     # 发送时间
  - read_time     # 阅读时间

-- 消息模板表
message_template
  - id            # 主键
  - code          # 模板编码
  - name          # 模板名称
  - content       # 模板内容
  - params        # 参数定义
  - type          # 模板类型
  - status        # 模板状态

-- 消息通道表
message_channel
  - id            # 主键
  - code          # 通道编码
  - name          # 通道名称
  - type          # 通道类型
  - config        # 通道配置
  - status        # 通道状态
```

## 4. 接口设计

### 4.1 消息服务接口
```java
public interface MessageService {
    void sendMessage(Message message);
    void sendBatchMessages(List<Message> messages);
    void sendScheduledMessage(Message message, Date scheduleTime);
    List<Message> receiveMessages(Long userId);
    void markAsRead(Long messageId);
}
```

### 4.2 模板服务接口
```java
public interface TemplateService {
    String processTemplate(String templateCode, Map<String, Object> params);
    void createTemplate(MessageTemplate template);
    void updateTemplate(MessageTemplate template);
    void deleteTemplate(String templateCode);
}
```

### 4.3 通道服务接口
```java
public interface ChannelService {
    void sendViaChannel(String channelCode, Message message);
    void registerChannel(MessageChannel channel);
    void updateChannel(MessageChannel channel);
    void disableChannel(String channelCode);
}
```

## 5. 配置说明

### 5.1 RabbitMQ配置
```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    virtual-host: /
    listener:
      simple:
        concurrency: 5
        max-concurrency: 10
```

### 5.2 WebSocket配置
```yaml
websocket:
  endpoint: /ws
  allowed-origins: "*"
  heartbeat:
    interval: 60000
    timeout: 25000
```

## 6. 使用示例

### 6.1 发送消息
```java
@Autowired
private MessageService messageService;

public void sendNotification() {
    Message message = new Message()
        .setType(MessageType.NOTIFICATION)
        .setTitle("系统通知")
        .setContent("您有新的待办任务")
        .setReceiverId(userId);
    
    messageService.sendMessage(message);
}
```

### 6.2 使用模板
```java
@Autowired
private TemplateService templateService;

public void sendWithTemplate() {
    Map<String, Object> params = new HashMap<>();
    params.put("userName", "张三");
    params.put("taskName", "合同审批");
    
    String content = templateService.processTemplate("task-notification", params);
    // 使用处理后的内容发送消息
}
```

### 6.3 WebSocket实时通讯
```java
@MessageMapping("/chat")
@SendTo("/topic/messages")
public ChatMessage handleChat(ChatMessage message) {
    // 处理聊天消息
    return message;
}
```

## 7. 测试说明

### 7.1 单元测试
```java
@Test
void sendMessage_Success() {
    Message message = createTestMessage();
    messageService.sendMessage(message);
    
    Message received = messageService.receiveMessages(userId).get(0);
    assertEquals(message.getContent(), received.getContent());
}
```

### 7.2 集成测试
```java
@SpringBootTest
class MessageIntegrationTest {
    @Autowired
    private MessageService messageService;
    
    @Test
    void testMessageFlow() {
        // 完整消息流程测试
    }
}
```

## 8. 性能优化

### 8.1 消息发送优化
- 使用消息队列异步处理
- 批量发送优化
- 消息压缩
- 连接池管理

### 8.2 消息存储优化
- 分表分库
- 消息归档
- 冷热数据分离
- 索引优化

### 8.3 实时通讯优化
- 心跳机制
- 消息压缩
- 连接复用
- 集群支持

## 9. 扩展性设计

### 9.1 消息处理器
```java
public interface MessageHandler {
    boolean support(MessageType type);
    void handle(Message message);
}
```

### 9.2 通道适配器
```java
public interface ChannelAdapter {
    boolean support(ChannelType type);
    void send(Message message);
}
```

## 10. 最佳实践

### 10.1 消息设计
- 定义清晰的消息格式
- 合理设置消息大小
- 处理消息幂等性
- 实现消息追踪

### 10.2 高可用设计
- 消息持久化
- 失败重试
- 集群部署
- 灾备方案

### 10.3 安全考虑
- 消息加密
- 身份认证
- 访问控制
- 敏感信息处理

## 11. 监控告警

### 11.1 监控指标
- 消息吞吐量
- 消息延迟
- 发送成功率
- 系统资源使用

### 11.2 告警规则
- 消息堆积告警
- 发送失败告警
- 系统负载告警
- 连接异常告警 
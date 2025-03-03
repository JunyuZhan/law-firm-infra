# 消息处理模块 (Core Message)

## 模块说明
消息处理模块是律师事务所管理系统的核心消息处理模块，负责系统内所有消息的发送、接收、路由和处理。该模块提供了统一的消息处理机制，支持多种消息类型和处理方式。

## 目录结构
```
core-message/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/lawfirm/core/message/
│   │   │       ├── config/            # 配置类
│   │   │       │   ├── MessageConfig.java       # 消息基础配置
│   │   │       │   └── MessageRedisConfig.java  # 消息存储配置
│   │   │       │
│   │   │       ├── service/           # 服务实现
│   │   │       │   ├── impl/         # 核心实现
│   │   │       │   │   ├── MessageSenderImpl.java     # 基础发送实现
│   │   │       │   │   ├── AsyncMessageSenderImpl.java # 异步发送
│   │   │       │   │   └── MessageManagerImpl.java    # 消息管理
│   │   │       │   │
│   │   │       │   └── strategy/     # 策略模式实现
│   │   │       │       ├── RetryStrategy.java       # 重试策略
│   │   │       │       └── RoutingStrategy.java     # 路由策略
│   │   │       │
│   │   │       ├── handler/          # 消息处理器
│   │   │       │   ├── NotificationHandler.java    # 通知类处理
│   │   │       │   ├── AlertHandler.java           # 预警处理
│   │   │       │   └── template/     # 处理模板
│   │   │       │       └── BaseMessageHandler.java # 基础处理模板
│   │   │       │
│   │   │       ├── listener/         # 消息监听器
│   │   │       │   ├── RedisMessageListener.java   # Redis监听
│   │   │       │   └── RocketMQListener.java       # RocketMQ监听
│   │   │       │
│   │   │       ├── exception/        # 异常类
│   │   │       │   ├── MessageSendException.java
│   │   │       │   └── MessageProcessException.java
│   │   │       │
│   │   │       └── utils/            # 工具类
│   │   │           ├── MessageIdGenerator.java    # ID生成器
│   │   │           └── MessageLogUtils.java       # 日志工具
│   │   │
│   │   └── resources/
│   │       └── message.properties    # 消息模块配置
│   │
│   └── test/                         # 测试目录
│       ├── java/
│       │   └── com/weidisoft/law/firm/core/message/
│       │       ├── service/
│       │       └── handler/
│       └── resources/
│           └── message-test.properties # 测试配置
└── pom.xml                           # 模块依赖定义
```

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

## 开发规范

### 1. 模块职责
- 仅包含消息处理逻辑实现
- 所有数据模型定义位于 `message-model` 模块
- 不在本模块定义任何版本号，统一由父POM管理

### 2. 开发要求
- 实现类必须实现 `message-model` 中定义的接口
- 异常处理必须使用 `exception` 包中定义的异常类
- 消息处理器必须继承 `BaseMessageHandler`
- 配置项统一在 `message.properties` 中管理
- 单元测试覆盖率 > 80%
- 性能要求：
  - 消息处理延迟 < 100ms
  - 并发处理能力 > 1000 TPS

## 配置说明
```yaml
message:
  sender:
    retry-times: 3
    retry-interval: 1000
    async-pool-size: 5
  receiver:
    pool-size: 10
    max-concurrent: 100
  storage:
    type: redis
    ttl: 7d
```

## 监控指标
- 消息处理成功率
- 消息处理延迟
- 消息队列深度
- 处理器性能指标
- 错误率统计

## 注意事项
1. 消息幂等性处理
2. 消息顺序性保证
3. 消息可靠性投递
4. 消息处理性能
5. 消息监控告警

## 使用示例

### 1. REST API调用示例

```http
# 发送系统通知
POST /api/v1/messages/notify
{
    "title": "系统维护通知",
    "content": "系统将于今晚22:00进行维护升级",
    "receivers": ["user1", "user2"],
    "channel": "INTERNAL"
}

# 发送案件消息
POST /api/v1/messages/case
{
    "caseId": "CASE2024001",
    "title": "案件状态更新",
    "content": "案件已进入审核阶段",
    "receivers": ["lawyer1", "client1"]
}

# 发送系统预警
POST /api/v1/messages/alert
{
    "level": "HIGH",
    "title": "系统异常预警",
    "content": "数据库连接异常，请及时处理"
}

# 批量发送通知
POST /api/v1/messages/notify/batch
{
    "title": "节日问候",
    "content": "祝您新年快乐！",
    "receiverGroups": [
        ["group1-user1", "group1-user2"],
        ["group2-user1", "group2-user2"]
    ],
    "channel": "SMS"
}

# 获取消息详情
GET /api/v1/messages/{messageId}

# 删除消息
DELETE /api/v1/messages/{messageId}
```

### 2. Java代码调用示例

```java
@Service
public class BusinessService {
    @Autowired
    private MessageFacade messageFacade;

    /**
     * 案件状态变更通知示例
     */
    public void notifyCaseStatusChange(String caseId, String newStatus) {
        // 1. 获取案件相关人员
        List<String> receivers = getCaseRelatedUsers(caseId);
        
        // 2. 发送案件消息
        String title = "案件状态更新通知";
        String content = String.format("案件（ID: %s）状态已更新为：%s", caseId, newStatus);
        messageFacade.sendCaseMessage(caseId, title, content, receivers);
        
        // 3. 如果是重要状态，还需要发送短信通知
        if (isImportantStatus(newStatus)) {
            messageFacade.sendSystemNotify(
                title,
                content,
                receivers,
                NotifyChannelEnum.SMS
            );
        }
    }

    /**
     * 系统异常预警示例
     */
    public void handleSystemException(String service, Exception e) {
        messageFacade.sendSystemAlert(
            "HIGH",
            service + " 服务异常",
            String.format("异常信息：%s", e.getMessage())
        );
    }

    /**
     * 批量通知示例
     */
    public void sendBatchNotification(String announcement) {
        // 1. 获取用户分组
        List<List<String>> userGroups = getUserGroups();
        
        // 2. 发送站内通知
        messageFacade.sendBatchNotify(
            "系统公告",
            announcement,
            userGroups,
            NotifyChannelEnum.INTERNAL
        );
    }
}
```

</rewritten_file>
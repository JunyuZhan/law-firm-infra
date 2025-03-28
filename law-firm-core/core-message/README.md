# 法律事务管理系统 - 消息处理模块

## 模块说明

消息处理模块提供了统一的系统消息服务，支持站内消息、邮件通知和短信通知等多种消息类型。本模块采用模板策略模式，支持同步和异步发送消息。本模块仅作为服务接口层提供给业务模块使用，不直接暴露REST接口。

## 核心功能

### 1. 消息类型支持
- 站内消息
- 电子邮件
- 短信通知

### 2. 消息模板管理
- 模板定义与维护
- 模板变量替换
- 多语言模板支持
- 模板版本控制

### 3. 消息发送服务
- 同步消息发送
- 异步消息发送
- 批量消息发送
- 消息发送状态追踪

### 4. 消息处理机制
- 消息路由策略
- 消息处理器链
- 消息重试机制
- 消息优先级处理

## 技术架构

### 1. 核心服务接口
- MessageManager: 消息管理服务接口
- MessageSender: 消息发送服务接口
- AsyncMessageSender: 异步消息发送服务接口
- MessageTemplateService: 消息模板服务接口

### 2. 组件设计
- BaseMessageHandler: 基础消息处理器
- MessageFactory: 消息工厂类
- RoutingStrategy: 消息路由策略
- TemplateEngine: 模板引擎封装

### 3. 数据模型
- BaseMessage: 基础消息实体
- SystemMessage: 系统消息
- EmailMessage: 电子邮件消息
- SmsMessage: 短信消息
- MessageTemplate: 消息模板

## 使用说明

### 1. 配置要求
```yaml
lawfirm:
  message:
    enabled: true
    async-enabled: true
    retry-max-attempts: 3
    templates-location: classpath:templates/message
    # 邮件配置
    email:
      enabled: true
      host: smtp.example.com
      port: 587
      username: ${EMAIL_USERNAME}
      password: ${EMAIL_PASSWORD}
      default-encoding: UTF-8
    # 短信配置
    sms:
      enabled: true
      provider: aliyun
      access-key: ${SMS_ACCESS_KEY}
      secret-key: ${SMS_SECRET_KEY}
      sign-name: 律师事务所
```

### 2. 服务接口调用示例
```java
// 注入服务
@Autowired
private MessageSender messageSender;

@Autowired
private AsyncMessageSender asyncMessageSender;

@Autowired
private MessageTemplateService templateService;

// 发送系统通知
MessageResult result = messageSender.sendSystemMessage(
    "新任务通知", 
    "您有一个新的合同审核任务，请尽快处理", 
    Arrays.asList("user123", "user456")
);

// 使用模板发送邮件
Map<String, Object> templateParams = new HashMap<>();
templateParams.put("userName", "张律师");
templateParams.put("caseId", "CASE-2024-001");
templateParams.put("caseTitle", "某公司合同纠纷案");

MessageResult emailResult = messageSender.sendTemplateEmail(
    "case_assignment",              // 模板代码
    templateParams,                 // 模板参数
    "新案件分配通知",                // 邮件主题
    Collections.singletonList("lawyer@example.com")  // 收件人列表
);

// 异步发送短信
asyncMessageSender.sendSms(
    "您的案件(CASE-2024-001)有新的进展，请登录系统查看详情",
    Collections.singletonList("13812345678")
);
```

### 3. 自定义消息处理器

```java
@Component
public class ContractNotificationHandler extends BaseMessageHandler<SystemMessage> {
    
    @Override
    public boolean canHandle(MessageTypeEnum type) {
        return MessageTypeEnum.SYSTEM == type;
    }
    
    @Override
    public void doHandle(String messageId, SystemMessage message) {
        // 合同通知的特殊处理逻辑
        if (message.getTitle().contains("合同")) {
            // 执行合同相关的特殊处理
        }
        
        // 调用下一个处理器
        if (nextHandler != null) {
            nextHandler.handle(messageId, message);
        }
    }
}
```

## 安全说明

1. 消息安全
   - 敏感信息保护
   - 消息加密传输
   - 发送频率限制

2. 模板安全
   - 模板访问控制
   - 模板内容验证
   - 模板注入防护

3. 发送安全
   - 身份认证与授权
   - IP白名单控制
   - 操作日志审计
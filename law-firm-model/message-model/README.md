# 消息管理模块

## 模块说明
消息管理模块提供了律师事务所系统中消息和通知相关的核心数据模型和服务接口定义。该模块主要包含消息管理、通知管理等功能的基础数据结构和服务定义。

## 目录结构
```
message-model/
├── entity/
│   ├── base/             # 基础消息
│   │   ├── BaseMessage.java      # 消息基类
│   │   └── BaseNotify.java       # 通知基类
│   ├── system/           # 系统消息
│   │   └── SystemMessage.java    # 系统消息
│   └── business/         # 业务消息
│       ├── CaseMessage.java      # 案件消息
│       └── ContractMessage.java  # 合同消息
├── enums/
│   ├── MessageTypeEnum.java    # 消息类型
│   ├── MessageStatusEnum.java  # 消息状态
│   └── NotifyChannelEnum.java  # 通知渠道
├── dto/
│   ├── message/
│   │   ├── MessageCreateDTO.java # 消息创建
│   │   └── MessageQueryDTO.java  # 消息查询
│   └── notify/
│       ├── NotifyCreateDTO.java  # 通知创建
│       └── NotifyQueryDTO.java   # 通知查询
├── vo/
│   ├── MessageVO.java      # 消息视图
│   └── NotifyVO.java       # 通知视图
└── service/
    ├── MessageService.java  # 消息服务
    └── NotifyService.java   # 通知服务
```

## 核心功能

### 1. 消息管理
- 消息类型：支持系统消息、案件消息、合同消息等多种类型
- 消息状态：跟踪消息的发送、接收、阅读等状态
- 消息查询：提供灵活的消息查询功能
- 消息处理：支持消息的创建、发送、阅读等操作

### 2. 通知管理
- 通知渠道：支持站内信、短信、邮件等多种通知渠道
- 通知模板：支持自定义通知模板
- 通知发送：支持即时发送和定时发送
- 通知状态：跟踪通知的发送状态和送达状态

## 主要类说明

### 实体类
1. BaseMessage
   - 消息基类，定义消息的基本属性
   - 包含：消息ID、标题、内容、发送者、接收者等

2. BaseNotify
   - 通知基类，定义通知的基本属性
   - 包含：通知ID、通知内容、通知渠道、发送时间等

3. SystemMessage
   - 系统消息实现，继承自BaseMessage
   - 适用于系统级别的消息通知

4. CaseMessage
   - 案件消息实现，继承自BaseMessage
   - 适用于案件相关的消息通知

5. ContractMessage
   - 合同消息实现，继承自BaseMessage
   - 适用于合同相关的消息通知

### 枚举类
1. MessageTypeEnum
   - 定义消息类型：如系统消息、案件消息、合同消息等

2. MessageStatusEnum
   - 定义消息状态：如未读、已读、已删除等

3. NotifyChannelEnum
   - 定义通知渠道：如站内信、短信、邮件等

### DTO类
1. MessageCreateDTO
   - 消息创建数据传输对象
   - 包含创建消息所需的必要信息

2. MessageQueryDTO
   - 消息查询数据传输对象
   - 包含查询消息所需的条件信息

3. NotifyCreateDTO
   - 通知创建数据传输对象
   - 包含创建通知所需的必要信息

4. NotifyQueryDTO
   - 通知查询数据传输对象
   - 包含查询通知所需的条件信息

### VO类
1. MessageVO
   - 消息视图对象
   - 用于展示消息信息

2. NotifyVO
   - 通知视图对象
   - 用于展示通知信息

### 服务接口
1. MessageService
   - 提供消息相关的业务操作接口
   - 包含消息的CRUD操作

2. NotifyService
   - 提供通知相关的业务操作接口
   - 包含通知的CRUD操作

## 使用说明
1. 引入依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>message-model</artifactId>
    <version>${project.version}</version>
</dependency>
```

2. 继承基类
```java
public class CustomMessage extends BaseMessage {
    // 自定义消息实现
}
```

3. 使用服务接口
```java
@Autowired
private MessageService messageService;

// 创建消息
MessageCreateDTO createDTO = new MessageCreateDTO();
Long messageId = messageService.createMessage(createDTO);

// 查询消息
MessageVO message = messageService.getMessage(messageId);
```

## 注意事项
1. 所有实体类都继承自BaseModel，确保基础字段的一致性
2. DTO类继承自BaseDTO，VO类继承自BaseVO
3. 遵循统一的命名规范和代码风格
4. 确保完整的单元测试覆盖
5. 注意消息发送的性能和可靠性
6. 考虑消息存储的容量和清理策略
7. 所有可序列化的类都必须定义serialVersionUID字段，避免序列化兼容性问题

/**
 * 确保所有可序列化的类（包括：DTO、VO、Entity等）都定义了serialVersionUID字段：
 * private static final long serialVersionUID = 1L;
 * 
 * 这对于Java对象的序列化和反序列化过程中的版本兼容性非常关键。
 * 如果类结构发生变化但serialVersionUID保持不变，JVM仍然会尝试反序列化。
 * 
 * 目前已确保以下类添加了serialVersionUID：
 * - BaseMessage 及其子类（SystemMessage, CaseMessage, ContractMessage等）
 * - BaseNotify 及其子类（SystemNotify等）
 * - 各种DTO（MessageCreateDTO, MessageQueryDTO, NotifyCreateDTO, NotifyQueryDTO等）
 * - 各种VO（MessageVO, NotifyVO等）
 */ 

## 迁移记录

### 2024-04-28 JPA到MyBatis Plus迁移
- 添加MyBatis Plus相关注解（@TableName、@TableField）
- 移除JPA相关注解和导入（@Entity、@Table、@Column等）
- 修改pom.xml，移除JPA依赖，添加MyBatis Plus依赖
- 以下实体类已完成迁移：
  - BaseMessage.java（基础消息类）
  - BaseNotify.java（基础通知类）
  - CaseMessage.java（案件消息类）
  - ContractMessage.java（合同消息类） 
# 消息管理模块

## 1. 功能概述

消息管理模块是律师事务所管理系统的内部沟通平台，用于管理系统内的各类消息通知，包括系统通知、业务消息、即时通讯等，提供统一的消息发送、接收、管理功能。

## 2. 页面结构

```bash
views/message/
├── list/                # 消息列表页
│   ├── index.vue       # 列表主页面
│   └── components/     # 列表页组件
│       ├── SearchForm.vue      # 搜索表单
│       ├── TableColumns.vue    # 表格列配置
│       ├── MessageType.vue     # 消息类型
│       └── MessageStatus.vue   # 消息状态
│
├── detail/             # 消息详情页
│   ├── index.vue      # 详情主页面
│   └── components/    # 详情页组件
│       ├── BasicInfo.vue       # 基本信息
│       ├── Content.vue         # 消息内容
│       ├── Attachments.vue     # 附件管理
│       └── ReadStatus.vue      # 阅读状态
│
├── chat/              # 即时通讯
│   ├── index.vue     # 聊天主页面
│   └── components/   # 聊天组件
│       ├── ChatList.vue        # 会话列表
│       ├── ChatWindow.vue      # 聊天窗口
│       ├── MessageInput.vue    # 消息输入
│       └── FileUpload.vue      # 文件上传
│
└── components/        # 公共组件
    ├── MessageBox.vue         # 消息盒子
    ├── NotificationBell.vue   # 通知铃铛
    └── MessageTemplate.vue    # 消息模板
```

## 3. 功能清单

### 3.1 消息列表
- [x] 消息搜索
  - 消息标题
  - 消息类型
  - 消息状态
  - 发送人
  - 接收人
  - 时间范围
  - 关键词
  
- [x] 消息分类
  - 系统通知
  - 业务消息
  - 私信消息
  - 群组消息
  - 公告通知

- [x] 批量操作
  - 批量已读
  - 批量删除
  - 批量转发
  - 批量归档

### 3.2 消息详情
- [x] 基本信息
  - 消息属性
    - 消息标题
    - 消息类型
    - 发送时间
    - 发送人
    - 接收人
    - 重要程度
    - 消息状态
  - 消息内容
    - 文本内容
    - 富文本内容
    - 图片内容
    - 附件内容

- [x] 阅读状态
  - 已读人员
  - 未读人员
  - 阅读时间
  - 阅读统计

- [x] 消息处理
  - 回复消息
  - 转发消息
  - 撤回消息
  - 删除消息

### 3.3 即时通讯
- [x] 会话管理
  - 个人会话
  - 群组会话
  - 会话置顶
  - 会话免打扰

- [x] 消息类型
  - 文本消息
  - 图片消息
  - 文件消息
  - 语音消息
  - 视频消息
  - 位置消息

- [x] 群组功能
  - 创建群组
  - 群组管理
  - 群组公告
  - 群组设置

### 3.4 消息通知
- [x] 通知方式
  - 站内通知
  - 邮件通知
  - 短信通知
  - 微信通知
  - APP推送

- [x] 通知设置
  - 通知级别
  - 免打扰时段
  - 通知模板
  - 订阅设置

## 4. API 接口

### 4.1 数据结构
```typescript
// 消息基本信息
interface Message {
  id: string;                 // 消息ID
  title: string;             // 消息标题
  type: MessageType;         // 消息类型
  content: string;           // 消息内容
  priority: Priority;        // 重要程度
  senderId: string;          // 发送人ID
  receiverIds: string[];     // 接收人ID列表
  status: MessageStatus;     // 消息状态
  createTime: string;        // 创建时间
  updateTime: string;        // 更新时间
}

// 消息类型
enum MessageType {
  SYSTEM = 'system',        // 系统通知
  BUSINESS = 'business',    // 业务消息
  PRIVATE = 'private',      // 私信消息
  GROUP = 'group',          // 群组消息
  ANNOUNCEMENT = 'announcement' // 公告通知
}

// 消息状态
enum MessageStatus {
  UNREAD = 'unread',       // 未读
  READ = 'read',           // 已读
  WITHDRAWN = 'withdrawn', // 已撤回
  DELETED = 'deleted'      // 已删除
}

// 聊天消息
interface ChatMessage {
  id: string;              // 消息ID
  sessionId: string;       // 会话ID
  type: ChatMessageType;   // 消息类型
  content: string;         // 消息内容
  senderId: string;        // 发送人ID
  status: SendStatus;      // 发送状态
  createTime: string;      // 发送时间
}

// 会话信息
interface ChatSession {
  id: string;              // 会话ID
  type: SessionType;       // 会话类型
  name: string;            // 会话名称
  avatar: string;          // 会话头像
  memberIds: string[];     // 成员ID列表
  lastMessage: ChatMessage;// 最后一条消息
  unreadCount: number;     // 未读数量
}
```

### 4.2 接口列表
```typescript
// 获取消息列表
export const getMessageList = (params: MessageListParams) => {
  return http.get('/message/list', { params });
};

// 获取消息详情
export const getMessageDetail = (id: string) => {
  return http.get(`/message/detail/${id}`);
};

// 发送消息
export const sendMessage = (data: Partial<Message>) => {
  return http.post('/message/send', data);
};

// 标记已读
export const markAsRead = (ids: string[]) => {
  return http.post('/message/mark-read', { ids });
};

// 撤回消息
export const withdrawMessage = (id: string) => {
  return http.post(`/message/withdraw/${id}`);
};

// 获取会话列表
export const getChatSessions = () => {
  return http.get('/chat/sessions');
};

// 获取聊天记录
export const getChatMessages = (sessionId: string, params: ChatMessageParams) => {
  return http.get(`/chat/messages/${sessionId}`, { params });
};

// 发送聊天消息
export const sendChatMessage = (data: Partial<ChatMessage>) => {
  return http.post('/chat/message/send', data);
};

// 创建群组
export const createGroup = (data: CreateGroupParams) => {
  return http.post('/chat/group/create', data);
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能模块     | 管理员   | 律所主任   | 合伙人      | 执业律师      | 实习律师   | 行政人员    |
|------------|----------|------------|------------|--------------|------------|------------|
| 查看消息     | ✓全所    | ✓全所      | ✓团队      | ✓个人+团队    | ✓个人      | ✓部门相关   |
| 发送消息     | ✓全所    | ✓全所      | ✓团队      | ✓个人+团队    | ✓个人      | ✓部门相关   |
| 群发消息     | ✓全所    | ✓全所      | ✓团队      | -            | -          | ✓部门相关   |
| 撤回消息     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | ✓个人      | ✓部门相关   |
| 删除消息     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | ✓个人      | ✓部门相关   |
| 管理群组     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | -          | ✓部门相关   |
| 发布公告     | ✓全所    | ✓全所      | ✓团队      | -            | -          | ✓部门相关   |
| 消息设置     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | ✓个人      | ✓部门相关   |

### 5.2 数据权限说明

1. 数据访问范围
   - 管理员和律所主任：可管理所有消息和群组
   - 合伙人：可管理本团队的消息和群组
   - 执业律师：可管理个人消息和参与的群组
   - 实习律师：仅可管理个人消息
   - 行政人员：可管理部门相关的消息和群组

2. 特殊权限说明
   - 消息发送：所有用户都可以发送个人消息
   - 群发消息：需要相应的权限级别
   - 消息撤回：只能撤回自己发送的消息
   - 消息删除：只能删除与自己相关的消息

3. 群组权限
   - 全所群组：管理员和律所主任可创建
   - 部门群组：部门主管可创建
   - 团队群组：团队负责人可创建
   - 项目群组：项目负责人可创建
   - 临时群组：所有用户可创建

### 5.3 路由配置
```typescript
// router/modules/message.ts
export default {
  path: '/message',
  name: 'Message',
  component: LAYOUT,
  meta: {
    title: '消息管理',
    icon: 'ant-design:message-outlined',
    roles: ['admin', 'director', 'partner', 'lawyer', 'trainee', 'admin_finance']
  },
  children: [
    {
      path: 'list',
      name: 'MessageList',
      component: () => import('@/views/message/list/index.vue'),
      meta: {
        title: '消息列表'
      }
    },
    {
      path: 'chat',
      name: 'Chat',
      component: () => import('@/views/message/chat/index.vue'),
      meta: {
        title: '即时通讯'
      }
    },
    {
      path: 'detail/:id',
      name: 'MessageDetail',
      component: () => import('@/views/message/detail/index.vue'),
      meta: {
        title: '消息详情',
        hideMenu: true
      }
    }
  ]
};
```

## 6. 状态管理

### 6.1 Store 结构
```typescript
// store/modules/message.ts
interface MessageState {
  messageList: Message[];
  currentMessage: Message | null;
  chatSessions: ChatSession[];
  currentSession: ChatSession | null;
  chatMessages: Record<string, ChatMessage[]>;
  loading: boolean;
  unreadCount: number;
  pagination: {
    current: number;
    pageSize: number;
    total: number;
  };
  searchParams: Recordable;
}

const useMessageStore = defineStore({
  id: 'app-message',
  state: (): MessageState => ({
    messageList: [],
    currentMessage: null,
    chatSessions: [],
    currentSession: null,
    chatMessages: {},
    loading: false,
    unreadCount: 0,
    pagination: {
      current: 1,
      pageSize: 10,
      total: 0
    },
    searchParams: {}
  }),
  getters: {
    getMessageList(): Message[] {
      return this.messageList;
    },
    getCurrentMessage(): Message | null {
      return this.currentMessage;
    },
    getChatSessions(): ChatSession[] {
      return this.chatSessions;
    },
    getUnreadCount(): number {
      return this.unreadCount;
    }
  },
  actions: {
    async fetchMessageList() {
      this.loading = true;
      try {
        const { items, total } = await getMessageList({
          ...this.searchParams,
          page: this.pagination.current,
          pageSize: this.pagination.pageSize
        });
        this.messageList = items;
        this.pagination.total = total;
      } finally {
        this.loading = false;
      }
    },
    async fetchChatSessions() {
      this.loading = true;
      try {
        this.chatSessions = await getChatSessions();
      } finally {
        this.loading = false;
      }
    },
    async loadChatMessages(sessionId: string) {
      this.loading = true;
      try {
        const messages = await getChatMessages(sessionId, {});
        this.chatMessages[sessionId] = messages;
      } finally {
        this.loading = false;
      }
    }
  }
});
```

## 7. 使用说明

### 7.1 消息处理流程
1. 查看消息列表
   - 进入消息列表页面
   - 查看未读消息
   - 筛选消息类型
   - 搜索特定消息

2. 处理消息
   - 阅读消息内容
   - 标记消息状态
   - 回复或转发消息
   - 归档重要消息

3. 即时通讯
   - 选择联系人
   - 发起会话
   - 发送消息
   - 共享文件

### 7.2 群组管理
1. 创建群组
   - 设置群组名称
   - 选择群组成员
   - 设置群组公告
   - 配置群组权限

2. 群组维护
   - 管理群组成员
   - 更新群组信息
   - 设置群组规则
   - 解散群组

### 7.3 通知设置
1. 个人设置
   - 选择通知方式
   - 设置免打扰
   - 配置提醒时间
   - 订阅消息类型

2. 系统设置
   - 配置通知模板
   - 设置发送规则
   - 管理订阅组
   - 监控发送状态

## 8. 最佳实践

### 8.1 消息管理
1. 分类管理
   - 建立消息分类体系
   - 设置优先级规则
   - 制定处理流程
   - 规范管理制度

2. 及时处理
   - 及时查看消息
   - 快速响应重要消息
   - 定期清理无用消息
   - 归档重要信息

3. 信息安全
   - 保护敏感信息
   - 设置访问权限
   - 定期备份数据
   - 监控异常行为

### 8.2 沟通效率
1. 提高效率
   - 使用消息模板
   - 批量处理消息
   - 设置快捷回复
   - 优化处理流程

2. 规范使用
   - 遵守沟通礼仪
   - 使用规范用语
   - 注意时效性
   - 确保信息准确

3. 持续优化
   - 收集用户反馈
   - 分析使用数据
   - 优化功能设计
   - 改进用户体验

## 9. 注意事项

1. 消息编号规则
   - 格式：XX + 年份 + 类型代码 + 序号，如：XX2024TZ001
   - 自动生成，不可修改
   - 需要保证唯一性

2. 消息推送
   - 多渠道推送
   - 实时性保证
   - 推送去重
   - 失败重试

3. 数据安全
   - 消息加密
   - 敏感信息过滤
   - 权限控制
   - 操作审计

4. 性能优化
   - 消息分页加载
   - 图片懒加载
   - WebSocket优化
   - 历史记录缓存 

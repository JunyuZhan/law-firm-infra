# 项目命名规范说明

## 1. 模块命名规范

### 1.1 基础模块命名
所有模块统一使用 `law-firm-` 作为前缀，采用小写字母，单词间用连字符（-）分隔。

示例：
- `law-firm-api`：API接口模块
- `law-firm-core`：核心业务模块
- `law-firm-common`：公共功能模块
- `law-firm-modules`：业务功能模块
- `law-firm-dependencies`：依赖管理模块
- `law-firm-model`：数据模型模块

### 1.2 子模块命名
子模块命名需要体现其所属的父模块，同样使用连字符分隔。

示例：
- `law-firm-common/common-message`：公共消息模块
- `law-firm-core/core-message`：核心消息模块
- `law-firm-api/api-client`：客户端API模块

## 2. 包命名规范

### 2.1 基础包路径
统一使用 `com.lawfirm` 作为基础包路径。

### 2.2 功能模块包命名
- 按照功能模块进行划分
- 使用小写字母
- 单词间不使用分隔符

示例：
```java
com.lawfirm.common.message
com.lawfirm.core.message
com.lawfirm.api.client
```

### 2.3 包内部结构
标准的包内部结构应包含：
```
├── controller    // 控制器层
├── service      // 服务层
│   └── impl     // 服务实现
├── repository   // 数据访问层
├── model        // 数据模型
├── dto          // 数据传输对象
├── vo           // 视图对象
├── enums        // 枚举类
├── config       // 配置类
└── util         // 工具类
```

## 3. 类命名规范

### 3.1 通用规则
- 使用大驼峰命名法（PascalCase）
- 名称应当是名词或名词短语
- 避免使用缩写（除非是广泛使用的缩写）

### 3.2 特定类型命名
- 接口：以 `I` 开头或以 `Service`、`Repository` 等功能描述结尾
- 抽象类：以 `Abstract` 或 `Base` 开头
- 实现类：以其实现的接口名称加 `Impl` 结尾
- 枚举类：以具体的枚举功能命名，以 `Enum` 或 `Type` 结尾
- 异常类：以 `Exception` 结尾
- 控制器类：以 `Controller` 结尾
- 配置类：以 `Config` 或 `Configuration` 结尾

示例：
```java
public interface MessageService
public class MessageServiceImpl
public abstract class AbstractMessageService
public class MessageController
public enum MessageType
public class MessageException
public class WebSocketConfig
```

## 4. 方法命名规范

### 4.1 通用规则
- 使用小驼峰命名法（camelCase）
- 动词或动词短语
- 名称应当表明方法的功能

### 4.2 常用动词约定
- 获取单个对象：`get`
- 获取集合对象：`list`
- 保存：`save`
- 删除：`delete`/`remove`
- 更新：`update`
- 查询：`query`/`find`
- 统计：`count`
- 判断：`is`/`has`/`should`

示例：
```java
public Message getMessage(Long id)
public List<Message> listMessages()
public void saveMessage(Message message)
public void deleteMessage(Long id)
public void updateMessage(Message message)
public List<Message> queryMessagesByType(String type)
public long countUnreadMessages()
public boolean isMessageRead(Long id)
```

## 5. 变量命名规范

### 5.1 通用规则
- 使用小驼峰命名法
- 名称应当有明确含义
- 避免使用单字母（除非是临时变量或循环计数器）

### 5.2 特殊类型变量
- 常量：全大写，单词间用下划线分隔
- 集合类型：建议使用复数形式
- 布尔类型：使用 `is`/`has`/`should` 等前缀

示例：
```java
private static final int MAX_MESSAGE_COUNT = 100;
private List<Message> messages;
private boolean isRead;
private boolean hasAttachment;
```

## 6. 配置文件命名规范

### 6.1 属性文件
- 使用小写字母
- 单词间用连字符分隔
- 环境特定配置添加环境后缀

示例：
```
application.yml
application-dev.yml
application-prod.yml
message-service.properties
```

### 6.2 XML配置文件
- 使用描述性名称
- 单词间用连字符分隔

示例：
```
mybatis-config.xml
logback-spring.xml
```

## 7. 数据库命名规范

### 7.1 数据库命名
- 使用小写字母
- 单词间用下划线分隔
- 环境特定数据库添加环境后缀

示例：
```sql
law_firm_db
law_firm_db_dev
law_firm_db_test
```

### 7.2 表命名
- 使用小写字母
- 单词间用下划线分隔
- 使用名词复数形式
- 模块相关的表使用模块名作为前缀

示例：
```sql
case_records        -- 案件记录表
case_documents      -- 案件文档表
auth_users          -- 用户表
auth_roles          -- 角色表
message_templates   -- 消息模板表
system_configs      -- 系统配置表
```

### 7.3 字段命名
- 使用小写字母
- 单词间用下划线分隔
- 字段名应当清晰表达其用途
- 布尔类型字段使用 `is_` 或 `has_` 前缀

示例：
```sql
id                  -- 主键
created_at          -- 创建时间
updated_at          -- 更新时间
created_by          -- 创建人
updated_by          -- 更新人
is_deleted          -- 是否删除
has_attachment      -- 是否有附件
status              -- 状态
```

### 7.4 索引命名
- 主键索引：`pk_表名`
- 唯一索引：`uk_表名_字段名`
- 普通索引：`idx_表名_字段名`
- 外键索引：`fk_表名_关联表名`

示例：
```sql
pk_case_records
uk_auth_users_username
idx_case_records_created_at
fk_case_documents_case_records
```

## 8. 前端命名规范

### 8.1 文件命名
- Vue组件文件：使用大驼峰命名法
- 路由页面组件：使用小写字母，单词间用连字符分隔
- 工具类文件：使用小驼峰命名法
- 样式文件：使用小写字母，单词间用连字符分隔

示例：
```
components/
  ├── MessageList.vue
  ├── UserAvatar.vue
views/
  ├── case-management/
  │   ├── case-list.vue
  │   └── case-detail.vue
utils/
  ├── dateFormatter.js
  └── stringUtils.js
styles/
  ├── common.scss
  └── theme-variables.scss
```

### 8.2 组件命名
- 组件名使用大驼峰命名法
- 应当是名词或名词短语
- 基础组件使用特定前缀（如 Base、App、V）

示例：
```vue
<template>
  <BaseButton />
  <AppHeader />
  <UserProfileCard />
  <CaseDetailForm />
</template>
```

### 8.3 属性命名
- Props：使用小驼峰命名法
- 事件：使用小驼峰命名法，建议以动词开头
- 自定义事件：使用 kebab-case（连字符）命名

示例：
```vue
<template>
  <UserList
    :userList="users"
    :pageSize="10"
    @update-user="handleUserUpdate"
    @delete-user="handleUserDelete"
  />
</template>
```

### 8.4 变量命名
- data属性：使用小驼峰命名法
- computed属性：使用小驼峰命名法
- methods：使用小驼峰命名法，动词或动词短语
- store模块：使用小驼峰命名法

示例：
```javascript
export default {
  data() {
    return {
      userList: [],
      currentPage: 1,
      isLoading: false
    }
  },
  computed: {
    filteredUsers() { /* ... */ },
    totalPages() { /* ... */ }
  },
  methods: {
    fetchUsers() { /* ... */ },
    handleUserUpdate() { /* ... */ },
    validateForm() { /* ... */ }
  }
}
```

### 8.5 CSS命名
- 使用 BEM 命名规范
- 块：使用小写字母
- 元素：双下划线 `__`
- 修饰符：双连字符 `--`

示例：
```scss
.case-card {
  &__header {
    &--highlighted { /* ... */ }
  }
  &__content {
    &--collapsed { /* ... */ }
  }
  &__footer {
    &--fixed { /* ... */ }
  }
}
```

## 9. 版本号命名规范

### 9.1 版本号格式
采用语义化版本号：`主版本号.次版本号.修订号`

- 主版本号：不兼容的API修改
- 次版本号：向下兼容的功能性新增
- 修订号：向下兼容的问题修正

示例：
```
1.0.0   -- 初始版本
1.1.0   -- 新增特性
1.1.1   -- 修复bug
2.0.0   -- 重大更新
```

### 9.2 版本阶段标识
- alpha：内部测试版
- beta：公开测试版
- rc：候选发布版
- release：正式发布版

示例：
```
1.0.0-alpha.1
1.0.0-beta.2
1.0.0-rc.1
1.0.0-release
``` 
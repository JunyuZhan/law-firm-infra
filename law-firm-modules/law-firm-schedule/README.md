# 日程管理模块（Schedule Module）

## 1. 模块概述

日程管理模块负责律师事务所内部人员的日程安排、会议预约、提醒通知等功能，为律师和员工提供高效的时间管理工具。本模块支持各类日程事项的创建、修改、查询和删除，并提供日程冲突检测、提醒通知等功能。

## 2. 核心功能

- **日程管理**：创建、编辑、查询和删除个人日程
- **会议安排**：会议室预约、参会人员邀请与确认
- **日程共享**：团队日程共享与权限管理
- **日程提醒**：基于时间的多级提醒机制
- **日程冲突检测**：自动检测并提示日程冲突
- **日历视图**：多种日历视图（日/周/月）展示
- **与案件关联**：支持将日程与特定案件关联
- **与任务关联**：支持将日程与任务模块关联
- **外部日历同步**：支持与外部日历系统同步（如Google Calendar、Outlook等）

## 3. 数据模型设计

### 3.1 核心实体

#### 3.1.1 日程事项（Schedule）
- id: Long - 主键ID
- title: String - 日程标题
- content: String - 日程内容/描述
- startTime: LocalDateTime - 开始时间
- endTime: LocalDateTime - 结束时间
- allDay: Boolean - 是否全天事项
- location: String - 地点
- type: ScheduleType - 日程类型（会议、任务、约见、法庭出庭等）
- priority: PriorityLevel - 优先级（高、中、低）
- status: ScheduleStatus - 状态（计划中、进行中、已完成、已取消）
- ownerId: Long - 所有者用户ID
- isPrivate: Boolean - 是否私密日程
- createTime: LocalDateTime - 创建时间
- updateTime: LocalDateTime - 更新时间
- deleted: Boolean - 是否删除

#### 3.1.2 日程参与者（ScheduleParticipant）
- id: Long - 主键ID
- scheduleId: Long - 关联的日程ID
- participantId: Long - 参与者ID（用户ID）
- participantType: ParticipantType - 参与者类型（组织者、必要参与者、可选参与者）
- responseStatus: ResponseStatus - 响应状态（接受、拒绝、未回复）
- createTime: LocalDateTime - 创建时间
- updateTime: LocalDateTime - 更新时间

#### 3.1.3 日程提醒（ScheduleReminder）
- id: Long - 主键ID
- scheduleId: Long - 关联的日程ID
- reminderTime: LocalDateTime - 提醒时间
- reminderType: ReminderType - 提醒类型（系统提醒、邮件提醒、短信提醒）
- reminderStatus: ReminderStatus - 提醒状态（待提醒、已提醒、已忽略）
- createTime: LocalDateTime - 创建时间
- updateTime: LocalDateTime - 更新时间

#### 3.1.4 会议室（MeetingRoom）
- id: Long - 主键ID
- name: String - 会议室名称
- location: String - 位置
- capacity: Integer - 容量（人数）
- facilities: String - 设施（JSON格式，例如投影仪、白板等）
- status: RoomStatus - 状态（可用、维护中、停用）
- createTime: LocalDateTime - 创建时间
- updateTime: LocalDateTime - 更新时间

#### 3.1.5 会议室预约（MeetingRoomReservation）
- id: Long - 主键ID
- meetingRoomId: Long - 会议室ID
- scheduleId: Long - 关联的日程ID
- startTime: LocalDateTime - 开始时间
- endTime: LocalDateTime - 结束时间
- status: ReservationStatus - 状态（待审核、已确认、已取消）
- createTime: LocalDateTime - 创建时间
- updateTime: LocalDateTime - 更新时间

### 3.2 关联关系

- 日程与参与者：一对多关系，一个日程可以有多个参与者
- 日程与提醒：一对多关系，一个日程可以设置多个提醒
- 日程与会议室：通过会议室预约建立多对多关系
- 日程与案件：多对多关系，一个日程可以关联多个案件
- 日程与任务：多对多关系，一个日程可以关联多个任务

## 4. 技术实现

### 4.1 模块依赖
- **base-model**：基础模型依赖
- **common-core**：核心通用组件
- **common-data**：数据处理组件
- **common-security**：安全控制组件
- **personnel-model**：人员模型（用于获取用户信息）
- **task-model**：任务模型（用于与任务关联）
- **case-model**：案件模型（用于与案件关联）

### 4.2 技术栈
- Spring Boot：作为基础框架
- MyBatis Plus：持久层框架
- Spring Security：安全框架
- MapStruct：对象映射工具
- Spring Validation：参数校验
- Spring Event：事件驱动机制（用于提醒等功能）

### 4.3 API设计
- 遵循RESTful设计原则
- 提供日程CRUD标准接口
- 提供日程冲突检测接口
- 提供日程搜索、过滤接口
- 提供会议室预约接口
- 提供日程共享权限管理接口

## 5. 集成点

### 5.1 与其他模块集成
- **人员模块**：获取用户信息，判断日程的可见性和访问权限
- **任务模块**：将任务与日程关联，实现任务到期提醒
- **案件模块**：将案件与日程关联，提供案件相关的日程安排
- **消息模块**：发送日程提醒通知
- **通知模块**：处理日程相关的系统通知
- **客户模块**：客户会面预约与日程关联

### 5.2 外部系统集成
- 提供外部日历系统同步接口
- 支持iCalendar格式导入导出

## 6. 开发计划

1. 创建schedule-model模块，定义数据实体
2. 实现基础CRUD服务
3. 实现日程冲突检测功能
4. 实现日程提醒机制
5. 实现会议室预约功能
6. 开发与其他模块的集成点
7. 实现外部日历同步功能
8. 完善接口文档和单元测试

## 7. 安全和权限控制

- 基于角色的访问控制（RBAC）
- 日程私密性设置
- 共享日程的权限管理
- 会议室预约权限控制
- 操作日志记录

# 日程管理模块 (Schedule Module)

## 模块说明
日程管理模块是律师事务所管理系统的日程安排和管理模块，负责管理律师和员工的日程安排、会议管理、日历管理等功能。该模块提供了灵活的日程管理机制，支持个人日程和团队日程的协同管理。

## 核心功能

### 1. 日程管理
- 日程创建安排
  - 个人日程
  - 团队日程
  - 会议日程
  - 庭审日程
- 日程类型管理
  - 类型定义
  - 优先级设置
  - 提醒方式
  - 显示颜色
- 日程状态管理
  - 待开始
  - 进行中
  - 已完成
  - 已取消

### 2. 会议管理
- 会议室预约
  - 会议室资源
  - 时段预约
  - 冲突检查
  - 设备预定
- 会议安排
  - 参会人员
  - 会议议程
  - 会议材料
  - 会议纪要
- 视频会议
  - 在线会议
  - 会议录制
  - 屏幕共享
  - 远程协作

### 3. 提醒管理
- 提醒方式
  - 系统提醒
  - 邮件提醒
  - 短信提醒
  - 微信提醒
- 提醒规则
  - 提前提醒
  - 重复提醒
  - 自定义规则
  - 紧急程度
- 提醒设置
  - 个性化设置
  - 全局设置
  - 默认模板
  - 免打扰时段

### 4. 日历视图
- 视图展示
  - 日视图
  - 周视图
  - 月视图
  - 列表视图
- 日历同步
  - 多端同步
  - 第三方同步
  - 离线支持
  - 实时更新
- 数据统计
  - 日程统计
  - 会议统计
  - 时间分析
  - 效率评估

## 核心组件

### 1. 日程服务
- ScheduleService：日程服务接口
- ScheduleTypeService：类型服务
- ScheduleStatusService：状态服务
- ScheduleReminderService：提醒服务
- ScheduleValidatorService：验证服务

### 2. 会议服务
- MeetingService：会议服务接口
- RoomService：会议室服务
- ParticipantService：参会人服务
- MaterialService：材料服务
- VideoConferenceService：视频会议服务

### 3. 提醒服务
- ReminderService：提醒服务接口
- NotificationService：通知服务
- RuleService：规则服务
- TemplateService：模板服务
- ChannelService：渠道服务

### 4. 日历服务
- CalendarService：日历服务接口
- ViewService：视图服务
- SyncService：同步服务
- StatisticsService：统计服务
- ExportService：导出服务

## 使用示例

### 1. 创建日程
```java
@Autowired
private ScheduleService scheduleService;

public ScheduleDTO createSchedule(ScheduleCreateRequest request) {
    // 创建日程
    Schedule schedule = new Schedule()
        .setTitle(request.getTitle())
        .setType(ScheduleTypeEnum.valueOf(request.getType()))
        .setStartTime(request.getStartTime())
        .setEndTime(request.getEndTime())
        .setLocation(request.getLocation())
        .setParticipants(request.getParticipants())
        .setReminder(request.getReminder())
        .setDescription(request.getDescription());
    
    // 保存日程
    return scheduleService.createSchedule(schedule);
}
```

### 2. 预约会议室
```java
@Autowired
private RoomService roomService;

public RoomReservationDTO reserveRoom(RoomReservationRequest request) {
    // 创建预约
    RoomReservation reservation = new RoomReservation()
        .setRoomId(request.getRoomId())
        .setStartTime(request.getStartTime())
        .setEndTime(request.getEndTime())
        .setTitle(request.getTitle())
        .setAttendees(request.getAttendees())
        .setEquipments(request.getEquipments());
    
    // 预约会议室
    return roomService.reserve(reservation);
}
```

### 3. 设置提醒
```java
@Autowired
private ReminderService reminderService;

public void setReminder(Long scheduleId, ReminderRequest request) {
    // 创建提醒
    Reminder reminder = new Reminder()
        .setScheduleId(scheduleId)
        .setType(ReminderTypeEnum.valueOf(request.getType()))
        .setAdvanceTime(request.getAdvanceTime())
        .setRepeatType(request.getRepeatType())
        .setChannels(request.getChannels());
    
    // 设置提醒
    reminderService.setReminder(reminder);
}
```

## 配置说明

### 1. 日程配置
```yaml
schedule:
  # 基础配置
  base:
    max-participants: 100
    enable-recurrence: true
    default-duration: 60
    
  # 会议配置
  meeting:
    room-check: true
    equipment-management: true
    video-conference: true
```

### 2. 提醒配置
```yaml
reminder:
  # 提醒方式
  channel:
    system: true
    email: true
    sms: true
    wechat: true
    
  # 提醒规则
  rule:
    default-advance-time: 15
    max-repeat-times: 3
    quiet-period: "23:00-07:00"
```

## 注意事项
1. 日程时间准确
2. 会议室冲突避免
3. 提醒及时送达
4. 数据实时同步
5. 资源合理利用 
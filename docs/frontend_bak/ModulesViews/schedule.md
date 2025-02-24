# 日程管理模块

## 1. 功能概述

日程管理模块是律师事务所管理系统的个人工作管理模块，用于管理律师及员工的日常工作安排，包括案件日程、会议日程、工作计划等，提供日程规划、提醒、共享等功能。

## 2. 页面结构

```bash
views/schedule/
├── list/                # 日程列表页
│   ├── index.vue       # 列表主页面
│   └── components/     # 列表页组件
│       ├── SearchForm.vue      # 搜索表单
│       ├── TableColumns.vue    # 表格列配置
│       ├── CalendarView.vue    # 日历视图
│       └── TimelineView.vue    # 时间轴视图
│
├── detail/             # 日程详情页
│   ├── index.vue      # 详情主页面
│   └── components/    # 详情页组件
│       ├── BasicInfo.vue       # 基本信息
│       ├── Reminder.vue        # 提醒设置
│       ├── Recurrence.vue      # 重复设置
│       └── Sharing.vue         # 共享设置
│
├── calendar/          # 日历管理
│   ├── index.vue     # 日历主页面
│   └── components/   # 日历组件
│       ├── MonthView.vue       # 月视图
│       ├── WeekView.vue        # 周视图
│       └── DayView.vue         # 日视图
│
└── components/        # 公共组件
    ├── ScheduleForm.vue       # 日程表单
    ├── ScheduleType.vue       # 日程类型
    └── ScheduleStatus.vue     # 日程状态
```

## 3. 功能清单

### 3.1 日程列表
- [x] 日程搜索
  - 日程标题
  - 日程类型
  - 日程状态
  - 时间范围
  - 重要程度
  - 创建人
  - 参与人
  
- [x] 多视图展示
  - 列表视图
  - 日历视图
  - 时间轴视图
  - 甘特图视图

- [x] 批量操作
  - 批量删除
  - 批量导出
  - 批量分享
  - 批量提醒

### 3.2 日程详情
- [x] 基本信息
  - 日程信息
    - 日程标题
    - 日程类型
    - 开始时间
    - 结束时间
    - 地点
    - 重要程度
    - 日程说明
  - 关联信息
    - 关联案件
    - 关联会议
    - 关联任务
    - 关联文档

- [x] 提醒设置
  - 提醒时间
  - 提醒方式
  - 提醒对象
  - 重复提醒

- [x] 重复设置
  - 重复类型
    - 每天
    - 每周
    - 每月
    - 每年
  - 重复规则
  - 结束条件
  - 例外日期

- [x] 共享设置
  - 共享范围
  - 共享权限
  - 订阅设置
  - 导出分享

### 3.3 日历管理
- [x] 日历设置
  - 默认视图
  - 工作时间
  - 节假日
  - 时区设置

- [x] 日历分类
  - 个人日历
  - 团队日历
  - 案件日历
  - 假期日历

### 3.4 统计分析
- [x] 日程统计
  - 日程数量
  - 时间分布
  - 类型分布
  - 完成情况

- [x] 工作分析
  - 工作量统计
  - 时间利用率
  - 工作效率
  - 任务达成率

## 4. API 接口

### 4.1 数据结构
```typescript
// 日程基本信息
interface Schedule {
  id: string;                 // 日程ID
  title: string;             // 日程标题
  type: ScheduleType;        // 日程类型
  status: ScheduleStatus;    // 日程状态
  priority: Priority;        // 重要程度
  startTime: string;        // 开始时间
  endTime: string;          // 结束时间
  location: string;         // 地点
  description: string;      // 日程说明
  creatorId: string;        // 创建人ID
  createTime: string;       // 创建时间
  updateTime: string;       // 更新时间
}

// 日程类型
enum ScheduleType {
  CASE = 'case',           // 案件日程
  MEETING = 'meeting',     // 会议日程
  TASK = 'task',          // 任务日程
  PERSONAL = 'personal'    // 个人日程
}

// 日程状态
enum ScheduleStatus {
  PENDING = 'pending',     // 待开始
  ONGOING = 'ongoing',     // 进行中
  COMPLETED = 'completed', // 已完成
  CANCELLED = 'cancelled'  // 已取消
}

// 重要程度
enum Priority {
  HIGH = 'high',           // 高
  MEDIUM = 'medium',       // 中
  LOW = 'low'             // 低
}

// 提醒设置
interface Reminder {
  id: string;              // 提醒ID
  scheduleId: string;      // 日程ID
  time: string;            // 提醒时间
  type: ReminderType;      // 提醒方式
  target: string[];        // 提醒对象
  repeat: boolean;         // 是否重复
}

// 重复规则
interface RecurrenceRule {
  id: string;              // 规则ID
  scheduleId: string;      // 日程ID
  type: RecurrenceType;    // 重复类型
  interval: number;        // 重复间隔
  endType: EndType;        // 结束类型
  endValue: string;        // 结束值
  exceptions: string[];    // 例外日期
}
```

### 4.2 接口列表
```typescript
// 获取日程列表
export const getScheduleList = (params: ScheduleListParams) => {
  return http.get('/schedule/list', { params });
};

// 获取日程详情
export const getScheduleDetail = (id: string) => {
  return http.get(`/schedule/detail/${id}`);
};

// 创建日程
export const createSchedule = (data: Partial<Schedule>) => {
  return http.post('/schedule/create', data);
};

// 更新日程
export const updateSchedule = (id: string, data: Partial<Schedule>) => {
  return http.put(`/schedule/update/${id}`, data);
};

// 删除日程
export const deleteSchedule = (id: string) => {
  return http.delete(`/schedule/delete/${id}`);
};

// 设置提醒
export const setReminder = (data: Partial<Reminder>) => {
  return http.post('/schedule/reminder/set', data);
};

// 设置重复
export const setRecurrence = (data: Partial<RecurrenceRule>) => {
  return http.post('/schedule/recurrence/set', data);
};

// 分享日程
export const shareSchedule = (data: ShareScheduleParams) => {
  return http.post('/schedule/share', data);
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能模块     | 管理员   | 律所主任   | 合伙人      | 执业律师      | 实习律师   | 行政人员    |
|------------|----------|------------|------------|--------------|------------|------------|
| 查看日程     | ✓全所    | ✓全所      | ✓团队      | ✓个人+团队    | ✓个人      | ✓部门相关   |
| 创建日程     | ✓       | ✓          | ✓团队      | ✓个人        | ✓个人      | ✓部门相关   |
| 编辑日程     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | ✓个人      | ✓部门相关   |
| 删除日程     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | -          | ✓部门相关   |
| 分享日程     | ✓全所    | ✓全所      | ✓团队      | ✓团队        | -          | ✓部门相关   |
| 导出日程     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | ✓个人      | ✓部门相关   |

### 5.2 数据权限说明

1. 数据访问范围
   - 管理员和律所主任：可查看和管理所有日程
   - 合伙人：可查看和管理本团队的日程
   - 执业律师：可查看个人创建的日程和团队共享的日程
   - 实习律师：仅可查看和管理个人日程
   - 行政人员：可查看与本部门相关的日程

2. 特殊权限说明
   - 日程创建：所有用户都可以创建个人日程
   - 日程编辑：只能编辑有权限的日程
   - 日程删除：只有管理员、律所主任和创建者可以删除
   - 日程分享：需要考虑数据保密性

3. 共享权限
   - 个人日程：默认仅创建者可见
   - 团队日程：团队成员可见
   - 部门日程：部门成员可见
   - 全所日程：所有人可见

### 5.3 路由配置
```typescript
// router/modules/schedule.ts
export default {
  path: '/schedule',
  name: 'Schedule',
  component: LAYOUT,
  meta: {
    title: '日程管理',
    icon: 'ant-design:calendar-outlined',
    roles: ['admin', 'lawyer', 'clerk', 'finance']
  },
  children: [
    {
      path: 'list',
      name: 'ScheduleList',
      component: () => import('@/views/schedule/list/index.vue'),
      meta: {
        title: '日程列表'
      }
    },
    {
      path: 'calendar',
      name: 'Calendar',
      component: () => import('@/views/schedule/calendar/index.vue'),
      meta: {
        title: '日历视图'
      }
    },
    {
      path: 'detail/:id',
      name: 'ScheduleDetail',
      component: () => import('@/views/schedule/detail/index.vue'),
      meta: {
        title: '日程详情',
        hideMenu: true
      }
    }
  ]
};
```

## 6. 状态管理

```typescript
// store/modules/schedule.ts
export const useScheduleStore = defineStore('schedule', {
  state: () => ({
    scheduleList: [],
    currentSchedule: null,
    calendarData: [],
    loading: false,
    total: 0
  }),
  
  getters: {
    getScheduleById: (state) => {
      return (id: string) => state.scheduleList.find(item => item.id === id);
    }
  },
  
  actions: {
    async getScheduleList(params: ScheduleListParams) {
      this.loading = true;
      try {
        const { data } = await getScheduleList(params);
        this.scheduleList = data.list;
        this.total = data.total;
      } finally {
        this.loading = false;
      }
    },
    
    async getScheduleDetail(id: string) {
      const { data } = await getScheduleDetail(id);
      this.currentSchedule = data;
    },
    
    async getCalendarData(params: CalendarParams) {
      const { data } = await getCalendarData(params);
      this.calendarData = data;
    }
  }
});
```

## 7. 使用示例

### 7.1 日程列表
```vue
<!-- views/schedule/list/index.vue -->
<template>
  <div class="schedule-list">
    <SearchForm @search="handleSearch" />
    
    <BasicTable
      :columns="columns"
      :dataSource="scheduleList"
      :loading="loading"
      @register="registerTable"
    >
      <template #toolbar>
        <a-button type="primary" @click="handleCreate">
          新建日程
        </a-button>
      </template>
      
      <template #action="{ record }">
        <TableAction
          :actions="getActions(record)"
        />
      </template>
    </BasicTable>
  </div>
</template>

<script lang="ts" setup>
import { useScheduleStore } from '@/store';
import { columns } from './components/TableColumns';
import SearchForm from './components/SearchForm.vue';

const scheduleStore = useScheduleStore();
const { loading, scheduleList } = storeToRefs(scheduleStore);

// 表格注册
const [registerTable] = useTable({
  api: scheduleStore.getScheduleList,
  columns,
  pagination: true,
  striped: false,
  useSearchForm: true,
  formConfig: {
    labelWidth: 120,
    schemas: searchFormSchema,
  },
});
</script>
```

## 8. 注意事项

1. 日程编号规则
   - 格式：RC + 年份 + 类型代码 + 序号，如：RC2024AJ001
   - 自动生成，不可修改
   - 需要保证唯一性

2. 日程提醒
   - 多渠道提醒
   - 灵活的提醒时间
   - 重复提醒设置
   - 批量提醒处理

3. 数据同步
   - 多端同步
   - 离线支持
   - 冲突处理
   - 变更通知

4. 性能优化
   - 日历渲染优化
   - 数据缓存策略
   - 按需加载
   - 延迟加载 

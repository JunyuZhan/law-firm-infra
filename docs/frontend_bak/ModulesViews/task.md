# 任务管理模块

## 1. 功能概述

任务管理模块是律师事务所管理系统的协作管理模块，用于管理律所内部的各类工作任务，包括案件任务、行政任务、日常任务等，提供任务分配、进度跟踪、协作管理等功能。

## 2. 页面结构

```bash
views/task/
├── list/                # 任务列表页
│   ├── index.vue       # 列表主页面
│   └── components/     # 列表页组件
│       ├── SearchForm.vue      # 搜索表单
│       ├── TableColumns.vue    # 表格列配置
│       ├── KanbanView.vue      # 看板视图
│       └── CalendarView.vue    # 日历视图
│
├── detail/             # 任务详情页
│   ├── index.vue      # 详情主页面
│   └── components/    # 详情页组件
│       ├── BasicInfo.vue       # 基本信息
│       ├── SubTasks.vue        # 子任务
│       ├── Comments.vue        # 评论
│       ├── Attachments.vue     # 附件
│       └── Timeline.vue        # 时间线
│
├── board/             # 任务看板
│   ├── index.vue     # 看板主页面
│   └── components/   # 看板组件
│       ├── BoardColumn.vue     # 看板列
│       └── TaskCard.vue        # 任务卡片
│
└── components/        # 公共组件
    ├── TaskForm.vue          # 任务表单
    ├── TaskPriority.vue      # 优先级标记
    └── TaskStatus.vue        # 状态标记
```

## 3. 功能清单

### 3.1 任务列表
- [x] 任务搜索
  - 任务标题
  - 任务类型
  - 任务状态
  - 优先级
  - 负责人
  - 创建人
  - 截止时间
  - 所属项目
  
- [x] 多视图展示
  - 列表视图
  - 看板视图
  - 日历视图
  - 甘特图视图
  - 统计视图

- [x] 批量操作
  - 批量分配
  - 批量更新
  - 批量导出
  - 批量归档

### 3.2 任务详情
- [x] 基本信息
  - 任务信息
    - 任务标题
    - 任务描述
    - 任务类型
    - 优先级
    - 截止时间
    - 预计工时
    - 实际工时
    - 完成进度
  - 关联信息
    - 所属项目
    - 关联案件
    - 关联文档
    - 关联人员

- [x] 子任务管理
  - 子任务列表
  - 添加子任务
  - 任务分解
  - 进度统计

- [x] 任务协作
  - 任务评论
  - 任务提醒
  - @功能
  - 文件共享

- [x] 任务跟踪
  - 状态更新
  - 进度记录
  - 工时记录
  - 操作日志

### 3.3 任务看板
- [x] 看板管理
  - 自定义列
  - 拖拽排序
  - 状态流转
  - 任务筛选

- [x] 任务卡片
  - 基本信息
  - 优先级标记
  - 截止提醒
  - 快捷操作

### 3.4 统计分析
- [x] 任务统计
  - 任务总览
  - 完成情况
  - 延期统计
  - 工时统计

- [x] 绩效分析
  - 个人绩效
  - 部门绩效
  - 工作量分析
  - 效率分析

## 4. API 接口

### 4.1 数据结构
```typescript
// 任务基本信息
interface Task {
  id: string;                 // 任务ID
  title: string;             // 任务标题
  description: string;       // 任务描述
  type: TaskType;           // 任务类型
  status: TaskStatus;       // 任务状态
  priority: TaskPriority;   // 优先级
  progress: number;         // 完成进度
  estimatedHours: number;   // 预计工时
  actualHours: number;      // 实际工时
  startTime: string;        // 开始时间
  endTime: string;          // 截止时间
  projectId: string;        // 项目ID
  creatorId: string;        // 创建人ID
  assigneeId: string;       // 负责人ID
  parentId: string;         // 父任务ID
  createTime: string;       // 创建时间
  updateTime: string;       // 更新时间
}

// 任务类型
enum TaskType {
  CASE = 'case',           // 案件任务
  ADMIN = 'admin',         // 行政任务
  DAILY = 'daily',         // 日常任务
  PROJECT = 'project'      // 项目任务
}

// 任务状态
enum TaskStatus {
  TODO = 'todo',           // 待处理
  DOING = 'doing',         // 进行中
  DONE = 'done',          // 已完成
  PENDING = 'pending',     // 待确认
  CLOSED = 'closed'        // 已关闭
}

// 任务优先级
enum TaskPriority {
  HIGH = 'high',           // 高
  MEDIUM = 'medium',       // 中
  LOW = 'low'             // 低
}

// 任务评论
interface TaskComment {
  id: string;              // 评论ID
  taskId: string;          // 任务ID
  content: string;         // 评论内容
  userId: string;          // 评论人ID
  createTime: string;      // 创建时间
  mentions: string[];      // @提及
}
```

### 4.2 接口列表
```typescript
// 获取任务列表
export const getTaskList = (params: TaskListParams) => {
  return http.get('/task/list', { params });
};

// 获取任务详情
export const getTaskDetail = (id: string) => {
  return http.get(`/task/detail/${id}`);
};

// 创建任务
export const createTask = (data: Partial<Task>) => {
  return http.post('/task/create', data);
};

// 更新任务
export const updateTask = (id: string, data: Partial<Task>) => {
  return http.put(`/task/update/${id}`, data);
};

// 删除任务
export const deleteTask = (id: string) => {
  return http.delete(`/task/delete/${id}`);
};

// 获取子任务
export const getSubTasks = (id: string) => {
  return http.get(`/task/sub-tasks/${id}`);
};

// 添加评论
export const addComment = (data: Partial<TaskComment>) => {
  return http.post('/task/comment/add', data);
};

// 获取任务统计
export const getTaskStats = (params: TaskStatsParams) => {
  return http.get('/task/stats', { params });
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能         | 管理员 | 律所主任 | 合伙人 | 执业律师 | 实习律师 | 行政人员 |
|------------|--------|----------|--------|----------|----------|----------|
| 查看任务     | ✓全所  | ✓全所    | ✓团队   | ✓个人+团队| ✓个人    | ✓部门    |
| 创建任务     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | -        | ✓部门    |
| 编辑任务     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | -        | ✓部门    |
| 删除任务     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | -        | ✓部门    |
| 分配任务     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | -        | ✓部门    |
| 任务审核     | ✓全所  | ✓全所    | ✓团队   | -        | -        | -        |
| 任务导出     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | ✓个人    | ✓部门    |
| 任务统计     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | ✓个人    | ✓部门    |

### 5.2 数据权限说明

### 5.2 数据权限说明

1. 数据访问范围
   - 管理员和律所主任：可查看和管理所有任务
   - 合伙人：可查看和管理本团队的任务
   - 执业律师：可查看个人任务和团队任务
   - 实习律师：仅可查看个人任务
   - 行政人员：可查看与本部门相关的任务

2. 特殊权限说明
   - 任务创建：需要考虑任务类型和级别
   - 任务分配：只能分配给有权限的人员
   - 任务审核：需要有相应的审核权限
   - 任务管理：只能管理权限范围内的任务

3. 任务类型权限
   - 全所任务：管理员和律所主任可创建
   - 部门任务：部门主管可创建
   - 团队任务：团队负责人可创建
   - 项目任务：项目负责人可创建
   - 个人任务：所有用户可创建

### 5.3 路由配置
```typescript
// router/modules/task.ts
export default {
  path: '/task',
  name: 'Task',
  component: LAYOUT,
  meta: {
    title: '任务管理',
    icon: 'ant-design:schedule-outlined',
    roles: ['admin', 'director', 'partner', 'lawyer', 'trainee', 'admin_finance']
  },
  children: [
    {
      path: 'list',
      name: 'TaskList',
      component: () => import('@/views/task/list/index.vue'),
      meta: {
        title: '任务列表'
      }
    },
    {
      path: 'board',
      name: 'TaskBoard',
      component: () => import('@/views/task/board/index.vue'),
      meta: {
        title: '任务看板'
      }
    },
    {
      path: 'detail/:id',
      name: 'TaskDetail',
      component: () => import('@/views/task/detail/index.vue'),
      meta: {
        title: '任务详情',
        hideMenu: true
      }
    }
  ]
};
```

## 6. 状态管理

```typescript
// store/modules/task.ts
export const useTaskStore = defineStore('task', {
  state: () => ({
    taskList: [],
    currentTask: null,
    boardData: [],
    loading: false,
    total: 0
  }),
  
  getters: {
    getTaskById: (state) => {
      return (id: string) => state.taskList.find(item => item.id === id);
    }
  },
  
  actions: {
    async getTaskList(params: TaskListParams) {
      this.loading = true;
      try {
        const { data } = await getTaskList(params);
        this.taskList = data.list;
        this.total = data.total;
      } finally {
        this.loading = false;
      }
    },
    
    async getTaskDetail(id: string) {
      const { data } = await getTaskDetail(id);
      this.currentTask = data;
    },
    
    async getBoardData(params: BoardParams) {
      const { data } = await getTaskBoard(params);
      this.boardData = data;
    }
  }
});
```

## 7. 使用示例

### 7.1 任务列表
```vue
<!-- views/task/list/index.vue -->
<template>
  <div class="task-list">
    <SearchForm @search="handleSearch" />
    
    <BasicTable
      :columns="columns"
      :dataSource="taskList"
      :loading="loading"
      @register="registerTable"
    >
      <template #toolbar>
        <a-button type="primary" @click="handleCreate">
          新建任务
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
import { useTaskStore } from '@/store';
import { columns } from './components/TableColumns';
import SearchForm from './components/SearchForm.vue';

const taskStore = useTaskStore();
const { loading, taskList } = storeToRefs(taskStore);

// 表格注册
const [registerTable] = useTable({
  api: taskStore.getTaskList,
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

1. 任务编号规则
   - 格式：RW + 年份 + 类型代码 + 序号，如：RW2024AJ001
   - 自动生成，不可修改
   - 需要保证唯一性

2. 任务提醒
   - 截止时间提醒
   - 任务分配提醒
   - 状态变更提醒
   - 评论@提醒

3. 数据安全
   - 任务权限控制
   - 敏感信息保护
   - 操作日志记录
   - 数据备份恢复

4. 性能优化
   - 列表页虚拟滚动
   - 看板拖拽优化
   - 评论按需加载
   - 统计数据缓存 

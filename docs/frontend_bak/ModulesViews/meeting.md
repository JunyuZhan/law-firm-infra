# 会议管理模块

## 1. 功能概述

会议管理模块是律师事务所管理系统的协同办公模块，用于管理律所内部的各类会议，包括案件讨论会、部门例会、培训会议等，提供会议预约、会议室管理、会议记录等功能。

## 2. 页面结构

```bash
views/meeting/
├── list/                # 会议列表页
│   ├── index.vue       # 列表主页面
│   └── components/     # 列表页组件
│       ├── SearchForm.vue      # 搜索表单
│       ├── TableColumns.vue    # 表格列配置
│       ├── CalendarView.vue    # 日历视图
│       └── RoomStatus.vue      # 会议室状态
│
├── detail/             # 会议详情页
│   ├── index.vue      # 详情主页面
│   └── components/    # 详情页组件
│       ├── BasicInfo.vue       # 基本信息
│       ├── Attendees.vue       # 参会人员
│       ├── Minutes.vue         # 会议纪要
│       └── Resources.vue       # 会议资源
│
├── room/              # 会议室管理
│   ├── index.vue     # 管理主页面
│   └── components/   # 管理组件
│       ├── RoomList.vue        # 会议室列表
│       ├── RoomForm.vue        # 会议室表单
│       ├── BookingForm.vue     # 预订表单
│       └── Calendar.vue        # 预订日历
│
└── components/        # 公共组件
    ├── MeetingType.vue        # 会议类型
    ├── MeetingStatus.vue      # 会议状态
    └── RoomSelector.vue       # 会议室选择
```

## 3. 功能清单

### 3.1 会议管理
- [x] 会议预约
  - 会议信息
    - 会议主题
    - 会议类型
    - 开始时间
    - 结束时间
    - 会议地点
    - 主持人
    - 参会人员
  - 会议资源
    - 会议室预订
    - 设备需求
    - 茶水服务
    - 文件准备
  
- [x] 会议通知
  - 参会邀请
  - 会议提醒
  - 变更通知
  - 取消通知

### 3.2 会议室管理
- [x] 会议室信息
  - 基本信息
    - 会议室名称
    - 位置信息
    - 容纳人数
    - 设备配置
  - 使用状态
    - 空闲状态
    - 预订状态
    - 使用中
    - 维护中

- [x] 预订管理
  - 预订申请
  - 审批流程
  - 使用确认
  - 取消预订

### 3.3 会议记录
- [x] 会议纪要
  - 会议议题
  - 讨论内容
  - 决议事项
  - 任务分配
  - 后续跟进

- [x] 资料管理
  - 会议材料
  - 签到记录
  - 会议照片
  - 录音录像

## 4. API 接口

### 4.1 数据结构
```typescript
// 会议信息
interface Meeting {
  id: string;                 // 会议ID
  title: string;             // 会议主题
  type: MeetingType;        // 会议类型
  status: MeetingStatus;    // 会议状态
  startTime: string;        // 开始时间
  endTime: string;          // 结束时间
  roomId: string;           // 会议室ID
  hostId: string;           // 主持人ID
  attendees: string[];      // 参会人员ID
  description: string;      // 会议说明
  resources: Resource[];    // 会议资源
  createTime: string;       // 创建时间
  updateTime: string;       // 更新时间
}

// 会议室信息
interface MeetingRoom {
  id: string;              // 会议室ID
  name: string;           // 会议室名称
  location: string;       // 位置信息
  capacity: number;       // 容纳人数
  equipment: string[];    // 设备配置
  status: RoomStatus;     // 使用状态
  bookings: Booking[];    // 预订记录
  createTime: string;     // 创建时间
  updateTime: string;     // 更新时间
}

// 会议纪要
interface Minutes {
  id: string;             // 纪要ID
  meetingId: string;      // 会议ID
  content: string;        // 纪要内容
  attachments: string[];  // 附件列表
  tasks: Task[];         // 任务列表
  creator: string;       // 创建人
  createTime: string;    // 创建时间
  updateTime: string;    // 更新时间
}
```

### 4.2 接口列表
```typescript
// 获取会议列表
export const getMeetingList = (params: MeetingListParams) => {
  return http.get('/meeting/list', { params });
};

// 获取会议详情
export const getMeetingDetail = (id: string) => {
  return http.get(`/meeting/detail/${id}`);
};

// 创建会议
export const createMeeting = (data: Partial<Meeting>) => {
  return http.post('/meeting/create', data);
};

// 更新会议
export const updateMeeting = (id: string, data: Partial<Meeting>) => {
  return http.put(`/meeting/update/${id}`, data);
};

// 取消会议
export const cancelMeeting = (id: string, reason: string) => {
  return http.post(`/meeting/cancel/${id}`, { reason });
};

// 获取会议室列表
export const getRoomList = (params: RoomListParams) => {
  return http.get('/meeting/room/list', { params });
};

// 预订会议室
export const bookRoom = (data: BookingParams) => {
  return http.post('/meeting/room/book', data);
};

// 提交会议纪要
export const submitMinutes = (data: Partial<Minutes>) => {
  return http.post('/meeting/minutes/submit', data);
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能         | 管理员 | 律所主任 | 合伙人 | 执业律师 | 实习律师 | 行政人员 |
|------------|--------|----------|--------|----------|----------|----------|
| 查看会议     | ✓全所  | ✓全所    | ✓团队   | ✓个人+团队| ✓个人    | ✓部门    |
| 创建会议     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | -        | ✓部门    |
| 编辑会议     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | -        | ✓部门    |
| 取消会议     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | -        | ✓部门    |
| 会议室管理   | ✓      | ✓        | -      | -        | -        | ✓部门    |
| 预订会议室   | ✓全所  | ✓全所    | ✓团队   | ✓申请    | ✓申请    | ✓部门    |
| 会议纪要     | ✓全所  | ✓全所    | ✓团队   | ✓个人    | ✓个人    | ✓部门    |

### 5.2 数据权限说明
### 5.2 数据权限说明

1. 数据访问范围
   - 管理员和律所主任：可查看和管理所有会议
   - 合伙人：可查看和管理本团队的会议
   - 执业律师：可查看个人创建的会议和团队会议
   - 实习律师：仅可查看被邀请的会议
   - 行政人员：可查看与本部门相关的会议

2. 特殊权限说明
   - 会议创建：需要考虑会议类型和规模
   - 会议室预订：需要通过审批流程
   - 重要会议：需要相应级别的审批
   - 会议记录：参会人员可查看

3. 审批流程权限
   - 普通会议：部门主管审批
   - 重要会议：需要律所主任审批
   - 全所会议：需要管理员审批

### 5.3 路由配置
```typescript
// router/modules/meeting.ts
export default {
  path: '/meeting',
  name: 'Meeting',
  component: LAYOUT,
  meta: {
    title: '会议管理',
    icon: 'ant-design:team-outlined',
    roles: ['admin', 'director', 'partner', 'lawyer', 'trainee', 'admin_finance']
  },
  children: [
    {
      path: 'list',
      name: 'MeetingList',
      component: () => import('@/views/meeting/list/index.vue'),
      meta: {
        title: '会议列表'
      }
    },
    {
      path: 'room',
      name: 'MeetingRoom',
      component: () => import('@/views/meeting/room/index.vue'),
      meta: {
        title: '会议室管理',
        roles: ['admin', 'director', 'admin_finance']
      }
    },
    {
      path: 'detail/:id',
      name: 'MeetingDetail',
      component: () => import('@/views/meeting/detail/index.vue'),
      meta: {
        title: '会议详情',
        hideMenu: true
      }
    }
  ]
};
```

## 6. 状态管理

```typescript
// store/modules/meeting.ts
export const useMeetingStore = defineStore('meeting', {
  state: () => ({
    meetingList: [],
    currentMeeting: null,
    roomList: [],
    loading: false,
    total: 0
  }),
  
  getters: {
    getMeetingById: (state) => {
      return (id: string) => state.meetingList.find(item => item.id === id);
    }
  },
  
  actions: {
    async getMeetingList(params: MeetingListParams) {
      this.loading = true;
      try {
        const { data } = await getMeetingList(params);
        this.meetingList = data.list;
        this.total = data.total;
      } finally {
        this.loading = false;
      }
    },
    
    async getMeetingDetail(id: string) {
      const { data } = await getMeetingDetail(id);
      this.currentMeeting = data;
    },
    
    async getRoomList(params: RoomListParams) {
      const { data } = await getRoomList(params);
      this.roomList = data;
    }
  }
});
```

## 7. 使用说明

### 7.1 会议预约流程
1. 进入会议预约页面
2. 填写会议信息
   - 选择会议类型
   - 设置会议时间
   - 选择会议室
   - 添加参会人员
3. 提交预约申请
4. 等待审批通过
5. 发送会议通知
6. 准备会议材料

### 7.2 会议室使用规范
1. 使用要求
   - 提前预约
   - 准时使用
   - 爱护设备
   - 及时归还

2. 预订要求
   - 选择合适容量
   - 说明设备需求
   - 注明使用时间
   - 遵守使用规则

### 7.3 注意事项
1. 会议管理
   - 提前发送通知
   - 准备会议材料
   - 控制会议时间
   - 做好会议记录

2. 设备使用
   - 提前调试设备
   - 正确使用设备
   - 及时报修问题
   - 节约能源资源

3. 文明用室
   - 保持整洁
   - 遵守秩序
   - 按时结束
   - 带走物品

## 8. 最佳实践

### 8.1 会议组织
1. 合理安排时间
2. 准备充分材料
3. 明确会议目标
4. 控制会议节奏

### 8.2 会议记录
1. 指定专人记录
2. 重点内容记录
3. 及时整理归档
4. 跟进会议决议

### 8.3 资源利用
1. 合理分配资源
2. 避免资源浪费
3. 提高使用效率
4. 及时处理问题

### 8.4 效果评估
1. 会议效果评估
2. 参会反馈收集
3. 持续改进优化
4. 总结经验教训

# 人事管理模块

## 1. 功能概述

人事管理模块是律师事务所管理系统的重要组成部分，用于管理律所的人力资源相关事务，包括员工信息管理、考勤管理、绩效考核、培训管理等，为律所的人力资源管理提供全面的解决方案。

## 2. 页面结构

```bash
views/hr/
├── employee/            # 员工管理
│   ├── index.vue       # 员工列表
│   └── components/     # 员工组件
│       ├── SearchForm.vue      # 搜索表单
│       ├── TableColumns.vue    # 表格列配置
│       ├── EmployeeForm.vue    # 员工表单
│       └── EmployeeDetail.vue  # 员工详情
│
├── attendance/         # 考勤管理
│   ├── index.vue      # 考勤列表
│   └── components/    # 考勤组件
│       ├── Calendar.vue        # 考勤日历
│       ├── Statistics.vue      # 统计分析
│       ├── LeaveForm.vue       # 请假表单
│       └── OvertimeForm.vue    # 加班表单
│
├── performance/       # 绩效管理
│   ├── index.vue     # 绩效列表
│   └── components/   # 绩效组件
│       ├── Assessment.vue      # 考核表单
│       ├── Evaluation.vue      # 评估详情
│       ├── Target.vue          # 目标设定
│       └── Result.vue          # 结果分析
│
├── training/         # 培训管理
│   ├── index.vue    # 培训列表
│   └── components/  # 培训组件
│       ├── Course.vue          # 课程管理
│       ├── Schedule.vue        # 培训安排
│       ├── Record.vue          # 培训记录
│       └── Certificate.vue     # 证书管理
│
└── organization/    # 组织架构
    ├── index.vue   # 组织列表
    └── components/ # 组织组件
        ├── DepartmentTree.vue  # 部门树形
        ├── Position.vue        # 职位管理
        ├── Transfer.vue        # 调动管理
        └── Structure.vue       # 架构图表
```

## 3. 功能清单

### 3.1 员工管理
- [x] 员工信息
  - 基本信息
    - 姓名
    - 性别
    - 出生日期
    - 身份证号
    - 联系方式
    - 入职日期
  - 职业信息
    - 职位
    - 部门
    - 工号
    - 执业证号
    - 专业领域
  - 教育背景
    - 学历
    - 毕业院校
    - 专业
    - 证书

- [x] 员工操作
  - 入职管理
  - 离职管理
  - 调岗管理
  - 档案管理

### 3.2 考勤管理
- [x] 考勤记录
  - 上下班打卡
  - 外勤打卡
  - 考勤统计
  - 异常处理

- [x] 假期管理
  - 请假申请
  - 加班申请
  - 调休管理
  - 假期统计

### 3.3 绩效管理
- [x] 绩效考核
  - 考核指标
  - 考核周期
  - 考核评分
  - 考核结果

- [x] 目标管理
  - 目标设定
  - 进度跟踪
  - 结果评估
  - 改进计划

### 3.4 培训管理
- [x] 培训计划
  - 课程设置
  - 讲师管理
  - 培训安排
  - 考勤记录

- [x] 培训评估
  - 培训反馈
  - 效果评估
  - 证书管理
  - 档案记录

### 3.5 组织架构
- [x] 部门管理
  - 部门设置
  - 人员配置
  - 权限设置
  - 架构调整

- [x] 职位管理
  - 职位设置
  - 职级体系
  - 晋升通道
  - 权限配置

## 4. API 接口

### 4.1 数据结构
```typescript
// 员工信息
interface Employee {
  id: string;                 // 员工ID
  name: string;              // 姓名
  gender: Gender;            // 性别
  birthday: string;          // 出生日期
  idCard: string;           // 身份证号
  phone: string;            // 联系电话
  email: string;            // 电子邮箱
  entryDate: string;        // 入职日期
  departmentId: string;     // 部门ID
  positionId: string;       // 职位ID
  employeeNo: string;       // 工号
  licenseNo: string;        // 执业证号
  status: EmployeeStatus;   // 员工状态
}

// 考勤记录
interface Attendance {
  id: string;              // 考勤ID
  employeeId: string;      // 员工ID
  date: string;            // 考勤日期
  checkIn: string;         // 上班时间
  checkOut: string;        // 下班时间
  type: AttendanceType;    // 考勤类型
  status: AttendanceStatus;// 考勤状态
  location: string;        // 打卡地点
  remark: string;          // 备注说明
}

// 绩效考核
interface Performance {
  id: string;              // 考核ID
  employeeId: string;      // 员工ID
  period: string;          // 考核周期
  targets: Target[];       // 考核目标
  score: number;           // 考核得分
  level: string;           // 考核等级
  feedback: string;        // 考核反馈
  status: AssessmentStatus;// 考核状态
}

// 培训记录
interface Training {
  id: string;              // 培训ID
  name: string;            // 培训名称
  type: TrainingType;      // 培训类型
  startTime: string;       // 开始时间
  endTime: string;         // 结束时间
  location: string;        // 培训地点
  trainer: string;         // 培训讲师
  participants: string[];  // 参训人员
  status: TrainingStatus;  // 培训状态
}
```

### 4.2 接口列表
```typescript
// 获取员工列表
export const getEmployeeList = (params: EmployeeListParams) => {
  return http.get('/hr/employee/list', { params });
};

// 创建员工
export const createEmployee = (data: Partial<Employee>) => {
  return http.post('/hr/employee/create', data);
};

// 更新员工信息
export const updateEmployee = (id: string, data: Partial<Employee>) => {
  return http.put(`/hr/employee/${id}`, data);
};

// 获取考勤记录
export const getAttendanceList = (params: AttendanceListParams) => {
  return http.get('/hr/attendance/list', { params });
};

// 提交考勤
export const submitAttendance = (data: Partial<Attendance>) => {
  return http.post('/hr/attendance/submit', data);
};

// 创建绩效考核
export const createPerformance = (data: Partial<Performance>) => {
  return http.post('/hr/performance/create', data);
};

// 提交考核结果
export const submitAssessment = (id: string, data: AssessmentResult) => {
  return http.post(`/hr/performance/assess/${id}`, data);
};

// 获取培训计划
export const getTrainingList = (params: TrainingListParams) => {
  return http.get('/hr/training/list', { params });
};

// 创建培训
export const createTraining = (data: Partial<Training>) => {
  return http.post('/hr/training/create', data);
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能模块     | 管理员   | 律所主任   | 合伙人      | 执业律师      | 实习律师   | 行政人员    |
|------------|----------|------------|------------|--------------|------------|------------|
| 查看员工     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | ✓个人      | ✓部门全权   |
| 员工管理     | ✓全所    | ✓全所      | ✓团队      | -            | -          | ✓部门全权   |
| 考勤管理     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | ✓个人      | ✓部门全权   |
| 绩效考核     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | ✓个人      | ✓部门全权   |
| 培训管理     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | ✓个人      | ✓部门全权   |
| 组织架构     | ✓全所    | ✓全所      | ✓团队      | -            | -          | ✓部门全权   |
| 薪酬管理     | ✓全所    | ✓全所      | ✓团队      | ✓个人/只读    | ✓个人/只读  | ✓部门全权   |
| 档案管理     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | ✓个人      | ✓部门全权   |

### 5.2 数据权限说明

1. 数据访问范围
   - 管理员和律所主任：可查看和管理所有人力资源数据
   - 合伙人：可查看和管理本团队的人力资源数据
   - 执业律师：可查看个人数据，无管理权限
   - 实习律师：仅可查看个人数据，无管理权限
   - 行政人员：负责人力资源日常管理，对本部门数据有完整操作权限

2. 特殊权限说明
   - 员工管理：仅管理员、律所主任和行政人员有权限
   - 考勤管理：所有人可以查看和管理个人考勤
   - 绩效考核：需要通过审批流程
   - 培训管理：所有人可以参与培训并查看个人培训记录
   - 薪酬管理：普通员工只能查看个人薪酬信息
   - 档案管理：严格控制敏感信息访问权限

3. 审批流程权限
   - 入职审批：需要多级审批
   - 离职审批：需要部门和管理层审批
   - 调岗审批：需要相关部门负责人审批
   - 绩效审批：需要直属上级和部门负责人审批

### 5.2 路由配置
```typescript
// router/modules/hr.ts
export default {
  path: '/hr',
  name: 'HR',
  component: LAYOUT,
  meta: {
    title: '人事管理',
    icon: 'ant-design:team-outlined',
    roles: ['admin', 'hr']
  },
  children: [
    {
      path: 'employee',
      name: 'Employee',
      component: () => import('@/views/hr/employee/index.vue'),
      meta: {
        title: '员工管理'
      }
    },
    {
      path: 'attendance',
      name: 'Attendance',
      component: () => import('@/views/hr/attendance/index.vue'),
      meta: {
        title: '考勤管理'
      }
    },
    {
      path: 'performance',
      name: 'Performance',
      component: () => import('@/views/hr/performance/index.vue'),
      meta: {
        title: '绩效管理'
      }
    },
    {
      path: 'training',
      name: 'Training',
      component: () => import('@/views/hr/training/index.vue'),
      meta: {
        title: '培训管理'
      }
    },
    {
      path: 'organization',
      name: 'Organization',
      component: () => import('@/views/hr/organization/index.vue'),
      meta: {
        title: '组织架构'
      }
    }
  ]
};
```

## 6. 状态管理

```typescript
// store/modules/hr.ts
export const useHRStore = defineStore('hr', {
  state: () => ({
    employeeList: [],
    attendanceList: [],
    performanceList: [],
    trainingList: [],
    loading: false,
    total: 0
  }),
  
  getters: {
    getEmployeeById: (state) => {
      return (id: string) => state.employeeList.find(item => item.id === id);
    }
  },
  
  actions: {
    async getEmployeeList(params: EmployeeListParams) {
      this.loading = true;
      try {
        const { data } = await getEmployeeList(params);
        this.employeeList = data.list;
        this.total = data.total;
      } finally {
        this.loading = false;
      }
    },
    
    async getAttendanceList(params: AttendanceListParams) {
      const { data } = await getAttendanceList(params);
      this.attendanceList = data;
    },
    
    async getPerformanceList(params: PerformanceListParams) {
      const { data } = await getPerformanceList(params);
      this.performanceList = data;
    }
  }
});
```

## 7. 使用示例

### 7.1 员工列表
```vue
<!-- views/hr/employee/index.vue -->
<template>
  <div class="employee-list">
    <SearchForm @search="handleSearch" />
    
    <BasicTable
      :columns="columns"
      :dataSource="employeeList"
      :loading="loading"
      @register="registerTable"
    >
      <template #toolbar>
        <a-button type="primary" @click="handleCreate">
          新增员工
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
import { useHRStore } from '@/store';
import { columns } from './components/TableColumns';
import SearchForm from './components/SearchForm.vue';

const hrStore = useHRStore();
const { loading, employeeList } = storeToRefs(hrStore);

// 表格注册
const [registerTable] = useTable({
  api: hrStore.getEmployeeList,
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

1. 编号规则
   - 工号：YG + 年份 + 序号，如：YG2024001
   - 考勤：KQ + 年月日 + 序号，如：KQ20240101001
   - 绩效：JX + 年月 + 序号，如：JX202401001
   - 培训：PX + 年月 + 序号，如：PX202401001

2. 数据安全
   - 个人信息加密
   - 权限精细控制
   - 操作日志记录
   - 数据备份恢复

3. 业务规则
   - 考勤规则配置
   - 绩效考核标准
   - 培训计划审批
   - 组织架构维护

4. 性能优化
   - 数据分页加载
   - 缓存优化
   - 并发控制
   - 大数据处理 

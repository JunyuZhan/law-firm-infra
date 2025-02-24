# 数据分析模块

## 1. 功能概述

数据分析模块是律师事务所管理系统的决策支持模块，通过对律所各项业务数据的统计、分析和可视化展示，为管理层提供经营决策支持，帮助律所提升管理效率和业务发展。

## 2. 页面结构

```bash
views/analytics/
├── dashboard/           # 数据仪表盘
│   ├── index.vue       # 仪表盘主页
│   └── components/     # 仪表盘组件
│       ├── Overview.vue        # 总览面板
│       ├── Trend.vue          # 趋势图表
│       ├── Ranking.vue        # 排名统计
│       └── Alert.vue          # 预警提示
│
├── business/           # 业务分析
│   ├── index.vue      # 业务分析页
│   └── components/    # 业务组件
│       ├── CaseAnalysis.vue    # 案件分析
│       ├── Income.vue          # 收入分析
│       ├── Client.vue          # 客户分析
│       └── Performance.vue     # 绩效分析
│
├── finance/           # 财务分析
│   ├── index.vue     # 财务分析页
│   └── components/   # 财务组件
│       ├── Revenue.vue         # 收入分析
│       ├── Expense.vue         # 支出分析
│       ├── Profit.vue          # 利润分析
│       └── Budget.vue          # 预算分析
│
├── report/           # 报表中心
│   ├── index.vue    # 报表页面
│   └── components/  # 报表组件
│       ├── Daily.vue           # 日报表
│       ├── Monthly.vue         # 月报表
│       ├── Annual.vue          # 年报表
│       └── Custom.vue          # 自定义报表
│
└── settings/        # 分析配置
    ├── index.vue   # 配置页面
    └── components/ # 配置组件
        ├── Indicator.vue       # 指标配置
        ├── Dimension.vue       # 维度配置
        ├── Chart.vue           # 图表配置
        └── Export.vue          # 导出配置
```

## 3. 功能清单

### 3.1 数据仪表盘
- [x] 业务总览
  - 案件统计
  - 收入统计
  - 客户统计
  - 人员统计
  
- [x] 趋势分析
  - 业务趋势
  - 收入趋势
  - 客户趋势
  - 绩效趋势

- [x] 实时监控
  - 数据更新
  - 异常预警
  - 任务提醒
  - 系统状态

### 3.2 业务分析
- [x] 案件分析
  - 案件类型分布
  - 案件状态分析
  - 案件周期分析
  - 案件质量分析

- [x] 客户分析
  - 客户来源分析
  - 客户行业分布
  - 客户价值分析
  - 客户满意度

### 3.3 财务分析
- [x] 收入分析
  - 收入构成
  - 收入趋势
  - 收入预测
  - 收入对比

- [x] 成本分析
  - 成本构成
  - 成本趋势
  - 成本控制
  - 成本优化

### 3.4 报表中心
- [x] 定期报表
  - 日报表
  - 周报表
  - 月报表
  - 年报表

- [x] 专项报表
  - 业务报表
  - 财务报表
  - 人事报表
  - 客户报表

## 4. API 接口

### 4.1 数据结构
```typescript
// 仪表盘数据
interface Dashboard {
  id: string;                 // 仪表盘ID
  name: string;              // 仪表盘名称
  type: DashboardType;       // 仪表盘类型
  layout: Layout[];          // 布局配置
  charts: Chart[];           // 图表配置
  filters: Filter[];         // 筛选条件
  updateTime: string;        // 更新时间
}

// 分析指标
interface Indicator {
  id: string;              // 指标ID
  name: string;            // 指标名称
  code: string;            // 指标代码
  type: IndicatorType;     // 指标类型
  unit: string;            // 单位
  formula: string;         // 计算公式
  dimension: string[];     // 统计维度
  description: string;     // 指标描述
}

// 分析报表
interface Report {
  id: string;              // 报表ID
  name: string;            // 报表名称
  type: ReportType;        // 报表类型
  period: string;          // 统计周期
  indicators: string[];    // 统计指标
  dimensions: string[];    // 统计维度
  filters: Filter[];       // 筛选条件
  createTime: string;      // 创建时间
}

// 分析结果
interface AnalysisResult {
  id: string;              // 分析ID
  type: AnalysisType;      // 分析类型
  dimension: string;       // 分析维度
  metrics: Metric[];       // 分析指标
  data: any[];            // 分析数据
  summary: string;         // 分析总结
  createTime: string;      // 创建时间
}
```

### 4.2 接口列表
```typescript
// 获取仪表盘数据
export const getDashboardData = (params: DashboardParams) => {
  return http.get('/analytics/dashboard/data', { params });
};

// 获取业务分析数据
export const getBusinessAnalysis = (params: BusinessAnalysisParams) => {
  return http.get('/analytics/business/analysis', { params });
};

// 获取财务分析数据
export const getFinanceAnalysis = (params: FinanceAnalysisParams) => {
  return http.get('/analytics/finance/analysis', { params });
};

// 生成分析报表
export const generateReport = (data: Partial<Report>) => {
  return http.post('/analytics/report/generate', data);
};

// 获取分析指标
export const getIndicators = (params: IndicatorParams) => {
  return http.get('/analytics/indicator/list', { params });
};

// 保存仪表盘配置
export const saveDashboard = (data: Partial<Dashboard>) => {
  return http.post('/analytics/dashboard/save', data);
};

// 导出分析数据
export const exportAnalysis = (params: ExportParams) => {
  return http.get('/analytics/export', { params });
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能模块     | 管理员 | 律所主任   | 合伙人      | 执业律师    | 实习律师   | 行政/财务   |
|------------|---------|------------|------------|------------|------------|------------|
| 数据仪表盘   | ✓全所   | ✓全所      | ✓团队      | ✓个人      | ✓个人      | ✓部门相关   |
| 业务分析     | ✓全所   | ✓全所      | ✓团队      | ✓个人      | -          | -          |
| 财务分析     | ✓全所   | ✓全所      | ✓团队/只读  | -          | -          | ✓部门全权   |
| 人员分析     | ✓全所   | ✓全所      | ✓团队      | -          | -          | ✓部门全权   |
| 报表生成     | ✓全所   | ✓全所      | ✓团队      | ✓个人      | -          | ✓部门相关   |
| 分析配置     | ✓      | ✓基础设置   | -          | -          | -          | -          |
| 数据导出     | ✓全所   | ✓全所      | ✓团队      | ✓个人      | -          | ✓部门相关   |

### 5.2 权限说明

- **✓全所**：可查看和操作全所范围的数据分析功能
- **✓团队**：仅可查看和操作本团队范围的数据分析功能
- **✓个人**：仅可查看和操作与个人相关的数据分析功能
- **✓部门全权**：对本部门数据拥有完整的分析权限
- **✓部门相关**：可查看和操作与本部门相关的数据分析功能
- **✓团队/只读**：仅可查看本团队范围的数据分析结果
- **✓基础设置**：仅可配置基础的分析指标和维度
- **-**：无权限访问

### 5.3 路由配置
```typescript
// router/modules/analytics.ts
export default {
  path: '/analytics',
  name: 'Analytics',
  component: LAYOUT,
  meta: {
    title: '数据分析',
    icon: 'ant-design:bar-chart-outlined',
    roles: ['admin', 'director', 'partner', 'lawyer', 'trainee', 'admin_finance']
  },
  children: [
    {
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/analytics/dashboard/index.vue'),
      meta: {
        title: '数据仪表盘',
        roles: ['admin', 'director', 'partner', 'lawyer', 'trainee', 'admin_finance']
      }
    },
    {
      path: 'business',
      name: 'Business',
      component: () => import('@/views/analytics/business/index.vue'),
      meta: {
        title: '业务分析',
        roles: ['admin', 'director', 'partner', 'lawyer']
      }
    },
    {
      path: 'finance',
      name: 'Finance',
      component: () => import('@/views/analytics/finance/index.vue'),
      meta: {
        title: '财务分析',
        roles: ['admin', 'director', 'partner', 'admin_finance']
      }
    },
    {
      path: 'personnel',
      name: 'Personnel',
      component: () => import('@/views/analytics/personnel/index.vue'),
      meta: {
        title: '人员分析',
        roles: ['admin', 'director', 'partner', 'admin_finance']
      }
    },
    {
      path: 'report',
      name: 'Report',
      component: () => import('@/views/analytics/report/index.vue'),
      meta: {
        title: '报表中心',
        roles: ['admin', 'director', 'partner', 'lawyer', 'admin_finance']
      }
    },
    {
      path: 'settings',
      name: 'Settings',
      component: () => import('@/views/analytics/settings/index.vue'),
      meta: {
        title: '分析配置',
        roles: ['admin', 'director']
      }
    }
  ]
}
```

### 5.4 数据权限控制

```typescript
// 数据权限控制接口
interface DataPermission {
  userId: string;           // 用户ID
  roleId: string;          // 角色ID
  dataScope: {             // 数据访问范围
    all?: boolean;         // 全所数据
    team?: string[];       // 团队数据ID列表
    personal?: boolean;    // 个人数据
    department?: string[]; // 部门数据ID列表
  };
  actions: {               // 操作权限
    view: boolean;         // 查看
    export: boolean;       // 导出
    configure: boolean;    // 配置
  };
}

// 数据过滤中间件
export const dataFilter = async (ctx: Context, next: Next) => {
  const { user, permission } = ctx.state;
  const dataScope = permission.dataScope;

  // 根据角色和数据范围过滤数据
  if (dataScope.all) {
    // 全所数据权限
    await next();
  } else if (dataScope.team?.length) {
    // 团队数据权限
    ctx.query.teamIds = dataScope.team;
    await next();
  } else if (dataScope.department?.length) {
    // 部门数据权限
    ctx.query.departmentIds = dataScope.department;
    await next();
  } else if (dataScope.personal) {
    // 个人数据权限
    ctx.query.userId = user.id;
    await next();
  } else {
    ctx.throw(403, '无数据访问权限');
  }
};
``` 

# 资产管理模块

## 1. 功能概述

资产管理模块是律师事务所管理系统的重要支撑模块，用于管理律所的固定资产、办公设备、IT设备等资产，实现资产全生命周期管理，包括资产采购、入库、领用、维护、报废等环节的管理。

### 1.1 主要功能

- 资产基础管理
  - 资产分类管理
  - 资产信息维护
  - 资产状态跟踪
  - 资产标签管理
  - 资产定位管理

- 资产操作管理
  - 资产采购管理
  - 资产入库管理
  - 资产领用管理
  - 资产归还管理
  - 资产维修管理
  - 资产报废管理

- 资产监控管理
  - 资产盘点管理
  - 使用情况统计
  - 维护记录跟踪
  - 折旧计算管理
  - 预警提醒管理

### 1.2 应用场景

1. 固定资产管理
   - 办公家具管理
   - 电子设备管理
   - 会议设备管理
   - 空调设备管理
   - 安防设备管理

2. 资产使用管理
   - 资产领用申请
   - 临时借用管理
   - 资产归还确认
   - 使用权变更
   - 维修保养管理

3. 资产价值管理
   - 资产价值核算
   - 折旧计算管理
   - 资产评估管理
   - 报废处理管理
   - 资产处置管理

## 2. 页面结构

```bash
views/asset/
├── list/                # 资产列表
│   ├── index.vue       # 列表主页面
│   └── components/     # 列表组件
│       ├── SearchForm.vue      # 搜索表单
│       ├── TableList.vue       # 表格列表
│       ├── FilterPanel.vue     # 筛选面板
│       └── BatchActions.vue    # 批量操作
│
├── detail/             # 资产详情
│   ├── index.vue      # 详情主页面
│   └── components/    # 详情组件
│       ├── BasicInfo.vue       # 基本信息
│       ├── UsageRecord.vue     # 使用记录
│       ├── MaintenanceLog.vue  # 维护记录
│       └── OperationLog.vue    # 操作日志
│
├── purchase/          # 资产采购
│   ├── index.vue     # 采购主页面
│   └── components/   # 采购组件
│       ├── PurchaseForm.vue    # 采购表单
│       ├── ApprovalFlow.vue    # 审批流程
│       └── Supplier.vue        # 供应商管理
│
├── maintenance/       # 资产维护
│   ├── index.vue     # 维护主页面
│   └── components/   # 维护组件
│       ├── RepairForm.vue      # 维修表单
│       ├── CheckForm.vue       # 检查表单
│       └── Schedule.vue        # 维护计划
│
└── components/        # 公共组件
    ├── AssetType.vue         # 资产类型
    ├── AssetStatus.vue       # 资产状态
    └── LocationPicker.vue    # 位置选择
```

## 3. 功能清单

### 3.1 资产管理
- [x] 资产列表
  - 资产查询
    - 资产编号
    - 资产名称
    - 资产类型
    - 使用状态
    - 所属部门
    - 责任人
  - 资产新增
  - 资产编辑
  - 资产删除
  - 批量操作
  - 导入导出

- [x] 资产详情
  - 基本信息
    - 资产编号
    - 资产名称
    - 资产类型
    - 规格型号
    - 购入日期
    - 购入价格
    - 使用年限
    - 折旧方法
    - 资产状态
  - 使用记录
    - 领用记录
    - 归还记录
    - 转移记录
    - 维修记录
  - 维护记录
    - 保养记录
    - 维修记录
    - 检查记录
    - 维护计划

### 3.2 资产操作
- [x] 资产采购
  - 采购申请
  - 审批流程
  - 供应商管理
  - 入库管理
  - 验收记录

- [x] 资产领用
  - 领用申请
  - 审批流程
  - 领用确认
  - 归还管理
  - 超期提醒

### 3.3 资产维护
- [x] 维护管理
  - 维护计划
  - 维修申请
  - 维修记录
  - 保养记录
  - 检查记录

## 4. API 接口

### 4.1 数据结构
```typescript
// 资产基本信息
interface Asset {
  id: string;                 // 资产ID
  assetNo: string;           // 资产编号
  name: string;              // 资产名称
  type: AssetType;           // 资产类型
  status: AssetStatus;       // 资产状态
  specification: string;     // 规格型号
  purchaseDate: string;      // 购入日期
  purchasePrice: number;     // 购入价格
  usefulLife: number;        // 使用年限
  department: string;        // 所属部门
  location: string;          // 存放位置
  custodian: string;        // 保管人
  createTime: string;       // 创建时间
  updateTime: string;       // 更新时间
}

// 资产类型
enum AssetType {
  OFFICE = 'office',        // 办公设备
  ELECTRONIC = 'electronic',// 电子设备
  FURNITURE = 'furniture',  // 办公家具
  VEHICLE = 'vehicle',      // 车辆
  OTHER = 'other'          // 其他
}

// 资产状态
enum AssetStatus {
  IDLE = 'idle',           // 闲置
  IN_USE = 'in_use',       // 使用中
  MAINTENANCE = 'maintenance',// 维护中
  SCRAPPED = 'scrapped'    // 已报废
}

// 资产操作记录
interface AssetOperation {
  id: string;              // 记录ID
  assetId: string;         // 资产ID
  type: OperationType;     // 操作类型
  operator: string;        // 操作人
  operateTime: string;     // 操作时间
  remark: string;          // 备注说明
}

// 维护记录
interface Maintenance {
  id: string;              // 记录ID
  assetId: string;         // 资产ID
  type: MaintenanceType;   // 维护类型
  content: string;         // 维护内容
  maintainer: string;      // 维护人
  cost: number;           // 维护费用
  startTime: string;      // 开始时间
  endTime: string;        // 结束时间
  status: string;         // 维护状态
  remark: string;         // 备注说明
}
```

### 4.2 接口列表
```typescript
// 获取资产列表
export const getAssetList = (params: AssetListParams) => {
  return http.get('/asset/list', { params });
};

// 获取资产详情
export const getAssetDetail = (id: string) => {
  return http.get(`/asset/detail/${id}`);
};

// 创建资产
export const createAsset = (data: Partial<Asset>) => {
  return http.post('/asset/create', data);
};

// 更新资产
export const updateAsset = (id: string, data: Partial<Asset>) => {
  return http.put(`/asset/update/${id}`, data);
};

// 删除资产
export const deleteAsset = (id: string) => {
  return http.delete(`/asset/delete/${id}`);
};

// 资产领用
export const borrowAsset = (data: AssetBorrowParams) => {
  return http.post('/asset/borrow', data);
};

// 资产归还
export const returnAsset = (data: AssetReturnParams) => {
  return http.post('/asset/return', data);
};

// 添加维护记录
export const addMaintenance = (data: Partial<Maintenance>) => {
  return http.post('/asset/maintenance/add', data);
};

// 获取维护记录
export const getMaintenanceList = (assetId: string) => {
  return http.get(`/asset/maintenance/list/${assetId}`);
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能模块     | 管理员   | 律所主任   | 合伙人      | 执业律师      | 实习律师   | 行政人员    |
|------------|----------|------------|------------|--------------|------------|------------|
| 查看资产     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | ✓个人      | ✓部门全权   |
| 资产申请     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | ✓个人      | ✓部门相关   |
| 资产审批     | ✓全所    | ✓全所      | ✓团队      | -            | -          | ✓部门全权   |
| 资产调配     | ✓全所    | ✓全所      | -          | -            | -          | ✓部门全权   |
| 资产维护     | ✓全所    | ✓全所      | -          | -            | -          | ✓部门全权   |
| 资产报废     | ✓全所    | ✓全所      | -          | -            | -          | ✓部门全权   |
| 统计分析     | ✓全所    | ✓全所      | ✓团队      | -            | -          | ✓部门全权   |
| 导出数据     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | -          | ✓部门全权   |

### 5.2 数据权限说明

1. 数据访问范围
   - 管理员：可查看和管理所有资产
   - 律所主任：可查看所有资产，审批资产相关申请
   - 合伙人：可查看团队资产，申请资产使用
   - 执业律师：可查看个人使用的资产，申请资产使用
   - 实习律师：可查看个人使用的资产，申请资产使用
   - 行政人员：负责资产日常管理，处理资产相关申请

2. 特殊权限说明
   - 资产采购：需要通过审批流程
   - 资产领用：需要审批，特殊资产可能需要多级审批
   - 资产维护：行政人员负责日常维护管理
   - 资产报废：需要通过审批流程

3. 审批流程权限
   - 一般资产：行政人员审批
   - 贵重资产：需要律所主任审批
   - 大额采购：需要管理员审批

### 5.3 路由配置
```typescript
// router/modules/asset.ts
export default {
  path: '/asset',
  name: 'Asset',
  component: LAYOUT,
  meta: {
    title: '资产管理',
    icon: 'ant-design:database-outlined',
    roles: ['admin', 'director', 'partner', 'lawyer', 'trainee', 'admin_finance']
  },
  children: [
    {
      path: 'list',
      name: 'AssetList',
      component: () => import('@/views/asset/list/index.vue'),
      meta: {
        title: '资产列表'
      }
    },
    {
      path: 'detail/:id',
      name: 'AssetDetail',
      component: () => import('@/views/asset/detail/index.vue'),
      meta: {
        title: '资产详情',
        hideMenu: true
      }
    },
    {
      path: 'purchase',
      name: 'AssetPurchase',
      component: () => import('@/views/asset/purchase/index.vue'),
      meta: {
        title: '资产采购',
        roles: ['admin', 'director', 'admin_finance']
      }
    },
    {
      path: 'maintenance',
      name: 'AssetMaintenance',
      component: () => import('@/views/asset/maintenance/index.vue'),
      meta: {
        title: '资产维护',
        roles: ['admin', 'director', 'admin_finance']
      }
    }
  ]
};
```

## 6. 状态管理

### 6.1 Store 结构
```typescript
// store/modules/asset.ts
interface AssetState {
  assetList: Asset[];
  currentAsset: Asset | null;
  maintenanceList: Maintenance[];
  loading: boolean;
  pagination: {
    current: number;
    pageSize: number;
    total: number;
  };
  searchParams: Recordable;
  filterOptions: {
    types: AssetType[];
    statuses: AssetStatus[];
  };
}

const useAssetStore = defineStore({
  id: 'app-asset',
  state: (): AssetState => ({
    assetList: [],
    currentAsset: null,
    maintenanceList: [],
    loading: false,
    pagination: {
      current: 1,
      pageSize: 10,
      total: 0
    },
    searchParams: {},
    filterOptions: {
      types: [],
      statuses: []
    }
  }),
  getters: {
    getAssetList(): Asset[] {
      return this.assetList;
    },
    getCurrentAsset(): Asset | null {
      return this.currentAsset;
    },
    getMaintenanceList(): Maintenance[] {
      return this.maintenanceList;
    }
  },
  actions: {
    async fetchAssetList() {
      this.loading = true;
      try {
        const { items, total } = await getAssetList({
          ...this.searchParams,
          page: this.pagination.current,
          pageSize: this.pagination.pageSize
        });
        this.assetList = items;
        this.pagination.total = total;
      } finally {
        this.loading = false;
      }
    },
    async fetchAssetDetail(id: string) {
      this.loading = true;
      try {
        this.currentAsset = await getAssetDetail(id);
      } finally {
        this.loading = false;
      }
    },
    async fetchMaintenanceList(assetId: string) {
      this.loading = true;
      try {
        this.maintenanceList = await getMaintenanceList(assetId);
      } finally {
        this.loading = false;
      }
    }
  }
});
```

## 7. 使用说明

### 7.1 资产采购流程
1. 进入资产采购页面
2. 填写采购申请
   - 资产名称
   - 资产类型
   - 规格型号
   - 采购数量
   - 预算金额
   - 供应商信息
3. 提交审批流程
4. 采购执行
5. 资产验收
6. 资产入库

### 7.2 资产领用流程
1. 进入资产列表页面
2. 选择需要领用的资产
3. 填写领用申请
   - 领用人
   - 领用时间
   - 预计归还时间
   - 用途说明
4. 提交审批
5. 领用确认
6. 按时归还

### 7.3 资产维护
1. 定期维护
   - 制定维护计划
   - 执行维护检查
   - 记录维护情况
   - 更新维护计划

2. 故障维修
   - 提交维修申请
   - 确定维修方案
   - 执行维修工作
   - 验收确认

### 7.4 注意事项
1. 资产安全
   - 妥善保管资产
   - 定期盘点核查
   - 及时报修维护
   - 规范使用操作

2. 责任管理
   - 明确保管责任
   - 定期交接确认
   - 损坏赔偿制度
   - 使用登记管理

3. 数据维护
   - 及时更新信息
   - 准确记录使用
   - 完整维护记录
   - 定期数据备份

## 8. 最佳实践

### 8.1 资产管理
1. 建立资产编码体系
2. 实施定期盘点制度
3. 执行预防性维护
4. 优化资产配置

### 8.2 使用管理
1. 规范借用流程
2. 及时登记变更
3. 定期检查状态
4. 追踪使用记录

### 8.3 维护保养
1. 制定维护计划
2. 执行定期检查
3. 记录维护情况
4. 评估维护效果

### 8.4 数据管理
1. 及时更新信息
2. 定期数据核查
3. 完善使用记录
4. 优化数据结构 

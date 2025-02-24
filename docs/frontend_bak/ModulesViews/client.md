# 客户管理模块

## 1. 功能概述

客户管理模块是律师事务所管理系统的基础模块，用于管理律所的所有客户信息，包括个人客户和企业客户，提供完整的客户生命周期管理功能。

## 2. 页面结构

```bash
views/client/
├── list/                # 客户列表页
│   ├── index.vue       # 列表主页面
│   └── components/     # 列表页组件
│       ├── SearchForm.vue      # 搜索表单
│       ├── TableColumns.vue    # 表格列配置
│       └── ImportExport.vue    # 导入导出组件
│
├── detail/             # 客户详情页
│   ├── index.vue      # 详情主页面
│   └── components/    # 详情页组件
│       ├── BasicInfo.vue       # 基本信息
│       ├── CaseList.vue        # 相关案件
│       ├── ContractList.vue    # 相关合同
│       ├── ContactRecord.vue   # 联系记录
│       └── FinanceRecord.vue   # 财务记录
│
└── components/         # 公共组件
    ├── ClientForm.vue  # 客户表单
    ├── ClientType.vue  # 客户类型
    └── ContactInfo.vue # 联系信息
```

## 3. 功能清单

### 3.1 客户列表
- [x] 客户搜索
  - 客户编号
  - 客户名称
  - 客户类型（个人/企业）
  - 联系方式
  - 所属律师
  - 创建时间
  
- [x] 客户列表展示
  - 分页显示
  - 排序功能
  - 类型标记
  - 快捷操作
  - 数据导出

- [x] 客户导入导出
  - Excel导入
  - Excel导出
  - 数据校验
  - 错误提示

### 3.2 客户详情
- [x] 基本信息
  - 个人客户信息
    - 姓名
    - 性别
    - 身份证号
    - 联系电话
    - 电子邮箱
    - 通讯地址
    - 职业信息
  - 企业客户信息
    - 企业名称
    - 统一社会信用代码
    - 法定代表人
    - 注册资本
    - 企业地址
    - 经营范围

- [x] 联系人管理
  - 联系人列表
  - 添加联系人
  - 编辑联系人
  - 设置主要联系人

- [x] 相关业务
  - 案件列表
  - 合同列表
  - 文档列表
  - 财务记录

- [x] 跟进记录
  - 联系记录
  - 访问记录
  - 服务记录
  - 投诉记录

### 3.3 统计分析
- [x] 客户分析
  - 客户来源分析
  - 客户类型分析
  - 地域分布分析
  - 行业分布分析

- [x] 业务分析
  - 案件数量统计
  - 合同金额统计
  - 服务评价分析
  - 客户活跃度分析

## 4. API 接口

### 4.1 数据结构
```typescript
// 客户基本信息
interface Client {
  id: string;                 // 客户ID
  clientNo: string;          // 客户编号
  type: ClientType;          // 客户类型
  name: string;              // 客户名称
  status: ClientStatus;      // 客户状态
  level: ClientLevel;        // 客户等级
  source: string;            // 客户来源
  lawyerId: string[];        // 负责律师ID
  createTime: string;        // 创建时间
  updateTime: string;        // 更新时间
}

// 个人客户信息
interface PersonalClient extends Client {
  gender: string;            // 性别
  idCard: string;           // 身份证号
  birthday: string;         // 出生日期
  occupation: string;       // 职业
  workUnit: string;         // 工作单位
}

// 企业客户信息
interface CorporateClient extends Client {
  creditCode: string;       // 统一社会信用代码
  legalPerson: string;      // 法定代表人
  registeredCapital: number;// 注册资本
  industry: string;         // 所属行业
  businessScope: string;    // 经营范围
}

// 客户类型
enum ClientType {
  PERSONAL = 'personal',    // 个人客户
  CORPORATE = 'corporate'   // 企业客户
}

// 客户状态
enum ClientStatus {
  ACTIVE = 'active',       // 活跃
  INACTIVE = 'inactive',   // 不活跃
  BLOCKED = 'blocked'      // 已封禁
}

// 客户等级
enum ClientLevel {
  VIP = 'vip',            // VIP客户
  REGULAR = 'regular',    // 普通客户
  POTENTIAL = 'potential' // 潜在客户
}
```

### 4.2 接口列表
```typescript
// 获取客户列表
export const getClientList = (params: ClientListParams) => {
  return http.get('/client/list', { params });
};

// 获取客户详情
export const getClientDetail = (id: string) => {
  return http.get(`/client/detail/${id}`);
};

// 创建客户
export const createClient = (data: Partial<Client>) => {
  return http.post('/client/create', data);
};

// 更新客户
export const updateClient = (id: string, data: Partial<Client>) => {
  return http.put(`/client/update/${id}`, data);
};

// 删除客户
export const deleteClient = (id: string) => {
  return http.delete(`/client/delete/${id}`);
};

// 导入客户
export const importClients = (file: File) => {
  const formData = new FormData();
  formData.append('file', file);
  return http.post('/client/import', formData);
};

// 导出客户
export const exportClients = (params: ClientExportParams) => {
  return http.get('/client/export', { params, responseType: 'blob' });
};
```

## 5. 权限控制

### 5.1 角色权限
| 功能模块     | 管理员   | 律所主任   | 合伙人      | 执业律师      | 实习律师   | 行政人员    |
|------------|----------|------------|------------|--------------|------------|------------|
| 查看客户     | ✓全所    | ✓全所      | ✓团队      | ✓个人+团队    | ✓个人/只读  | ✓部门相关   |
| 新增客户     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | -          | -          |
| 编辑客户     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | -          | ✓部门相关   |
| 删除客户     | ✓全所    | ✓全所      | ✓团队      | -            | -          | -          |
| 客户分配     | ✓全所    | ✓全所      | ✓团队      | -            | -          | -          |
| 联系记录     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | ✓个人      | ✓部门相关   |
| 客户分析     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | -          | ✓部门相关   |
| 导出数据     | ✓全所    | ✓全所      | ✓团队      | ✓个人        | -          | ✓部门相关   |

### 5.2 数据权限说明

1. 数据访问范围
   - 管理员和律所主任：可查看和管理所有客户数据
   - 合伙人：可查看和管理本团队的客户数据
   - 执业律师：可查看个人负责的客户，以及团队内共享的客户数据
   - 实习律师：仅可查看被分配的客户数据，无修改权限
   - 行政/财务人员：可查看与本部门相关的客户数据

2. 特殊权限说明
   - 客户创建：执业律师可以创建新客户，但需要关联到团队
   - 客户编辑：执业律师只能编辑自己负责的客户信息
   - 客户删除：仅管理员和律所主任有权限
   - 财务信息：财务人员对客户财务信息有完整操作权限
   - 敏感信息：部分敏感信息（如证件号码、财务信息）需要特殊权限才能查看

3. 数据共享规则
   - 团队内部：团队成员可共享客户基本信息
   - 跨团队：需要通过审批流程进行授权
   - 财务信息：仅财务部门和高级管理层可查看完整财务记录
   - 联系记录：创建人和团队负责人可查看

### 5.3 路由配置
```typescript
// router/modules/client.ts
export default {
  path: '/client',
  name: 'Client',
  component: LAYOUT,
  meta: {
    title: '客户管理',
    icon: 'ant-design:user-outlined',
    roles: ['admin', 'lawyer', 'clerk', 'finance']
  },
  children: [
    {
      path: 'list',
      name: 'ClientList',
      component: () => import('@/views/client/list/index.vue'),
      meta: {
        title: '客户列表'
      }
    },
    {
      path: 'detail/:id',
      name: 'ClientDetail',
      component: () => import('@/views/client/detail/index.vue'),
      meta: {
        title: '客户详情',
        hideMenu: true
      }
    }
  ]
};
```

## 6. 状态管理

### 6.1 Store 结构
```typescript
// store/modules/client.ts
interface ClientState {
  clientList: Client[];
  currentClient: Client | null;
  loading: boolean;
  pagination: {
    current: number;
    pageSize: number;
    total: number;
  };
  searchParams: Recordable;
  filterOptions: {
    types: ClientType[];
    levels: ClientLevel[];
    sources: string[];
  };
}

const useClientStore = defineStore({
  id: 'app-client',
  state: (): ClientState => ({
    clientList: [],
    currentClient: null,
    loading: false,
    pagination: {
      current: 1,
      pageSize: 10,
      total: 0
    },
    searchParams: {},
    filterOptions: {
      types: [],
      levels: [],
      sources: []
    }
  }),
  getters: {
    getClientList(): Client[] {
      return this.clientList;
    },
    getCurrentClient(): Client | null {
      return this.currentClient;
    }
  },
  actions: {
    async fetchClientList() {
      this.loading = true;
      try {
        const { items, total } = await getClientList({
          ...this.searchParams,
          page: this.pagination.current,
          pageSize: this.pagination.pageSize
        });
        this.clientList = items;
        this.pagination.total = total;
      } finally {
        this.loading = false;
      }
    },
    async fetchClientDetail(id: string) {
      this.loading = true;
      try {
        this.currentClient = await getClientDetail(id);
      } finally {
        this.loading = false;
      }
    }
  }
});
```

## 7. 使用说明

### 7.1 客户创建流程
1. 进入客户列表页面
2. 点击"新建客户"按钮
3. 选择客户类型（个人/企业）
4. 填写基本信息
   - 个人客户：姓名、证件信息、联系方式等
   - 企业客户：企业名称、统一社会信用代码、法定代表人等
5. 选择负责律师和所属团队
6. 设置客户等级和来源
7. 上传相关证明文件
8. 提交创建

### 7.2 客户管理操作
1. 信息维护
   - 定期更新客户信息
   - 记录重要联系记录
   - 维护相关案件信息
   - 更新财务往来记录

2. 客户分类管理
   - 按照客户等级分类
   - 按照业务类型分类
   - 按照地域分布分类
   - 按照行业领域分类

3. 客户关系维护
   - 定期回访计划
   - 重要节日问候
   - 法律资讯推送
   - 满意度调查

### 7.3 数据导入导出
1. 数据导入
   - 支持 Excel 格式导入
   - 按模板填写数据
   - 系统自动校验数据
   - 错误数据提示修正

2. 数据导出
   - 支持多种导出格式
   - 可选择导出字段
   - 支持批量导出
   - 导出数据加密

### 7.4 注意事项
1. 客户信息安全
   - 严格控制敏感信息访问权限
   - 定期审查数据访问记录
   - 遵守数据保护相关法规
   - 防止客户信息泄露

2. 数据质量管理
   - 确保必填信息完整
   - 定期清理无效数据
   - 及时更新变更信息
   - 保持数据一致性

3. 客户服务规范
   - 遵守职业道德规范
   - 保持服务质量标准
   - 及时响应客户需求
   - 妥善处理投诉建议

4. 系统使用建议
   - 定期备份重要数据
   - 及时记录客户沟通
   - 做好工作交接记录
   - 遵守操作规程

## 8. 注意事项

1. 客户编号规则
   - 个人客户：P + 年份 + 序号，如：P2024001
   - 企业客户：C + 年份 + 序号，如：C2024001
   - 自动生成，不可修改

2. 数据安全
   - 客户信息脱敏处理
   - 敏感操作需要二次确认
   - 操作日志完整记录

3. 数据完整性
   - 必填字段验证
   - 数据格式校验
   - 重复数据检查

4. 性能优化
   - 列表页使用虚拟滚动
   - 大批量导入使用队列处理
   - 详情页按需加载 

## 9. 工作流程

### 9.1 客户引入流程
1. 初步接触
   - 来访登记
   - 基本信息采集
   - 需求初步了解
   - 利益冲突检查

2. 立项评估
   - 业务可行性评估
   - 风险等级评估
   - 团队资源评估
   - 收费标准确定

3. 客户建档
   - 基础信息录入
   - 证明文件收集
   - 团队分配确定
   - 服务协议签订

4. 后续跟进
   - 定期回访计划
   - 服务质量跟踪
   - 满意度调查
   - 潜在需求挖掘

### 9.2 审批流程
1. 重要信息变更
   - 提交变更申请
   - 部门主管审核
   - 分管领导审批
   - 变更记录存档

2. 客户信息共享
   - 提出共享申请
   - 原团队负责人审批
   - 目标团队确认
   - 设置共享权限

3. 客户终止流程
   - 终止原因说明
   - 服务总结报告
   - 档案资料归档
   - 财务结算确认

## 10. 系统集成

### 10.1 外部系统集成
1. 工商信息查询
   - 企业信息核验
   - 经营状态监控
   - 变更信息推送
   - 风险信息预警

2. 司法信息查询
   - 诉讼信息查询
   - 失信人查询
   - 执行信息查询
   - 法律风险评估

3. 电子签章系统
   - 合同在线签署
   - 签章权限管理
   - 签署记录存档
   - 签署流程追踪

### 10.2 内部系统集成
1. 案件管理系统
   - 客户案件关联
   - 案件信息同步
   - 文档共享调用
   - 进度实时更新

2. 财务管理系统
   - 收费记录关联
   - 账单自动生成
   - 收付款管理
   - 财务报表统计

3. 知识管理系统
   - 相关案例推送
   - 法律文书模板
   - 法规更新提醒
   - 业务经验分享

## 11. 常见问题

### 11.1 数据问题
1. 重复客户处理
   - 系统自动查重
   - 手动合并规则
   - 历史数据保留
   - 关联信息处理

2. 数据迁移注意事项
   - 数据格式转换
   - 字段映射规则
   - 数据完整性检查
   - 迁移日志记录

3. 数据备份策略
   - 定时自动备份
   - 手动备份方式
   - 备份数据恢复
   - 备份日志管理

### 11.2 权限问题
1. 跨团队协作
   - 临时权限申请
   - 协作范围限定
   - 操作权限控制
   - 协作记录追踪

2. 敏感信息访问
   - 访问权限申请
   - 使用期限设置
   - 操作行为记录
   - 定期权限审查

### 11.3 业务问题
1. 客户分类调整
   - 调整申请流程
   - 历史记录保留
   - 相关人员通知
   - 服务方案更新

2. 客户信息变更
   - 变更申请提交
   - 佐证材料上传
   - 审批流程处理
   - 变更记录存档

3. 客户投诉处理
   - 投诉信息记录
   - 处理流程分配
   - 解决方案跟踪
   - 满意度回访

## 12. 最佳实践

### 12.1 数据管理
1. 定期数据质量检查
2. 及时更新客户信息
3. 做好数据备份工作
4. 保持数据一致性

### 12.2 安全管理
1. 严格权限分配
2. 定期安全审计
3. 敏感信息加密
4. 操作日志完整

### 12.3 服务管理
1. 标准化服务流程
2. 及时响应客户需求
3. 定期客户回访
4. 持续改进服务质量

### 12.4 团队协作
1. 规范信息共享
2. 加强沟通协调
3. 明确职责分工
4. 做好工作交接

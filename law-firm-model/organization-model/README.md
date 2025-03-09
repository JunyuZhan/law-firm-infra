# 组织架构模型模块 (Organization Model)

## 模块说明
组织架构模型模块是律师事务所管理系统的基础数据模型，负责定义系统中所有与组织架构相关的实体类、数据传输对象、视图对象和枚举类型。该模块是整个系统的组织管理基础，为其他模块提供组织架构相关的数据支持。

## 目录结构
```
organization/
├── constants/                # 常量定义
│   ├── OrganizationConstants.java      # 组织模块核心常量
│   ├── OrganizationFieldConstants.java # 组织字段相关常量（长度限制、默认值等）
│   ├── FirmFieldConstants.java         # 律所字段相关常量
│   └── DepartmentFieldConstants.java   # 部门字段相关常量
│
├── entity/                  # 实体类
│   ├── base/               # 基础实体
│   │   └── BaseOrganizationEntity.java  # 组织实体基类（ID、编码、名称等通用属性）
│   │
│   ├── history/            # 历史记录实体
│   │   └── OrganizationChangeHistory.java # 组织变更历史实体（记录所有变更）
│   │
│   ├── relation/           # 关联关系实体
│   │   └── EmployeeOrganization.java    # 员工与组织关联实体
│   │
│   ├── firm/              # 律所相关实体
│   │   ├── LawFirm.java     # 律师事务所实体（执业许可、营业信息等）
│   │   └── Branch.java      # 分所实体（分所信息、区域信息等）
│   │
│   ├── department/        # 部门相关实体
│   │   ├── Department.java    # 部门基础实体（类型、负责人等）
│   │   ├── BusinessDept.java  # 业务部门实体（业务领域、案件类型等）
│   │   └── FunctionalDept.java # 职能部门实体（职能类型、服务范围等）
│   │
│   └── team/             # 团队相关实体
│       ├── Team.java         # 团队基础实体（团队类型、负责人等）
│       └── ProjectGroup.java  # 项目组实体（项目信息、成员等）
│
├── dto/                  # 数据传输对象
│   ├── firm/            # 律所相关DTO
│   ├── department/      # 部门相关DTO
│   ├── position/        # 职位相关DTO
│   └── team/            # 团队相关DTO
│
├── vo/                  # 视图对象
│   ├── firm/           # 律所相关VO
│   ├── department/     # 部门相关VO
│   ├── position/       # 职位相关VO
│   └── team/           # 团队相关VO
│
├── enums/              # 枚举定义
│   ├── FirmTypeEnum.java        # 律所类型枚举（合伙制、个人所等）
│   ├── DepartmentTypeEnum.java  # 部门类型枚举（业务部、职能部等）
│   ├── PositionTypeEnum.java    # 职位类型枚举
│   ├── CenterTypeEnum.java      # 中心类型枚举
│   └── TeamTypeEnum.java        # 团队类型枚举（专业团队、项目组等）
│
├── mapper/             # MyBatis Mapper接口
│   ├── firm/          # 律所相关Mapper
│   ├── department/    # 部门相关Mapper
│   └── team/          # 团队相关Mapper
│
└── service/           # 服务接口定义（不包含实现）
    ├── OrganizationTreeService.java             # 组织树服务接口
    ├── OrganizationPersonnelRelationService.java # 组织与人员关系服务接口
    ├── OrganizationAuthBridge.java              # 与认证模块的桥接接口
    ├── PositionService.java                      # 职位服务接口
    ├── firm/         # 律所相关服务接口
    ├── department/   # 部门相关服务接口
    └── team/         # 团队相关服务接口
```

## 核心功能

### 1. 律师事务所管理
- 总分所体系管理
  - 总所信息维护
  - 分所信息管理
  - 总分所关系维护
- 基本信息管理
  - 律所基本信息
  - 执业许可信息
  - 联系方式信息
  - 地址信息管理
- 营业状态管理
  - 正常营业
  - 停业整顿
  - 注销管理

### 2. 部门管理
- 部门类型
  - 业务部门（诉讼部、非诉部等）
  - 职能部门（行政部、财务部等）
- 部门属性
  - 基本信息维护
  - 负责人管理
  - 联系方式管理
  - 职能描述管理

### 3. 职位管理
- 职位体系维护
  - 职位名称管理
  - 职位等级管理
  - 岗位职责管理
- 职级晋升路径
  - 晋升序列定义
  - 职级要求管理
  - 晋升资格规则

### 4. 团队管理
- 团队基本属性
  - 团队基本信息
  - 团队负责人
  - 团队成员管理
  - 团队状态管理
- 项目组管理
  - 项目基本信息
  - 项目进度管理
  - 项目成员管理
  - 关联案件管理

### 5. 组织变更历史
- 变更记录管理
  - 变更类型记录
  - 变更内容记录
  - 变更人员记录
  - 变更时间记录
- 审计跟踪
  - 组织结构审计
  - 人员变动审计
  - 权限变更审计

## 核心类说明

### 1. 基础实体类
- BaseOrganizationEntity：组织实体基类
  - 基础属性：编码、名称、描述等
  - 状态管理：启用/禁用状态
  - 排序管理：排序号
  - 备注信息：备注字段

- OrganizationChangeHistory：组织变更历史实体
  - 变更信息：变更类型、变更内容
  - 审计信息：操作人、操作时间
  - 实体关联：关联实体类型、实体ID
  - 变更前后：JSON格式存储变更前后的数据

### 2. 关联关系实体类
- EmployeeOrganization：员工与组织关联实体
  - 关联信息：员工ID、组织ID、关联类型
  - 时间信息：开始时间、结束时间
  - 状态信息：当前状态
  - 关联属性：主要关联标记、排序号

### 3. 业务实体类
- LawFirm：律师事务所实体
  - 基本信息：名称、编码、许可证号等
  - 营业信息：成立日期、营业状态等
  - 联系信息：地址、电话、邮箱等
  - 总分所关系：是否总所、总所ID等
  
- Department：部门实体
  - 部门属性：类型、职能描述等
  - 负责人信息：负责人ID、姓名等
  - 所属关系：所属律所ID等
  - 上下级关系：父部门ID、子部门列表

- Position：职位实体（注：位于service包中）
  - 职位属性：名称、类型、职级等
  - 部门关联：所属部门ID等
  - 职责描述：工作职责、要求等
  - 职位状态：启用/禁用状态

- Team：团队基础实体
  - 团队属性：类型、规模等
  - 负责人信息：负责人ID、姓名等
  - 所属关系：所属律所ID、部门ID等
  - 团队状态：筹备中、运行中、已解散等

### 4. 常量类
- OrganizationConstants：组织核心常量
  - 状态常量：各类状态定义
  - 类型常量：组织类型常量
  - 错误码常量：错误码定义
  - 业务规则常量：业务规则相关常量

- OrganizationFieldConstants：组织字段常量
  - 通用字段名称常量
  - 字段长度常量
  - 默认值常量

- FirmFieldConstants：律所字段常量
  - 律所表字段常量
  - 律所业务字段常量
  - 表单验证常量

- DepartmentFieldConstants：部门字段常量
  - 部门表字段常量
  - 部门类型常量
  - 部门业务规则常量

### 5. 服务接口
- OrganizationTreeService：组织树服务接口
  - 组织树构建
  - 组织结构查询
  - 组织路径管理

- OrganizationPersonnelRelationService：组织与人员关系服务接口
  - 员工组织关联管理
  - 组织人员查询
  - 人员组织查询

- PositionService：职位服务接口
  - 职位基本信息管理
  - 职级体系管理
  - 晋升路径管理

- OrganizationAuthBridge：组织与认证桥接接口
  - 组织权限关联
  - 组织与角色关联
  - 组织数据权限配置

### 6. 枚举类
- FirmTypeEnum：律所类型枚举
  - 合伙制律所
  - 个人所
  - 国有律所等

- DepartmentTypeEnum：部门类型枚举
  - 业务部门
  - 职能部门
  - 虚拟部门等

- PositionTypeEnum：职位类型枚举
  - 管理类职位
  - 专业类职位
  - 辅助类职位等

- TeamTypeEnum：团队类型枚举
  - 专业团队
  - 项目组
  - 任务小组等

- CenterTypeEnum：中心类型枚举
  - 业务中心
  - 研发中心
  - 培训中心等

## 与其他模块的关系

### 1. 与人事模块(personnel-model)的关系
- 提供组织结构数据：部门、职位等基础数据
- 通过OrganizationPersonnelRelationService实现与人事模块的松耦合集成
- 职位变更同步到员工职位历史
- 组织变更可能触发人员调整
- EmployeeOrganization实体记录员工与组织的关联关系

### 2. 与权限模块(auth-model)的关系
- 组织结构是权限配置的基础
- 通过OrganizationAuthBridge接口与认证模块集成
- 部门、团队等组织单位可作为数据权限的边界
- 职位可与角色关联，形成基于职位的权限控制

### 3. 与案件模块(case-model)的关系
- 部门分配案件
- 团队/项目组承接案件
- 案件按组织单位统计和报表
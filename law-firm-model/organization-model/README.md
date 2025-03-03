# 组织架构模型模块 (Organization Model)

## 模块说明
组织架构模型模块是律师事务所管理系统的基础数据模型，负责定义系统中所有与组织架构相关的实体类、数据传输对象、视图对象和枚举类型。该模块是整个系统的组织管理基础，为其他模块提供组织架构相关的数据支持。

## 目录结构
```
organization/
├── constants/                # 常量定义（仅数据模型相关）
│   ├── OrganizationFieldConstants.java  # 字段相关常量（长度限制、默认值等）
│   ├── FirmFieldConstants.java          # 律所字段常量
│   └── DepartmentFieldConstants.java    # 部门字段常量
├── entity/                  # 实体类
│   ├── base/               # 基础实体
│   │   ├── BaseOrganizationEntity.java  # 组织实体基类（ID、编码、名称等通用属性）
│   ├── firm/              # 律所相关实体
│   │   ├── LawFirm.java     # 律师事务所实体（执业许可、营业信息等）
│   │   ├── Branch.java      # 分所实体（总分所关系、区域信息等）
│   │   └── Office.java      # 办公室实体（位置、使用状态等）
│   ├── department/        # 部门相关实体
│   │   ├── Department.java    # 部门基础实体（类型、负责人等）
│   │   ├── BusinessDept.java  # 业务部门实体（业务领域、案件类型等）
│   │   └── FunctionalDept.java # 职能部门实体（职能类型、服务范围等）
│   └── team/             # 团队相关实体
│       ├── Team.java         # 团队基础实体（团队类型、负责人等）
│       └── ProjectGroup.java  # 项目组实体（项目信息、成员等）
├── dto/                  # 数据传输对象
│   ├── firm/            # 律所相关DTO
│   │   ├── LawFirmDTO.java   # 律所数据传输（创建、更新请求等）
│   │   └── BranchDTO.java    # 分所数据传输（创建、关联等）
│   ├── department/      # 部门相关DTO
│   │   └── DepartmentDTO.java # 部门数据传输（创建、人员变更等）
│   └── team/            # 团队相关DTO
│       └── TeamDTO.java      # 团队数据传输（创建、成员管理等）
├── vo/                  # 视图对象
│   ├── firm/           # 律所相关VO
│   │   ├── LawFirmVO.java   # 律所视图（列表展示、详情等）
│   │   └── BranchVO.java    # 分所视图（列表展示、关系等）
│   ├── department/     # 部门相关VO
│   │   └── DepartmentVO.java # 部门视图（树形展示、详情等）
│   └── team/           # 团队相关VO
│       └── TeamVO.java      # 团队视图（列表展示、成员等）
├── enums/              # 枚举定义
│   ├── FirmTypeEnum.java     # 律所类型枚举（总所、分所等）
│   ├── DepartmentTypeEnum.java # 部门类型枚举（业务部、职能部等）
│   └── TeamTypeEnum.java      # 团队类型枚举（专业团队、项目组等）
└── service/           # 服务接口定义（不包含实现）
    ├── firm/         # 律所相关服务接口
    │   ├── LawFirmService.java  # 律所管理服务接口
    │   └── BranchService.java   # 分所管理服务接口
    ├── department/   # 部门相关服务接口
    │   └── DepartmentService.java # 部门管理服务接口
    └── team/         # 团队相关服务接口
        └── TeamService.java     # 团队管理服务接口
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
  - 管理部门（主任办公室等）
- 部门结构
  - 树形组织结构
  - 部门层级关系
  - 部门排序管理
- 部门属性
  - 基本信息维护
  - 负责人管理
  - 联系方式管理
  - 职能描述管理

### 3. 团队管理
- 业务团队
  - 专业领域团队
  - 项目团队
  - 客户服务团队
- 团队属性
  - 团队基本信息
  - 团队负责人
  - 团队成员管理
  - 团队状态管理

### 4. 办公地点管理
- 办公室信息
  - 办公地点管理
  - 办公室分配
  - 使用状态管理
- 工位管理
  - 工位信息维护
  - 工位分配状态
  - 工位使用记录

## 核心类说明

### 1. 基础实体类
- BaseOrganizationEntity：组织实体基类
  - 基础属性：编码、名称、描述等
  - 状态管理：启用/禁用状态
  - 排序管理：排序号
  - 备注信息：备注字段

- TreeEntity：树形结构实体基类
  - 继承自BaseOrganizationEntity
  - 父子关系：父级ID、子节点数量
  - 层级管理：层级路径、层级深度
  - 节点属性：是否叶子节点

### 2. 业务实体类
- LawFirm：律师事务所实体
  - 基本信息：名称、编码、许可证号等
  - 营业信息：成立日期、营业状态等
  - 联系信息：地址、电话、邮箱等
  - 总分所关系：是否总所、总所ID等

- Department：部门实体
  - 继承自TreeEntity
  - 部门属性：类型、职能描述等
  - 负责人信息：负责人ID、姓名等
  - 所属关系：所属律所ID等

### 3. 常量类
- OrganizationConstants：组织通用常量
  - 状态常量
  - 类型常量
  - 错误码常量
  - 业务规则常量
- FirmConstants：律所相关常量
  - 律所类型常量
  - 营业状态常量
  - 业务规则常量
- DepartmentConstants：部门相关常量
  - 部门类型常量
  - 部门状态常量
  - 业务规则常量

### 4. 服务接口
- LawFirmService：律所服务接口
  - 律所基本信息管理
  - 总分所关系管理
  - 律所状态管理
- DepartmentService：部门服务接口
  - 部门基本信息管理
  - 部门层级关系管理
  - 部门人员管理
- TeamService：团队服务接口
  - 团队基本信息管理
  - 团队成员管理
  - 团队状态管理

### 5. 异常类
- OrganizationException：组织模块基础异常
  - 通用错误码定义
  - 异常信息模板
- FirmException：律所相关异常
  - 律所特有错误码
  - 律所业务异常
- DepartmentException：部门相关异常
  - 部门特有错误码
  - 部门业务异常

### 6. 工具类
- OrganizationUtils：组织通用工具类
  - 编码生成工具
  - 状态检查工具
  - 数据校验工具
- TreeEntityUtils：树形结构工具类
  - 树形结构构建
  - 层级路径处理
  - 节点关系处理

## 使用说明

### 1. 依赖引入
```
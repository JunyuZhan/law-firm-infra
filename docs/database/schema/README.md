# 数据库表结构设计

## 系统表结构

### 1. 系统管理相关表
| 表名 | 说明 | 备注 |
|------|------|------|
| sys_user | 用户表 | 存储系统用户信息 |
| sys_role | 角色表 | 存储角色信息 |
| sys_menu | 菜单表 | 存储菜单和权限信息 |
| sys_user_role | 用户角色关联表 | 用户和角色的关联关系 |
| sys_role_menu | 角色菜单关联表 | 角色和菜单的关联关系 |
| sys_dict | 字典表 | 存储系统字典数据 |
| sys_config | 配置表 | 存储系统配置信息 |
| sys_log | 日志表 | 存储系统操作日志 |

### 2. 组织架构相关表
| 表名 | 说明 | 备注 |
|------|------|------|
| org_department | 部门表 | 存储部门信息 |
| org_position | 职位表 | 存储职位信息 |
| org_employee | 员工表 | 存储员工基本信息 |
| org_lawyer | 律师表 | 存储律师特有信息 |

### 3. 案件管理相关表
| 表名 | 说明 | 备注 |
|------|------|------|
| case_info | 案件信息表 | 存储案件基本信息 |
| case_party | 当事人表 | 存储案件当事人信息 |
| case_lawyer | 案件律师关联表 | 案件和律师的关联关系 |
| case_document | 案件文档表 | 存储案件相关文档 |
| case_schedule | 案件日程表 | 存储案件相关日程 |

### 4. 客户管理相关表
| 表名 | 说明 | 备注 |
|------|------|------|
| client_info | 客户信息表 | 存储客户基本信息 |
| client_contact | 客户联系人表 | 存储客户联系人信息 |
| client_follow | 客户跟进表 | 存储客户跟进记录 |
| client_service | 客户服务表 | 存储客户服务记录 |

### 5. 合同管理相关表
| 表名 | 说明 | 备注 |
|------|------|------|
| contract_info | 合同信息表 | 存储合同基本信息 |
| contract_party | 合同方表 | 存储合同相关方信息 |
| contract_payment | 合同收款表 | 存储合同收款记录 |
| contract_review | 合同审批表 | 存储合同审批记录 |

## 表结构详细说明

### 1. 系统用户表 (sys_user)
```sql
CREATE TABLE sys_user (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    email VARCHAR(100) COMMENT '邮箱',
    mobile VARCHAR(20) COMMENT '手机号',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    dept_id BIGINT COMMENT '部门ID',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    create_by BIGINT COMMENT '创建人',
    update_by BIGINT COMMENT '更新人',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';
```

### 2. 案件信息表 (case_info)
```sql
CREATE TABLE case_info (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    case_number VARCHAR(50) NOT NULL COMMENT '案件编号',
    case_name VARCHAR(200) NOT NULL COMMENT '案件名称',
    case_type VARCHAR(50) COMMENT '案件类型',
    client_id BIGINT COMMENT '客户ID',
    lead_lawyer_id BIGINT COMMENT '主办律师ID',
    status VARCHAR(50) COMMENT '案件状态',
    priority VARCHAR(20) COMMENT '优先级',
    filing_date DATE COMMENT '立案日期',
    closing_date DATE COMMENT '结案日期',
    description TEXT COMMENT '案件描述',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    create_by BIGINT COMMENT '创建人',
    update_by BIGINT COMMENT '更新人',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_case_number (case_number),
    KEY idx_client_id (client_id),
    KEY idx_lead_lawyer_id (lead_lawyer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='案件信息表';
```

### 3. 客户信息表 (client_info)
```sql
CREATE TABLE client_info (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    client_number VARCHAR(50) NOT NULL COMMENT '客户编号',
    client_name VARCHAR(200) NOT NULL COMMENT '客户名称',
    client_type VARCHAR(50) COMMENT '客户类型',
    industry VARCHAR(100) COMMENT '所属行业',
    credit_code VARCHAR(50) COMMENT '统一社会信用代码',
    legal_representative VARCHAR(50) COMMENT '法定代表人',
    address VARCHAR(500) COMMENT '地址',
    contact_name VARCHAR(50) COMMENT '联系人姓名',
    contact_phone VARCHAR(20) COMMENT '联系人电话',
    contact_email VARCHAR(100) COMMENT '联系人邮箱',
    status VARCHAR(20) COMMENT '客户状态',
    source VARCHAR(50) COMMENT '客户来源',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    create_by BIGINT COMMENT '创建人',
    update_by BIGINT COMMENT '更新人',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_client_number (client_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户信息表';
```

### 4. 合同信息表 (contract_info)
```sql
CREATE TABLE contract_info (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    contract_number VARCHAR(50) NOT NULL COMMENT '合同编号',
    contract_name VARCHAR(200) NOT NULL COMMENT '合同名称',
    contract_type VARCHAR(50) COMMENT '合同类型',
    case_id BIGINT COMMENT '关联案件ID',
    client_id BIGINT COMMENT '客户ID',
    amount DECIMAL(15,2) COMMENT '合同金额',
    sign_date DATE COMMENT '签订日期',
    start_date DATE COMMENT '生效日期',
    end_date DATE COMMENT '到期日期',
    status VARCHAR(50) COMMENT '合同状态',
    description TEXT COMMENT '合同描述',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    create_by BIGINT COMMENT '创建人',
    update_by BIGINT COMMENT '更新人',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_contract_number (contract_number),
    KEY idx_case_id (case_id),
    KEY idx_client_id (client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同信息表';
```

## 表关系说明

### 1. 用户权限关系
```
sys_user -> sys_user_role -> sys_role -> sys_role_menu -> sys_menu
```

### 2. 案件关系
```
case_info -> case_lawyer -> org_lawyer
case_info -> case_party -> client_info
case_info -> case_document
```

### 3. 客户关系
```
client_info -> client_contact
client_info -> client_follow
client_info -> client_service
```

### 4. 合同关系
```
contract_info -> contract_party
contract_info -> contract_payment
contract_info -> contract_review
```

## 数据字典

### 1. 状态字典
- 通用状态：0-禁用，1-正常
- 删除标记：0-未删除，1-已删除
- 案件状态：PENDING-待处理，PROCESSING-处理中，COMPLETED-已完成
- 合同状态：DRAFT-草稿，REVIEWING-审核中，ACTIVE-生效中，EXPIRED-已到期

### 2. 类型字典
- 案件类型：CIVIL-民事案件，CRIMINAL-刑事案件，ADMINISTRATIVE-行政案件
- 客户类型：PERSONAL-个人客户，ENTERPRISE-企业客户，GOVERNMENT-政府机构
- 合同类型：SERVICE-服务合同，CONSULTING-咨询合同，AGENCY-代理合同 
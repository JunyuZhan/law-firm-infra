-- Client模块表结构初始化
-- 版本: V4001
-- 模块: client
-- 创建时间: 2023-08-01
-- 说明: 创建客户管理模块相关表结构

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- client_info表（客户基本信息表）
CREATE TABLE IF NOT EXISTS client_info (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  client_no VARCHAR(32) NOT NULL COMMENT '客户编号',
  client_name VARCHAR(100) NOT NULL COMMENT '客户名称',
  client_type INTEGER DEFAULT 1 COMMENT '客户类型（1-个人客户 2-企业客户 3-政府机构 4-社会组织）',
  client_level INTEGER DEFAULT 1 COMMENT '客户等级（1-普通客户 2-重要客户 3-VIP客户）',
  client_source INTEGER DEFAULT 1 COMMENT '客户来源（1-主动咨询 2-同行推荐 3-客户推荐 4-广告宣传 5-老客户）',
  industry VARCHAR(50) COMMENT '客户行业',
  scale VARCHAR(50) COMMENT '客户规模',
  phone VARCHAR(20) COMMENT '联系电话',
  email VARCHAR(100) COMMENT '电子邮箱',
  manager_id BIGINT COMMENT '负责人ID',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常 1-停用）',
  id_type VARCHAR(20) COMMENT '证件类型（个人客户：身份证/护照/其他，企业客户：营业执照）',
  id_number VARCHAR(50) COMMENT '证件号码',
  credit_level VARCHAR(2) COMMENT '信用等级（A/B/C/D）',
  legal_representative VARCHAR(50) COMMENT '法定代表人（企业客户）',
  unified_social_credit_code VARCHAR(50) COMMENT '统一社会信用代码（企业客户）',
  case_count INTEGER DEFAULT 0 COMMENT '案件总数',
  active_case_count INTEGER DEFAULT 0 COMMENT '活跃案件数',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_client_no (client_no),
  KEY idx_client_name (client_name),
  KEY idx_client_type (client_type),
  KEY idx_client_level (client_level),
  KEY idx_manager_id (manager_id),
  KEY idx_id_number (id_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户基本信息表';

-- client_contact表（客户联系人表）
CREATE TABLE IF NOT EXISTS client_contact (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  client_id BIGINT NOT NULL COMMENT '客户ID',
  contact_name VARCHAR(50) NOT NULL COMMENT '联系人姓名',
  contact_type INTEGER DEFAULT 1 COMMENT '联系人类型（1-法定代表人 2-业务联系人 3-财务联系人 4-其他）',
  department VARCHAR(50) COMMENT '所属部门',
  position VARCHAR(50) COMMENT '职务职位',
  mobile VARCHAR(20) COMMENT '手机号码',
  telephone VARCHAR(20) COMMENT '固定电话',
  email VARCHAR(100) COMMENT '电子邮箱',
  importance INTEGER DEFAULT 1 COMMENT '重要程度（1-普通 2-重要 3-非常重要）',
  is_default INTEGER DEFAULT 0 COMMENT '是否默认联系人（0-否 1-是）',
  status INTEGER DEFAULT 1 COMMENT '联系人状态（0-无效 1-有效）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_client_id (client_id),
  KEY idx_contact_name (contact_name),
  KEY idx_is_default (is_default),
  KEY idx_mobile (mobile)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户联系人表';

-- client_address表（客户地址表）
CREATE TABLE IF NOT EXISTS client_address (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  client_id BIGINT NOT NULL COMMENT '客户ID',
  address_type INTEGER DEFAULT 1 COMMENT '地址类型（1-注册地址 2-办公地址 3-收件地址 4-开票地址 99-其他）',
  country VARCHAR(50) DEFAULT '中国' COMMENT '国家地区',
  province VARCHAR(50) COMMENT '省份',
  city VARCHAR(50) COMMENT '城市',
  district VARCHAR(50) COMMENT '区县',
  address VARCHAR(255) NOT NULL COMMENT '详细地址',
  postcode VARCHAR(20) COMMENT '邮政编码',
  is_default INTEGER DEFAULT 0 COMMENT '是否默认地址（0-否 1-是）',
  address_tag VARCHAR(50) COMMENT '地址标签',
  status INTEGER DEFAULT 1 COMMENT '地址状态（0-无效 1-有效）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_client_id (client_id),
  KEY idx_address_type (address_type),
  KEY idx_is_default (is_default)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户地址表';

-- client_category表（客户分类表）
CREATE TABLE IF NOT EXISTS client_category (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  category_name VARCHAR(50) NOT NULL COMMENT '分类名称',
  category_code VARCHAR(50) NOT NULL COMMENT '分类编码',
  level INTEGER DEFAULT 1 COMMENT '分类层级',
  parent_id BIGINT DEFAULT 0 COMMENT '上级分类ID',
  category_path VARCHAR(255) COMMENT '分类路径',
  description VARCHAR(255) COMMENT '分类描述',
  sort_weight INTEGER DEFAULT 0 COMMENT '排序权重',
  status INTEGER DEFAULT 1 COMMENT '分类状态（0-禁用 1-启用）',
  allow_select INTEGER DEFAULT 1 COMMENT '是否允许客户选择（0-不允许 1-允许）',
  is_system INTEGER DEFAULT 0 COMMENT '是否系统预置（0-否 1-是）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_category_code (category_code),
  KEY idx_parent_id (parent_id),
  KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户分类表';

-- client_tag表（客户标签表）
CREATE TABLE IF NOT EXISTS client_tag (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  tag_name VARCHAR(50) NOT NULL COMMENT '标签名称',
  color VARCHAR(20) COMMENT '标签颜色',
  tag_type VARCHAR(30) COMMENT '标签类型',
  sort INTEGER DEFAULT 0 COMMENT '排序',
  status INTEGER DEFAULT 1 COMMENT '状态（0-禁用，1-启用）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_tag_name (tag_name),
  KEY idx_tag_type (tag_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户标签表';

-- client_tag_relation表（客户标签关联表）
CREATE TABLE IF NOT EXISTS client_tag_relation (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  client_id BIGINT NOT NULL COMMENT '客户ID',
  tag_id BIGINT NOT NULL COMMENT '标签ID',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_client_tag (client_id, tag_id),
  KEY idx_client_id (client_id),
  KEY idx_tag_id (tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户标签关联表';

-- client_relation表（客户关系表）
CREATE TABLE IF NOT EXISTS client_relation (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  relation_type VARCHAR(50) NOT NULL COMMENT '关系类型',
  source_client_id BIGINT NOT NULL COMMENT '源客户ID',
  target_client_id BIGINT NOT NULL COMMENT '目标客户ID',
  relation_desc VARCHAR(255) COMMENT '关系描述',
  start_time DATETIME COMMENT '开始时间',
  end_time DATETIME COMMENT '结束时间',
  status INTEGER DEFAULT 1 COMMENT '状态（0-停用 1-正常）',
  effective_time DATETIME COMMENT '生效时间',
  expiry_time DATETIME COMMENT '失效时间',
  priority INTEGER DEFAULT 0 COMMENT '优先级',
  attributes TEXT COMMENT '关系属性（JSON格式）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_source_client_id (source_client_id),
  KEY idx_target_client_id (target_client_id),
  KEY idx_relation_type (relation_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户关系表';

-- client_follow_up表（客户跟进记录表）
CREATE TABLE IF NOT EXISTS client_follow_up (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  client_id BIGINT NOT NULL COMMENT '客户ID',
  follow_up_type VARCHAR(30) NOT NULL COMMENT '跟进类型（电话、邮件、拜访等）',
  content TEXT NOT NULL COMMENT '跟进内容',
  result VARCHAR(500) COMMENT '跟进结果',
  business_id BIGINT COMMENT '关联业务ID（案件/合同）',
  business_type VARCHAR(30) COMMENT '业务类型',
  next_follow_time DATETIME COMMENT '下次跟进时间',
  is_remind INTEGER DEFAULT 0 COMMENT '是否需要提醒（0-否，1-是）',
  remind_time DATETIME COMMENT '提醒时间',
  remind_type VARCHAR(20) COMMENT '提醒方式（邮件、短信等）',
  assignee_id BIGINT COMMENT '负责人ID',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_client_id (client_id),
  KEY idx_business_id (business_id, business_type),
  KEY idx_follow_up_type (follow_up_type),
  KEY idx_next_follow_time (next_follow_time),
  KEY idx_assignee_id (assignee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户跟进记录表';

-- case_party表（案件当事人表）
CREATE TABLE IF NOT EXISTS case_party (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  case_id BIGINT NOT NULL COMMENT '案件ID',
  relation_type VARCHAR(50) NOT NULL COMMENT '关系类型',
  source_client_id BIGINT NOT NULL COMMENT '源客户ID',
  target_client_id BIGINT NOT NULL COMMENT '目标客户ID',
  relation_desc VARCHAR(255) COMMENT '关系描述',
  party_type INTEGER NOT NULL COMMENT '当事人类型（1-原告 2-被告 3-第三人）',
  party_role VARCHAR(50) COMMENT '当事人角色',
  agent_type INTEGER COMMENT '代理类型（1-特别授权 2-一般授权）',
  entrust_time DATETIME COMMENT '委托时间',
  agent_authorities TEXT COMMENT '代理权限（JSON格式）',
  case_status INTEGER DEFAULT 0 COMMENT '案件状态（0-未开始 1-进行中 2-已结束）',
  start_time DATETIME COMMENT '开始时间',
  end_time DATETIME COMMENT '结束时间',
  status INTEGER DEFAULT 1 COMMENT '状态（0-停用 1-正常）',
  effective_time DATETIME COMMENT '生效时间',
  expiry_time DATETIME COMMENT '失效时间',
  priority INTEGER DEFAULT 0 COMMENT '优先级',
  attributes TEXT COMMENT '关系属性（JSON格式）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_case_id (case_id),
  KEY idx_source_client_id (source_client_id),
  KEY idx_party_type (party_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='案件当事人表';

-- contract_party表（合同当事人表）
CREATE TABLE IF NOT EXISTS contract_party (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  contract_id BIGINT NOT NULL COMMENT '合同ID',
  relation_type VARCHAR(50) NOT NULL COMMENT '关系类型',
  source_client_id BIGINT NOT NULL COMMENT '源客户ID',
  target_client_id BIGINT NOT NULL COMMENT '目标客户ID',
  relation_desc VARCHAR(255) COMMENT '关系描述',
  party_type INTEGER NOT NULL COMMENT '当事人类型（1-甲方 2-乙方 3-丙方）',
  sign_role VARCHAR(50) COMMENT '签约角色',
  sign_qualification INTEGER DEFAULT 1 COMMENT '签约资格（0-无效 1-有效）',
  sign_time DATETIME COMMENT '签约时间',
  performance_status INTEGER DEFAULT 0 COMMENT '履约状态（0-未履行 1-履约中 2-已履行 3-违约）',
  contract_rights TEXT COMMENT '合同权利（JSON格式）',
  contract_obligations TEXT COMMENT '合同义务（JSON格式）',
  start_time DATETIME COMMENT '开始时间',
  end_time DATETIME COMMENT '结束时间',
  status INTEGER DEFAULT 1 COMMENT '状态（0-停用 1-正常）',
  effective_time DATETIME COMMENT '生效时间',
  expiry_time DATETIME COMMENT '失效时间',
  priority INTEGER DEFAULT 0 COMMENT '优先级',
  attributes TEXT COMMENT '关系属性（JSON格式）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_contract_id (contract_id),
  KEY idx_source_client_id (source_client_id),
  KEY idx_party_type (party_type),
  KEY idx_performance_status (performance_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同当事人表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1;

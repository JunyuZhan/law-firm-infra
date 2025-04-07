-- 客户管理模块表结构初始化脚本

-- 客户分类表
CREATE TABLE IF NOT EXISTS client_category (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  category_name VARCHAR(50) NOT NULL COMMENT '分类名称',
  category_code VARCHAR(50) NOT NULL COMMENT '分类编码',
  parent_id BIGINT DEFAULT 0 COMMENT '父级ID',
  level INT DEFAULT 1 COMMENT '层级',
  sort_order INT DEFAULT 0 COMMENT '排序',
  description VARCHAR(255) DEFAULT NULL COMMENT '描述',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by VARCHAR(64) DEFAULT NULL COMMENT '更新人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除(0-否,1-是)',
  UNIQUE KEY uk_category_code (category_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户分类表';

-- 客户信息表
CREATE TABLE IF NOT EXISTS client_info (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  client_name VARCHAR(100) NOT NULL COMMENT '客户名称',
  client_code VARCHAR(50) NOT NULL COMMENT '客户编码',
  client_type VARCHAR(20) NOT NULL COMMENT '客户类型(INDIVIDUAL-个人,COMPANY-企业,GOVERNMENT-政府机构)',
  business_license VARCHAR(100) DEFAULT NULL COMMENT '营业执照号码',
  unified_social_credit_code VARCHAR(100) DEFAULT NULL COMMENT '统一社会信用代码',
  tax_number VARCHAR(100) DEFAULT NULL COMMENT '税务登记号',
  registered_capital DECIMAL(20,2) DEFAULT NULL COMMENT '注册资本',
  industry VARCHAR(50) DEFAULT NULL COMMENT '所属行业',
  address VARCHAR(255) DEFAULT NULL COMMENT '地址',
  postal_code VARCHAR(20) DEFAULT NULL COMMENT '邮政编码',
  website VARCHAR(100) DEFAULT NULL COMMENT '网址',
  email VARCHAR(100) DEFAULT NULL COMMENT '电子邮箱',
  telephone VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
  fax VARCHAR(20) DEFAULT NULL COMMENT '传真',
  legal_representative VARCHAR(50) DEFAULT NULL COMMENT '法定代表人',
  contact_person VARCHAR(50) DEFAULT NULL COMMENT '联系人',
  contact_position VARCHAR(50) DEFAULT NULL COMMENT '联系人职位',
  contact_phone VARCHAR(20) DEFAULT NULL COMMENT '联系人电话',
  contact_email VARCHAR(100) DEFAULT NULL COMMENT '联系人邮箱',
  principal_id BIGINT DEFAULT NULL COMMENT '负责人ID',
  principal_name VARCHAR(50) DEFAULT NULL COMMENT '负责人名称',
  category_id BIGINT DEFAULT NULL COMMENT '客户分类ID',
  category_name VARCHAR(50) DEFAULT NULL COMMENT '客户分类名称',
  source VARCHAR(50) DEFAULT NULL COMMENT '客户来源',
  status VARCHAR(20) DEFAULT 'NORMAL' COMMENT '状态(NORMAL-正常,FROZEN-冻结,BLACKLIST-黑名单)',
  importance VARCHAR(20) DEFAULT 'NORMAL' COMMENT '重要程度(VIP,HIGH,NORMAL,LOW)',
  credit_rating VARCHAR(20) DEFAULT 'NORMAL' COMMENT '信用等级(EXCELLENT,GOOD,NORMAL,BAD)',
  annual_revenue DECIMAL(20,2) DEFAULT NULL COMMENT '年收入',
  employee_count INT DEFAULT NULL COMMENT '员工人数',
  established_date DATE DEFAULT NULL COMMENT '成立日期',
  description TEXT DEFAULT NULL COMMENT '客户描述',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by VARCHAR(64) DEFAULT NULL COMMENT '更新人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除(0-否,1-是)',
  UNIQUE KEY uk_client_code (client_code),
  INDEX idx_client_name (client_name),
  INDEX idx_client_type (client_type),
  INDEX idx_principal_id (principal_id),
  INDEX idx_category_id (category_id),
  INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户信息表';

-- 客户跟踪记录表
CREATE TABLE IF NOT EXISTS client_follow_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  client_id BIGINT NOT NULL COMMENT '客户ID',
  client_name VARCHAR(100) NOT NULL COMMENT '客户名称',
  follow_type VARCHAR(50) NOT NULL COMMENT '跟踪类型(PHONE-电话,VISIT-拜访,EMAIL-邮件,MEETING-会议)',
  follow_content TEXT NOT NULL COMMENT '跟踪内容',
  follow_time DATETIME NOT NULL COMMENT '跟踪时间',
  follow_user_id BIGINT NOT NULL COMMENT '跟踪人ID',
  follow_user_name VARCHAR(50) NOT NULL COMMENT '跟踪人姓名',
  next_follow_time DATETIME DEFAULT NULL COMMENT '下次跟踪时间',
  next_follow_content VARCHAR(255) DEFAULT NULL COMMENT '下次跟踪内容',
  status VARCHAR(20) DEFAULT 'NORMAL' COMMENT '状态(NORMAL-正常,PROCESSING-处理中,COMPLETED-已完成)',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by VARCHAR(64) DEFAULT NULL COMMENT '更新人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除(0-否,1-是)',
  INDEX idx_client_id (client_id),
  INDEX idx_follow_time (follow_time),
  INDEX idx_follow_user_id (follow_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户跟踪记录表';

-- 客户附件表
CREATE TABLE IF NOT EXISTS client_attachment (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  client_id BIGINT NOT NULL COMMENT '客户ID',
  attachment_name VARCHAR(100) NOT NULL COMMENT '附件名称',
  attachment_type VARCHAR(50) NOT NULL COMMENT '附件类型',
  file_path VARCHAR(255) NOT NULL COMMENT '文件路径',
  file_size BIGINT NOT NULL COMMENT '文件大小',
  upload_time DATETIME NOT NULL COMMENT '上传时间',
  upload_user_id BIGINT NOT NULL COMMENT '上传人ID',
  upload_user_name VARCHAR(50) NOT NULL COMMENT '上传人姓名',
  description VARCHAR(255) DEFAULT NULL COMMENT '附件描述',
  create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by VARCHAR(64) DEFAULT NULL COMMENT '更新人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除(0-否,1-是)',
  INDEX idx_client_id (client_id),
  INDEX idx_upload_time (upload_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户附件表'; 
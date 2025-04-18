-- Auth模块表结构初始化
-- 版本: V2001
-- 模块: auth
-- 创建时间: 2023-06-15
-- 说明: 创建认证模块的所有相关表结构

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- auth_user表（用户表）
CREATE TABLE IF NOT EXISTS auth_user (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  username VARCHAR(50) NOT NULL COMMENT '用户名',
  password VARCHAR(100) NOT NULL COMMENT '密码',
  email VARCHAR(100) COMMENT '邮箱',
  mobile VARCHAR(20) COMMENT '手机号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  employee_id BIGINT COMMENT '关联的员工ID',
  last_login_time DATETIME COMMENT '最后登录时间',
  last_login_ip VARCHAR(50) COMMENT '最后登录IP',
  account_expire_time DATETIME COMMENT '账号过期时间',
  password_expire_time DATETIME COMMENT '密码过期时间',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='认证用户表';

-- auth_role表（角色表）
CREATE TABLE IF NOT EXISTS auth_role (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  name VARCHAR(50) NOT NULL COMMENT '角色名称',
  code VARCHAR(50) NOT NULL COMMENT '角色编码',
  type INTEGER DEFAULT 1 COMMENT '角色类型（0-系统角色，1-自定义角色）',
  data_scope INTEGER DEFAULT 0 COMMENT '数据范围（0-全部数据，1-本部门及以下数据，2-本部门数据，3-个人数据，4-自定义数据）',
  business_role_type VARCHAR(50) COMMENT '业务角色类型',
  parent_id BIGINT DEFAULT 0 COMMENT '父级角色ID，用于角色继承',
  sort INTEGER DEFAULT 0 COMMENT '显示顺序',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_role_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- auth_permission表（权限表）
CREATE TABLE IF NOT EXISTS auth_permission (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  name VARCHAR(50) NOT NULL COMMENT '权限名称',
  code VARCHAR(100) NOT NULL COMMENT '权限编码',
  type INTEGER DEFAULT 0 COMMENT '权限类型（0-菜单，1-按钮，2-API）',
  operation_type INTEGER DEFAULT 0 COMMENT '操作类型（0-完全权限，1-只读权限，2-申请权限，3-创建权限，4-编辑权限，5-删除权限，6-审批权限）',
  data_scope INTEGER DEFAULT 0 COMMENT '数据权限范围（0-全所数据，1-团队数据，2-个人数据，3-部门数据，4-自定义数据）',
  module VARCHAR(50) COMMENT '业务模块',
  parent_id BIGINT DEFAULT 0 COMMENT '父级权限ID',
  path VARCHAR(200) COMMENT '权限路径',
  icon VARCHAR(100) COMMENT '图标',
  component VARCHAR(255) COMMENT '组件路径',
  permission VARCHAR(100) COMMENT '权限标识',
  visible TINYINT DEFAULT 1 COMMENT '是否可见（0-隐藏，1-显示）',
  external INTEGER DEFAULT 0 COMMENT '是否外链（0-否，1-是）',
  cache INTEGER DEFAULT 0 COMMENT '是否缓存（0-否，1-是）',
  redirect VARCHAR(255) COMMENT '重定向路径',
  sort INTEGER DEFAULT 0 COMMENT '显示顺序',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_permission_code (code),
  KEY idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- auth_user_role表（用户角色关联表）
CREATE TABLE IF NOT EXISTS auth_user_role (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  user_id BIGINT NOT NULL COMMENT '用户ID',
  role_id BIGINT NOT NULL COMMENT '角色ID',
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
  UNIQUE KEY uk_user_role (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- auth_role_permission表（角色权限关联表）
CREATE TABLE IF NOT EXISTS auth_role_permission (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  role_id BIGINT NOT NULL COMMENT '角色ID',
  permission_id BIGINT NOT NULL COMMENT '权限ID',
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
  UNIQUE KEY uk_role_permission (role_id, permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- auth_login_history表（登录历史表）
CREATE TABLE IF NOT EXISTS auth_login_history (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  user_id BIGINT NOT NULL COMMENT '用户ID',
  username VARCHAR(50) NOT NULL COMMENT '用户名',
  login_time DATETIME NOT NULL COMMENT '登录时间',
  logout_time DATETIME COMMENT '登出时间',
  login_ip VARCHAR(50) COMMENT '登录IP',
  login_location VARCHAR(100) COMMENT '登录地点',
  browser VARCHAR(50) COMMENT '浏览器类型',
  os VARCHAR(50) COMMENT '操作系统',
  status INTEGER DEFAULT 0 COMMENT '状态（0-成功，1-失败）',
  msg VARCHAR(255) COMMENT '消息（成功或失败原因）',
  login_type VARCHAR(20) COMMENT '登录类型（用户名密码、手机号、微信等）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_user_id (user_id),
  KEY idx_login_time (login_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录历史表';

-- sys_permission_request表（权限申请表）
CREATE TABLE IF NOT EXISTS sys_permission_request (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  user_id BIGINT NOT NULL COMMENT '申请用户ID',
  business_type VARCHAR(50) COMMENT '业务类型',
  business_id BIGINT COMMENT '业务ID',
  permission_code VARCHAR(100) NOT NULL COMMENT '权限编码',
  reason VARCHAR(500) COMMENT '申请理由',
  status INTEGER DEFAULT 0 COMMENT '状态：0-待审批, 1-已批准, 2-已拒绝',
  approver_id BIGINT COMMENT '审批人ID',
  approve_time DATETIME COMMENT '审批时间',
  approve_remark VARCHAR(500) COMMENT '审批备注',
  expire_time DATETIME COMMENT '权限过期时间',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_user_id (user_id),
  KEY idx_status (status),
  KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限申请表';

-- sys_team_permission表（团队权限关联表）
CREATE TABLE IF NOT EXISTS sys_team_permission (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  team_id BIGINT NOT NULL COMMENT '团队ID',
  permission_id BIGINT NOT NULL COMMENT '权限ID',
  user_id BIGINT COMMENT '用户ID（可选，如果为null表示团队所有成员都具有该权限）',
  resource_type VARCHAR(50) COMMENT '资源类型（可选，用于记录权限针对的具体资源类型）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  UNIQUE KEY uk_team_permission_user (team_id, permission_id, user_id),
  KEY idx_team_id (team_id),
  KEY idx_permission_id (permission_id),
  KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队权限关联表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 
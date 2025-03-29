-- 系统用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) COMMENT '昵称',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    avatar VARCHAR(255) COMMENT '头像',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 系统角色表
CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_code VARCHAR(50) NOT NULL COMMENT '角色编码',
    description VARCHAR(255) COMMENT '角色描述',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    business_role_type VARCHAR(50) COMMENT '业务角色类型(ADMIN,DIRECTOR,PARTNER,LAWYER,TRAINEE,FINANCE)',
    data_scope TINYINT DEFAULT 0 COMMENT '数据范围：0-全部数据，1-本部门及以下数据，2-本部门数据，3-个人数据，4-自定义数据',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_role_code (role_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_user_role (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 创建菜单表
CREATE TABLE IF NOT EXISTS sys_menu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
    name VARCHAR(50) NOT NULL COMMENT '菜单名称',
    path VARCHAR(200) COMMENT '路由路径',
    component VARCHAR(255) COMMENT '组件路径',
    perms VARCHAR(100) COMMENT '权限标识',
    type TINYINT NOT NULL COMMENT '类型: 0-目录, 1-菜单, 2-按钮',
    icon VARCHAR(100) COMMENT '图标',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    visible TINYINT DEFAULT 1 COMMENT '是否可见: 0-隐藏, 1-显示',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
    creator BIGINT COMMENT '创建人',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater BIGINT COMMENT '更新人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除'
);

-- 角色菜单关联表
CREATE TABLE IF NOT EXISTS sys_role_menu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    creator BIGINT COMMENT '创建人',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater BIGINT COMMENT '更新人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除'
);

-- 部门表
CREATE TABLE IF NOT EXISTS sys_dept (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父部门ID',
    name VARCHAR(50) NOT NULL COMMENT '部门名称',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    leader VARCHAR(50) COMMENT '负责人',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
    creator BIGINT COMMENT '创建人',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater BIGINT COMMENT '更新人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除'
);

-- 创建权限表
CREATE TABLE IF NOT EXISTS sys_permission (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    permission_name VARCHAR(50) NOT NULL COMMENT '权限名称',
    permission_code VARCHAR(50) NOT NULL COMMENT '权限编码',
    permission_type TINYINT NOT NULL COMMENT '权限类型：0-菜单，1-按钮，2-API',
    operation_type TINYINT DEFAULT 0 COMMENT '操作类型：0-完全权限，1-只读权限，2-申请权限，3-创建权限，4-编辑权限，5-删除权限，6-审批权限',
    data_scope TINYINT DEFAULT 0 COMMENT '数据范围：0-全所数据，1-团队数据，2-个人数据，3-部门数据，4-自定义数据',
    module VARCHAR(50) COMMENT '业务模块：CASE,CLIENT,DOCUMENT,FINANCE等',
    parent_id BIGINT DEFAULT 0 COMMENT '父级ID',
    icon VARCHAR(100) COMMENT '图标',
    path VARCHAR(255) COMMENT '路径',
    component VARCHAR(255) COMMENT '组件',
    sort INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_permission_code (permission_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统权限表';

-- 创建角色权限关联表
CREATE TABLE IF NOT EXISTS sys_role_permission (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_id BIGINT NOT NULL COMMENT '角色ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_role_permission (role_id, permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 创建团队权限关联表
CREATE TABLE IF NOT EXISTS sys_team_permission (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_id BIGINT NOT NULL COMMENT '团队ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    user_id BIGINT COMMENT '用户ID（可选，如果为null表示团队所有成员都具有该权限）',
    resource_type VARCHAR(50) COMMENT '资源类型（可选，用于记录权限针对的具体资源类型）',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_team_permission (team_id, permission_id, user_id, resource_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队权限关联表';

-- 创建登录历史表
CREATE TABLE IF NOT EXISTS sys_login_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    ip VARCHAR(50) COMMENT 'IP地址',
    device VARCHAR(255) COMMENT '设备信息',
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    status TINYINT DEFAULT 1 COMMENT '状态：0-失败，1-成功',
    message VARCHAR(255) COMMENT '消息'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录历史表';

-- 创建字典表
CREATE TABLE IF NOT EXISTS sys_dict (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  dict_name VARCHAR(50) NOT NULL,
  dict_code VARCHAR(50) NOT NULL,
  description VARCHAR(200),
  created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted TINYINT DEFAULT 0
);

-- 认证令牌表
CREATE TABLE IF NOT EXISTS auth_token (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  access_token VARCHAR(255) NOT NULL,
  refresh_token VARCHAR(255),
  token_type VARCHAR(20) DEFAULT 'Bearer',
  expires_in BIGINT NOT NULL,
  scope VARCHAR(100),
  created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted TINYINT DEFAULT 0
);

-- 存储桶表
CREATE TABLE IF NOT EXISTS storage_bucket (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  bucket_name VARCHAR(100) NOT NULL,
  storage_type VARCHAR(20) DEFAULT 'LOCAL',
  status TINYINT DEFAULT 1,
  created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted TINYINT DEFAULT 0
);

-- 文件对象表
CREATE TABLE IF NOT EXISTS storage_file (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  bucket_id BIGINT NOT NULL,
  file_name VARCHAR(255) NOT NULL,
  file_size BIGINT DEFAULT 0,
  content_type VARCHAR(100),
  storage_path VARCHAR(255) NOT NULL,
  status TINYINT DEFAULT 1,
  created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted TINYINT DEFAULT 0
);

-- 创建权限申请表
CREATE TABLE IF NOT EXISTS sys_permission_request (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '申请用户',
    business_type VARCHAR(50) COMMENT '业务类型',
    business_id BIGINT COMMENT '业务ID',
    permission_code VARCHAR(100) COMMENT '权限编码',
    reason VARCHAR(500) COMMENT '申请理由',
    status TINYINT DEFAULT 0 COMMENT '状态: 0-待审批, 1-已批准, 2-已拒绝',
    approver_id BIGINT COMMENT '审批人',
    approve_time DATETIME COMMENT '审批时间',
    approve_remark VARCHAR(500) COMMENT '审批备注',
    expire_time DATETIME COMMENT '权限过期时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限申请表';

-- 用户表
CREATE TABLE IF NOT EXISTS `auth_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `account_expire_time` datetime DEFAULT NULL COMMENT '账号过期时间',
  `password_expire_time` datetime DEFAULT NULL COMMENT '密码过期时间',
  `employee_id` bigint(20) DEFAULT NULL COMMENT '关联员工ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `tenant_code` varchar(50) DEFAULT NULL COMMENT '租户编码',
  `version` int(11) DEFAULT 0 COMMENT '版本号',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS `auth_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `code` varchar(50) NOT NULL COMMENT '角色编码',
  `status` tinyint(4) DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `tenant_code` varchar(50) DEFAULT NULL COMMENT '租户编码',
  `version` int(11) DEFAULT 0 COMMENT '版本号',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS `auth_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `tenant_code` varchar(50) DEFAULT NULL COMMENT '租户编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 权限表
CREATE TABLE IF NOT EXISTS `auth_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) NOT NULL COMMENT '权限名称',
  `code` varchar(50) NOT NULL COMMENT '权限编码',
  `type` varchar(20) NOT NULL COMMENT '权限类型(menu-菜单,button-按钮,api-接口)',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父权限ID',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `path` varchar(200) DEFAULT NULL COMMENT '路径',
  `component` varchar(200) DEFAULT NULL COMMENT '组件',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `status` tinyint(4) DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
  `visible` tinyint(1) DEFAULT 1 COMMENT '是否可见(0-隐藏,1-显示)',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `tenant_code` varchar(50) DEFAULT NULL COMMENT '租户编码',
  `version` int(11) DEFAULT 0 COMMENT '版本号',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 角色权限关联表
CREATE TABLE IF NOT EXISTS `auth_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `tenant_code` varchar(50) DEFAULT NULL COMMENT '租户编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_permission` (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表'; 
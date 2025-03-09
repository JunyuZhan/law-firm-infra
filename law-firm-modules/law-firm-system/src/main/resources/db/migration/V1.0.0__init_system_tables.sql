-- 系统配置表
CREATE TABLE IF NOT EXISTS sys_config (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  config_name varchar(100) NOT NULL COMMENT '配置名称',
  config_key varchar(100) NOT NULL COMMENT '配置键',
  config_value varchar(500) NOT NULL COMMENT '配置值',
  config_type varchar(20) NOT NULL DEFAULT 'SYSTEM' COMMENT '配置类型（SYSTEM系统配置/BUSINESS业务配置）',
  is_system tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否系统内置（0否/1是）',
  remark varchar(500) DEFAULT NULL COMMENT '备注说明',
  create_by varchar(64) NOT NULL COMMENT '创建人',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0否/1是）',
  PRIMARY KEY (id),
  UNIQUE KEY uk_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- 字典表
CREATE TABLE IF NOT EXISTS sys_dict (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  dict_name varchar(100) NOT NULL COMMENT '字典名称',
  dict_type varchar(100) NOT NULL COMMENT '字典类型',
  status tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态（0正常/1停用）',
  remark varchar(500) DEFAULT NULL COMMENT '备注说明',
  create_by varchar(64) NOT NULL COMMENT '创建人',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0否/1是）',
  PRIMARY KEY (id),
  UNIQUE KEY uk_dict_type (dict_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- 字典项表
CREATE TABLE IF NOT EXISTS sys_dict_item (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  dict_id bigint(20) NOT NULL COMMENT '字典ID',
  item_label varchar(100) NOT NULL COMMENT '字典标签',
  item_value varchar(100) NOT NULL COMMENT '字典键值',
  item_sort int(10) NOT NULL DEFAULT 0 COMMENT '排序号',
  status tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态（0正常/1停用）',
  remark varchar(500) DEFAULT NULL COMMENT '备注说明',
  create_by varchar(64) NOT NULL COMMENT '创建人',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0否/1是）',
  PRIMARY KEY (id),
  KEY idx_dict_id (dict_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典项表';

-- 系统升级表
CREATE TABLE IF NOT EXISTS sys_upgrade (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  version varchar(50) NOT NULL COMMENT '版本号',
  version_name varchar(100) NOT NULL COMMENT '版本名称',
  description text DEFAULT NULL COMMENT '版本描述',
  upgrade_time datetime NOT NULL COMMENT '升级时间',
  status varchar(20) NOT NULL DEFAULT 'PENDING' COMMENT '状态（PENDING待升级/UPGRADING升级中/SUCCESS成功/FAILED失败）',
  backup_path varchar(255) DEFAULT NULL COMMENT '备份路径',
  create_by varchar(64) NOT NULL COMMENT '创建人',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0否/1是）',
  PRIMARY KEY (id),
  UNIQUE KEY uk_version (version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统升级表';

-- 系统升级补丁表
CREATE TABLE IF NOT EXISTS sys_upgrade_patch (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  upgrade_id bigint(20) NOT NULL COMMENT '升级ID',
  patch_name varchar(100) NOT NULL COMMENT '补丁名称',
  patch_type varchar(20) NOT NULL DEFAULT 'SQL' COMMENT '补丁类型（SQL/JAR/CONFIG）',
  patch_path varchar(255) NOT NULL COMMENT '补丁路径',
  execute_order int(10) NOT NULL DEFAULT 0 COMMENT '执行顺序',
  status varchar(20) NOT NULL DEFAULT 'PENDING' COMMENT '状态（PENDING待执行/EXECUTING执行中/SUCCESS成功/FAILED失败）',
  error_message text DEFAULT NULL COMMENT '错误信息',
  create_by varchar(64) NOT NULL COMMENT '创建人',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0否/1是）',
  PRIMARY KEY (id),
  KEY idx_upgrade_id (upgrade_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统升级补丁表'; 
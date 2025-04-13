-- 添加员工事件日志表

-- 创建员工事件日志表
CREATE TABLE IF NOT EXISTS `personnel_employee_event_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `employee_id` bigint(20) NOT NULL COMMENT '员工ID',
  `event_type` tinyint(4) NOT NULL COMMENT '事件类型：1-入职，2-转正，3-调岗，4-调薪，5-奖惩，6-离职，7-退休，8-其他',
  `event_date` date NOT NULL COMMENT '事件日期',
  `title` varchar(100) NOT NULL COMMENT '事件标题',
  `content` text COMMENT '事件内容',
  `before_status` tinyint(4) DEFAULT NULL COMMENT '变更前状态',
  `after_status` tinyint(4) DEFAULT NULL COMMENT '变更后状态',
  `before_department_id` bigint(20) DEFAULT NULL COMMENT '变更前部门ID',
  `after_department_id` bigint(20) DEFAULT NULL COMMENT '变更后部门ID',
  `before_position_id` bigint(20) DEFAULT NULL COMMENT '变更前职位ID',
  `after_position_id` bigint(20) DEFAULT NULL COMMENT '变更后职位ID',
  `before_salary` decimal(12,2) DEFAULT NULL COMMENT '变更前薪资',
  `after_salary` decimal(12,2) DEFAULT NULL COMMENT '变更后薪资',
  `attachment_url` varchar(255) DEFAULT NULL COMMENT '附件URL',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  KEY `idx_event_log_employee_id` (`employee_id`),
  KEY `idx_event_log_event_type` (`event_type`),
  KEY `idx_event_log_event_date` (`event_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工事件日志表';

-- 创建员工教育经历表
CREATE TABLE IF NOT EXISTS `personnel_employee_education` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `employee_id` bigint(20) NOT NULL COMMENT '员工ID',
  `school_name` varchar(100) NOT NULL COMMENT '学校名称',
  `major` varchar(100) DEFAULT NULL COMMENT '专业',
  `degree` tinyint(4) DEFAULT NULL COMMENT '学位：1-学士，2-硕士，3-博士，0-其他',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `certificate_url` varchar(255) DEFAULT NULL COMMENT '证书URL',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  KEY `idx_education_employee_id` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工教育经历表';

-- 创建员工工作经历表
CREATE TABLE IF NOT EXISTS `personnel_employee_work_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `employee_id` bigint(20) NOT NULL COMMENT '员工ID',
  `company_name` varchar(100) NOT NULL COMMENT '公司名称',
  `position` varchar(100) DEFAULT NULL COMMENT '职位',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `responsibilities` varchar(500) DEFAULT NULL COMMENT '工作职责',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  KEY `idx_work_history_employee_id` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工工作经历表'; 
-- 人事模块数据库表初始化脚本
-- 添加模块标识前缀P，避免版本冲突

-- 注意：这是一个虚拟的迁移脚本，实际中应该根据personnel-model中的实体类定义创建表结构
-- 数据表实际上已经在其他模块中创建，这里只是为了演示

-- 创建人员表（如果不存在）
CREATE TABLE IF NOT EXISTS `Pperson` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(32) NOT NULL COMMENT '人员编号',
  `name` varchar(64) NOT NULL COMMENT '姓名',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别:0-女,1-男',
  `birth_date` date DEFAULT NULL COMMENT '出生日期',
  `id_card_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(64) DEFAULT NULL COMMENT '电子邮箱',
  `address` varchar(255) DEFAULT NULL COMMENT '住址',
  `photo_url` varchar(255) DEFAULT NULL COMMENT '照片URL',
  `user_id` bigint(20) DEFAULT NULL COMMENT '关联用户ID',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态:0-禁用,1-启用',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除:0-未删除,1-已删除',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`, `tenant_id`),
  UNIQUE KEY `uk_id_card` (`id_card_no`, `tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员信息表';

-- 创建员工表（如果不存在）
CREATE TABLE IF NOT EXISTS `Pemployee` (
  `id` bigint(20) NOT NULL COMMENT '主键ID,关联person表ID',
  `employee_no` varchar(32) NOT NULL COMMENT '工号',
  `join_date` date DEFAULT NULL COMMENT '入职日期',
  `employee_type` tinyint(2) NOT NULL COMMENT '员工类型:1-正式,2-试用,3-实习,4-临时,5-顾问',
  `employee_status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '员工状态:1-在职,2-离职,3-退休,4-停职',
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `position_id` bigint(20) DEFAULT NULL COMMENT '职位ID',
  `supervisor_id` bigint(20) DEFAULT NULL COMMENT '上级主管ID',
  `employment_type` tinyint(2) DEFAULT NULL COMMENT '雇佣类型:1-全职,2-兼职',
  `position_level` int(11) DEFAULT NULL COMMENT '职级',
  `position_rank` int(11) DEFAULT NULL COMMENT '职等',
  `contract_start_date` date DEFAULT NULL COMMENT '合同开始日期',
  `contract_end_date` date DEFAULT NULL COMMENT '合同结束日期',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_employee_no` (`employee_no`, `tenant_id`),
  CONSTRAINT `fk_employee_person` FOREIGN KEY (`id`) REFERENCES `Pperson` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工信息表';

-- 创建律师表（如果不存在）
CREATE TABLE IF NOT EXISTS `Plawyer` (
  `id` bigint(20) NOT NULL COMMENT '主键ID,关联employee表ID',
  `license_number` varchar(32) DEFAULT NULL COMMENT '律师执业证号',
  `license_issue_date` date DEFAULT NULL COMMENT '证书发放日期',
  `license_expiry_date` date DEFAULT NULL COMMENT '证书到期日期',
  `practice_area` varchar(255) DEFAULT NULL COMMENT '执业领域',
  `law_firm_id` bigint(20) DEFAULT NULL COMMENT '所属律所ID',
  `law_firm_join_date` date DEFAULT NULL COMMENT '加入律所日期',
  `lawyer_type` tinyint(2) DEFAULT NULL COMMENT '律师类型:1-合伙人律师,2-专职律师,3-兼职律师',
  `bar_association` varchar(100) DEFAULT NULL COMMENT '所属律师协会',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_license_number` (`license_number`, `tenant_id`),
  CONSTRAINT `fk_lawyer_employee` FOREIGN KEY (`id`) REFERENCES `Pemployee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='律师信息表';

-- 创建行政人员表（如果不存在）
CREATE TABLE IF NOT EXISTS `Pstaff` (
  `id` bigint(20) NOT NULL COMMENT '主键ID,关联employee表ID',
  `staff_function` varchar(100) DEFAULT NULL COMMENT '行政职能',
  `specialist_area` varchar(255) DEFAULT NULL COMMENT '专业领域',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_staff_employee` FOREIGN KEY (`id`) REFERENCES `Pemployee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='行政人员信息表'; 
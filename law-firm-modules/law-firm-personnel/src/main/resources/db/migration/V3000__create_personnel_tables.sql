-- 创建人事模块基础表

-- 创建人员信息表（人员基本信息）
CREATE TABLE IF NOT EXISTS `personnel_person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别：1-男，2-女，0-未知',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `id_type` tinyint(4) DEFAULT NULL COMMENT '证件类型：1-身份证，2-护照，3-军官证，4-港澳通行证，5-台湾通行证，6-外国人居留许可，0-其他',
  `id_number` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `address` varchar(200) DEFAULT NULL COMMENT '居住地址',
  `education` tinyint(4) DEFAULT NULL COMMENT '学历：1-小学，2-初中，3-高中，4-中专，5-大专，6-本科，7-硕士，8-博士，0-其他',
  `political_status` tinyint(4) DEFAULT NULL COMMENT '政治面貌：1-党员，2-预备党员，3-团员，4-民主党派，5-群众，0-其他',
  `marital_status` tinyint(4) DEFAULT NULL COMMENT '婚姻状况：1-未婚，2-已婚，3-离婚，4-丧偶，0-未知',
  `emergency_contact` varchar(50) DEFAULT NULL COMMENT '紧急联系人',
  `emergency_phone` varchar(20) DEFAULT NULL COMMENT '紧急联系人电话',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `user_id` bigint(20) DEFAULT NULL COMMENT '关联用户ID',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_person_id_number` (`id_number`),
  KEY `idx_person_name` (`name`),
  KEY `idx_person_phone` (`phone`),
  KEY `idx_person_email` (`email`),
  KEY `idx_person_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员信息表';

-- 创建员工表（员工相关信息）
CREATE TABLE IF NOT EXISTS `personnel_employee` (
  `id` bigint(20) NOT NULL COMMENT '主键ID（关联personnel_person表的id）',
  `employee_no` varchar(50) NOT NULL COMMENT '员工编号',
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `position_id` bigint(20) DEFAULT NULL COMMENT '职位ID',
  `employment_date` date DEFAULT NULL COMMENT '入职日期',
  `resignation_date` date DEFAULT NULL COMMENT '离职日期',
  `employment_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '聘用类型：1-全职，2-兼职，3-实习，4-劳务，0-其他',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1-试用期，2-正式，3-离职，4-退休，5-实习，6-停职，0-其他',
  `salary` decimal(12,2) DEFAULT NULL COMMENT '基本工资',
  `probation_months` int(11) DEFAULT '3' COMMENT '试用期月数',
  `probation_end_date` date DEFAULT NULL COMMENT '试用期结束日期',
  `resume_url` varchar(255) DEFAULT NULL COMMENT '简历URL',
  `contract_url` varchar(255) DEFAULT NULL COMMENT '合同URL',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_employee_no` (`employee_no`),
  KEY `idx_employee_department_id` (`department_id`),
  KEY `idx_employee_position_id` (`position_id`),
  KEY `idx_employee_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工表';

-- 创建律师表（律师特有信息）
CREATE TABLE IF NOT EXISTS `personnel_lawyer` (
  `id` bigint(20) NOT NULL COMMENT '主键ID（关联personnel_employee表的id）',
  `lawyer_no` varchar(50) NOT NULL COMMENT '律师执业号',
  `license_date` date DEFAULT NULL COMMENT '执业证日期',
  `license_expiry_date` date DEFAULT NULL COMMENT '执业证到期日期',
  `license_issuing_authority` varchar(100) DEFAULT NULL COMMENT '执业证发证机关',
  `license_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '执业证状态：1-有效，2-过期，3-吊销，4-注销，0-其他',
  `lawyer_level` tinyint(4) DEFAULT NULL COMMENT '律师级别：1-实习律师，2-初级律师，3-中级律师，4-高级律师，5-合伙人',
  `specialty_area` varchar(200) DEFAULT NULL COMMENT '专长领域,多个用逗号分隔',
  `license_url` varchar(255) DEFAULT NULL COMMENT '执业证URL',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lawyer_no` (`lawyer_no`),
  KEY `idx_lawyer_level` (`lawyer_level`),
  KEY `idx_lawyer_license_status` (`license_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='律师表';

-- 创建行政人员表（行政人员特有信息）
CREATE TABLE IF NOT EXISTS `personnel_staff` (
  `id` bigint(20) NOT NULL COMMENT '主键ID（关联personnel_employee表的id）',
  `staff_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '行政人员类型：1-行政，2-财务，3-人事，4-前台，5-IT，0-其他',
  `responsibilities` varchar(500) DEFAULT NULL COMMENT '工作职责',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  KEY `idx_staff_type` (`staff_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='行政人员表'; 
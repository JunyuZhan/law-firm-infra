-- 员工信息表
CREATE TABLE IF NOT EXISTS `personnel_employee` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `employee_no` varchar(32) NOT NULL COMMENT '工号',
    `name` varchar(64) NOT NULL COMMENT '姓名',
    `gender` tinyint NOT NULL COMMENT '性别:0-女,1-男',
    `birthday` date COMMENT '出生日期',
    `id_card` varchar(18) NOT NULL COMMENT '身份证号',
    `mobile` varchar(11) NOT NULL COMMENT '手机号',
    `email` varchar(64) COMMENT '邮箱',
    `branch_id` bigint NOT NULL COMMENT '分支机构ID',
    `department_id` bigint NOT NULL COMMENT '部门ID',
    `position_id` bigint NOT NULL COMMENT '职位ID',
    `entry_date` date NOT NULL COMMENT '入职日期',
    `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态:0-离职,1-在职',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_employee_no` (`employee_no`),
    UNIQUE KEY `uk_id_card` (`id_card`),
    UNIQUE KEY `uk_mobile` (`mobile`),
    KEY `idx_branch_id` (`branch_id`),
    KEY `idx_department_id` (`department_id`),
    KEY `idx_position_id` (`position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工信息表';

-- 员工档案表
CREATE TABLE IF NOT EXISTS `personnel_employee_archive` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `employee_id` bigint NOT NULL COMMENT '员工ID',
    `education` varchar(32) NOT NULL COMMENT '学历',
    `school` varchar(128) NOT NULL COMMENT '毕业院校',
    `major` varchar(64) NOT NULL COMMENT '专业',
    `graduation_date` date NOT NULL COMMENT '毕业日期',
    `work_experience` text COMMENT '工作经历',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_employee_id` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工档案表';

-- 员工证书表
CREATE TABLE IF NOT EXISTS `personnel_employee_certificate` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `employee_id` bigint NOT NULL COMMENT '员工ID',
    `certificate_type` varchar(32) NOT NULL COMMENT '证书类型',
    `certificate_no` varchar(64) NOT NULL COMMENT '证书编号',
    `certificate_name` varchar(128) NOT NULL COMMENT '证书名称',
    `issuing_authority` varchar(128) NOT NULL COMMENT '发证机构',
    `issuing_date` date NOT NULL COMMENT '发证日期',
    `expiry_date` date COMMENT '到期日期',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_employee_id` (`employee_id`),
    UNIQUE KEY `uk_certificate_no` (`certificate_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工证书表'; 
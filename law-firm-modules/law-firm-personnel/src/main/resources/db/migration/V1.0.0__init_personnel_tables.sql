-- 员工表
CREATE TABLE per_employee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    law_firm_id BIGINT NOT NULL COMMENT '所属律所ID',
    branch_id BIGINT COMMENT '所属分支机构ID',
    department_id BIGINT COMMENT '所属部门ID',
    position_id BIGINT COMMENT '职位ID',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    employee_code VARCHAR(20) COMMENT '员工编号',
    employee_type VARCHAR(20) NOT NULL COMMENT '员工类型',
    id_number VARCHAR(18) COMMENT '身份证号',
    birth_date DATE COMMENT '出生日期',
    gender VARCHAR(10) COMMENT '性别',
    address VARCHAR(200) COMMENT '地址',
    mobile VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    entry_date DATE COMMENT '入职日期',
    leave_date DATE COMMENT '离职日期',
    employment_status VARCHAR(20) COMMENT '在职状态',
    enabled BOOLEAN DEFAULT TRUE COMMENT '是否启用',
    remark VARCHAR(500) COMMENT '备注',
    created_by BIGINT COMMENT '创建人',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_by BIGINT COMMENT '更新人',
    updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    version INT DEFAULT 0 COMMENT '版本号',
    UNIQUE KEY uk_employee_code (employee_code),
    UNIQUE KEY uk_id_number (id_number),
    UNIQUE KEY uk_mobile (mobile)
) COMMENT '员工表';

-- 律师表
CREATE TABLE lawyer (
    employee_id BIGINT PRIMARY KEY COMMENT '员工ID',
    title VARCHAR(20) NOT NULL COMMENT '律师职称',
    license_number VARCHAR(50) COMMENT '执业证号',
    license_date DATE COMMENT '执业证取得日期',
    practicing_institution VARCHAR(100) COMMENT '执业机构',
    specialties VARCHAR(500) COMMENT '专业领域',
    experience VARCHAR(500) COMMENT '执业经历',
    is_partner BOOLEAN DEFAULT FALSE COMMENT '是否合伙人',
    practice_status VARCHAR(20) COMMENT '执业状态',
    partner_date DATE COMMENT '成为合伙人日期',
    qualifications VARCHAR(500) COMMENT '资质认证',
    FOREIGN KEY (employee_id) REFERENCES per_employee(id)
) COMMENT '律师表';

-- 行政人员表
CREATE TABLE staff (
    employee_id BIGINT PRIMARY KEY COMMENT '员工ID',
    job_duties VARCHAR(100) COMMENT '工作职责',
    work_scope VARCHAR(100) COMMENT '工作范围',
    education VARCHAR(50) COMMENT '教育背景',
    major VARCHAR(50) COMMENT '专业',
    work_experience VARCHAR(500) COMMENT '工作经历',
    skills_certificates VARCHAR(500) COMMENT '技能证书',
    contract_start_date DATE COMMENT '合同开始日期',
    contract_end_date DATE COMMENT '合同结束日期',
    contract_type VARCHAR(20) COMMENT '合同类型',
    performance_record VARCHAR(500) COMMENT '绩效记录',
    FOREIGN KEY (employee_id) REFERENCES per_employee(id)
) COMMENT '行政人员表';

-- 员工档案表
CREATE TABLE personnel_employee_archive (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    employee_id BIGINT NOT NULL COMMENT '员工ID',
    archive_no VARCHAR(32) NOT NULL COMMENT '档案编号',
    archive_type VARCHAR(32) NOT NULL COMMENT '档案类型（EDUCATION-教育经历, WORK-工作经历, CERTIFICATE-证书, CONTRACT-合同, OTHER-其他）',
    title VARCHAR(128) NOT NULL COMMENT '标题',
    content TEXT COMMENT '内容',
    file_path VARCHAR(256) COMMENT '文件路径',
    file_name VARCHAR(128) COMMENT '文件名称',
    file_size BIGINT COMMENT '文件大小(字节)',
    file_type VARCHAR(32) COMMENT '文件类型',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态（1-有效 2-无效）',
    valid_start_date DATE COMMENT '有效期开始日期',
    valid_end_date DATE COMMENT '有效期结束日期',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT COMMENT '创建人',
    update_by BIGINT COMMENT '更新人',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除',
    UNIQUE KEY uk_archive_no (archive_no),
    KEY idx_employee_id (employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工档案表';

-- 员工证照表
CREATE TABLE personnel_employee_certificate (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    employee_id BIGINT NOT NULL COMMENT '员工ID',
    cert_type VARCHAR(32) NOT NULL COMMENT '证照类型（ID_CARD-身份证, DIPLOMA-学历证书, QUALIFICATION-资格证书, OTHER-其他）',
    cert_no VARCHAR(64) NOT NULL COMMENT '证照编号',
    cert_name VARCHAR(128) NOT NULL COMMENT '证照名称',
    issuing_authority VARCHAR(128) COMMENT '发证机构',
    issuing_date DATE COMMENT '发证日期',
    valid_start_date DATE COMMENT '有效期开始日期',
    valid_end_date DATE COMMENT '有效期结束日期',
    file_path VARCHAR(256) COMMENT '文件路径',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态（1-有效 2-无效）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT COMMENT '创建人',
    update_by BIGINT COMMENT '更新人',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除',
    UNIQUE KEY uk_employee_cert (employee_id, cert_type, cert_no),
    KEY idx_employee_id (employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工证照表'; 
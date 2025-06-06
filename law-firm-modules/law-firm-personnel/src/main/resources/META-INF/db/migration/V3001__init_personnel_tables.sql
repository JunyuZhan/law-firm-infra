-- 版本: V3001
-- 模块: 人员管理模块 (V3000-V3999)
-- 创建时间: 2023-06-01
-- 说明: 人员管理功能的完整表结构定义
-- 包括：组织架构、人员信息、员工管理、律师管理、教育经历、工作经历、合同管理等

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 组织架构表 =======================

-- org_department表（部门表）
DROP TABLE IF EXISTS org_department;
CREATE TABLE org_department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '部门ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父部门ID',
    code VARCHAR(50) NOT NULL COMMENT '部门编码',
    name VARCHAR(100) NOT NULL COMMENT '部门名称',
    full_name VARCHAR(255) COMMENT '部门全称',
    level INT DEFAULT 1 COMMENT '层级深度',
    path VARCHAR(500) COMMENT '部门路径(用/分隔)',
    manager_id BIGINT COMMENT '部门负责人ID',
    phone VARCHAR(20) COMMENT '部门电话',
    email VARCHAR(100) COMMENT '部门邮箱',
    address VARCHAR(255) COMMENT '办公地址',
    function_desc TEXT COMMENT '部门职能描述',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    sort INT DEFAULT 0 COMMENT '排序号',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_code (tenant_id, code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_parent_id (parent_id),
    INDEX idx_manager_id (manager_id),
    INDEX idx_status (status),
    INDEX idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门表';

-- org_position表（职位表）
DROP TABLE IF EXISTS org_position;
CREATE TABLE org_position (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '职位ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    department_id BIGINT NOT NULL COMMENT '所属部门ID',
    code VARCHAR(50) NOT NULL COMMENT '职位编码',
    name VARCHAR(100) NOT NULL COMMENT '职位名称',
    level INT COMMENT '职位等级(1-10)',
    `rank` VARCHAR(20) COMMENT '职位层级(junior/middle/senior/expert)',
    category VARCHAR(50) COMMENT '职位类别(律师/行政/管理/财务等)',
    job_description TEXT COMMENT '职位描述',
    requirements TEXT COMMENT '任职要求',
    salary_range VARCHAR(100) COMMENT '薪资范围',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    sort INT DEFAULT 0 COMMENT '排序号',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_code (tenant_id, code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_department_id (department_id),
    INDEX idx_category (category),
    INDEX idx_level (level),
    INDEX idx_status (status),
    
    CONSTRAINT fk_position_department FOREIGN KEY (department_id) REFERENCES org_department(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='职位表';

-- org_team表（团队表）
DROP TABLE IF EXISTS org_team;
CREATE TABLE org_team (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '团队ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    department_id BIGINT COMMENT '所属部门ID',
    code VARCHAR(50) NOT NULL COMMENT '团队编码',
    name VARCHAR(100) NOT NULL COMMENT '团队名称',
    type TINYINT DEFAULT 1 COMMENT '团队类型(1-常设团队,2-项目团队,3-临时团队)',
    leader_id BIGINT COMMENT '团队负责人ID',
    member_count INT DEFAULT 0 COMMENT '团队人数',
    description TEXT COMMENT '团队描述',
    objectives TEXT COMMENT '团队目标',
    start_date DATE COMMENT '成立日期',
    end_date DATE COMMENT '解散日期',
    status TINYINT DEFAULT 1 COMMENT '状态(0-解散,1-活跃,2-暂停)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_code (tenant_id, code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_department_id (department_id),
    INDEX idx_type (type),
    INDEX idx_leader_id (leader_id),
    INDEX idx_status (status),
    
    CONSTRAINT fk_team_department FOREIGN KEY (department_id) REFERENCES org_department(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='团队表';

-- org_project_group表（项目组表）
DROP TABLE IF EXISTS org_project_group;
CREATE TABLE org_project_group (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '项目组ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    team_id BIGINT COMMENT '所属团队ID',
    code VARCHAR(50) NOT NULL COMMENT '项目组编码',
    name VARCHAR(100) NOT NULL COMMENT '项目组名称',
    project_id BIGINT COMMENT '关联项目ID',
    case_id BIGINT COMMENT '关联案件ID',
    leader_id BIGINT COMMENT '负责人ID',
    member_count INT DEFAULT 0 COMMENT '成员数量',
    start_date DATE COMMENT '开始日期',
    end_date DATE COMMENT '结束日期',
    status TINYINT DEFAULT 1 COMMENT '状态(0-筹备,1-进行,2-结束)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_code (tenant_id, code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_team_id (team_id),
    INDEX idx_project_id (project_id),
    INDEX idx_case_id (case_id),
    INDEX idx_leader_id (leader_id),
    INDEX idx_status (status),
    
    CONSTRAINT fk_project_group_team FOREIGN KEY (team_id) REFERENCES org_team(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='项目组表';

-- ======================= Personnel模块表结构 =======================

-- per_person表（人员基本信息表）
DROP TABLE IF EXISTS per_person;
CREATE TABLE per_person (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '人员ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    code VARCHAR(50) NOT NULL COMMENT '人员编号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    english_name VARCHAR(100) COMMENT '英文名',
    gender TINYINT DEFAULT 0 COMMENT '性别(0-未知,1-男,2-女)',
    birth_date DATE COMMENT '出生日期',
    id_type TINYINT DEFAULT 1 COMMENT '证件类型(1-身份证,2-护照,3-其他)',
    id_number VARCHAR(50) COMMENT '证件号码',
    nationality VARCHAR(50) DEFAULT '中国' COMMENT '国籍',
    ethnicity VARCHAR(20) COMMENT '民族',
    marital_status TINYINT COMMENT '婚姻状况(1-未婚,2-已婚,3-离异,4-丧偶)',
    political_status VARCHAR(50) COMMENT '政治面貌',
    hometown VARCHAR(100) COMMENT '籍贯',
    avatar_url VARCHAR(255) COMMENT '头像URL',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_code (tenant_id, code),
    UNIQUE KEY uk_tenant_id_number (tenant_id, id_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_name (name),
    INDEX idx_gender (gender),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='人员基本信息表';

-- per_employee表（员工信息表）
DROP TABLE IF EXISTS per_employee;
CREATE TABLE per_employee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '员工ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    person_id BIGINT NOT NULL COMMENT '关联人员ID',
    work_number VARCHAR(50) NOT NULL COMMENT '工号',
    user_id BIGINT COMMENT '关联用户ID',
    employee_status TINYINT DEFAULT 1 COMMENT '员工状态(1-在职,2-离职,3-试用期,4-休假,5-停职)',
    employee_type TINYINT DEFAULT 1 COMMENT '员工类型(1-律师,2-行政人员,3-实习生,4-顾问,99-其他)',
    department_id BIGINT COMMENT '所属部门ID',
    position_id BIGINT COMMENT '职位ID',
    team_id BIGINT COMMENT '所属团队ID',
    entry_date DATE COMMENT '入职日期',
    regular_date DATE COMMENT '转正日期',
    resign_date DATE COMMENT '离职日期',
    work_email VARCHAR(100) COMMENT '工作邮箱',
    work_phone VARCHAR(20) COMMENT '工作电话',
    office_location VARCHAR(100) COMMENT '办公地点',
    supervisor_id BIGINT COMMENT '直属上级ID',
    work_years INT DEFAULT 0 COMMENT '工作年限',
    education TINYINT COMMENT '学历(1-专科,2-本科,3-硕士,4-博士,5-其他)',
    graduate_school VARCHAR(100) COMMENT '毕业院校',
    major VARCHAR(100) COMMENT '专业',
    graduate_year INT COMMENT '毕业年份',
    contract_status TINYINT DEFAULT 0 COMMENT '合同状态(0-未签,1-已签,2-过期,3-终止)',
    current_contract_id BIGINT COMMENT '当前合同ID',
    is_partner TINYINT DEFAULT 0 COMMENT '是否合伙人(0-否,1-是)',
    partner_date DATE COMMENT '成为合伙人日期',
    equity_ratio DECIMAL(10,4) COMMENT '股权比例',
    mentor_id BIGINT COMMENT '导师ID',
    function_type TINYINT COMMENT '职能类型',
    function_desc VARCHAR(200) COMMENT '职能描述',
    job_duties TEXT COMMENT '工作职责',
    service_scope VARCHAR(500) COMMENT '服务范围',
    skill_certificates VARCHAR(500) COMMENT '技能证书',
    resign_reason VARCHAR(500) COMMENT '离职原因',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    sort INT DEFAULT 0 COMMENT '排序号',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_work_number (tenant_id, work_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_person_id (person_id),
    INDEX idx_user_id (user_id),
    INDEX idx_employee_status (employee_status),
    INDEX idx_employee_type (employee_type),
    INDEX idx_department_id (department_id),
    INDEX idx_position_id (position_id),
    INDEX idx_team_id (team_id),
    INDEX idx_supervisor_id (supervisor_id),
    INDEX idx_entry_date (entry_date),
    INDEX idx_status (status),
    
    CONSTRAINT fk_employee_person FOREIGN KEY (person_id) REFERENCES per_person(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工信息表';

-- per_contact表（联系方式表）
DROP TABLE IF EXISTS per_contact;
CREATE TABLE per_contact (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '联系方式ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    person_id BIGINT NOT NULL COMMENT '人员ID',
    contact_type TINYINT NOT NULL COMMENT '联系方式类型(1-手机,2-固话,3-邮箱,4-微信,5-QQ)',
    contact_value VARCHAR(100) NOT NULL COMMENT '联系方式值',
    is_primary TINYINT DEFAULT 0 COMMENT '是否主要联系方式(0-否,1-是)',
    is_work TINYINT DEFAULT 0 COMMENT '是否工作联系方式(0-否,1-是)',
    description VARCHAR(100) COMMENT '联系方式描述',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_person_id (person_id),
    INDEX idx_contact_type (contact_type),
    INDEX idx_contact_value (contact_value),
    INDEX idx_is_primary (is_primary),
    
    CONSTRAINT fk_contact_person FOREIGN KEY (person_id) REFERENCES per_person(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='联系方式表';

-- per_address表（地址信息表）
DROP TABLE IF EXISTS per_address;
CREATE TABLE per_address (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '地址ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    person_id BIGINT NOT NULL COMMENT '人员ID',
    address_type TINYINT NOT NULL COMMENT '地址类型(1-现居地址,2-户籍地址,3-紧急联系人地址)',
    province VARCHAR(50) COMMENT '省份',
    city VARCHAR(50) COMMENT '城市',
    district VARCHAR(50) COMMENT '区县',
    street VARCHAR(100) COMMENT '街道',
    detail_address VARCHAR(255) COMMENT '详细地址',
    postal_code VARCHAR(20) COMMENT '邮政编码',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认地址(0-否,1-是)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_person_id (person_id),
    INDEX idx_address_type (address_type),
    INDEX idx_province_city (province, city),
    
    CONSTRAINT fk_address_person FOREIGN KEY (person_id) REFERENCES per_person(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='地址信息表';

-- ======================= 专业信息表 =======================

-- per_lawyer表（律师专业信息表）
DROP TABLE IF EXISTS per_lawyer;
CREATE TABLE per_lawyer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '律师信息ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    employee_id BIGINT NOT NULL COMMENT '员工ID',
    license_number VARCHAR(50) UNIQUE COMMENT '律师执业证号',
    license_issue_date DATE COMMENT '执业证颁发日期',
    license_expire_date DATE COMMENT '执业证过期日期',
    issuing_authority VARCHAR(100) COMMENT '颁发机构',
    practice_years INT COMMENT '执业年限',
    lawyer_level TINYINT COMMENT '律师等级(1-初级,2-中级,3-高级,4-资深)',
    practice_areas JSON COMMENT '执业领域(JSON格式)',
    expertise TEXT COMMENT '专长领域',
    language_skills VARCHAR(255) COMMENT '语言技能',
    professional_certificates JSON COMMENT '专业证书(JSON格式)',
    bar_association VARCHAR(100) COMMENT '律师协会',
    is_partner TINYINT DEFAULT 0 COMMENT '是否合伙人(0-否,1-是)',
    partner_date DATE COMMENT '成为合伙人日期',
    equity_ratio DECIMAL(10,4) COMMENT '股权比例',
    mentor_id BIGINT COMMENT '导师ID',
    profile TEXT COMMENT '律师简介',
    achievements TEXT COMMENT '律师成就',
    case_win_rate DECIMAL(5,2) COMMENT '胜诉率',
    case_count INT DEFAULT 0 COMMENT '案件数量',
    status TINYINT DEFAULT 1 COMMENT '状态(0-停职,1-正常执业,2-暂停执业)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_employee_id (employee_id),
    INDEX idx_license_number (license_number),
    INDEX idx_lawyer_level (lawyer_level),
    INDEX idx_is_partner (is_partner),
    INDEX idx_status (status),
    
    CONSTRAINT fk_lawyer_employee FOREIGN KEY (employee_id) REFERENCES per_employee(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='律师专业信息表';

-- per_education表（教育经历表）
DROP TABLE IF EXISTS per_education_experience;
CREATE TABLE per_education_experience (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '教育经历ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    person_id BIGINT NOT NULL COMMENT '人员ID',
    education_level TINYINT COMMENT '学历层次(1-专科,2-本科,3-硕士,4-博士,5-博士后)',
    degree_type TINYINT COMMENT '学位类型(1-学士,2-硕士,3-博士)',
    school_name VARCHAR(100) COMMENT '学校名称',
    school_type TINYINT COMMENT '学校类型(1-985,2-211,3-一本,4-二本,5-专科,6-海外)',
    major VARCHAR(100) COMMENT '专业',
    specialty VARCHAR(100) COMMENT '专业方向',
    admission_date DATE COMMENT '入学时间',
    graduation_date DATE COMMENT '毕业时间',
    is_full_time TINYINT DEFAULT 1 COMMENT '是否全日制(0-否,1-是)',
    graduation_status TINYINT DEFAULT 1 COMMENT '毕业状态(1-正常毕业,2-肄业,3-结业)',
    gpa DECIMAL(3,2) COMMENT '学分绩点',
    ranking INT COMMENT '专业排名',
    thesis_title VARCHAR(255) COMMENT '论文题目',
    advisor VARCHAR(50) COMMENT '导师姓名',
    honors JSON COMMENT '荣誉奖项(JSON格式)',
    is_highest TINYINT DEFAULT 0 COMMENT '是否最高学历(0-否,1-是)',
    certificate_number VARCHAR(100) COMMENT '学历证书编号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_person_id (person_id),
    INDEX idx_education_level (education_level),
    INDEX idx_school_name (school_name),
    INDEX idx_graduation_date (graduation_date),
    INDEX idx_is_highest (is_highest),
    
    CONSTRAINT fk_education_person FOREIGN KEY (person_id) REFERENCES per_person(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='教育经历表';

-- per_work_experience表（工作经历表）
DROP TABLE IF EXISTS per_work_experience;
CREATE TABLE per_work_experience (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '工作经历ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    person_id BIGINT NOT NULL COMMENT '人员ID',
    company_name VARCHAR(100) NOT NULL COMMENT '公司名称',
    company_type TINYINT COMMENT '公司类型(1-律所,2-企业,3-政府,4-事业单位,5-其他)',
    industry VARCHAR(50) COMMENT '行业',
    position_name VARCHAR(100) COMMENT '职位名称',
    position_level TINYINT COMMENT '职位级别(1-基层,2-中层,3-高层)',
    department VARCHAR(100) COMMENT '部门',
    start_date DATE COMMENT '开始时间',
    end_date DATE COMMENT '结束时间',
    is_current TINYINT DEFAULT 0 COMMENT '是否当前工作(0-否,1-是)',
    work_description TEXT COMMENT '工作描述',
    achievements TEXT COMMENT '工作成就',
    leaving_reason VARCHAR(255) COMMENT '离职原因',
    supervisor_name VARCHAR(50) COMMENT '直属上级',
    supervisor_contact VARCHAR(100) COMMENT '上级联系方式',
    salary_range VARCHAR(50) COMMENT '薪资范围',
    reference_available TINYINT DEFAULT 1 COMMENT '是否可作为推荐人(0-否,1-是)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_person_id (person_id),
    INDEX idx_company_name (company_name),
    INDEX idx_position_name (position_name),
    INDEX idx_start_date (start_date),
    INDEX idx_is_current (is_current),
    
    CONSTRAINT fk_work_experience_person FOREIGN KEY (person_id) REFERENCES per_person(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作经历表';

-- per_contract表（人事合同表）
DROP TABLE IF EXISTS per_contract;
CREATE TABLE per_contract (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '合同ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    employee_id BIGINT NOT NULL COMMENT '员工ID',
    contract_number VARCHAR(50) NOT NULL COMMENT '合同编号',
    contract_type TINYINT DEFAULT 1 COMMENT '合同类型(1-劳动合同,2-实习协议,3-顾问协议,4-合伙协议)',
    contract_name VARCHAR(100) COMMENT '合同名称',
    start_date DATE COMMENT '合同开始日期',
    end_date DATE COMMENT '合同结束日期',
    probation_months INT DEFAULT 0 COMMENT '试用期(月)',
    basic_salary DECIMAL(12,2) COMMENT '基本工资',
    performance_salary DECIMAL(12,2) COMMENT '绩效工资',
    allowance DECIMAL(12,2) COMMENT '津贴补助',
    benefits JSON COMMENT '福利待遇(JSON格式)',
    work_hours VARCHAR(50) COMMENT '工作时间',
    work_location VARCHAR(100) COMMENT '工作地点',
    contract_status TINYINT DEFAULT 1 COMMENT '合同状态(1-生效,2-到期,3-解除,4-中止)',
    signing_date DATE COMMENT '签署日期',
    file_path VARCHAR(255) COMMENT '合同文件路径',
    is_current TINYINT DEFAULT 1 COMMENT '是否当前合同(0-否,1-是)',
    termination_reason VARCHAR(255) COMMENT '终止原因',
    termination_date DATE COMMENT '终止日期',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_contract_number (tenant_id, contract_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_employee_id (employee_id),
    INDEX idx_contract_type (contract_type),
    INDEX idx_contract_status (contract_status),
    INDEX idx_start_date (start_date),
    INDEX idx_end_date (end_date),
    INDEX idx_is_current (is_current),
    
    CONSTRAINT fk_contract_employee FOREIGN KEY (employee_id) REFERENCES per_employee(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='人事合同表';

-- per_employee_organization表（员工组织关系表）
DROP TABLE IF EXISTS per_employee_organization;
CREATE TABLE per_employee_organization (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关系ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    employee_id BIGINT NOT NULL COMMENT '员工ID',
    organization_type TINYINT NOT NULL COMMENT '组织类型(1-部门,2-团队,3-项目组)',
    organization_id BIGINT NOT NULL COMMENT '组织ID',
    role_type TINYINT DEFAULT 1 COMMENT '角色类型(1-成员,2-负责人,3-副负责人)',
    start_date DATE COMMENT '开始时间',
    end_date DATE COMMENT '结束时间',
    is_current TINYINT DEFAULT 1 COMMENT '是否当前关系(0-否,1-是)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_employee_id (employee_id),
    INDEX idx_organization_type (organization_type),
    INDEX idx_organization_id (organization_id),
    INDEX idx_role_type (role_type),
    INDEX idx_is_current (is_current),
    
    CONSTRAINT fk_emp_org_employee FOREIGN KEY (employee_id) REFERENCES per_employee(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工组织关系表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1;

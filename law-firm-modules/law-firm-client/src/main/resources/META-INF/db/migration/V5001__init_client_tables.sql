-- 客户管理模块表结构
-- 版本: V5001
-- 模块: 客户管理模块 (V5000-V5999)
-- 创建时间: 2023-06-01
-- 说明: 客户管理功能的完整表结构定义
-- 包括：客户基础信息、联系人管理、地址管理、分类管理、标签管理、关系管理、跟进管理

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 客户基础信息表 =======================

-- client_info表（客户基本信息表）
DROP TABLE IF EXISTS client_info;
CREATE TABLE client_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '客户ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    client_number VARCHAR(50) NOT NULL COMMENT '客户编号',
    client_name VARCHAR(100) NOT NULL COMMENT '客户名称',
    client_type TINYINT DEFAULT 1 COMMENT '客户类型(1-个人客户,2-企业客户,3-政府机构,4-社会组织)',
    client_level TINYINT DEFAULT 1 COMMENT '客户等级(1-普通客户,2-重要客户,3-VIP客户)',
    client_source TINYINT DEFAULT 1 COMMENT '客户来源(1-主动咨询,2-同行推荐,3-客户推荐,4-广告宣传,5-老客户)',
    industry VARCHAR(50) COMMENT '所属行业',
    scale VARCHAR(50) COMMENT '客户规模',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '电子邮箱',
    manager_id BIGINT COMMENT '负责人ID',
    manager_name VARCHAR(50) COMMENT '负责人姓名',
    id_type TINYINT COMMENT '证件类型(1-身份证,2-护照,3-营业执照,4-其他)',
    id_number VARCHAR(50) COMMENT '证件号码',
    credit_level VARCHAR(2) COMMENT '信用等级(A/B/C/D)',
    legal_representative VARCHAR(50) COMMENT '法定代表人',
    unified_social_credit_code VARCHAR(50) COMMENT '统一社会信用代码',
    case_count INT DEFAULT 0 COMMENT '案件总数',
    active_case_count INT DEFAULT 0 COMMENT '活跃案件数',
    contract_count INT DEFAULT 0 COMMENT '合同总数',
    total_amount DECIMAL(15,2) DEFAULT 0 COMMENT '总金额',
    last_contact_time DATETIME COMMENT '最后联系时间',
    next_follow_time DATETIME COMMENT '下次跟进时间',
    customer_service_id BIGINT COMMENT '客服人员ID',
    customer_service_name VARCHAR(50) COMMENT '客服人员姓名',
    tags JSON COMMENT '客户标签(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_client_number (tenant_id, client_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_client_name (client_name),
    INDEX idx_client_type (client_type),
    INDEX idx_client_level (client_level),
    INDEX idx_client_source (client_source),
    INDEX idx_manager_id (manager_id),
    INDEX idx_id_number (id_number),
    INDEX idx_credit_level (credit_level),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户基本信息表';

-- client_contact表（客户联系人表）
DROP TABLE IF EXISTS client_contact;
CREATE TABLE client_contact (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '联系人ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    client_id BIGINT NOT NULL COMMENT '客户ID',
    contact_name VARCHAR(50) NOT NULL COMMENT '联系人姓名',
    contact_type TINYINT DEFAULT 1 COMMENT '联系人类型(1-法定代表人,2-业务联系人,3-财务联系人,4-其他)',
    department VARCHAR(50) COMMENT '所属部门',
    position VARCHAR(50) COMMENT '职务职位',
    mobile VARCHAR(20) COMMENT '手机号码',
    telephone VARCHAR(20) COMMENT '固定电话',
    email VARCHAR(100) COMMENT '电子邮箱',
    wechat VARCHAR(50) COMMENT '微信号',
    qq VARCHAR(20) COMMENT 'QQ号',
    importance TINYINT DEFAULT 1 COMMENT '重要程度(1-普通,2-重要,3-非常重要)',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认联系人(0-否,1-是)',
    gender TINYINT COMMENT '性别(1-男,2-女)',
    birth_date DATE COMMENT '出生日期',
    education VARCHAR(50) COMMENT '学历',
    hobby VARCHAR(255) COMMENT '兴趣爱好',
    last_contact_time DATETIME COMMENT '最后联系时间',
    contact_frequency VARCHAR(20) COMMENT '联系频率',
    status TINYINT DEFAULT 1 COMMENT '状态(0-无效,1-有效)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_client_id (client_id),
    INDEX idx_contact_name (contact_name),
    INDEX idx_contact_type (contact_type),
    INDEX idx_mobile (mobile),
    INDEX idx_is_default (is_default),
    INDEX idx_status (status),
    
    CONSTRAINT fk_client_contact_client FOREIGN KEY (client_id) REFERENCES client_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户联系人表';

-- client_address表（客户地址表）
DROP TABLE IF EXISTS client_address;
CREATE TABLE client_address (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '地址ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    client_id BIGINT NOT NULL COMMENT '客户ID',
    address_type TINYINT DEFAULT 1 COMMENT '地址类型(1-注册地址,2-办公地址,3-收件地址,4-开票地址,5-其他)',
    country VARCHAR(50) DEFAULT '中国' COMMENT '国家地区',
    province VARCHAR(50) COMMENT '省份',
    city VARCHAR(50) COMMENT '城市',
    district VARCHAR(50) COMMENT '区县',
    address VARCHAR(255) NOT NULL COMMENT '详细地址',
    postcode VARCHAR(20) COMMENT '邮政编码',
    longitude DECIMAL(10,7) COMMENT '经度',
    latitude DECIMAL(10,7) COMMENT '纬度',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认地址(0-否,1-是)',
    address_tag VARCHAR(50) COMMENT '地址标签',
    contact_person VARCHAR(50) COMMENT '联系人',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    status TINYINT DEFAULT 1 COMMENT '状态(0-无效,1-有效)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_client_id (client_id),
    INDEX idx_address_type (address_type),
    INDEX idx_province_city (province, city),
    INDEX idx_is_default (is_default),
    INDEX idx_status (status),
    
    CONSTRAINT fk_client_address_client FOREIGN KEY (client_id) REFERENCES client_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户地址表';

-- ======================= 客户分类管理表 =======================

-- client_category表（客户分类表）
DROP TABLE IF EXISTS client_category;
CREATE TABLE client_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    category_name VARCHAR(50) NOT NULL COMMENT '分类名称',
    category_code VARCHAR(50) NOT NULL COMMENT '分类编码',
    level TINYINT DEFAULT 1 COMMENT '分类层级',
    parent_id BIGINT DEFAULT 0 COMMENT '上级分类ID',
    category_path VARCHAR(255) COMMENT '分类路径',
    description VARCHAR(255) COMMENT '分类描述',
    sort INT DEFAULT 0 COMMENT '排序权重',
    allow_select TINYINT DEFAULT 1 COMMENT '是否允许选择(0-否,1-是)',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统预置(0-否,1-是)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_category_code (tenant_id, category_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_parent_id (parent_id),
    INDEX idx_level (level),
    INDEX idx_sort (sort),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户分类表';

-- client_tag表（客户标签表）
DROP TABLE IF EXISTS client_tag;
CREATE TABLE client_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '标签ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tag_name VARCHAR(50) NOT NULL COMMENT '标签名称',
    tag_code VARCHAR(50) NOT NULL COMMENT '标签编码',
    color VARCHAR(20) COMMENT '标签颜色',
    tag_type VARCHAR(30) COMMENT '标签类型',
    description VARCHAR(255) COMMENT '标签描述',
    usage_count INT DEFAULT 0 COMMENT '使用次数',
    sort INT DEFAULT 0 COMMENT '排序',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统预置(0-否,1-是)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_tag_code (tenant_id, tag_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_tag_name (tag_name),
    INDEX idx_tag_type (tag_type),
    INDEX idx_sort (sort),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户标签表';

-- client_tag_relation表（客户标签关联表）
DROP TABLE IF EXISTS client_tag_relation;
CREATE TABLE client_tag_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    client_id BIGINT NOT NULL COMMENT '客户ID',
    tag_id BIGINT NOT NULL COMMENT '标签ID',
    tag_value VARCHAR(100) COMMENT '标签值',
    effective_time DATETIME COMMENT '生效时间',
    expiry_time DATETIME COMMENT '失效时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    
    UNIQUE KEY uk_client_tag (client_id, tag_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_client_id (client_id),
    INDEX idx_tag_id (tag_id),
    
    CONSTRAINT fk_client_tag_relation_client FOREIGN KEY (client_id) REFERENCES client_info(id) ON DELETE CASCADE,
    CONSTRAINT fk_client_tag_relation_tag FOREIGN KEY (tag_id) REFERENCES client_tag(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户标签关联表';

-- ======================= 客户关系管理表 =======================

-- client_relation表（客户关系表）
DROP TABLE IF EXISTS client_relation;
CREATE TABLE client_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关系ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    relation_type TINYINT NOT NULL COMMENT '关系类型(1-推荐关系,2-合作关系,3-竞争关系,4-上下游关系,5-其他)',
    source_client_id BIGINT NOT NULL COMMENT '源客户ID',
    target_client_id BIGINT NOT NULL COMMENT '目标客户ID',
    relation_desc VARCHAR(255) COMMENT '关系描述',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    effective_time DATETIME COMMENT '生效时间',
    expiry_time DATETIME COMMENT '失效时间',
    priority TINYINT DEFAULT 0 COMMENT '优先级(1-低,2-中,3-高)',
    attributes JSON COMMENT '关系属性(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-停用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_relation_type (relation_type),
    INDEX idx_source_client_id (source_client_id),
    INDEX idx_target_client_id (target_client_id),
    INDEX idx_status (status),
    
    CONSTRAINT fk_client_relation_source FOREIGN KEY (source_client_id) REFERENCES client_info(id) ON DELETE CASCADE,
    CONSTRAINT fk_client_relation_target FOREIGN KEY (target_client_id) REFERENCES client_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户关系表';

-- client_follow_up表（客户跟进表）
DROP TABLE IF EXISTS client_follow_up;
CREATE TABLE client_follow_up (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '跟进ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    client_id BIGINT NOT NULL COMMENT '客户ID',
    follow_type TINYINT DEFAULT 1 COMMENT '跟进类型(1-电话跟进,2-邮件跟进,3-实地拜访,4-会议沟通,5-其他)',
    follow_title VARCHAR(100) NOT NULL COMMENT '跟进标题',
    follow_content TEXT COMMENT '跟进内容',
    follow_time DATETIME NOT NULL COMMENT '跟进时间',
    follower_id BIGINT NOT NULL COMMENT '跟进人ID',
    follower_name VARCHAR(50) COMMENT '跟进人姓名',
    contact_person VARCHAR(50) COMMENT '联系人',
    result TINYINT COMMENT '跟进结果(1-成功,2-失败,3-待定)',
    next_follow_time DATETIME COMMENT '下次跟进时间',
    next_follow_plan TEXT COMMENT '下次跟进计划',
    importance TINYINT DEFAULT 2 COMMENT '重要性(1-低,2-中,3-高)',
    satisfaction TINYINT COMMENT '满意度(1-不满意,2-一般,3-满意,4-非常满意)',
    attachments JSON COMMENT '附件信息(JSON格式)',
    tags JSON COMMENT '跟进标签(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_client_id (client_id),
    INDEX idx_follow_type (follow_type),
    INDEX idx_follow_time (follow_time),
    INDEX idx_follower_id (follower_id),
    INDEX idx_next_follow_time (next_follow_time),
    INDEX idx_result (result),
    INDEX idx_status (status),
    
    CONSTRAINT fk_client_follow_up_client FOREIGN KEY (client_id) REFERENCES client_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户跟进表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 
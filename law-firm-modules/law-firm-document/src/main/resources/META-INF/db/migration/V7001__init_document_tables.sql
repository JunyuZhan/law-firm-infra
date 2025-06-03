-- 文档管理模块表结构
-- 版本: V7001
-- 模块: 文档管理模块 (V7000-V7999)
-- 创建时间: 2023-06-01
-- 说明: 文档管理功能的完整表结构定义
-- 包括：文档基础信息、内容管理、分类标签、版本控制、权限管理、业务关联、模板审核

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 文档基础信息表 =======================

-- document_info表（文档基本信息表）
DROP TABLE IF EXISTS document_info;
CREATE TABLE document_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '文档ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    document_number VARCHAR(50) NOT NULL COMMENT '文档编号',
    title VARCHAR(200) NOT NULL COMMENT '文档标题',
    description TEXT COMMENT '文档描述',
    keywords VARCHAR(500) COMMENT '关键词',
    document_type TINYINT DEFAULT 1 COMMENT '文档类型(1-合同文档,2-案件文档,3-法律意见,4-备忘录,5-模板,6-其他)',
    category_id BIGINT COMMENT '文档分类ID',
    file_name VARCHAR(200) COMMENT '文件名',
    file_type VARCHAR(50) COMMENT '文件类型',
    file_size BIGINT COMMENT '文件大小(字节)',
    file_extension VARCHAR(20) COMMENT '文件扩展名',
    mime_type VARCHAR(100) COMMENT 'MIME类型',
    file_path VARCHAR(500) COMMENT '文件路径',
    storage_type TINYINT DEFAULT 1 COMMENT '存储类型(1-本地,2-云存储,3-NAS)',
    storage_bucket VARCHAR(100) COMMENT '存储桶',
    file_md5 VARCHAR(32) COMMENT '文件MD5',
    author_id BIGINT COMMENT '作者ID',
    author_name VARCHAR(50) COMMENT '作者姓名',
    owner_id BIGINT COMMENT '所有者ID',
    owner_name VARCHAR(50) COMMENT '所有者姓名',
    department_id BIGINT COMMENT '所属部门ID',
    department_name VARCHAR(100) COMMENT '所属部门名称',
    document_status TINYINT DEFAULT 1 COMMENT '文档状态(1-草稿,2-审核中,3-已发布,4-已归档,5-已作废)',
    security_level TINYINT DEFAULT 1 COMMENT '安全级别(1-公开,2-内部,3-机密,4-绝密)',
    access_level TINYINT DEFAULT 1 COMMENT '访问权限(1-私有,2-部门内,3-公司内,4-公开)',
    is_encrypted TINYINT DEFAULT 0 COMMENT '是否加密(0-否,1-是)',
    encryption_type VARCHAR(20) COMMENT '加密类型',
    is_template TINYINT DEFAULT 0 COMMENT '是否模板(0-否,1-是)',
    template_id BIGINT COMMENT '模板ID',
    language VARCHAR(10) DEFAULT 'zh-CN' COMMENT '文档语言',
    page_count INT DEFAULT 0 COMMENT '页数',
    word_count INT DEFAULT 0 COMMENT '字数',
    view_count INT DEFAULT 0 COMMENT '查看次数',
    download_count INT DEFAULT 0 COMMENT '下载次数',
    share_count INT DEFAULT 0 COMMENT '分享次数',
    last_view_time DATETIME COMMENT '最后查看时间',
    last_modify_time DATETIME COMMENT '最后修改时间',
    publish_time DATETIME COMMENT '发布时间',
    archive_time DATETIME COMMENT '归档时间',
    expire_time DATETIME COMMENT '过期时间',
    tags JSON COMMENT '文档标签(JSON格式)',
    custom_fields JSON COMMENT '自定义字段(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_document_number (tenant_id, document_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_title (title),
    INDEX idx_document_type (document_type),
    INDEX idx_category_id (category_id),
    INDEX idx_document_status (document_status),
    INDEX idx_author_id (author_id),
    INDEX idx_owner_id (owner_id),
    INDEX idx_department_id (department_id),
    INDEX idx_security_level (security_level),
    INDEX idx_is_template (is_template),
    INDEX idx_template_id (template_id),
    INDEX idx_file_md5 (file_md5),
    INDEX idx_create_time (create_time),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档基本信息表';

-- document_content表（文档内容表）
DROP TABLE IF EXISTS document_content;
CREATE TABLE document_content (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '内容ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    document_id BIGINT NOT NULL COMMENT '文档ID',
    content_type TINYINT DEFAULT 1 COMMENT '内容类型(1-文本内容,2-HTML内容,3-原始文件,4-预览图)',
    content LONGTEXT COMMENT '文档内容',
    content_html LONGTEXT COMMENT 'HTML格式内容',
    content_text LONGTEXT COMMENT '纯文本内容(用于搜索)',
    preview_url VARCHAR(500) COMMENT '预览地址',
    thumbnail_url VARCHAR(500) COMMENT '缩略图地址',
    extract_text LONGTEXT COMMENT '提取的文本内容',
    content_encoding VARCHAR(20) DEFAULT 'UTF-8' COMMENT '内容编码',
    content_size BIGINT COMMENT '内容大小',
    is_extracted TINYINT DEFAULT 0 COMMENT '是否已提取文本(0-否,1-是)',
    extract_time DATETIME COMMENT '提取时间',
    ocr_result LONGTEXT COMMENT 'OCR识别结果',
    is_ocr_processed TINYINT DEFAULT 0 COMMENT '是否已OCR处理(0-否,1-是)',
    ocr_time DATETIME COMMENT 'OCR处理时间',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_document_content_type (document_id, content_type),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_document_id (document_id),
    INDEX idx_content_type (content_type),
    INDEX idx_is_extracted (is_extracted),
    INDEX idx_is_ocr_processed (is_ocr_processed),
    INDEX idx_status (status),
    
    CONSTRAINT fk_document_content_document FOREIGN KEY (document_id) REFERENCES document_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档内容表';

-- document_attachment表（文档附件表）
DROP TABLE IF EXISTS document_attachment;
CREATE TABLE document_attachment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '附件ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    document_id BIGINT NOT NULL COMMENT '文档ID',
    attachment_name VARCHAR(200) NOT NULL COMMENT '附件名称',
    attachment_type TINYINT DEFAULT 1 COMMENT '附件类型(1-相关文件,2-签名页,3-附录,4-参考资料)',
    file_name VARCHAR(200) COMMENT '文件名',
    file_path VARCHAR(500) COMMENT '文件路径',
    file_type VARCHAR(50) COMMENT '文件类型',
    file_size BIGINT COMMENT '文件大小',
    file_md5 VARCHAR(32) COMMENT '文件MD5',
    upload_time DATETIME COMMENT '上传时间',
    uploader_id BIGINT COMMENT '上传人ID',
    uploader_name VARCHAR(50) COMMENT '上传人姓名',
    description VARCHAR(500) COMMENT '附件描述',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_document_id (document_id),
    INDEX idx_attachment_type (attachment_type),
    INDEX idx_uploader_id (uploader_id),
    INDEX idx_sort_order (sort_order),
    INDEX idx_status (status),
    
    CONSTRAINT fk_document_attachment_document FOREIGN KEY (document_id) REFERENCES document_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档附件表';

-- ======================= 文档分类标签表 =======================

-- document_category表（文档分类表）
DROP TABLE IF EXISTS document_category;
CREATE TABLE document_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    category_name VARCHAR(100) NOT NULL COMMENT '分类名称',
    category_code VARCHAR(50) NOT NULL COMMENT '分类编码',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
    level TINYINT DEFAULT 1 COMMENT '分类层级',
    category_path VARCHAR(500) COMMENT '分类路径',
    description VARCHAR(500) COMMENT '分类描述',
    icon VARCHAR(100) COMMENT '分类图标',
    color VARCHAR(20) COMMENT '分类颜色',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统分类(0-否,1-是)',
    is_enabled TINYINT DEFAULT 1 COMMENT '是否启用(0-否,1-是)',
    document_count INT DEFAULT 0 COMMENT '文档数量',
    template_config JSON COMMENT '模板配置(JSON格式)',
    permission_config JSON COMMENT '权限配置(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_category_code (tenant_id, category_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_category_name (category_name),
    INDEX idx_parent_id (parent_id),
    INDEX idx_level (level),
    INDEX idx_sort_order (sort_order),
    INDEX idx_is_enabled (is_enabled),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档分类表';

-- document_tag表（文档标签表）
DROP TABLE IF EXISTS document_tag;
CREATE TABLE document_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '标签ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tag_name VARCHAR(50) NOT NULL COMMENT '标签名称',
    tag_code VARCHAR(50) NOT NULL COMMENT '标签编码',
    tag_type TINYINT DEFAULT 1 COMMENT '标签类型(1-业务标签,2-状态标签,3-优先级标签,4-自定义标签)',
    tag_category VARCHAR(50) COMMENT '标签分类',
    color VARCHAR(20) COMMENT '标签颜色',
    icon VARCHAR(100) COMMENT '标签图标',
    description VARCHAR(200) COMMENT '标签描述',
    usage_count INT DEFAULT 0 COMMENT '使用次数',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统标签(0-否,1-是)',
    is_enabled TINYINT DEFAULT 1 COMMENT '是否启用(0-否,1-是)',
    creator_id BIGINT COMMENT '创建者ID',
    creator_name VARCHAR(50) COMMENT '创建者姓名',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
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
    INDEX idx_tag_category (tag_category),
    INDEX idx_sort_order (sort_order),
    INDEX idx_is_enabled (is_enabled),
    INDEX idx_creator_id (creator_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档标签表';

-- document_tag_relation表（文档标签关联表）
DROP TABLE IF EXISTS document_tag_relation;
CREATE TABLE document_tag_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    document_id BIGINT NOT NULL COMMENT '文档ID',
    tag_id BIGINT NOT NULL COMMENT '标签ID',
    tag_value VARCHAR(200) COMMENT '标签值',
    add_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    add_by VARCHAR(50) COMMENT '添加人',
    
    UNIQUE KEY uk_document_tag (document_id, tag_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_document_id (document_id),
    INDEX idx_tag_id (tag_id),
    
    CONSTRAINT fk_document_tag_relation_document FOREIGN KEY (document_id) REFERENCES document_info(id) ON DELETE CASCADE,
    CONSTRAINT fk_document_tag_relation_tag FOREIGN KEY (tag_id) REFERENCES document_tag(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档标签关联表';

-- ======================= 文档版本权限表 =======================

-- document_version表（文档版本表）
DROP TABLE IF EXISTS document_version;
CREATE TABLE document_version (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '版本ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    document_id BIGINT NOT NULL COMMENT '文档ID',
    version_number VARCHAR(20) NOT NULL COMMENT '版本号',
    version_name VARCHAR(100) COMMENT '版本名称',
    version_type TINYINT DEFAULT 1 COMMENT '版本类型(1-主版本,2-次版本,3-修订版)',
    version_status TINYINT DEFAULT 1 COMMENT '版本状态(1-草稿,2-发布,3-归档,4-废弃)',
    file_path VARCHAR(500) COMMENT '文件路径',
    file_size BIGINT COMMENT '文件大小',
    file_md5 VARCHAR(32) COMMENT '文件MD5',
    change_description TEXT COMMENT '变更描述',
    change_type TINYINT COMMENT '变更类型(1-新增,2-修改,3-删除,4-合并)',
    parent_version_id BIGINT COMMENT '父版本ID',
    is_current TINYINT DEFAULT 0 COMMENT '是否当前版本(0-否,1-是)',
    creator_id BIGINT COMMENT '创建者ID',
    creator_name VARCHAR(50) COMMENT '创建者姓名',
    create_ip VARCHAR(50) COMMENT '创建IP',
    approval_required TINYINT DEFAULT 0 COMMENT '是否需要审批(0-否,1-是)',
    approval_status TINYINT COMMENT '审批状态(1-待审批,2-已审批,3-已拒绝)',
    approver_id BIGINT COMMENT '审批人ID',
    approval_time DATETIME COMMENT '审批时间',
    approval_comment TEXT COMMENT '审批意见',
    download_count INT DEFAULT 0 COMMENT '下载次数',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_document_version_number (document_id, version_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_document_id (document_id),
    INDEX idx_version_status (version_status),
    INDEX idx_creator_id (creator_id),
    INDEX idx_is_current (is_current),
    INDEX idx_parent_version_id (parent_version_id),
    INDEX idx_approval_status (approval_status),
    INDEX idx_status (status),
    
    CONSTRAINT fk_document_version_document FOREIGN KEY (document_id) REFERENCES document_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档版本表';

-- document_permission表（文档权限表）
DROP TABLE IF EXISTS document_permission;
CREATE TABLE document_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '权限ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    document_id BIGINT NOT NULL COMMENT '文档ID',
    subject_type TINYINT NOT NULL COMMENT '权限主体类型(1-用户,2-角色,3-部门)',
    subject_id BIGINT NOT NULL COMMENT '权限主体ID',
    subject_name VARCHAR(100) COMMENT '权限主体名称',
    permission_type TINYINT NOT NULL COMMENT '权限类型(1-查看,2-编辑,3-下载,4-分享,5-删除,6-管理)',
    permission_scope TINYINT DEFAULT 1 COMMENT '权限范围(1-当前版本,2-所有版本)',
    is_granted TINYINT DEFAULT 1 COMMENT '是否授权(0-拒绝,1-允许)',
    grant_source TINYINT DEFAULT 1 COMMENT '授权来源(1-直接授权,2-继承授权,3-分享授权)',
    grantor_id BIGINT COMMENT '授权人ID',
    grantor_name VARCHAR(50) COMMENT '授权人姓名',
    grant_time DATETIME COMMENT '授权时间',
    expire_time DATETIME COMMENT '过期时间',
    conditions JSON COMMENT '权限条件(JSON格式)',
    ip_restrictions VARCHAR(500) COMMENT 'IP限制',
    time_restrictions VARCHAR(200) COMMENT '时间限制',
    is_inherited TINYINT DEFAULT 0 COMMENT '是否继承权限(0-否,1-是)',
    inherit_from_id BIGINT COMMENT '继承来源ID',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_document_subject_permission (document_id, subject_type, subject_id, permission_type),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_document_id (document_id),
    INDEX idx_subject_type_id (subject_type, subject_id),
    INDEX idx_permission_type (permission_type),
    INDEX idx_grantor_id (grantor_id),
    INDEX idx_grant_time (grant_time),
    INDEX idx_expire_time (expire_time),
    INDEX idx_status (status),
    
    CONSTRAINT fk_document_permission_document FOREIGN KEY (document_id) REFERENCES document_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档权限表';

-- document_share表（文档分享表）
DROP TABLE IF EXISTS document_share;
CREATE TABLE document_share (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分享ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    document_id BIGINT NOT NULL COMMENT '文档ID',
    share_code VARCHAR(32) NOT NULL COMMENT '分享码',
    share_title VARCHAR(200) COMMENT '分享标题',
    share_type TINYINT DEFAULT 1 COMMENT '分享类型(1-链接分享,2-密码分享,3-邀请分享)',
    share_scope TINYINT DEFAULT 1 COMMENT '分享范围(1-公开,2-内部,3-指定用户)',
    access_password VARCHAR(20) COMMENT '访问密码',
    access_permissions JSON COMMENT '访问权限(JSON格式)',
    access_count INT DEFAULT 0 COMMENT '访问次数',
    download_count INT DEFAULT 0 COMMENT '下载次数',
    max_access_count INT COMMENT '最大访问次数',
    max_download_count INT COMMENT '最大下载次数',
    share_url VARCHAR(500) COMMENT '分享链接',
    qr_code_url VARCHAR(500) COMMENT '二维码链接',
    sharer_id BIGINT NOT NULL COMMENT '分享人ID',
    sharer_name VARCHAR(50) COMMENT '分享人姓名',
    share_message TEXT COMMENT '分享留言',
    expire_time DATETIME COMMENT '过期时间',
    last_access_time DATETIME COMMENT '最后访问时间',
    last_access_ip VARCHAR(50) COMMENT '最后访问IP',
    is_active TINYINT DEFAULT 1 COMMENT '是否有效(0-否,1-是)',
    watermark_config JSON COMMENT '水印配置(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_share_code (share_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_document_id (document_id),
    INDEX idx_share_type (share_type),
    INDEX idx_sharer_id (sharer_id),
    INDEX idx_expire_time (expire_time),
    INDEX idx_is_active (is_active),
    INDEX idx_status (status),
    
    CONSTRAINT fk_document_share_document FOREIGN KEY (document_id) REFERENCES document_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档分享表';

-- ======================= 文档业务关联表 =======================

-- document_case_relation表（案件文档关联表）
DROP TABLE IF EXISTS document_case_relation;
CREATE TABLE document_case_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    document_id BIGINT NOT NULL COMMENT '文档ID',
    case_id BIGINT NOT NULL COMMENT '案件ID',
    relation_type TINYINT DEFAULT 1 COMMENT '关联类型(1-案件材料,2-证据文件,3-法律文书,4-庭审记录,5-其他)',
    relation_desc VARCHAR(200) COMMENT '关联描述',
    is_key_document TINYINT DEFAULT 0 COMMENT '是否关键文档(0-否,1-是)',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    add_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    add_by VARCHAR(50) COMMENT '添加人',
    
    UNIQUE KEY uk_document_case (document_id, case_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_document_id (document_id),
    INDEX idx_case_id (case_id),
    INDEX idx_relation_type (relation_type),
    INDEX idx_is_key_document (is_key_document),
    
    CONSTRAINT fk_document_case_relation_document FOREIGN KEY (document_id) REFERENCES document_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='案件文档关联表';

-- document_contract_relation表（合同文档关联表）
DROP TABLE IF EXISTS document_contract_relation;
CREATE TABLE document_contract_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    document_id BIGINT NOT NULL COMMENT '文档ID',
    contract_id BIGINT NOT NULL COMMENT '合同ID',
    relation_type TINYINT DEFAULT 1 COMMENT '关联类型(1-合同正文,2-补充协议,3-附件,4-签署页,5-其他)',
    relation_desc VARCHAR(200) COMMENT '关联描述',
    is_key_document TINYINT DEFAULT 0 COMMENT '是否关键文档(0-否,1-是)',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    add_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    add_by VARCHAR(50) COMMENT '添加人',
    
    UNIQUE KEY uk_document_contract (document_id, contract_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_document_id (document_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_relation_type (relation_type),
    INDEX idx_is_key_document (is_key_document),
    
    CONSTRAINT fk_document_contract_relation_document FOREIGN KEY (document_id) REFERENCES document_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同文档关联表';

-- document_client_relation表（客户文档关联表）
DROP TABLE IF EXISTS document_client_relation;
CREATE TABLE document_client_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    document_id BIGINT NOT NULL COMMENT '文档ID',
    client_id BIGINT NOT NULL COMMENT '客户ID',
    relation_type TINYINT DEFAULT 1 COMMENT '关联类型(1-客户资料,2-尽调文档,3-法律意见,4-服务记录,5-其他)',
    relation_desc VARCHAR(200) COMMENT '关联描述',
    is_key_document TINYINT DEFAULT 0 COMMENT '是否关键文档(0-否,1-是)',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    add_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    add_by VARCHAR(50) COMMENT '添加人',
    
    UNIQUE KEY uk_document_client (document_id, client_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_document_id (document_id),
    INDEX idx_client_id (client_id),
    INDEX idx_relation_type (relation_type),
    INDEX idx_is_key_document (is_key_document),
    
    CONSTRAINT fk_document_client_relation_document FOREIGN KEY (document_id) REFERENCES document_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户文档关联表';

-- ======================= 文档模板审核表 =======================

-- document_template表（文档模板表）
DROP TABLE IF EXISTS document_template;
CREATE TABLE document_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '模板ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    template_name VARCHAR(200) NOT NULL COMMENT '模板名称',
    template_code VARCHAR(50) NOT NULL COMMENT '模板编码',
    template_type TINYINT DEFAULT 1 COMMENT '模板类型(1-合同模板,2-法律文书,3-意见书,4-备忘录,5-其他)',
    template_category VARCHAR(50) COMMENT '模板分类',
    template_content LONGTEXT COMMENT '模板内容',
    template_fields JSON COMMENT '模板字段(JSON格式)',
    template_rules JSON COMMENT '模板规则(JSON格式)',
    preview_url VARCHAR(500) COMMENT '预览地址',
    file_path VARCHAR(500) COMMENT '模板文件路径',
    file_type VARCHAR(50) COMMENT '文件类型',
    file_size BIGINT COMMENT '文件大小',
    version_number VARCHAR(20) DEFAULT '1.0' COMMENT '版本号',
    is_active TINYINT DEFAULT 1 COMMENT '是否启用(0-否,1-是)',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认模板(0-否,1-是)',
    usage_count INT DEFAULT 0 COMMENT '使用次数',
    creator_id BIGINT COMMENT '创建者ID',
    creator_name VARCHAR(50) COMMENT '创建者姓名',
    approval_status TINYINT DEFAULT 1 COMMENT '审批状态(1-待审批,2-已审批,3-已拒绝)',
    approver_id BIGINT COMMENT '审批人ID',
    approval_time DATETIME COMMENT '审批时间',
    approval_comment TEXT COMMENT '审批意见',
    applicable_scope TEXT COMMENT '适用范围',
    usage_instructions TEXT COMMENT '使用说明',
    change_log TEXT COMMENT '变更日志',
    tags JSON COMMENT '标签(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_template_code (tenant_id, template_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_template_name (template_name),
    INDEX idx_template_type (template_type),
    INDEX idx_template_category (template_category),
    INDEX idx_is_active (is_active),
    INDEX idx_is_default (is_default),
    INDEX idx_creator_id (creator_id),
    INDEX idx_approval_status (approval_status),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档模板表';

-- document_review表（文档审核表）
DROP TABLE IF EXISTS document_review;
CREATE TABLE document_review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '审核ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    document_id BIGINT NOT NULL COMMENT '文档ID',
    review_type TINYINT DEFAULT 1 COMMENT '审核类型(1-内容审核,2-合规审核,3-质量审核,4-发布审核)',
    review_stage TINYINT DEFAULT 1 COMMENT '审核阶段(1-初审,2-复审,3-终审)',
    review_status TINYINT DEFAULT 1 COMMENT '审核状态(1-待审核,2-审核通过,3-审核拒绝,4-需要修改)',
    reviewer_id BIGINT COMMENT '审核人ID',
    reviewer_name VARCHAR(50) COMMENT '审核人姓名',
    review_time DATETIME COMMENT '审核时间',
    review_opinion TEXT COMMENT '审核意见',
    review_result TEXT COMMENT '审核结果',
    review_score DECIMAL(3,1) COMMENT '审核评分',
    issues_found JSON COMMENT '发现的问题(JSON格式)',
    suggestions TEXT COMMENT '修改建议',
    next_reviewer_id BIGINT COMMENT '下一审核人ID',
    process_instance_id VARCHAR(64) COMMENT '流程实例ID',
    task_id VARCHAR(64) COMMENT '任务ID',
    priority TINYINT DEFAULT 2 COMMENT '优先级(1-低,2-中,3-高,4-紧急)',
    deadline DATETIME COMMENT '审核截止时间',
    review_duration INT COMMENT '审核耗时(分钟)',
    attachments JSON COMMENT '审核附件(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_document_id (document_id),
    INDEX idx_review_type (review_type),
    INDEX idx_review_status (review_status),
    INDEX idx_reviewer_id (reviewer_id),
    INDEX idx_review_time (review_time),
    INDEX idx_next_reviewer_id (next_reviewer_id),
    INDEX idx_deadline (deadline),
    INDEX idx_status (status),
    
    CONSTRAINT fk_document_review_document FOREIGN KEY (document_id) REFERENCES document_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档审核表';

-- document_operation_log表（文档操作日志表）
DROP TABLE IF EXISTS document_operation_log;
CREATE TABLE document_operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    document_id BIGINT NOT NULL COMMENT '文档ID',
    operation_type TINYINT NOT NULL COMMENT '操作类型(1-创建,2-查看,3-下载,4-编辑,5-删除,6-分享,7-评论,8-收藏)',
    operation_desc VARCHAR(200) COMMENT '操作描述',
    operator_id BIGINT COMMENT '操作人ID',
    operator_name VARCHAR(50) COMMENT '操作人姓名',
    operator_ip VARCHAR(50) COMMENT '操作IP',
    user_agent VARCHAR(500) COMMENT '用户代理',
    operation_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    operation_result TINYINT DEFAULT 1 COMMENT '操作结果(1-成功,2-失败)',
    error_message VARCHAR(500) COMMENT '错误信息',
    operation_details JSON COMMENT '操作详情(JSON格式)',
    request_params JSON COMMENT '请求参数(JSON格式)',
    response_data JSON COMMENT '响应数据(JSON格式)',
    execution_time INT COMMENT '执行时间(毫秒)',
    trace_id VARCHAR(64) COMMENT '链路追踪ID',
    session_id VARCHAR(64) COMMENT '会话ID',
    module_name VARCHAR(50) COMMENT '模块名称',
    method_name VARCHAR(100) COMMENT '方法名称',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_document_id (document_id),
    INDEX idx_operation_type (operation_type),
    INDEX idx_operator_id (operator_id),
    INDEX idx_operation_time (operation_time),
    INDEX idx_operation_result (operation_result),
    INDEX idx_trace_id (trace_id),
    
    CONSTRAINT fk_document_operation_log_document FOREIGN KEY (document_id) REFERENCES document_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档操作日志表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 
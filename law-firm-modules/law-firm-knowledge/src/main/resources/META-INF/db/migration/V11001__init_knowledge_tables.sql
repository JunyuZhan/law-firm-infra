-- 知识管理模块表结构
-- 版本: V11001
-- 模块: 知识管理模块 (V11000-V11999)
-- 创建时间: 2023-06-01
-- 说明: 知识管理功能的完整表结构定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 知识基础管理表 =======================

-- knowledge_document表（知识文档表）
DROP TABLE IF EXISTS knowledge_document;
CREATE TABLE knowledge_document (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '知识ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    knowledge_number VARCHAR(50) NOT NULL COMMENT '知识编号',
    title VARCHAR(200) NOT NULL COMMENT '知识标题',
    subtitle VARCHAR(200) COMMENT '知识副标题',
    summary VARCHAR(1000) COMMENT '知识摘要',
    knowledge_type TINYINT DEFAULT 1 COMMENT '知识类型(1-法律法规,2-案例分析,3-合同范本,4-法律文书,5-法律研究,6-业务知识,7-管理制度)',
    content_type TINYINT DEFAULT 1 COMMENT '内容类型(1-文本,2-富文本,3-Markdown,4-PDF,5-Word,6-PPT,7-视频,8-音频)',
    category_id BIGINT COMMENT '分类ID',
    category_path VARCHAR(500) COMMENT '分类路径',
    author_id BIGINT COMMENT '作者ID',
    author_name VARCHAR(50) COMMENT '作者姓名',
    author_department VARCHAR(100) COMMENT '作者部门',
    reviewer_id BIGINT COMMENT '审核人ID',
    reviewer_name VARCHAR(50) COMMENT '审核人姓名',
    review_time DATETIME COMMENT '审核时间',
    publisher_id BIGINT COMMENT '发布人ID',
    publisher_name VARCHAR(50) COMMENT '发布人姓名',
    publish_time DATETIME COMMENT '发布时间',
    keywords VARCHAR(500) COMMENT '关键词(逗号分隔)',
    difficulty_level TINYINT DEFAULT 1 COMMENT '难度等级(1-入门,2-初级,3-中级,4-高级,5-专家)',
    importance_level TINYINT DEFAULT 1 COMMENT '重要程度(1-一般,2-重要,3-核心,4-关键,5-战略)',
    quality_score DECIMAL(3,1) DEFAULT 0.0 COMMENT '质量评分(0.0-10.0)',
    document_status TINYINT DEFAULT 1 COMMENT '文档状态(1-草稿,2-待审核,3-已发布,4-已归档,5-已下架)',
    visibility TINYINT DEFAULT 1 COMMENT '可见性(1-公开,2-部门,3-岗位,4-私有)',
    is_featured TINYINT DEFAULT 0 COMMENT '是否精选(0-否,1-是)',
    is_top TINYINT DEFAULT 0 COMMENT '是否置顶(0-否,1-是)',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    download_count INT DEFAULT 0 COMMENT '下载次数',
    like_count INT DEFAULT 0 COMMENT '点赞次数',
    favorite_count INT DEFAULT 0 COMMENT '收藏次数',
    comment_count INT DEFAULT 0 COMMENT '评论次数',
    share_count INT DEFAULT 0 COMMENT '分享次数',
    last_view_time DATETIME COMMENT '最后浏览时间',
    effective_date DATE COMMENT '生效日期',
    expire_date DATE COMMENT '失效日期',
    source_url VARCHAR(500) COMMENT '来源链接',
    source_type TINYINT DEFAULT 1 COMMENT '来源类型(1-原创,2-整理,3-转载,4-翻译)',
    copyright_info VARCHAR(200) COMMENT '版权信息',
    attachment_count INT DEFAULT 0 COMMENT '附件数量',
    version_number VARCHAR(20) DEFAULT '1.0' COMMENT '版本号',
    tags JSON COMMENT '标签信息(JSON格式)',
    custom_fields JSON COMMENT '自定义字段(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_knowledge_number (tenant_id, knowledge_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_title (title),
    INDEX idx_knowledge_type (knowledge_type),
    INDEX idx_category_id (category_id),
    INDEX idx_author_id (author_id),
    INDEX idx_document_status (document_status),
    INDEX idx_visibility (visibility),
    INDEX idx_publish_time (publish_time),
    INDEX idx_effective_date (effective_date),
    INDEX idx_expire_date (expire_date),
    INDEX idx_is_featured (is_featured),
    INDEX idx_is_top (is_top),
    INDEX idx_view_count (view_count),
    INDEX idx_like_count (like_count),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识文档表';

-- knowledge_content表（知识内容表）
DROP TABLE IF EXISTS knowledge_content;
CREATE TABLE knowledge_content (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '内容ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    knowledge_id BIGINT NOT NULL COMMENT '知识ID',
    content_type TINYINT DEFAULT 1 COMMENT '内容类型(1-主要内容,2-HTML内容,3-纯文本,4-原始内容)',
    content LONGTEXT COMMENT '知识内容',
    content_html LONGTEXT COMMENT 'HTML格式内容',
    content_text LONGTEXT COMMENT '纯文本内容(用于搜索)',
    content_markdown LONGTEXT COMMENT 'Markdown格式内容',
    content_size BIGINT COMMENT '内容大小(字节)',
    word_count INT DEFAULT 0 COMMENT '字数统计',
    reading_time INT DEFAULT 0 COMMENT '预估阅读时间(分钟)',
    is_extracted TINYINT DEFAULT 0 COMMENT '是否已提取文本(0-否,1-是)',
    extract_time DATETIME COMMENT '提取时间',
    content_encoding VARCHAR(20) DEFAULT 'UTF-8' COMMENT '内容编码',
    content_hash VARCHAR(64) COMMENT '内容哈希值',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_knowledge_content_type (knowledge_id, content_type),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_knowledge_id (knowledge_id),
    INDEX idx_content_type (content_type),
    INDEX idx_is_extracted (is_extracted),
    INDEX idx_content_hash (content_hash),
    INDEX idx_status (status),
    
    CONSTRAINT fk_knowledge_content_document FOREIGN KEY (knowledge_id) REFERENCES knowledge_document(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识内容表';

-- knowledge_attachment表（知识附件表）
DROP TABLE IF EXISTS knowledge_attachment;
CREATE TABLE knowledge_attachment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '附件ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    knowledge_id BIGINT NOT NULL COMMENT '知识ID',
    file_name VARCHAR(200) NOT NULL COMMENT '文件名称',
    original_name VARCHAR(200) COMMENT '原始文件名',
    file_path VARCHAR(500) NOT NULL COMMENT '文件路径',
    file_url VARCHAR(500) COMMENT '文件访问URL',
    file_size BIGINT COMMENT '文件大小(字节)',
    file_type VARCHAR(50) COMMENT '文件类型',
    file_extension VARCHAR(20) COMMENT '文件扩展名',
    mime_type VARCHAR(100) COMMENT 'MIME类型',
    file_md5 VARCHAR(32) COMMENT '文件MD5值',
    storage_type TINYINT DEFAULT 1 COMMENT '存储类型(1-本地,2-OSS,3-CDN)',
    storage_bucket VARCHAR(100) COMMENT '存储桶',
    download_count INT DEFAULT 0 COMMENT '下载次数',
    is_preview TINYINT DEFAULT 0 COMMENT '是否可预览(0-否,1-是)',
    preview_url VARCHAR(500) COMMENT '预览地址',
    thumbnail_url VARCHAR(500) COMMENT '缩略图地址',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    uploader_id BIGINT COMMENT '上传人ID',
    uploader_name VARCHAR(50) COMMENT '上传人姓名',
    upload_time DATETIME COMMENT '上传时间',
    description VARCHAR(500) COMMENT '附件描述',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_knowledge_id (knowledge_id),
    INDEX idx_file_type (file_type),
    INDEX idx_file_md5 (file_md5),
    INDEX idx_sort_order (sort_order),
    INDEX idx_upload_time (upload_time),
    INDEX idx_status (status),
    
    CONSTRAINT fk_knowledge_attachment_document FOREIGN KEY (knowledge_id) REFERENCES knowledge_document(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识附件表';

-- ======================= 分类标签管理表 =======================

-- knowledge_category表（知识分类表）
DROP TABLE IF EXISTS knowledge_category;
CREATE TABLE knowledge_category (
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
    knowledge_count INT DEFAULT 0 COMMENT '知识文档数量',
    template_config JSON COMMENT '模板配置(JSON格式)',
    permission_config JSON COMMENT '权限配置(JSON格式)',
    manager_id BIGINT COMMENT '分类管理员ID',
    manager_name VARCHAR(50) COMMENT '分类管理员姓名',
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
    INDEX idx_manager_id (manager_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识分类表';

-- knowledge_tag表（知识标签表）
DROP TABLE IF EXISTS knowledge_tag;
CREATE TABLE knowledge_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '标签ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tag_name VARCHAR(50) NOT NULL COMMENT '标签名称',
    tag_code VARCHAR(50) NOT NULL COMMENT '标签编码',
    tag_type TINYINT DEFAULT 1 COMMENT '标签类型(1-法律领域,2-业务类型,3-重要程度,4-适用范围,5-自定义)',
    tag_color VARCHAR(20) DEFAULT '#007bff' COMMENT '标签颜色',
    tag_icon VARCHAR(100) COMMENT '标签图标',
    parent_id BIGINT DEFAULT 0 COMMENT '父标签ID',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    use_count INT DEFAULT 0 COMMENT '使用次数',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统标签(0-否,1-是)',
    is_hot TINYINT DEFAULT 0 COMMENT '是否热门标签(0-否,1-是)',
    creator_id BIGINT COMMENT '创建人ID',
    creator_name VARCHAR(50) COMMENT '创建人姓名',
    description VARCHAR(200) COMMENT '标签描述',
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
    INDEX idx_parent_id (parent_id),
    INDEX idx_sort_order (sort_order),
    INDEX idx_use_count (use_count),
    INDEX idx_is_hot (is_hot),
    INDEX idx_creator_id (creator_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识标签表';

-- knowledge_tag_relation表（知识标签关联表）
DROP TABLE IF EXISTS knowledge_tag_relation;
CREATE TABLE knowledge_tag_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    knowledge_id BIGINT NOT NULL COMMENT '知识ID',
    tag_id BIGINT NOT NULL COMMENT '标签ID',
    tag_weight DECIMAL(3,2) DEFAULT 1.00 COMMENT '标签权重',
    relation_type TINYINT DEFAULT 1 COMMENT '关联类型(1-主标签,2-辅助标签,3-自动标签)',
    operator_id BIGINT COMMENT '操作人ID',
    operator_name VARCHAR(50) COMMENT '操作人姓名',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_knowledge_tag (knowledge_id, tag_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_knowledge_id (knowledge_id),
    INDEX idx_tag_id (tag_id),
    INDEX idx_relation_type (relation_type),
    INDEX idx_operator_id (operator_id),
    INDEX idx_status (status),
    
    CONSTRAINT fk_knowledge_tag_relation_document FOREIGN KEY (knowledge_id) REFERENCES knowledge_document(id) ON DELETE CASCADE,
    CONSTRAINT fk_knowledge_tag_relation_tag FOREIGN KEY (tag_id) REFERENCES knowledge_tag(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识标签关联表';

-- ======================= 互动管理表 =======================

-- knowledge_comment表（知识评论表）
DROP TABLE IF EXISTS knowledge_comment;
CREATE TABLE knowledge_comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '评论ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    knowledge_id BIGINT NOT NULL COMMENT '知识ID',
    content TEXT NOT NULL COMMENT '评论内容',
    content_type TINYINT DEFAULT 1 COMMENT '内容类型(1-文本,2-富文本)',
    user_id BIGINT NOT NULL COMMENT '评论用户ID',
    user_name VARCHAR(50) COMMENT '评论用户名',
    user_avatar VARCHAR(200) COMMENT '用户头像',
    parent_id BIGINT DEFAULT 0 COMMENT '父评论ID',
    root_id BIGINT DEFAULT 0 COMMENT '根评论ID',
    reply_user_id BIGINT COMMENT '被回复用户ID',
    reply_user_name VARCHAR(50) COMMENT '被回复用户名',
    comment_level TINYINT DEFAULT 1 COMMENT '评论层级',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    reply_count INT DEFAULT 0 COMMENT '回复数',
    is_author_reply TINYINT DEFAULT 0 COMMENT '是否作者回复(0-否,1-是)',
    is_expert_reply TINYINT DEFAULT 0 COMMENT '是否专家回复(0-否,1-是)',
    is_featured TINYINT DEFAULT 0 COMMENT '是否精选评论(0-否,1-是)',
    quality_score DECIMAL(3,1) DEFAULT 0.0 COMMENT '评论质量分',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    user_agent VARCHAR(500) COMMENT '用户代理',
    comment_status TINYINT DEFAULT 1 COMMENT '评论状态(1-正常,2-待审核,3-已屏蔽,4-已删除)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_knowledge_id (knowledge_id),
    INDEX idx_user_id (user_id),
    INDEX idx_parent_id (parent_id),
    INDEX idx_root_id (root_id),
    INDEX idx_comment_status (comment_status),
    INDEX idx_is_featured (is_featured),
    INDEX idx_like_count (like_count),
    INDEX idx_create_time (create_time),
    INDEX idx_status (status),
    
    CONSTRAINT fk_knowledge_comment_document FOREIGN KEY (knowledge_id) REFERENCES knowledge_document(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识评论表';

-- knowledge_favorite表（知识收藏表）
DROP TABLE IF EXISTS knowledge_favorite;
CREATE TABLE knowledge_favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '收藏ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    knowledge_id BIGINT NOT NULL COMMENT '知识ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    user_name VARCHAR(50) COMMENT '用户姓名',
    folder_id BIGINT DEFAULT 0 COMMENT '收藏夹ID',
    folder_name VARCHAR(100) COMMENT '收藏夹名称',
    note VARCHAR(500) COMMENT '收藏备注',
    tags JSON COMMENT '个人标签(JSON格式)',
    favorite_type TINYINT DEFAULT 1 COMMENT '收藏类型(1-个人收藏,2-共享收藏)',
    priority TINYINT DEFAULT 1 COMMENT '优先级(1-一般,2-重要,3-紧急)',
    access_count INT DEFAULT 0 COMMENT '访问次数',
    last_access_time DATETIME COMMENT '最后访问时间',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_user_knowledge (user_id, knowledge_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_knowledge_id (knowledge_id),
    INDEX idx_user_id (user_id),
    INDEX idx_folder_id (folder_id),
    INDEX idx_favorite_type (favorite_type),
    INDEX idx_priority (priority),
    INDEX idx_last_access_time (last_access_time),
    INDEX idx_status (status),
    
    CONSTRAINT fk_knowledge_favorite_document FOREIGN KEY (knowledge_id) REFERENCES knowledge_document(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识收藏表';

-- knowledge_like表（知识点赞表）
DROP TABLE IF EXISTS knowledge_like;
CREATE TABLE knowledge_like (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '点赞ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    knowledge_id BIGINT NOT NULL COMMENT '知识ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    user_name VARCHAR(50) COMMENT '用户姓名',
    like_type TINYINT DEFAULT 1 COMMENT '点赞类型(1-点赞,2-点踩)',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_user_knowledge_like (user_id, knowledge_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_knowledge_id (knowledge_id),
    INDEX idx_user_id (user_id),
    INDEX idx_like_type (like_type),
    INDEX idx_create_time (create_time),
    INDEX idx_status (status),
    
    CONSTRAINT fk_knowledge_like_document FOREIGN KEY (knowledge_id) REFERENCES knowledge_document(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识点赞表';

-- ======================= 统计分析表 =======================

-- knowledge_view_log表（知识浏览记录表）
DROP TABLE IF EXISTS knowledge_view_log;
CREATE TABLE knowledge_view_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '浏览记录ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    knowledge_id BIGINT NOT NULL COMMENT '知识ID',
    user_id BIGINT COMMENT '用户ID',
    user_name VARCHAR(50) COMMENT '用户姓名',
    session_id VARCHAR(100) COMMENT '会话ID',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    user_agent VARCHAR(500) COMMENT '用户代理',
    referer VARCHAR(500) COMMENT '来源页面',
    view_duration INT DEFAULT 0 COMMENT '浏览时长(秒)',
    view_source TINYINT DEFAULT 1 COMMENT '浏览来源(1-直接访问,2-搜索,3-推荐,4-分享)',
    device_type TINYINT DEFAULT 1 COMMENT '设备类型(1-PC,2-移动端,3-平板)',
    view_date DATE COMMENT '浏览日期',
    view_hour TINYINT COMMENT '浏览小时',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_knowledge_id (knowledge_id),
    INDEX idx_user_id (user_id),
    INDEX idx_view_date (view_date),
    INDEX idx_view_hour (view_hour),
    INDEX idx_view_source (view_source),
    INDEX idx_device_type (device_type),
    INDEX idx_create_time (create_time),
    INDEX idx_status (status),
    
    CONSTRAINT fk_knowledge_view_log_document FOREIGN KEY (knowledge_id) REFERENCES knowledge_document(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识浏览记录表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1;
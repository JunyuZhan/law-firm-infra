-- law-firm-evidence 证据模块初始化表结构脚本
-- 请在此处编写证据相关的建表SQL 

CREATE TABLE evidence (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    case_id BIGINT NOT NULL COMMENT '关联案件ID',
    name VARCHAR(255) NOT NULL COMMENT '证据名称',
    type VARCHAR(50) COMMENT '证据类型',
    source VARCHAR(255) COMMENT '证据来源',
    proof_matter VARCHAR(1024) COMMENT '证明事项',
    submitter_id BIGINT COMMENT '提交人ID',
    submit_time DATETIME COMMENT '提交时间',
    evidence_chain VARCHAR(255) COMMENT '证据链',
    challenge_status VARCHAR(255) COMMENT '质证情况',
    remark VARCHAR(1024) COMMENT '备注',
    create_time DATETIME,
    create_by VARCHAR(64),
    update_time DATETIME,
    update_by VARCHAR(64)
);

CREATE TABLE evidence_catalog (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    case_id BIGINT NOT NULL COMMENT '关联案件ID',
    name VARCHAR(255) NOT NULL COMMENT '目录名称',
    parent_id BIGINT COMMENT '父级ID',
    level INT COMMENT '层级',
    path VARCHAR(255) COMMENT '路径',
    leaf BOOLEAN COMMENT '是否叶子节点',
    remark VARCHAR(1024) COMMENT '备注',
    create_time DATETIME,
    create_by VARCHAR(64),
    update_time DATETIME,
    update_by VARCHAR(64)
);

CREATE TABLE evidence_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL COMMENT '标签名称',
    description VARCHAR(255) COMMENT '标签描述',
    remark VARCHAR(1024) COMMENT '备注',
    create_time DATETIME,
    create_by VARCHAR(64),
    update_time DATETIME,
    update_by VARCHAR(64)
);

CREATE TABLE evidence_tag_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    evidence_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    remark VARCHAR(1024) COMMENT '备注',
    create_time DATETIME,
    create_by VARCHAR(64),
    update_time DATETIME,
    update_by VARCHAR(64)
);

CREATE TABLE evidence_attachment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    evidence_id BIGINT NOT NULL,
    attachment_type VARCHAR(64) COMMENT '附件类型',
    file_id BIGINT NOT NULL,
    description VARCHAR(255) COMMENT '附件描述',
    remark VARCHAR(1024) COMMENT '备注',
    create_time DATETIME,
    create_by VARCHAR(64),
    update_time DATETIME,
    update_by VARCHAR(64)
);

CREATE TABLE evidence_document_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    evidence_id BIGINT NOT NULL,
    document_id BIGINT NOT NULL,
    relation_type VARCHAR(64) COMMENT '关系类型',
    remark VARCHAR(1024) COMMENT '备注',
    create_time DATETIME,
    create_by VARCHAR(64),
    update_time DATETIME,
    update_by VARCHAR(64)
);

CREATE TABLE evidence_fact_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    evidence_id BIGINT NOT NULL,
    fact_id BIGINT NOT NULL,
    proof_matter VARCHAR(1024) COMMENT '证明事项',
    proof_level VARCHAR(32) COMMENT '证明力等级',
    remark VARCHAR(1024) COMMENT '备注',
    create_time DATETIME,
    create_by VARCHAR(64),
    update_time DATETIME,
    update_by VARCHAR(64)
);

CREATE TABLE evidence_personnel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    evidence_id BIGINT NOT NULL,
    personnel_id BIGINT NOT NULL,
    role_type VARCHAR(64) COMMENT '角色类型',
    remark VARCHAR(1024) COMMENT '备注',
    create_time DATETIME,
    create_by VARCHAR(64),
    update_time DATETIME,
    update_by VARCHAR(64)
);

CREATE TABLE evidence_review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    evidence_id BIGINT NOT NULL,
    reviewer_id BIGINT NOT NULL,
    reviewer_name VARCHAR(64),
    review_time DATETIME,
    review_opinion VARCHAR(1024),
    review_status VARCHAR(32),
    remark VARCHAR(1024) COMMENT '备注',
    create_time DATETIME,
    create_by VARCHAR(64),
    update_time DATETIME,
    update_by VARCHAR(64)
);

CREATE TABLE evidence_challenge (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    evidence_id BIGINT NOT NULL,
    challenger_id BIGINT,
    challenger_name VARCHAR(64),
    challenge_time DATETIME,
    opinion VARCHAR(1024),
    conclusion VARCHAR(1024),
    attachment_id BIGINT,
    remark VARCHAR(1024) COMMENT '备注',
    create_time DATETIME,
    create_by VARCHAR(64),
    update_time DATETIME,
    update_by VARCHAR(64)
);

CREATE TABLE evidence_trace (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    evidence_id BIGINT NOT NULL,
    operation_type VARCHAR(64),
    operator_id BIGINT,
    operator_name VARCHAR(64),
    operation_time DATETIME,
    node VARCHAR(255),
    preservation_no VARCHAR(255),
    remark VARCHAR(1024) COMMENT '备注',
    create_time DATETIME,
    create_by VARCHAR(64),
    update_time DATETIME,
    update_by VARCHAR(64)
); 
-- 印章信息表
CREATE TABLE seal (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    law_firm_id BIGINT NOT NULL COMMENT '所属律所ID',
    seal_code VARCHAR(32) NOT NULL COMMENT '印章编号',
    seal_name VARCHAR(100) NOT NULL COMMENT '印章名称',
    seal_type VARCHAR(20) NOT NULL COMMENT '印章类型',
    seal_status VARCHAR(20) NOT NULL COMMENT '印章状态',
    keeper_id BIGINT COMMENT '保管人ID',
    keeper_name VARCHAR(50) COMMENT '保管人姓名',
    storage_location VARCHAR(200) COMMENT '存放位置',
    manufacturer VARCHAR(100) COMMENT '制章单位',
    manufacture_date DATE COMMENT '制章日期',
    valid_period DATE COMMENT '有效期',
    remark VARCHAR(500) COMMENT '备注',
    enabled BOOLEAN DEFAULT TRUE COMMENT '是否启用',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    created_by VARCHAR(50) COMMENT '创建人',
    updated_by VARCHAR(50) COMMENT '更新人',
    UNIQUE KEY uk_seal_code (seal_code)
) COMMENT '印章信息表';

-- 印章使用记录表
CREATE TABLE seal_use_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    seal_id BIGINT NOT NULL COMMENT '印章ID',
    law_firm_id BIGINT NOT NULL COMMENT '所属律所ID',
    applicant_id BIGINT NOT NULL COMMENT '申请人ID',
    applicant_name VARCHAR(50) NOT NULL COMMENT '申请人姓名',
    apply_time DATETIME NOT NULL COMMENT '申请时间',
    purpose VARCHAR(500) NOT NULL COMMENT '用印目的',
    document_type VARCHAR(50) NOT NULL COMMENT '用印文件类型',
    document_number VARCHAR(100) COMMENT '文件编号',
    approver_id BIGINT COMMENT '审批人ID',
    approver_name VARCHAR(50) COMMENT '审批人姓名',
    approve_time DATETIME COMMENT '审批时间',
    approve_status VARCHAR(20) NOT NULL COMMENT '审批状态',
    approve_opinion VARCHAR(500) COMMENT '审批意见',
    use_time DATETIME COMMENT '用印时间',
    return_time DATETIME COMMENT '归还时间',
    remark VARCHAR(500) COMMENT '备注',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    created_by VARCHAR(50) COMMENT '创建人',
    updated_by VARCHAR(50) COMMENT '更新人'
) COMMENT '印章使用记录表';

-- 印章图片表
CREATE TABLE seal_image (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    seal_id BIGINT NOT NULL COMMENT '印章ID',
    image_type VARCHAR(20) NOT NULL COMMENT '图片类型',
    image_url VARCHAR(500) NOT NULL COMMENT '图片URL',
    image_name VARCHAR(100) COMMENT '图片名称',
    file_size BIGINT COMMENT '文件大小(字节)',
    upload_time DATETIME NOT NULL COMMENT '上传时间',
    remark VARCHAR(500) COMMENT '备注',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    created_by VARCHAR(50) COMMENT '创建人',
    updated_by VARCHAR(50) COMMENT '更新人'
) COMMENT '印章图片表'; 
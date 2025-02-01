-- 创建部门表
CREATE TABLE org_dept (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '部门ID',
    parent_id BIGINT COMMENT '父部门ID',
    dept_name VARCHAR(50) NOT NULL COMMENT '部门名称',
    order_num INT DEFAULT 0 COMMENT '显示顺序',
    leader VARCHAR(20) COMMENT '负责人',
    phone VARCHAR(11) COMMENT '联系电话',
    email VARCHAR(50) COMMENT '邮箱',
    ancestors VARCHAR(255) DEFAULT '0' COMMENT '祖级列表',
    created_by BIGINT COMMENT '创建者',
    created_time DATETIME COMMENT '创建时间',
    updated_by BIGINT COMMENT '更新者',
    updated_time DATETIME COMMENT '更新时间',
    deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
    version INT DEFAULT 0 COMMENT '版本号',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- 创建角色部门关联表
CREATE TABLE org_role_dept (
    role_id BIGINT NOT NULL COMMENT '角色ID',
    dept_id BIGINT NOT NULL COMMENT '部门ID',
    PRIMARY KEY (role_id, dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和部门关联表';

-- 创建用户部门关联表
CREATE TABLE org_user_dept (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    dept_id BIGINT NOT NULL COMMENT '部门ID',
    PRIMARY KEY (user_id, dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和部门关联表';

-- 创建索引
CREATE INDEX idx_parent_id ON org_dept(parent_id);
CREATE INDEX idx_dept_name ON org_dept(dept_name);
CREATE INDEX idx_order_num ON org_dept(order_num);
CREATE INDEX idx_ancestors ON org_dept(ancestors); 
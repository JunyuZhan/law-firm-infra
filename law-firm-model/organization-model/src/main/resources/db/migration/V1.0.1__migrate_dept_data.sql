-- 迁移部门数据
INSERT INTO org_dept (
    id,
    parent_id,
    dept_name,
    order_num,
    leader,
    phone,
    email,
    ancestors,
    created_by,
    created_time,
    updated_by,
    updated_time,
    deleted,
    version
)
SELECT 
    id,
    parent_id,
    dept_name,
    order_num,
    leader,
    phone,
    email,
    ancestors,
    created_by,
    created_time,
    updated_by,
    updated_time,
    deleted,
    version
FROM sys_dept;

-- 迁移角色部门关联数据
INSERT INTO sys_role_dept (role_id, dept_id)
SELECT role_id, dept_id
FROM sys_role_dept;

-- 迁移用户部门关联数据
INSERT INTO sys_user_dept (user_id, dept_id)
SELECT user_id, dept_id
FROM sys_user_dept; 
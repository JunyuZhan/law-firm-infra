-- 清理数据
DELETE FROM case_status_log;
DELETE FROM case_info;

-- 插入测试案件数据
INSERT INTO case_info (
    id, case_number, case_name, description, case_type, case_status, status,
    client_id, principal_id, law_firm_id, branch_id, department_id,
    filing_time, create_time, update_time
) VALUES (
    1, 'TEST-001', '测试案件1', '测试案件描述1', 'CIVIL', 'DRAFT', 'ENABLED',
    1, 'test-lawyer', 1, 1, 1,
    NOW(), NOW(), NOW()
);

INSERT INTO case_info (
    id, case_number, case_name, description, case_type, case_status, status,
    client_id, principal_id, law_firm_id, branch_id, department_id,
    filing_time, create_time, update_time
) VALUES (
    2, 'TEST-002', '测试案件2', '测试案件描述2', 'CRIMINAL', 'IN_PROGRESS', 'ENABLED',
    2, 'test-lawyer', 1, 1, 1,
    NOW(), NOW(), NOW()
);

-- 插入测试状态日志数据
INSERT INTO case_status_log (
    id, case_id, from_status, to_status, operator, reason, operate_time,
    create_time, update_time
) VALUES (
    1, 1, 'DRAFT', 'PENDING', 'test-operator', '测试状态变更1',
    NOW(), NOW(), NOW()
);

INSERT INTO case_status_log (
    id, case_id, from_status, to_status, operator, reason, operate_time,
    create_time, update_time
) VALUES (
    2, 2, 'DRAFT', 'IN_PROGRESS', 'test-operator', '测试状态变更2',
    NOW(), NOW(), NOW()
); 
-- 清理测试数据
DELETE FROM document_info WHERE document_number LIKE 'TEST-%';

-- 插入测试数据
INSERT INTO document_info (
    id, document_number, document_name, document_type, security_level,
    status, file_path, file_size, file_type, version,
    create_time, create_by, update_time, update_by
) VALUES 
(1001, 'TEST-DOC-001', '测试合同1', 'CONTRACT', 'INTERNAL',
 'DRAFT', '/test/files/contract1.pdf', 1024, 'pdf', 1,
 NOW(), 'admin', NOW(), 'admin'),
 
(1002, 'TEST-DOC-002', '测试协议1', 'AGREEMENT', 'INTERNAL',
 'APPROVED', '/test/files/agreement1.docx', 2048, 'docx', 1,
 NOW(), 'admin', NOW(), 'admin'),
 
(1003, 'TEST-DOC-003', '测试报告1', 'REPORT', 'CONFIDENTIAL',
 'REVIEWING', '/test/files/report1.xlsx', 3072, 'xlsx', 1,
 NOW(), 'admin', NOW(), 'admin');

-- 插入版本历史数据
INSERT INTO document_version_history (
    id, document_id, version, file_path, file_size,
    create_time, create_by, remark
) VALUES 
(2001, 1001, '1.0', '/test/files/contract1_v1.pdf', 1024,
 NOW(), 'admin', '初始版本'),
 
(2002, 1002, '1.0', '/test/files/agreement1_v1.docx', 2048,
 NOW(), 'admin', '初始版本'),
 
(2003, 1003, '1.0', '/test/files/report1_v1.xlsx', 3072,
 NOW(), 'admin', '初始版本'); 
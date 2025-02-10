-- 插入测试文档数据
INSERT INTO doc_document (
    document_number,
    document_name,
    document_type,
    security_level,
    document_status,
    category_id,
    law_firm_id,
    create_by,
    update_by
) VALUES
('DOC-TEST-001', '测试合同1', 'CONTRACT', 'NORMAL', 'DRAFT', 1, 1, 'test_user', 'test_user'),
('DOC-TEST-002', '测试协议1', 'AGREEMENT', 'NORMAL', 'NORMAL', 1, 1, 'test_user', 'test_user'),
('DOC-TEST-003', '测试报告1', 'REPORT', 'CONFIDENTIAL', 'NORMAL', 2, 1, 'test_user', 'test_user');

-- 插入文档存储数据
INSERT INTO doc_document_storage (
    document_id,
    file_name,
    file_type,
    file_size,
    storage_path,
    md5,
    create_by,
    update_by
) VALUES
(1, 'test1.pdf', 'pdf', 1024, '/storage/test1.pdf', 'md5-1', 'test_user', 'test_user'),
(2, 'test2.docx', 'docx', 2048, '/storage/test2.docx', 'md5-2', 'test_user', 'test_user'),
(3, 'test3.pdf', 'pdf', 3072, '/storage/test3.pdf', 'md5-3', 'test_user', 'test_user');

-- 插入文档版本数据
INSERT INTO doc_document_version (
    document_id,
    version_number,
    file_name,
    file_type,
    file_size,
    storage_path,
    md5,
    change_description,
    create_by,
    update_by
) VALUES
(1, 'v1.0', 'test1_v1.pdf', 'pdf', 1024, '/storage/test1_v1.pdf', 'md5-v1', '初始版本', 'test_user', 'test_user'),
(1, 'v1.1', 'test1_v2.pdf', 'pdf', 1536, '/storage/test1_v2.pdf', 'md5-v2', '修改内容', 'test_user', 'test_user'),
(2, 'v1.0', 'test2_v1.docx', 'docx', 2048, '/storage/test2_v1.docx', 'md5-v3', '初始版本', 'test_user', 'test_user'); 
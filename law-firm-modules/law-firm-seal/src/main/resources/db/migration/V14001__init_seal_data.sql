-- 插入测试印章数据
INSERT INTO seal 
(law_firm_id, seal_code, seal_name, seal_type, seal_status, keeper_id, keeper_name, 
storage_location, manufacturer, manufacture_date, valid_period, enabled, created_by, updated_by)
VALUES
(1, 'SEAL20240001', '北京某某律师事务所公章', 'OFFICIAL', 'IN_USE', 1, '张三', 
'档案室保险柜', '北京某某印章制作有限公司', '2024-01-01', '2029-12-31', true, 'admin', 'admin'),
(1, 'SEAL20240002', '北京某某律师事务所财务专用章', 'FINANCIAL', 'IN_USE', 2, '李四', 
'财务室保险柜', '北京某某印章制作有限公司', '2024-01-01', '2029-12-31', true, 'admin', 'admin'),
(1, 'SEAL20240003', '北京某某律师事务所合同专用章', 'CONTRACT', 'IN_USE', 3, '王五', 
'办公室保险柜', '北京某某印章制作有限公司', '2024-01-01', '2029-12-31', true, 'admin', 'admin');

-- 插入测试使用记录
INSERT INTO seal_use_record 
(seal_id, law_firm_id, applicant_id, applicant_name, apply_time, purpose, document_type, 
document_number, approver_id, approver_name, approve_time, approve_status, use_time, return_time, created_by, updated_by)
VALUES
(1, 1, 4, '赵六', '2024-02-01 09:00:00', '用于签署合作协议', 'CONTRACT', 
'HT20240201001', 1, '张三', '2024-02-01 10:00:00', 'APPROVED', '2024-02-01 10:30:00', '2024-02-01 11:00:00', 'admin', 'admin'),
(2, 1, 5, '钱七', '2024-02-02 14:00:00', '用于开具发票', 'INVOICE', 
'FP20240202001', 2, '李四', '2024-02-02 15:00:00', 'APPROVED', '2024-02-02 15:30:00', '2024-02-02 16:00:00', 'admin', 'admin'),
(3, 1, 6, '孙八', '2024-02-03 11:00:00', '用于签署委托合同', 'CONTRACT', 
'HT20240203001', 3, '王五', '2024-02-03 11:30:00', 'APPROVED', '2024-02-03 14:00:00', '2024-02-03 15:00:00', 'admin', 'admin');

-- 插入测试图片数据
INSERT INTO seal_image 
(seal_id, image_type, image_url, image_name, file_size, upload_time, created_by, updated_by)
VALUES
(1, 'FRONT', '/uploads/seals/SEAL20240001_front.jpg', 'SEAL20240001_front.jpg', 102400, '2024-01-01 10:00:00', 'admin', 'admin'),
(1, 'BACK', '/uploads/seals/SEAL20240001_back.jpg', 'SEAL20240001_back.jpg', 98304, '2024-01-01 10:00:00', 'admin', 'admin'),
(2, 'FRONT', '/uploads/seals/SEAL20240002_front.jpg', 'SEAL20240002_front.jpg', 112640, '2024-01-01 10:30:00', 'admin', 'admin'),
(2, 'BACK', '/uploads/seals/SEAL20240002_back.jpg', 'SEAL20240002_back.jpg', 106496, '2024-01-01 10:30:00', 'admin', 'admin'),
(3, 'FRONT', '/uploads/seals/SEAL20240003_front.jpg', 'SEAL20240003_front.jpg', 118784, '2024-01-01 11:00:00', 'admin', 'admin'),
(3, 'BACK', '/uploads/seals/SEAL20240003_back.jpg', 'SEAL20240003_back.jpg', 110592, '2024-01-01 11:00:00', 'admin', 'admin'); 
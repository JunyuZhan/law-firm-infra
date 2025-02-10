-- 插入测试员工数据
INSERT INTO per_employee 
(law_firm_id, name, employee_code, employee_type, id_number, birth_date, gender, mobile, email, 
branch_id, department_id, position_id, entry_date, employment_status, enabled, created_by, updated_by)
VALUES
(1, '张三', 'EMP20240001', 'LAWYER', '110101199001011234', '1990-01-01', '男', '13800138001', 'zhangsan@lawfirm.com', 
1, 1, 1, '2024-01-01', 'REGULAR', true, 1, 1),
(1, '李四', 'EMP20240002', 'ADMINISTRATIVE', '110101199202022345', '1992-02-02', '男', '13800138002', 'lisi@lawfirm.com', 
1, 2, 2, '2024-01-02', 'PROBATION', true, 1, 1),
(1, '王五', 'EMP20240003', 'LAWYER', '110101199503033456', '1995-03-03', '女', '13800138003', 'wangwu@lawfirm.com', 
2, 3, 3, '2024-01-03', 'PROBATION', true, 1, 1);

-- 插入测试档案数据
INSERT INTO personnel_employee_archive 
(employee_id, archive_no, archive_type, title, content, status, create_by, update_by)
VALUES
(1, 'ARC20240001', 'EDUCATION', '本科学历', '法学专业本科毕业证书', 1, 1, 1),
(1, 'ARC20240002', 'CERTIFICATE', '律师资格证', '法律职业资格证书', 1, 1, 1),
(2, 'ARC20240003', 'EDUCATION', '研究生学历', '法学专业硕士毕业证书', 1, 1, 1);

-- 插入测试证照数据
INSERT INTO personnel_employee_certificate 
(employee_id, cert_type, cert_no, cert_name, issuing_authority, issuing_date, status, create_by, update_by)
VALUES
(1, 'QUALIFICATION', 'L20240001', '法律职业资格证', '司法部', '2023-12-01', 1, 1, 1),
(2, 'QUALIFICATION', 'L20240002', '法律职业资格证', '司法部', '2023-12-02', 1, 1, 1),
(3, 'QUALIFICATION', 'L20240003', '法律职业资格证', '司法部', '2023-12-03', 1, 1, 1);

-- 插入律师数据
INSERT INTO lawyer
(employee_id, title, license_number, license_date, practicing_institution, specialties, experience, is_partner, practice_status)
VALUES
(1, 'SENIOR_LAWYER', 'L20240001', '2015-01-01', '北京市第一律师事务所', '公司法,合同法', '从事法律工作10年', false, 'ACTIVE'),
(3, 'LAWYER', 'L20240003', '2020-01-01', '北京市第一律师事务所', '知识产权,劳动法', '从事法律工作3年', false, 'ACTIVE');

-- 插入行政人员数据
INSERT INTO staff
(employee_id, job_duties, work_scope, education, major, work_experience, contract_type)
VALUES
(2, '行政助理', '文件管理,日常事务', '本科', '工商管理', '3年行政工作经验', 'FULL_TIME'); 
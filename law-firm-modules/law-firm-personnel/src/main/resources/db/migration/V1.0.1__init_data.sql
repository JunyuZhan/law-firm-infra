-- 插入测试员工数据
INSERT INTO `personnel_employee` 
(`employee_no`, `name`, `gender`, `birthday`, `id_card`, `mobile`, `email`, `branch_id`, `department_id`, `position_id`, `entry_date`, `status`)
VALUES
('EMP001', '张三', 1, '1990-01-15', '110101199001150123', '13800138001', 'zhangsan@lawfirm.com', 1, 1, 1, '2020-01-01', 1),
('EMP002', '李四', 0, '1992-03-20', '110101199203200456', '13800138002', 'lisi@lawfirm.com', 1, 2, 2, '2020-02-01', 1),
('EMP003', '王五', 1, '1988-07-08', '110101198807080789', '13800138003', 'wangwu@lawfirm.com', 2, 3, 3, '2020-03-01', 1);

-- 插入员工档案数据
INSERT INTO `personnel_employee_archive`
(`employee_id`, `education`, `school`, `major`, `graduation_date`, `work_experience`)
VALUES
(1, '本科', '北京大学', '法学', '2012-06-30', '2012-2019 某律师事务所执业律师'),
(2, '硕士', '清华大学', '经济法', '2015-06-30', '2015-2019 某法律咨询公司法务主管'),
(3, '博士', '中国人民大学', '民商法', '2013-06-30', '2013-2019 某大型企业法务总监');

-- 插入员工证书数据
INSERT INTO `personnel_employee_certificate`
(`employee_id`, `certificate_type`, `certificate_no`, `certificate_name`, `issuing_authority`, `issuing_date`, `expiry_date`)
VALUES
(1, '律师资格证', 'LS2012001', '法律职业资格证书', '中华人民共和国司法部', '2012-09-01', NULL),
(2, '律师资格证', 'LS2015001', '法律职业资格证书', '中华人民共和国司法部', '2015-09-01', NULL),
(3, '律师资格证', 'LS2013001', '法律职业资格证书', '中华人民共和国司法部', '2013-09-01', NULL); 
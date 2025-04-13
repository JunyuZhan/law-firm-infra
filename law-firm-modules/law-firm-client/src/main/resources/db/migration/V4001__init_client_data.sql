-- 初始化客户演示数据

-- 插入示例客户数据（企业客户）
INSERT INTO client_info (id, client_no, name, type, source, industry, level, status, contact_name, contact_phone, contact_email, address, region, legal_representative, business_license, registered_capital, unified_social_credit_code, establish_date, follow_up_status, responsible_lawyer_id, deleted, tenant_id, create_time, create_by, update_time, update_by) VALUES
(1, 'C20250001', '远东科技有限公司', 2, 1, '信息技术', 3, 3, '张经理', '13800138001', 'zhang@fareast.com', '北京市海淀区中关村大厦1201室', '北京市海淀区', '张远东', '110108000123456', 10000000.00, '91110108MA00123456', '2010-05-15', 4, 1, 0, 1, NOW(), 1, NOW(), 1),
(2, 'C20250002', '金鑫投资有限公司', 2, 2, '金融投资', 4, 3, '李总', '13900139001', 'li@jinxin.com', '上海市浦东新区陆家嘴金融中心2301室', '上海市浦东新区', '李金鑫', '310115000234567', 50000000.00, '91310115MA00234567', '2012-08-20', 4, 2, 0, 1, NOW(), 1, NOW(), 1),
(3, 'C20250003', '绿洲房地产开发有限公司', 2, 3, '房地产', 3, 2, '王董', '13700137001', 'wang@lvzhou.com', '深圳市南山区科技园3号楼1501室', '深圳市南山区', '王绿洲', '440305000345678', 30000000.00, '91440305MA00345678', '2015-03-10', 2, 3, 0, 1, NOW(), 1, NOW(), 1),
(4, 'C20250004', '海蓝医药科技有限公司', 2, 1, '医药制造', 2, 1, '刘经理', '13600136001', 'liu@hailang.com', '广州市黄埔区科学城光谱路8号', '广州市黄埔区', '刘海蓝', '440112000456789', 20000000.00, '91440112MA00456789', '2018-11-05', 1, 1, 0, 1, NOW(), 1, NOW(), 1),
(5, 'C20250005', '优康医疗器械有限公司', 2, 4, '医疗器械', 2, 2, '陈主管', '13500135001', 'chen@youkang.com', '杭州市滨江区江南大道388号', '杭州市滨江区', '陈优康', '330108000567890', 15000000.00, '91330108MA00567890', '2019-06-18', 2, 2, 0, 1, NOW(), 1, NOW(), 1);

-- 插入示例客户数据（个人客户）
INSERT INTO client_info (id, client_no, name, type, source, level, status, contact_phone, contact_email, address, region, id_type, id_number, follow_up_status, responsible_lawyer_id, deleted, tenant_id, create_time, create_by, update_time, update_by) VALUES
(6, 'C20250006', '赵明', 1, 2, 2, 3, '13400134001', 'zhao@example.com', '北京市朝阳区建国路18号公寓2单元501室', '北京市朝阳区', 1, '110101199001011234', 4, 3, 0, 1, NOW(), 1, NOW(), 1),
(7, 'C20250007', '钱军', 1, 1, 1, 2, '13300133001', 'qian@example.com', '上海市静安区南京西路1601号', '上海市静安区', 1, '310101198505052345', 2, 1, 0, 1, NOW(), 1, NOW(), 1),
(8, 'C20250008', '孙女士', 1, 3, 3, 3, '13200132001', 'sun@example.com', '广州市天河区天河路228号', '广州市天河区', 1, '440101198212123456', 4, 2, 0, 1, NOW(), 1, NOW(), 1),
(9, 'C20250009', '李先生', 1, 5, 1, 1, '13100131001', 'li@example.com', '深圳市福田区深南大道1088号', '深圳市福田区', 1, '440301199307074567', 1, 3, 0, 1, NOW(), 1, NOW(), 1),
(10, 'C20250010', '周教授', 1, 2, 4, 3, '13000130001', 'zhou@university.edu', '南京市鼓楼区汉口路22号大学教师公寓', '南京市鼓楼区', 1, '320102196806085678', 4, 1, 0, 1, NOW(), 1, NOW(), 1);

-- 插入示例客户联系人数据
INSERT INTO client_contact (id, client_id, name, gender, position, department, phone, email, is_primary, is_decision_maker, deleted, tenant_id, create_time, create_by, update_time, update_by) VALUES
(1, 1, '张经理', 1, '总经理', '管理层', '13800138001', 'zhang@fareast.com', 1, 1, 0, 1, NOW(), 1, NOW(), 1),
(2, 1, '陈助理', 2, '行政助理', '行政部', '13800138002', 'chen@fareast.com', 0, 0, 0, 1, NOW(), 1, NOW(), 1),
(3, 2, '李总', 1, '董事长', '董事会', '13900139001', 'li@jinxin.com', 1, 1, 0, 1, NOW(), 1, NOW(), 1),
(4, 2, '赵秘书', 2, '董事长秘书', '董事会办公室', '13900139002', 'zhao@jinxin.com', 0, 0, 0, 1, NOW(), 1, NOW(), 1),
(5, 3, '王董', 1, '董事长', '董事会', '13700137001', 'wang@lvzhou.com', 1, 1, 0, 1, NOW(), 1, NOW(), 1),
(6, 3, '林总', 1, '总经理', '管理层', '13700137002', 'lin@lvzhou.com', 0, 1, 0, 1, NOW(), 1, NOW(), 1),
(7, 4, '刘经理', 1, '研发经理', '研发部', '13600136001', 'liu@hailang.com', 1, 0, 0, 1, NOW(), 1, NOW(), 1),
(8, 4, '吴总', 1, '总经理', '管理层', '13600136002', 'wu@hailang.com', 0, 1, 0, 1, NOW(), 1, NOW(), 1),
(9, 5, '陈主管', 2, '采购主管', '采购部', '13500135001', 'chen@youkang.com', 1, 0, 0, 1, NOW(), 1, NOW(), 1),
(10, 5, '郑总', 1, '总经理', '管理层', '13500135002', 'zheng@youkang.com', 0, 1, 0, 1, NOW(), 1, NOW(), 1); 
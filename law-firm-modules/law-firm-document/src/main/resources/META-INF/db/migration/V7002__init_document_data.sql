-- 文档管理模块基础数据
-- 版本: V7002
-- 模块: 文档管理模块 (V7000-V7999)
-- 创建时间: 2023-06-01
-- 说明: 文档管理功能的基础字典数据和初始化数据

-- 设置字符集
SET NAMES utf8mb4;

-- ======================= 系统字典类型 =======================

-- 文档相关字典类型（使用ON DUPLICATE KEY UPDATE避免冲突）
INSERT INTO sys_dict_type (tenant_id, dict_name, dict_type, status, create_by, create_time, remark) VALUES
(0, '文档类型', 'doc_document_type', 1, 'system', NOW(), '文档类型字典'),
(0, '文档状态', 'doc_status', 1, 'system', NOW(), '文档状态字典'),
(0, '安全级别', 'doc_security_level', 1, 'system', NOW(), '文档安全级别字典'),
(0, '访问权限', 'doc_access_level', 1, 'system', NOW(), '文档访问权限字典'),
(0, '存储类型', 'doc_storage_type', 1, 'system', NOW(), '文档存储类型字典'),
(0, '内容类型', 'doc_content_type', 1, 'system', NOW(), '文档内容类型字典'),
(0, '附件类型', 'doc_attachment_type', 1, 'system', NOW(), '文档附件类型字典'),
(0, '标签类型', 'doc_tag_type', 1, 'system', NOW(), '文档标签类型字典'),
(0, '版本类型', 'doc_version_type', 1, 'system', NOW(), '文档版本类型字典'),
(0, '版本状态', 'doc_version_status', 1, 'system', NOW(), '文档版本状态字典'),
(0, '权限主体类型', 'doc_subject_type', 1, 'system', NOW(), '权限主体类型字典'),
(0, '权限类型', 'doc_permission_type', 1, 'system', NOW(), '权限类型字典'),
(0, '授权来源', 'doc_grant_source', 1, 'system', NOW(), '授权来源字典'),
(0, '分享类型', 'doc_share_type', 1, 'system', NOW(), '分享类型字典'),
(0, '分享范围', 'doc_share_scope', 1, 'system', NOW(), '分享范围字典'),
(0, '关联类型', 'doc_relation_type', 1, 'system', NOW(), '文档关联类型字典'),
(0, '模板类型', 'doc_template_type', 1, 'system', NOW(), '文档模板类型字典'),
(0, '审核类型', 'doc_review_type', 1, 'system', NOW(), '文档审核类型字典'),
(0, '审核阶段', 'doc_review_stage', 1, 'system', NOW(), '文档审核阶段字典'),
(0, '审核状态', 'doc_review_status', 1, 'system', NOW(), '文档审核状态字典'),
(0, '操作类型', 'doc_operation_type', 1, 'system', NOW(), '文档操作类型字典'),
(0, '变更类型', 'doc_change_type', 1, 'system', NOW(), '文档变更类型字典')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- ======================= 系统字典数据 =======================

-- 文档类型
INSERT INTO sys_dict_data (tenant_id, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(0, 1, '合同文档', '1', 'doc_document_type', '', 'primary', 1, 1, 'system', NOW(), '合同相关文档'),
(0, 2, '案件文档', '2', 'doc_document_type', '', 'info', 0, 1, 'system', NOW(), '案件相关文档'),
(0, 3, '法律意见', '3', 'doc_document_type', '', 'success', 0, 1, 'system', NOW(), '法律意见书'),
(0, 4, '备忘录', '4', 'doc_document_type', '', 'warning', 0, 1, 'system', NOW(), '内部备忘录'),
(0, 5, '模板文档', '5', 'doc_document_type', '', 'danger', 0, 1, 'system', NOW(), '文档模板'),
(0, 6, '其他文档', '6', 'doc_document_type', '', 'default', 0, 1, 'system', NOW(), '其他类型文档');

-- 文档状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '草稿', '1', 'doc_status', '', 'warning', 1, 1, 'system', NOW(), '文档草稿状态'),
(2, '审核中', '2', 'doc_status', '', 'info', 0, 1, 'system', NOW(), '文档审核中'),
(3, '已发布', '3', 'doc_status', '', 'success', 0, 1, 'system', NOW(), '文档已发布'),
(4, '已归档', '4', 'doc_status', '', 'primary', 0, 1, 'system', NOW(), '文档已归档'),
(5, '已作废', '5', 'doc_status', '', 'danger', 0, 1, 'system', NOW(), '文档已作废');

-- 安全级别
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '公开', '1', 'doc_security_level', '', 'success', 1, 1, 'system', NOW(), '公开级别'),
(2, '内部', '2', 'doc_security_level', '', 'info', 0, 1, 'system', NOW(), '内部级别'),
(3, '机密', '3', 'doc_security_level', '', 'warning', 0, 1, 'system', NOW(), '机密级别'),
(4, '绝密', '4', 'doc_security_level', '', 'danger', 0, 1, 'system', NOW(), '绝密级别');

-- 访问权限
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '私有', '1', 'doc_access_level', '', 'danger', 1, 1, 'system', NOW(), '私有访问'),
(2, '部门内', '2', 'doc_access_level', '', 'warning', 0, 1, 'system', NOW(), '部门内访问'),
(3, '公司内', '3', 'doc_access_level', '', 'info', 0, 1, 'system', NOW(), '公司内访问'),
(4, '公开', '4', 'doc_access_level', '', 'success', 0, 1, 'system', NOW(), '公开访问');

-- 存储类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '本地存储', '1', 'doc_storage_type', '', 'primary', 1, 1, 'system', NOW(), '本地文件系统'),
(2, '云存储', '2', 'doc_storage_type', '', 'success', 0, 1, 'system', NOW(), '云对象存储'),
(3, 'NAS存储', '3', 'doc_storage_type', '', 'info', 0, 1, 'system', NOW(), '网络附加存储');

-- 内容类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '文本内容', '1', 'doc_content_type', '', 'primary', 1, 1, 'system', NOW(), '纯文本内容'),
(2, 'HTML内容', '2', 'doc_content_type', '', 'info', 0, 1, 'system', NOW(), 'HTML格式内容'),
(3, '原始文件', '3', 'doc_content_type', '', 'success', 0, 1, 'system', NOW(), '原始文件内容'),
(4, '预览图', '4', 'doc_content_type', '', 'warning', 0, 1, 'system', NOW(), '预览图片');

-- 附件类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '相关文件', '1', 'doc_attachment_type', '', 'primary', 1, 1, 'system', NOW(), '相关文件'),
(2, '签名页', '2', 'doc_attachment_type', '', 'success', 0, 1, 'system', NOW(), '签名页文件'),
(3, '附录', '3', 'doc_attachment_type', '', 'info', 0, 1, 'system', NOW(), '附录文件'),
(4, '参考资料', '4', 'doc_attachment_type', '', 'warning', 0, 1, 'system', NOW(), '参考资料');

-- 标签类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '业务标签', '1', 'doc_tag_type', '', 'primary', 1, 1, 'system', NOW(), '业务相关标签'),
(2, '状态标签', '2', 'doc_tag_type', '', 'info', 0, 1, 'system', NOW(), '状态相关标签'),
(3, '优先级标签', '3', 'doc_tag_type', '', 'warning', 0, 1, 'system', NOW(), '优先级标签'),
(4, '自定义标签', '4', 'doc_tag_type', '', 'success', 0, 1, 'system', NOW(), '自定义标签');

-- 版本类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '主版本', '1', 'doc_version_type', '', 'danger', 1, 1, 'system', NOW(), '主要版本更新'),
(2, '次版本', '2', 'doc_version_type', '', 'warning', 0, 1, 'system', NOW(), '次要版本更新'),
(3, '修订版', '3', 'doc_version_type', '', 'info', 0, 1, 'system', NOW(), '修订版本更新');

-- 版本状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '草稿', '1', 'doc_version_status', '', 'warning', 1, 1, 'system', NOW(), '版本草稿'),
(2, '发布', '2', 'doc_version_status', '', 'success', 0, 1, 'system', NOW(), '版本已发布'),
(3, '归档', '3', 'doc_version_status', '', 'primary', 0, 1, 'system', NOW(), '版本已归档'),
(4, '废弃', '4', 'doc_version_status', '', 'danger', 0, 1, 'system', NOW(), '版本已废弃');

-- 权限主体类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '用户', '1', 'doc_subject_type', '', 'primary', 1, 1, 'system', NOW(), '用户权限'),
(2, '角色', '2', 'doc_subject_type', '', 'success', 0, 1, 'system', NOW(), '角色权限'),
(3, '部门', '3', 'doc_subject_type', '', 'info', 0, 1, 'system', NOW(), '部门权限');

-- 权限类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '查看', '1', 'doc_permission_type', '', 'info', 1, 1, 'system', NOW(), '查看权限'),
(2, '编辑', '2', 'doc_permission_type', '', 'warning', 0, 1, 'system', NOW(), '编辑权限'),
(3, '下载', '3', 'doc_permission_type', '', 'success', 0, 1, 'system', NOW(), '下载权限'),
(4, '分享', '4', 'doc_permission_type', '', 'primary', 0, 1, 'system', NOW(), '分享权限'),
(5, '删除', '5', 'doc_permission_type', '', 'danger', 0, 1, 'system', NOW(), '删除权限'),
(6, '管理', '6', 'doc_permission_type', '', 'dark', 0, 1, 'system', NOW(), '管理权限');

-- 授权来源
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '直接授权', '1', 'doc_grant_source', '', 'primary', 1, 1, 'system', NOW(), '直接授权'),
(2, '继承授权', '2', 'doc_grant_source', '', 'info', 0, 1, 'system', NOW(), '继承授权'),
(3, '分享授权', '3', 'doc_grant_source', '', 'success', 0, 1, 'system', NOW(), '分享授权');

-- 分享类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '链接分享', '1', 'doc_share_type', '', 'primary', 1, 1, 'system', NOW(), '链接分享'),
(2, '密码分享', '2', 'doc_share_type', '', 'warning', 0, 1, 'system', NOW(), '密码分享'),
(3, '邀请分享', '3', 'doc_share_type', '', 'success', 0, 1, 'system', NOW(), '邀请分享');

-- 分享范围
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '公开', '1', 'doc_share_scope', '', 'success', 1, 1, 'system', NOW(), '公开分享'),
(2, '内部', '2', 'doc_share_scope', '', 'info', 0, 1, 'system', NOW(), '内部分享'),
(3, '指定用户', '3', 'doc_share_scope', '', 'warning', 0, 1, 'system', NOW(), '指定用户分享');

-- 模板类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '合同模板', '1', 'doc_template_type', '', 'primary', 1, 1, 'system', NOW(), '合同模板'),
(2, '法律文书', '2', 'doc_template_type', '', 'info', 0, 1, 'system', NOW(), '法律文书模板'),
(3, '意见书', '3', 'doc_template_type', '', 'success', 0, 1, 'system', NOW(), '意见书模板'),
(4, '备忘录', '4', 'doc_template_type', '', 'warning', 0, 1, 'system', NOW(), '备忘录模板'),
(5, '其他', '5', 'doc_template_type', '', 'default', 0, 1, 'system', NOW(), '其他模板');

-- 审核类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '内容审核', '1', 'doc_review_type', '', 'primary', 1, 1, 'system', NOW(), '内容审核'),
(2, '合规审核', '2', 'doc_review_type', '', 'warning', 0, 1, 'system', NOW(), '合规审核'),
(3, '质量审核', '3', 'doc_review_type', '', 'info', 0, 1, 'system', NOW(), '质量审核'),
(4, '发布审核', '4', 'doc_review_type', '', 'success', 0, 1, 'system', NOW(), '发布审核');

-- 审核阶段
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '初审', '1', 'doc_review_stage', '', 'info', 1, 1, 'system', NOW(), '初次审核'),
(2, '复审', '2', 'doc_review_stage', '', 'warning', 0, 1, 'system', NOW(), '复次审核'),
(3, '终审', '3', 'doc_review_stage', '', 'danger', 0, 1, 'system', NOW(), '最终审核');

-- 审核状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '待审核', '1', 'doc_review_status', '', 'warning', 1, 1, 'system', NOW(), '待审核'),
(2, '审核通过', '2', 'doc_review_status', '', 'success', 0, 1, 'system', NOW(), '审核通过'),
(3, '审核拒绝', '3', 'doc_review_status', '', 'danger', 0, 1, 'system', NOW(), '审核拒绝'),
(4, '需要修改', '4', 'doc_review_status', '', 'info', 0, 1, 'system', NOW(), '需要修改');

-- 操作类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '创建', '1', 'doc_operation_type', '', 'success', 1, 1, 'system', NOW(), '创建文档'),
(2, '查看', '2', 'doc_operation_type', '', 'info', 0, 1, 'system', NOW(), '查看文档'),
(3, '下载', '3', 'doc_operation_type', '', 'primary', 0, 1, 'system', NOW(), '下载文档'),
(4, '编辑', '4', 'doc_operation_type', '', 'warning', 0, 1, 'system', NOW(), '编辑文档'),
(5, '删除', '5', 'doc_operation_type', '', 'danger', 0, 1, 'system', NOW(), '删除文档'),
(6, '分享', '6', 'doc_operation_type', '', 'dark', 0, 1, 'system', NOW(), '分享文档'),
(7, '评论', '7', 'doc_operation_type', '', 'secondary', 0, 1, 'system', NOW(), '评论文档'),
(8, '收藏', '8', 'doc_operation_type', '', 'light', 0, 1, 'system', NOW(), '收藏文档');

-- 变更类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '新增', '1', 'doc_change_type', '', 'success', 1, 1, 'system', NOW(), '新增内容'),
(2, '修改', '2', 'doc_change_type', '', 'warning', 0, 1, 'system', NOW(), '修改内容'),
(3, '删除', '3', 'doc_change_type', '', 'danger', 0, 1, 'system', NOW(), '删除内容'),
(4, '合并', '4', 'doc_change_type', '', 'info', 0, 1, 'system', NOW(), '合并内容');

-- ======================= 文档分类初始化 =======================

-- 文档分类数据
INSERT INTO document_category (tenant_id, category_name, category_code, parent_id, level, category_path, description, sort_order, is_system, create_by, create_time) VALUES
-- 一级分类
(0, '合同文档', 'CONTRACT_DOCS', 0, 1, '/合同文档', '合同相关文档分类', 1, 1, 'system', NOW()),
(0, '案件文档', 'CASE_DOCS', 0, 1, '/案件文档', '案件相关文档分类', 2, 1, 'system', NOW()),
(0, '法律意见', 'LEGAL_OPINION', 0, 1, '/法律意见', '法律意见书分类', 3, 1, 'system', NOW()),
(0, '内部文档', 'INTERNAL_DOCS', 0, 1, '/内部文档', '内部文档分类', 4, 1, 'system', NOW()),
(0, '客户资料', 'CLIENT_DOCS', 0, 1, '/客户资料', '客户相关文档分类', 5, 1, 'system', NOW()),

-- 合同文档二级分类
(0, '服务合同', 'SERVICE_CONTRACT', 1, 2, '/合同文档/服务合同', '法律服务合同', 1, 1, 'system', NOW()),
(0, '劳动合同', 'LABOR_CONTRACT', 1, 2, '/合同文档/劳动合同', '劳动合同文档', 2, 1, 'system', NOW()),
(0, '买卖合同', 'SALES_CONTRACT', 1, 2, '/合同文档/买卖合同', '买卖合同文档', 3, 1, 'system', NOW()),
(0, '租赁合同', 'LEASE_CONTRACT', 1, 2, '/合同文档/租赁合同', '租赁合同文档', 4, 1, 'system', NOW()),

-- 案件文档二级分类
(0, '民事案件', 'CIVIL_CASE', 2, 2, '/案件文档/民事案件', '民事案件文档', 1, 1, 'system', NOW()),
(0, '刑事案件', 'CRIMINAL_CASE', 2, 2, '/案件文档/刑事案件', '刑事案件文档', 2, 1, 'system', NOW()),
(0, '行政案件', 'ADMINISTRATIVE_CASE', 2, 2, '/案件文档/行政案件', '行政案件文档', 3, 1, 'system', NOW()),
(0, '仲裁案件', 'ARBITRATION_CASE', 2, 2, '/案件文档/仲裁案件', '仲裁案件文档', 4, 1, 'system', NOW()),

-- 法律意见二级分类
(0, '合规意见', 'COMPLIANCE_OPINION', 3, 2, '/法律意见/合规意见', '合规法律意见', 1, 1, 'system', NOW()),
(0, '尽调意见', 'DUE_DILIGENCE_OPINION', 3, 2, '/法律意见/尽调意见', '尽职调查意见', 2, 1, 'system', NOW()),
(0, '风险评估', 'RISK_ASSESSMENT', 3, 2, '/法律意见/风险评估', '法律风险评估', 3, 1, 'system', NOW()),

-- 内部文档二级分类
(0, '管理制度', 'MANAGEMENT_SYSTEM', 4, 2, '/内部文档/管理制度', '内部管理制度', 1, 1, 'system', NOW()),
(0, '工作流程', 'WORK_PROCESS', 4, 2, '/内部文档/工作流程', '工作流程文档', 2, 1, 'system', NOW()),
(0, '培训资料', 'TRAINING_MATERIALS', 4, 2, '/内部文档/培训资料', '培训学习资料', 3, 1, 'system', NOW()),

-- 客户资料二级分类
(0, '企业客户', 'ENTERPRISE_CLIENT', 5, 2, '/客户资料/企业客户', '企业客户资料', 1, 1, 'system', NOW()),
(0, '个人客户', 'INDIVIDUAL_CLIENT', 5, 2, '/客户资料/个人客户', '个人客户资料', 2, 1, 'system', NOW()),
(0, '政府机构', 'GOVERNMENT_CLIENT', 5, 2, '/客户资料/政府机构', '政府机构客户', 3, 1, 'system', NOW());

-- ======================= 文档标签初始化 =======================

-- 业务标签
INSERT INTO document_tag (tenant_id, tag_name, tag_code, tag_type, tag_category, color, description, sort_order, is_system, create_by, create_time) VALUES
(0, '重要', 'IMPORTANT', 1, '优先级', '#ff4d4f', '重要文档', 1, 1, 'system', NOW()),
(0, '紧急', 'URGENT', 1, '优先级', '#ff7a45', '紧急文档', 2, 1, 'system', NOW()),
(0, '机密', 'CONFIDENTIAL', 1, '安全', '#722ed1', '机密文档', 3, 1, 'system', NOW()),
(0, '归档', 'ARCHIVED', 2, '状态', '#8c8c8c', '已归档文档', 4, 1, 'system', NOW()),
(0, '待审核', 'PENDING_REVIEW', 2, '状态', '#faad14', '待审核文档', 5, 1, 'system', NOW()),
(0, '已审核', 'REVIEWED', 2, '状态', '#52c41a', '已审核文档', 6, 1, 'system', NOW()),
(0, '模板', 'TEMPLATE', 1, '类型', '#1890ff', '模板文档', 7, 1, 'system', NOW()),
(0, '草稿', 'DRAFT', 2, '状态', '#d9d9d9', '草稿文档', 8, 1, 'system', NOW()),
(0, '最终版', 'FINAL', 2, '状态', '#13c2c2', '最终版本', 9, 1, 'system', NOW()),
(0, '需修改', 'NEED_REVISION', 2, '状态', '#fa541c', '需要修改', 10, 1, 'system', NOW());

-- ======================= 文档模板初始化 =======================

-- 基础文档模板
INSERT INTO document_template (tenant_id, template_name, template_code, template_type, template_category, template_content, version_number, creator_name, create_by, create_time) VALUES
(0, '法律服务合同模板', 'LEGAL_SERVICE_CONTRACT_TEMPLATE', 1, '服务合同', 
'法律服务合同

甲方：{client_name}
乙方：{law_firm_name}

根据《中华人民共和国合同法》及相关法律法规，甲乙双方本着平等、自愿、公平、诚实信用的原则，就法律服务事宜达成如下协议：

一、服务内容
{service_content}

二、服务期限
自 {start_date} 至 {end_date}

三、服务费用
{service_fee}

四、双方权利义务
{rights_obligations}

五、其他约定
{other_agreements}

甲方（盖章）：             乙方（盖章）：
法定代表人：               法定代表人：
签订日期：{sign_date}      签订日期：{sign_date}',
'1.0', 'system', 'system', NOW()),

(0, '律师函模板', 'LAWYER_LETTER_TEMPLATE', 2, '法律文书',
'律师函

{recipient_name}：

{law_firm_name}接受{client_name}的委托，指派本律师就{matter_description}事宜，致函贵方。

{main_content}

望贵方收函后，能够重视并积极回应上述事项。如有疑问，请及时联系我们。

此致
敬礼！

{law_firm_name}
律师：{lawyer_name}
执业证号：{license_number}
联系电话：{phone}
{date}',
'1.0', 'system', 'system', NOW()),

(0, '法律意见书模板', 'LEGAL_OPINION_TEMPLATE', 3, '意见书',
'法律意见书

委托方：{client_name}
出具方：{law_firm_name}
日期：{date}

一、基本情况
{basic_info}

二、审查范围
{review_scope}

三、法律依据
{legal_basis}

四、法律分析
{legal_analysis}

五、法律意见
{legal_opinion}

六、风险提示
{risk_warning}

特此出具法律意见书。

{law_firm_name}
律师：{lawyer_name}
{date}',
'1.0', 'system', 'system', NOW());

-- 初始化完成提示
SELECT '文档管理模块基础数据初始化完成' AS result;
SELECT CONCAT('已创建文档分类：', COUNT(*), '个') AS category_count FROM document_category WHERE is_system = 1;
SELECT CONCAT('已创建文档标签：', COUNT(*), '个') AS tag_count FROM document_tag WHERE is_system = 1;
SELECT CONCAT('已创建文档模板：', COUNT(*), '个') AS template_count FROM document_template; 
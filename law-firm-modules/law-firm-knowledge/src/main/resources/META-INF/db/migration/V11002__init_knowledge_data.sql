-- 知识管理模块基础数据
-- 版本: V11002
-- 模块: 知识管理模块 (V11000-V11999)
-- 创建时间: 2023-06-01
-- 说明: 知识管理功能的基础字典数据和初始化数据

-- 设置字符集
SET NAMES utf8mb4;

-- ======================= 系统字典类型 =======================

-- 知识管理相关字典类型
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) VALUES
('知识类型', 'knowledge_type', 1, 'system', NOW(), '知识文档类型字典'),
('内容类型', 'knowledge_content_type', 1, 'system', NOW(), '知识内容类型字典'),
('文档状态', 'knowledge_document_status', 1, 'system', NOW(), '知识文档状态字典'),
('可见性', 'knowledge_visibility', 1, 'system', NOW(), '知识可见性字典'),
('难度等级', 'knowledge_difficulty_level', 1, 'system', NOW(), '知识难度等级字典'),
('重要程度', 'knowledge_importance_level', 1, 'system', NOW(), '知识重要程度字典'),
('来源类型', 'knowledge_source_type', 1, 'system', NOW(), '知识来源类型字典'),
('标签类型', 'knowledge_tag_type', 1, 'system', NOW(), '知识标签类型字典'),
('关联类型', 'knowledge_relation_type', 1, 'system', NOW(), '知识标签关联类型字典'),
('评论状态', 'knowledge_comment_status', 1, 'system', NOW(), '知识评论状态字典'),
('收藏类型', 'knowledge_favorite_type', 1, 'system', NOW(), '知识收藏类型字典'),
('优先级', 'knowledge_priority', 1, 'system', NOW(), '知识优先级字典'),
('点赞类型', 'knowledge_like_type', 1, 'system', NOW(), '知识点赞类型字典'),
('浏览来源', 'knowledge_view_source', 1, 'system', NOW(), '知识浏览来源字典'),
('设备类型', 'knowledge_device_type', 1, 'system', NOW(), '访问设备类型字典'),
('存储类型', 'knowledge_storage_type', 1, 'system', NOW(), '知识存储类型字典');

-- ======================= 系统字典数据 =======================

-- 知识类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '法律法规', '1', 'knowledge_type', '', 'primary', 1, 1, 'system', NOW(), '法律法规类知识'),
(2, '案例分析', '2', 'knowledge_type', '', 'success', 0, 1, 'system', NOW(), '案例分析类知识'),
(3, '合同范本', '3', 'knowledge_type', '', 'info', 0, 1, 'system', NOW(), '合同范本类知识'),
(4, '法律文书', '4', 'knowledge_type', '', 'warning', 0, 1, 'system', NOW(), '法律文书类知识'),
(5, '法律研究', '5', 'knowledge_type', '', 'dark', 0, 1, 'system', NOW(), '法律研究类知识'),
(6, '业务知识', '6', 'knowledge_type', '', 'secondary', 0, 1, 'system', NOW(), '业务知识类知识'),
(7, '管理制度', '7', 'knowledge_type', '', 'danger', 0, 1, 'system', NOW(), '管理制度类知识');

-- 内容类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '文本', '1', 'knowledge_content_type', '', 'primary', 1, 1, 'system', NOW(), '纯文本内容'),
(2, '富文本', '2', 'knowledge_content_type', '', 'success', 0, 1, 'system', NOW(), '富文本内容'),
(3, 'Markdown', '3', 'knowledge_content_type', '', 'info', 0, 1, 'system', NOW(), 'Markdown格式'),
(4, 'PDF', '4', 'knowledge_content_type', '', 'warning', 0, 1, 'system', NOW(), 'PDF文档'),
(5, 'Word', '5', 'knowledge_content_type', '', 'dark', 0, 1, 'system', NOW(), 'Word文档'),
(6, 'PPT', '6', 'knowledge_content_type', '', 'secondary', 0, 1, 'system', NOW(), 'PPT演示文稿'),
(7, '视频', '7', 'knowledge_content_type', '', 'danger', 0, 1, 'system', NOW(), '视频内容'),
(8, '音频', '8', 'knowledge_content_type', '', 'light', 0, 1, 'system', NOW(), '音频内容');

-- 文档状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '草稿', '1', 'knowledge_document_status', '', 'secondary', 1, 1, 'system', NOW(), '文档草稿状态'),
(2, '待审核', '2', 'knowledge_document_status', '', 'warning', 0, 1, 'system', NOW(), '文档待审核状态'),
(3, '已发布', '3', 'knowledge_document_status', '', 'success', 0, 1, 'system', NOW(), '文档已发布状态'),
(4, '已归档', '4', 'knowledge_document_status', '', 'info', 0, 1, 'system', NOW(), '文档已归档状态'),
(5, '已下架', '5', 'knowledge_document_status', '', 'danger', 0, 1, 'system', NOW(), '文档已下架状态');

-- 可见性
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '公开', '1', 'knowledge_visibility', '', 'success', 1, 1, 'system', NOW(), '所有人可见'),
(2, '部门', '2', 'knowledge_visibility', '', 'primary', 0, 1, 'system', NOW(), '部门内可见'),
(3, '岗位', '3', 'knowledge_visibility', '', 'info', 0, 1, 'system', NOW(), '特定岗位可见'),
(4, '私有', '4', 'knowledge_visibility', '', 'warning', 0, 1, 'system', NOW(), '仅创建者可见');

-- 难度等级
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '入门', '1', 'knowledge_difficulty_level', '', 'success', 1, 1, 'system', NOW(), '入门级别'),
(2, '初级', '2', 'knowledge_difficulty_level', '', 'primary', 0, 1, 'system', NOW(), '初级级别'),
(3, '中级', '3', 'knowledge_difficulty_level', '', 'info', 0, 1, 'system', NOW(), '中级级别'),
(4, '高级', '4', 'knowledge_difficulty_level', '', 'warning', 0, 1, 'system', NOW(), '高级级别'),
(5, '专家', '5', 'knowledge_difficulty_level', '', 'danger', 0, 1, 'system', NOW(), '专家级别');

-- 重要程度
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '一般', '1', 'knowledge_importance_level', '', 'secondary', 1, 1, 'system', NOW(), '一般重要'),
(2, '重要', '2', 'knowledge_importance_level', '', 'primary', 0, 1, 'system', NOW(), '重要'),
(3, '核心', '3', 'knowledge_importance_level', '', 'info', 0, 1, 'system', NOW(), '核心重要'),
(4, '关键', '4', 'knowledge_importance_level', '', 'warning', 0, 1, 'system', NOW(), '关键重要'),
(5, '战略', '5', 'knowledge_importance_level', '', 'danger', 0, 1, 'system', NOW(), '战略重要');

-- 来源类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '原创', '1', 'knowledge_source_type', '', 'primary', 1, 1, 'system', NOW(), '原创内容'),
(2, '整理', '2', 'knowledge_source_type', '', 'success', 0, 1, 'system', NOW(), '整理内容'),
(3, '转载', '3', 'knowledge_source_type', '', 'info', 0, 1, 'system', NOW(), '转载内容'),
(4, '翻译', '4', 'knowledge_source_type', '', 'warning', 0, 1, 'system', NOW(), '翻译内容');

-- 标签类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '法律领域', '1', 'knowledge_tag_type', '', 'primary', 1, 1, 'system', NOW(), '法律领域标签'),
(2, '业务类型', '2', 'knowledge_tag_type', '', 'success', 0, 1, 'system', NOW(), '业务类型标签'),
(3, '重要程度', '3', 'knowledge_tag_type', '', 'info', 0, 1, 'system', NOW(), '重要程度标签'),
(4, '适用范围', '4', 'knowledge_tag_type', '', 'warning', 0, 1, 'system', NOW(), '适用范围标签'),
(5, '自定义', '5', 'knowledge_tag_type', '', 'dark', 0, 1, 'system', NOW(), '自定义标签');

-- 关联类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '主标签', '1', 'knowledge_relation_type', '', 'primary', 1, 1, 'system', NOW(), '主要标签'),
(2, '辅助标签', '2', 'knowledge_relation_type', '', 'secondary', 0, 1, 'system', NOW(), '辅助标签'),
(3, '自动标签', '3', 'knowledge_relation_type', '', 'info', 0, 1, 'system', NOW(), '自动标签');

-- 评论状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '正常', '1', 'knowledge_comment_status', '', 'success', 1, 1, 'system', NOW(), '评论正常状态'),
(2, '待审核', '2', 'knowledge_comment_status', '', 'warning', 0, 1, 'system', NOW(), '评论待审核状态'),
(3, '已屏蔽', '3', 'knowledge_comment_status', '', 'danger', 0, 1, 'system', NOW(), '评论已屏蔽状态'),
(4, '已删除', '4', 'knowledge_comment_status', '', 'dark', 0, 1, 'system', NOW(), '评论已删除状态');

-- 收藏类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '个人收藏', '1', 'knowledge_favorite_type', '', 'primary', 1, 1, 'system', NOW(), '个人收藏'),
(2, '共享收藏', '2', 'knowledge_favorite_type', '', 'success', 0, 1, 'system', NOW(), '共享收藏');

-- 优先级
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '一般', '1', 'knowledge_priority', '', 'secondary', 1, 1, 'system', NOW(), '一般优先级'),
(2, '重要', '2', 'knowledge_priority', '', 'primary', 0, 1, 'system', NOW(), '重要优先级'),
(3, '紧急', '3', 'knowledge_priority', '', 'danger', 0, 1, 'system', NOW(), '紧急优先级');

-- 点赞类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '点赞', '1', 'knowledge_like_type', '', 'success', 1, 1, 'system', NOW(), '点赞'),
(2, '点踩', '2', 'knowledge_like_type', '', 'danger', 0, 1, 'system', NOW(), '点踩');

-- 浏览来源
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '直接访问', '1', 'knowledge_view_source', '', 'primary', 1, 1, 'system', NOW(), '直接访问'),
(2, '搜索', '2', 'knowledge_view_source', '', 'success', 0, 1, 'system', NOW(), '搜索访问'),
(3, '推荐', '3', 'knowledge_view_source', '', 'info', 0, 1, 'system', NOW(), '推荐访问'),
(4, '分享', '4', 'knowledge_view_source', '', 'warning', 0, 1, 'system', NOW(), '分享访问');

-- 设备类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, 'PC', '1', 'knowledge_device_type', '', 'primary', 1, 1, 'system', NOW(), 'PC端'),
(2, '移动端', '2', 'knowledge_device_type', '', 'success', 0, 1, 'system', NOW(), '移动端'),
(3, '平板', '3', 'knowledge_device_type', '', 'info', 0, 1, 'system', NOW(), '平板端');

-- 存储类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '本地', '1', 'knowledge_storage_type', '', 'primary', 1, 1, 'system', NOW(), '本地存储'),
(2, 'OSS', '2', 'knowledge_storage_type', '', 'success', 0, 1, 'system', NOW(), '对象存储'),
(3, 'CDN', '3', 'knowledge_storage_type', '', 'info', 0, 1, 'system', NOW(), 'CDN存储');

-- ======================= 知识分类数据 =======================

-- 初始化知识分类（一级分类）
INSERT INTO knowledge_category (tenant_id, category_name, category_code, parent_id, level, category_path, description, icon, sort_order, is_system, is_enabled, create_by, create_time, status) VALUES
(0, '法律法规', 'LAW_REGULATION', 0, 1, '/LAW_REGULATION', '法律法规类知识分类', 'law', 1, 1, 1, 'system', NOW(), 1),
(0, '案例分析', 'CASE_ANALYSIS', 0, 1, '/CASE_ANALYSIS', '案例分析类知识分类', 'case', 2, 1, 1, 'system', NOW(), 1),
(0, '合同范本', 'CONTRACT_TEMPLATE', 0, 1, '/CONTRACT_TEMPLATE', '合同范本类知识分类', 'contract', 3, 1, 1, 'system', NOW(), 1),
(0, '法律文书', 'LEGAL_DOCUMENT', 0, 1, '/LEGAL_DOCUMENT', '法律文书类知识分类', 'document', 4, 1, 1, 'system', NOW(), 1),
(0, '法律研究', 'LEGAL_RESEARCH', 0, 1, '/LEGAL_RESEARCH', '法律研究类知识分类', 'research', 5, 1, 1, 'system', NOW(), 1),
(0, '业务知识', 'BUSINESS_KNOWLEDGE', 0, 1, '/BUSINESS_KNOWLEDGE', '业务知识类知识分类', 'business', 6, 1, 1, 'system', NOW(), 1),
(0, '管理制度', 'MANAGEMENT_SYSTEM', 0, 1, '/MANAGEMENT_SYSTEM', '管理制度类知识分类', 'management', 7, 1, 1, 'system', NOW(), 1);

-- 初始化知识分类（二级分类 - 法律法规）
INSERT INTO knowledge_category (tenant_id, category_name, category_code, parent_id, level, category_path, description, sort_order, is_system, is_enabled, create_by, create_time, status) VALUES
(0, '宪法', 'CONSTITUTIONAL_LAW', 1, 2, '/LAW_REGULATION/CONSTITUTIONAL_LAW', '宪法相关法规', 1, 1, 1, 'system', NOW(), 1),
(0, '民商法', 'CIVIL_COMMERCIAL_LAW', 1, 2, '/LAW_REGULATION/CIVIL_COMMERCIAL_LAW', '民商法相关法规', 2, 1, 1, 'system', NOW(), 1),
(0, '刑法', 'CRIMINAL_LAW', 1, 2, '/LAW_REGULATION/CRIMINAL_LAW', '刑法相关法规', 3, 1, 1, 'system', NOW(), 1),
(0, '行政法', 'ADMINISTRATIVE_LAW', 1, 2, '/LAW_REGULATION/ADMINISTRATIVE_LAW', '行政法相关法规', 4, 1, 1, 'system', NOW(), 1),
(0, '经济法', 'ECONOMIC_LAW', 1, 2, '/LAW_REGULATION/ECONOMIC_LAW', '经济法相关法规', 5, 1, 1, 'system', NOW(), 1),
(0, '社会法', 'SOCIAL_LAW', 1, 2, '/LAW_REGULATION/SOCIAL_LAW', '社会法相关法规', 6, 1, 1, 'system', NOW(), 1),
(0, '诉讼与非诉讼程序法', 'PROCEDURAL_LAW', 1, 2, '/LAW_REGULATION/PROCEDURAL_LAW', '诉讼程序法相关法规', 7, 1, 1, 'system', NOW(), 1);

-- 初始化知识分类（二级分类 - 案例分析）
INSERT INTO knowledge_category (tenant_id, category_name, category_code, parent_id, level, category_path, description, sort_order, is_system, is_enabled, create_by, create_time, status) VALUES
(0, '民事案例', 'CIVIL_CASE', 2, 2, '/CASE_ANALYSIS/CIVIL_CASE', '民事案例分析', 1, 1, 1, 'system', NOW(), 1),
(0, '刑事案例', 'CRIMINAL_CASE', 2, 2, '/CASE_ANALYSIS/CRIMINAL_CASE', '刑事案例分析', 2, 1, 1, 'system', NOW(), 1),
(0, '行政案例', 'ADMINISTRATIVE_CASE', 2, 2, '/CASE_ANALYSIS/ADMINISTRATIVE_CASE', '行政案例分析', 3, 1, 1, 'system', NOW(), 1),
(0, '典型案例', 'TYPICAL_CASE', 2, 2, '/CASE_ANALYSIS/TYPICAL_CASE', '典型案例分析', 4, 1, 1, 'system', NOW(), 1),
(0, '疑难案例', 'DIFFICULT_CASE', 2, 2, '/CASE_ANALYSIS/DIFFICULT_CASE', '疑难案例分析', 5, 1, 1, 'system', NOW(), 1);

-- 初始化知识分类（二级分类 - 合同范本）
INSERT INTO knowledge_category (tenant_id, category_name, category_code, parent_id, level, category_path, description, sort_order, is_system, is_enabled, create_by, create_time, status) VALUES
(0, '买卖合同', 'SALES_CONTRACT', 3, 2, '/CONTRACT_TEMPLATE/SALES_CONTRACT', '买卖合同范本', 1, 1, 1, 'system', NOW(), 1),
(0, '租赁合同', 'LEASE_CONTRACT', 3, 2, '/CONTRACT_TEMPLATE/LEASE_CONTRACT', '租赁合同范本', 2, 1, 1, 'system', NOW(), 1),
(0, '劳动合同', 'LABOR_CONTRACT', 3, 2, '/CONTRACT_TEMPLATE/LABOR_CONTRACT', '劳动合同范本', 3, 1, 1, 'system', NOW(), 1),
(0, '建设工程合同', 'CONSTRUCTION_CONTRACT', 3, 2, '/CONTRACT_TEMPLATE/CONSTRUCTION_CONTRACT', '建设工程合同范本', 4, 1, 1, 'system', NOW(), 1),
(0, '技术合同', 'TECHNOLOGY_CONTRACT', 3, 2, '/CONTRACT_TEMPLATE/TECHNOLOGY_CONTRACT', '技术合同范本', 5, 1, 1, 'system', NOW(), 1),
(0, '金融合同', 'FINANCIAL_CONTRACT', 3, 2, '/CONTRACT_TEMPLATE/FINANCIAL_CONTRACT', '金融合同范本', 6, 1, 1, 'system', NOW(), 1),
(0, '其他合同', 'OTHER_CONTRACT', 3, 2, '/CONTRACT_TEMPLATE/OTHER_CONTRACT', '其他合同范本', 7, 1, 1, 'system', NOW(), 1);

-- 初始化知识分类（二级分类 - 法律文书）
INSERT INTO knowledge_category (tenant_id, category_name, category_code, parent_id, level, category_path, description, sort_order, is_system, is_enabled, create_by, create_time, status) VALUES
(0, '诉讼文书', 'LITIGATION_DOCUMENT', 4, 2, '/LEGAL_DOCUMENT/LITIGATION_DOCUMENT', '诉讼文书模板', 1, 1, 1, 'system', NOW(), 1),
(0, '非诉文书', 'NON_LITIGATION_DOCUMENT', 4, 2, '/LEGAL_DOCUMENT/NON_LITIGATION_DOCUMENT', '非诉讼文书模板', 2, 1, 1, 'system', NOW(), 1),
(0, '仲裁文书', 'ARBITRATION_DOCUMENT', 4, 2, '/LEGAL_DOCUMENT/ARBITRATION_DOCUMENT', '仲裁文书模板', 3, 1, 1, 'system', NOW(), 1),
(0, '公证文书', 'NOTARY_DOCUMENT', 4, 2, '/LEGAL_DOCUMENT/NOTARY_DOCUMENT', '公证文书模板', 4, 1, 1, 'system', NOW(), 1);

-- ======================= 知识标签数据 =======================

-- 初始化知识标签（法律领域标签）
INSERT INTO knowledge_tag (tenant_id, tag_name, tag_code, tag_type, tag_color, sort_order, is_system, creator_name, description, create_by, create_time, status) VALUES
(0, '民法典', 'CIVIL_CODE', 1, '#007bff', 1, 1, 'system', '民法典相关知识', 'system', NOW(), 1),
(0, '公司法', 'COMPANY_LAW', 1, '#28a745', 2, 1, 'system', '公司法相关知识', 'system', NOW(), 1),
(0, '合同法', 'CONTRACT_LAW', 1, '#17a2b8', 3, 1, 'system', '合同法相关知识', 'system', NOW(), 1),
(0, '证券法', 'SECURITIES_LAW', 1, '#ffc107', 4, 1, 'system', '证券法相关知识', 'system', NOW(), 1),
(0, '劳动法', 'LABOR_LAW', 1, '#dc3545', 5, 1, 'system', '劳动法相关知识', 'system', NOW(), 1),
(0, '知识产权', 'IP_LAW', 1, '#6f42c1', 6, 1, 'system', '知识产权法相关知识', 'system', NOW(), 1),
(0, '房地产', 'REAL_ESTATE', 1, '#fd7e14', 7, 1, 'system', '房地产法相关知识', 'system', NOW(), 1),
(0, '金融保险', 'FINANCE_INSURANCE', 1, '#20c997', 8, 1, 'system', '金融保险法相关知识', 'system', NOW(), 1),
(0, '环境保护', 'ENVIRONMENTAL', 1, '#198754', 9, 1, 'system', '环境保护法相关知识', 'system', NOW(), 1),
(0, '税收法规', 'TAX_LAW', 1, '#6610f2', 10, 1, 'system', '税收法规相关知识', 'system', NOW(), 1);

-- 初始化知识标签（业务类型标签）
INSERT INTO knowledge_tag (tenant_id, tag_name, tag_code, tag_type, tag_color, sort_order, is_system, creator_name, description, create_by, create_time, status) VALUES
(0, '诉讼业务', 'LITIGATION_BUSINESS', 2, '#007bff', 11, 1, 'system', '诉讼业务相关知识', 'system', NOW(), 1),
(0, '非诉业务', 'NON_LITIGATION_BUSINESS', 2, '#28a745', 12, 1, 'system', '非诉讼业务相关知识', 'system', NOW(), 1),
(0, '顾问业务', 'CONSULTANT_BUSINESS', 2, '#17a2b8', 13, 1, 'system', '法律顾问业务相关知识', 'system', NOW(), 1),
(0, '专项服务', 'SPECIAL_SERVICE', 2, '#ffc107', 14, 1, 'system', '专项法律服务相关知识', 'system', NOW(), 1),
(0, '尽职调查', 'DUE_DILIGENCE', 2, '#dc3545', 15, 1, 'system', '尽职调查相关知识', 'system', NOW(), 1);

-- 初始化知识标签（重要程度标签）
INSERT INTO knowledge_tag (tenant_id, tag_name, tag_code, tag_type, tag_color, sort_order, is_system, creator_name, description, create_by, create_time, status) VALUES
(0, '核心知识', 'CORE_KNOWLEDGE', 3, '#dc3545', 16, 1, 'system', '核心重要知识', 'system', NOW(), 1),
(0, '重要知识', 'IMPORTANT_KNOWLEDGE', 3, '#ffc107', 17, 1, 'system', '重要知识', 'system', NOW(), 1),
(0, '一般知识', 'GENERAL_KNOWLEDGE', 3, '#17a2b8', 18, 1, 'system', '一般知识', 'system', NOW(), 1),
(0, '参考知识', 'REFERENCE_KNOWLEDGE', 3, '#6c757d', 19, 1, 'system', '参考知识', 'system', NOW(), 1);

-- 初始化知识标签（适用范围标签）
INSERT INTO knowledge_tag (tenant_id, tag_name, tag_code, tag_type, tag_color, sort_order, is_system, creator_name, description, create_by, create_time, status) VALUES
(0, '全所适用', 'FIRM_WIDE', 4, '#28a745', 20, 1, 'system', '全所人员适用', 'system', NOW(), 1),
(0, '部门适用', 'DEPARTMENT_SPECIFIC', 4, '#007bff', 21, 1, 'system', '特定部门适用', 'system', NOW(), 1),
(0, '专业适用', 'PROFESSIONAL_SPECIFIC', 4, '#6f42c1', 22, 1, 'system', '特定专业适用', 'system', NOW(), 1),
(0, '个人适用', 'PERSONAL_SPECIFIC', 4, '#fd7e14', 23, 1, 'system', '个人专用', 'system', NOW(), 1);

-- 初始化完成提示
SELECT '知识管理模块基础数据初始化完成' AS result;
SELECT CONCAT('已创建字典类型：', COUNT(*), '个') AS dict_type_count FROM sys_dict_type WHERE dict_type LIKE 'knowledge_%';
SELECT CONCAT('已创建字典数据：', COUNT(*), '个') AS dict_data_count FROM sys_dict_data WHERE dict_type LIKE 'knowledge_%';
SELECT CONCAT('已创建知识分类：', COUNT(*), '个') AS category_count FROM knowledge_category WHERE tenant_id = 0;
SELECT CONCAT('已创建知识标签：', COUNT(*), '个') AS tag_count FROM knowledge_tag WHERE tenant_id = 0; 
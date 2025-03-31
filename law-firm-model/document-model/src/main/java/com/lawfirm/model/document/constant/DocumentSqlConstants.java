package com.lawfirm.model.document.constant;

/**
 * 文档模块SQL常量类
 * 集中管理文档相关SQL查询语句，提高可维护性和安全性
 */
public class DocumentSqlConstants {
    
    /**
     * 根据文件类型查询文档
     */
    public static final String SELECT_BY_FILE_TYPE = 
            "SELECT * FROM doc_document WHERE file_type = #{fileType} AND deleted = 0";
    
    /**
     * 根据文档标题模糊查询
     * 注意：调用前应对keyword参数进行验证，防止SQL注入
     */
    public static final String SELECT_BY_TITLE_KEYWORD = 
            "SELECT * FROM doc_document WHERE title LIKE CONCAT('%', #{keyword}, '%') AND deleted = 0";
    
    /**
     * 查询最近创建的文档列表
     */
    public static final String SELECT_RECENT_DOCUMENTS = 
            "SELECT * FROM doc_document WHERE deleted = 0 ORDER BY create_time DESC LIMIT #{limit}";
    
    /**
     * 根据文档ID批量查询
     */
    public static final String SELECT_BY_IDS = 
            "SELECT * FROM doc_document WHERE id IN " +
            "<foreach item='item' collection='ids' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach> " +
            "AND deleted = 0";
    
    /**
     * 文档权限相关SQL常量
     */
    public static class Permission {
        /**
         * 根据文档ID查询权限列表
         */
        public static final String FIND_BY_DOCUMENT_ID = 
                "SELECT * FROM doc_permission WHERE document_id = #{documentId} AND is_enabled = 1";
        
        /**
         * 根据文档ID删除权限
         */
        public static final String DELETE_BY_DOCUMENT_ID = 
                "DELETE FROM doc_permission WHERE document_id = #{documentId}";
        
        /**
         * 根据文档ID查询文档
         */
        public static final String FIND_DOCUMENT_BY_ID = 
                "SELECT * FROM doc_base WHERE id = #{documentId}";
        
        /**
         * 统计用户对文档的权限数
         */
        public static final String COUNT_BY_DOCUMENT_AND_USER = 
                "SELECT COUNT(*) FROM doc_permission WHERE document_id = #{documentId} AND subject_type = 'USER' AND subject_id = #{userId} AND is_enabled = 1";
    }
    
    /**
     * 文档标签相关SQL常量
     */
    public static class Tag {
        /**
         * 根据标签名称查询
         */
        public static final String SELECT_BY_NAME = 
                "SELECT * FROM doc_tag WHERE tag_name = #{tagName} AND deleted = 0";
        
        /**
         * 查询热门标签
         */
        public static final String SELECT_HOT_TAGS = 
                "SELECT t.*, COUNT(r.document_id) as doc_count FROM doc_tag t " +
                "INNER JOIN doc_tag_rel r ON t.id = r.tag_id " +
                "WHERE t.deleted = 0 " +
                "GROUP BY t.id ORDER BY doc_count DESC LIMIT #{limit}";
    }
    
    /**
     * 文档标签关联相关SQL常量
     */
    public static class TagRel {
        /**
         * 根据文档ID查询标签关联
         */
        public static final String SELECT_BY_DOCUMENT_ID = 
                "SELECT * FROM doc_tag_rel WHERE document_id = #{documentId}";
        
        /**
         * 根据标签ID查询文档ID列表
         */
        public static final String SELECT_DOCUMENT_IDS_BY_TAG_ID = 
                "SELECT document_id FROM doc_tag_rel WHERE tag_id = #{tagId}";
        
        /**
         * 删除文档的标签关联
         */
        public static final String DELETE_BY_DOCUMENT_ID = 
                "DELETE FROM doc_tag_rel WHERE document_id = #{documentId}";
    }
    
    /**
     * 文档分类相关SQL常量
     */
    public static class Category {
        /**
         * 根据父类ID查询子分类
         */
        public static final String SELECT_BY_PARENT_ID = 
                "SELECT * FROM doc_category WHERE parent_id = #{parentId} AND deleted = 0 ORDER BY sort_order ASC";
        
        /**
         * 查询分类下的文档数量
         */
        public static final String COUNT_DOCUMENTS_BY_CATEGORY_ID = 
                "SELECT COUNT(*) FROM doc_document WHERE category_id = #{categoryId} AND deleted = 0";
                
        /**
         * 查询根分类
         */
        public static final String SELECT_ROOT_CATEGORIES = 
                "SELECT * FROM doc_category WHERE parent_id = 0 AND deleted = 0 ORDER BY sort_order ASC";
    }
    
    /**
     * 案件文档相关SQL常量
     */
    public static class Case {
        /**
         * 根据案件ID查询文档
         */
        public static final String SELECT_BY_CASE_ID = 
                "SELECT d.* FROM doc_document d " +
                "INNER JOIN doc_case c ON d.id = c.document_id " +
                "WHERE c.case_id = #{caseId} AND d.deleted = 0 ORDER BY d.create_time DESC";
                
        /**
         * 根据案件ID和文档类型查询
         */
        public static final String SELECT_BY_CASE_ID_AND_TYPE = 
                "SELECT d.* FROM doc_document d " +
                "INNER JOIN doc_case c ON d.id = c.document_id " +
                "WHERE c.case_id = #{caseId} AND d.document_type = #{documentType} AND d.deleted = 0";
    }
    
    /**
     * 合同文档相关SQL常量
     */
    public static class Contract {
        /**
         * 根据合同ID查询文档
         */
        public static final String SELECT_BY_CONTRACT_ID = 
                "SELECT d.* FROM doc_document d " +
                "INNER JOIN doc_contract c ON d.id = c.document_id " +
                "WHERE c.contract_id = #{contractId} AND d.deleted = 0";
    }
    
    /**
     * 模板文档相关SQL常量
     */
    public static class Template {
        /**
         * 查询分类下的模板文档
         */
        public static final String SELECT_BY_CATEGORY_ID = 
                "SELECT * FROM doc_template WHERE category_id = #{categoryId} AND deleted = 0 ORDER BY create_time DESC";
                
        /**
         * 根据模板编码查询
         */
        public static final String SELECT_BY_TEMPLATE_CODE = 
                "SELECT * FROM doc_template WHERE template_code = #{templateCode} AND deleted = 0 LIMIT 1";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private DocumentSqlConstants() {
        throw new IllegalStateException("SQL常量类不应被实例化");
    }
} 
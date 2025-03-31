package com.lawfirm.model.knowledge.constant;

/**
 * 知识库模块SQL常量类
 * 集中管理知识库相关SQL查询语句，提高可维护性和安全性
 */
public class KnowledgeSqlConstants {
    
    /**
     * 知识Mapper相关SQL常量
     */
    public static class Knowledge {
        /**
         * 根据分类ID查询知识列表
         */
        public static final String SELECT_BY_CATEGORY_ID = 
                "SELECT * FROM knowledge WHERE category_id = #{categoryId}";
        
        /**
         * 根据标签ID查询知识列表
         */
        public static final String SELECT_BY_TAG_ID = 
                "SELECT k.* FROM knowledge k " +
                "JOIN knowledge_tag_relation r ON k.id = r.knowledge_id " +
                "WHERE r.tag_id = #{tagId} " +
                "ORDER BY k.create_time DESC";
        
        /**
         * 根据关键词搜索知识
         * 注意：调用前应对keyword参数进行验证，防止SQL注入
         */
        public static final String SEARCH_BY_KEYWORD = 
                "SELECT * FROM knowledge WHERE title LIKE CONCAT('%', #{keyword}, '%') OR content LIKE CONCAT('%', #{keyword}, '%')";
        
        /**
         * 获取最新知识
         */
        public static final String SELECT_LATEST_KNOWLEDGE = 
                "SELECT * FROM knowledge ORDER BY create_time DESC LIMIT #{limit}";
        
        /**
         * 查询相关知识
         */
        public static final String SELECT_RELATED = 
                "SELECT * FROM knowledge " +
                "WHERE category_id = #{categoryId} " +
                "AND id != #{excludeId} " +
                "ORDER BY create_time DESC " +
                "LIMIT #{limit}";
        
        /**
         * 统计分类的知识数量
         */
        public static final String COUNT_BY_CATEGORY_ID = 
                "SELECT COUNT(*) FROM knowledge WHERE category_id = #{categoryId}";
    }
    
    /**
     * 知识分类Mapper相关SQL常量
     */
    public static class Category {
        /**
         * 根据父ID查询分类列表
         */
        public static final String SELECT_BY_PARENT_ID = 
                "SELECT * FROM knowledge_category WHERE parent_id = #{parentId} ORDER BY sort";
        
        /**
         * 根据路径查询子分类
         */
        public static final String SELECT_BY_PATH = 
                "SELECT * FROM knowledge_category WHERE path LIKE CONCAT(#{path}, '%') ORDER BY sort";
        
        /**
         * 根据编码查询分类
         */
        public static final String SELECT_BY_CODE = 
                "SELECT * FROM knowledge_category WHERE code = #{code}";
    }
    
    /**
     * 知识标签Mapper相关SQL常量
     */
    public static class Tag {
        /**
         *:根据编码查询标签
         */
        public static final String SELECT_BY_CODE = 
                "SELECT * FROM knowledge_tag WHERE code = #{code}";
        
        /**
         * 根据名称模糊查询标签
         */
        public static final String SELECT_BY_NAME = 
                "SELECT * FROM knowledge_tag WHERE name LIKE CONCAT('%', #{name}, '%') ORDER BY sort";
    }
    
    /**
     * 知识标签关系Mapper相关SQL常量
     */
    public static class TagRelation {
        /**
         * 查询知识对应的标签ID列表
         */
        public static final String SELECT_TAG_IDS_BY_KNOWLEDGE_ID = 
                "SELECT tag_id FROM knowledge_tag_relation WHERE knowledge_id = #{knowledgeId}";
        
        /**
         * 查询标签对应的知识ID列表
         */
        public static final String SELECT_KNOWLEDGE_IDS_BY_TAG_ID = 
                "SELECT knowledge_id FROM knowledge_tag_relation WHERE tag_id = #{tagId}";
        
        /**
         * 删除知识的所有标签关系
         */
        public static final String DELETE_BY_KNOWLEDGE_ID = 
                "DELETE FROM knowledge_tag_relation WHERE knowledge_id = #{knowledgeId}";
        
        /**
         * 删除标签的所有知识关系
         */
        public static final String DELETE_BY_TAG_ID = 
                "DELETE FROM knowledge_tag_relation WHERE tag_id = #{tagId}";
    }
    
    /**
     * 知识附件Mapper相关SQL常量
     */
    public static class Attachment {
        /**
         * 查询知识的附件列表
         */
        public static final String SELECT_BY_KNOWLEDGE_ID = 
                "SELECT * FROM knowledge_attachment WHERE knowledge_id = #{knowledgeId} ORDER BY sort";
        
        /**
         * 根据文件类型查询附件
         */
        public static final String SELECT_BY_FILE_TYPE = 
                "SELECT * FROM knowledge_attachment WHERE file_type = #{fileType} ORDER BY sort";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private KnowledgeSqlConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
} 
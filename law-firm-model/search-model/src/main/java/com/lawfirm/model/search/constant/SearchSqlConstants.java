package com.lawfirm.model.search.constant;

/**
 * 搜索模块SQL常量类
 * 集中管理搜索相关SQL查询语句，提高可维护性和安全性
 */
public class SearchSqlConstants {
    
    /**
     * 搜索文档相关SQL常量
     */
    public static class SearchDoc {
        /**
         * 根据对象ID和类型查询文档
         */
        public static final String FIND_BY_OBJECT_ID_AND_TYPE = 
                "SELECT * FROM search_doc WHERE object_id = #{objectId} AND object_type = #{objectType}";
                
        /**
         * 根据索引ID查询文档列表
         */
        public static final String FIND_BY_INDEX_ID = 
                "SELECT * FROM search_doc WHERE index_id = #{indexId}";
                
        /**
         * 删除指定类型的文档
         */
        public static final String DELETE_BY_OBJECT_TYPE = 
                "DELETE FROM search_doc WHERE object_type = #{objectType}";
                
        /**
         * 更新文档状态
         */
        public static final String UPDATE_STATUS = 
                "UPDATE search_doc SET status = #{status} WHERE id = #{id}";
    }
    
    /**
     * 搜索索引相关SQL常量
     */
    public static class SearchIndex {
        /**
         * 根据索引名称查询
         */
        public static final String FIND_BY_INDEX_NAME = 
                "SELECT * FROM search_index WHERE index_name = #{indexName}";
                
        /**
         * 根据索引类型查询
         */
        public static final String FIND_BY_INDEX_TYPE = 
                "SELECT * FROM search_index WHERE index_type = #{indexType}";
                
        /**
         * 更新索引状态
         */
        public static final String UPDATE_STATUS = 
                "UPDATE search_index SET status = #{status} WHERE id = #{id}";
                
        /**
         * 更新文档数量
         */
        public static final String UPDATE_DOC_COUNT = 
                "UPDATE search_index SET doc_count = #{docCount} WHERE id = #{id}";
    }
    
    /**
     * 搜索历史相关SQL常量
     */
    public static class SearchHistory {
        /**
         * 根据用户ID查询搜索历史
         */
        public static final String FIND_BY_USER_ID = 
                "SELECT * FROM search_history WHERE user_id = #{userId} ORDER BY search_time DESC";
                
        /**
         * 查询热门搜索词
         */
        public static final String FIND_HOT_KEYWORDS = 
                "SELECT keyword, COUNT(*) as search_count FROM search_history " +
                "GROUP BY keyword ORDER BY search_count DESC LIMIT #{limit}";
                
        /**
         * 清除用户搜索历史
         */
        public static final String DELETE_BY_USER_ID = 
                "DELETE FROM search_history WHERE user_id = #{userId}";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private SearchSqlConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
} 
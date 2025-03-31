package com.lawfirm.model.ai.constant;

/**
 * AI模块SQL常量类
 * 集中管理AI相关SQL查询语句，提高可维护性和安全性
 */
public class AISqlConstants {
    
    /**
     * AI模型相关SQL常量
     */
    public static class Model {
        /**
         * 根据模型名称查询
         */
        public static final String FIND_BY_MODEL_NAME = 
                "SELECT * FROM ai_model WHERE model_name = #{modelName} AND deleted = 0";
                
        /**
         * 根据模型类型查询
         */
        public static final String FIND_BY_MODEL_TYPE = 
                "SELECT * FROM ai_model WHERE model_type = #{modelType} AND deleted = 0";
                
        /**
         * 查询启用状态的模型
         */
        public static final String FIND_ENABLED_MODELS = 
                "SELECT * FROM ai_model WHERE status = 1 AND deleted = 0";
    }
    
    /**
     * AI问答记录相关SQL常量
     */
    public static class QARecord {
        /**
         * 根据用户ID查询问答记录
         */
        public static final String FIND_BY_USER_ID = 
                "SELECT * FROM ai_qa_record WHERE user_id = #{userId} ORDER BY create_time DESC";
                
        /**
         * 根据会话ID查询问答记录
         */
        public static final String FIND_BY_SESSION_ID = 
                "SELECT * FROM ai_qa_record WHERE session_id = #{sessionId} ORDER BY create_time ASC";
                
        /**
         * 查询类似问题
         */
        public static final String FIND_SIMILAR_QUESTIONS = 
                "SELECT * FROM ai_qa_record WHERE question LIKE CONCAT('%', #{keyword}, '%') AND deleted = 0";
    }
    
    /**
     * AI知识库相关SQL常量
     */
    public static class Knowledge {
        /**
         * 根据标题查询知识
         */
        public static final String FIND_BY_TITLE = 
                "SELECT * FROM ai_knowledge WHERE title LIKE CONCAT('%', #{title}, '%') AND deleted = 0";
                
        /**
         * 根据类型查询知识
         */
        public static final String FIND_BY_TYPE = 
                "SELECT * FROM ai_knowledge WHERE knowledge_type = #{type} AND deleted = 0";
                
        /**
         * 查询最近更新的知识
         */
        public static final String FIND_RECENT_UPDATED = 
                "SELECT * FROM ai_knowledge ORDER BY update_time DESC LIMIT #{limit}";
    }
    
    /**
     * AI训练数据相关SQL常量
     */
    public static class TrainingData {
        /**
         * 根据类型查询训练数据
         */
        public static final String FIND_BY_DATA_TYPE = 
                "SELECT * FROM ai_training_data WHERE data_type = #{dataType} AND deleted = 0";
                
        /**
         * 查询未处理的训练数据
         */
        public static final String FIND_UNPROCESSED_DATA = 
                "SELECT * FROM ai_training_data WHERE process_status = 0 AND deleted = 0";
                
        /**
         * 更新处理状态
         */
        public static final String UPDATE_PROCESS_STATUS = 
                "UPDATE ai_training_data SET process_status = #{processStatus} WHERE id = #{id}";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private AISqlConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
} 
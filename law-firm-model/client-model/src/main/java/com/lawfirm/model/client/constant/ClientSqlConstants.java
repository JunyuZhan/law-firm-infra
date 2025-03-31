package com.lawfirm.model.client.constant;

/**
 * 客户模块SQL常量类
 * 集中管理客户相关SQL查询语句，提高可维护性和安全性
 */
public class ClientSqlConstants {
    
    /**
     * 客户基础查询相关SQL常量
     */
    public static class Client {
        /**
         * 根据客户编号查询客户
         */
        public static final String SELECT_BY_CLIENT_NO = 
                "SELECT * FROM client_info WHERE client_no = #{clientNo} AND deleted = 0";
                
        /**
         * 根据客户名称模糊查询
         */
        public static final String SELECT_BY_NAME = 
                "SELECT * FROM client_info WHERE client_name LIKE CONCAT('%', #{name}, '%') AND deleted = 0";
    }
    
    /**
     * 客户标签相关SQL常量
     */
    public static class Tag {
        /**
         * 获取客户的标签
         */
        public static final String SELECT_BY_CLIENT_ID = 
                "SELECT t.* FROM client_tag t " +
                "INNER JOIN client_tag_relation r ON t.id = r.tag_id " +
                "WHERE r.client_id = #{clientId} AND t.deleted = 0";
                
        /**
         * 查询标签下的客户数量
         */
        public static final String COUNT_CLIENTS_BY_TAG_ID = 
                "SELECT COUNT(DISTINCT client_id) FROM client_tag_relation WHERE tag_id = #{tagId}";
    }
    
    /**
     * 客户标签关联相关SQL常量
     */
    public static class TagRelation {
        /**
         * 获取客户的标签关联
         */
        public static final String SELECT_BY_CLIENT_ID = 
                "SELECT * FROM client_tag_relation WHERE client_id = #{clientId}";
                
        /**
         * 获取标签关联的客户
         */
        public static final String SELECT_BY_TAG_ID = 
                "SELECT * FROM client_tag_relation WHERE tag_id = #{tagId}";
                
        /**
         * 删除客户的标签关联
         */
        public static final String DELETE_BY_CLIENT_ID = 
                "DELETE FROM client_tag_relation WHERE client_id = #{clientId}";
                
        /**
         * 删除标签的关联
         */
        public static final String DELETE_BY_TAG_ID = 
                "DELETE FROM client_tag_relation WHERE tag_id = #{tagId}";
                
        /**
         * 删除客户的指定标签关联
         */
        public static final String DELETE_BY_CLIENT_ID_AND_TAG_ID = 
                "DELETE FROM client_tag_relation WHERE client_id = #{clientId} AND tag_id = #{tagId}";
    }
    
    /**
     * 客户跟进记录相关SQL常量
     */
    public static class FollowUp {
        /**
         * 获取客户的跟进记录
         */
        public static final String SELECT_BY_CLIENT_ID = 
                "SELECT * FROM client_follow_up WHERE client_id = #{clientId} AND deleted = 0 ORDER BY follow_time DESC";
                
        /**
         * 获取指定时间范围内的跟进记录
         */
        public static final String SELECT_BY_TIME_RANGE = 
                "SELECT * FROM client_follow_up WHERE client_id = #{clientId} " +
                "AND follow_time >= #{startTime} AND follow_time <= #{endTime} " +
                "AND deleted = 0 ORDER BY follow_time DESC";
                
        /**
         * 获取指定类型的跟进记录
         */
        public static final String SELECT_BY_TYPE = 
                "SELECT * FROM client_follow_up WHERE client_id = #{clientId} " +
                "AND follow_up_type = #{followUpType} AND deleted = 0 ORDER BY follow_time DESC";
                
        /**
         * 获取用户负责的客户跟进记录
         */
        public static final String SELECT_BY_USER_ID = 
                "SELECT * FROM client_follow_up WHERE creator_id = #{userId} AND deleted = 0 ORDER BY create_time DESC";
                
        /**
         * 查询需要提醒的跟进记录
         */
        public static final String SELECT_REMINDERS = 
                "SELECT * FROM client_follow_up WHERE remind_time <= #{remindTime} " +
                "AND remind_status = 0 AND deleted = 0";
    }
    
    /**
     * 客户联系人相关SQL常量
     */
    public static class Contact {
        /**
         * 获取客户的联系人
         */
        public static final String SELECT_BY_CLIENT_ID = 
                "SELECT * FROM client_contact WHERE client_id = #{clientId} AND deleted = 0";
                
        /**
         * 获取客户的默认联系人
         */
        public static final String SELECT_DEFAULT_BY_CLIENT_ID = 
                "SELECT * FROM client_contact WHERE client_id = #{clientId} AND is_default = 1 AND deleted = 0";
    }
    
    /**
     * 客户地址相关SQL常量
     */
    public static class Address {
        /**
         * 获取客户的地址
         */
        public static final String SELECT_BY_CLIENT_ID = 
                "SELECT * FROM client_address WHERE client_id = #{clientId} AND deleted = 0";
                
        /**
         * 获取客户的默认地址
         */
        public static final String SELECT_DEFAULT_BY_CLIENT_ID = 
                "SELECT * FROM client_address WHERE client_id = #{clientId} AND is_default = 1 AND deleted = 0";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private ClientSqlConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
} 
package com.lawfirm.model.message.constant;

/**
 * 消息模块SQL常量类
 * 集中管理消息相关SQL查询语句，提高可维护性和安全性
 */
public class MessageSqlConstants {
    
    /**
     * 消息相关SQL常量
     */
    public static class Message {
        /**
         * 根据接收者ID查询未读消息
         */
        public static final String FIND_UNREAD_BY_RECEIVER_ID = 
                "SELECT * FROM sys_message WHERE receiver_id = #{receiverId} AND read_status = 0 AND deleted = 0 ORDER BY create_time DESC";
                
        /**
         * 根据接收者ID查询所有消息
         */
        public static final String FIND_BY_RECEIVER_ID = 
                "SELECT * FROM sys_message WHERE receiver_id = #{receiverId} AND deleted = 0 ORDER BY create_time DESC";
                
        /**
         * 根据发送者ID查询消息
         */
        public static final String FIND_BY_SENDER_ID = 
                "SELECT * FROM sys_message WHERE sender_id = #{senderId} AND deleted = 0 ORDER BY create_time DESC";
                
        /**
         * 根据消息类型查询
         */
        public static final String FIND_BY_MESSAGE_TYPE = 
                "SELECT * FROM sys_message WHERE message_type = #{messageType} AND deleted = 0 ORDER BY create_time DESC";
                
        /**
         * 更新消息已读状态
         */
        public static final String UPDATE_READ_STATUS = 
                "UPDATE sys_message SET read_status = 1, read_time = NOW() WHERE id = #{id}";
                
        /**
         * 批量更新消息已读状态
         */
        public static final String BATCH_UPDATE_READ_STATUS = 
                "UPDATE sys_message SET read_status = 1, read_time = NOW() WHERE receiver_id = #{receiverId} AND read_status = 0";
    }
    
    /**
     * 消息模板相关SQL常量
     */
    public static class MessageTemplate {
        /**
         * 根据模板编码查询
         */
        public static final String FIND_BY_TEMPLATE_CODE = 
                "SELECT * FROM sys_message_template WHERE template_code = #{templateCode} AND deleted = 0";
                
        /**
         * 根据模板类型查询
         */
        public static final String FIND_BY_TEMPLATE_TYPE = 
                "SELECT * FROM sys_message_template WHERE template_type = #{templateType} AND deleted = 0";
    }
    
    /**
     * 通知设置相关SQL常量
     */
    public static class NotificationSetting {
        /**
         * 根据用户ID查询通知设置
         */
        public static final String FIND_BY_USER_ID = 
                "SELECT * FROM sys_notification_setting WHERE user_id = #{userId}";
                
        /**
         * 根据用户ID和通知类型查询设置
         */
        public static final String FIND_BY_USER_ID_AND_TYPE = 
                "SELECT * FROM sys_notification_setting WHERE user_id = #{userId} AND notification_type = #{notificationType}";
                
        /**
         * 更新通知状态
         */
        public static final String UPDATE_NOTIFICATION_STATUS = 
                "UPDATE sys_notification_setting SET status = #{status} WHERE id = #{id}";
    }
    
    /**
     * 聊天记录相关SQL常量
     */
    public static class ChatRecord {
        /**
         * 查询两用户之间的聊天记录
         */
        public static final String FIND_BY_USER_IDS = 
                "SELECT * FROM sys_chat_record WHERE (sender_id = #{userIdA} AND receiver_id = #{userIdB}) " +
                "OR (sender_id = #{userIdB} AND receiver_id = #{userIdA}) " +
                "ORDER BY send_time ASC";
                
        /**
         * 根据消息内容搜索
         */
        public static final String SEARCH_BY_CONTENT = 
                "SELECT * FROM sys_chat_record WHERE content LIKE CONCAT('%', #{keyword}, '%') " +
                "AND (sender_id = #{userId} OR receiver_id = #{userId}) ORDER BY send_time DESC";
                
        /**
         * 查询用户最近的聊天对象
         */
        public static final String FIND_RECENT_CONTACTS = 
                "SELECT DISTINCT IF(sender_id = #{userId}, receiver_id, sender_id) as contact_id " +
                "FROM sys_chat_record WHERE sender_id = #{userId} OR receiver_id = #{userId} " +
                "ORDER BY MAX(send_time) DESC LIMIT #{limit}";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private MessageSqlConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
} 
package com.lawfirm.model.log.constant;

/**
 * 日志模块SQL常量类
 * 集中管理日志相关SQL查询语句，提高可维护性和安全性
 */
public class LogSqlConstants {
    
    /**
     * 审计日志相关SQL常量
     */
    public static class AuditLog {
        /**
         * 根据操作用户查询日志
         */
        public static final String SELECT_BY_OPERATOR = 
                "SELECT * FROM audit_log WHERE operator_id = #{operatorId} ORDER BY operation_time DESC";
                
        /**
         * 根据操作类型查询日志
         */
        public static final String SELECT_BY_OPERATION_TYPE = 
                "SELECT * FROM audit_log WHERE operation_type = #{operationType} ORDER BY operation_time DESC";
                
        /**
         * 根据操作对象查询日志
         */
        public static final String SELECT_BY_TARGET_TYPE = 
                "SELECT * FROM audit_log WHERE target_type = #{targetType} ORDER BY operation_time DESC";
                
        /**
         * 根据时间范围查询日志
         */
        public static final String SELECT_BY_TIME_RANGE = 
                "SELECT * FROM audit_log WHERE operation_time >= #{startTime} AND operation_time <= #{endTime} ORDER BY operation_time DESC";
                
        /**
         * 查询最近的操作日志
         */
        public static final String SELECT_RECENT_LOGS = 
                "SELECT * FROM audit_log ORDER BY operation_time DESC LIMIT #{limit}";
    }
    
    /**
     * 审计记录相关SQL常量
     */
    public static class AuditRecord {
        /**
         * 根据审计日志ID查询详细记录
         */
        public static final String SELECT_BY_AUDIT_LOG_ID = 
                "SELECT * FROM audit_record WHERE audit_log_id = #{auditLogId}";
                
        /**
         * 根据操作对象ID查询审计记录
         */
        public static final String SELECT_BY_TARGET_ID = 
                "SELECT * FROM audit_record WHERE target_id = #{targetId} ORDER BY id DESC";
                
        /**
         * 查询指定字段的修改记录
         */
        public static final String SELECT_BY_FIELD_NAME = 
                "SELECT * FROM audit_record WHERE field_name = #{fieldName} AND target_id = #{targetId} ORDER BY id DESC";
    }
    
    /**
     * 登录日志相关SQL常量
     */
    public static class LoginLog {
        /**
         * 根据用户ID查询登录日志
         */
        public static final String SELECT_BY_USER_ID = 
                "SELECT * FROM login_log WHERE user_id = #{userId} ORDER BY login_time DESC";
                
        /**
         * 查询登录失败记录
         */
        public static final String SELECT_FAILED_LOGS = 
                "SELECT * FROM login_log WHERE login_status = 0 ORDER BY login_time DESC";
                
        /**
         * 统计用户登录次数
         */
        public static final String COUNT_BY_USER_ID = 
                "SELECT COUNT(*) FROM login_log WHERE user_id = #{userId}";
                
        /**
         * 查询最近一次登录记录
         */
        public static final String SELECT_LAST_LOGIN = 
                "SELECT * FROM login_log WHERE user_id = #{userId} AND login_status = 1 ORDER BY login_time DESC LIMIT 1";
    }
    
    /**
     * 操作日志相关SQL常量
     */
    public static class OperationLog {
        /**
         * 根据模块查询操作日志
         */
        public static final String SELECT_BY_MODULE = 
                "SELECT * FROM operation_log WHERE module = #{module} ORDER BY operation_time DESC";
                
        /**
         * 根据操作类型查询操作日志
         */
        public static final String SELECT_BY_OPERATION_TYPE = 
                "SELECT * FROM operation_log WHERE operation_type = #{operationType} ORDER BY operation_time DESC";
                
        /**
         * 根据操作状态查询操作日志
         */
        public static final String SELECT_BY_STATUS = 
                "SELECT * FROM operation_log WHERE status = #{status} ORDER BY operation_time DESC";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private LogSqlConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
} 
package com.lawfirm.model.system.constant;

/**
 * 系统模块SQL常量类
 * 集中管理系统相关SQL查询语句，提高可维护性和安全性
 */
public class SystemSqlConstants {
    
    /**
     * 系统配置相关SQL常量
     */
    public static class Config {
        /**
         * 根据配置键查询配置
         */
        public static final String SELECT_BY_KEY = 
                "SELECT id,config_key,config_value,remark AS description,create_by,update_by,deleted,create_time,update_time FROM sys_config WHERE config_key = #{configKey} AND deleted = 0";
                
        /**
         * 根据配置分组查询配置列表
         */
        public static final String SELECT_BY_GROUP = 
                "SELECT id,config_key,config_value,remark AS description,create_by,update_by,deleted,create_time,update_time FROM sys_config WHERE group_name = #{configGroup} AND deleted = 0";
    }
    
    /**
     * 系统字典相关SQL常量
     */
    public static class Dict {
        /**
         * 根据字典类型查询字典
         */
        public static final String SELECT_BY_TYPE = 
                "SELECT * FROM sys_dict WHERE dict_type = #{dictType} AND deleted = 0";
                
        /**
         * 根据字典类型查询字典项
         */
        public static final String SELECT_ITEMS_BY_DICT_TYPE = 
                "SELECT i.* FROM sys_dict_item i " +
                "INNER JOIN sys_dict d ON i.dict_id = d.id " +
                "WHERE d.dict_type = #{dictType} AND i.deleted = 0 " +
                "ORDER BY i.sort";
                
        /**
         * 根据字典ID查询字典项
         */
        public static final String SELECT_ITEMS_BY_DICT_ID = 
                "SELECT * FROM sys_dict_item WHERE dict_id = #{dictId} AND deleted = 0 ORDER BY sort";
    }
    
    /**
     * 系统监控相关SQL常量
     */
    public static class Monitor {
        /**
         * 查询指定时间段内的监控数据
         */
        public static final String SELECT_DATA_BY_TIME_RANGE = 
                "SELECT * FROM sys_monitor_data " +
                "WHERE monitor_time >= #{startTime} AND monitor_time <= #{endTime} " +
                "AND monitor_type = #{monitorType} " +
                "ORDER BY monitor_time";
                
        /**
         * 查询最新的监控数据
         */
        public static final String SELECT_LATEST_DATA = 
                "SELECT * FROM sys_monitor_data " +
                "WHERE monitor_type = #{monitorType} " +
                "ORDER BY monitor_time DESC LIMIT 1";
                
        /**
         * 查询未处理的告警
         */
        public static final String SELECT_UNPROCESSED_ALERTS = 
                "SELECT * FROM sys_monitor_alert " +
                "WHERE process_status = 0 " +
                "ORDER BY alert_time DESC";
    }
    
    /**
     * 系统升级相关SQL常量
     */
    public static class Upgrade {
        /**
         * 查询最新的系统版本
         */
        public static final String SELECT_LATEST_VERSION = 
                "SELECT * FROM sys_upgrade_version " +
                "ORDER BY version_code DESC LIMIT 1";
                
        /**
         * 查询待执行的升级脚本
         */
        public static final String SELECT_PENDING_SCRIPTS = 
                "SELECT * FROM sys_upgrade_script " +
                "WHERE execute_status = 0 " +
                "ORDER BY script_order";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private SystemSqlConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
} 
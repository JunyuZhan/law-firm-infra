package com.lawfirm.model.workflow.constant;

/**
 * 工作流模块SQL常量类
 * 集中管理工作流相关SQL查询语句，提高可维护性和安全性
 */
public class WorkflowSqlConstants {
    
    /**
     * 任务相关SQL常量
     */
    public static class Task {
        /**
         * 查询用户当前任务数量
         */
        public static final String SELECT_USER_TASK_COUNT = 
                "SELECT COUNT(*) FROM workflow_task WHERE handler_id = #{userId} AND status = 0";
        
        /**
         * 查询用户待办任务列表
         */
        public static final String SELECT_USER_PENDING_TASKS = 
                "SELECT * FROM workflow_task WHERE handler_id = #{userId} AND status = 0 ORDER BY create_time DESC";
        
        /**
         * 查询流程实例关联的所有任务
         */
        public static final String SELECT_PROCESS_INSTANCE_TASKS = 
                "SELECT * FROM workflow_task WHERE process_instance_id = #{processInstanceId} ORDER BY create_time";
                
        /**
         * 根据任务ID查询任务
         */
        public static final String SELECT_BY_TASK_ID = 
                "SELECT * FROM workflow_task WHERE id = #{taskId}";
                
        /**
         * 更新任务状态
         */
        public static final String UPDATE_TASK_STATUS = 
                "UPDATE workflow_task SET status = #{status}, update_time = NOW() WHERE id = #{taskId}";
                
        /**
         * 更新任务处理人
         */
        public static final String UPDATE_TASK_HANDLER = 
                "UPDATE workflow_task SET handler_id = #{handlerId}, update_time = NOW() WHERE id = #{taskId}";
    }
    
    /**
     * 流程实例相关SQL常量
     */
    public static class Process {
        /**
         * 根据流程实例ID查询
         */
        public static final String SELECT_BY_INSTANCE_ID = 
                "SELECT * FROM workflow_process WHERE instance_id = #{instanceId}";
                
        /**
         * 根据业务ID查询流程
         */
        public static final String SELECT_BY_BUSINESS_ID = 
                "SELECT * FROM workflow_process WHERE business_id = #{businessId} AND business_type = #{businessType}";
                
        /**
         * 查询用户发起的流程
         */
        public static final String SELECT_BY_INITIATOR = 
                "SELECT * FROM workflow_process WHERE initiator_id = #{initiatorId} ORDER BY create_time DESC";
                
        /**
         * 更新流程状态
         */
        public static final String UPDATE_PROCESS_STATUS = 
                "UPDATE workflow_process SET status = #{status}, update_time = NOW() WHERE id = #{id}";
    }
    
    /**
     * 流程模板相关SQL常量
     */
    public static class Template {
        /**
         * 根据模板键查询最新版本
         */
        public static final String SELECT_LATEST_BY_KEY = 
                "SELECT * FROM workflow_template WHERE process_key = #{key} ORDER BY version DESC LIMIT 1";
                
        /**
         * 根据模板键和版本查询
         */
        public static final String SELECT_BY_KEY_AND_VERSION = 
                "SELECT * FROM workflow_template WHERE process_key = #{key} AND version = #{version}";
                
        /**
         * 根据流程定义ID查询
         */
        public static final String SELECT_BY_DEFINITION_ID = 
                "SELECT * FROM workflow_template WHERE process_definition_id = #{processDefinitionId}";
                
        /**
         * 根据分类查询模板列表
         */
        public static final String SELECT_BY_CATEGORY = 
                "SELECT * FROM workflow_template WHERE category = #{category} AND status = 1 ORDER BY create_time DESC";
                
        /**
         * 查询所有启用状态的模板
         */
        public static final String SELECT_ALL_ENABLED = 
                "SELECT * FROM workflow_template WHERE status = 1 ORDER BY sort";
    }
    
    /**
     * 公共流程相关SQL常量
     */
    public static class CommonProcess {
        /**
         * 根据类型查询公共流程
         */
        public static final String SELECT_BY_TYPE = 
                "SELECT * FROM workflow_common_process WHERE process_type = #{type} AND status = 1";
                
        /**
         * 查询部门适用的公共流程
         */
        public static final String SELECT_BY_DEPARTMENT = 
                "SELECT * FROM workflow_common_process WHERE FIND_IN_SET(#{departmentId}, applicable_departments) AND status = 1";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private WorkflowSqlConstants() {
        throw new IllegalStateException("SQL常量类不应被实例化");
    }
} 
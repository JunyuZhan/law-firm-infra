package com.lawfirm.model.cases.constants;

/**
 * 案例模块SQL常量类
 * 集中管理案例相关SQL查询语句，提高可维护性和安全性
 */
public class CaseSqlConstants {
    
    /**
     * 案件基础相关SQL常量
     */
    public static class Case {
        /**
         * 根据案件编号查询
         */
        public static final String SELECT_BY_CASE_NO = 
                "SELECT * FROM case_info WHERE case_no = #{caseNo} AND deleted = 0";
                
        /**
         * 根据客户ID查询案件列表
         */
        public static final String SELECT_BY_CLIENT_ID = 
                "SELECT * FROM case_info WHERE client_id = #{clientId} AND deleted = 0 ORDER BY create_time DESC";
                
        /**
         * 根据案件状态查询
         */
        public static final String SELECT_BY_STATUS = 
                "SELECT * FROM case_info WHERE case_status = #{status} AND deleted = 0 ORDER BY create_time DESC";
                
        /**
         * 根据案件类型查询
         */
        public static final String SELECT_BY_TYPE = 
                "SELECT * FROM case_info WHERE case_type = #{type} AND deleted = 0 ORDER BY create_time DESC";
                
        /**
         * 查询指定法院的案件
         */
        public static final String SELECT_BY_COURT = 
                "SELECT * FROM case_info WHERE court = #{court} AND deleted = 0 ORDER BY create_time DESC";
    }
    
    /**
     * 案件团队相关SQL常量
     */
    public static class Team {
        /**
         * 根据案件ID查询团队
         */
        public static final String SELECT_BY_CASE_ID = 
                "SELECT * FROM case_team WHERE case_id = #{caseId} AND deleted = 0";
                
        /**
         * 根据负责人查询团队
         */
        public static final String SELECT_BY_LEADER = 
                "SELECT * FROM case_team WHERE leader_id = #{leaderId} AND deleted = 0";
    }
    
    /**
     * 案件参与人相关SQL常量
     */
    public static class Participant {
        /**
         * 根据案件ID查询参与人
         */
        public static final String SELECT_BY_CASE_ID = 
                "SELECT * FROM case_participant WHERE case_id = #{caseId} AND deleted = 0";
                
        /**
         * 根据参与人类型查询
         */
        public static final String SELECT_BY_TYPE = 
                "SELECT * FROM case_participant WHERE case_id = #{caseId} AND participant_type = #{type} AND deleted = 0";
    }
    
    /**
     * 案件分配相关SQL常量
     */
    public static class Assignment {
        /**
         * 根据案件ID查询分配记录
         */
        public static final String SELECT_BY_CASE_ID = 
                "SELECT * FROM case_assignment WHERE case_id = #{caseId} AND deleted = 0 ORDER BY assign_time DESC";
                
        /**
         * 根据律师ID查询被分配的案件
         */
        public static final String SELECT_BY_LAWYER_ID = 
                "SELECT c.* FROM case_info c " +
                "INNER JOIN case_assignment a ON c.id = a.case_id " +
                "WHERE a.lawyer_id = #{lawyerId} AND a.deleted = 0 AND c.deleted = 0 " +
                "ORDER BY a.assign_time DESC";
    }
    
    /**
     * 案件文档相关SQL常量
     */
    public static class Document {
        /**
         * 根据案件ID查询文档
         */
        public static final String SELECT_BY_CASE_ID = 
                "SELECT * FROM case_document WHERE case_id = #{caseId} AND deleted = 0 ORDER BY create_time DESC";
                
        /**
         * 根据文档类型查询
         */
        public static final String SELECT_BY_TYPE = 
                "SELECT * FROM case_document WHERE case_id = #{caseId} AND document_type = #{documentType} AND deleted = 0";
    }
    
    /**
     * 案件任务相关SQL常量
     */
    public static class Task {
        /**
         * 根据案件ID查询任务
         */
        public static final String SELECT_BY_CASE_ID = 
                "SELECT * FROM case_task WHERE case_id = #{caseId} AND deleted = 0 ORDER BY priority DESC, create_time DESC";
                
        /**
         * 根据执行人查询任务
         */
        public static final String SELECT_BY_EXECUTOR = 
                "SELECT * FROM case_task WHERE executor_id = #{executorId} AND deleted = 0 ORDER BY priority DESC, create_time DESC";
                
        /**
         * 查询未完成的任务
         */
        public static final String SELECT_UNFINISHED = 
                "SELECT * FROM case_task WHERE case_id = #{caseId} AND status < 2 AND deleted = 0 ORDER BY priority DESC";
    }
    
    /**
     * 案件笔记相关SQL常量
     */
    public static class Note {
        /**
         * 根据案件ID查询笔记
         */
        public static final String SELECT_BY_CASE_ID = 
                "SELECT * FROM case_note WHERE case_id = #{caseId} AND deleted = 0 ORDER BY create_time DESC";
                
        /**
         * 根据创建人查询笔记
         */
        public static final String SELECT_BY_CREATOR = 
                "SELECT * FROM case_note WHERE creator_id = #{creatorId} AND deleted = 0 ORDER BY create_time DESC";
    }
    
    /**
     * 案件费用相关SQL常量
     */
    public static class Fee {
        /**
         * 根据案件ID查询费用
         */
        public static final String SELECT_BY_CASE_ID = 
                "SELECT * FROM case_fee WHERE case_id = #{caseId} AND deleted = 0 ORDER BY create_time DESC";
                
        /**
         * 根据费用类型查询
         */
        public static final String SELECT_BY_TYPE = 
                "SELECT * FROM case_fee WHERE case_id = #{caseId} AND fee_type = #{feeType} AND deleted = 0";
                
        /**
         * 统计案件费用总额
         */
        public static final String SUM_AMOUNT_BY_CASE_ID = 
                "SELECT SUM(amount) FROM case_fee WHERE case_id = #{caseId} AND deleted = 0";
    }
    
    /**
     * 案件事件相关SQL常量
     */
    public static class Event {
        /**
         * 根据案件ID查询事件
         */
        public static final String SELECT_BY_CASE_ID = 
                "SELECT * FROM case_event WHERE case_id = #{caseId} AND deleted = 0 ORDER BY event_time DESC";
                
        /**
         * 根据事件类型查询
         */
        public static final String SELECT_BY_TYPE = 
                "SELECT * FROM case_event WHERE case_id = #{caseId} AND event_type = #{eventType} AND deleted = 0 ORDER BY event_time DESC";
                
        /**
         * 查询指定时间范围内的事件
         */
        public static final String SELECT_BY_TIME_RANGE = 
                "SELECT * FROM case_event WHERE case_id = #{caseId} " +
                "AND event_time >= #{startTime} AND event_time <= #{endTime} " +
                "AND deleted = 0 ORDER BY event_time DESC";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private CaseSqlConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
} 
package com.lawfirm.model.task.constants;

/**
 * 工作任务模块SQL常量类
 */
public class WorkTaskSqlConstants {
    
    /**
     * 工作任务相关SQL
     */
    public static class Task {
        /**
         * 查询任务列表SQL
         */
        public static final String SELECT_TASKS =
            "SELECT * FROM work_task " +
            "WHERE 1=1 " +
            "<if test='title != null and title != \"\"'>AND title LIKE CONCAT('%', #{title}, '%')</if> " +
            "<if test='status != null'>AND status = #{status}</if> " +
            "<if test='priority != null'>AND priority = #{priority}</if> " +
            "<if test='assigneeId != null'>AND assignee_id = #{assigneeId}</if> " +
            "<if test='creatorId != null'>AND creator_id = #{creatorId}</if> " +
            "<if test='categoryId != null'>AND category_id = #{categoryId}</if> " +
            "<if test='startTime != null'>AND start_time >= #{startTime}</if> " +
            "<if test='endTime != null'>AND end_time <= #{endTime}</if> " +
            "<if test='tenantId != null'>AND tenant_id = #{tenantId}</if> " +
            "<if test='!includeCompleted'>AND status != 2</if> " +
            "<if test='!includeCancelled'>AND status != 3</if> " +
            "ORDER BY create_time DESC";
        
        /**
         * 查询用户待办任务数量SQL
         */
        public static final String SELECT_USER_TASK_COUNT =
            "SELECT COUNT(*) FROM work_task WHERE assignee_id = #{userId} AND status = 0";
        
        /**
         * 查询用户待办任务列表SQL
         */
        public static final String SELECT_USER_PENDING_TASKS =
            "SELECT * FROM work_task WHERE assignee_id = #{userId} AND status = 0 ORDER BY create_time DESC";
        
        /**
         * 查询子任务列表SQL
         */
        public static final String SELECT_SUB_TASKS =
            "SELECT * FROM work_task WHERE parent_id = #{parentId} ORDER BY create_time";
    }
    
    /**
     * 工作任务标签相关SQL
     */
    public static class Tag {
        /**
         * 查询任务标签列表SQL
         */
        public static final String SELECT_TASK_TAGS =
            "SELECT t.* FROM work_task_tag t " +
            "INNER JOIN work_task_tag_relation r ON t.id = r.tag_id " +
            "WHERE r.task_id = #{taskId}";
    }
    
    /**
     * 工作任务标签关联关系相关SQL
     */
    public static class TagRelation {
        /**
         * 根据任务ID获取标签ID列表SQL
         */
        public static final String SELECT_TAG_IDS_BY_TASK_ID =
            "SELECT tag_id FROM work_task_tag_relation WHERE task_id = #{taskId}";
        
        /**
         * 根据标签ID获取任务ID列表SQL
         */
        public static final String SELECT_TASK_IDS_BY_TAG_ID =
            "SELECT task_id FROM work_task_tag_relation WHERE tag_id = #{tagId}";
    }
    
    /**
     * 工作任务评论相关SQL
     */
    public static class Comment {
        /**
         * 查询任务评论列表SQL
         */
        public static final String SELECT_TASK_COMMENTS =
            "SELECT * FROM work_task_comment WHERE task_id = #{taskId} ORDER BY create_time DESC";
    }
    
    /**
     * 工作任务附件相关SQL
     */
    public static class Attachment {
        /**
         * 查询任务附件列表SQL
         */
        public static final String SELECT_TASK_ATTACHMENTS =
            "SELECT * FROM work_task_attachment WHERE task_id = #{taskId} ORDER BY create_time DESC";
    }
    
    /**
     * 工作任务分类相关SQL
     */
    public static class Category {
        /**
         * 查询子分类列表SQL
         */
        public static final String SELECT_CHILD_CATEGORIES =
            "SELECT * FROM work_task_category WHERE parent_id = #{parentId} ORDER BY sort";
        
        /**
         * 根据分类编码获取分类SQL
         */
        public static final String SELECT_BY_CODE =
            "SELECT * FROM work_task_category WHERE code = #{code}";
    }
} 
package com.lawfirm.model.task.constants;

/**
 * 工作任务模块常量类
 */
public class WorkTaskConstants {
    
    /**
     * 工作任务模块常量
     */
    public static class Module {
        /**
         * 模块名称
         */
        public static final String NAME = "work_task";
        
        /**
         * 模块描述
         */
        public static final String DESCRIPTION = "工作任务管理模块";
    }
    
    /**
     * 工作任务相关常量
     */
    public static class Task {
        /**
         * 任务表名
         */
        public static final String TABLE_NAME = "work_task";
        
        /**
         * 任务标题最大长度
         */
        public static final int TITLE_MAX_LENGTH = 100;
        
        /**
         * 任务内容最大长度
         */
        public static final int CONTENT_MAX_LENGTH = 2000;
        
        /**
         * 默认任务进度
         */
        public static final int DEFAULT_PROGRESS = 0;
        
        /**
         * 最大任务进度
         */
        public static final int MAX_PROGRESS = 100;
        
        /**
         * 默认预计工时
         */
        public static final double DEFAULT_ESTIMATED_HOURS = 0.0;
        
        /**
         * 默认实际工时
         */
        public static final double DEFAULT_ACTUAL_HOURS = 0.0;
    }
    
    /**
     * 工作任务标签相关常量
     */
    public static class Tag {
        /**
         * 标签表名
         */
        public static final String TABLE_NAME = "work_task_tag";
        
        /**
         * 标签名称最大长度
         */
        public static final int NAME_MAX_LENGTH = 50;
        
        /**
         * 标签描述最大长度
         */
        public static final int DESCRIPTION_MAX_LENGTH = 200;
        
        /**
         * 标签颜色最大长度
         */
        public static final int COLOR_MAX_LENGTH = 20;
    }
    
    /**
     * 工作任务评论相关常量
     */
    public static class Comment {
        /**
         * 评论表名
         */
        public static final String TABLE_NAME = "work_task_comment";
        
        /**
         * 评论内容最大长度
         */
        public static final int CONTENT_MAX_LENGTH = 1000;
    }
    
    /**
     * 工作任务附件相关常量
     */
    public static class Attachment {
        /**
         * 附件表名
         */
        public static final String TABLE_NAME = "work_task_attachment";
        
        /**
         * 文件名称最大长度
         */
        public static final int FILE_NAME_MAX_LENGTH = 255;
        
        /**
         * 文件路径最大长度
         */
        public static final int FILE_PATH_MAX_LENGTH = 500;
        
        /**
         * 文件类型最大长度
         */
        public static final int FILE_TYPE_MAX_LENGTH = 50;
        
        /**
         * 文件MD5最大长度
         */
        public static final int FILE_MD5_MAX_LENGTH = 32;
    }
    
    /**
     * 工作任务分类相关常量
     */
    public static class Category {
        /**
         * 分类表名
         */
        public static final String TABLE_NAME = "work_task_category";
        
        /**
         * 分类名称最大长度
         */
        public static final int NAME_MAX_LENGTH = 50;
        
        /**
         * 分类编码最大长度
         */
        public static final int CODE_MAX_LENGTH = 50;
        
        /**
         * 分类描述最大长度
         */
        public static final int DESCRIPTION_MAX_LENGTH = 200;
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private WorkTaskConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
} 
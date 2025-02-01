package com.lawfirm.document.constant;

/**
 * 文档模块常量类
 */
public class DocumentConstant {
    
    /**
     * 文件大小限制（单位：字节）
     */
    public static final long MAX_FILE_SIZE = 100 * 1024 * 1024; // 100MB
    
    /**
     * 支持的文件类型
     */
    public static final String[] SUPPORTED_FILE_TYPES = {
        "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx",
        "txt", "jpg", "jpeg", "png", "gif"
    };
    
    /**
     * 支持预览的文件类型
     */
    public static final String[] PREVIEW_SUPPORTED_TYPES = {
        "pdf", "jpg", "jpeg", "png", "gif"
    };
    
    /**
     * 支持Office预览的文件类型
     */
    public static final String[] OFFICE_PREVIEW_TYPES = {
        "doc", "docx", "xls", "xlsx", "ppt", "pptx"
    };
    
    /**
     * 缩略图尺寸
     */
    public static class Thumbnail {
        public static final int DEFAULT_WIDTH = 200;
        public static final int DEFAULT_HEIGHT = 200;
        public static final int MAX_WIDTH = 800;
        public static final int MAX_HEIGHT = 800;
    }
    
    /**
     * 文档状态
     */
    public static class Status {
        public static final int DRAFT = 0;      // 草稿
        public static final int REVIEWING = 1;   // 审核中
        public static final int APPROVED = 2;    // 已通过
        public static final int REJECTED = 3;    // 已驳回
        public static final int ARCHIVED = 4;    // 已归档
    }
    
    /**
     * 文件存储相关常量
     */
    public static class Storage {
        public static final String PATH_SEPARATOR = "/";
        public static final String VERSION_PREFIX = "v";
        public static final String THUMBNAIL_SUFFIX = "_thumb";
    }
    
    private DocumentConstant() {
        throw new IllegalStateException("Constant class");
    }
    
    public static final String IMAGE_FORMAT_PNG = "PNG";
    public static final String IMAGE_FORMAT_JPG = "JPG";
    public static final String IMAGE_FORMAT_PDF = "PDF";
    
    public static final String CONTENT_TYPE_PDF = "application/pdf";
    public static final String CONTENT_TYPE_PNG = "image/png";
    public static final String CONTENT_TYPE_JPG = "image/jpeg";
    
    public static final String FILE_EXTENSION_PDF = ".pdf";
    public static final String FILE_EXTENSION_PNG = ".png";
    public static final String FILE_EXTENSION_JPG = ".jpg";
} 
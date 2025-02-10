package com.lawfirm.model.document.constant;

/**
 * 文档领域常量定义
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
     * 文件格式
     */
    public static class Format {
        public static final String PNG = "PNG";
        public static final String JPG = "JPG";
        public static final String PDF = "PDF";
    }
    
    /**
     * 内容类型
     */
    public static class ContentType {
        public static final String PDF = "application/pdf";
        public static final String PNG = "image/png";
        public static final String JPG = "image/jpeg";
    }
    
    /**
     * 文件扩展名
     */
    public static class Extension {
        public static final String PDF = ".pdf";
        public static final String PNG = ".png";
        public static final String JPG = ".jpg";
    }
    
    private DocumentConstant() {
        throw new IllegalStateException("Constant class");
    }
} 
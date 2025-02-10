package com.lawfirm.document.constant;

/**
 * 文档模块业务实现相关常量
 */
public class DocumentModuleConstant {
    
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
     * 文件存储相关常量
     */
    public static class Storage {
        public static final String PATH_SEPARATOR = "/";
        public static final String VERSION_PREFIX = "v";
        public static final String THUMBNAIL_SUFFIX = "_thumb";
    }
    
    private DocumentModuleConstant() {
        throw new IllegalStateException("Constant class");
    }
} 
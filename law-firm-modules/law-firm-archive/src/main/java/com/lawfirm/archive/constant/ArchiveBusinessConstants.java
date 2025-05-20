package com.lawfirm.archive.constant;

/**
 * 档案业务常量
 * 
 * 仅供业务层使用，模型层请勿引用。
 */
public final class ArchiveBusinessConstants {
    private ArchiveBusinessConstants() { throw new IllegalStateException("常量类不应被实例化"); }

    /**
     * 控制器相关API路径常量
     */
    public static final class Controller {
        public static final String API_PREFIX = "/api/v1/archives";
        public static final String API_FILE_PREFIX = "/api/v1/archives/files";
        public static final String API_BORROW_PREFIX = "/api/v1/archives/borrow";
        public static final String API_SYNC_PREFIX = "/api/v1/archives/sync";
        // 可根据实际业务继续细分
    }
} 
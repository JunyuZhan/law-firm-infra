package com.lawfirm.model.cases.constants;

import com.lawfirm.model.base.constants.BaseConstants;
import java.util.Arrays;
import java.util.List;

/**
 * 文档相关限制常量
 */
public interface DocumentConstants extends BaseConstants {
    
    /**
     * 文件类型相关
     */
    interface FileType {
        List<String> ALLOWED_EXTENSIONS = Arrays.asList(
            "doc", "docx", "pdf", "txt", "rtf", "xls", "xlsx",
            "ppt", "pptx", "jpg", "jpeg", "png", "gif", "zip", "rar"
        );
    }
    
    /**
     * 文件大小限制（单位：字节）
     */
    interface FileSize {
        long MAX_FILE = 50 * 1024 * 1024; // 50MB
        long MAX_IMAGE = 10 * 1024 * 1024; // 10MB
        long MAX_ARCHIVE = 100 * 1024 * 1024; // 100MB
    }
    
    /**
     * 文件命名规则
     */
    interface FileName {
        int MAX_LENGTH = 200;
        String PATTERN = "^[^<>:;,?\"*|/]+$";
        String SEPARATOR = Symbol.UNDERSCORE;
    }
    
    /**
     * 文档存储路径规则
     */
    interface Path {
        String BASE_UPLOAD = Symbol.SLASH + "upload" + Symbol.SLASH + "cases";
        String DOCUMENT = "documents" + Symbol.SLASH;
        String EVIDENCE = "evidences" + Symbol.SLASH;
        String ATTACHMENT = "attachments" + Symbol.SLASH;
        String TEMP = "temp" + Symbol.SLASH;
    }
    
    /**
     * 文档版本号规则
     */
    interface Version {
        String SEPARATOR = "v";
        String PATTERN = "^v\\d+(\\.\\d+)?$";
        String INITIAL = "v1.0";
    }
    
    /**
     * 文档分类限制
     */
    interface Category {
        int MAX_DEPTH = 3;
        int NAME_MAX_LENGTH = 50;
        int MAX_PER_LEVEL = 20;
    }
    
    /**
     * 文档描述限制
     */
    interface Description {
        int TITLE_MAX_LENGTH = 200;
        int CONTENT_MAX_LENGTH = 500;
        int KEYWORDS_MAX_LENGTH = 100;
    }
    
    /**
     * 文档标签限制
     */
    interface Tag {
        int MAX_PER_DOCUMENT = 10;
        int NAME_MAX_LENGTH = 20;
    }
    
    /**
     * 批量操作限制
     */
    interface Batch {
        int MAX_UPLOAD_SIZE = 10;
        int MAX_DELETE_SIZE = 50;
    }
} 
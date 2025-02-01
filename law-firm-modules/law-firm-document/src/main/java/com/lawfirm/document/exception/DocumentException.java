package com.lawfirm.document.exception;

import lombok.Getter;

/**
 * 文档模块异常类
 */
@Getter
public class DocumentException extends RuntimeException {
    
    private final String code;
    private final String message;
    
    public static final String ERROR_DOCUMENT_NOT_FOUND = "DOC_001";
    public static final String ERROR_DOCUMENT_ALREADY_EXISTS = "DOC_002";
    public static final String ERROR_FILE_TYPE_NOT_SUPPORTED = "DOC_003";
    public static final String ERROR_FILE_SIZE_EXCEEDED = "DOC_004";
    public static final String ERROR_STORAGE_FAILED = "DOC_005";
    public static final String ERROR_PREVIEW_NOT_SUPPORTED = "DOC_006";
    public static final String ERROR_VERSION_INVALID = "DOC_007";
    public static final String ERROR_FILE_UPLOAD_FAILED = "DOC_008";
    public static final String ERROR_FILE_URL_FAILED = "DOC_009";
    
    public DocumentException(String message) {
        super(message);
        this.code = ERROR_STORAGE_FAILED;
        this.message = message;
    }
    
    public DocumentException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
    public DocumentException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
} 
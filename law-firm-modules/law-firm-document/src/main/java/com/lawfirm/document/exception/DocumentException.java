package com.lawfirm.document.exception;

import com.lawfirm.common.exception.BusinessException;

/**
 * 文档模块异常类
 */
public class DocumentException extends BusinessException {
    
    public static final String ERROR_DOCUMENT_NOT_FOUND = "DOC_001";
    public static final String ERROR_DOCUMENT_ALREADY_EXISTS = "DOC_002";
    public static final String ERROR_FILE_TYPE_NOT_SUPPORTED = "DOC_003";
    public static final String ERROR_FILE_SIZE_EXCEEDED = "DOC_004";
    public static final String ERROR_STORAGE_FAILED = "DOC_005";
    public static final String ERROR_PREVIEW_NOT_SUPPORTED = "DOC_006";
    public static final String ERROR_VERSION_INVALID = "DOC_007";
    
    public DocumentException(String code) {
        super(code);
    }
    
    public DocumentException(String code, String message) {
        super(code, message);
    }
    
    public DocumentException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
} 
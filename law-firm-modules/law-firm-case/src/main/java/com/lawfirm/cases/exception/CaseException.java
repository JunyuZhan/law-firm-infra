package com.lawfirm.cases.exception;

import com.lawfirm.common.core.exception.BaseException;
import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.model.cases.constants.CaseErrorConstants;

/**
 * 案件异常类
 * 
 * 案件模块特有的业务异常定义，继承自通用基础异常BaseException。
 * 使用case-model中定义的错误码常量。
 */
public class CaseException extends BaseException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 模块前缀，用于标识异常来源
     */
    private static final String MODULE_PREFIX = "CASE_";
    
    /**
     * 构造案件异常
     * 
     * @param resultCode 结果码
     */
    public CaseException(ResultCode resultCode) {
        super(resultCode);
    }
    
    /**
     * 构造案件异常
     * 
     * @param resultCode 结果码
     * @param message 错误信息
     */
    public CaseException(ResultCode resultCode, String message) {
        super(resultCode, message);
    }
    
    /**
     * 构造案件异常
     * 
     * @param resultCode 结果码
     * @param cause 原因
     */
    public CaseException(ResultCode resultCode, Throwable cause) {
        super(resultCode, cause);
    }
    
    /**
     * 构造案件异常
     * 
     * @param resultCode 结果码
     * @param message 错误信息
     * @param cause 原因
     */
    public CaseException(ResultCode resultCode, String message, Throwable cause) {
        super(resultCode, message, cause);
    }
    
    /**
     * 创建案件不存在异常
     * 
     * @param caseId 案件ID
     * @return 异常对象
     */
    public static CaseException caseNotFound(Long caseId) {
        return new CaseException(
            ResultCode.NOT_FOUND, 
            MODULE_PREFIX + CaseErrorConstants.Business.CASE_NOT_FOUND + ": 案件不存在, ID=" + caseId
        );
    }
    
    /**
     * 创建案件已存在异常
     * 
     * @param caseNumber 案件编号
     * @return 异常对象
     */
    public static CaseException caseAlreadyExists(String caseNumber) {
        return new CaseException(
            ResultCode.BAD_REQUEST, 
            MODULE_PREFIX + CaseErrorConstants.Business.CASE_NUMBER_EXISTS + ": 案件已存在, 编号=" + caseNumber
        );
    }
    
    /**
     * 创建案件状态无效异常
     * 
     * @param currentStatus 当前状态
     * @param targetStatus 目标状态
     * @return 异常对象
     */
    public static CaseException caseStatusInvalid(String currentStatus, String targetStatus) {
        return new CaseException(
            ResultCode.BAD_REQUEST, 
            MODULE_PREFIX + CaseErrorConstants.Business.INVALID_STATUS_CHANGE + 
            ": 当前状态 " + currentStatus + " 不能变更为 " + targetStatus
        );
    }
    
    /**
     * 创建没有案件操作权限异常
     * 
     * @param caseId 案件ID
     * @param operation 操作
     * @return 异常对象
     */
    public static CaseException casePermissionDenied(Long caseId, String operation) {
        return new CaseException(
            ResultCode.FORBIDDEN, 
            MODULE_PREFIX + CaseErrorConstants.Permission.NO_PERMISSION + 
            ": 没有案件操作权限, 案件ID=" + caseId + ", 操作=" + operation
        );
    }
    
    /**
     * 创建案件数据无效异常
     * 
     * @param field 字段
     * @param value 值
     * @return 异常对象
     */
    public static CaseException caseDataInvalid(String field, Object value) {
        return new CaseException(
            ResultCode.BAD_REQUEST, 
            MODULE_PREFIX + CaseErrorConstants.Parameter.PARAMETER_VALUE_ERROR + 
            ": 案件数据无效, " + field + "=" + value
        );
    }
    
    /**
     * 创建案件操作无效异常
     * 
     * @param caseId 案件ID
     * @param operation 操作
     * @return 异常对象
     */
    public static CaseException caseOperationInvalid(Long caseId, String operation) {
        return new CaseException(
            ResultCode.BAD_REQUEST, 
            MODULE_PREFIX + CaseErrorConstants.Status.STATUS_NOT_ALLOWED + 
            ": 案件操作无效, 案件ID=" + caseId + ", 操作=" + operation
        );
    }
} 
package com.lawfirm.model.cases.constant;

import lombok.Getter;

/**
 * 案件合同错误码
 */
@Getter
public enum CaseContractErrorCode {
    
    // 案件合同相关错误: 700-799
    CASE_CONTRACT_INVALID(700, "案件合同无效"),
    CASE_CONTRACT_STATUS_INVALID(701, "案件合同状态无效"),
    CASE_CONTRACT_PERMISSION_DENIED(702, "案件合同操作权限不足");

    private final int code;
    private final String message;

    CaseContractErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
} 
package com.lawfirm.model.cases.constants;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 案件字段限制常量
 */
public interface CaseFieldConstants extends BaseConstants {
    
    // 案件编号相关限制
    int CASE_NUMBER_MIN_LENGTH = 6;
    int CASE_NUMBER_MAX_LENGTH = 32;
    String CASE_NUMBER_PATTERN = "^[A-Z0-9]{6,32}$";
    
    // 案件名称相关限制
    int CASE_NAME_MIN_LENGTH = 2;
    int CASE_NAME_MAX_LENGTH = 200;
    
    // 案件描述相关限制
    int CASE_DESCRIPTION_MAX_LENGTH = 500;
    int CASE_REMARK_MAX_LENGTH = 500;
    
    // 金额相关限制
    int FEE_SCALE = 2; // 金额精度：小数点后2位
    String CASE_FEE_PATTERN = "^\\d{1,10}(\\.\\d{1,2})?$";
    
    // 日期时间格式
    String DATE_PATTERN = "yyyy-MM-dd";
    String TIME_PATTERN = "HH:mm:ss";
    String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    // 法院相关字段限制
    int COURT_NAME_MAX_LENGTH = 100;
    int JUDGE_NAME_MAX_LENGTH = 50;
    int COURT_CASE_NUMBER_MAX_LENGTH = 50;
    
    // 律师相关字段限制
    int LAWYER_NAME_MAX_LENGTH = 50;
    int LAWYER_LICENSE_MAX_LENGTH = 20;
    
    // 委托人相关字段限制
    int CLIENT_NAME_MAX_LENGTH = 100;
    int CLIENT_ID_CARD_MAX_LENGTH = 18;
    int CLIENT_PHONE_MAX_LENGTH = 20;
    
    // 利益冲突相关限制
    int CONFLICT_REASON_MAX_LENGTH = 500;
    
    // 分页相关限制
    int PAGE_MAX_SIZE = 100;
    
    // 通用长度限制
    int MIN_LENGTH = 1;
    int MAX_LENGTH = 500;
    int NAME_MAX_LENGTH = 100;
    int CODE_MAX_LENGTH = 50;
    int REASON_MAX_LENGTH = 500;
    int PATTERN_MAX_LENGTH = 200;
    
    // 字段名称常量
    String CaseNumber = "caseNumber";
    String CaseName = "caseName";
    String Description = "description";
    String Lawyer = "lawyer";
    String Fee = "fee";
    String Court = "court";
    String Conflict = "conflict";
} 
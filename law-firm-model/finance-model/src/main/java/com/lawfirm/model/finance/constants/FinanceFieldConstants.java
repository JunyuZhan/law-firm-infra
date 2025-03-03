package com.lawfirm.model.finance.constants;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 财务字段限制常量
 */
public interface FinanceFieldConstants extends BaseConstants {
    
    /**
     * 财务字段限制
     */
    interface Length {
        /**
         * 编号最大长度
         */
        int MAX_NUMBER_LENGTH = 32;
        
        /**
         * 名称最大长度
         */
        int MAX_NAME_LENGTH = 64;
        
        /**
         * 描述最大长度
         */
        int MAX_DESCRIPTION_LENGTH = 512;
        
        /**
         * 金额精度
         */
        int AMOUNT_PRECISION = 2;
        
        /**
         * 金额最大位数（包含小数点）
         */
        int MAX_AMOUNT_DIGITS = 12;
        
        /**
         * 发票号码长度
         */
        int INVOICE_NUMBER_LENGTH = 20;
        
        /**
         * 纳税人识别号长度
         */
        int TAXPAYER_NUMBER_LENGTH = 18;
        
        /**
         * 发票抬头最大长度
         */
        int MAX_INVOICE_TITLE_LENGTH = 100;
        
        /**
         * 备注最大长度
         */
        int MAX_REMARK_LENGTH = 1000;
        
        /**
         * 地址最大长度
         */
        int MAX_ADDRESS_LENGTH = 200;
        
        /**
         * 电话号码最大长度
         */
        int MAX_PHONE_LENGTH = 20;
        
        /**
         * 邮箱最大长度
         */
        int MAX_EMAIL_LENGTH = 50;
        
        /**
         * 银行账号最大长度
         */
        int MAX_BANK_ACCOUNT_LENGTH = 30;
        
        /**
         * 开户行名称最大长度
         */
        int MAX_BANK_NAME_LENGTH = 100;
    }
    
    /**
     * 财务字段名称
     */
    interface FieldName {
        String INVOICE_NUMBER = "invoice_number";
        String INVOICE_TYPE = "invoice_type";
        String INVOICE_STATUS = "invoice_status";
        String AMOUNT = "amount";
        String TITLE = "title";
        String CONTENT = "content";
        String ISSUE_TIME = "issue_time";
        String ISSUED_BY = "issued_by";
        String TAXPAYER_NUMBER = "taxpayer_number";
        String BANK_NAME = "bank_name";
        String BANK_ACCOUNT = "bank_account";
    }
} 
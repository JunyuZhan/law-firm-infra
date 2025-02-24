package com.lawfirm.model.cases.constants;

import com.lawfirm.model.base.constants.BaseConstants;
import java.math.BigDecimal;

/**
 * 费用相关常量
 */
public interface CostConstants extends BaseConstants {
    
    /**
     * 费用精度
     */
    interface Precision {
        int SCALE = 2;
        String PATTERN = "^\\d+(\\.\\d{1,2})?$";
    }
    
    /**
     * 费用类型
     */
    interface Type {
        String HOURLY = "hourly";
        String FIXED = "fixed";
        String CONTINGENCY = "contingency";
        String RETAINER = "retainer";
        String MIXED = "mixed";
    }
    
    /**
     * 费用状态
     */
    interface Status {
        String PENDING = "pending";
        String CONFIRMED = "confirmed";
        String INVOICED = "invoiced";
        String PAID = "paid";
        String CANCELLED = "cancelled";
    }
    
    /**
     * 费用金额限制
     */
    interface Amount {
        BigDecimal MIN = BigDecimal.ZERO;
        BigDecimal MAX = new BigDecimal("9999999999.99");
        int MAX_LENGTH = 12; // 包含小数点和小数位
    }
    
    /**
     * 费率限制
     */
    interface Rate {
        BigDecimal MIN_HOURLY = new BigDecimal("100.00");
        BigDecimal MAX_HOURLY = new BigDecimal("9999.99");
        int MIN_MINUTES = 15; // 最小计费时间（分钟）
    }
    
    /**
     * 折扣限制
     */
    interface Discount {
        BigDecimal MIN = BigDecimal.ZERO;
        BigDecimal MAX = new BigDecimal("1.00");
        int SCALE = 2;
    }
    
    /**
     * 发票限制
     */
    interface Invoice {
        int NUMBER_LENGTH = 20;
        int TITLE_MAX_LENGTH = 200;
        int REMARK_MAX_LENGTH = 500;
    }
    
    /**
     * 费用描述限制
     */
    interface Description {
        int ITEM_MAX_LENGTH = 100;
        int DETAIL_MAX_LENGTH = 500;
        int REMARK_MAX_LENGTH = 200;
    }
    
    /**
     * 批量操作限制，继承自BaseConstants.Default
     */
    interface Batch extends Default {
        int MAX_ITEMS = 100;
        int MAX_INVOICE_ITEMS = 50;
    }
    
    /**
     * 时间限制
     */
    interface Time {
        int MAX_BILLING_DAYS = 365; // 最大账单周期（天）
        int DEFAULT_PAYMENT_DAYS = 30; // 默认付款期限（天）
    }
} 
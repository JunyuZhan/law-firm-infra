package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政案由
 */
public class AdminCause {

    /**
     * 行政案由一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        ADMINISTRATIVE_ACTION("行政行为", "01");

        private final String description;
        private final String code;

        First(String description, String code) {
            this.description = description;
            this.code = code;
        }

        @Override
        public String getValue() {
            return this.code;
        }

        @Override
        public String getDescription() {
            return this.description;
        }
    }

    /**
     * 行政案由二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        // 行政处罚
        ADMINISTRATIVE_PENALTY("行政处罚", "0101"),
        
        // 行政强制措施
        ADMINISTRATIVE_COERCIVE_MEASURES("行政强制措施", "0102"),
        
        // 行政强制执行
        ADMINISTRATIVE_ENFORCEMENT("行政强制执行", "0103"),
        
        // 行政许可
        ADMINISTRATIVE_LICENSE("行政许可", "0104"),
        
        // 行政征收或者征用
        ADMINISTRATIVE_REQUISITION("行政征收或者征用", "0105"),
        
        // 行政登记
        ADMINISTRATIVE_REGISTRATION("行政登记", "0106"),
        
        // 行政确认
        ADMINISTRATIVE_CONFIRMATION("行政确认", "0107"),
        
        // 行政给付
        ADMINISTRATIVE_PAYMENT("行政给付", "0108"),
        
        // 行政允诺
        ADMINISTRATIVE_PROMISE("行政允诺", "0109"),
        
        // 行政征缴
        ADMINISTRATIVE_COLLECTION("行政征缴", "0110"),
        
        // 行政奖励
        ADMINISTRATIVE_REWARD("行政奖励", "0111"),
        
        // 行政收费
        ADMINISTRATIVE_FEES("行政收费", "0112"),
        
        // 政府信息公开
        GOVERNMENT_INFORMATION_DISCLOSURE("政府信息公开", "0113"),
        
        // 行政批复
        ADMINISTRATIVE_REPLY("行政批复", "0114"),
        
        // 行政处理
        ADMINISTRATIVE_TREATMENT("行政处理", "0115"),
        
        // 行政复议
        ADMINISTRATIVE_RECONSIDERATION("行政复议", "0116"),
        
        // 行政裁决
        ADMINISTRATIVE_ADJUDICATION("行政裁决", "0117"),
        
        // 行政协议
        ADMINISTRATIVE_AGREEMENT("行政协议", "0118"),
        
        // 行政补偿
        ADMINISTRATIVE_COMPENSATION("行政补偿", "0119"),
        
        // 行政赔偿
        ADMINISTRATIVE_INDEMNITY("行政赔偿", "0120"),
        
        // 不履行职责
        FAILURE_TO_PERFORM_DUTIES("不履行职责", "0121"),
        
        // 行政行为公益诉讼
        PUBLIC_INTEREST_LITIGATION("行政行为公益诉讼", "0122");

        private final String description;
        private final String code;
        private final First parentCause = First.ADMINISTRATIVE_ACTION;

        Second(String description, String code) {
            this.description = description;
            this.code = code;
        }

        @Override
        public String getValue() {
            return this.code;
        }

        @Override
        public String getDescription() {
            return this.description;
        }
    }
} 
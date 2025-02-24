package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 票据纠纷案由
 */
public class BillCause {

    /**
     * 票据纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        BILL("票据纠纷", "28");

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
     * 票据纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 340.票据付款请求权纠纷
        PAYMENT_REQUEST("票据付款请求权纠纷", "1340"),
        
        // 341.票据追索权纠纷
        RECOURSE("票据追索权纠纷", "1341"),
        
        // 342.票据交付请求权纠纷
        DELIVERY_REQUEST("票据交付请求权纠纷", "1342"),
        
        // 343.票据返还请求权纠纷
        RETURN_REQUEST("票据返还请求权纠纷", "1343"),
        
        // 344.票据损害责任纠纷
        DAMAGE_LIABILITY("票据损害责任纠纷", "1344"),
        
        // 345.票据利益返还请求权纠纷
        BENEFIT_RETURN("票据利益返还请求权纠纷", "1345"),
        
        // 346.汇票回单签发请求权纠纷
        BILL_RECEIPT("汇票回单签发请求权纠纷", "1346"),
        
        // 347.票据保证纠纷
        BILL_GUARANTEE("票据保证纠纷", "1347"),
        
        // 348.确认票据无效纠纷
        INVALIDATION("确认票据无效纠纷", "1348"),
        
        // 349.票据代理纠纷
        BILL_AGENCY("票据代理纠纷", "1349"),
        
        // 350.票据回购纠纷
        BILL_REPURCHASE("票据回购纠纷", "1350");

        private final String description;
        private final String code;
        private final First parentCause = First.BILL;

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
package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 与破产有关的纠纷案由
 */
public class BankruptcyCause {

    /**
     * 与破产有关的纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        BANKRUPTCY("与破产有关的纠纷", "23");

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
     * 与破产有关的纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 289.请求撤销个别清偿行为纠纷
        REVOKE_INDIVIDUAL_SETTLEMENT("请求撤销个别清偿行为纠纷", "1289"),
        
        // 290.请求确认债务人行为无效纠纷
        CONFIRM_DEBTOR_INVALID("请求确认债务人行为无效纠纷", "1290"),
        
        // 291.对外追收债权纠纷
        EXTERNAL_DEBT_COLLECTION("对外追收债权纠纷", "1291"),
        
        // 292.追收未缴出资纠纷
        UNPAID_CONTRIBUTION("追收未缴出资纠纷", "1292"),
        
        // 293.追收抽逃出资纠纷
        WITHDRAWN_CONTRIBUTION("追收抽逃出资纠纷", "1293"),
        
        // 294.追收非正常收入纠纷
        ABNORMAL_INCOME("追收非正常收入纠纷", "1294"),
        
        // 295.破产债权确认纠纷
        BANKRUPTCY_CLAIM_CONFIRMATION("破产债权确认纠纷", "1295"),
        
        // 296.取回权纠纷
        RECOVERY_RIGHT("取回权纠纷", "1296"),
        
        // 297.破产抵销权纠纷
        BANKRUPTCY_SETOFF("破产抵销权纠纷", "1297"),
        
        // 298.别除权纠纷
        SECURITY_RIGHT("别除权纠纷", "1298"),
        
        // 299.破产撤销权纠纷
        BANKRUPTCY_REVOCATION("破产撤销权纠纷", "1299"),
        
        // 300.损害债务人利益赔偿纠纷
        DEBTOR_INTEREST_DAMAGE("损害债务人利益赔偿纠纷", "1300"),
        
        // 301.管理人责任纠纷
        ADMINISTRATOR_LIABILITY("管理人责任纠纷", "1301");

        private final String description;
        private final String code;
        private final First parentCause = First.BANKRUPTCY;

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

    /**
     * 破产债权确认纠纷三级案由
     */
    @Getter
    public enum BankruptcyClaimConfirmation implements BaseEnum<String> {
        
        // (1)职工破产债权确认纠纷
        EMPLOYEE_CLAIM("职工破产债权确认纠纷", "129501"),
        
        // (2)普通破产债权确认纠纷
        GENERAL_CLAIM("普通破产债权确认纠纷", "129502");

        private final String description;
        private final String code;
        private final Second parentCause = Second.BANKRUPTCY_CLAIM_CONFIRMATION;

        BankruptcyClaimConfirmation(String description, String code) {
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
     * 取回权纠纷三级案由
     */
    @Getter
    public enum RecoveryRight implements BaseEnum<String> {
        
        // (1)一般取回权纠纷
        GENERAL_RECOVERY("一般取回权纠纷", "129601"),
        
        // (2)出卖人取回权纠纷
        SELLER_RECOVERY("出卖人取回权纠纷", "129602");

        private final String description;
        private final String code;
        private final Second parentCause = Second.RECOVERY_RIGHT;

        RecoveryRight(String description, String code) {
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
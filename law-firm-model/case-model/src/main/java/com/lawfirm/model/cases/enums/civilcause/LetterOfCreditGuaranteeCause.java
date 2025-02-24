package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 信用证和独立保函纠纷案由
 */
public class LetterOfCreditGuaranteeCause {

    /**
     * 信用证纠纷一级案由
     */
    @Getter
    public enum LetterOfCreditFirst implements BaseEnum<String> {
        
        LETTER_OF_CREDIT("信用证纠纷", "29");

        private final String description;
        private final String code;

        LetterOfCreditFirst(String description, String code) {
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
     * 信用证纠纷二级案由
     */
    @Getter
    public enum LetterOfCreditSecond implements BaseEnum<String> {
        
        // 351.委托开立信用证纠纷
        ENTRUST_OPENING("委托开立信用证纠纷", "1351"),
        
        // 352.信用证开证纠纷
        OPENING("信用证开证纠纷", "1352"),
        
        // 353.信用证议付纠纷
        NEGOTIATION("信用证议付纠纷", "1353"),
        
        // 354.信用证欺诈纠纷
        FRAUD("信用证欺诈纠纷", "1354"),
        
        // 355.信用证融资纠纷
        FINANCING("信用证融资纠纷", "1355"),
        
        // 356.信用证转让纠纷
        TRANSFER("信用证转让纠纷", "1356");

        private final String description;
        private final String code;
        private final LetterOfCreditFirst parentCause = LetterOfCreditFirst.LETTER_OF_CREDIT;

        LetterOfCreditSecond(String description, String code) {
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
     * 独立保函纠纷一级案由
     */
    @Getter
    public enum GuaranteeFirst implements BaseEnum<String> {
        
        GUARANTEE("独立保函纠纷", "30");

        private final String description;
        private final String code;

        GuaranteeFirst(String description, String code) {
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
     * 独立保函纠纷二级案由
     */
    @Getter
    public enum GuaranteeSecond implements BaseEnum<String> {
        
        // 357.独立保函开立纠纷
        OPENING("独立保函开立纠纷", "1357"),
        
        // 358.独立保函付款纠纷
        PAYMENT("独立保函付款纠纷", "1358"),
        
        // 359.独立保函追偿纠纷
        RECOURSE("独立保函追偿纠纷", "1359"),
        
        // 360.独立保函欺诈纠纷
        FRAUD("独立保函欺诈纠纷", "1360"),
        
        // 361.独立保函转让纠纷
        TRANSFER("独立保函转让纠纷", "1361"),
        
        // 362.独立保函通知纠纷
        NOTIFICATION("独立保函通知纠纷", "1362"),
        
        // 363.独立保函撤销纠纷
        REVOCATION("独立保函撤销纠纷", "1363");

        private final String description;
        private final String code;
        private final GuaranteeFirst parentCause = GuaranteeFirst.GUARANTEE;

        GuaranteeSecond(String description, String code) {
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
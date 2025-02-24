package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 垄断纠纷案由
 */
public class MonopolyCause {

    /**
     * 垄断纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        MONOPOLY("垄断纠纷", "16");

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
     * 垄断纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 183.垄断协议纠纷
        MONOPOLY_AGREEMENT("垄断协议纠纷", "1183"),
        
        // 184.滥用市场支配地位纠纷
        MARKET_DOMINANCE_ABUSE("滥用市场支配地位纠纷", "1184"),
        
        // 185.经营者集中纠纷
        BUSINESS_CONCENTRATION("经营者集中纠纷", "1185");

        private final String description;
        private final String code;
        private final First parentCause = First.MONOPOLY;

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
     * 垄断协议纠纷三级案由
     */
    @Getter
    public enum MonopolyAgreement implements BaseEnum<String> {
        
        // (1)横向垄断协议纠纷
        HORIZONTAL("横向垄断协议纠纷", "118301"),
        
        // (2)纵向垄断协议纠纷
        VERTICAL("纵向垄断协议纠纷", "118302");

        private final String description;
        private final String code;
        private final Second parentCause = Second.MONOPOLY_AGREEMENT;

        MonopolyAgreement(String description, String code) {
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
     * 滥用市场支配地位纠纷三级案由
     */
    @Getter
    public enum MarketDominanceAbuse implements BaseEnum<String> {
        
        // (1)垄断定价纠纷
        MONOPOLY_PRICING("垄断定价纠纷", "118401"),
        
        // (2)掠夺定价纠纷
        PREDATORY_PRICING("掠夺定价纠纷", "118402"),
        
        // (3)拒绝交易纠纷
        REFUSAL_TO_DEAL("拒绝交易纠纷", "118403"),
        
        // (4)限定交易纠纷
        RESTRICTED_TRADING("限定交易纠纷", "118404"),
        
        // (5)捆绑交易纠纷
        TIED_TRADING("捆绑交易纠纷", "118405"),
        
        // (6)差别待遇纠纷
        DISCRIMINATORY_TREATMENT("差别待遇纠纷", "118406");

        private final String description;
        private final String code;
        private final Second parentCause = Second.MARKET_DOMINANCE_ABUSE;

        MarketDominanceAbuse(String description, String code) {
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
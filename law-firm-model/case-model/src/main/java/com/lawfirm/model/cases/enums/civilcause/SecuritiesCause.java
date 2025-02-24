package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 证券纠纷案由
 */
public class SecuritiesCause {

    /**
     * 证券纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        SECURITIES("证券纠纷", "24");

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
     * 证券纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 302.证券权利确认纠纷
        SECURITIES_RIGHTS_CONFIRMATION("证券权利确认纠纷", "1302"),
        
        // 303.证券交易合同纠纷
        SECURITIES_TRADING_CONTRACT("证券交易合同纠纷", "1303"),
        
        // 304.金融衍生品种交易纠纷
        FINANCIAL_DERIVATIVES_TRADING("金融衍生品种交易纠纷", "1304"),
        
        // 305.证券承销合同纠纷
        SECURITIES_UNDERWRITING("证券承销合同纠纷", "1305"),
        
        // 306.证券投资咨询纠纷
        SECURITIES_INVESTMENT_CONSULTING("证券投资咨询纠纷", "1306"),
        
        // 307.证券资信评级服务合同纠纷
        SECURITIES_RATING_SERVICE("证券资信评级服务合同纠纷", "1307"),
        
        // 308.证券回购合同纠纷
        SECURITIES_REPURCHASE("证券回购合同纠纷", "1308"),
        
        // 309.证券上市合同纠纷
        SECURITIES_LISTING("证券上市合同纠纷", "1309"),
        
        // 310.证券交易代理合同纠纷
        SECURITIES_TRADING_AGENCY("证券交易代理合同纠纷", "1310"),
        
        // 311.证券上市保荐合同纠纷
        SECURITIES_LISTING_SPONSOR("证券上市保荐合同纠纷", "1311"),
        
        // 312.证券发行纠纷
        SECURITIES_ISSUANCE("证券发行纠纷", "1312"),
        
        // 313.证券返还纠纷
        SECURITIES_RETURN("证券返还纠纷", "1313"),
        
        // 314.证券欺诈责任纠纷
        SECURITIES_FRAUD("证券欺诈责任纠纷", "1314"),
        
        // 315.证券托管纠纷
        SECURITIES_CUSTODY("证券托管纠纷", "1315"),
        
        // 316.证券登记、存管、结算纠纷
        SECURITIES_REGISTRATION("证券登记、存管、结算纠纷", "1316"),
        
        // 317.融资融券交易纠纷
        MARGIN_TRADING("融资融券交易纠纷", "1317"),
        
        // 318.客户交易结算资金纠纷
        CLIENT_SETTLEMENT_FUND("客户交易结算资金纠纷", "1318");

        private final String description;
        private final String code;
        private final First parentCause = First.SECURITIES;

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
     * 证券权利确认纠纷三级案由
     */
    @Getter
    public enum SecuritiesRightsConfirmation implements BaseEnum<String> {
        
        // (1)股票权利确认纠纷
        STOCK_RIGHTS("股票权利确认纠纷", "130201"),
        
        // (2)公司债券权利确认纠纷
        CORPORATE_BOND_RIGHTS("公司债券权利确认纠纷", "130202"),
        
        // (3)国债权利确认纠纷
        GOVERNMENT_BOND_RIGHTS("国债权利确认纠纷", "130203"),
        
        // (4)证券投资基金权利确认纠纷
        FUND_RIGHTS("证券投资基金权利确认纠纷", "130204");

        private final String description;
        private final String code;
        private final Second parentCause = Second.SECURITIES_RIGHTS_CONFIRMATION;

        SecuritiesRightsConfirmation(String description, String code) {
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
     * 证券交易合同纠纷三级案由
     */
    @Getter
    public enum SecuritiesTrading implements BaseEnum<String> {
        
        // (1)股票交易纠纷
        STOCK_TRADING("股票交易纠纷", "130301"),
        
        // (2)公司债券交易纠纷
        CORPORATE_BOND_TRADING("公司债券交易纠纷", "130302"),
        
        // (3)国债交易纠纷
        GOVERNMENT_BOND_TRADING("国债交易纠纷", "130303"),
        
        // (4)证券投资基金交易纠纷
        FUND_TRADING("证券投资基金交易纠纷", "130304");

        private final String description;
        private final String code;
        private final Second parentCause = Second.SECURITIES_TRADING_CONTRACT;

        SecuritiesTrading(String description, String code) {
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
     * 证券承销合同纠纷三级案由
     */
    @Getter
    public enum SecuritiesUnderwriting implements BaseEnum<String> {
        
        // (1)证券代销合同纠纷
        AGENCY_SALES("证券代销合同纠纷", "130501"),
        
        // (2)证券包销合同纠纷
        UNDERWRITING_CONTRACT("证券包销合同纠纷", "130502");

        private final String description;
        private final String code;
        private final Second parentCause = Second.SECURITIES_UNDERWRITING;

        SecuritiesUnderwriting(String description, String code) {
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
     * 证券回购合同纠纷三级案由
     */
    @Getter
    public enum SecuritiesRepurchase implements BaseEnum<String> {
        
        // (1)股票回购合同纠纷
        STOCK_REPURCHASE("股票回购合同纠纷", "130801"),
        
        // (2)国债回购合同纠纷
        GOVERNMENT_BOND_REPURCHASE("国债回购合同纠纷", "130802"),
        
        // (3)公司债券回购合同纠纷
        CORPORATE_BOND_REPURCHASE("公司债券回购合同纠纷", "130803"),
        
        // (4)证券投资基金回购合同纠纷
        FUND_REPURCHASE("证券投资基金回购合同纠纷", "130804"),
        
        // (5)质押式证券回购纠纷
        PLEDGED_REPURCHASE("质押式证券回购纠纷", "130805");

        private final String description;
        private final String code;
        private final Second parentCause = Second.SECURITIES_REPURCHASE;

        SecuritiesRepurchase(String description, String code) {
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
     * 证券发行纠纷三级案由
     */
    @Getter
    public enum SecuritiesIssuance implements BaseEnum<String> {
        
        // (1)证券认购纠纷
        SECURITIES_SUBSCRIPTION("证券认购纠纷", "131201"),
        
        // (2)证券发行失败纠纷
        ISSUANCE_FAILURE("证券发行失败纠纷", "131202");

        private final String description;
        private final String code;
        private final Second parentCause = Second.SECURITIES_ISSUANCE;

        SecuritiesIssuance(String description, String code) {
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
     * 证券欺诈责任纠纷三级案由
     */
    @Getter
    public enum SecuritiesFraud implements BaseEnum<String> {
        
        // (1)证券内幕交易责任纠纷
        INSIDER_TRADING("证券内幕交易责任纠纷", "131401"),
        
        // (2)操纵证券交易市场责任纠纷
        MARKET_MANIPULATION("操纵证券交易市场责任纠纷", "131402"),
        
        // (3)证券虚假陈述责任纠纷
        FALSE_STATEMENT("证券虚假陈述责任纠纷", "131403"),
        
        // (4)欺诈客户责任纠纷
        CLIENT_FRAUD("欺诈客户责任纠纷", "131404");

        private final String description;
        private final String code;
        private final Second parentCause = Second.SECURITIES_FRAUD;

        SecuritiesFraud(String description, String code) {
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
package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 与公司和合伙企业有关的纠纷案由
 */
public class CompanyPartnershipCause {

    /**
     * 与公司有关的纠纷一级案由
     */
    @Getter
    public enum CompanyFirst implements BaseEnum<String> {
        
        COMPANY("与公司有关的纠纷", "21");

        private final String description;
        private final String code;

        CompanyFirst(String description, String code) {
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
     * 与公司有关的纠纷二级案由
     */
    @Getter
    public enum CompanySecond implements BaseEnum<String> {
        
        // 262.股东资格确认纠纷
        SHAREHOLDER_QUALIFICATION("股东资格确认纠纷", "1262"),
        
        // 263.股东名册记载纠纷
        SHAREHOLDER_REGISTER("股东名册记载纠纷", "1263"),
        
        // 264.请求变更公司登记纠纷
        COMPANY_REGISTRATION_CHANGE("请求变更公司登记纠纷", "1264"),
        
        // 265.股东出资纠纷
        SHAREHOLDER_CONTRIBUTION("股东出资纠纷", "1265"),
        
        // 266.新增资本认购纠纷
        CAPITAL_SUBSCRIPTION("新增资本认购纠纷", "1266"),
        
        // 267.股东知情权纠纷
        SHAREHOLDER_INFORMATION_RIGHT("股东知情权纠纷", "1267"),
        
        // 268.请求公司收购股份纠纷
        SHARE_REPURCHASE("请求公司收购股份纠纷", "1268"),
        
        // 269.股权转让纠纷
        EQUITY_TRANSFER("股权转让纠纷", "1269"),
        
        // 270.公司决议纠纷
        COMPANY_RESOLUTION("公司决议纠纷", "1270"),
        
        // 271.公司设立纠纷
        COMPANY_ESTABLISHMENT("公司设立纠纷", "1271"),
        
        // 272.公司证照返还纠纷
        COMPANY_LICENSE_RETURN("公司证照返还纠纷", "1272"),
        
        // 273.发起人责任纠纷
        PROMOTER_LIABILITY("发起人责任纠纷", "1273"),
        
        // 274.公司盈余分配纠纷
        PROFIT_DISTRIBUTION("公司盈余分配纠纷", "1274"),
        
        // 275.损害股东利益责任纠纷
        SHAREHOLDER_INTEREST_DAMAGE("损害股东利益责任纠纷", "1275"),
        
        // 276.损害公司利益责任纠纷
        COMPANY_INTEREST_DAMAGE("损害公司利益责任纠纷", "1276"),
        
        // 277.损害公司债权人利益责任纠纷
        CREDITOR_INTEREST_DAMAGE("损害公司债权人利益责任纠纷", "1277"),
        
        // 278.公司关联交易损害责任纠纷
        RELATED_TRANSACTION_DAMAGE("公司关联交易损害责任纠纷", "1278"),
        
        // 279.公司合并纠纷
        COMPANY_MERGER("公司合并纠纷", "1279"),
        
        // 280.公司分立纠纷
        COMPANY_DIVISION("公司分立纠纷", "1280"),
        
        // 281.公司减资纠纷
        CAPITAL_REDUCTION("公司减资纠纷", "1281"),
        
        // 282.公司增资纠纷
        CAPITAL_INCREASE("公司增资纠纷", "1282"),
        
        // 283.公司解散纠纷
        COMPANY_DISSOLUTION("公司解散纠纷", "1283"),
        
        // 284.清算责任纠纷
        LIQUIDATION_LIABILITY("清算责任纠纷", "1284"),
        
        // 285.上市公司收购纠纷
        LISTED_COMPANY_ACQUISITION("上市公司收购纠纷", "1285");

        private final String description;
        private final String code;
        private final CompanyFirst parentCause = CompanyFirst.COMPANY;

        CompanySecond(String description, String code) {
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
     * 公司决议纠纷三级案由
     */
    @Getter
    public enum CompanyResolution implements BaseEnum<String> {
        
        // (1)公司决议效力确认纠纷
        RESOLUTION_VALIDITY("公司决议效力确认纠纷", "127001"),
        
        // (2)公司决议撤销纠纷
        RESOLUTION_REVOCATION("公司决议撤销纠纷", "127002");

        private final String description;
        private final String code;
        private final CompanySecond parentCause = CompanySecond.COMPANY_RESOLUTION;

        CompanyResolution(String description, String code) {
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
     * 损害公司债权人利益责任纠纷三级案由
     */
    @Getter
    public enum CreditorInterestDamage implements BaseEnum<String> {
        
        // (1)股东损害公司债权人利益责任纠纷
        SHAREHOLDER_DAMAGE("股东损害公司债权人利益责任纠纷", "127701"),
        
        // (2)实际控制人损害公司债权人利益责任纠纷
        CONTROLLER_DAMAGE("实际控制人损害公司债权人利益责任纠纷", "127702");

        private final String description;
        private final String code;
        private final CompanySecond parentCause = CompanySecond.CREDITOR_INTEREST_DAMAGE;

        CreditorInterestDamage(String description, String code) {
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
     * 合伙企业纠纷一级案由
     */
    @Getter
    public enum PartnershipFirst implements BaseEnum<String> {
        
        PARTNERSHIP("合伙企业纠纷", "22");

        private final String description;
        private final String code;

        PartnershipFirst(String description, String code) {
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
     * 合伙企业纠纷二级案由
     */
    @Getter
    public enum PartnershipSecond implements BaseEnum<String> {
        
        // 286.入伙纠纷
        PARTNERSHIP_ENTRY("入伙纠纷", "1286"),
        
        // 287.退伙纠纷
        PARTNERSHIP_WITHDRAWAL("退伙纠纷", "1287"),
        
        // 288.合伙企业财产份额转让纠纷
        PARTNERSHIP_SHARE_TRANSFER("合伙企业财产份额转让纠纷", "1288");

        private final String description;
        private final String code;
        private final PartnershipFirst parentCause = PartnershipFirst.PARTNERSHIP;

        PartnershipSecond(String description, String code) {
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
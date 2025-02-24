package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 不正当竞争纠纷案由
 */
public class UnfairCompetitionCause {

    /**
     * 不正当竞争纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        UNFAIR_COMPETITION("不正当竞争纠纷", "15");

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
     * 不正当竞争纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 173.仿冒纠纷
        IMITATION("仿冒纠纷", "1173"),
        
        // 174.商业贿赂不正当竞争纠纷
        COMMERCIAL_BRIBERY("商业贿赂不正当竞争纠纷", "1174"),
        
        // 175.虚假宣传纠纷
        FALSE_ADVERTISING("虚假宣传纠纷", "1175"),
        
        // 176.侵害商业秘密纠纷
        TRADE_SECRET_INFRINGEMENT("侵害商业秘密纠纷", "1176"),
        
        // 177.低价倾销不正当竞争纠纷
        DUMPING("低价倾销不正当竞争纠纷", "1177"),
        
        // 178.捆绑销售不正当竞争纠纷
        TIED_SELLING("捆绑销售不正当竞争纠纷", "1178"),
        
        // 179.有奖销售纠纷
        PRIZE_PROMOTION("有奖销售纠纷", "1179"),
        
        // 180.商业诋毁纠纷
        COMMERCIAL_DEFAMATION("商业诋毁纠纷", "1180"),
        
        // 181.串通投标不正当竞争纠纷
        BID_RIGGING("串通投标不正当竞争纠纷", "1181"),
        
        // 182.网络不正当竞争纠纷
        INTERNET_UNFAIR_COMPETITION("网络不正当竞争纠纷", "1182");

        private final String description;
        private final String code;
        private final First parentCause = First.UNFAIR_COMPETITION;

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
     * 仿冒纠纷三级案由
     */
    @Getter
    public enum Imitation implements BaseEnum<String> {
        
        // (1)擅自使用与他人有一定影响的商品名称、包装、装潢等相同或者近似的标识纠纷
        PRODUCT_DECORATION("擅自使用与他人有一定影响的商品名称、包装、装潢等相同或者近似的标识纠纷", "117301"),
        
        // (2)擅自使用他人有一定影响的企业名称、社会组织名称、姓名纠纷
        ENTERPRISE_NAME("擅自使用他人有一定影响的企业名称、社会组织名称、姓名纠纷", "117302"),
        
        // (3)擅自使用他人有一定影响的域名主体部分、网站名称、网页纠纷
        DOMAIN_NAME("擅自使用他人有一定影响的域名主体部分、网站名称、网页纠纷", "117303");

        private final String description;
        private final String code;
        private final Second parentCause = Second.IMITATION;

        Imitation(String description, String code) {
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
     * 侵害商业秘密纠纷三级案由
     */
    @Getter
    public enum TradeSecretInfringement implements BaseEnum<String> {
        
        // (1)侵害技术秘密纠纷
        TECHNICAL_SECRET("侵害技术秘密纠纷", "117601"),
        
        // (2)侵害经营秘密纠纷
        BUSINESS_SECRET("侵害经营秘密纠纷", "117602");

        private final String description;
        private final String code;
        private final Second parentCause = Second.TRADE_SECRET_INFRINGEMENT;

        TradeSecretInfringement(String description, String code) {
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
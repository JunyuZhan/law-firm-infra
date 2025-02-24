package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 知识产权合同纠纷案由
 */
public class IpCompetitionCause {

    /**
     * 知识产权合同纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        IP_CONTRACT("知识产权合同纠纷", "13");

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
     * 知识产权合同纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 146.著作权合同纠纷
        COPYRIGHT_CONTRACT("著作权合同纠纷", "1146"),
        
        // 147.商标合同纠纷
        TRADEMARK_CONTRACT("商标合同纠纷", "1147"),
        
        // 148.专利合同纠纷
        PATENT_CONTRACT("专利合同纠纷", "1148"),
        
        // 149.植物新品种合同纠纷
        PLANT_VARIETY_CONTRACT("植物新品种合同纠纷", "1149"),
        
        // 150.集成电路布图设计合同纠纷
        IC_LAYOUT_DESIGN_CONTRACT("集成电路布图设计合同纠纷", "1150"),
        
        // 151.商业秘密合同纠纷
        TRADE_SECRET_CONTRACT("商业秘密合同纠纷", "1151"),
        
        // 152.技术合同纠纷
        TECHNOLOGY_CONTRACT("技术合同纠纷", "1152"),
        
        // 153.特许经营合同纠纷
        FRANCHISE_CONTRACT("特许经营合同纠纷", "1153"),
        
        // 154.企业名称（商号）合同纠纷
        ENTERPRISE_NAME_CONTRACT("企业名称（商号）合同纠纷", "1154"),
        
        // 155.特殊标志合同纠纷
        SPECIAL_MARK_CONTRACT("特殊标志合同纠纷", "1155"),
        
        // 156.网络域名合同纠纷
        DOMAIN_NAME_CONTRACT("网络域名合同纠纷", "1156"),
        
        // 157.知识产权质押合同纠纷
        IP_PLEDGE_CONTRACT("知识产权质押合同纠纷", "1157");

        private final String description;
        private final String code;
        private final First parentCause = First.IP_CONTRACT;

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
     * 著作权合同纠纷三级案由
     */
    @Getter
    public enum CopyrightContract implements BaseEnum<String> {
        
        // (1)委托创作合同纠纷
        COMMISSIONED_CREATION("委托创作合同纠纷", "114601"),
        
        // (2)合作创作合同纠纷
        COOPERATIVE_CREATION("合作创作合同纠纷", "114602"),
        
        // (3)著作权转让合同纠纷
        COPYRIGHT_TRANSFER("著作权转让合同纠纷", "114603"),
        
        // (4)著作权许可使用合同纠纷
        COPYRIGHT_LICENSE("著作权许可使用合同纠纷", "114604"),
        
        // (5)出版合同纠纷
        PUBLISHING("出版合同纠纷", "114605"),
        
        // (6)表演合同纠纷
        PERFORMANCE("表演合同纠纷", "114606"),
        
        // (7)音像制品制作合同纠纷
        AUDIO_VIDEO_PRODUCTION("音像制品制作合同纠纷", "114607"),
        
        // (8)广播电视播放合同纠纷
        BROADCASTING("广播电视播放合同纠纷", "114608"),
        
        // (9)邻接权转让合同纠纷
        NEIGHBORING_RIGHT_TRANSFER("邻接权转让合同纠纷", "114609"),
        
        // (10)邻接权许可使用合同纠纷
        NEIGHBORING_RIGHT_LICENSE("邻接权许可使用合同纠纷", "114610"),
        
        // (11)计算机软件开发合同纠纷
        SOFTWARE_DEVELOPMENT("计算机软件开发合同纠纷", "114611"),
        
        // (12)计算机软件著作权转让合同纠纷
        SOFTWARE_COPYRIGHT_TRANSFER("计算机软件著作权转让合同纠纷", "114612"),
        
        // (13)计算机软件著作权许可使用合同纠纷
        SOFTWARE_COPYRIGHT_LICENSE("计算机软件著作权许可使用合同纠纷", "114613");

        private final String description;
        private final String code;
        private final Second parentCause = Second.COPYRIGHT_CONTRACT;

        CopyrightContract(String description, String code) {
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
     * 商标合同纠纷三级案由
     */
    @Getter
    public enum TrademarkContract implements BaseEnum<String> {
        
        // (1)商标权转让合同纠纷
        TRADEMARK_TRANSFER("商标权转让合同纠纷", "114701"),
        
        // (2)商标使用许可合同纠纷
        TRADEMARK_LICENSE("商标使用许可合同纠纷", "114702"),
        
        // (3)商标代理合同纠纷
        TRADEMARK_AGENCY("商标代理合同纠纷", "114703");

        private final String description;
        private final String code;
        private final Second parentCause = Second.TRADEMARK_CONTRACT;

        TrademarkContract(String description, String code) {
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
     * 专利合同纠纷三级案由
     */
    @Getter
    public enum PatentContract implements BaseEnum<String> {
        
        // (1)专利申请权转让合同纠纷
        PATENT_APPLICATION_TRANSFER("专利申请权转让合同纠纷", "114801"),
        
        // (2)专利权转让合同纠纷
        PATENT_RIGHT_TRANSFER("专利权转让合同纠纷", "114802"),
        
        // (3)发明专利实施许可合同纠纷
        INVENTION_PATENT_LICENSE("发明专利实施许可合同纠纷", "114803"),
        
        // (4)实用新型专利实施许可合同纠纷
        UTILITY_MODEL_LICENSE("实用新型专利实施许可合同纠纷", "114804"),
        
        // (5)外观设计专利实施许可合同纠纷
        DESIGN_PATENT_LICENSE("外观设计专利实施许可合同纠纷", "114805"),
        
        // (6)专利代理合同纠纷
        PATENT_AGENCY("专利代理合同纠纷", "114806");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PATENT_CONTRACT;

        PatentContract(String description, String code) {
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
     * 植物新品种合同纠纷三级案由
     */
    @Getter
    public enum PlantVarietyContract implements BaseEnum<String> {
        
        // (1)植物新品种育种合同纠纷
        BREEDING("植物新品种育种合同纠纷", "114901"),
        
        // (2)植物新品种申请权转让合同纠纷
        APPLICATION_RIGHT_TRANSFER("植物新品种申请权转让合同纠纷", "114902"),
        
        // (3)植物新品种权转让合同纠纷
        VARIETY_RIGHT_TRANSFER("植物新品种权转让合同纠纷", "114903"),
        
        // (4)植物新品种实施许可合同纠纷
        VARIETY_LICENSE("植物新品种实施许可合同纠纷", "114904");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PLANT_VARIETY_CONTRACT;

        PlantVarietyContract(String description, String code) {
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
     * 集成电路布图设计合同纠纷三级案由
     */
    @Getter
    public enum ICLayoutDesignContract implements BaseEnum<String> {
        
        // (1)集成电路布图设计创作合同纠纷
        CREATION("集成电路布图设计创作合同纠纷", "115001"),
        
        // (2)集成电路布图设计专有权转让合同纠纷
        RIGHT_TRANSFER("集成电路布图设计专有权转让合同纠纷", "115002"),
        
        // (3)集成电路布图设计许可使用合同纠纷
        LICENSE("集成电路布图设计许可使用合同纠纷", "115003");

        private final String description;
        private final String code;
        private final Second parentCause = Second.IC_LAYOUT_DESIGN_CONTRACT;

        ICLayoutDesignContract(String description, String code) {
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
     * 商业秘密合同纠纷三级案由
     */
    @Getter
    public enum TradeSecretContract implements BaseEnum<String> {
        
        // (1)技术秘密让与合同纠纷
        TECHNICAL_SECRET_TRANSFER("技术秘密让与合同纠纷", "115101"),
        
        // (2)技术秘密许可使用合同纠纷
        TECHNICAL_SECRET_LICENSE("技术秘密许可使用合同纠纷", "115102"),
        
        // (3)经营秘密让与合同纠纷
        BUSINESS_SECRET_TRANSFER("经营秘密让与合同纠纷", "115103"),
        
        // (4)经营秘密许可使用合同纠纷
        BUSINESS_SECRET_LICENSE("经营秘密许可使用合同纠纷", "115104");

        private final String description;
        private final String code;
        private final Second parentCause = Second.TRADE_SECRET_CONTRACT;

        TradeSecretContract(String description, String code) {
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
     * 技术合同纠纷三级案由
     */
    @Getter
    public enum TechnologyContract implements BaseEnum<String> {
        
        // (1)技术委托开发合同纠纷
        COMMISSIONED_DEVELOPMENT("技术委托开发合同纠纷", "115201"),
        
        // (2)技术合作开发合同纠纷
        COOPERATIVE_DEVELOPMENT("技术合作开发合同纠纷", "115202"),
        
        // (3)技术转化合同纠纷
        TECHNOLOGY_TRANSFORMATION("技术转化合同纠纷", "115203"),
        
        // (4)技术转让合同纠纷
        TECHNOLOGY_TRANSFER("技术转让合同纠纷", "115204"),
        
        // (5)技术许可合同纠纷
        TECHNOLOGY_LICENSE("技术许可合同纠纷", "115205"),
        
        // (6)技术咨询合同纠纷
        TECHNOLOGY_CONSULTING("技术咨询合同纠纷", "115206"),
        
        // (7)技术服务合同纠纷
        TECHNOLOGY_SERVICE("技术服务合同纠纷", "115207"),
        
        // (8)技术培训合同纠纷
        TECHNOLOGY_TRAINING("技术培训合同纠纷", "115208"),
        
        // (9)技术中介合同纠纷
        TECHNOLOGY_INTERMEDIARY("技术中介合同纠纷", "115209"),
        
        // (10)技术进口合同纠纷
        TECHNOLOGY_IMPORT("技术进口合同纠纷", "115210"),
        
        // (11)技术出口合同纠纷
        TECHNOLOGY_EXPORT("技术出口合同纠纷", "115211"),
        
        // (12)职务技术成果完成人奖励、报酬纠纷
        WORK_ACHIEVEMENT_REWARD("职务技术成果完成人奖励、报酬纠纷", "115212"),
        
        // (13)技术成果完成人署名权、荣誉权、奖励权纠纷
        ACHIEVEMENT_RIGHTS("技术成果完成人署名权、荣誉权、奖励权纠纷", "115213");

        private final String description;
        private final String code;
        private final Second parentCause = Second.TECHNOLOGY_CONTRACT;

        TechnologyContract(String description, String code) {
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
     * 企业名称（商号）合同纠纷三级案由
     */
    @Getter
    public enum EnterpriseNameContract implements BaseEnum<String> {
        
        // (1)企业名称（商号）转让合同纠纷
        NAME_TRANSFER("企业名称（商号）转让合同纠纷", "115401"),
        
        // (2)企业名称（商号）使用合同纠纷
        NAME_USE("企业名称（商号）使用合同纠纷", "115402");

        private final String description;
        private final String code;
        private final Second parentCause = Second.ENTERPRISE_NAME_CONTRACT;

        EnterpriseNameContract(String description, String code) {
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
     * 网络域名合同纠纷三级案由
     */
    @Getter
    public enum DomainNameContract implements BaseEnum<String> {
        
        // (1)网络域名注册合同纠纷
        REGISTRATION("网络域名注册合同纠纷", "115601"),
        
        // (2)网络域名转让合同纠纷
        TRANSFER("网络域名转让合同纠纷", "115602"),
        
        // (3)网络域名许可使用合同纠纷
        LICENSE("网络域名许可使用合同纠纷", "115603");

        private final String description;
        private final String code;
        private final Second parentCause = Second.DOMAIN_NAME_CONTRACT;

        DomainNameContract(String description, String code) {
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

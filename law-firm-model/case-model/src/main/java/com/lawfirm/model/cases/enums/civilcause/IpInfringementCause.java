package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 知识产权权属、侵权纠纷案由
 */
public class IpInfringementCause {

    /**
     * 知识产权权属、侵权纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        IP_INFRINGEMENT("知识产权权属、侵权纠纷", "14");

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
     * 知识产权权属、侵权纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 158.著作权权属、侵权纠纷
        COPYRIGHT_INFRINGEMENT("著作权权属、侵权纠纷", "1158"),
        
        // 159.商标权权属、侵权纠纷
        TRADEMARK_INFRINGEMENT("商标权权属、侵权纠纷", "1159"),
        
        // 160.专利权权属、侵权纠纷
        PATENT_INFRINGEMENT("专利权权属、侵权纠纷", "1160"),
        
        // 161.植物新品种权权属、侵权纠纷
        PLANT_VARIETY_INFRINGEMENT("植物新品种权权属、侵权纠纷", "1161"),
        
        // 162.集成电路布图设计专有权权属、侵权纠纷
        IC_LAYOUT_DESIGN_INFRINGEMENT("集成电路布图设计专有权权属、侵权纠纷", "1162"),
        
        // 163.侵害企业名称（商号）权纠纷
        ENTERPRISE_NAME_INFRINGEMENT("侵害企业名称（商号）权纠纷", "1163"),
        
        // 164.侵害特殊标志专有权纠纷
        SPECIAL_MARK_INFRINGEMENT("侵害特殊标志专有权纠纷", "1164"),
        
        // 165.网络域名权属、侵权纠纷
        DOMAIN_NAME_INFRINGEMENT("网络域名权属、侵权纠纷", "1165"),
        
        // 166.发现权纠纷
        DISCOVERY_RIGHT("发现权纠纷", "1166"),
        
        // 167.发明权纠纷
        INVENTION_RIGHT("发明权纠纷", "1167"),
        
        // 168.其他科技成果权纠纷
        OTHER_TECH_ACHIEVEMENT("其他科技成果权纠纷", "1168"),
        
        // 169.确认不侵害知识产权纠纷
        NON_INFRINGEMENT_CONFIRMATION("确认不侵害知识产权纠纷", "1169"),
        
        // 170.因申请知识产权临时措施损害责任纠纷
        TEMPORARY_MEASURE_DAMAGE("因申请知识产权临时措施损害责任纠纷", "1170"),
        
        // 171.因恶意提起知识产权诉讼损害责任纠纷
        MALICIOUS_LITIGATION_DAMAGE("因恶意提起知识产权诉讼损害责任纠纷", "1171"),
        
        // 172.专利权宣告无效后返还费用纠纷
        PATENT_INVALIDATION_REFUND("专利权宣告无效后返还费用纠纷", "1172");

        private final String description;
        private final String code;
        private final First parentCause = First.IP_INFRINGEMENT;

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
     * 著作权权属、侵权纠纷三级案由
     */
    @Getter
    public enum CopyrightInfringement implements BaseEnum<String> {
        
        // (1)著作权权属纠纷
        COPYRIGHT_OWNERSHIP("著作权权属纠纷", "115801"),
        
        // (2)侵害作品发表权纠纷
        PUBLICATION_RIGHT("侵害作品发表权纠纷", "115802"),
        
        // (3)侵害作品署名权纠纷
        AUTHORSHIP_RIGHT("侵害作品署名权纠纷", "115803"),
        
        // (4)侵害作品修改权纠纷
        MODIFICATION_RIGHT("侵害作品修改权纠纷", "115804"),
        
        // (5)侵害保护作品完整权纠纷
        INTEGRITY_RIGHT("侵害保护作品完整权纠纷", "115805"),
        
        // (6)侵害作品复制权纠纷
        REPRODUCTION_RIGHT("侵害作品复制权纠纷", "115806"),
        
        // (7)侵害作品发行权纠纷
        DISTRIBUTION_RIGHT("侵害作品发行权纠纷", "115807"),
        
        // (8)侵害作品出租权纠纷
        RENTAL_RIGHT("侵害作品出租权纠纷", "115808"),
        
        // (9)侵害作品展览权纠纷
        EXHIBITION_RIGHT("侵害作品展览权纠纷", "115809"),
        
        // (10)侵害作品表演权纠纷
        PERFORMANCE_RIGHT("侵害作品表演权纠纷", "115810"),
        
        // (11)侵害作品放映权纠纷
        PROJECTION_RIGHT("侵害作品放映权纠纷", "115811"),
        
        // (12)侵害作品广播权纠纷
        BROADCASTING_RIGHT("侵害作品广播权纠纷", "115812"),
        
        // (13)侵害作品信息网络传播权纠纷
        NETWORK_TRANSMISSION_RIGHT("侵害作品信息网络传播权纠纷", "115813"),
        
        // (14)侵害作品摄制权纠纷
        FILMING_RIGHT("侵害作品摄制权纠纷", "115814"),
        
        // (15)侵害作品改编权纠纷
        ADAPTATION_RIGHT("侵害作品改编权纠纷", "115815"),
        
        // (16)侵害作品翻译权纠纷
        TRANSLATION_RIGHT("侵害作品翻译权纠纷", "115816"),
        
        // (17)侵害作品汇编权纠纷
        COMPILATION_RIGHT("侵害作品汇编权纠纷", "115817"),
        
        // (18)侵害其他著作财产权纠纷
        OTHER_PROPERTY_RIGHT("侵害其他著作财产权纠纷", "115818"),
        
        // (19)出版者权权属纠纷
        PUBLISHER_RIGHT("出版者权权属纠纷", "115819"),
        
        // (20)表演者权权属纠纷
        PERFORMER_RIGHT("表演者权权属纠纷", "115820"),
        
        // (21)录音录像制作者权权属纠纷
        PRODUCER_RIGHT("录音录像制作者权权属纠纷", "115821"),
        
        // (22)广播组织权权属纠纷
        BROADCASTER_RIGHT("广播组织权权属纠纷", "115822"),
        
        // (23)侵害出版者权纠纷
        PUBLISHER_INFRINGEMENT("侵害出版者权纠纷", "115823"),
        
        // (24)侵害表演者权纠纷
        PERFORMER_INFRINGEMENT("侵害表演者权纠纷", "115824"),
        
        // (25)侵害录音录像制作者权纠纷
        PRODUCER_INFRINGEMENT("侵害录音录像制作者权纠纷", "115825"),
        
        // (26)侵害广播组织权纠纷
        BROADCASTER_INFRINGEMENT("侵害广播组织权纠纷", "115826"),
        
        // (27)计算机软件著作权权属纠纷
        SOFTWARE_COPYRIGHT_OWNERSHIP("计算机软件著作权权属纠纷", "115827"),
        
        // (28)侵害计算机软件著作权纠纷
        SOFTWARE_COPYRIGHT_INFRINGEMENT("侵害计算机软件著作权纠纷", "115828");

        private final String description;
        private final String code;
        private final Second parentCause = Second.COPYRIGHT_INFRINGEMENT;

        CopyrightInfringement(String description, String code) {
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
     * 商标权权属、侵权纠纷三级案由
     */
    @Getter
    public enum TrademarkInfringement implements BaseEnum<String> {
        
        // (1)商标权权属纠纷
        TRADEMARK_OWNERSHIP("商标权权属纠纷", "115901"),
        
        // (2)侵害商标权纠纷
        TRADEMARK_INFRINGEMENT("侵害商标权纠纷", "115902");

        private final String description;
        private final String code;
        private final Second parentCause = Second.TRADEMARK_INFRINGEMENT;

        TrademarkInfringement(String description, String code) {
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
     * 专利权权属、侵权纠纷三级案由
     */
    @Getter
    public enum PatentInfringement implements BaseEnum<String> {
        
        // (1)专利申请权权属纠纷
        PATENT_APPLICATION_OWNERSHIP("专利申请权权属纠纷", "116001"),
        
        // (2)专利权权属纠纷
        PATENT_OWNERSHIP("专利权权属纠纷", "116002"),
        
        // (3)侵害发明专利权纠纷
        INVENTION_PATENT_INFRINGEMENT("侵害发明专利权纠纷", "116003"),
        
        // (4)侵害实用新型专利权纠纷
        UTILITY_MODEL_INFRINGEMENT("侵害实用新型专利权纠纷", "116004"),
        
        // (5)侵害外观设计专利权纠纷
        DESIGN_PATENT_INFRINGEMENT("侵害外观设计专利权纠纷", "116005"),
        
        // (6)假冒他人专利纠纷
        PATENT_COUNTERFEITING("假冒他人专利纠纷", "116006"),
        
        // (7)发明专利临时保护期使用费纠纷
        TEMPORARY_PROTECTION_FEE("发明专利临时保护期使用费纠纷", "116007"),
        
        // (8)职务发明创造发明人、设计人奖励、报酬纠纷
        WORK_INVENTION_REWARD("职务发明创造发明人、设计人奖励、报酬纠纷", "116008"),
        
        // (9)发明创造发明人、设计人署名权纠纷
        INVENTOR_AUTHORSHIP("发明创造发明人、设计人署名权纠纷", "116009"),
        
        // (10)标准必要专利使用费纠纷
        STANDARD_ESSENTIAL_PATENT_FEE("标准必要专利使用费纠纷", "116010");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PATENT_INFRINGEMENT;

        PatentInfringement(String description, String code) {
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
     * 植物新品种权权属、侵权纠纷三级案由
     */
    @Getter
    public enum PlantVarietyInfringement implements BaseEnum<String> {
        
        // (1)植物新品种申请权权属纠纷
        APPLICATION_RIGHT_OWNERSHIP("植物新品种申请权权属纠纷", "116101"),
        
        // (2)植物新品种权权属纠纷
        VARIETY_RIGHT_OWNERSHIP("植物新品种权权属纠纷", "116102"),
        
        // (3)侵害植物新品种权纠纷
        VARIETY_RIGHT_INFRINGEMENT("侵害植物新品种权纠纷", "116103"),
        
        // (4)植物新品种临时保护期使用费纠纷
        TEMPORARY_PROTECTION_FEE("植物新品种临时保护期使用费纠纷", "116104");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PLANT_VARIETY_INFRINGEMENT;

        PlantVarietyInfringement(String description, String code) {
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
     * 集成电路布图设计专有权权属、侵权纠纷三级案由
     */
    @Getter
    public enum ICLayoutDesignInfringement implements BaseEnum<String> {
        
        // (1)集成电路布图设计专有权权属纠纷
        LAYOUT_DESIGN_OWNERSHIP("集成电路布图设计专有权权属纠纷", "116201"),
        
        // (2)侵害集成电路布图设计专有权纠纷
        LAYOUT_DESIGN_INFRINGEMENT("侵害集成电路布图设计专有权纠纷", "116202");

        private final String description;
        private final String code;
        private final Second parentCause = Second.IC_LAYOUT_DESIGN_INFRINGEMENT;

        ICLayoutDesignInfringement(String description, String code) {
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
     * 网络域名权属、侵权纠纷三级案由
     */
    @Getter
    public enum DomainNameInfringement implements BaseEnum<String> {
        
        // (1)网络域名权属纠纷
        DOMAIN_NAME_OWNERSHIP("网络域名权属纠纷", "116501"),
        
        // (2)侵害网络域名纠纷
        DOMAIN_NAME_INFRINGEMENT("侵害网络域名纠纷", "116502");

        private final String description;
        private final String code;
        private final Second parentCause = Second.DOMAIN_NAME_INFRINGEMENT;

        DomainNameInfringement(String description, String code) {
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
     * 确认不侵害知识产权纠纷三级案由
     */
    @Getter
    public enum NonInfringementConfirmation implements BaseEnum<String> {
        
        // (1)确认不侵害专利权纠纷
        PATENT_NON_INFRINGEMENT("确认不侵害专利权纠纷", "116901"),
        
        // (2)确认不侵害商标权纠纷
        TRADEMARK_NON_INFRINGEMENT("确认不侵害商标权纠纷", "116902"),
        
        // (3)确认不侵害著作权纠纷
        COPYRIGHT_NON_INFRINGEMENT("确认不侵害著作权纠纷", "116903"),
        
        // (4)确认不侵害植物新品种权纠纷
        PLANT_VARIETY_NON_INFRINGEMENT("确认不侵害植物新品种权纠纷", "116904"),
        
        // (5)确认不侵害集成电路布图设计专用权纠纷
        IC_LAYOUT_NON_INFRINGEMENT("确认不侵害集成电路布图设计专用权纠纷", "116905"),
        
        // (6)确认不侵害计算机软件著作权纠纷
        SOFTWARE_NON_INFRINGEMENT("确认不侵害计算机软件著作权纠纷", "116906");

        private final String description;
        private final String code;
        private final Second parentCause = Second.NON_INFRINGEMENT_CONFIRMATION;

        NonInfringementConfirmation(String description, String code) {
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
     * 因申请知识产权临时措施损害责任纠纷三级案由
     */
    @Getter
    public enum TemporaryMeasureDamage implements BaseEnum<String> {
        
        // (1)因申请诉前停止侵害专利权损害责任纠纷
        PATENT_PRELIMINARY_INJUNCTION("因申请诉前停止侵害专利权损害责任纠纷", "117001"),
        
        // (2)因申请诉前停止侵害注册商标专用权损害责任纠纷
        TRADEMARK_PRELIMINARY_INJUNCTION("因申请诉前停止侵害注册商标专用权损害责任纠纷", "117002"),
        
        // (3)因申请诉前停止侵害著作权损害责任纠纷
        COPYRIGHT_PRELIMINARY_INJUNCTION("因申请诉前停止侵害著作权损害责任纠纷", "117003"),
        
        // (4)因申请诉前停止侵害植物新品种权损害责任纠纷
        PLANT_VARIETY_PRELIMINARY_INJUNCTION("因申请诉前停止侵害植物新品种权损害责任纠纷", "117004"),
        
        // (5)因申请海关知识产权保护措施损害责任纠纷
        CUSTOMS_PROTECTION("因申请海关知识产权保护措施损害责任纠纷", "117005"),
        
        // (6)因申请诉前停止侵害计算机软件著作权损害责任纠纷
        SOFTWARE_PRELIMINARY_INJUNCTION("因申请诉前停止侵害计算机软件著作权损害责任纠纷", "117006"),
        
        // (7)因申请诉前停止侵害集成电路布图设计专用权损害责任纠纷
        IC_LAYOUT_PRELIMINARY_INJUNCTION("因申请诉前停止侵害集成电路布图设计专用权损害责任纠纷", "117007");

        private final String description;
        private final String code;
        private final Second parentCause = Second.TEMPORARY_MEASURE_DAMAGE;

        TemporaryMeasureDamage(String description, String code) {
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
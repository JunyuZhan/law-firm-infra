package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 物权纠纷案由
 */
public class PropertyRightCause {

    /**
     * 三、物权纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        PROPERTY_RIGHT("物权纠纷", "03");

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
     * 物权纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 四、不动产登记纠纷
        // 35.异议登记不当损害责任纠纷
        REGISTRATION_OBJECTION("异议登记不当损害责任纠纷", "0335"),
        
        // 36.虚假登记损害责任纠纷
        REGISTRATION_FALSE("虚假登记损害责任纠纷", "0336"),
        
        // 五、物权保护纠纷
        // 37.物权确认纠纷
        PROPERTY_CONFIRM("物权确认纠纷", "0337"),
        
        // 38.返还原物纠纷
        RETURN_PROPERTY("返还原物纠纷", "0338"),
        
        // 39.排除妨害纠纷
        REMOVE_NUISANCE("排除妨害纠纷", "0339"),
        
        // 40.消除危险纠纷
        ELIMINATE_DANGER("消除危险纠纷", "0340"),
        
        // 41.修理、重作、更换纠纷
        REPAIR_REMAKE("修理、重作、更换纠纷", "0341"),
        
        // 42.恢复原状纠纷
        RESTORE_STATUS("恢复原状纠纷", "0342"),
        
        // 43.财产损害赔偿纠纷
        PROPERTY_DAMAGE("财产损害赔偿纠纷", "0343"),
        
        // 六、所有权纠纷
        // 44.侵害集体经济组织成员权益纠纷
        COLLECTIVE_MEMBER_RIGHTS("侵害集体经济组织成员权益纠纷", "0344"),
        
        // 45.建筑物区分所有权纠纷
        BUILDING_OWNERSHIP("建筑物区分所有权纠纷", "0345"),
        
        // 46.业主撤销权纠纷
        OWNER_REVOCATION("业主撤销权纠纷", "0346"),
        
        // 47.业主知情权纠纷
        OWNER_INFORMATION("业主知情权纠纷", "0347"),
        
        // 48.遗失物返还纠纷
        LOST_PROPERTY("遗失物返还纠纷", "0348"),
        
        // 49.漂流物返还纠纷
        DRIFTING_PROPERTY("漂流物返还纠纷", "0349"),
        
        // 50.埋藏物返还纠纷
        BURIED_PROPERTY("埋藏物返还纠纷", "0350"),
        
        // 51.隐藏物返还纠纷
        HIDDEN_PROPERTY("隐藏物返还纠纷", "0351"),
        
        // 52.添附物归属纠纷
        ATTACHED_PROPERTY("添附物归属纠纷", "0352"),
        
        // 53.相邻关系纠纷
        NEIGHBOR_RELATION("相邻关系纠纷", "0353"),
        
        // 54.共有纠纷
        COMMON_OWNERSHIP("共有纠纷", "0354"),
        
        // 七、用益物权纠纷
        // 55.海域使用权纠纷
        SEA_USE("海域使用权纠纷", "0355"),
        
        // 56.探矿权纠纷
        MINING_EXPLORATION("探矿权纠纷", "0356"),
        
        // 57.采矿权纠纷
        MINING("采矿权纠纷", "0357"),
        
        // 58.取水权纠纷
        WATER_INTAKE("取水权纠纷", "0358"),
        
        // 59.养殖权纠纷
        AQUACULTURE("养殖权纠纷", "0359"),
        
        // 60.捕捞权纠纷
        FISHING("捕捞权纠纷", "0360"),
        
        // 61.土地承包经营权纠纷
        LAND_CONTRACT("土地承包经营权纠纷", "0361"),
        
        // 62.土地经营权纠纷
        LAND_MANAGEMENT("土地经营权纠纷", "0362"),
        
        // 63.建设用地使用权纠纷
        CONSTRUCTION_LAND("建设用地使用权纠纷", "0363"),
        
        // 64.宅基地使用权纠纷
        HOMESTEAD("宅基地使用权纠纷", "0364"),
        
        // 65.居住权纠纷
        RESIDENCE("居住权纠纷", "0365"),
        
        // 66.地役权纠纷
        EASEMENT("地役权纠纷", "0366"),
        
        // 八、担保物权纠纷
        // 67.抵押权纠纷
        MORTGAGE("抵押权纠纷", "0367"),
        
        // 68.质权纠纷
        PLEDGE("质权纠纷", "0368"),
        
        // 69.留置权纠纷
        LIEN("留置权纠纷", "0369"),
        
        // 九、占有保护纠纷
        // 70.占有物返还纠纷
        POSSESSION_RETURN("占有物返还纠纷", "0370"),
        
        // 71.占有排除妨害纠纷
        POSSESSION_NUISANCE("占有排除妨害纠纷", "0371"),
        
        // 72.占有消除危险纠纷
        POSSESSION_DANGER("占有消除危险纠纷", "0372"),
        
        // 73.占有物损害赔偿纠纷
        POSSESSION_DAMAGE("占有物损害赔偿纠纷", "0373");

        private final String description;
        private final String code;
        private final First parentCause = First.PROPERTY_RIGHT;

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
     * 物权确认纠纷三级案由
     */
    @Getter
    public enum PropertyConfirm implements BaseEnum<String> {
        
        // (1)所有权确认纠纷
        OWNERSHIP("所有权确认纠纷", "033701"),
        
        // (2)用益物权确认纠纷
        USUFRUCT("用益物权确认纠纷", "033702"),
        
        // (3)担保物权确认纠纷
        SECURITY("担保物权确认纠纷", "033703");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PROPERTY_CONFIRM;

        PropertyConfirm(String description, String code) {
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
     * 建筑物区分所有权纠纷三级案由
     */
    @Getter
    public enum BuildingOwnership implements BaseEnum<String> {
        
        // (1)业主专有权纠纷
        EXCLUSIVE_RIGHT("业主专有权纠纷", "034501"),
        
        // (2)业主共有权纠纷
        COMMON_RIGHT("业主共有权纠纷", "034502"),
        
        // (3)车位纠纷
        PARKING_SPACE("车位纠纷", "034503"),
        
        // (4)车库纠纷
        GARAGE("车库纠纷", "034504");

        private final String description;
        private final String code;
        private final Second parentCause = Second.BUILDING_OWNERSHIP;

        BuildingOwnership(String description, String code) {
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
     * 相邻关系纠纷三级案由
     */
    @Getter
    public enum NeighborRelation implements BaseEnum<String> {
        
        // (1)相邻用水、排水纠纷
        WATER("相邻用水、排水纠纷", "035301"),
        
        // (2)相邻通行纠纷
        PASSAGE("相邻通行纠纷", "035302"),
        
        // (3)相邻土地、建筑物利用关系纠纷
        LAND_BUILDING("相邻土地、建筑物利用关系纠纷", "035303"),
        
        // (4)相邻通风纠纷
        VENTILATION("相邻通风纠纷", "035304"),
        
        // (5)相邻采光、日照纠纷
        LIGHTING("相邻采光、日照纠纷", "035305"),
        
        // (6)相邻污染侵害纠纷
        POLLUTION("相邻污染侵害纠纷", "035306"),
        
        // (7)相邻损害防免关系纠纷
        DAMAGE_PREVENTION("相邻损害防免关系纠纷", "035307");

        private final String description;
        private final String code;
        private final Second parentCause = Second.NEIGHBOR_RELATION;

        NeighborRelation(String description, String code) {
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
     * 共有纠纷三级案由
     */
    @Getter
    public enum CommonOwnership implements BaseEnum<String> {
        
        // (1)共有权确认纠纷
        CONFIRM("共有权确认纠纷", "035401"),
        
        // (2)共有物分割纠纷
        DIVISION("共有物分割纠纷", "035402"),
        
        // (3)共有人优先购买权纠纷
        PRIORITY_PURCHASE("共有人优先购买权纠纷", "035403"),
        
        // (4)债权人代位析产纠纷
        CREDITOR_DIVISION("债权人代位析产纠纷", "035404");

        private final String description;
        private final String code;
        private final Second parentCause = Second.COMMON_OWNERSHIP;

        CommonOwnership(String description, String code) {
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
     * 土地承包经营权纠纷三级案由
     */
    @Getter
    public enum LandContract implements BaseEnum<String> {
        
        // (1)土地承包经营权确认纠纷
        CONFIRM("土地承包经营权确认纠纷", "036101"),
        
        // (2)承包地征收补偿费用分配纠纷
        COMPENSATION("承包地征收补偿费用分配纠纷", "036102"),
        
        // (3)土地承包经营权继承纠纷
        INHERITANCE("土地承包经营权继承纠纷", "036103");

        private final String description;
        private final String code;
        private final Second parentCause = Second.LAND_CONTRACT;

        LandContract(String description, String code) {
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
     * 抵押权纠纷三级案由
     */
    @Getter
    public enum Mortgage implements BaseEnum<String> {
        
        // (1)建筑物和其他土地附着物抵押权纠纷
        BUILDING("建筑物和其他土地附着物抵押权纠纷", "036701"),
        
        // (2)在建建筑物抵押权纠纷
        CONSTRUCTION("在建建筑物抵押权纠纷", "036702"),
        
        // (3)建设用地使用权抵押权纠纷
        LAND_USE("建设用地使用权抵押权纠纷", "036703"),
        
        // (4)土地经营权抵押权纠纷
        LAND_MANAGEMENT("土地经营权抵押权纠纷", "036704"),
        
        // (5)探矿权抵押权纠纷
        MINING_EXPLORATION("探矿权抵押权纠纷", "036705"),
        
        // (6)采矿权抵押权纠纷
        MINING("采矿权抵押权纠纷", "036706"),
        
        // (7)海域使用权抵押权纠纷
        SEA_USE("海域使用权抵押权纠纷", "036707"),
        
        // (8)动产抵押权纠纷
        MOVABLE_PROPERTY("动产抵押权纠纷", "036708"),
        
        // (9)在建船舶、航空器抵押权纠纷
        VESSEL_AIRCRAFT("在建船舶、航空器抵押权纠纷", "036709"),
        
        // (10)动产浮动抵押权纠纷
        FLOATING_CHARGE("动产浮动抵押权纠纷", "036710"),
        
        // (11)最高额抵押权纠纷
        MAXIMUM_AMOUNT("最高额抵押权纠纷", "036711");

        private final String description;
        private final String code;
        private final Second parentCause = Second.MORTGAGE;

        Mortgage(String description, String code) {
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
     * 质权纠纷三级案由
     */
    @Getter
    public enum Pledge implements BaseEnum<String> {
        
        // (1)动产质权纠纷
        MOVABLE_PROPERTY("动产质权纠纷", "036801"),
        
        // (2)转质权纠纷
        REPLEDGE("转质权纠纷", "036802"),
        
        // (3)最高额质权纠纷
        MAXIMUM_AMOUNT("最高额质权纠纷", "036803"),
        
        // (4)票据质权纠纷
        BILL("票据质权纠纷", "036804"),
        
        // (5)债券质权纠纷
        BOND("债券质权纠纷", "036805"),
        
        // (6)存单质权纠纷
        DEPOSIT_RECEIPT("存单质权纠纷", "036806"),
        
        // (7)仓单质权纠纷
        WAREHOUSE_RECEIPT("仓单质权纠纷", "036807"),
        
        // (8)提单质权纠纷
        BILL_OF_LADING("提单质权纠纷", "036808"),
        
        // (9)股权质权纠纷
        EQUITY("股权质权纠纷", "036809"),
        
        // (10)基金份额质权纠纷
        FUND_SHARE("基金份额质权纠纷", "036810"),
        
        // (11)知识产权质权纠纷
        INTELLECTUAL_PROPERTY("知识产权质权纠纷", "036811"),
        
        // (12)应收账款质权纠纷
        ACCOUNTS_RECEIVABLE("应收账款质权纠纷", "036812");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PLEDGE;

        Pledge(String description, String code) {
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
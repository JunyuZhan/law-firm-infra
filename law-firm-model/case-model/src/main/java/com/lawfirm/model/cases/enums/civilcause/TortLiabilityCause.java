package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 侵权责任纠纷案由
 */
public class TortLiabilityCause {

    /**
     * 侵权责任纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        TORT_LIABILITY("侵权责任纠纷", "31");

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
     * 侵权责任纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 364.监护人责任纠纷
        GUARDIAN_LIABILITY("监护人责任纠纷", "1364"),
        
        // 365.用人单位责任纠纷
        EMPLOYER_LIABILITY("用人单位责任纠纷", "1365"),
        
        // 366.劳务派遣工作人员侵权责任纠纷
        DISPATCHED_WORKER_LIABILITY("劳务派遣工作人员侵权责任纠纷", "1366"),
        
        // 367.提供劳务者致害责任纠纷
        SERVICE_PROVIDER_DAMAGE("提供劳务者致害责任纠纷", "1367"),
        
        // 368.提供劳务者受害责任纠纷
        SERVICE_PROVIDER_INJURY("提供劳务者受害责任纠纷", "1368"),
        
        // 369.网络侵权责任纠纷
        NETWORK_TORT("网络侵权责任纠纷", "1369"),
        
        // 370.违反安全保障义务责任纠纷
        SAFETY_OBLIGATION("违反安全保障义务责任纠纷", "1370"),
        
        // 371.教育机构责任纠纷
        EDUCATIONAL_INSTITUTION("教育机构责任纠纷", "1371"),
        
        // 372.性骚扰损害责任纠纷
        SEXUAL_HARASSMENT("性骚扰损害责任纠纷", "1372"),
        
        // 373.产品责任纠纷
        PRODUCT_LIABILITY("产品责任纠纷", "1373"),
        
        // 374.机动车交通事故责任纠纷
        MOTOR_VEHICLE_ACCIDENT("机动车交通事故责任纠纷", "1374"),
        
        // 375.非机动车交通事故责任纠纷
        NON_MOTOR_VEHICLE_ACCIDENT("非机动车交通事故责任纠纷", "1375"),
        
        // 376.医疗损害责任纠纷
        MEDICAL_DAMAGE("医疗损害责任纠纷", "1376"),
        
        // 377.环境污染责任纠纷
        ENVIRONMENTAL_POLLUTION("环境污染责任纠纷", "1377"),
        
        // 378.生态破坏责任纠纷
        ECOLOGICAL_DAMAGE("生态破坏责任纠纷", "1378"),
        
        // 379.高度危险责任纠纷
        HIGH_RISK("高度危险责任纠纷", "1379"),
        
        // 380.饲养动物损害责任纠纷
        ANIMAL_KEEPING("饲养动物损害责任纠纷", "1380"),
        
        // 381.建筑物和物件损害责任纠纷
        BUILDING_OBJECT_DAMAGE("建筑物和物件损害责任纠纷", "1381"),
        
        // 382.触电人身损害责任纠纷
        ELECTRIC_SHOCK("触电人身损害责任纠纷", "1382"),
        
        // 383.义务帮工人受害责任纠纷
        VOLUNTEER_HELPER_INJURY("义务帮工人受害责任纠纷", "1383"),
        
        // 384.见义勇为人受害责任纠纷
        GOOD_SAMARITAN_INJURY("见义勇为人受害责任纠纷", "1384"),
        
        // 385.公证损害责任纠纷
        NOTARY_DAMAGE("公证损害责任纠纷", "1385"),
        
        // 386.防卫过当损害责任纠纷
        EXCESSIVE_DEFENSE("防卫过当损害责任纠纷", "1386"),
        
        // 387.紧急避险损害责任纠纷
        EMERGENCY_AVOIDANCE("紧急避险损害责任纠纷", "1387"),
        
        // 388.驻香港、澳门特别行政区军人执行职务侵权责任纠纷
        HK_MACAO_MILITARY("驻香港、澳门特别行政区军人执行职务侵权责任纠纷", "1388"),
        
        // 389.铁路运输损害责任纠纷
        RAILWAY_TRANSPORT_DAMAGE("铁路运输损害责任纠纷", "1389"),
        
        // 390.水上运输损害责任纠纷
        WATER_TRANSPORT_DAMAGE("水上运输损害责任纠纷", "1390"),
        
        // 391.航空运输损害责任纠纷
        AIR_TRANSPORT_DAMAGE("航空运输损害责任纠纷", "1391"),
        
        // 392.因申请财产保全损害责任纠纷
        PROPERTY_PRESERVATION_DAMAGE("因申请财产保全损害责任纠纷", "1392"),
        
        // 393.因申请行为保全损害责任纠纷
        BEHAVIOR_PRESERVATION_DAMAGE("因申请行为保全损害责任纠纷", "1393"),
        
        // 394.因申请证据保全损害责任纠纷
        EVIDENCE_PRESERVATION_DAMAGE("因申请证据保全损害责任纠纷", "1394"),
        
        // 395.因申请先予执行损害责任纠纷
        ADVANCE_EXECUTION_DAMAGE("因申请先予执行损害责任纠纷", "1395");

        private final String description;
        private final String code;
        private final First parentCause = First.TORT_LIABILITY;

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
     * 网络侵权责任纠纷三级案由
     */
    @Getter
    public enum NetworkTort implements BaseEnum<String> {
        
        // (1)网络侵害虚拟财产纠纷
        VIRTUAL_PROPERTY("网络侵害虚拟财产纠纷", "136901");

        private final String description;
        private final String code;
        private final Second parentCause = Second.NETWORK_TORT;

        NetworkTort(String description, String code) {
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
     * 违反安全保障义务责任纠纷三级案由
     */
    @Getter
    public enum SafetyObligation implements BaseEnum<String> {
        
        // (1)经营场所、公共场所的经营者、管理者责任纠纷
        BUSINESS_PUBLIC_PLACE("经营场所、公共场所的经营者、管理者责任纠纷", "137001"),
        
        // (2)群众性活动组织者责任纠纷
        MASS_ACTIVITY_ORGANIZER("群众性活动组织者责任纠纷", "137002");

        private final String description;
        private final String code;
        private final Second parentCause = Second.SAFETY_OBLIGATION;

        SafetyObligation(String description, String code) {
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
     * 产品责任纠纷三级案由
     */
    @Getter
    public enum ProductLiability implements BaseEnum<String> {
        
        // (1)产品生产者责任纠纷
        PRODUCER("产品生产者责任纠纷", "137301"),
        
        // (2)产品销售者责任纠纷
        SELLER("产品销售者责任纠纷", "137302"),
        
        // (3)产品运输者责任纠纷
        TRANSPORTER("产品运输者责任纠纷", "137303"),
        
        // (4)产品仓储者责任纠纷
        STORAGE_PROVIDER("产品仓储者责任纠纷", "137304");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PRODUCT_LIABILITY;

        ProductLiability(String description, String code) {
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
     * 医疗损害责任纠纷三级案由
     */
    @Getter
    public enum MedicalDamage implements BaseEnum<String> {
        
        // (1)侵害患者知情同意权责任纠纷
        INFORMED_CONSENT("侵害患者知情同意权责任纠纷", "137601"),
        
        // (2)医疗产品责任纠纷
        MEDICAL_PRODUCT("医疗产品责任纠纷", "137602");

        private final String description;
        private final String code;
        private final Second parentCause = Second.MEDICAL_DAMAGE;

        MedicalDamage(String description, String code) {
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
     * 环境污染责任纠纷三级案由
     */
    @Getter
    public enum EnvironmentalPollution implements BaseEnum<String> {
        
        // (1)大气污染责任纠纷
        AIR_POLLUTION("大气污染责任纠纷", "137701"),
        
        // (2)水污染责任纠纷
        WATER_POLLUTION("水污染责任纠纷", "137702"),
        
        // (3)土壤污染责任纠纷
        SOIL_POLLUTION("土壤污染责任纠纷", "137703"),
        
        // (4)电子废物污染责任纠纷
        ELECTRONIC_WASTE("电子废物污染责任纠纷", "137704"),
        
        // (5)固体废物污染责任纠纷
        SOLID_WASTE("固体废物污染责任纠纷", "137705"),
        
        // (6)噪声污染责任纠纷
        NOISE_POLLUTION("噪声污染责任纠纷", "137706"),
        
        // (7)光污染责任纠纷
        LIGHT_POLLUTION("光污染责任纠纷", "137707"),
        
        // (8)放射性污染责任纠纷
        RADIATION_POLLUTION("放射性污染责任纠纷", "137708");

        private final String description;
        private final String code;
        private final Second parentCause = Second.ENVIRONMENTAL_POLLUTION;

        EnvironmentalPollution(String description, String code) {
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
     * 高度危险责任纠纷三级案由
     */
    @Getter
    public enum HighRisk implements BaseEnum<String> {
        
        // (1)民用核设施、核材料损害责任纠纷
        NUCLEAR_FACILITY("民用核设施、核材料损害责任纠纷", "137901"),
        
        // (2)民用航空器损害责任纠纷
        CIVIL_AIRCRAFT("民用航空器损害责任纠纷", "137902"),
        
        // (3)占有、使用高度危险物损害责任纠纷
        POSSESSION_USE("占有、使用高度危险物损害责任纠纷", "137903"),
        
        // (4)高度危险活动损害责任纠纷
        DANGEROUS_ACTIVITY("高度危险活动损害责任纠纷", "137904"),
        
        // (5)遗失、抛弃高度危险物损害责任纠纷
        LOST_ABANDONED("遗失、抛弃高度危险物损害责任纠纷", "137905"),
        
        // (6)非法占有高度危险物损害责任纠纷
        ILLEGAL_POSSESSION("非法占有高度危险物损害责任纠纷", "137906");

        private final String description;
        private final String code;
        private final Second parentCause = Second.HIGH_RISK;

        HighRisk(String description, String code) {
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
     * 建筑物和物件损害责任纠纷三级案由
     */
    @Getter
    public enum BuildingObjectDamage implements BaseEnum<String> {
        
        // (1)物件脱落、坠落损害责任纠纷
        OBJECT_FALLING("物件脱落、坠落损害责任纠纷", "138101"),
        
        // (2)建筑物、构筑物倒塌、塌陷损害责任纠纷
        BUILDING_COLLAPSE("建筑物、构筑物倒塌、塌陷损害责任纠纷", "138102"),
        
        // (3)高空抛物、坠物损害责任纠纷
        HIGH_ALTITUDE_FALLING("高空抛物、坠物损害责任纠纷", "138103"),
        
        // (4)堆放物倒塌、滚落、滑落损害责任纠纷
        STACKED_OBJECT_FALLING("堆放物倒塌、滚落、滑落损害责任纠纷", "138104"),
        
        // (5)公共道路妨碍通行损害责任纠纷
        ROAD_OBSTRUCTION("公共道路妨碍通行损害责任纠纷", "138105"),
        
        // (6)林木折断、倾倒、果实坠落损害责任纠纷
        TREE_FALLING("林木折断、倾倒、果实坠落损害责任纠纷", "138106"),
        
        // (7)地面施工、地下设施损害责任纠纷
        GROUND_UNDERGROUND("地面施工、地下设施损害责任纠纷", "138107");

        private final String description;
        private final String code;
        private final Second parentCause = Second.BUILDING_OBJECT_DAMAGE;

        BuildingObjectDamage(String description, String code) {
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
     * 铁路运输损害责任纠纷三级案由
     */
    @Getter
    public enum RailwayTransportDamage implements BaseEnum<String> {
        
        // (1)铁路运输人身损害责任纠纷
        PERSONAL_INJURY("铁路运输人身损害责任纠纷", "138901"),
        
        // (2)铁路运输财产损害责任纠纷
        PROPERTY_DAMAGE("铁路运输财产损害责任纠纷", "138902");

        private final String description;
        private final String code;
        private final Second parentCause = Second.RAILWAY_TRANSPORT_DAMAGE;

        RailwayTransportDamage(String description, String code) {
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
     * 水上运输损害责任纠纷三级案由
     */
    @Getter
    public enum WaterTransportDamage implements BaseEnum<String> {
        
        // (1)水上运输人身损害责任纠纷
        PERSONAL_INJURY("水上运输人身损害责任纠纷", "139001"),
        
        // (2)水上运输财产损害责任纠纷
        PROPERTY_DAMAGE("水上运输财产损害责任纠纷", "139002");

        private final String description;
        private final String code;
        private final Second parentCause = Second.WATER_TRANSPORT_DAMAGE;

        WaterTransportDamage(String description, String code) {
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
     * 航空运输损害责任纠纷三级案由
     */
    @Getter
    public enum AirTransportDamage implements BaseEnum<String> {
        
        // (1)航空运输人身损害责任纠纷
        PERSONAL_INJURY("航空运输人身损害责任纠纷", "139101"),
        
        // (2)航空运输财产损害责任纠纷
        PROPERTY_DAMAGE("航空运输财产损害责任纠纷", "139102");

        private final String description;
        private final String code;
        private final Second parentCause = Second.AIR_TRANSPORT_DAMAGE;

        AirTransportDamage(String description, String code) {
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
package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 非讼程序案件案由
 */
public class NonLitigationProcedureCause {

    /**
     * 非讼程序案件一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        NON_LITIGATION_PROCEDURE("非讼程序案件案由", "10");

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
     * 非讼程序案件二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        // 选民资格案件
        VOTER_QUALIFICATION("选民资格案件", "1396"),
        
        // 宣告失踪、宣告死亡案件
        DECLARATION_MISSING_DEATH("宣告失踪、宣告死亡案件", "1397"),
        
        // 认定自然人无民事行为能力、限制民事行为能力案件
        CIVIL_CAPACITY("认定自然人无民事行为能力、限制民事行为能力案件", "1402"),
        
        // 指定遗产管理人案件
        ESTATE_ADMINISTRATOR("指定遗产管理人案件", "1406"),
        
        // 认定财产无主案件
        OWNERLESS_PROPERTY("认定财产无主案件", "1407"),
        
        // 确认调解协议案件
        MEDIATION_AGREEMENT("确认调解协议案件", "1409"),
        
        // 实现担保物权案件
        SECURITY_INTEREST("实现担保物权案件", "1411"),
        
        // 监护权特别程序案件
        GUARDIANSHIP("监护权特别程序案件", "1413"),
        
        // 督促程序案件
        PAYMENT_ORDER("督促程序案件", "1418"),
        
        // 公示催告程序案件
        PUBLIC_SUMMONS("公示催告程序案件", "1419"),
        
        // 公司清算案件
        COMPANY_LIQUIDATION("公司清算案件", "1420"),
        
        // 破产程序案件
        BANKRUPTCY("破产程序案件", "1421"),
        
        // 申请诉前停止侵害知识产权案件
        PRE_LITIGATION_IP("申请诉前停止侵害知识产权案件", "1425"),
        
        // 申请保全案件
        PRESERVATION("申请保全案件", "1431"),
        
        // 申请人身安全保护令案件
        PERSONAL_PROTECTION("申请人身安全保护令案件", "1442"),
        
        // 申请人格权侵害禁令案件
        PERSONALITY_RIGHT("申请人格权侵害禁令案件", "1443"),
        
        // 仲裁程序案件
        ARBITRATION("仲裁程序案件", "1444"),
        
        // 海事诉讼特别程序案件
        MARITIME_SPECIAL("海事诉讼特别程序案件", "1446"),
        
        // 申请承认与执行法院判决、仲裁裁决案件
        RECOGNITION_ENFORCEMENT("申请承认与执行法院判决、仲裁裁决案件", "1453");

        private final String description;
        private final String code;
        private final First parentCause = First.NON_LITIGATION_PROCEDURE;

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
     * 选民资格三级案由
     */
    @Getter
    public enum VoterQualification implements BaseEnum<String> {
        CONFIRM_VOTER_QUALIFICATION("申请确定选民资格", "139601");

        private final String description;
        private final String code;
        private final Second parentCause = Second.VOTER_QUALIFICATION;

        VoterQualification(String description, String code) {
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
     * 宣告失踪、死亡三级案由
     */
    @Getter
    public enum DeclarationMissingDeath implements BaseEnum<String> {
        DECLARE_MISSING("申请宣告自然人失踪", "139701"),
        REVOKE_MISSING("申请撤销宣告失踪判决", "139702"),
        APPOINT_PROPERTY_MANAGER("申请为失踪人财产指定、变更代管人", "139703"),
        DECLARE_DEATH("申请宣告自然人死亡", "139704"),
        REVOKE_DEATH("申请撤销宣告自然人死亡判决", "139705");

        private final String description;
        private final String code;
        private final Second parentCause = Second.DECLARATION_MISSING_DEATH;

        DeclarationMissingDeath(String description, String code) {
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
     * 民事行为能力认定三级案由
     */
    @Getter
    public enum CivilCapacity implements BaseEnum<String> {
        DECLARE_NO_CAPACITY("申请宣告自然人无民事行为能力", "140201"),
        DECLARE_LIMITED_CAPACITY("申请宣告自然人限制民事行为能力", "140202"),
        RESTORE_LIMITED_CAPACITY("申请宣告自然人恢复限制民事行为能力", "140203"),
        RESTORE_FULL_CAPACITY("申请宣告自然人恢复完全民事行为能力", "140204");

        private final String description;
        private final String code;
        private final Second parentCause = Second.CIVIL_CAPACITY;

        CivilCapacity(String description, String code) {
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
     * 遗产管理人三级案由
     */
    @Getter
    public enum EstateAdministrator implements BaseEnum<String> {
        APPOINT_ADMINISTRATOR("申请指定遗产管理人", "140601");

        private final String description;
        private final String code;
        private final Second parentCause = Second.ESTATE_ADMINISTRATOR;

        EstateAdministrator(String description, String code) {
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
     * 认定财产无主三级案由
     */
    @Getter
    public enum OwnerlessProperty implements BaseEnum<String> {
        CONFIRM_OWNERLESS("申请认定财产无主", "140701"),
        REVOKE_OWNERLESS("申请撤销认定财产无主判决", "140702");

        private final String description;
        private final String code;
        private final Second parentCause = Second.OWNERLESS_PROPERTY;

        OwnerlessProperty(String description, String code) {
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
     * 确认调解协议三级案由
     */
    @Getter
    public enum MediationAgreement implements BaseEnum<String> {
        CONFIRM_MEDIATION("申请司法确认调解协议", "140901"),
        REVOKE_MEDIATION("申请撤销确认调解协议裁定", "141001");

        private final String description;
        private final String code;
        private final Second parentCause = Second.MEDIATION_AGREEMENT;

        MediationAgreement(String description, String code) {
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
     * 实现担保物权三级案由
     */
    @Getter
    public enum SecurityInterest implements BaseEnum<String> {
        REALIZE_SECURITY("申请实现担保物权", "141101"),
        REVOKE_SECURITY("申请撤销准许实现担保物权裁定", "141102");

        private final String description;
        private final String code;
        private final Second parentCause = Second.SECURITY_INTEREST;

        SecurityInterest(String description, String code) {
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
     * 监护权特别程序三级案由
     */
    @Getter
    public enum Guardianship implements BaseEnum<String> {
        CONFIRM_GUARDIAN("申请确定监护人", "141301"),
        APPOINT_GUARDIAN("申请指定监护人", "141302"),
        CHANGE_GUARDIAN("申请变更监护人", "141303"),
        REVOKE_GUARDIAN("申请撤销监护人资格", "141304"),
        RESTORE_GUARDIAN("申请恢复监护人资格", "141305");

        private final String description;
        private final String code;
        private final Second parentCause = Second.GUARDIANSHIP;

        Guardianship(String description, String code) {
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
     * 破产程序三级案由
     */
    @Getter
    public enum Bankruptcy implements BaseEnum<String> {
        LIQUIDATION("申请破产清算", "142101"),
        REORGANIZATION("申请破产重整", "142102"),
        RECONCILIATION("申请破产和解", "142103"),
        ADDITIONAL_DISTRIBUTION("申请对破产财产追加分配", "142104");

        private final String description;
        private final String code;
        private final Second parentCause = Second.BANKRUPTCY;

        Bankruptcy(String description, String code) {
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
     * 诉前停止侵害知识产权三级案由
     */
    @Getter
    public enum PreLitigationIP implements BaseEnum<String> {
        PATENT("申请诉前停止侵害专利权", "142501"),
        TRADEMARK("申请诉前停止侵害注册商标专用权", "142502"),
        COPYRIGHT("申请诉前停止侵害著作权", "142503"),
        PLANT_VARIETY("申请诉前停止侵害植物新品种权", "142504"),
        SOFTWARE("申请诉前停止侵害计算机软件著作权", "142505"),
        IC_LAYOUT("申请诉前停止侵害集成电路布图设计专用权", "142506");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PRE_LITIGATION_IP;

        PreLitigationIP(String description, String code) {
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
     * 申请保全三级案由
     */
    @Getter
    public enum Preservation implements BaseEnum<String> {
        PRE_LITIGATION_PROPERTY("申请诉前财产保全", "143101"),
        PRE_LITIGATION_ACTION("申请诉前行为保全", "143102"),
        PRE_LITIGATION_EVIDENCE("申请诉前证据保全", "143103"),
        PRE_ARBITRATION_PROPERTY("申请仲裁前财产保全", "143104"),
        PRE_ARBITRATION_ACTION("申请仲裁前行为保全", "143105"),
        PRE_ARBITRATION_EVIDENCE("申请仲裁前证据保全", "143106"),
        ARBITRATION_PROPERTY("仲裁程序中的财产保全", "143107"),
        ARBITRATION_EVIDENCE("仲裁程序中的证据保全", "143108"),
        PRE_ENFORCEMENT_PROPERTY("申请执行前财产保全", "143109"),
        STOP_LETTER_OF_CREDIT("申请中止支付信用证项下款项", "143110"),
        STOP_LETTER_OF_GUARANTEE("申请中止支付保函项下款项", "143111");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PRESERVATION;

        Preservation(String description, String code) {
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
     * 海事诉讼特别程序三级案由
     */
    @Getter
    public enum MaritimeSpecial implements BaseEnum<String> {
        MARITIME_PRESERVATION("申请海事请求保全", "144601"),
        MARITIME_PAYMENT_ORDER("申请海事支付令", "144701"),
        MARITIME_INJUNCTION("申请海事强制令", "144801"),
        MARITIME_EVIDENCE("申请海事证据保全", "144901"),
        MARITIME_LIABILITY_FUND("申请设立海事赔偿责任限制基金", "145001"),
        MARITIME_PRIORITY_NOTICE("申请船舶优先权催告", "145101"),
        MARITIME_DEBT_REGISTRATION("申请海事债权登记与受偿", "145201");

        private final String description;
        private final String code;
        private final Second parentCause = Second.MARITIME_SPECIAL;

        MaritimeSpecial(String description, String code) {
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
     * 申请承认与执行判决、仲裁裁决三级案由
     */
    @Getter
    public enum RecognitionEnforcement implements BaseEnum<String> {
        MARITIME_ARBITRATION("申请执行海事仲裁裁决", "145301"),
        IP_ARBITRATION("申请执行知识产权仲裁裁决", "145401"),
        FOREIGN_ARBITRATION("申请执行涉外仲裁裁决", "145501"),
        HK_CIVIL_JUDGMENT("申请认可和执行香港特别行政区法院民事判决", "145601"),
        HK_ARBITRATION("申请认可和执行香港特别行政区仲裁裁决", "145701"),
        MACAO_CIVIL_JUDGMENT("申请认可和执行澳门特别行政区法院民事判决", "145801"),
        MACAO_ARBITRATION("申请认可和执行澳门特别行政区仲裁裁决", "145901"),
        TAIWAN_CIVIL_JUDGMENT("申请认可和执行台湾地区法院民事判决", "146001"),
        TAIWAN_ARBITRATION("申请认可和执行台湾地区仲裁裁决", "146101"),
        FOREIGN_CIVIL_JUDGMENT("申请承认和执行外国法院民事判决、裁定", "146201"),
        FOREIGN_ARBITRATION_AWARD("申请承认和执行外国仲裁裁决", "146301");

        private final String description;
        private final String code;
        private final Second parentCause = Second.RECOGNITION_ENFORCEMENT;

        RecognitionEnforcement(String description, String code) {
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
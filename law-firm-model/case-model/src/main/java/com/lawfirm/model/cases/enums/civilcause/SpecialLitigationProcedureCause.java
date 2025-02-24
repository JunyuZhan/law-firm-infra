package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 特殊诉讼程序案件案由
 */
public class SpecialLitigationProcedureCause {

    /**
     * 特殊诉讼程序案件一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        SPECIAL_LITIGATION_PROCEDURE("特殊诉讼程序案件案由", "11");

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
     * 特殊诉讼程序案件二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        // 与宣告失踪、宣告死亡案件有关的纠纷
        MISSING_DEATH_RELATED("与宣告失踪、宣告死亡案件有关的纠纷", "1464"),
        
        // 公益诉讼
        PUBLIC_INTEREST("公益诉讼", "1466"),
        
        // 第三人撤销之诉
        THIRD_PARTY_REVOCATION("第三人撤销之诉", "1470"),
        
        // 执行程序中的异议之诉
        EXECUTION_OBJECTION("执行程序中的异议之诉", "1471");

        private final String description;
        private final String code;
        private final First parentCause = First.SPECIAL_LITIGATION_PROCEDURE;

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
     * 与宣告失踪、宣告死亡案件有关的纠纷三级案由
     */
    @Getter
    public enum MissingDeathRelated implements BaseEnum<String> {
        MISSING_DEBT("失踪人债务支付纠纷", "146401"),
        REVOKED_DEATH_PROPERTY("被撤销死亡宣告人请求返还财产纠纷", "146501");

        private final String description;
        private final String code;
        private final Second parentCause = Second.MISSING_DEATH_RELATED;

        MissingDeathRelated(String description, String code) {
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
     * 公益诉讼三级案由
     */
    @Getter
    public enum PublicInterest implements BaseEnum<String> {
        ENVIRONMENTAL_PROTECTION("生态环境保护民事公益诉讼", "146601"),
        HERO_PROTECTION("英雄烈士保护民事公益诉讼", "146701"),
        MINOR_PROTECTION("未成年人保护民事公益诉讼", "146801"),
        CONSUMER_PROTECTION("消费者权益保护民事公益诉讼", "146901");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PUBLIC_INTEREST;

        PublicInterest(String description, String code) {
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
     * 生态环境保护民事公益诉讼四级案由
     */
    @Getter
    public enum EnvironmentalProtection implements BaseEnum<String> {
        POLLUTION("环境污染民事公益诉讼", "14660101"),
        DESTRUCTION("生态破坏民事公益诉讼", "14660102"),
        DAMAGE_COMPENSATION("生态环境损害赔偿诉讼", "14660103");

        private final String description;
        private final String code;
        private final PublicInterest parentCause = PublicInterest.ENVIRONMENTAL_PROTECTION;

        EnvironmentalProtection(String description, String code) {
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
     * 第三人撤销之诉三级案由
     */
    @Getter
    public enum ThirdPartyRevocation implements BaseEnum<String> {
        REVOCATION("第三人撤销之诉", "147001");

        private final String description;
        private final String code;
        private final Second parentCause = Second.THIRD_PARTY_REVOCATION;

        ThirdPartyRevocation(String description, String code) {
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
     * 执行程序中的异议之诉三级案由
     */
    @Getter
    public enum ExecutionObjection implements BaseEnum<String> {
        EXECUTION_OBJECTION("执行异议之诉", "147101"),
        ADD_CHANGE_EXECUTED("追加、变更被执行人异议之诉", "147201"),
        DISTRIBUTION_PLAN("执行分配方案异议之诉", "147301");

        private final String description;
        private final String code;
        private final Second parentCause = Second.EXECUTION_OBJECTION;

        ExecutionObjection(String description, String code) {
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
     * 执行异议之诉四级案由
     */
    @Getter
    public enum ExecutionObjectionDetail implements BaseEnum<String> {
        THIRD_PARTY("案外人执行异议之诉", "14710101"),
        APPLICANT("申请执行人执行异议之诉", "14710102");

        private final String description;
        private final String code;
        private final ExecutionObjection parentCause = ExecutionObjection.EXECUTION_OBJECTION;

        ExecutionObjectionDetail(String description, String code) {
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
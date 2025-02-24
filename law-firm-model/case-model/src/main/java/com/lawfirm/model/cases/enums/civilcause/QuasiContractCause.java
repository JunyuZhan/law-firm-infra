package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 准合同纠纷案由
 */
public class QuasiContractCause {

    /**
     * 不当得利纠纷一级案由
     */
    @Getter
    public enum UnjustEnrichmentFirst implements BaseEnum<String> {
        
        UNJUST_ENRICHMENT("不当得利纠纷", "11");

        private final String description;
        private final String code;

        UnjustEnrichmentFirst(String description, String code) {
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
     * 不当得利纠纷二级案由
     */
    @Getter
    public enum UnjustEnrichmentSecond implements BaseEnum<String> {
        
        // 144.不当得利纠纷
        UNJUST_ENRICHMENT("不当得利纠纷", "1144");

        private final String description;
        private final String code;
        private final UnjustEnrichmentFirst parentCause = UnjustEnrichmentFirst.UNJUST_ENRICHMENT;

        UnjustEnrichmentSecond(String description, String code) {
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
     * 无因管理纠纷一级案由
     */
    @Getter
    public enum NegotiorumGestioFirst implements BaseEnum<String> {
        
        NEGOTIORUM_GESTIO("无因管理纠纷", "12");

        private final String description;
        private final String code;

        NegotiorumGestioFirst(String description, String code) {
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
     * 无因管理纠纷二级案由
     */
    @Getter
    public enum NegotiorumGestioSecond implements BaseEnum<String> {
        
        // 145.无因管理纠纷
        NEGOTIORUM_GESTIO("无因管理纠纷", "1145");

        private final String description;
        private final String code;
        private final NegotiorumGestioFirst parentCause = NegotiorumGestioFirst.NEGOTIORUM_GESTIO;

        NegotiorumGestioSecond(String description, String code) {
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
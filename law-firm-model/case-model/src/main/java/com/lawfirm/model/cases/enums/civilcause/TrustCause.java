package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 信托纠纷案由
 */
public class TrustCause {

    /**
     * 信托纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        TRUST("信托纠纷", "26");

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
     * 信托纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 330.民事信托纠纷
        CIVIL_TRUST("民事信托纠纷", "1330"),
        
        // 331.营业信托纠纷
        BUSINESS_TRUST("营业信托纠纷", "1331"),
        
        // 332.公益信托纠纷
        PUBLIC_TRUST("公益信托纠纷", "1332");

        private final String description;
        private final String code;
        private final First parentCause = First.TRUST;

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
} 
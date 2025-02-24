package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 婚姻家庭、继承纠纷案由
 */
public class MarriageInheritanceCause {

    /**
     * 二、婚姻家庭、继承纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        MARRIAGE_INHERITANCE("婚姻家庭、继承纠纷", "02");

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
     * 婚姻家庭、继承纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 12.婚约财产纠纷
        MARRIAGE_PROPERTY("婚约财产纠纷", "0212"),
        
        // 13.婚内夫妻财产分割纠纷
        MARRIAGE_DIVISION("婚内夫妻财产分割纠纷", "0213"),
        
        // 14.离婚纠纷
        DIVORCE("离婚纠纷", "0214"),
        
        // 15.离婚后财产纠纷
        DIVORCE_PROPERTY("离婚后财产纠纷", "0215"),
        
        // 16.离婚后损害责任纠纷
        DIVORCE_DAMAGE("离婚后损害责任纠纷", "0216"),
        
        // 17.婚姻无效纠纷
        MARRIAGE_INVALID("婚姻无效纠纷", "0217"),
        
        // 18.撤销婚姻纠纷
        MARRIAGE_REVOKE("撤销婚姻纠纷", "0218"),
        
        // 19.夫妻财产约定纠纷
        MARRIAGE_AGREEMENT("夫妻财产约定纠纷", "0219"),
        
        // 20.同居关系纠纷
        COHABITATION("同居关系纠纷", "0220"),
        
        // 21.亲子关系纠纷
        PARENT_CHILD("亲子关系纠纷", "0221"),
        
        // 22.抚养纠纷
        CHILD_SUPPORT("抚养纠纷", "0222"),
        
        // 23.扶养纠纷
        SUPPORT("扶养纠纷", "0223"),
        
        // 24.赡养纠纷
        ELDER_SUPPORT("赡养纠纷", "0224"),
        
        // 25.收养关系纠纷
        ADOPTION("收养关系纠纷", "0225"),
        
        // 26.监护权纠纷
        GUARDIANSHIP("监护权纠纷", "0226"),
        
        // 27.探望权纠纷
        VISITATION("探望权纠纷", "0227"),
        
        // 28.分家析产纠纷
        FAMILY_PROPERTY("分家析产纠纷", "0228"),
        
        // 29.法定继承纠纷
        LEGAL_INHERITANCE("法定继承纠纷", "0229"),
        
        // 30.遗嘱继承纠纷
        WILL_INHERITANCE("遗嘱继承纠纷", "0230"),
        
        // 31.被继承人债务清偿纠纷
        INHERITANCE_DEBT("被继承人债务清偿纠纷", "0231"),
        
        // 32.遗赠纠纷
        LEGACY("遗赠纠纷", "0232"),
        
        // 33.遗赠扶养协议纠纷
        LEGACY_SUPPORT("遗赠扶养协议纠纷", "0233"),
        
        // 34.遗产管理纠纷
        ESTATE_MANAGEMENT("遗产管理纠纷", "0234");

        private final String description;
        private final String code;
        private final First parentCause = First.MARRIAGE_INHERITANCE;

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
     * 同居关系纠纷三级案由
     */
    @Getter
    public enum Cohabitation implements BaseEnum<String> {
        
        // (1)同居关系析产纠纷
        PROPERTY_DIVISION("同居关系析产纠纷", "022001"),
        
        // (2)同居关系子女抚养纠纷
        CHILD_SUPPORT("同居关系子女抚养纠纷", "022002");

        private final String description;
        private final String code;
        private final Second parentCause = Second.COHABITATION;

        Cohabitation(String description, String code) {
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
     * 亲子关系纠纷三级案由
     */
    @Getter
    public enum ParentChild implements BaseEnum<String> {
        
        // (1)确认亲子关系纠纷
        CONFIRM("确认亲子关系纠纷", "022101"),
        
        // (2)否认亲子关系纠纷
        DENY("否认亲子关系纠纷", "022102");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PARENT_CHILD;

        ParentChild(String description, String code) {
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
     * 抚养纠纷三级案由
     */
    @Getter
    public enum ChildSupport implements BaseEnum<String> {
        
        // (1)抚养费纠纷
        SUPPORT_FEE("抚养费纠纷", "022201"),
        
        // (2)变更抚养关系纠纷
        CHANGE_RELATION("变更抚养关系纠纷", "022202");

        private final String description;
        private final String code;
        private final Second parentCause = Second.CHILD_SUPPORT;

        ChildSupport(String description, String code) {
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
     * 扶养纠纷三级案由
     */
    @Getter
    public enum Support implements BaseEnum<String> {
        
        // (1)扶养费纠纷
        SUPPORT_FEE("扶养费纠纷", "022301"),
        
        // (2)变更扶养关系纠纷
        CHANGE_RELATION("变更扶养关系纠纷", "022302");

        private final String description;
        private final String code;
        private final Second parentCause = Second.SUPPORT;

        Support(String description, String code) {
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
     * 赡养纠纷三级案由
     */
    @Getter
    public enum ElderSupport implements BaseEnum<String> {
        
        // (1)赡养费纠纷
        SUPPORT_FEE("赡养费纠纷", "022401"),
        
        // (2)变更赡养关系纠纷
        CHANGE_RELATION("变更赡养关系纠纷", "022402");

        private final String description;
        private final String code;
        private final Second parentCause = Second.ELDER_SUPPORT;

        ElderSupport(String description, String code) {
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
     * 收养关系纠纷三级案由
     */
    @Getter
    public enum Adoption implements BaseEnum<String> {
        
        // (1)确认收养关系纠纷
        CONFIRM("确认收养关系纠纷", "022501"),
        
        // (2)解除收养关系纠纷
        DISSOLVE("解除收养关系纠纷", "022502");

        private final String description;
        private final String code;
        private final Second parentCause = Second.ADOPTION;

        Adoption(String description, String code) {
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
     * 法定继承纠纷三级案由
     */
    @Getter
    public enum LegalInheritance implements BaseEnum<String> {
        
        // (1)转继承纠纷
        TRANSFER("转继承纠纷", "022901"),
        
        // (2)代位继承纠纷
        SUBROGATION("代位继承纠纷", "022902");

        private final String description;
        private final String code;
        private final Second parentCause = Second.LEGAL_INHERITANCE;

        LegalInheritance(String description, String code) {
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
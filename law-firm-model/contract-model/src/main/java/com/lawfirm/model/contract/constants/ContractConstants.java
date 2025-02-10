package com.lawfirm.model.contract.constants;

/**
 * 合同模块常量
 */
public class ContractConstants {

    /**
     * 合同编号前缀
     */
    public static final String CONTRACT_NUMBER_PREFIX = "CT";

    /**
     * 合同文件存储路径
     */
    public static final String CONTRACT_FILE_PATH = "contract/files/";

    /**
     * 合同附件存储路径
     */
    public static final String CONTRACT_ATTACHMENT_PATH = "contract/attachments/";

    /**
     * 合同模板存储路径
     */
    public static final String CONTRACT_TEMPLATE_PATH = "contract/templates/";

    /**
     * 合同相关表名
     */
    public static class TableNames {
        public static final String CONTRACT = "ct_contract";
        public static final String CONTRACT_PARTY = "ct_contract_party";
        public static final String CONTRACT_CLAUSE = "ct_contract_clause";
        public static final String CONTRACT_APPROVAL = "ct_contract_approval";
        public static final String CONTRACT_PAYMENT = "ct_contract_payment";
        public static final String CONTRACT_ATTACHMENT = "ct_contract_attachment";
    }

    /**
     * 合同字段长度限制
     */
    public static class FieldLength {
        public static final int CONTRACT_NUMBER = 50;
        public static final int CONTRACT_NAME = 200;
        public static final int PARTY_NAME = 100;
        public static final int CLAUSE_TITLE = 200;
        public static final int CLAUSE_CONTENT = 2000;
        public static final int APPROVAL_OPINION = 500;
        public static final int ATTACHMENT_NAME = 200;
        public static final int REMARK = 500;
    }

    /**
     * 合同金额相关
     */
    public static class Amount {
        public static final int PRECISION = 19;  // 总长度
        public static final int SCALE = 2;       // 小数位数
    }

    private ContractConstants() {
        throw new IllegalStateException("Utility class");
    }
}


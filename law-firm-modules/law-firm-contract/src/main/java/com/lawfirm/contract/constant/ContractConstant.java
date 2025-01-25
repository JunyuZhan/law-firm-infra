package com.lawfirm.contract.constant;

/**
 * 合同模块常量
 */
public interface ContractConstant {

    /**
     * 合同类型
     */
    interface ContractType {
        /**
         * 常规合同
         */
        int REGULAR = 1;

        /**
         * 委托合同
         */
        int ENTRUSTED = 2;

        /**
         * 顾问合同
         */
        int CONSULTANT = 3;
    }

    /**
     * 合同状态
     */
    interface ContractStatus {
        /**
         * 草稿
         */
        int DRAFT = 0;

        /**
         * 审批中
         */
        int APPROVING = 1;

        /**
         * 已生效
         */
        int EFFECTIVE = 2;

        /**
         * 已到期
         */
        int EXPIRED = 3;

        /**
         * 已终止
         */
        int TERMINATED = 4;
    }

    /**
     * 合同条款类型
     */
    interface ClauseType {
        /**
         * 基本条款
         */
        int BASIC = 1;

        /**
         * 特殊条款
         */
        int SPECIAL = 2;

        /**
         * 其他条款
         */
        int OTHER = 3;
    }

    /**
     * 审批节点
     */
    interface ApprovalNode {
        /**
         * 部门负责人
         */
        int DEPARTMENT_MANAGER = 1;

        /**
         * 分支机构负责人
         */
        int BRANCH_MANAGER = 2;

        /**
         * 法务审核
         */
        int LEGAL_REVIEW = 3;

        /**
         * 财务审核
         */
        int FINANCE_REVIEW = 4;
    }

    /**
     * 审批状态
     */
    interface ApprovalStatus {
        /**
         * 待审批
         */
        int PENDING = 0;

        /**
         * 已通过
         */
        int APPROVED = 1;

        /**
         * 已拒绝
         */
        int REJECTED = 2;
    }
} 
package com.lawfirm.contract.constant;

/**
 * 合同模块常量定义
 */
public interface ContractConstant {
    
    /**
     * 合同状态
     */
    interface Status {
        /**
         * 草稿
         */
        int DRAFT = 0;
        
        /**
         * 审批中
         */
        int REVIEWING = 1;
        
        /**
         * 已生效
         */
        int EFFECTIVE = 2;
        
        /**
         * 履行中
         */
        int PERFORMING = 3;
        
        /**
         * 已完成
         */
        int COMPLETED = 4;
        
        /**
         * 已终止
         */
        int TERMINATED = 5;
        
        /**
         * 已作废
         */
        int INVALID = 6;
    }
    
    /**
     * 合同类型
     */
    interface Type {
        /**
         * 采购合同
         */
        String PURCHASE = "purchase";
        
        /**
         * 销售合同
         */
        String SALES = "sales";
        
        /**
         * 劳务合同
         */
        String LABOR = "labor";
        
        /**
         * 租赁合同
         */
        String LEASE = "lease";
        
        /**
         * 技术服务合同
         */
        String TECH_SERVICE = "tech_service";
        
        /**
         * 法律服务合同
         */
        String LEGAL_SERVICE = "legal_service";
        
        /**
         * 咨询服务合同
         */
        String CONSULTING = "consulting";
    }
    
    /**
     * 审批状态
     */
    interface ReviewStatus {
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
        
        /**
         * 已撤回
         */
        int WITHDRAWN = 3;
    }
    
    /**
     * 缓存相关常量
     */
    interface Cache {
        /**
         * 合同缓存名称
         */
        String CONTRACT = "contract";
        
        /**
         * 合同模板缓存
         */
        String CONTRACT_TEMPLATE = "contract_template";
    }
    
    /**
     * 合同模板状态
     */
    interface TemplateStatus {
        /**
         * 启用
         */
        int ENABLED = 1;
        
        /**
         * 禁用
         */
        int DISABLED = 0;
    }
    
    /**
     * 条款类型
     */
    interface ClauseType {
        /**
         * 标准条款
         */
        int STANDARD = 1;
        
        /**
         * 自定义条款
         */
        int CUSTOM = 2;
    }
    
    /**
     * 冲突检查状态
     */
    interface ConflictCheckStatus {
        /**
         * 未检查
         */
        int UNCHECKED = 0;
        
        /**
         * 检查中
         */
        int CHECKING = 1;
        
        /**
         * 无冲突
         */
        int NO_CONFLICT = 2;
        
        /**
         * 存在冲突
         */
        int HAS_CONFLICT = 3;
    }
    
    /**
     * 冲突类型
     */
    interface ConflictType {
        /**
         * 客户冲突
         */
        String CLIENT = "client";
        
        /**
         * 律师冲突
         */
        String LAWYER = "lawyer";
        
        /**
         * 案件冲突
         */
        String CASE = "case";
    }
} 
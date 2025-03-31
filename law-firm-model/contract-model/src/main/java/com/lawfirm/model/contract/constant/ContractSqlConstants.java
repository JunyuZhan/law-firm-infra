package com.lawfirm.model.contract.constant;

/**
 * 合同模块SQL常量类
 * 集中管理合同相关SQL查询语句，提高可维护性和安全性
 */
public class ContractSqlConstants {
    
    /**
     * 合同基础查询相关SQL常量
     */
    public static class Contract {
        /**
         * 根据合同编号查询合同
         */
        public static final String SELECT_BY_CONTRACT_NO = 
                "SELECT * FROM contract WHERE contract_no = #{contractNo} AND deleted = 0";
                
        /**
         * 根据客户ID查询合同列表
         */
        public static final String SELECT_BY_CLIENT_ID = 
                "SELECT * FROM contract WHERE client_id = #{clientId} AND deleted = 0 ORDER BY create_time DESC";
                
        /**
         * 根据案件ID查询合同列表
         */
        public static final String SELECT_BY_CASE_ID = 
                "SELECT * FROM contract WHERE case_id = #{caseId} AND deleted = 0 ORDER BY create_time DESC";
                
        /**
         * 根据状态查询合同列表
         */
        public static final String SELECT_BY_STATUS = 
                "SELECT * FROM contract WHERE status = #{status} AND deleted = 0 ORDER BY create_time DESC";
    }
    
    /**
     * 合同审核相关SQL常量
     */
    public static class Review {
        /**
         * 根据合同ID查询审核记录
         */
        public static final String SELECT_APPROVAL_BY_CONTRACT_ID = 
                "SELECT * FROM contract_approval WHERE contract_id = #{contractId} ORDER BY node ASC, approval_time DESC";
                
        /**
         * 查询用户待审核的合同
         */
        public static final String SELECT_PENDING_REVIEWS = 
                "SELECT c.* FROM contract c " +
                "INNER JOIN contract_approval a ON c.id = a.contract_id " +
                "WHERE a.approver_id = #{approverId} AND a.status = 0 AND c.deleted = 0 " +
                "ORDER BY c.create_time DESC";
                
        /**
         * 查询当前节点的审核记录
         */
        public static final String SELECT_CURRENT_NODE_APPROVAL = 
                "SELECT * FROM contract_approval WHERE contract_id = #{contractId} AND node = #{node}";
    }
    
    /**
     * 合同模板相关SQL常量
     */
    public static class Template {
        /**
         * 根据模板编码查询模板
         */
        public static final String SELECT_BY_TEMPLATE_CODE = 
                "SELECT * FROM contract_template WHERE template_code = #{templateCode} AND deleted = 0";
                
        /**
         * 查询分类下的模板列表
         */
        public static final String SELECT_BY_CATEGORY_ID = 
                "SELECT * FROM contract_template WHERE category_id = #{categoryId} AND deleted = 0 ORDER BY create_time DESC";
    }
    
    /**
     * 私有构造函数防止实例化
     */
    private ContractSqlConstants() {
        throw new IllegalStateException("常量类不应被实例化");
    }
} 
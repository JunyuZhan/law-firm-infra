package com.lawfirm.model.contract.constant;

/**
 * 合同冲突相关常量
 */
public interface ContractConflictConstant {
    
    /**
     * 缓存相关常量
     */
    interface Cache {
        /**
         * 合同冲突缓存名称
         */
        String CONTRACT_CONFLICT = "contract_conflict";
        
        /**
         * 合同冲突检查结果缓存名称
         */
        String CONFLICT_CHECK_RESULT = "conflict_check_result";
        
        /**
         * 缓存过期时间（秒）
         */
        long EXPIRE_TIME = 3600;
    }
    
    /**
     * 冲突检查相关常量
     */
    interface Check {
        /**
         * 检查结果缓存key前缀
         */
        String CHECK_RESULT_KEY_PREFIX = "conflict:check:result:";
        
        /**
         * 检查历史缓存key前缀
         */
        String CHECK_HISTORY_KEY_PREFIX = "conflict:check:history:";
        
        /**
         * 检查锁key前缀
         */
        String CHECK_LOCK_KEY_PREFIX = "conflict:check:lock:";
        
        /**
         * 检查锁过期时间（秒）
         */
        long LOCK_EXPIRE_TIME = 300;
    }
    
    /**
     * 冲突规则相关常量
     */
    interface Rule {
        /**
         * 规则缓存key前缀
         */
        String RULE_KEY_PREFIX = "conflict:rule:";
        
        /**
         * 规则组缓存key前缀
         */
        String RULE_GROUP_KEY_PREFIX = "conflict:rule:group:";
        
        /**
         * 规则白名单缓存key前缀
         */
        String RULE_WHITELIST_KEY_PREFIX = "conflict:rule:whitelist:";
    }
} 
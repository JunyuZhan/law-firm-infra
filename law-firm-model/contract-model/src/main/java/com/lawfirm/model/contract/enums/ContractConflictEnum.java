package com.lawfirm.model.contract.enums;

import lombok.Getter;

/**
 * 合同冲突相关枚举
 */
public class ContractConflictEnum {
    
    /**
     * 冲突检查状态
     */
    @Getter
    public enum CheckStatus {
        UNCHECKED(0, "未检查"),
        CHECKING(1, "检查中"),
        NO_CONFLICT(2, "无冲突"),
        HAS_CONFLICT(3, "存在冲突");
        
        private final Integer code;
        private final String desc;
        
        CheckStatus(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
    
    /**
     * 冲突类型
     */
    @Getter
    public enum ConflictType {
        CLIENT("client", "客户冲突"),
        LAWYER("lawyer", "律师冲突"),
        CASE("case", "案件冲突");
        
        private final String code;
        private final String desc;
        
        ConflictType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
    
    /**
     * 冲突级别
     */
    @Getter
    public enum ConflictLevel {
        LOW(1, "轻微"),
        MEDIUM(2, "中等"),
        HIGH(3, "严重");
        
        private final Integer code;
        private final String desc;
        
        ConflictLevel(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
} 
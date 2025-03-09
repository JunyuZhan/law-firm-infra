package com.lawfirm.model.organization.constants;

/**
 * 组织通用常量
 */
public interface OrganizationConstants {

    /**
     * 组织类型
     */
    interface OrganizationType {
        String FIRM = "FIRM";           // 律所
        String BRANCH = "BRANCH";       // 分所
        String DEPARTMENT = "DEPT";     // 部门
        String TEAM = "TEAM";           // 团队
        String PROJECT_GROUP = "GROUP"; // 项目组
    }
    
    /**
     * 变更类型
     */
    interface ChangeType {
        String CREATE = "CREATE"; // 创建
        String UPDATE = "UPDATE"; // 更新
        String DELETE = "DELETE"; // 删除
        String MOVE = "MOVE";     // 移动
        String MERGE = "MERGE";   // 合并
        String SPLIT = "SPLIT";   // 拆分
    }
    
    /**
     * 状态常量
     */
    interface Status {
        int DISABLED = 0; // 禁用
        int ENABLED = 1;  // 启用
    }
    
    /**
     * 树结构常量
     */
    interface Tree {
        String PATH_SEPARATOR = "/"; // 路径分隔符
        int MAX_DEPTH = 8;           // 最大层级深度
        int ROOT_LEVEL = 1;          // 根节点层级
    }
    
    /**
     * 关系常量
     */
    interface Relation {
        String PRIMARY = "PRIMARY";       // 主要关系
        String SECONDARY = "SECONDARY";   // 次要关系
        String TEMPORARY = "TEMPORARY";   // 临时关系
    }
} 
package com.lawfirm.model.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 流程权限实体�? * 定义流程相关权限，包括启动、查看、取消等
 *
 * @author JunyuZhan
 */
@Data
@NoArgsConstructor
@TableName("wf_process_permission")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ProcessPermission extends ModelBaseEntity {
    
    private static final long serialVersionUID = 1L;

    /**
     * 流程定义ID或流程定义键
     */
    @TableField("process_definition_key")
    private String processDefinitionKey;

    /**
     * 权限类型�?-流程定义权限�?-流程实例权限
     */
    @TableField("permission_type")
    private Integer permissionType;

    /**
     * 操作类型�?-启动�?-查看�?-处理�?-取消�?-挂起�?-激�?     */
    @TableField("operation_type")
    private Integer operationType;

    /**
     * 权限目标类型�?-角色�?-用户�?-部门
     */
    @TableField("target_type")
    private Integer targetType;

    /**
     * 权限目标ID（角色ID、用户ID或部门ID�?     */
    @TableField("target_id")
    private Long targetId;

    /**
     * 权限策略�?-允许�?-拒绝
     */
    @TableField("permission_policy")
    private Integer permissionPolicy;

    /**
     * 排序�?     */
    @TableField("sort_order")
    private Integer sortOrder;
    
    /**
     * 权限类型枚举
     */
    public enum PermissionType {
        /**
         * 流程定义权限
         */
        DEFINITION(1),
        
        /**
         * 流程实例权限
         */
        INSTANCE(2);
        
        private final int value;
        
        PermissionType(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }
    
    /**
     * 操作类型枚举
     */
    public enum OperationType {
        /**
         * 启动流程
         */
        START(1),
        
        /**
         * 查看流程
         */
        VIEW(2),
        
        /**
         * 处理流程
         */
        HANDLE(3),
        
        /**
         * 取消流程
         */
        CANCEL(4),
        
        /**
         * 挂起流程
         */
        SUSPEND(5),
        
        /**
         * 激活流�?         */
        ACTIVATE(6);
        
        private final int value;
        
        OperationType(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }
    
    /**
     * 权限目标类型枚举
     */
    public enum TargetType {
        /**
         * 角色
         */
        ROLE(1),
        
        /**
         * 用户
         */
        USER(2),
        
        /**
         * 部门
         */
        DEPARTMENT(3);
        
        private final int value;
        
        TargetType(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }
    
    /**
     * 权限策略枚举
     */
    public enum PermissionPolicy {
        /**
         * 允许
         */
        ALLOW(1),
        
        /**
         * 拒绝
         */
        DENY(2);
        
        private final int value;
        
        PermissionPolicy(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }
} 

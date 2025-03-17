package com.lawfirm.model.client.event;

import lombok.Getter;
import lombok.ToString;

/**
 * 客户变更事件
 */
@Getter
@ToString
public class ClientChangeEvent {
    
    /**
     * 变更类型
     */
    public enum ChangeType {
        /**
         * 更新
         */
        UPDATE,
        
        /**
         * 删除
         */
        DELETE,
        
        /**
         * 状态变更
         */
        STATUS_CHANGE
    }
    
    /**
     * 客户ID
     */
    private final Long clientId;
    
    /**
     * 变更类型
     */
    private final ChangeType changeType;
    
    /**
     * 新状态（可选，仅当changeType为STATUS_CHANGE时有值）
     */
    private final String newStatus;
    
    /**
     * 创建客户更新事件
     * 
     * @param clientId 客户ID
     * @return 事件实例
     */
    public static ClientChangeEvent createUpdateEvent(Long clientId) {
        return new ClientChangeEvent(clientId, ChangeType.UPDATE, null);
    }
    
    /**
     * 创建客户删除事件
     * 
     * @param clientId 客户ID
     * @return 事件实例
     */
    public static ClientChangeEvent createDeleteEvent(Long clientId) {
        return new ClientChangeEvent(clientId, ChangeType.DELETE, null);
    }
    
    /**
     * 创建客户状态变更事件
     * 
     * @param clientId 客户ID
     * @param newStatus 新状态
     * @return 事件实例
     */
    public static ClientChangeEvent createStatusChangeEvent(Long clientId, String newStatus) {
        return new ClientChangeEvent(clientId, ChangeType.STATUS_CHANGE, newStatus);
    }
    
    /**
     * 构造函数
     * 
     * @param clientId 客户ID
     * @param changeType 变更类型
     * @param newStatus 新状态
     */
    private ClientChangeEvent(Long clientId, ChangeType changeType, String newStatus) {
        this.clientId = clientId;
        this.changeType = changeType;
        this.newStatus = newStatus;
    }
} 
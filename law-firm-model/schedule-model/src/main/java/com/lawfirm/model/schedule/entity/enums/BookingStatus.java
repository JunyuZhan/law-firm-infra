package com.lawfirm.model.schedule.entity.enums;

/**
 * 会议室预订状态枚举
 */
public enum BookingStatus {
    
    /**
     * 已取消
     */
    CANCELLED(0, "已取消"),
    
    /**
     * 待审批
     */
    PENDING(1, "待审批"),
    
    /**
     * 已审批
     */
    CONFIRMED(2, "已审批"),
    
    /**
     * 已拒绝
     */
    REJECTED(3, "已拒绝"),
    
    /**
     * 已完成
     */
    COMPLETED(4, "已完成");
    
    private final int code;
    private final String description;
    
    BookingStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * 根据状态码获取枚举
     * 
     * @param code 状态码
     * @return 状态枚举
     */
    public static BookingStatus getByCode(int code) {
        for (BookingStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
} 
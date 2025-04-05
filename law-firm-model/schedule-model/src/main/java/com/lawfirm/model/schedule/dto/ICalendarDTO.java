package com.lawfirm.model.schedule.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * iCalendar格式数据传输对象
 * 用于支持iCalendar格式导入导出
 */
@Data
@Accessors(chain = true)
public class ICalendarDTO {
    
    /**
     * iCalendar数据内容
     */
    @NotBlank(message = "iCalendar数据不能为空")
    private String icalData;
    
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    
    /**
     * 是否覆盖已存在的日程
     */
    private Boolean overrideExisting = false;
    
    /**
     * 导入的日程ID列表（导入成功后返回）
     */
    private List<Long> importedScheduleIds;
    
    /**
     * 导入状态（成功、部分成功、失败）
     */
    private String importStatus;
    
    /**
     * 错误信息
     */
    private String errorMessage;
    
    /**
     * 导入成功的事件数量
     */
    private Integer successCount;
    
    /**
     * 导入失败的事件数量
     */
    private Integer failureCount;
} 
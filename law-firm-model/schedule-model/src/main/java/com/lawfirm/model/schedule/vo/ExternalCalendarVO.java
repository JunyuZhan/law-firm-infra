package com.lawfirm.model.schedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 外部日历VO
 */
@Data
@Schema(description = "外部日历VO")
public class ExternalCalendarVO {

    @Schema(description = "日历ID")
    private Long id;

    @Schema(description = "日历名称")
    private String name;

    @Schema(description = "日历描述")
    private String description;

    @Schema(description = "颜色（十六进制颜色码）")
    private String color;

    @Schema(description = "所有者用户ID")
    private Long userId;

    @Schema(description = "所有者用户名")
    private String username;

    @Schema(description = "提供商类型：1-Google, 2-Microsoft, 3-Apple, 4-其他")
    private Integer providerType;
    
    @Schema(description = "提供商类型名称")
    private String providerTypeName;
    
    @Schema(description = "外部日历ID（提供商平台的ID）")
    private String externalId;
    
    @Schema(description = "上次同步时间")
    private LocalDateTime lastSyncTime;
    
    @Schema(description = "状态：1-正常, 2-同步失败, 3-授权过期, 4-已暂停")
    private Integer status;
    
    @Schema(description = "状态名称")
    private String statusName;
    
    @Schema(description = "日程数量")
    private Integer eventCount;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 
package com.lawfirm.model.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 日历同步配置DTO
 */
@Data
@Schema(description = "日历同步配置DTO")
public class CalendarSyncConfigDTO {

    @Schema(description = "同步方向：1-单向(外部到本地), 2-单向(本地到外部), 3-双向同步")
    @NotNull(message = "同步方向不能为空")
    private Integer syncDirection;

    @Schema(description = "同步间隔（分钟）")
    private Integer syncInterval;

    @Schema(description = "同步的日历分类列表")
    private List<String> syncCategories;
} 
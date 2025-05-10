package com.lawfirm.model.archive.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 档案统计VO
 */
@Data
@Schema(description = "档案统计VO")
public class ArchiveStatisticsVO {

    /**
     * 总档案数量
     */
    @Schema(description = "总档案数量")
    private Integer totalCount;
    
    /**
     * 本月新增数量
     */
    @Schema(description = "本月新增数量")
    private Integer monthlyCount;
    
    /**
     * 已同步数量
     */
    @Schema(description = "已同步数量")
    private Integer syncedCount;
    
    /**
     * 未同步数量
     */
    @Schema(description = "未同步数量")
    private Integer unsyncedCount;
    
    /**
     * 同步失败数量
     */
    @Schema(description = "同步失败数量")
    private Integer failedCount;
    
    /**
     * 按档案类型统计
     */
    @Schema(description = "按档案类型统计")
    private List<Map<String, Object>> typeStats;
    
    /**
     * 按月份统计
     */
    @Schema(description = "按月份统计")
    private List<Map<String, Object>> monthlyStats;
    
    /**
     * 同步状态统计
     */
    @Schema(description = "同步状态统计")
    private List<Map<String, Object>> syncStats;
    
    /**
     * 部门统计
     */
    @Schema(description = "部门统计")
    private List<Map<String, Object>> deptStats;
} 
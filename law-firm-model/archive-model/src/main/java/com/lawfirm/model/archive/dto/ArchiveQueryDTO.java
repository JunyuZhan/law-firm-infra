package com.lawfirm.model.archive.dto;

import com.lawfirm.model.archive.enums.ArchiveSourceTypeEnum;
import com.lawfirm.model.archive.enums.ArchiveStatusEnum;
import com.lawfirm.model.archive.enums.ArchiveTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 档案查询DTO
 */
@Data
@Schema(description = "档案查询DTO")
public class ArchiveQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 档案编号
     */
    @Schema(description = "档案编号")
    private String archiveNo;
    
    /**
     * 档案标题
     */
    @Schema(description = "档案标题")
    private String title;
    
    /**
     * 档案类型
     */
    @Schema(description = "档案类型")
    private ArchiveTypeEnum archiveType;
    
    /**
     * 档案状态
     */
    @Schema(description = "档案状态")
    private ArchiveStatusEnum status;
    
    /**
     * 来源类型
     */
    @Schema(description = "来源类型")
    private ArchiveSourceTypeEnum sourceType;
    
    /**
     * 来源ID
     */
    @Schema(description = "来源ID")
    private String sourceId;
    
    /**
     * 经办人ID
     */
    @Schema(description = "经办人ID")
    private String handlerId;
    
    /**
     * 经办人姓名
     */
    @Schema(description = "经办人姓名")
    private String handlerName;
    
    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private String departmentId;
    
    /**
     * 归档开始时间
     */
    @Schema(description = "归档开始时间")
    private LocalDateTime archiveTimeStart;
    
    /**
     * 归档结束时间
     */
    @Schema(description = "归档结束时间")
    private LocalDateTime archiveTimeEnd;
    
    /**
     * 是否已同步（0-未同步，1-已同步）
     */
    @Schema(description = "是否已同步（0-未同步，1-已同步）")
    private Integer isSynced;
    
    /**
     * 关键词
     */
    @Schema(description = "关键词")
    private String keywords;
    
    /**
     * 页码
     */
    @Schema(description = "页码")
    private Integer pageNum = 1;
    
    /**
     * 每页大小
     */
    @Schema(description = "每页大小")
    private Integer pageSize = 10;
} 
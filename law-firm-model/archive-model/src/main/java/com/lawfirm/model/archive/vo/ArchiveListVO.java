package com.lawfirm.model.archive.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 档案列表展示VO
 */
@Data
@Schema(description = "档案列表展示VO")
public class ArchiveListVO {

    /**
     * 档案ID
     */
    @Schema(description = "档案ID")
    private String id;

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
     * 档案类型编码
     */
    @Schema(description = "档案类型编码")
    private Integer archiveTypeCode;

    /**
     * 档案类型名称
     */
    @Schema(description = "档案类型名称")
    private String archiveTypeName;

    /**
     * 档案状态编码
     */
    @Schema(description = "档案状态编码")
    private Integer statusCode;

    /**
     * 档案状态名称
     */
    @Schema(description = "档案状态名称")
    private String statusName;

    /**
     * 来源类型编码
     */
    @Schema(description = "来源类型编码")
    private Integer sourceTypeCode;

    /**
     * 来源类型名称
     */
    @Schema(description = "来源类型名称")
    private String sourceTypeName;

    /**
     * 来源ID
     */
    @Schema(description = "来源ID")
    private String sourceId;

    /**
     * 归档时间
     */
    @Schema(description = "归档时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime archiveTime;

    /**
     * 经办人姓名
     */
    @Schema(description = "经办人姓名")
    private String handlerName;

    /**
     * 是否已同步（0-未同步，1-已同步）
     */
    @Schema(description = "是否已同步")
    private Integer isSynced;

    /**
     * 同步状态展示文本
     */
    @Schema(description = "同步状态展示文本")
    private String syncedText;

    /**
     * 同步时间
     */
    @Schema(description = "同步时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime syncTime;
}
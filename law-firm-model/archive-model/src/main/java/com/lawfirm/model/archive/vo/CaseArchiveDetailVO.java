package com.lawfirm.model.archive.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件档案详情VO
 */
@Data
@Schema(description = "案件档案详情VO")
public class CaseArchiveDetailVO {

    /**
     * 档案ID
     */
    @Schema(description = "档案ID")
    private String id;

    /**
     * 案件ID
     */
    @Schema(description = "案件ID")
    private String caseId;

    /**
     * 案件编号
     */
    @Schema(description = "案件编号")
    private String caseNo;

    /**
     * 案件标题
     */
    @Schema(description = "案件标题")
    private String caseTitle;

    /**
     * 负责律师ID
     */
    @Schema(description = "负责律师ID")
    private String lawyerId;

    /**
     * 负责律师姓名
     */
    @Schema(description = "负责律师姓名")
    private String lawyerName;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String clientId;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String clientName;

    /**
     * 案件类型
     */
    @Schema(description = "案件类型")
    private String caseType;

    /**
     * 案件状态
     */
    @Schema(description = "案件状态")
    private String caseStatus;

    /**
     * 案件金额
     */
    @Schema(description = "案件金额")
    private BigDecimal caseAmount;

    /**
     * 案件开始时间
     */
    @Schema(description = "案件开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime caseStartTime;

    /**
     * 案件结束时间
     */
    @Schema(description = "案件结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime caseEndTime;

    /**
     * 归档时间
     */
    @Schema(description = "归档时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime archiveTime;

    /**
     * 归档人ID
     */
    @Schema(description = "归档人ID")
    private String archiveUserId;

    /**
     * 归档人姓名
     */
    @Schema(description = "归档人姓名")
    private String archiveUserName;

    /**
     * 归档备注
     */
    @Schema(description = "归档备注")
    private String archiveRemark;
    
    /**
     * 同步状态（0-未同步，1-同步中，2-已同步，3-同步失败）
     */
    @Schema(description = "同步状态")
    private Integer syncStatus;
    
    /**
     * 同步状态描述
     */
    @Schema(description = "同步状态描述")
    private String syncStatusDesc;
    
    /**
     * 同步时间
     */
    @Schema(description = "同步时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime syncTime;
    
    /**
     * 相关文档列表
     */
    @Schema(description = "相关文档列表")
    private List<ArchiveFileVO> fileList;
} 
package com.lawfirm.model.evidence.dto;

import com.lawfirm.model.evidence.enums.EvidenceTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 证据DTO
 * 用于证据相关的数据传输（新增、编辑、查询等）
 */
@Data
@Schema(description = "证据DTO")
public class EvidenceDTO {
    /** 证据ID */
    @Schema(description = "证据ID")
    private Long id;
    /** 关联案件ID */
    @Schema(description = "案件ID")
    private Long caseId;
    /** 证据名称 */
    @Schema(description = "证据名称")
    private String name;
    /** 证据类型 */
    @Schema(description = "证据类型")
    private EvidenceTypeEnum type;
    /** 证据来源 */
    private String source;
    /** 证明事项 */
    private String proofMatter;
    /** 提交人ID */
    private Long submitterId;
    /** 提交时间 */
    private LocalDateTime submitTime;
    /** 证据链 */
    private String evidenceChain;
    /** 质证情况 */
    private String challengeStatus;
    /** 关联文档ID列表 */
    private List<Long> documentIds;
    /** 是否归档 */
    private Boolean archived;
    /** 归档时间 */
    private LocalDateTime archiveTime;
} 
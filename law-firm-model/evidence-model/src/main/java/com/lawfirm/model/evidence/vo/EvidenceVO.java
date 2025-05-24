package com.lawfirm.model.evidence.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.lawfirm.model.evidence.enums.EvidenceTypeEnum;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 证据VO
 * 用于前端展示证据信息
 */
@Data
@Schema(description = "证据VO")
public class EvidenceVO {
    @Schema(description = "证据ID")
    private Long id;
    @Schema(description = "证据名称")
    private String name;
    @Schema(description = "证据类型")
    private EvidenceTypeEnum type;
    /** 证据类型名称（如"书证"） */
    private String typeName;
    @Schema(description = "证据来源")
    private String source;
    @Schema(description = "证明事项")
    private String proofMatter;
    /** 提交人姓名 */
    private String submitterName;
    @Schema(description = "提交时间")
    private LocalDateTime submitTime;
    @Schema(description = "证据链")
    private String evidenceChain;
    @Schema(description = "质证情况")
    private String challengeStatus;
    /** 关联文档名称列表 */
    private List<String> documentNames;
    @Schema(description = "案件ID")
    private Long caseId;
    @Schema(description = "提交人ID")
    private Long submitterId;
    @Schema(description = "是否归档")
    private Boolean archived;
    @Schema(description = "归档时间")
    private LocalDateTime archiveTime;
} 
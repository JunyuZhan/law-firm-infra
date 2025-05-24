package com.lawfirm.model.evidence.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.evidence.enums.EvidenceTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据实体
 * 用于描述案件中的单条证据及其业务属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "证据实体")
public class Evidence extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    /** 关联案件ID */
    @Schema(description = "案件ID")
    private Long caseId;
    /** 证据名称 */
    @Schema(description = "证据名称")
    private String name;
    /** 证据类型（如书证、物证等） */
    @Schema(description = "证据类型")
    private EvidenceTypeEnum type;
    /** 证据来源（如当事人、第三方等） */
    @Schema(description = "证据来源")
    private String source;
    /** 证明事项（证据拟证明的事实） */
    @Schema(description = "证明事项")
    private String proofMatter;
    /** 提交人ID */
    @Schema(description = "提交人ID")
    private Long submitterId;
    /** 提交时间 */
    @Schema(description = "提交时间")
    private LocalDateTime submitTime;
    /** 证据链（如存证编号、区块链哈希等） */
    @Schema(description = "证据链")
    private String evidenceChain;
    /** 质证情况（如有无异议、质证结论等） */
    @Schema(description = "质证情况")
    private String challengeStatus;
    /** 关联文档ID列表（如扫描件、原件、复印件等） */
    @Schema(description = "文档ID列表")
    private transient List<Long> documentIds;
    /** 是否归档 */
    @Schema(description = "是否归档")
    private Boolean archived;
    /** 归档时间 */
    @Schema(description = "归档时间")
    private LocalDateTime archiveTime;
} 
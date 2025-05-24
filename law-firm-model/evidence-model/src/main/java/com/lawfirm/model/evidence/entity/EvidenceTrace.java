package com.lawfirm.model.evidence.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 证据流转/溯源实体
 * 记录证据的采集、流转、存证、转交、归档等全生命周期信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "证据流转/溯源实体")
public class EvidenceTrace extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    /** 证据ID */
    @Schema(description = "证据ID")
    private Long evidenceId;
    /** 操作类型（采集、转交、存证、归档等） */
    @Schema(description = "操作类型")
    private String operationType;
    /** 操作人ID */
    @Schema(description = "操作人ID")
    private Long operatorId;
    /** 操作人姓名 */
    @Schema(description = "操作人姓名")
    private String operatorName;
    /** 操作时间 */
    @Schema(description = "操作时间")
    private LocalDateTime operationTime;
    /** 流转节点/描述 */
    @Schema(description = "节点")
    private String node;
    /** 存证编号/区块链哈希 */
    @Schema(description = "保存编号")
    private String preservationNo;
} 
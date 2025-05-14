package com.lawfirm.model.contract.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * 合同冲突检查实体类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("contract_conflict")
@Schema(description = "合同冲突检查实体类")
public class ContractConflict extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "合同ID")
    @TableField("contract_id")
    private Long contractId;  // 合同ID
    
    @Schema(description = "检查状态")
    @TableField("check_status")
    private Integer checkStatus; // 检查状态
    
    @Schema(description = "检查时间")
    @TableField("check_time")
    private Date checkTime;   // 检查时间
    
    @Schema(description = "检查人ID")
    @TableField("checker_id")
    private Long checkerId;   // 检查人ID
    
    @Schema(description = "冲突类型")
    @TableField("conflict_type")
    private String conflictType; // 冲突类型
    
    @Schema(description = "冲突级别")
    @TableField("conflict_level")
    private Integer conflictLevel; // 冲突级别
    
    @Schema(description = "冲突描述")
    @TableField("conflict_desc")
    private String conflictDesc; // 冲突描述
    
    @Schema(description = "冲突详情")
    @TableField("conflict_details")
    private String conflictDetails; // 冲突详情
    
    @Schema(description = "关联合同ID")
    @TableField("related_contract_id")
    private Long relatedContractId; // 关联合同ID
    
    @Schema(description = "关联客户ID")
    @TableField("related_client_id")
    private Long relatedClientId; // 关联客户ID
    
    @Schema(description = "关联律师ID")
    @TableField("related_lawyer_id")
    private Long relatedLawyerId; // 关联律师ID
    
    @Schema(description = "关联案件ID")
    @TableField("related_case_id")
    private Long relatedCaseId; // 关联案件ID
    
    @Schema(description = "解决方案")
    @TableField("resolution")
    private String resolution; // 解决方案
    
    @Schema(description = "是否已解决")
    @TableField("is_resolved")
    private Boolean isResolved; // 是否已解决
} 
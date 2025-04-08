package com.lawfirm.model.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 合同冲突VO
 */
@Data
@Schema(description = "合同冲突VO")
public class ContractConflictVO {
    
    @Schema(description = "冲突ID")
    private Long id;
    
    @Schema(description = "合同ID")
    private Long contractId;
    
    @Schema(description = "合同编号")
    private String contractNo;
    
    @Schema(description = "合同名称")
    private String contractName;
    
    @Schema(description = "检查状态(0-未检查,1-检查中,2-无冲突,3-存在冲突)")
    private Integer checkStatus;
    
    @Schema(description = "检查状态描述")
    private String checkStatusDesc;
    
    @Schema(description = "检查时间")
    private Date checkTime;
    
    @Schema(description = "检查人ID")
    private Long checkerId;
    
    @Schema(description = "检查人姓名")
    private String checkerName;
    
    @Schema(description = "冲突类型(client-客户冲突,lawyer-律师冲突,case-案件冲突)")
    private String conflictType;
    
    @Schema(description = "冲突类型描述")
    private String conflictTypeDesc;
    
    @Schema(description = "冲突级别(1-轻微,2-中等,3-严重)")
    private Integer conflictLevel;
    
    @Schema(description = "冲突级别描述")
    private String conflictLevelDesc;
    
    @Schema(description = "冲突描述")
    private String conflictDesc;
    
    @Schema(description = "冲突详情(JSON格式)")
    private String conflictDetails;
    
    @Schema(description = "关联合同ID")
    private Long relatedContractId;
    
    @Schema(description = "关联合同编号")
    private String relatedContractNo;
    
    @Schema(description = "关联合同名称")
    private String relatedContractName;
    
    @Schema(description = "解决方案")
    private String resolution;
    
    @Schema(description = "是否已解决")
    private Boolean isResolved;
    
    @Schema(description = "创建时间")
    private Date createTime;
    
    @Schema(description = "更新时间")
    private Date updateTime;
} 
package com.lawfirm.model.contract.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 合同团队实体类
 * 用于记录合同项目的律师团队成员信息
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("contract_team")
@Schema(description = "合同团队实体类，用于记录合同项目的律师团队成员信息")
public class ContractTeam extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "关联的合同ID")
    @TableField("contract_id")
    private Long contractId;        // 关联的合同ID
    
    @Schema(description = "律师ID")
    @TableField("attorney_id")
    private Long attorneyId;        // 律师ID
    
    @Schema(description = "角色类型(主办律师、协办律师、辅助人员等)")
    @TableField("role_type")
    private String roleType;        // 角色类型(主办律师、协办律师、辅助人员等)
    
    @Schema(description = "负责内容描述")
    @TableField("responsibility")
    private String responsibility;  // 负责内容描述
    
    @Schema(description = "加入团队日期")
    @TableField("join_date")
    private Date joinDate;          // 加入团队日期
    
    @Schema(description = "状态(1-活跃, 0-已退出)")
    @TableField("status")
    private Integer status;         // 状态(1-活跃, 0-已退出)
    
    @Schema(description = "计费小时数")
    @TableField("billable_hours")
    private Double billableHours;   // 计费小时数
    
    @Schema(description = "小时费率")
    @TableField("hourly_rate")
    private Double hourlyRate;      // 小时费率
    
    @Schema(description = "工作量占比(百分比)")
    @TableField("workload_percent")
    private Integer workloadPercent; // 工作量占比(百分比)
    
    @Schema(description = "备注说明")
    @TableField("remarks")
    private String remarks;         // 备注说明
} 
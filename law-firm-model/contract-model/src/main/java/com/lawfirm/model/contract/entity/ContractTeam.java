package com.lawfirm.model.contract.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
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
public class ContractTeam extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    
    @TableField("contract_id")
    private Long contractId;        // 关联的合同ID
    
    @TableField("attorney_id")
    private Long attorneyId;        // 律师ID
    
    @TableField("role_type")
    private String roleType;        // 角色类型(主办律师、协办律师、辅助人员等)
    
    @TableField("responsibility")
    private String responsibility;  // 负责内容描述
    
    @TableField("join_date")
    private Date joinDate;          // 加入团队日期
    
    @TableField("status")
    private Integer status;         // 状态(1-活跃, 0-已退出)
    
    @TableField("billable_hours")
    private Double billableHours;   // 计费小时数
    
    @TableField("hourly_rate")
    private Double hourlyRate;      // 小时费率
    
    @TableField("workload_percent")
    private Integer workloadPercent; // 工作量占比(百分比)
    
    @TableField("remarks")
    private String remarks;         // 备注说明
} 
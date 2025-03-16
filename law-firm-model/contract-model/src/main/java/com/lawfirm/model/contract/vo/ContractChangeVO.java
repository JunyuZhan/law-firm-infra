package com.lawfirm.model.contract.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 合同变更记录视图对象
 * 用于展示合同的变更历史记录
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ContractChangeVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    
    private Long contractId;      // 合同ID
    private String contractNo;    // 合同编号
    private Integer changeType;   // 变更类型(1:条款变更 2:金额变更 3:期限变更 4:项目变更 5:团队变更 6:其他)
    private String changeTypeName; // 变更类型名称
    private String changeTitle;   // 变更标题
    private String changeContent; // 变更内容描述
    private String changeBasis;   // 变更依据
    private Date changeDate;      // 变更日期
    private Long initiatorId;     // 发起人ID
    private String initiatorName; // 发起人姓名
    private Integer changeStatus; // 变更状态(0:草稿 1:审批中 2:已生效 3:已驳回)
    private String statusName;    // 状态名称
    private String beforeValue;   // 变更前数值
    private String afterValue;    // 变更后数值
    private String attachments;   // 附件信息
    private Double affectAmount;  // 影响金额
    private String affectTerms;   // 影响条款
    private Date affectDate;      // 影响日期
    private String remarks;       // 备注说明
} 
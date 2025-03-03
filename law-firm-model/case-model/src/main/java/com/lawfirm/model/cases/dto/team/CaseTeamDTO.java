package com.lawfirm.model.cases.dto.team;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.enums.team.TeamMemberRoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 案件团队数据传输对象
 * 
 * 包含团队成员的基本信息，如成员角色、负责内容、工作量分配等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseTeamDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 案件名称
     */
    private String caseName;

    /**
     * 团队成员ID（律师ID）
     */
    private Long memberId;

    /**
     * 团队成员姓名
     */
    private String memberName;

    /**
     * 团队成员角色
     */
    private TeamMemberRoleEnum memberRole;

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 负责内容
     */
    private String responsibility;

    /**
     * 工作量占比（百分比）
     */
    private BigDecimal workloadPercentage;

    /**
     * 收益分配比例（百分比）
     */
    private BigDecimal profitPercentage;

    /**
     * 加入时间
     */
    private transient LocalDateTime joinTime;

    /**
     * 退出时间
     */
    private transient LocalDateTime exitTime;

    /**
     * 是否为主要负责人
     */
    private Boolean isPrimary;

    /**
     * 操作权限（0-只读，1-编辑，2-管理）
     */
    private Integer operationPermission;

    /**
     * 查看权限（0-全部，1-部分，2-仅自己负责部分）
     */
    private Integer viewPermission;

    /**
     * 是否接收通知
     */
    private Boolean receiveNotification;

    /**
     * 通知频率（0-实时，1-每日，2-每周）
     */
    private Integer notificationFrequency;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 获取操作权限名称
     */
    public String getOperationPermissionName() {
        if (this.operationPermission == null) {
            return "未知";
        }
        
        switch (this.operationPermission) {
            case 0:
                return "只读";
            case 1:
                return "编辑";
            case 2:
                return "管理";
            default:
                return "未知";
        }
    }

    /**
     * 获取查看权限名称
     */
    public String getViewPermissionName() {
        if (this.viewPermission == null) {
            return "未知";
        }
        
        switch (this.viewPermission) {
            case 0:
                return "全部";
            case 1:
                return "部分";
            case 2:
                return "仅自己负责部分";
            default:
                return "未知";
        }
    }

    /**
     * 获取通知频率名称
     */
    public String getNotificationFrequencyName() {
        if (this.notificationFrequency == null) {
            return "未知";
        }
        
        switch (this.notificationFrequency) {
            case 0:
                return "实时";
            case 1:
                return "每日";
            case 2:
                return "每周";
            default:
                return "未知";
        }
    }

    /**
     * 判断是否为主办律师
     */
    public boolean isLeadLawyer() {
        return this.memberRole != null && 
               this.memberRole == TeamMemberRoleEnum.LEAD_LAWYER;
    }

    /**
     * 判断是否为协办律师
     */
    public boolean isAssistantLawyer() {
        return this.memberRole != null && 
               this.memberRole == TeamMemberRoleEnum.ASSOCIATE_LAWYER;
    }

    /**
     * 判断是否为实习律师
     */
    public boolean isIntern() {
        return this.memberRole != null && 
               this.memberRole == TeamMemberRoleEnum.TRAINEE_LAWYER;
    }

    /**
     * 判断是否为顾问
     */
    public boolean isConsultant() {
        return this.memberRole != null && 
               this.memberRole == TeamMemberRoleEnum.EXPERT_CONSULTANT;
    }

    /**
     * 判断是否为合伙人
     */
    public boolean isPartner() {
        return this.memberRole != null && 
               this.memberRole == TeamMemberRoleEnum.SUPERVISING_PARTNER;
    }

    /**
     * 判断是否为管理员
     */
    public boolean isAdmin() {
        return this.operationPermission != null && this.operationPermission == 2;
    }

    /**
     * 判断是否有编辑权限
     */
    public boolean hasEditPermission() {
        return this.operationPermission != null && this.operationPermission >= 1;
    }

    /**
     * 判断是否仍在团队中（未退出）
     */
    public boolean isActive() {
        return this.exitTime == null;
    }
} 
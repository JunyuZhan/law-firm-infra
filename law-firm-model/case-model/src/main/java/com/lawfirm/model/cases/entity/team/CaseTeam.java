package com.lawfirm.model.cases.entity.team;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.TenantEntity;
import com.lawfirm.model.cases.enums.team.TeamMemberRoleEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 案件团队实体类
 * 
 * 案件团队记录了参与案件处理的律师团队成员信息，包括团队成员、
 * 工作分配、时间信息、权限设置等。
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("case_team")
public class CaseTeam extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    @TableField("case_id")
    private Long caseId;

    /**
     * 案件编号
     */
    @TableField("case_number")
    private String caseNumber;

    /**
     * 案件名称
     */
    @TableField("case_name")
    private String caseName;

    /**
     * 团队成员ID（律师ID）
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * 团队成员姓名
     */
    @TableField("member_name")
    private String memberName;

    /**
     * 团队成员角色
     */
    @TableField("member_role")
    private Integer memberRole;

    /**
     * 部门ID
     */
    @TableField("department_id")
    private Long departmentId;

    /**
     * 部门名称
     */
    @TableField("department_name")
    private String departmentName;

    /**
     * 负责内容
     */
    @TableField("responsibility")
    private String responsibility;

    /**
     * 工作量占比（百分比）
     */
    @TableField("workload_percentage")
    private BigDecimal workloadPercentage;

    /**
     * 收益分配比例（百分比）
     */
    @TableField("profit_percentage")
    private BigDecimal profitPercentage;

    /**
     * 加入时间
     */
    @TableField("join_time")
    private transient LocalDateTime joinTime;

    /**
     * 退出时间
     */
    @TableField("exit_time")
    private transient LocalDateTime exitTime;

    /**
     * 是否为主要负责人
     */
    @TableField("is_primary")
    private Boolean isPrimary;

    /**
     * 操作权限（0-只读，1-编辑，2-管理）
     */
    @TableField("operation_permission")
    private Integer operationPermission;

    /**
     * 查看权限（0-全部，1-部分，2-仅自己负责部分）
     */
    @TableField("view_permission")
    private Integer viewPermission;

    /**
     * 是否接收通知
     */
    @TableField("receive_notification")
    private Boolean receiveNotification;

    /**
     * 通知频率（0-实时，1-每日，2-每周）
     */
    @TableField("notification_frequency")
    private Integer notificationFrequency;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 获取团队成员角色枚举
     */
    public TeamMemberRoleEnum getMemberRoleEnum() {
        return TeamMemberRoleEnum.valueOf(this.memberRole);
    }

    /**
     * 设置团队成员角色
     */
    public CaseTeam setMemberRoleEnum(TeamMemberRoleEnum memberRoleEnum) {
        this.memberRole = memberRoleEnum != null ? memberRoleEnum.getValue() : null;
        return this;
    }

    /**
     * 判断是否为主办律师
     */
    public boolean isLeadLawyer() {
        return this.memberRole != null && 
               this.getMemberRoleEnum() == TeamMemberRoleEnum.LEAD_LAWYER;
    }

    /**
     * 判断是否为协办律师
     */
    public boolean isAssistantLawyer() {
        return this.memberRole != null && 
               this.getMemberRoleEnum() == TeamMemberRoleEnum.ASSOCIATE_LAWYER;
    }

    /**
     * 判断是否为实习律师
     */
    public boolean isIntern() {
        return this.memberRole != null && 
               this.getMemberRoleEnum() == TeamMemberRoleEnum.TRAINEE_LAWYER;
    }

    /**
     * 判断是否为顾问
     */
    public boolean isConsultant() {
        return this.memberRole != null && 
               this.getMemberRoleEnum() == TeamMemberRoleEnum.EXPERT_CONSULTANT;
    }

    /**
     * 判断是否为合伙人
     */
    public boolean isPartner() {
        return this.memberRole != null && 
               this.getMemberRoleEnum() == TeamMemberRoleEnum.SUPERVISING_PARTNER;
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

    /**
     * 计算在团队中的天数
     */
    public long getDaysInTeam() {
        LocalDateTime start = this.joinTime != null ? this.joinTime : 
                             (this.getCreateTime() != null ? this.getCreateTime() : LocalDateTime.now());
        LocalDateTime end = this.exitTime != null ? this.exitTime : LocalDateTime.now();
        return java.time.temporal.ChronoUnit.DAYS.between(start, end);
    }
} 
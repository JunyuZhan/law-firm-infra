package com.lawfirm.model.organization.dto.team;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.organization.constants.DepartmentFieldConstants;
import com.lawfirm.model.organization.constants.OrganizationFieldConstants;
import com.lawfirm.model.organization.enums.TeamTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

/**
 * 团队数据传输对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TeamDTO extends BaseDTO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 团队编码
     */
    @NotBlank(message = "团队编码不能为空")
    @Pattern(regexp = OrganizationFieldConstants.Code.PATTERN, message = "编码格式不正确")
    @Size(min = OrganizationFieldConstants.Code.MIN_LENGTH, max = OrganizationFieldConstants.Code.MAX_LENGTH, message = "编码长度必须在{min}到{max}之间")
    private String code;

    /**
     * 团队名称
     */
    @NotBlank(message = "团队名称不能为空")
    @Size(max = DepartmentFieldConstants.Team.NAME_MAX_LENGTH, message = "名称长度不能超过{max}")
    private String name;

    /**
     * 所属律所ID
     */
    @NotNull(message = "所属律所ID不能为空")
    private Long firmId;

    /**
     * 所属部门ID
     */
    @NotNull(message = "所属部门ID不能为空")
    private Long departmentId;

    /**
     * 父级团队ID
     */
    private Long parentId;

    /**
     * 团队类型
     */
    @NotNull(message = "团队类型不能为空")
    private TeamTypeEnum type;

    /**
     * 负责人ID
     */
    @NotNull(message = "负责人ID不能为空")
    private Long leaderId;

    /**
     * 负责人姓名
     */
    @NotBlank(message = "负责人姓名不能为空")
    @Size(max = 32, message = "负责人姓名长度不能超过{max}")
    private String leaderName;

    /**
     * 联系电话
     */
    @Size(max = OrganizationFieldConstants.Contact.PHONE_MAX_LENGTH, message = "联系电话长度不能超过{max}")
    private String phone;

    /**
     * 联系邮箱
     */
    @Size(max = OrganizationFieldConstants.Contact.EMAIL_MAX_LENGTH, message = "联系邮箱长度不能超过{max}")
    private String email;

    /**
     * 团队规模（人数）
     */
    private Integer teamSize;

    /**
     * 团队职责
     */
    @Size(max = 500, message = "团队职责长度不能超过{max}")
    private String responsibility;

    /**
     * 团队状态（0-筹备中 1-运行中 2-已解散）
     */
    private Integer teamStatus;

    /**
     * 项目相关信息（仅项目组）
     */
    private transient ProjectInfo projectInfo;

    /**
     * 描述
     */
    @Size(max = OrganizationFieldConstants.Description.MAX_LENGTH, message = "描述长度不能超过{max}")
    private String description;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 项目信息（内部类）
     */
    @Data
    public static class ProjectInfo {
        
        private static final long serialVersionUID = 1L;
        
        /**
         * 项目编号
         */
        @Size(max = 32, message = "项目编号长度不能超过{max}")
        private String projectCode;

        /**
         * 项目名称
         */
        @Size(max = 128, message = "项目名称长度不能超过{max}")
        private String projectName;

        /**
         * 项目描述
         */
        @Size(max = 500, message = "项目描述长度不能超过{max}")
        private String projectDesc;

        /**
         * 项目开始日期
         */
        private transient LocalDate startDate;

        /**
         * 预计结束日期
         */
        private transient LocalDate expectedEndDate;

        /**
         * 实际结束日期
         */
        private transient LocalDate actualEndDate;

        /**
         * 项目状态（0-未开始 1-进行中 2-已完成 3-已终止）
         */
        private Integer projectStatus;

        /**
         * 项目成员ID列表
         */
        private transient List<Long> memberIds;

        /**
         * 关联案件ID列表
         */
        private transient List<Long> caseIds;

        /**
         * 关联客户ID列表
         */
        private transient List<Long> clientIds;

        /**
         * 项目进度（0-100）
         */
        private Integer progress;

        /**
         * 项目优先级（1-低 2-中 3-高）
         */
        private Integer priority;
    }
} 
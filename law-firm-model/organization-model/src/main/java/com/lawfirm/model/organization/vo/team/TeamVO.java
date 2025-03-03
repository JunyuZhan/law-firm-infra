package com.lawfirm.model.organization.vo.team;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.organization.enums.TeamTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

/**
 * 团队视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TeamVO extends BaseVO {

    private static final long serialVersionUID = 1L;


    /**
     * 团队编码
     */
    private String code;

    /**
     * 团队名称
     */
    private String name;

    /**
     * 所属律所ID
     */
    private Long firmId;

    /**
     * 所属律所名称
     */
    private String firmName;

    /**
     * 所属部门ID
     */
    private Long departmentId;

    /**
     * 所属部门名称
     */
    private String departmentName;

    /**
     * 父级团队ID
     */
    private Long parentId;

    /**
     * 父级团队名称
     */
    private String parentName;

    /**
     * 团队类型
     */
    private TeamTypeEnum type;

    /**
     * 团队类型描述
     */
    private String typeDesc;

    /**
     * 负责人ID
     */
    private Long leaderId;

    /**
     * 负责人姓名
     */
    private String leaderName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 联系邮箱
     */
    private String email;

    /**
     * 团队规模（人数）
     */
    private Integer teamSize;

    /**
     * 团队职责
     */
    private String responsibility;

    /**
     * 团队状态（0-筹备中 1-运行中 2-已解散）
     */
    private Integer teamStatus;

    /**
     * 团队状态描述
     */
    private String teamStatusDesc;

    /**
     * 项目相关信息（仅项目组）
     */
    private transient ProjectInfo projectInfo;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 层级路径
     */
    private String path;

    /**
     * 是否叶子节点
     */
    private Boolean leaf;

    /**
     * 子团队列表
     */
    private transient List<TeamVO> children;

    /**
     * 团队成员列表
     */
    private transient List<TeamMember> members;

    /**
     * 项目信息（内部类）
     */
    @Data
    public static class ProjectInfo {
        /**
         * 项目编号
         */
        private String projectCode;

        /**
         * 项目名称
         */
        private String projectName;

        /**
         * 项目描述
         */
        private String projectDesc;

        /**
         * 项目开始日期
         */
        @JsonFormat(pattern = "yyyy-MM-dd")
        private transient LocalDate startDate;

        /**
         * 预计结束日期
         */
        @JsonFormat(pattern = "yyyy-MM-dd")
        private transient LocalDate expectedEndDate;

        /**
         * 实际结束日期
         */
        @JsonFormat(pattern = "yyyy-MM-dd")
        private transient LocalDate actualEndDate;

        /**
         * 项目状态（0-未开始 1-进行中 2-已完成 3-已终止）
         */
        private Integer projectStatus;

        /**
         * 项目状态描述
         */
        private String projectStatusDesc;

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

        /**
         * 项目优先级描述
         */
        private String priorityDesc;
    }

    /**
     * 团队成员信息（内部类）
     */
    @Data
    public static class TeamMember {
        /**
         * 成员ID
         */
        private Long id;

        /**
         * 成员姓名
         */
        private String name;

        /**
         * 成员角色
         */
        private String role;

        /**
         * 加入时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd")
        private transient LocalDate joinDate;

        /**
         * 联系电话
         */
        private String phone;

        /**
         * 联系邮箱
         */
        private String email;
    }
} 

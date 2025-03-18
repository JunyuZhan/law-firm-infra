package com.lawfirm.model.organization.service.team;

import com.lawfirm.model.organization.dto.team.TeamDTO;
import com.lawfirm.model.organization.vo.team.TeamVO;

import java.util.List;

/**
 * 组织团队服务接口
 */
public interface OrganizationTeamService {
    
    /**
     * 创建团队
     *
     * @param dto 团队信息
     * @return 团队ID
     */
    Long createTeam(TeamDTO dto);

    /**
     * 更新团队信息
     *
     * @param dto 团队信息
     */
    void updateTeam(TeamDTO dto);

    /**
     * 获取团队详情
     *
     * @param id 团队ID
     * @return 团队详情
     */
    TeamVO getTeam(Long id);

    /**
     * 获取团队树形结构
     *
     * @param firmId 律所ID
     * @param departmentId 部门ID（可选）
     * @param type 团队类型（可选）
     * @param status 状态（可选）
     * @return 团队树
     */
    List<TeamVO> getTeamTree(Long firmId, Long departmentId, Integer type, Integer status);

    /**
     * 获取团队列表（扁平结构）
     *
     * @param firmId 律所ID
     * @param departmentId 部门ID（可选）
     * @param type 团队类型（可选）
     * @param status 状态（可选）
     * @return 团队列表
     */
    List<TeamVO> listTeams(Long firmId, Long departmentId, Integer type, Integer status);

    /**
     * 更新团队状态
     *
     * @param id 团队ID
     * @param status 状态
     */
    void updateStatus(Long id, Integer status);

    /**
     * 更新团队负责人
     *
     * @param id 团队ID
     * @param leaderId 负责人ID
     * @param leaderName 负责人姓名
     */
    void updateLeader(Long id, Long leaderId, String leaderName);

    /**
     * 移动团队
     *
     * @param id 团队ID
     * @param targetParentId 目标父团队ID
     */
    void moveTeam(Long id, Long targetParentId);

    /**
     * 更新团队所属部门
     *
     * @param id 团队ID
     * @param departmentId 部门ID
     */
    void updateDepartment(Long id, Long departmentId);

    /**
     * 获取子团队列表
     *
     * @param parentId 父团队ID
     * @param status 状态（可选）
     * @return 子团队列表
     */
    List<TeamVO> listChildren(Long parentId, Integer status);

    /**
     * 统计团队数量
     *
     * @param firmId 律所ID
     * @param departmentId 部门ID（可选）
     * @param type 团队类型（可选）
     * @param status 状态（可选）
     * @return 数量
     */
    Integer countTeams(Long firmId, Long departmentId, Integer type, Integer status);

    /**
     * 检查团队编码是否已存在
     *
     * @param code 团队编码
     * @param firmId 律所ID
     * @param excludeId 排除的团队ID（用于更新时检查）
     * @return 是否存在
     */
    Boolean checkCodeExists(String code, Long firmId, Long excludeId);

    /**
     * 检查团队名称是否已存在
     *
     * @param name 团队名称
     * @param firmId 律所ID
     * @param departmentId 部门ID
     * @param excludeId 排除的团队ID（用于更新时检查）
     * @return 是否存在
     */
    Boolean checkNameExists(String name, Long firmId, Long departmentId, Long excludeId);

    /**
     * 添加团队成员
     *
     * @param id 团队ID
     * @param memberIds 成员ID列表
     */
    void addMembers(Long id, List<Long> memberIds);

    /**
     * 移除团队成员
     *
     * @param id 团队ID
     * @param memberIds 成员ID列表
     */
    void removeMembers(Long id, List<Long> memberIds);

    /**
     * 获取团队成员列表
     *
     * @param id 团队ID
     * @return 成员列表
     */
    List<TeamVO.TeamMember> listMembers(Long id);

    /**
     * 更新项目信息（仅项目组）
     *
     * @param id 团队ID
     * @param projectInfo 项目信息
     */
    void updateProjectInfo(Long id, TeamDTO.ProjectInfo projectInfo);
} 
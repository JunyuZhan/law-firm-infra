package com.lawfirm.cases.convert;

import com.lawfirm.model.cases.dto.team.CaseTeamDTO;
import com.lawfirm.model.cases.entity.team.CaseTeam;
import com.lawfirm.model.cases.enums.team.TeamMemberRoleEnum;
import com.lawfirm.model.cases.vo.team.CaseTeamVO;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 案件团队对象转换器
 * <p>
 * 提供案件团队实体与DTO、VO之间的转换方法，避免在服务层频繁使用BeanUtils复制属性
 * </p>
 */
public class TeamConvert {

    /**
     * 将案件团队实体转换为案件团队DTO
     *
     * @param entity 案件团队实体
     * @return 案件团队DTO
     */
    public static CaseTeamDTO entityToDTO(CaseTeam entity) {
        if (entity == null) {
            return null;
        }
        
        CaseTeamDTO dto = new CaseTeamDTO();
        BeanUtils.copyProperties(entity, dto);
        
        // 转换枚举类型
        if (entity.getMemberRole() != null) {
            TeamMemberRoleEnum roleEnum = TeamMemberRoleEnum.valueOf(entity.getMemberRole());
            if (roleEnum != null) {
                dto.setMemberRole(roleEnum.name());
            }
        }
        
        return dto;
    }
    
    /**
     * 将案件团队DTO转换为案件团队实体
     *
     * @param dto 案件团队DTO
     * @return 案件团队实体
     */
    public static CaseTeam dtoToEntity(CaseTeamDTO dto) {
        if (dto == null) {
            return null;
        }
        
        CaseTeam entity = new CaseTeam();
        BeanUtils.copyProperties(dto, entity);
        
        // 转换枚举类型
        if (dto.getMemberRole() != null) {
            TeamMemberRoleEnum roleEnum = TeamMemberRoleEnum.valueOf(dto.getMemberRole());
            if (roleEnum != null) {
                entity.setMemberRole(roleEnum.getValue());
            }
        }
        
        // 设置时间
        LocalDateTime now = LocalDateTime.now();
        if (entity.getCreateTime() == null) {
            entity.setCreateTime(now);
        }
        entity.setUpdateTime(now);
        
        return entity;
    }
    
    /**
     * 将案件团队实体转换为案件团队VO
     *
     * @param entity 案件团队实体
     * @return 案件团队VO
     */
    public static CaseTeamVO entityToVO(CaseTeam entity) {
        if (entity == null) {
            return null;
        }
        
        CaseTeamVO vo = new CaseTeamVO();
        BeanUtils.copyProperties(entity, vo);
        
        // 转换枚举类型
        if (entity.getMemberRole() != null) {
            vo.setMemberRole(TeamMemberRoleEnum.valueOf(entity.getMemberRole()));
        }
        
        return vo;
    }
    
    /**
     * 批量转换案件团队实体为案件团队DTO
     *
     * @param entityList 案件团队实体列表
     * @return 案件团队DTO列表
     */
    public static List<CaseTeamDTO> entityListToDTOList(List<CaseTeam> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return new ArrayList<>();
        }
        
        return entityList.stream()
                .map(TeamConvert::entityToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 批量转换案件团队实体为案件团队VO
     *
     * @param entityList 案件团队实体列表
     * @return 案件团队VO列表
     */
    public static List<CaseTeamVO> entityListToVOList(List<CaseTeam> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return new ArrayList<>();
        }
        
        return entityList.stream()
                .map(TeamConvert::entityToVO)
                .collect(Collectors.toList());
    }
    
    /**
     * 批量转换案件团队DTO为案件团队实体
     *
     * @param dtoList 案件团队DTO列表
     * @return 案件团队实体列表
     */
    public static List<CaseTeam> dtoListToEntityList(List<CaseTeamDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return new ArrayList<>();
        }
        
        return dtoList.stream()
                .map(TeamConvert::dtoToEntity)
                .collect(Collectors.toList());
    }
}

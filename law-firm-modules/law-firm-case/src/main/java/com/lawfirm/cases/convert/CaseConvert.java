package com.lawfirm.cases.convert;

import com.lawfirm.model.cases.dto.base.CaseBaseDTO;
import com.lawfirm.model.cases.dto.base.CaseCreateDTO;
import com.lawfirm.model.cases.dto.base.CaseUpdateDTO;
import com.lawfirm.model.cases.entity.base.Case;
import com.lawfirm.model.cases.enums.base.*;
import com.lawfirm.model.cases.vo.base.CaseDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 案件对象转换器
 * <p>
 * 提供案件实体与DTO、VO之间的转换方法，避免在服务层频繁使用BeanUtils复制属性
 * </p>
 */
public class CaseConvert {

    /**
     * 将案件实体转换为基础DTO
     *
     * @param entity 案件实体
     * @return 案件基础DTO
     */
    public static CaseBaseDTO entityToBaseDTO(Case entity) {
        if (entity == null) {
            return null;
        }
        
        CaseBaseDTO dto = new CaseBaseDTO();
        
        // 复制基本属性
        dto.setId(entity.getId());
        dto.setCaseNumber(entity.getCaseNumber());
        dto.setCaseName(entity.getCaseName());
        dto.setCaseDescription(entity.getCaseDescription());
        
        // 转换枚举类型
        if (entity.getCaseType() != null) {
            dto.setCaseType(CaseTypeEnum.valueOf(entity.getCaseType().toString()));
        }
        if (entity.getCaseStatus() != null) {
            dto.setCaseStatus(CaseStatusEnum.valueOf(entity.getCaseStatus().toString()));
        }
        if (entity.getCaseProgress() != null) {
            dto.setCaseProgress(CaseProgressEnum.valueOf(entity.getCaseProgress().toString()));
        }
        if (entity.getCaseStage() != null) {
            dto.setCaseStage(CaseStageEnum.valueOf(entity.getCaseStage().toString()));
        }
        if (entity.getCaseDifficulty() != null) {
            dto.setCaseDifficulty(CaseDifficultyEnum.valueOf(entity.getCaseDifficulty().toString()));
        }
        if (entity.getCaseImportance() != null) {
            dto.setCaseImportance(CaseImportanceEnum.valueOf(entity.getCaseImportance().toString()));
        }
        if (entity.getCasePriority() != null) {
            dto.setCasePriority(CasePriorityEnum.valueOf(entity.getCasePriority().toString()));
        }
        if (entity.getCaseSource() != null) {
            dto.setCaseSource(CaseSourceEnum.valueOf(entity.getCaseSource().toString()));
        }
        if (entity.getHandleType() != null) {
            dto.setHandleType(CaseHandleTypeEnum.valueOf(entity.getHandleType().toString()));
        }
        if (entity.getFeeType() != null) {
            dto.setFeeType(CaseFeeTypeEnum.valueOf(entity.getFeeType().toString()));
        }
        
        // 复制其他属性
        dto.setClientId(entity.getClientId());
        dto.setClientName(entity.getClientName());
        dto.setLawyerId(entity.getLawyerId());
        dto.setLawyerName(entity.getLawyerName());
        dto.setRemark(entity.getRemarks());
        
        // 处理标签
        if (entity.getCaseTags() != null && !entity.getCaseTags().isEmpty()) {
            dto.setCaseTags(entity.getCaseTags());
        }
        
        return dto;
    }
    
    /**
     * 将案件基础DTO转换为案件实体
     *
     * @param dto 案件基础DTO
     * @return 案件实体
     */
    public static Case baseDTOToEntity(CaseBaseDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Case entity = new Case();
        BeanUtils.copyProperties(dto, entity);
        
        // 转换枚举类型
        if (dto.getCaseType() != null) {
            entity.setCaseType(dto.getCaseType().getValue());
        }
        if (dto.getCaseStatus() != null) {
            entity.setCaseStatus(dto.getCaseStatus().getValue());
        }
        if (dto.getCaseProgress() != null) {
            entity.setCaseProgress(dto.getCaseProgress().getValue());
        }
        if (dto.getCaseStage() != null) {
            entity.setCaseStage(dto.getCaseStage().getValue());
        }
        if (dto.getCaseDifficulty() != null) {
            entity.setCaseDifficulty(dto.getCaseDifficulty().getValue());
        }
        if (dto.getCaseImportance() != null) {
            entity.setCaseImportance(dto.getCaseImportance().getValue());
        }
        if (dto.getCasePriority() != null) {
            entity.setCasePriority(dto.getCasePriority().getValue());
        }
        if (dto.getCaseSource() != null) {
            entity.setCaseSource(dto.getCaseSource().getValue());
        }
        if (dto.getHandleType() != null) {
            entity.setHandleType(dto.getHandleType().getValue());
        }
        if (dto.getFeeType() != null) {
            entity.setFeeType(dto.getFeeType().getValue());
        }
        
        return entity;
    }
    
    /**
     * 将案件实体转换为详情VO
     *
     * @param entity 案件实体
     * @return 案件详情VO
     */
    public static CaseDetailVO entityToDetailVO(Case entity) {
        if (entity == null) {
            return null;
        }
        
        CaseDetailVO vo = new CaseDetailVO();
        
        // 复制基本属性
        vo.setId(entity.getId());
        vo.setCaseNumber(entity.getCaseNumber());
        vo.setCaseName(entity.getCaseName());
        vo.setDescription(entity.getCaseDescription());
        
        // 转换枚举类型
        if (entity.getCaseType() != null) {
            vo.setCaseType(CaseTypeEnum.valueOf(entity.getCaseType().toString()));
        }
        if (entity.getCaseStatus() != null) {
            vo.setCaseStatus(CaseStatusEnum.valueOf(entity.getCaseStatus().toString()));
        }
        if (entity.getCaseProgress() != null) {
            vo.setCaseProgress(CaseProgressEnum.valueOf(entity.getCaseProgress().toString()));
        }
        if (entity.getCaseStage() != null) {
            vo.setCaseStage(CaseStageEnum.valueOf(entity.getCaseStage().toString()));
        }
        if (entity.getCaseDifficulty() != null) {
            vo.setCaseDifficulty(CaseDifficultyEnum.valueOf(entity.getCaseDifficulty().toString()));
        }
        if (entity.getCaseImportance() != null) {
            vo.setCaseImportance(CaseImportanceEnum.valueOf(entity.getCaseImportance().toString()));
        }
        if (entity.getCasePriority() != null) {
            vo.setCasePriority(CasePriorityEnum.valueOf(entity.getCasePriority().toString()));
        }
        if (entity.getCaseSource() != null) {
            vo.setCaseSource(CaseSourceEnum.valueOf(entity.getCaseSource().toString()));
        }
        if (entity.getHandleType() != null) {
            vo.setHandleType(CaseHandleTypeEnum.valueOf(entity.getHandleType().toString()));
        }
        if (entity.getFeeType() != null) {
            vo.setFeeType(CaseFeeTypeEnum.valueOf(entity.getFeeType().toString()));
        }
        
        // 复制其他属性
        vo.setClientId(entity.getClientId());
        vo.setClientName(entity.getClientName());
        vo.setLeaderId(entity.getLawyerId());
        vo.setLeaderName(entity.getLawyerName());
        vo.setRemark(entity.getRemarks());
        
        // 处理标签
        if (entity.getCaseTags() != null && !entity.getCaseTags().isEmpty()) {
            vo.setTags(Arrays.asList(entity.getCaseTags().split(",")));
        }
        
        return vo;
    }
    
    /**
     * 将案件创建DTO转换为案件实体
     *
     * @param dto 案件创建DTO
     * @return 案件实体
     */
    public static Case createDTOToEntity(CaseCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Case entity = baseDTOToEntity(dto);
        
        // 设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        
        return entity;
    }
    
    /**
     * 将案件更新DTO转换为案件实体
     *
     * @param dto 案件更新DTO
     * @return 案件实体
     */
    public static Case updateDTOToEntity(CaseUpdateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        CaseBaseDTO baseDTO = dto.toBaseDTO();
        Case entity = baseDTOToEntity(baseDTO);
        
        // 设置更新时间
        entity.setUpdateTime(LocalDateTime.now());
        
        return entity;
    }
    
    /**
     * 批量转换案件实体为案件详情VO
     *
     * @param entityList 案件实体列表
     * @return 案件详情VO列表
     */
    public static List<CaseDetailVO> entityListToDetailVOList(List<Case> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return new ArrayList<>();
        }
        
        return entityList.stream()
                .map(CaseConvert::entityToDetailVO)
                .collect(Collectors.toList());
    }
}

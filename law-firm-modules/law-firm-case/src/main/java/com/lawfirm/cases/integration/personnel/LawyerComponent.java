package com.lawfirm.cases.integration.personnel;

import com.lawfirm.model.personnel.dto.LawyerDTO;
import com.lawfirm.model.personnel.service.LawyerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 律师组件
 * 负责与人事模块的集成，获取律师相关信息
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LawyerComponent {

    private final LawyerService lawyerService;

    /**
     * 获取律师详细信息
     *
     * @param lawyerId 律师ID
     * @return 律师详情
     */
    public Optional<LawyerDTO> getLawyerDetail(Long lawyerId) {
        if (lawyerId == null) {
            return Optional.empty();
        }

        try {
            return Optional.ofNullable(lawyerService.getLawyerById(lawyerId));
        } catch (Exception e) {
            log.error("获取律师详情异常，lawyerId={}", lawyerId, e);
            return Optional.empty();
        }
    }

    /**
     * 根据专业领域获取律师列表
     *
     * @param practiceArea 专业领域
     * @return 律师列表
     */
    public List<LawyerDTO> getLawyersByPracticeArea(String practiceArea) {
        if (practiceArea == null || practiceArea.trim().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            return lawyerService.getLawyersByPracticeArea(practiceArea);
        } catch (Exception e) {
            log.error("获取专业领域律师列表异常，practiceArea={}", practiceArea, e);
            return Collections.emptyList();
        }
    }

    /**
     * 获取特定部门的律师列表
     *
     * @param departmentId 部门ID
     * @return 律师列表
     */
    public List<LawyerDTO> getLawyersByDepartment(Long departmentId) {
        if (departmentId == null) {
            return Collections.emptyList();
        }

        try {
            return lawyerService.getLawyersByDepartment(departmentId);
        } catch (Exception e) {
            log.error("获取部门律师列表异常，departmentId={}", departmentId, e);
            return Collections.emptyList();
        }
    }

    /**
     * 检查律师的工作负荷
     *
     * @param lawyerId 律师ID
     * @return 工作负荷百分比（0-100）
     */
    public int getLawyerWorkload(Long lawyerId) {
        if (lawyerId == null) {
            return 0;
        }

        try {
            return lawyerService.getLawyerWorkloadPercentage(lawyerId);
        } catch (Exception e) {
            log.error("获取律师工作负荷异常，lawyerId={}", lawyerId, e);
            return 0;
        }
    }

    /**
     * 获取可用于案件分配的律师列表
     *
     * @param practiceArea 专业领域
     * @param maxWorkloadPercentage 最大工作负荷百分比
     * @return 律师列表
     */
    public List<LawyerDTO> getAvailableLawyers(String practiceArea, int maxWorkloadPercentage) {
        if (practiceArea == null || practiceArea.trim().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            return lawyerService.getAvailableLawyers(practiceArea, maxWorkloadPercentage);
        } catch (Exception e) {
            log.error("获取可用律师列表异常，practiceArea={}, maxWorkload={}", practiceArea, maxWorkloadPercentage, e);
            return Collections.emptyList();
        }
    }

    /**
     * 批量获取律师信息
     *
     * @param lawyerIds 律师ID集合
     * @return 律师信息列表
     */
    public List<LawyerDTO> getLawyersByIds(Set<Long> lawyerIds) {
        if (lawyerIds == null || lawyerIds.isEmpty()) {
            return Collections.emptyList();
        }

        try {
            return lawyerService.getLawyersByIds(lawyerIds);
        } catch (Exception e) {
            log.error("批量获取律师信息异常，lawyerIds.size={}", lawyerIds.size(), e);
            return Collections.emptyList();
        }
    }
} 
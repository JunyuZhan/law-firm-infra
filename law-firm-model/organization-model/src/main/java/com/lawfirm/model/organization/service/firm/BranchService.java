package com.lawfirm.model.organization.service.firm;

import com.lawfirm.model.organization.dto.firm.BranchDTO;
import com.lawfirm.model.organization.vo.firm.BranchVO;

import java.util.List;

/**
 * 分所服务接口
 */
public interface BranchService {
    
    /**
     * 创建分所
     *
     * @param dto 分所信息
     * @return 分所ID
     */
    Long createBranch(BranchDTO dto);

    /**
     * 更新分所信息
     *
     * @param dto 分所信息
     */
    void updateBranch(BranchDTO dto);

    /**
     * 获取分所详情
     *
     * @param id 分所ID
     * @return 分所详情
     */
    BranchVO getBranch(Long id);

    /**
     * 获取分所列表
     *
     * @param headOfficeId 总所ID
     * @param status 状态（可选）
     * @param province 省份（可选）
     * @param city 城市（可选）
     * @return 分所列表
     */
    List<BranchVO> listBranches(Long headOfficeId, Integer status, String province, String city);

    /**
     * 更新分所状态
     *
     * @param id 分所ID
     * @param status 状态
     */
    void updateStatus(Long id, Integer status);

    /**
     * 更新分所执业许可信息
     *
     * @param id 分所ID
     * @param licenseNumber 执业许可证号
     * @param licenseExpireDate 执业许可证有效期
     */
    void updateLicense(Long id, String licenseNumber, String licenseExpireDate);

    /**
     * 更新分所负责人
     *
     * @param id 分所ID
     * @param managerId 负责人ID
     * @param managerName 负责人姓名
     */
    void updateManager(Long id, Long managerId, String managerName);

    /**
     * 统计分所数量
     *
     * @param headOfficeId 总所ID
     * @param status 状态（可选）
     * @return 数量
     */
    Integer countBranches(Long headOfficeId, Integer status);

    /**
     * 检查分所名称是否已存在
     *
     * @param name 分所名称
     * @param headOfficeId 总所ID
     * @param excludeId 排除的分所ID（用于更新时检查）
     * @return 是否存在
     */
    Boolean checkNameExists(String name, Long headOfficeId, Long excludeId);

    /**
     * 检查统一社会信用代码是否已存在
     *
     * @param creditCode 统一社会信用代码
     * @param excludeId 排除的分所ID（用于更新时检查）
     * @return 是否存在
     */
    Boolean checkCreditCodeExists(String creditCode, Long excludeId);

    /**
     * 获取分所所在地区统计
     *
     * @param headOfficeId 总所ID
     * @return 地区统计列表
     */
    List<RegionStatistics> getRegionStatistics(Long headOfficeId);

    /**
     * 地区统计信息
     */
    class RegionStatistics {
        private String province;
        private String city;
        private Integer count;
        
        // getters and setters
    }
} 
package com.lawfirm.model.organization.service.firm;

import com.lawfirm.model.organization.dto.firm.LawFirmDTO;
import com.lawfirm.model.organization.vo.firm.LawFirmVO;

import java.util.List;

/**
 * 律师事务所服务接口
 */
public interface LawFirmService {
    
    /**
     * 创建律师事务所
     *
     * @param dto 律师事务所信息
     * @return 律师事务所ID
     */
    Long createLawFirm(LawFirmDTO dto);

    /**
     * 更新律师事务所信息
     *
     * @param dto 律师事务所信息
     */
    void updateLawFirm(LawFirmDTO dto);

    /**
     * 获取律师事务所详情
     *
     * @param id 律师事务所ID
     * @return 律师事务所详情
     */
    LawFirmVO getLawFirm(Long id);

    /**
     * 获取律师事务所列表
     *
     * @param status 状态（可选）
     * @param type 类型（可选）
     * @return 律师事务所列表
     */
    List<LawFirmVO> listLawFirms(Integer status, Integer type);

    /**
     * 更新律师事务所状态
     *
     * @param id 律师事务所ID
     * @param status 状态
     */
    void updateStatus(Long id, Integer status);

    /**
     * 更新执业许可信息
     *
     * @param id 律师事务所ID
     * @param licenseNumber 执业许可证号
     * @param licenseExpireDate 执业许可证有效期
     */
    void updateLicense(Long id, String licenseNumber, String licenseExpireDate);

    /**
     * 获取总所下的所有分所
     *
     * @param headOfficeId 总所ID
     * @return 分所列表
     */
    List<LawFirmVO> listBranches(Long headOfficeId);

    /**
     * 统计律师事务所数量
     *
     * @param status 状态（可选）
     * @param type 类型（可选）
     * @return 数量
     */
    Integer countLawFirms(Integer status, Integer type);

    /**
     * 检查律所名称是否已存在
     *
     * @param name 律所名称
     * @param excludeId 排除的律所ID（用于更新时检查）
     * @return 是否存在
     */
    Boolean checkNameExists(String name, Long excludeId);

    /**
     * 检查统一社会信用代码是否已存在
     *
     * @param creditCode 统一社会信用代码
     * @param excludeId 排除的律所ID（用于更新时检查）
     * @return 是否存在
     */
    Boolean checkCreditCodeExists(String creditCode, Long excludeId);
} 
package com.lawfirm.model.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.personnel.dto.lawyer.CreateLawyerDTO;
import com.lawfirm.model.personnel.dto.lawyer.UpdateLawyerDTO;
import com.lawfirm.model.personnel.entity.Lawyer;
import com.lawfirm.model.personnel.vo.LawyerVO;

import java.time.LocalDate;
import java.util.List;

/**
 * 律师服务接口
 */
public interface LawyerService extends EmployeeService {

    /**
     * 创建律师
     *
     * @param createDTO 创建参数
     * @return 律师ID
     */
    Long createLawyer(CreateLawyerDTO createDTO);

    /**
     * 更新律师
     *
     * @param updateDTO 更新参数
     * @return 是否成功
     */
    boolean updateLawyer(UpdateLawyerDTO updateDTO);

    /**
     * 获取律师详情
     *
     * @param id 律师ID
     * @return 律师详情
     */
    LawyerVO getLawyerDetail(Long id);

    /**
     * 获取律师列表
     *
     * @param teamId 团队ID
     * @param level 律师职级
     * @return 律师列表
     */
    List<LawyerVO> listLawyers(Long teamId, Integer level);

    /**
     * 分页查询律师
     *
     * @param page 分页参数
     * @param teamId 团队ID
     * @param level 律师职级
     * @param keyword 关键字
     * @return 分页结果
     */
    Page<LawyerVO> pageLawyers(Page<Lawyer> page, Long teamId, Integer level, String keyword);

    /**
     * 调整律师职级
     *
     * @param id 律师ID
     * @param level 新职级
     * @param reason 调整原因
     * @return 是否成功
     */
    boolean changeLevel(Long id, Integer level, String reason);

    /**
     * 调整所属团队
     *
     * @param id 律师ID
     * @param teamId 新团队ID
     * @param reason 调整原因
     * @return 是否成功
     */
    boolean changeTeam(Long id, Long teamId, String reason);

    /**
     * 设置/取消合伙人
     *
     * @param id 律师ID
     * @param isPartner 是否合伙人
     * @param partnerDate 成为合伙人日期
     * @param equityRatio 股权比例
     * @return 是否成功
     */
    boolean setPartner(Long id, Boolean isPartner, LocalDate partnerDate, Double equityRatio);

    /**
     * 更新执业证书
     *
     * @param id 律师ID
     * @param licenseNumber 执业证号
     * @param issueDate 发证日期
     * @param expireDate 失效日期
     * @return 是否成功
     */
    boolean updateLicense(Long id, String licenseNumber, LocalDate issueDate, LocalDate expireDate);

    /**
     * 检查执业证号是否存在
     *
     * @param licenseNumber 执业证号
     * @param excludeId 排除的律师ID
     * @return 是否存在
     */
    boolean checkLicenseNumberExists(String licenseNumber, Long excludeId);

    /**
     * 根据执业证号获取律师
     *
     * @param licenseNumber 执业证号
     * @return 律师信息
     */
    LawyerVO getLawyerByLicenseNumber(String licenseNumber);
} 
package com.lawfirm.model.cases.service.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.cases.dto.base.CaseCreateDTO;
import com.lawfirm.model.cases.dto.base.CaseQueryDTO;
import com.lawfirm.model.cases.dto.base.CaseUpdateDTO;
import com.lawfirm.model.cases.entity.base.Case;
import com.lawfirm.model.cases.enums.base.CaseStatusEnum;
import com.lawfirm.model.cases.vo.base.CaseDetailVO;
import com.lawfirm.model.cases.vo.base.CaseQueryVO;
import com.lawfirm.model.cases.vo.business.CaseStatusVO;

import java.util.List;

/**
 * 案件基础服务接口
 */
public interface CaseService extends BaseService<Case> {

    /**
     * 创建案件
     *
     * @param createDTO 创建参数
     * @return 案件ID
     */
    Long createCase(CaseCreateDTO createDTO);

    /**
     * 更新案件
     *
     * @param updateDTO 更新参数
     */
    void updateCase(CaseUpdateDTO updateDTO);

    /**
     * 获取案件详情
     *
     * @param caseId 案件ID
     * @return 案件详情
     */
    CaseDetailVO getCaseDetail(Long caseId);

    /**
     * 分页查询案件
     *
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    Page<CaseQueryVO> queryCases(CaseQueryDTO queryDTO);

    /**
     * 更新案件状态
     *
     * @param caseId 案件ID
     * @param status 目标状态
     * @param reason 变更原因
     */
    void updateCaseStatus(Long caseId, CaseStatusEnum status, String reason);

    /**
     * 获取案件状态历史
     *
     * @param caseId 案件ID
     * @return 状态历史列表
     */
    List<CaseStatusVO> getCaseStatusHistory(Long caseId);

    /**
     * 检查案件编号是否存在
     *
     * @param caseNumber 案件编号
     * @return 是否存在
     */
    boolean checkCaseNumberExists(String caseNumber);

    /**
     * 生成案件编号
     *
     * @return 案件编号
     */
    String generateCaseNumber();

    /**
     * 归档案件
     *
     * @param caseId 案件ID
     * @param reason 归档原因
     */
    void archiveCase(Long caseId, String reason);

    /**
     * 重启案件
     *
     * @param caseId 案件ID
     * @param reason 重启原因
     */
    void reopenCase(Long caseId, String reason);

    /**
     * 批量更新案件状态
     *
     * @param caseIds 案件ID列表
     * @param status 目标状态
     * @param reason 变更原因
     */
    void batchUpdateStatus(List<Long> caseIds, CaseStatusEnum status, String reason);

    /**
     * 获取我的案件列表
     *
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    Page<CaseQueryVO> getMyCases(CaseQueryDTO queryDTO);

    /**
     * 获取我参与的案件列表
     *
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    Page<CaseQueryVO> getMyParticipatedCases(CaseQueryDTO queryDTO);
} 
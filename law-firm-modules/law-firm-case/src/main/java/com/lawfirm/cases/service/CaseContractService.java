package com.lawfirm.cases.service;

import com.lawfirm.model.cases.dto.business.CaseContractDTO;
import com.lawfirm.model.cases.vo.business.CaseContractVO;

import java.util.List;

/**
 * 案件合同服务接口
 */
public interface CaseContractService {

    /**
     * 创建案件合同
     *
     * @param caseId 案件ID
     * @param contractDTO 合同信息
     * @return 合同ID
     */
    Long createCaseContract(Long caseId, CaseContractDTO contractDTO);

    /**
     * 更新案件合同
     *
     * @param caseId 案件ID
     * @param contractId 合同ID
     * @param contractDTO 合同信息
     * @return 是否成功
     */
    boolean updateCaseContract(Long caseId, Long contractId, CaseContractDTO contractDTO);

    /**
     * 删除案件合同
     *
     * @param caseId 案件ID
     * @param contractId 合同ID
     * @return 是否成功
     */
    boolean deleteCaseContract(Long caseId, Long contractId);

    /**
     * 获取案件合同详情
     *
     * @param caseId 案件ID
     * @param contractId 合同ID
     * @return 合同详情
     */
    CaseContractVO getCaseContractDetail(Long caseId, Long contractId);

    /**
     * 获取案件的所有合同
     *
     * @param caseId 案件ID
     * @return 合同列表
     */
    List<CaseContractVO> listCaseContracts(Long caseId);

    /**
     * 签署案件合同
     *
     * @param caseId 案件ID
     * @param contractId 合同ID
     * @param signerId 签署人ID
     * @param signatureData 签名数据
     * @return 是否成功
     */
    boolean signCaseContract(Long caseId, Long contractId, Long signerId, String signatureData);

    /**
     * 审核案件合同
     *
     * @param caseId 案件ID
     * @param contractId 合同ID
     * @param approved 是否通过
     * @param opinion 审核意见
     * @return 是否成功
     */
    boolean reviewCaseContract(Long caseId, Long contractId, boolean approved, String opinion);

    /**
     * 检查案件合同状态
     *
     * @param caseId 案件ID
     * @return 合同状态是否有效
     */
    boolean checkCaseContractStatus(Long caseId);
} 
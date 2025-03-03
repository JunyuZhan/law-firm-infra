package com.lawfirm.model.cases.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.business.CaseContractDTO;
import com.lawfirm.model.cases.vo.business.CaseContractVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件合同服务接口
 */
public interface CaseContractService {

    /**
     * 创建合同
     *
     * @param contractDTO 合同信息
     * @return 合同ID
     */
    Long createContract(CaseContractDTO contractDTO);

    /**
     * 更新合同
     *
     * @param contractDTO 合同信息
     * @return 是否成功
     */
    boolean updateContract(CaseContractDTO contractDTO);

    /**
     * 删除合同
     *
     * @param contractId 合同ID
     * @return 是否成功
     */
    boolean deleteContract(Long contractId);

    /**
     * 获取合同详情
     *
     * @param contractId 合同ID
     * @return 合同详情
     */
    CaseContractVO getContractDetail(Long contractId);

    /**
     * 获取案件的所有合同
     *
     * @param caseId 案件ID
     * @return 合同列表
     */
    List<CaseContractVO> listCaseContracts(Long caseId);

    /**
     * 分页查询合同
     *
     * @param caseId 案件ID
     * @param contractType 合同类型
     * @param contractStatus 合同状态
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    IPage<CaseContractVO> pageContracts(Long caseId, Integer contractType, Integer contractStatus, 
                                      Integer pageNum, Integer pageSize);

    /**
     * 签署合同
     *
     * @param contractId 合同ID
     * @param signerId 签署人ID
     * @param signatureData 签名数据
     * @return 是否成功
     */
    boolean signContract(Long contractId, Long signerId, String signatureData);

    /**
     * 审核合同
     *
     * @param contractId 合同ID
     * @param approved 是否通过
     * @param opinion 审核意见
     * @return 是否成功
     */
    boolean reviewContract(Long contractId, boolean approved, String opinion);

    /**
     * 作废合同
     *
     * @param contractId 合同ID
     * @param reason 作废原因
     * @return 是否成功
     */
    boolean voidContract(Long contractId, String reason);

    /**
     * 归档合同
     *
     * @param contractId 合同ID
     * @return 是否成功
     */
    boolean archiveContract(Long contractId);

    /**
     * 生成合同文档
     *
     * @param contractId 合同ID
     * @return 文档ID
     */
    Long generateContractDocument(Long contractId);

    /**
     * 添加合同附件
     *
     * @param contractId 合同ID
     * @param documentId 文档ID
     * @return 是否成功
     */
    boolean addContractAttachment(Long contractId, Long documentId);

    /**
     * 移除合同附件
     *
     * @param contractId 合同ID
     * @param documentId 文档ID
     * @return 是否成功
     */
    boolean removeContractAttachment(Long contractId, Long documentId);

    /**
     * 设置合同提醒
     *
     * @param contractId 合同ID
     * @param remindTime 提醒时间
     * @param remindType 提醒类型
     * @return 是否成功
     */
    boolean setContractReminder(Long contractId, LocalDateTime remindTime, Integer remindType);

    /**
     * 获取待签署的合同
     *
     * @param userId 用户ID
     * @return 合同列表
     */
    List<CaseContractVO> listPendingSignContracts(Long userId);

    /**
     * 获取待审核的合同
     *
     * @param userId 用户ID
     * @return 合同列表
     */
    List<CaseContractVO> listPendingReviewContracts(Long userId);

    /**
     * 检查合同是否存在
     *
     * @param contractId 合同ID
     * @return 是否存在
     */
    boolean checkContractExists(Long contractId);

    /**
     * 统计案件合同数量
     *
     * @param caseId 案件ID
     * @param contractType 合同类型
     * @param contractStatus 合同状态
     * @return 数量
     */
    int countContracts(Long caseId, Integer contractType, Integer contractStatus);
} 
package com.lawfirm.model.cases.service.business;

import com.lawfirm.model.base.dto.PageDTO;
import com.lawfirm.model.cases.vo.business.ContractDetailVO;
import com.lawfirm.model.cases.vo.business.ContractTemplateVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件合同服务接口
 */
public interface CaseContractService {

    /**
     * 创建合同
     *
     * @param caseId 案件ID
     * @param templateId 模板ID
     * @param title 合同标题
     * @param content 合同内容
     * @param parties 合同当事人
     * @return 合同ID
     */
    Long createContract(Long caseId, Long templateId, String title, String content, List<String> parties);

    /**
     * 更新合同
     *
     * @param contractId 合同ID
     * @param title 合同标题
     * @param content 合同内容
     * @param parties 合同当事人
     * @return 是否成功
     */
    Boolean updateContract(Long contractId, String title, String content, List<String> parties);

    /**
     * 删除合同
     *
     * @param contractId 合同ID
     * @return 是否成功
     */
    Boolean deleteContract(Long contractId);

    /**
     * 获取合同详情
     *
     * @param contractId 合同ID
     * @return 合同详情
     */
    ContractDetailVO getContractDetail(Long contractId);

    /**
     * 分页查询合同
     *
     * @param caseId 案件ID
     * @param status 合同状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageDTO<ContractDetailVO> pageContracts(Long caseId, String status,
            LocalDateTime startTime, LocalDateTime endTime, Integer pageNum, Integer pageSize);

    /**
     * 创建合同模板
     *
     * @param name 模板名称
     * @param content 模板内容
     * @param type 模板类型
     * @return 模板ID
     */
    Long createTemplate(String name, String content, String type);

    /**
     * 更新合同模板
     *
     * @param templateId 模板ID
     * @param name 模板名称
     * @param content 模板内容
     * @return 是否成功
     */
    Boolean updateTemplate(Long templateId, String name, String content);

    /**
     * 删除合同模板
     *
     * @param templateId 模板ID
     * @return 是否成功
     */
    Boolean deleteTemplate(Long templateId);

    /**
     * 获取合同模板详情
     *
     * @param templateId 模板ID
     * @return 模板详情
     */
    ContractTemplateVO getTemplateDetail(Long templateId);

    /**
     * 获取合同模板列表
     *
     * @param type 模板类型
     * @return 模板列表
     */
    List<ContractTemplateVO> listTemplates(String type);

    /**
     * 发送合同
     *
     * @param contractId 合同ID
     * @param recipients 接收人列表
     * @return 是否成功
     */
    Boolean sendContract(Long contractId, List<String> recipients);

    /**
     * 签署合同
     *
     * @param contractId 合同ID
     * @param signerId 签署人ID
     * @param signature 签名数据
     * @return 是否成功
     */
    Boolean signContract(Long contractId, Long signerId, byte[] signature);

    /**
     * 归档合同
     *
     * @param contractId 合同ID
     * @return 是否成功
     */
    Boolean archiveContract(Long contractId);

    /**
     * 导出合同
     *
     * @param contractId 合同ID
     * @param format 导出格式
     * @return 文件路径
     */
    String exportContract(Long contractId, String format);
} 
package com.lawfirm.model.contract.service;

import com.lawfirm.model.contract.entity.ContractTemplate;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.contract.dto.ContractTemplateCreateDTO;
import com.lawfirm.model.contract.dto.ContractTemplateUpdateDTO;
import com.lawfirm.model.contract.dto.ContractTemplateQueryDTO;
import com.lawfirm.model.contract.vo.ContractTemplateVO;
import com.lawfirm.model.contract.vo.ContractTemplateDetailVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 合同模板服务接口，提供合同模板相关的业务逻辑
 */
public interface ContractTemplateService extends BaseService<ContractTemplate> {

    /**
     * 创建合同模板
     * @param createDTO 创建参数
     * @return 模板ID
     */
    Long createTemplate(ContractTemplateCreateDTO createDTO);

    /**
     * 更新合同模板
     * @param updateDTO 更新参数
     * @return 是否成功
     */
    boolean updateTemplate(ContractTemplateUpdateDTO updateDTO);

    /**
     * 查询合同模板列表
     * @param queryDTO 查询参数
     * @return 模板列表
     */
    List<ContractTemplateVO> listTemplates(ContractTemplateQueryDTO queryDTO);

    /**
     * 分页查询合同模板
     * @param page 分页参数
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    IPage<ContractTemplateVO> pageTemplates(IPage<ContractTemplateVO> page, ContractTemplateQueryDTO queryDTO);
    
    /**
     * 获取合同模板详情
     * @param id 模板ID
     * @return 模板详情
     */
    ContractTemplateDetailVO getTemplateDetail(Long id);
    
    /**
     * 启用模板
     * @param id 模板ID
     * @return 是否成功
     */
    boolean enableTemplate(Long id);
    
    /**
     * 禁用模板
     * @param id 模板ID
     * @return 是否成功
     */
    boolean disableTemplate(Long id);
} 
package com.lawfirm.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.contract.util.ContractConverter;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.contract.dto.ContractCreateDTO;
import com.lawfirm.model.contract.dto.ContractQueryDTO;
import com.lawfirm.model.contract.dto.ContractUpdateDTO;
import com.lawfirm.model.contract.entity.Contract;
import com.lawfirm.model.contract.mapper.ContractMapper;
import com.lawfirm.model.contract.service.ContractService;
import com.lawfirm.model.contract.vo.ContractDetailVO;
import com.lawfirm.model.contract.vo.ContractVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import com.lawfirm.model.client.service.ClientService;
import com.lawfirm.model.client.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.search.service.SearchService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 合同服务实现类
 */
@Slf4j
@Service("contractServiceImpl")
@RequiredArgsConstructor
public class ContractServiceImpl extends BaseServiceImpl<ContractMapper, Contract> implements ContractService {

    private final ContractMapper contractMapper;
    
    @Autowired(required = false)
    private ClientService clientService;
    
    /**
     * 注入core层审计服务，便于后续记录合同操作日志
     */
    @Autowired(required = false)
    @Qualifier("clientAuditService")
    private AuditService auditService;

    /**
     * 注入core层消息发送服务，便于后续合同相关通知等
     */
    @Autowired(required = false)
    @Qualifier("clientMessageSender")
    private MessageSender messageSender;

    /**
     * 注入core层文件存储服务，便于后续合同附件上传等
     */
    @Autowired(required = false)
    @Qualifier("clientFileService")
    private FileService fileService;

    /**
     * 注入core层存储桶服务
     */
    @Autowired(required = false)
    @Qualifier("clientBucketService")
    private BucketService bucketService;

    /**
     * 注入core层搜索服务
     */
    @Autowired(required = false)
    @Qualifier("clientSearchService")
    private SearchService searchService;
    
    @Override
    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    
    @Override
    public Long getCurrentUserId() {
        return SecurityUtils.getUserId();
    }
    
    @Override
    public Long getCurrentTenantId() {
        // 如果系统支持多租户，则从SecurityContext中获取租户ID
        // 如果系统不支持多租户，则返回默认租户ID
        return 1L; // 默认返回租户ID为1
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createContract(ContractCreateDTO createDTO) {
        log.info("创建合同: {}", createDTO.getContractName());
        
        // 验证客户是否存在
        if (createDTO.getClientId() != null) {
            validateClient(createDTO.getClientId());
        } else {
            log.warn("创建合同时未指定客户ID，可能导致业务问题");
        }
        
        // 转换DTO为实体
        Contract contract = ContractConverter.toEntity(createDTO);
        
        // 保存合同
        save(contract);
        
        return contract.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateContract(ContractUpdateDTO updateDTO) {
        log.info("更新合同: {}", updateDTO.getId());
        
        // 获取合同
        Contract contract = getById(updateDTO.getId());
        if (contract == null) {
            log.error("合同不存在: {}", updateDTO.getId());
            return false;
        }
        
        // 更新合同信息
        ContractConverter.updateEntity(contract, updateDTO);
        
        // 保存更新
        return updateById(contract);
    }

    @Override
    public List<ContractVO> listContracts(ContractQueryDTO queryDTO) {
        log.info("查询合同列表");
        
        // 构建查询条件
        LambdaQueryWrapper<Contract> wrapper = buildQueryWrapper(queryDTO);
        
        // 查询合同列表
        List<Contract> contracts = list(wrapper);
        
        // 转换为VO
        return contracts.stream()
                .map(ContractConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public IPage<ContractVO> pageContracts(IPage<ContractVO> page, ContractQueryDTO queryDTO) {
        log.info("分页查询合同列表: 页码={}, 页大小={}", page.getCurrent(), page.getSize());
        
        // 构建查询条件
        LambdaQueryWrapper<Contract> wrapper = buildQueryWrapper(queryDTO);
        
        // 分页查询
        IPage<Contract> contractPage = page(page.convert(c -> new Contract()), wrapper);
        
        // 转换为VO
        return contractPage.convert(ContractConverter::toVO);
    }
    
    @Override
    public ContractDetailVO getContractDetail(Long id) {
        log.info("获取合同详情: {}", id);
        
        // 获取合同基本信息
        Contract contract = getById(id);
        if (contract == null) {
            log.error("合同不存在: {}", id);
            return null;
        }
        
        // 转换为详情VO
        ContractDetailVO detailVO = ContractConverter.toDetailVO(contract);
        
        // 查询关联数据
        // 1. 查询合同条款
        // detailVO.setTerms(contractTermService.listByContractId(id));
        // 2. 查询合同团队成员
        // detailVO.setTeamMembers(contractTeamService.listByContractId(id));
        // 3. 查询合同收费项目
        // detailVO.setChargeItems(contractChargeService.listByContractId(id));
        // 4. 查询合同里程碑
        // detailVO.setMilestones(contractMilestoneService.listByContractId(id));
        // 5. 查询合同附件
        // detailVO.setAttachments(contractAttachmentService.listByContractId(id));
        // 6. 查询审批记录
        // detailVO.setApprovalRecords(contractApprovalService.listByContractId(id));
        // 7. 查询变更记录
        // detailVO.setChangeRecords(contractChangeService.listByContractId(id));
        
        return detailVO;
    }
    
    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<Contract> buildQueryWrapper(ContractQueryDTO queryDTO) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        
        // 合同编号
        if (queryDTO.getContractNo() != null) {
            wrapper.like(Contract::getContractNo, queryDTO.getContractNo());
        }
        
        // 合同名称
        if (queryDTO.getContractName() != null) {
            wrapper.like(Contract::getContractName, queryDTO.getContractName());
        }
        
        // 合同类型
        if (queryDTO.getContractType() != null) {
            wrapper.eq(Contract::getContractType, queryDTO.getContractType());
        }
        
        // 合同状态
        if (queryDTO.getStatus() != null) {
            wrapper.eq(Contract::getStatus, queryDTO.getStatus());
        }
        
        // 客户ID
        if (queryDTO.getClientId() != null) {
            wrapper.eq(Contract::getClientId, queryDTO.getClientId());
        }
        
        // 排序
        wrapper.orderByDesc(Contract::getUpdateTime);
        
        return wrapper;
    }

    /**
     * 验证客户是否存在
     * @param clientId 客户ID
     * @throws IllegalArgumentException 如果客户不存在
     */
    private void validateClient(Long clientId) {
        if (clientService == null) {
            log.warn("客户服务未注入，无法验证客户是否存在，ID: {}", clientId);
            return;
        }
        
        try {
            ClientDTO client = clientService.getClientDetail(clientId);
            if (client == null) {
                throw new IllegalArgumentException("客户不存在，ID: " + clientId);
            }
            log.info("客户验证通过，客户ID: {}，客户名称: {}", clientId, client.getClientName());
        } catch (Exception e) {
            log.error("验证客户时发生异常，客户ID: {}", clientId, e);
            throw new IllegalArgumentException("验证客户失败: " + e.getMessage());
        }
    }
} 
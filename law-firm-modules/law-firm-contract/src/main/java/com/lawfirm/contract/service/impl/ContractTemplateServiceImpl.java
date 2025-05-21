package com.lawfirm.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.contract.util.ContractTemplateConverter;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.contract.dto.ContractTemplateCreateDTO;
import com.lawfirm.model.contract.dto.ContractTemplateQueryDTO;
import com.lawfirm.model.contract.dto.ContractTemplateUpdateDTO;
import com.lawfirm.model.contract.entity.ContractTemplate;
import com.lawfirm.model.contract.mapper.ContractTemplateMapper;
import com.lawfirm.model.contract.service.ContractTemplateService;
import com.lawfirm.model.contract.vo.ContractTemplateDetailVO;
import com.lawfirm.model.contract.vo.ContractTemplateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.search.service.SearchService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 合同模板服务实现类
 */
@Slf4j
@Service("contractTemplateServiceImpl")
@RequiredArgsConstructor
public class ContractTemplateServiceImpl extends BaseServiceImpl<ContractTemplateMapper, ContractTemplate> implements ContractTemplateService {

    private final ContractTemplateMapper contractTemplateMapper;

    /**
     * 注入core层审计服务，便于后续记录模板操作日志
     */
    @Autowired(required = false)
    @Qualifier("clientAuditService")
    private AuditService auditService;

    /**
     * 注入core层消息发送服务，便于后续模板相关通知等
     */
    @Autowired(required = false)
    @Qualifier("clientMessageSender")
    private MessageSender messageSender;

    /**
     * 注入core层文件存储服务，便于后续模板附件上传等
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
    public boolean exists(QueryWrapper<ContractTemplate> queryWrapper) {
        return count(queryWrapper) > 0;
    }

    @Override
    public boolean save(ContractTemplate entity) {
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(List<ContractTemplate> entities) {
        return super.saveBatch(entities);
    }

    @Override
    public boolean update(ContractTemplate entity) {
        return updateById(entity);
    }

    @Override
    public boolean updateBatch(List<ContractTemplate> entities) {
        return updateBatchById(entities);
    }

    @Override
    public boolean remove(Long id) {
        return removeById(id);
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return removeByIds(ids);
    }

    @Override
    public ContractTemplate getById(Long id) {
        return super.getById(id);
    }

    @Override
    public List<ContractTemplate> list(QueryWrapper<ContractTemplate> wrapper) {
        return super.list(wrapper);
    }

    @Override
    public Page<ContractTemplate> page(Page<ContractTemplate> page, QueryWrapper<ContractTemplate> wrapper) {
        return super.page(page, wrapper);
    }

    @Override
    public long count(QueryWrapper<ContractTemplate> wrapper) {
        return super.count(wrapper);
    }

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
    public Long createTemplate(ContractTemplateCreateDTO createDTO) {
        log.info("创建合同模板: {}", createDTO);
        
        // 转换DTO为实体
        ContractTemplate template = ContractTemplateConverter.toEntity(createDTO);
        
        // 设置创建人信息
        template.setCreatorId(getCurrentUserId());
        
        // 保存模板
        save(template);
        return template.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTemplate(ContractTemplateUpdateDTO updateDTO) {
        log.info("更新合同模板: {}", updateDTO);
        
        // 获取模板
        ContractTemplate template = getById(updateDTO.getId());
        if (template == null) {
            log.error("合同模板不存在: {}", updateDTO.getId());
            return false;
        }
        
        // 更新模板信息
        ContractTemplateConverter.updateEntity(template, updateDTO);
        
        // 设置更新人信息
        template.setReviewerId(getCurrentUserId());
        
        return updateById(template);
    }

    @Override
    public List<ContractTemplateVO> listTemplates(ContractTemplateQueryDTO queryDTO) {
        log.info("查询合同模板列表: {}", queryDTO);
        
        // 构建查询条件
        QueryWrapper<ContractTemplate> wrapper = buildQueryWrapper(queryDTO);
        
        // 查询模板列表
        List<ContractTemplate> templates = list(wrapper);
        
        // 转换为VO
        return templates.stream()
                .map(ContractTemplateConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public IPage<ContractTemplateVO> pageTemplates(IPage<ContractTemplateVO> page, ContractTemplateQueryDTO queryDTO) {
        log.info("分页查询合同模板: page={}, queryDTO={}", page, queryDTO);
        
        // 构建查询条件
        QueryWrapper<ContractTemplate> wrapper = buildQueryWrapper(queryDTO);
        
        // 分页查询
        Page<ContractTemplate> templatePage = new Page<>(page.getCurrent(), page.getSize());
        Page<ContractTemplate> result = page(templatePage, wrapper);
        
        // 转换为VO
        return result.convert(ContractTemplateConverter::toVO);
    }
    
    /**
     * 构建查询条件
     */
    private QueryWrapper<ContractTemplate> buildQueryWrapper(ContractTemplateQueryDTO queryDTO) {
        QueryWrapper<ContractTemplate> wrapper = new QueryWrapper<>();
        
        // 添加查询条件
        if (queryDTO.getTemplateName() != null) {
            wrapper.like("template_name", queryDTO.getTemplateName());
        }
        if (queryDTO.getTemplateCode() != null) {
            wrapper.eq("template_code", queryDTO.getTemplateCode());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq("status", queryDTO.getStatus());
        }
        if (queryDTO.getCategory() != null) {
            wrapper.eq("category", queryDTO.getCategory().toString());
        }
        
        // 添加排序条件
        wrapper.orderByDesc("create_time");
        
        return wrapper;
    }

    @Override
    public ContractTemplateDetailVO getTemplateDetail(Long id) {
        log.info("获取合同模板详情: {}", id);
        
        // 获取模板
        ContractTemplate template = getById(id);
        if (template == null) {
            log.error("合同模板不存在: {}", id);
            return null;
        }
        
        // 转换为详情VO
        return ContractTemplateConverter.toDetailVO(template);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean enableTemplate(Long id) {
        log.info("启用合同模板: {}", id);
        
        // 获取模板
        ContractTemplate template = getById(id);
        if (template == null) {
            log.error("合同模板不存在: {}", id);
            return false;
        }
        
        // 更新状态为启用
        template.setStatus(1); // 1-启用
        template.setReviewerId(getCurrentUserId());
        
        return updateById(template);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disableTemplate(Long id) {
        log.info("禁用合同模板: {}", id);
        
        // 获取模板
        ContractTemplate template = getById(id);
        if (template == null) {
            log.error("合同模板不存在: {}", id);
            return false;
        }
        
        // 更新状态为禁用
        template.setStatus(0); // 0-禁用
        template.setReviewerId(getCurrentUserId());
        
        return updateById(template);
    }
} 
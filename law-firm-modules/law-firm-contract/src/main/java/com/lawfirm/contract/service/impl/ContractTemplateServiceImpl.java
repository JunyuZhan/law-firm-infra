package com.lawfirm.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.contract.dto.ContractTemplateCreateDTO;
import com.lawfirm.model.contract.dto.ContractTemplateQueryDTO;
import com.lawfirm.model.contract.dto.ContractTemplateUpdateDTO;
import com.lawfirm.model.contract.entity.ContractTemplate;
import com.lawfirm.model.contract.mapper.ContractTemplateMapper;
import com.lawfirm.model.contract.service.ContractTemplateService;
import com.lawfirm.model.contract.vo.ContractTemplateDetailVO;
import com.lawfirm.model.contract.vo.ContractTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 合同模板服务实现类
 */
@Slf4j
@Service("contractTemplateService")
public class ContractTemplateServiceImpl extends ServiceImpl<ContractTemplateMapper, ContractTemplate> implements ContractTemplateService {

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
    @Transactional(rollbackFor = Exception.class)
    public Long createTemplate(ContractTemplateCreateDTO createDTO) {
        log.info("创建合同模板: {}", createDTO);
        
        // TODO: 实现具体业务逻辑
        ContractTemplate template = new ContractTemplate();
        BeanUtils.copyProperties(createDTO, template);
        
        // 设置默认值
        template.setStatus(1); // 默认启用
        template.setUsageCount(0); // 初始使用次数为0
        
        save(template);
        return template.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTemplate(ContractTemplateUpdateDTO updateDTO) {
        log.info("更新合同模板: {}", updateDTO);
        
        // TODO: 实现具体业务逻辑
        ContractTemplate template = getById(updateDTO.getId());
        if (template == null) {
            log.error("合同模板不存在: {}", updateDTO.getId());
            return false;
        }
        
        BeanUtils.copyProperties(updateDTO, template);
        return updateById(template);
    }

    @Override
    public List<ContractTemplateVO> listTemplates(ContractTemplateQueryDTO queryDTO) {
        log.info("查询合同模板列表: {}", queryDTO);
        
        // TODO: 实现具体业务逻辑
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
            wrapper.eq("category", queryDTO.getCategory());
        }
        
        // 转换结果
        List<ContractTemplate> templates = list(wrapper);
        List<ContractTemplateVO> result = new ArrayList<>(templates.size());
        
        for (ContractTemplate template : templates) {
            ContractTemplateVO vo = new ContractTemplateVO();
            BeanUtils.copyProperties(template, vo);
            result.add(vo);
        }
        
        return result;
    }

    @Override
    public IPage<ContractTemplateVO> pageTemplates(IPage<ContractTemplateVO> page, ContractTemplateQueryDTO queryDTO) {
        log.info("分页查询合同模板: page={}, queryDTO={}", page, queryDTO);
        
        // TODO: 实现具体业务逻辑
        Page<ContractTemplate> templatePage = new Page<>(page.getCurrent(), page.getSize());
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
            wrapper.eq("category", queryDTO.getCategory());
        }
        
        // 执行查询
        Page<ContractTemplate> result = page(templatePage, wrapper);
        
        // 转换结果
        IPage<ContractTemplateVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<ContractTemplateVO> records = new ArrayList<>(result.getRecords().size());
        
        for (ContractTemplate template : result.getRecords()) {
            ContractTemplateVO vo = new ContractTemplateVO();
            BeanUtils.copyProperties(template, vo);
            records.add(vo);
        }
        
        voPage.setRecords(records);
        return voPage;
    }

    @Override
    public ContractTemplateDetailVO getTemplateDetail(Long id) {
        log.info("获取合同模板详情: {}", id);
        
        // TODO: 实现具体业务逻辑
        ContractTemplate template = getById(id);
        if (template == null) {
            log.error("合同模板不存在: {}", id);
            return null;
        }
        
        ContractTemplateDetailVO detailVO = new ContractTemplateDetailVO();
        BeanUtils.copyProperties(template, detailVO);
        
        return detailVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean enableTemplate(Long id) {
        log.info("启用合同模板: {}", id);
        
        // TODO: 实现具体业务逻辑
        ContractTemplate template = getById(id);
        if (template == null) {
            log.error("合同模板不存在: {}", id);
            return false;
        }
        
        template.setStatus(1); // 1-启用
        return updateById(template);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disableTemplate(Long id) {
        log.info("禁用合同模板: {}", id);
        
        // TODO: 实现具体业务逻辑
        ContractTemplate template = getById(id);
        if (template == null) {
            log.error("合同模板不存在: {}", id);
            return false;
        }
        
        template.setStatus(0); // 0-禁用
        return updateById(template);
    }
} 
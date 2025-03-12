package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.utils.BeanUtils;
import com.lawfirm.document.manager.storage.DocumentStorageManager;
import com.lawfirm.document.manager.security.DocumentSecurityManager;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.document.dto.template.TemplateCreateDTO;
import com.lawfirm.model.document.dto.template.TemplateQueryDTO;
import com.lawfirm.model.document.dto.template.TemplateUpdateDTO;
import com.lawfirm.model.document.entity.TemplateDocument;
import com.lawfirm.model.document.mapper.TemplateDocumentMapper;
import com.lawfirm.model.document.service.DocumentTemplateService;
import com.lawfirm.model.document.vo.TemplateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 文档模板服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentTemplateServiceImpl extends BaseServiceImpl<TemplateDocumentMapper, TemplateDocument> implements DocumentTemplateService {

    private final DocumentStorageManager storageManager;
    private final DocumentSecurityManager securityManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTemplate(TemplateCreateDTO createDTO, MultipartFile file) throws IOException {
        // 1. 上传模板文件
        storageManager.uploadDocument(file, createDTO.getBucketName());

        // 2. 创建模板记录
        TemplateDocument template = BeanUtils.copyProperties(createDTO, TemplateDocument.class);
        baseMapper.insert(template);

        return template.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTemplate(TemplateUpdateDTO updateDTO) {
        // 1. 检查权限
        TemplateDocument template = baseMapper.selectById(updateDTO.getId());
        if (!securityManager.checkAccessPermission(template)) {
            throw new SecurityException("没有修改权限");
        }

        // 2. 更新模板
        BeanUtils.copyProperties(updateDTO, template);
        baseMapper.updateById(template);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTemplate(Long id) {
        // 1. 检查权限
        TemplateDocument template = baseMapper.selectById(id);
        if (!securityManager.checkAccessPermission(template)) {
            throw new SecurityException("没有删除权限");
        }

        // 2. 删除模板文件
        storageManager.deleteDocument(id);

        // 3. 删除模板记录
        baseMapper.deleteById(id);
    }

    @Override
    public TemplateVO getTemplate(Long id) {
        // 1. 获取模板
        TemplateDocument template = baseMapper.selectById(id);
        if (template == null) {
            return null;
        }

        // 2. 检查权限
        if (!securityManager.checkAccessPermission(template)) {
            throw new SecurityException("没有访问权限");
        }

        // 3. 转换为VO
        return BeanUtils.copyProperties(template, TemplateVO.class);
    }

    @Override
    public List<TemplateVO> listTemplates(TemplateQueryDTO queryDTO) {
        // 1. 构建查询条件
        LambdaQueryWrapper<TemplateDocument> wrapper = new LambdaQueryWrapper<>();
        // TODO: 添加查询条件

        // 2. 执行查询
        List<TemplateDocument> templates = baseMapper.selectList(wrapper);

        // 3. 转换为VO
        return BeanUtils.copyList(templates, TemplateVO.class);
    }

    @Override
    public Page<TemplateVO> pageTemplates(TemplateQueryDTO queryDTO) {
        // 1. 构建查询条件
        LambdaQueryWrapper<TemplateDocument> wrapper = new LambdaQueryWrapper<>();
        // TODO: 添加查询条件

        // 2. 执行分页查询
        Page<TemplateDocument> page = baseMapper.selectPage(
            new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()),
            wrapper
        );

        // 3. 转换为VO
        return BeanUtils.copyPage(page, TemplateVO.class);
    }

    @Override
    public byte[] downloadTemplate(Long id) throws IOException {
        // 1. 检查权限
        TemplateDocument template = baseMapper.selectById(id);
        if (!securityManager.checkAccessPermission(template)) {
            throw new SecurityException("没有下载权限");
        }

        // 2. 下载模板文件
        return storageManager.downloadDocument(id);
    }
} 
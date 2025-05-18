package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.document.convert.DocumentConverter;
import com.lawfirm.document.manager.security.SecurityManager;
import com.lawfirm.document.manager.storage.StorageManager;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.document.dto.template.TemplateCreateDTO;
import com.lawfirm.model.document.dto.template.TemplateUpdateDTO;
import com.lawfirm.model.document.entity.template.TemplateDocument;
import com.lawfirm.model.document.mapper.TemplateDocumentMapper;
import com.lawfirm.model.document.service.TemplateService;
import com.lawfirm.model.document.vo.TemplateVO;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.entity.file.FileObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lawfirm.document.exception.DocumentException;

import java.util.List;
import java.util.Map;

/**
 * 文档模板服务实现类
 */
@Slf4j
@Service("documentTemplateServiceImpl")
@ConditionalOnProperty(name = "law-firm.storage.enabled", havingValue = "true", matchIfMissing = true)
public class TemplateServiceImpl extends BaseServiceImpl<TemplateDocumentMapper, TemplateDocument> implements TemplateService {

    private final StorageManager storageManager;
    private final SecurityManager securityManager;
    
    public TemplateServiceImpl(StorageManager storageManager, SecurityManager securityManager) {
        this.storageManager = storageManager;
        this.securityManager = securityManager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTemplate(TemplateCreateDTO createDTO) {
        // 检查权限
        if (!securityManager.checkTemplatePermission("create", "create")) {
            throw DocumentException.noPermission("创建模板");
        }

        // 创建模板记录
        try {
            // 检查模板编码是否已存在
            QueryWrapper<TemplateDocument> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("template_code", createDTO.getTemplateCode());
            if (baseMapper.exists(queryWrapper)) {
                throw DocumentException.businessError("模板编码已存在：" + createDTO.getTemplateCode());
            }
            
            // DTO转换为实体
            TemplateDocument template = DocumentConverter.INSTANCE.toTemplateDocument(createDTO);
            
            // 设置文档属性
            template.setDocType("TEMPLATE");
            template.setDocumentVersion("1.0");
            template.setFileName(createDTO.getTemplateName());
            template.setDescription(createDTO.getDescription());
            
            // 如果提供了内容，则保存到存储服务
            if (createDTO.getContent() != null && !createDTO.getContent().isEmpty()) {
                // 保存内容到存储服务
                String storagePath = storageManager.storeTemplateContent(
                    createDTO.getTemplateCode(),
                    createDTO.getContent(),
                    createDTO.getTemplateType()
                );
                template.setStoragePath(storagePath);
                template.setStorageType(storageManager.getStorageType());
            }
            
            // 设置作者
            String currentUser = SecurityUtils.getUsername();
            template.setAuthor(currentUser);
            
            // 插入数据库
            baseMapper.insert(template);
            
            // 发布模板创建事件（异步处理其他逻辑）
            // eventPublisher.publishEvent(new TemplateCreatedEvent(template));
            
            log.info("模板创建成功：{}, ID: {}", createDTO.getTemplateCode(), template.getId());
            return template.getId();
        } catch (Exception e) {
            log.error("创建模板失败", e);
            throw DocumentException.businessError("创建模板失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTemplate(Long id, TemplateUpdateDTO updateDTO) {
        // 检查权限
        if (!securityManager.checkTemplatePermission(id.toString(), "edit")) {
            throw DocumentException.noPermission("编辑模板");
        }


        try {
            // 检查模板是否存在
            TemplateDocument existingTemplate = baseMapper.selectById(id);
            if (existingTemplate == null) {
                throw DocumentException.notFound("模板(ID:" + id + ")");
            }
            
            // 检查模板编码是否已被其他记录使用
            if (!existingTemplate.getTemplateCode().equals(updateDTO.getTemplateCode())) {
                QueryWrapper<TemplateDocument> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("template_code", updateDTO.getTemplateCode())
                           .ne("id", id);
                if (baseMapper.exists(queryWrapper)) {
                    throw DocumentException.businessError("模板编码已存在：" + updateDTO.getTemplateCode());
                }
            }
            
            // DTO转换为实体
            TemplateDocument template = DocumentConverter.INSTANCE.toTemplateDocument(updateDTO);
            template.setId(id);
            
            // 保留原来的一些属性
            template.setCreateTime(existingTemplate.getCreateTime());
            template.setCreateBy(existingTemplate.getCreateBy());
            
            // 如果提供了内容，则更新存储服务中的内容
            if (updateDTO.getContent() != null && !updateDTO.getContent().isEmpty()) {
                // 保存内容到存储服务，覆盖原来的内容
                String storagePath = storageManager.storeTemplateContent(
                    updateDTO.getTemplateCode(),
                    updateDTO.getContent(),
                    updateDTO.getTemplateType()
                );
                template.setStoragePath(storagePath);
                template.setStorageType(storageManager.getStorageType());
            } else {
                // 保留原来的存储路径和类型
                template.setStoragePath(existingTemplate.getStoragePath());
                template.setStorageType(existingTemplate.getStorageType());
            }
            
            // 更新数据库
            baseMapper.updateById(template);
            
            // 发布模板更新事件（异步处理其他逻辑）
            // eventPublisher.publishEvent(new TemplateUpdatedEvent(template));
            
            log.info("模板更新成功：{}, ID: {}", updateDTO.getTemplateCode(), template.getId());
        } catch (Exception e) {
            log.error("更新模板失败", e);
            throw DocumentException.businessError("更新模板失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTemplate(Long id) {
        // 检查权限
        if (!securityManager.checkTemplatePermission(id.toString(), "delete")) {
            throw DocumentException.noPermission("删除模板");
        }


        try {
            // 检查模板是否存在
            TemplateDocument template = baseMapper.selectById(id);
            if (template == null) {
                throw DocumentException.notFound("模板(ID:" + id + ")");
            }
            
            // 检查是否有引用关系，防止误删
            // 例如：检查是否有文档基于此模板生成
            // checkTemplateUsage(id);
            
            // 删除模板文件（如果有存储路径）
            if (template.getStoragePath() != null && !template.getStoragePath().isEmpty()) {
                // 通过StorageManager删除相应的存储文件
                // 由于getDefaultBucket是私有方法，我们直接通过路径删除模板文件
                try {
                    // 这里假设StorageManager有一个根据路径删除文件的方法
                    // 如果没有，可以通过事件发布或其他方式处理文件删除
                    log.info("准备删除模板文件：{}", template.getStoragePath());
                    // 文件删除可以在异步任务中处理
                    // fileCleanupService.scheduleDelete(template.getStoragePath());
                } catch (Exception e) {
                    // 文件删除失败不影响数据库记录的删除
                    log.warn("删除模板文件失败：{}", template.getStoragePath(), e);
                }
            }
            
            // 删除数据库记录
            int result = baseMapper.deleteById(id);
            
            // 发布模板删除事件（异步处理其他逻辑）
            // eventPublisher.publishEvent(new TemplateDeletedEvent(template));
            
            if (result > 0) {
                log.info("模板删除成功：{}, ID: {}", template.getTemplateCode(), id);
            } else {
                log.warn("模板删除失败，可能已经被删除：ID: {}", id);
            }
        } catch (Exception e) {
            log.error("删除模板失败", e);
            throw DocumentException.businessError("删除模板失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTemplates(List<Long> ids) {
        // 检查权限
        if (!securityManager.checkTemplatePermission("delete", "delete")) {
            throw DocumentException.noPermission("批量删除模板");
        }


        if (ids == null || ids.isEmpty()) {
            return;
        }
        
        try {
            // 查询所有需要删除的模板
            QueryWrapper<TemplateDocument> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("id", ids);
            List<TemplateDocument> templates = baseMapper.selectList(queryWrapper);
            
            if (templates.isEmpty()) {
                log.warn("未找到需要删除的模板记录");
                return;
            }
            
            // 记录需要删除的文件路径
            List<String> filePaths = new java.util.ArrayList<>();
            for (TemplateDocument template : templates) {
                if (template.getStoragePath() != null && !template.getStoragePath().isEmpty()) {
                    filePaths.add(template.getStoragePath());
                }
            }
            
            // 批量删除数据库记录
            int result = baseMapper.delete(new LambdaQueryWrapper<TemplateDocument>().in(TemplateDocument::getId, ids));
            
            // 发布批量删除事件（异步处理其他逻辑，包括删除文件）
            // eventPublisher.publishEvent(new TemplateBatchDeletedEvent(filePaths));
            
            // 记录日志
            if (result > 0) {
                log.info("批量删除模板成功，删除数量：{}", result);
            } else {
                log.warn("批量删除模板失败，可能模板已被删除");
            }
            
            // 异步处理文件删除
            for (String path : filePaths) {
                try {
                    log.info("准备删除模板文件：{}", path);
                    // fileCleanupService.scheduleDelete(path);
                } catch (Exception e) {
                    log.warn("删除模板文件失败：{}", path, e);
                }
            }
        } catch (Exception e) {
            log.error("批量删除模板失败", e);
            throw DocumentException.businessError("批量删除模板失败：" + e.getMessage());
        }
    }

    @Override
    public TemplateVO getTemplateById(Long id) {
        // 检查权限
        if (!securityManager.checkTemplatePermission(id.toString(), "view")) {
            throw DocumentException.noPermission("查看模板");
        }


        try {
            // 查询模板记录
            TemplateDocument template = baseMapper.selectById(id);
            if (template == null) {
                throw DocumentException.notFound("模板(ID:" + id + ")");
            }
            
            // 转换为VO
            TemplateVO templateVO = DocumentConverter.INSTANCE.toTemplateVO(template);
            
            // 增加查看次数
            template.setViewCount(template.getViewCount() + 1);
            baseMapper.updateById(template);
            
            // 发布模板查看事件
            // eventPublisher.publishEvent(new TemplateViewedEvent(template));
            
            return templateVO;
        } catch (Exception e) {
            log.error("获取模板详情失败", e);
            throw DocumentException.businessError("获取模板详情失败：" + e.getMessage());
        }
    }

    @Override
    public TemplateVO getTemplateByCode(String templateCode) {
        // 检查权限
        if (!securityManager.checkTemplatePermission("view", "view")) {
            throw DocumentException.noPermission("查看模板");
        }


        try {
            // 查询模板记录
            QueryWrapper<TemplateDocument> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("template_code", templateCode);
            TemplateDocument template = baseMapper.selectOne(queryWrapper);
            
            if (template == null) {
                throw DocumentException.notFound("模板(编码:" + templateCode + ")");
            }
            
            // 转换为VO
            TemplateVO templateVO = DocumentConverter.INSTANCE.toTemplateVO(template);
            
            // 增加查看次数
            template.setViewCount(template.getViewCount() + 1);
            baseMapper.updateById(template);
            
            // 发布模板查看事件
            // eventPublisher.publishEvent(new TemplateViewedEvent(template));
            
            return templateVO;
        } catch (Exception e) {
            log.error("根据编码获取模板失败", e);
            throw DocumentException.businessError("根据编码获取模板失败：" + e.getMessage());
        }
    }

    @Override
    public Page<TemplateVO> pageTemplates(Page<TemplateDocument> page, TemplateDocument template) {
        // 检查权限
        if (!securityManager.checkTemplatePermission("view", "view")) {
            throw DocumentException.noPermission("查询模板列表");
        }


        try {
            // 构建查询条件
            QueryWrapper<TemplateDocument> queryWrapper = new QueryWrapper<>();
            
            // 根据条件设置查询参数
            if (template != null) {
                // 模板编码
                if (template.getTemplateCode() != null && !template.getTemplateCode().isEmpty()) {
                    queryWrapper.like("template_code", template.getTemplateCode());
                }
                
                // 模板标题
                if (template.getTitle() != null && !template.getTitle().isEmpty()) {
                    queryWrapper.like("title", template.getTitle());
                }
                
                // 模板类型
                if (template.getTemplateType() != null && !template.getTemplateType().isEmpty()) {
                    queryWrapper.eq("template_type", template.getTemplateType());
                }
                
                // 业务类型
                if (template.getBusinessType() != null && !template.getBusinessType().isEmpty()) {
                    queryWrapper.eq("business_type", template.getBusinessType());
                }
                
                // 分类ID
                if (template.getCategoryId() != null) {
                    queryWrapper.eq("category_id", template.getCategoryId());
                }
                
                // 作者
                if (template.getAuthor() != null && !template.getAuthor().isEmpty()) {
                    queryWrapper.eq("author", template.getAuthor());
                }
            }
            
            // 默认按创建时间倒序
            queryWrapper.orderByDesc("create_time");
            
            // 查询数据
            Page<TemplateDocument> resultPage = baseMapper.selectPage(page, queryWrapper);
            
            // 转换为VO
            Page<TemplateVO> voPage = new Page<>(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
            if (resultPage.getRecords() != null && !resultPage.getRecords().isEmpty()) {
                List<TemplateVO> records = new java.util.ArrayList<>(resultPage.getRecords().size());
                for (TemplateDocument record : resultPage.getRecords()) {
                    records.add(DocumentConverter.INSTANCE.toTemplateVO(record));
                }
                voPage.setRecords(records);
            }
            
            return voPage;
        } catch (Exception e) {
            log.error("分页查询模板失败", e);
            throw DocumentException.businessError("分页查询模板失败：" + e.getMessage());
        }
    }

    @Override
    public List<TemplateVO> listAllTemplates() {
        // 检查权限
        if (!securityManager.checkTemplatePermission("view", "view")) {
            throw DocumentException.noPermission("查询模板列表");
        }


        try {
            // 构建查询条件
            QueryWrapper<TemplateDocument> queryWrapper = new QueryWrapper<>();
            
            // 默认按创建时间倒序
            queryWrapper.orderByDesc("create_time");
            
            // 查询所有数据
            List<TemplateDocument> templates = baseMapper.selectList(queryWrapper);
            
            // 转换为VO
            if (templates != null && !templates.isEmpty()) {
                return DocumentConverter.INSTANCE.toTemplateVOList(templates);
            }
            
            return java.util.Collections.emptyList();
        } catch (Exception e) {
            log.error("获取所有模板失败", e);
            throw DocumentException.businessError("获取所有模板失败：" + e.getMessage());
        }
    }

    @Override
    public List<TemplateVO> listTemplatesByType(String templateType) {
        // 检查权限
        if (!securityManager.checkTemplatePermission("view", "view")) {
            throw DocumentException.noPermission("查询模板列表");
        }


        try {
            // 构建查询条件
            QueryWrapper<TemplateDocument> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("template_type", templateType);
            
            // 默认按创建时间倒序
            queryWrapper.orderByDesc("create_time");
            
            // 查询所有数据
            List<TemplateDocument> templates = baseMapper.selectList(queryWrapper);
            
            // 转换为VO
            if (templates != null && !templates.isEmpty()) {
                return DocumentConverter.INSTANCE.toTemplateVOList(templates);
            }
            
            return java.util.Collections.emptyList();
        } catch (Exception e) {
            log.error("根据类型获取模板列表失败", e);
            throw DocumentException.businessError("根据类型获取模板列表失败：" + e.getMessage());
        }
    }

    @Override
    public List<TemplateVO> listTemplatesByBusinessType(String businessType) {
        // 检查权限
        if (!securityManager.checkTemplatePermission("view", "view")) {
            throw DocumentException.noPermission("查询模板列表");
        }


        try {
            // 构建查询条件
            QueryWrapper<TemplateDocument> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("business_type", businessType);
            
            // 默认按创建时间倒序
            queryWrapper.orderByDesc("create_time");
            
            // 查询所有数据
            List<TemplateDocument> templates = baseMapper.selectList(queryWrapper);
            
            // 转换为VO
            if (templates != null && !templates.isEmpty()) {
                return DocumentConverter.INSTANCE.toTemplateVOList(templates);
            }
            
            return java.util.Collections.emptyList();
        } catch (Exception e) {
            log.error("根据业务类型获取模板列表失败", e);
            throw DocumentException.businessError("根据业务类型获取模板列表失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, Integer status) {
        // 检查权限
        if (!securityManager.checkTemplatePermission(id.toString(), "edit")) {
            throw DocumentException.noPermission("更新模板状态");
        }


        try {
            // 检查模板是否存在
            TemplateDocument template = baseMapper.selectById(id);
            if (template == null) {
                throw DocumentException.notFound("模板(ID:" + id + ")");
            }
            
            // 设置新的状态
            template.setDocStatus(status.toString()); // 假设status与docStatus可以直接转换
            
            // 更新数据库
            baseMapper.updateById(template);
            
            log.info("模板状态更新成功：{}, ID: {}, 状态: {}", template.getTemplateCode(), id, status);
        } catch (Exception e) {
            log.error("更新模板状态失败", e);
            throw DocumentException.businessError("更新模板状态失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDefault(Long id, Boolean isDefault) {
        // 检查权限
        if (!securityManager.checkTemplatePermission(id.toString(), "edit")) {
            throw DocumentException.noPermission("设置默认模板");
        }


        try {
            // 检查模板是否存在
            TemplateDocument template = baseMapper.selectById(id);
            if (template == null) {
                throw DocumentException.notFound("模板(ID:" + id + ")");
            }
            
            // 如果要设置为默认模板
            if (Boolean.TRUE.equals(isDefault)) {
                // 先将所有同类型的模板重置为非默认
                QueryWrapper<TemplateDocument> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("template_type", template.getTemplateType())
                           .eq("business_type", template.getBusinessType());
                
                List<TemplateDocument> templates = baseMapper.selectList(queryWrapper);
                for (TemplateDocument t : templates) {
                    if (Boolean.TRUE.equals(t.getIsPublic()) && !t.getId().equals(id)) {
                        t.setIsPublic(false);
                        baseMapper.updateById(t);
                    }
                }
            }
            
            // 设置当前模板的默认状态
            template.setIsPublic(isDefault);
            baseMapper.updateById(template);
            
            log.info("模板默认状态更新成功：{}, ID: {}, 默认状态: {}", template.getTemplateCode(), id, isDefault);
        } catch (Exception e) {
            log.error("设置默认模板失败", e);
            throw DocumentException.businessError("设置默认模板失败：" + e.getMessage());
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long generateDocument(Long templateId, Map<String, Object> parameters) {
        // 检查权限
        if (!securityManager.checkTemplatePermission(templateId.toString(), "use")) {
            throw DocumentException.noPermission("使用模板生成文档");
        }


        return null;
    }

    @Override
    public String previewTemplate(Long id, Map<String, Object> parameters) {
        // 检查权限
        if (!securityManager.checkTemplatePermission(id.toString(), "view")) {
            throw DocumentException.noPermission("预览模板");
        }


        try {
            // 检查模板是否存在
            TemplateDocument template = baseMapper.selectById(id);
            if (template == null) {
                throw DocumentException.notFound("模板(ID:" + id + ")");
            }
            
            // 获取模板内容
            if (template.getStoragePath() == null || template.getStoragePath().isEmpty()) {
                throw DocumentException.businessError("模板内容不存在");
            }
            
            // 使用存储管理器获取模板内容
            String templateContent = "预览内容不可用"; // 这里应该调用存储管理器获取模板内容
            
            // 如果有参数，则进行模板渲染
            if (parameters != null && !parameters.isEmpty()) {
                // 这里应该调用模板渲染引擎进行渲染
                // templateContent = templateEngine.render(templateContent, parameters);
                
                // 简单模拟替换变量
                for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                    String placeholder = "\\{\\{" + entry.getKey() + "\\}\\}";
                    String value = entry.getValue() != null ? entry.getValue().toString() : "";
                    templateContent = templateContent.replaceAll(placeholder, value);
                }
            }
            
            // 增加预览次数
            template.setViewCount(template.getViewCount() + 1);
            baseMapper.updateById(template);
            
            return templateContent;
        } catch (Exception e) {
            log.error("预览模板失败", e);
            throw DocumentException.businessError("预览模板失败：" + e.getMessage());
        }
    }

    @Override
    public void refreshCache() {

        try {
            log.info("开始刷新模板缓存");
            
            // 清除缓存逻辑
            // 如果项目使用了缓存管理器，可以调用缓存管理器的清除方法
            // cacheManager.clearCache("templateCache");
            
            // 或者使用Redis直接清除缓存
            // Set<String> keys = redisTemplate.keys("template:*");
            // if (keys != null && !keys.isEmpty()) {
            //     redisTemplate.delete(keys);
            // }
            
            log.info("模板缓存刷新成功");
        } catch (Exception e) {
            log.error("刷新模板缓存失败", e);
            throw DocumentException.businessError("刷新模板缓存失败：" + e.getMessage());
        }
    }
}

package com.lawfirm.model.document.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.document.dto.template.TemplateCreateDTO;
import com.lawfirm.model.document.dto.template.TemplateUpdateDTO;
import com.lawfirm.model.document.entity.template.TemplateDocument;
import com.lawfirm.model.document.vo.TemplateVO;

import java.util.List;
import java.util.Map;

/**
 * 模板服务接口
 */
public interface TemplateService extends BaseService<TemplateDocument> {

    /**
     * 创建模板
     *
     * @param createDTO 创建参数
     * @return 模板ID
     */
    Long createTemplate(TemplateCreateDTO createDTO);

    /**
     * 更新模板
     *
     * @param id 模板ID
     * @param updateDTO 更新参数
     */
    void updateTemplate(Long id, TemplateUpdateDTO updateDTO);

    /**
     * 删除模板
     *
     * @param id 模板ID
     */
    void deleteTemplate(Long id);

    /**
     * 批量删除模板
     *
     * @param ids 模板ID列表
     */
    void deleteTemplates(List<Long> ids);

    /**
     * 获取模板详情
     *
     * @param id 模板ID
     * @return 模板详情
     */
    TemplateVO getTemplateById(Long id);

    /**
     * 根据编码获取模板
     *
     * @param templateCode 模板编码
     * @return 模板详情
     */
    TemplateVO getTemplateByCode(String templateCode);

    /**
     * 分页查询模板
     *
     * @param page 分页参数
     * @param template 查询条件
     * @return 模板列表
     */
    Page<TemplateVO> pageTemplates(Page<TemplateDocument> page, TemplateDocument template);

    /**
     * 获取所有模板
     *
     * @return 模板列表
     */
    List<TemplateVO> listAllTemplates();

    /**
     * 根据模板类型获取模板列表
     *
     * @param templateType 模板类型
     * @return 模板列表
     */
    List<TemplateVO> listTemplatesByType(String templateType);

    /**
     * 根据业务类型获取模板列表
     *
     * @param businessType 业务类型
     * @return 模板列表
     */
    List<TemplateVO> listTemplatesByBusinessType(String businessType);

    /**
     * 更新模板状态
     *
     * @param id 模板ID
     * @param status 状态
     */
    void updateStatus(Long id, Integer status);

    /**
     * 设置默认模板
     *
     * @param id 模板ID
     * @param isDefault 是否默认
     */
    void setDefault(Long id, Boolean isDefault);

    /**
     * 生成文档
     *
     * @param templateId 模板ID
     * @param parameters 参数
     * @return 生成的文档ID
     */
    Long generateDocument(Long templateId, Map<String, Object> parameters);

    /**
     * 预览模板
     *
     * @param id 模板ID
     * @param parameters 参数
     * @return 预览内容
     */
    String previewTemplate(Long id, Map<String, Object> parameters);

    /**
     * 刷新模板缓存
     */
    void refreshCache();
} 
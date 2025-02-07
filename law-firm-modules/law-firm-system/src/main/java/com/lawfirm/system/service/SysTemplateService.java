package com.lawfirm.system.service;

import com.lawfirm.common.data.service.BaseService;
import com.lawfirm.model.system.entity.SysTemplate;
import com.lawfirm.model.system.vo.SysTemplateVO;
import com.lawfirm.model.system.dto.SysTemplateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 系统模板服务接口
 */
public interface SysTemplateService extends BaseService<SysTemplate, SysTemplateVO> {

    /**
     * 创建模板
     */
    SysTemplateVO create(SysTemplateDTO templateDTO);

    /**
     * 更新模板
     */
    SysTemplateVO update(SysTemplateDTO templateDTO);

    /**
     * 删除模板
     */
    void deleteTemplate(Long id);

    /**
     * 根据编码查询模板
     */
    SysTemplateVO getByCode(String code);

    /**
     * 根据类型查询模板列表
     */
    List<SysTemplateVO> listByType(String type);

    /**
     * 上传模板文件
     */
    SysTemplateVO uploadTemplate(MultipartFile file);
} 
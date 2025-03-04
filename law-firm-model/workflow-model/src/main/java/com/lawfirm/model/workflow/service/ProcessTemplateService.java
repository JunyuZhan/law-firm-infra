package com.lawfirm.model.workflow.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;

/**
 * 流程模板服务接口
 * 提供流程模板管理相关的业务功�? * 
 * @author JunyuZhan
 */
public interface ProcessTemplateService {

    /**
     * 部署流程模板
     * 
     * @param name 模板名称
     * @param key 模板标识
     * @param category 模板分类
     * @param file BPMN文件
     * @return 模板ID
     */
    String deployProcessTemplate(String name, String key, String category, MultipartFile file);

    /**
     * 更新流程模板
     * 
     * @param id 模板ID
     * @param name 模板名称
     * @param category 模板分类
     * @param file BPMN文件
     * @return 模板ID
     */
    String updateProcessTemplate(String id, String name, String category, MultipartFile file);

    /**
     * 获取流程模板
     * 
     * @param id 模板ID
     * @return 流程模板
     */
    Object getProcessTemplate(String id);

    /**
     * 分页查询流程模板
     * 
     * @param key 模板标识
     * @param name 模板名称
     * @param category 模板分类
     * @param current 当前�?     * @param size 每页条数
     * @return 模板分页数据
     */
    Page<?> getProcessTemplatePage(String key, String name, String category, int current, int size);

    /**
     * 删除流程模板
     * 
     * @param id 模板ID
     */
    void deleteProcessTemplate(String id);
} 

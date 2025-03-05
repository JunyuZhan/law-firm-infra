package com.lawfirm.core.workflow.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.workflow.entity.ProcessTemplate;
import com.lawfirm.model.workflow.service.ProcessTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 流程模板控制�? * 提供流程模板管理的RESTful API
 * 
 * @author JunyuZhan
 */
@Slf4j
@RestController
@RequestMapping("/api/workflow/templates")
@Api(tags = "流程模板管理")
@RequiredArgsConstructor
public class ProcessTemplateController {

    private final ProcessTemplateService processTemplateService;

    /**
     * 部署流程模板
     * 
     * @param name 模板名称
     * @param key 模板标识
     * @param category 模板分类
     * @param file BPMN文件
     * @return 模板ID
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("部署流程模板")
    public ResponseEntity<String> deployProcessTemplate(
            @ApiParam("模板名称") @RequestParam String name,
            @ApiParam("模板标识") @RequestParam String key,
            @ApiParam("模板分类") @RequestParam(required = false) String category,
            @ApiParam("BPMN文件") @RequestParam MultipartFile file) {
        log.info("部署流程模板请求, 名称: {}, 标识: {}, 分类: {}", name, key, category);
        String templateId = processTemplateService.deployProcessTemplate(name, key, category, file);
        return ResponseEntity.ok(templateId);
    }

    /**
     * 更新流程模板
     * 
     * @param id 模板ID
     * @param name 模板名称
     * @param category 模板分类
     * @param file BPMN文件
     * @return 模板ID
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("更新流程模板")
    public ResponseEntity<String> updateProcessTemplate(
            @ApiParam("模板ID") @PathVariable String id,
            @ApiParam("模板名称") @RequestParam String name,
            @ApiParam("模板分类") @RequestParam(required = false) String category,
            @ApiParam("BPMN文件") @RequestParam MultipartFile file) {
        log.info("更新流程模板请求, 模板ID: {}, 名称: {}, 分类: {}", id, name, category);
        String templateId = processTemplateService.updateProcessTemplate(id, name, category, file);
        return ResponseEntity.ok(templateId);
    }

    /**
     * 获取流程模板
     * 
     * @param id 模板ID
     * @return 流程模板
     */
    @GetMapping("/{id}")
    @ApiOperation("获取流程模板")
    public ResponseEntity<Object> getProcessTemplate(
            @ApiParam("模板ID") @PathVariable String id) {
        log.info("获取流程模板请求, 模板ID: {}", id);
        Object template = processTemplateService.getProcessTemplate(id);
        return ResponseEntity.ok(template);
    }

    /**
     * 分页查询流程模板
     * 
     * @param key 模板标识
     * @param name 模板名称
     * @param category 模板分类
     * @param current 当前�?     * @param size 每页条数
     * @return 模板分页数据
     */
    @GetMapping
    @ApiOperation("分页查询流程模板")
    public ResponseEntity<Page<?>> getProcessTemplatePage(
            @ApiParam("模板标识") @RequestParam(required = false) String key,
            @ApiParam("模板名称") @RequestParam(required = false) String name,
            @ApiParam("模板分类") @RequestParam(required = false) String category,
            @ApiParam("当前�?) @RequestParam(defaultValue = "1") int current,
            @ApiParam("每页条数") @RequestParam(defaultValue = "10") int size) {
        log.info("分页查询流程模板请求, Key: {}, 名称: {}, 分类: {}", key, name, category);
        Page<?> page = processTemplateService.getProcessTemplatePage(key, name, category, current, size);
        return ResponseEntity.ok(page);
    }

    /**
     * 删除流程模板
     * 
     * @param id 模板ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除流程模板")
    public ResponseEntity<Void> deleteProcessTemplate(
            @ApiParam("模板ID") @PathVariable String id) {
        log.info("删除流程模板请求, 模板ID: {}", id);
        processTemplateService.deleteProcessTemplate(id);
        return ResponseEntity.ok().build();
    }
} 

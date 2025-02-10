package com.lawfirm.core.workflow.controller;

import com.lawfirm.common.web.controller.BaseController;
import com.lawfirm.core.workflow.enums.ProcessTemplateEnum;
import com.lawfirm.core.workflow.service.ProcessTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 流程模板控制器
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workflow/templates")
public class ProcessTemplateController extends BaseController {

    private final ProcessTemplateService processTemplateService;

    /**
     * 部署流程模板
     */
    @PostMapping("/{processKey}/deploy")
    public String deployTemplate(
            @PathVariable String processKey,
            @RequestParam("file") MultipartFile file) {
        ProcessTemplateEnum template = ProcessTemplateEnum.valueOf(processKey);
        return processTemplateService.deployTemplate(template, file);
    }

    /**
     * 更新流程模板
     */
    @PostMapping("/{processKey}/update")
    public String updateTemplate(
            @PathVariable String processKey,
            @RequestParam("file") MultipartFile file) {
        ProcessTemplateEnum template = ProcessTemplateEnum.valueOf(processKey);
        return processTemplateService.updateTemplate(template, file);
    }

    /**
     * 删除流程模板
     */
    @DeleteMapping("/{processKey}")
    public void deleteTemplate(
            @PathVariable String processKey,
            @RequestParam(defaultValue = "false") boolean cascade) {
        ProcessTemplateEnum template = ProcessTemplateEnum.valueOf(processKey);
        processTemplateService.deleteTemplate(template, cascade);
    }

    /**
     * 获取流程模板定义ID
     */
    @GetMapping("/{processKey}/definition-id")
    public String getTemplateDefinitionId(@PathVariable String processKey) {
        ProcessTemplateEnum template = ProcessTemplateEnum.valueOf(processKey);
        return processTemplateService.getTemplateDefinitionId(template);
    }

    /**
     * 获取流程模板XML
     */
    @GetMapping("/{processKey}/xml")
    public ResponseEntity<InputStreamResource> getTemplateXml(@PathVariable String processKey) {
        ProcessTemplateEnum template = ProcessTemplateEnum.valueOf(processKey);
        InputStream inputStream = processTemplateService.getTemplateXml(template);
        if (inputStream == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(new InputStreamResource(inputStream));
    }

    /**
     * 获取流程模板图片
     */
    @GetMapping("/{processKey}/diagram")
    public ResponseEntity<InputStreamResource> getTemplateDiagram(@PathVariable String processKey) {
        ProcessTemplateEnum template = ProcessTemplateEnum.valueOf(processKey);
        InputStream inputStream = processTemplateService.getTemplateDiagram(template);
        if (inputStream == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(inputStream));
    }

    /**
     * 查询流程模板列表
     */
    @GetMapping
    public List<ProcessTemplateEnum> listTemplates(
            @RequestParam(required = false) String category) {
        return processTemplateService.listTemplates(category);
    }

    /**
     * 获取流程模板表单定义
     */
    @GetMapping("/{processKey}/form")
    public Map<String, Object> getTemplateForm(@PathVariable String processKey) {
        ProcessTemplateEnum template = ProcessTemplateEnum.valueOf(processKey);
        return processTemplateService.getTemplateForm(template);
    }

    /**
     * 获取流程模板节点定义
     */
    @GetMapping("/{processKey}/nodes")
    public Map<String, Object> getTemplateNodes(@PathVariable String processKey) {
        ProcessTemplateEnum template = ProcessTemplateEnum.valueOf(processKey);
        return processTemplateService.getTemplateNodes(template);
    }

    /**
     * 验证流程模板定义
     */
    @PostMapping("/validate")
    public boolean validateTemplate(@RequestParam("file") MultipartFile file) {
        return processTemplateService.validateTemplate(file);
    }
} 
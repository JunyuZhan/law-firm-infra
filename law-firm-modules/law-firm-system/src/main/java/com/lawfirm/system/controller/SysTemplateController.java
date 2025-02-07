package com.lawfirm.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lawfirm.common.web.controller.BaseController;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.web.response.R;
import com.lawfirm.model.system.dto.SysTemplateDTO;
import com.lawfirm.model.system.entity.SysTemplate;
import com.lawfirm.model.system.vo.SysTemplateVO;
import com.lawfirm.system.service.SysTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "系统模板管理")
@RestController
@RequestMapping("/system/template")
@RequiredArgsConstructor
public class SysTemplateController extends BaseController {

    private final SysTemplateService templateService;

    @PostMapping("/upload")
    @Operation(summary = "上传模板")
    public R<SysTemplateVO> uploadTemplate(@RequestParam("file") MultipartFile file) {
        return R.ok(templateService.uploadTemplate(file));
    }

    @Operation(summary = "分页查询模板")
    @GetMapping("/page")
    public R<PageResult<SysTemplateVO>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            SysTemplateDTO query) {
        QueryWrapper<SysTemplate> wrapper = new QueryWrapper<>();
        // 构建查询条件
        if (StringUtils.hasText(query.getName())) {
            wrapper.like("name", query.getName());
        }
        if (StringUtils.hasText(query.getCode())) {
            wrapper.eq("code", query.getCode());
        }
        if (query.getType() != null) {
            wrapper.eq("type", query.getType());
        }
        return R.ok(templateService.pageVO(buildPage(), wrapper));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取模板详情")
    public R<SysTemplateVO> getById(@PathVariable Long id) {
        return R.ok(templateService.findById(id));
    }

    @PostMapping
    @Operation(summary = "创建模板")
    public R<SysTemplateVO> create(@RequestBody SysTemplateDTO templateDTO) {
        return R.ok(templateService.create(templateDTO));
    }

    @PutMapping
    @Operation(summary = "更新模板")
    public R<SysTemplateVO> update(@RequestBody SysTemplateDTO templateDTO) {
        return R.ok(templateService.update(templateDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除模板")
    public R<Void> delete(@PathVariable Long id) {
        templateService.deleteTemplate(id);
        return R.ok();
    }

    @PutMapping("/{id}/status/{status}")
    @Operation(summary = "更新模板状态")
    public R<Void> updateStatus(
            @PathVariable Long id,
            @PathVariable Integer status) {
        UpdateWrapper<SysTemplate> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("status", status);
        templateService.update(wrapper);
        return R.ok();
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "根据类型查询模板")
    public R<List<SysTemplateVO>> listByType(@PathVariable String type) {
        return R.ok(templateService.listByType(type));
    }

    @Operation(summary = "查询模板列表")
    @GetMapping("/list")
    public R<List<SysTemplateVO>> list(SysTemplateDTO query) {
        QueryWrapper<SysTemplate> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(query.getType())) {
            wrapper.eq("type", query.getType());
        }
        return R.ok(templateService.listVO(wrapper));
    }
} 
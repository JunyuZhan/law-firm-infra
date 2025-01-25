package com.lawfirm.system.controller;

import com.lawfirm.common.core.domain.R;
import com.lawfirm.model.system.entity.SysDict;
import com.lawfirm.system.service.SysDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统字典控制器
 */
@Tag(name = "字典管理")
@RestController
@RequestMapping("/system/dict")
@RequiredArgsConstructor
public class SysDictController {

    private final SysDictService dictService;

    @Operation(summary = "创建字典")
    @PostMapping
    public R<Void> createDict(@Validated @RequestBody SysDict dict) {
        dictService.createDict(dict);
        return R.ok();
    }

    @Operation(summary = "更新字典")
    @PutMapping
    public R<Void> updateDict(@Validated @RequestBody SysDict dict) {
        dictService.updateDict(dict);
        return R.ok();
    }

    @Operation(summary = "删除字典")
    @DeleteMapping("/{id}")
    public R<Void> deleteDict(@PathVariable Long id) {
        dictService.deleteDict(id);
        return R.ok();
    }

    @Operation(summary = "根据字典类型查询字典列表")
    @GetMapping("/type/{type}")
    public R<List<SysDict>> listByType(@PathVariable String type) {
        return R.ok(dictService.listByType(type));
    }

    @Operation(summary = "根据字典类型和值查询字典")
    @GetMapping("/type/{type}/value/{value}")
    public R<SysDict> getByTypeAndValue(@PathVariable String type, @PathVariable String value) {
        return R.ok(dictService.getByTypeAndValue(type, value));
    }

    @Operation(summary = "查询所有字典类型")
    @GetMapping("/types")
    public R<List<String>> listAllTypes() {
        return R.ok(dictService.listAllTypes());
    }

    @Operation(summary = "刷新字典缓存")
    @PostMapping("/refresh")
    public R<Void> refreshCache() {
        dictService.refreshCache();
        return R.ok();
    }
} 
package com.lawfirm.system.controller.dict;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.common.security.annotation.RequiresPermissions;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.model.system.dto.dict.DictCreateDTO;
import com.lawfirm.model.system.dto.dict.DictUpdateDTO;
import com.lawfirm.model.system.entity.dict.SysDict;
import com.lawfirm.model.system.service.DictService;
import com.lawfirm.model.system.vo.dict.DictVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统字典控制器
 */
@Tag(name = "系统字典管理")
@RestController
@RequestMapping("/system/dict")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DictController extends BaseController {

    private final DictService dictService;

    /**
     * 获取字典列表
     */
    @Operation(summary = "获取字典列表")
    @GetMapping("/list")
    @RequiresPermissions("system:dict:list")
    public CommonResult<Page<DictVO>> list(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "字典名称") @RequestParam(required = false) String dictName,
            @Parameter(description = "字典编码") @RequestParam(required = false) String dictCode,
            @Parameter(description = "字典类型") @RequestParam(required = false) String dictType) {
        
        Page<SysDict> page = new Page<>(pageNum, pageSize);
        SysDict dict = new SysDict();
        dict.setDictName(dictName);
        dict.setDictCode(dictCode);
        dict.setDictType(dictType);
        
        Page<DictVO> result = dictService.pageDicts(page, dict);
        return success(result);
    }

    /**
     * 获取字典详情
     */
    @Operation(summary = "获取字典详情")
    @GetMapping("/{id}")
    @RequiresPermissions("system:dict:query")
    public CommonResult<DictVO> getInfo(@Parameter(description = "字典ID") @PathVariable Long id) {
        DictVO dict = dictService.getDictById(id);
        return success(dict);
    }

    /**
     * 根据编码获取字典
     */
    @Operation(summary = "根据编码获取字典")
    @GetMapping("/code/{dictCode}")
    public CommonResult<DictVO> getDictByCode(@Parameter(description = "字典编码") @PathVariable String dictCode) {
        DictVO dict = dictService.getDictByCode(dictCode);
        return success(dict);
    }

    /**
     * 根据类型获取字典列表
     */
    @Operation(summary = "根据类型获取字典列表")
    @GetMapping("/type/{dictType}")
    public CommonResult<List<DictVO>> getDictsByType(@Parameter(description = "字典类型") @PathVariable String dictType) {
        List<DictVO> dicts = dictService.listDictsByType(dictType);
        return success(dicts);
    }

    /**
     * 获取所有字典
     */
    @Operation(summary = "获取所有字典")
    @GetMapping("/all")
    @RequiresPermissions("system:dict:list")
    public CommonResult<List<DictVO>> getAllDicts() {
        List<DictVO> dicts = dictService.listAllDicts();
        return success(dicts);
    }

    /**
     * 新增字典
     */
    @Operation(summary = "新增字典")
    @PostMapping
    @RequiresPermissions("system:dict:add")
    @Log(title = "字典管理", businessType = "INSERT")
    public CommonResult<Long> add(@Valid @RequestBody DictCreateDTO dict) {
        Long id = dictService.createDict(dict);
        return success(id);
    }

    /**
     * 修改字典
     */
    @Operation(summary = "修改字典")
    @PutMapping("/{id}")
    @RequiresPermissions("system:dict:edit")
    @Log(title = "字典管理", businessType = "UPDATE")
    public CommonResult<Void> edit(
            @Parameter(description = "字典ID") @PathVariable Long id,
            @Valid @RequestBody DictUpdateDTO dict) {
        dictService.updateDict(id, dict);
        return success();
    }

    /**
     * 删除字典
     */
    @Operation(summary = "删除字典")
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:dict:remove")
    @Log(title = "字典管理", businessType = "DELETE")
    public CommonResult<Void> remove(@Parameter(description = "字典ID") @PathVariable Long id) {
        dictService.deleteDict(id);
        return success();
    }

    /**
     * 批量删除字典
     */
    @Operation(summary = "批量删除字典")
    @DeleteMapping("/batch")
    @RequiresPermissions("system:dict:remove")
    @Log(title = "字典管理", businessType = "DELETE")
    public CommonResult<Void> batchRemove(@Parameter(description = "字典ID列表") @RequestBody List<Long> ids) {
        dictService.deleteDicts(ids);
        return success();
    }

    /**
     * 刷新字典缓存
     */
    @Operation(summary = "刷新字典缓存")
    @PostMapping("/refreshCache")
    @RequiresPermissions("system:dict:refresh")
    @Log(title = "字典管理", businessType = "CLEAN")
    public CommonResult<Void> refreshCache() {
        dictService.refreshCache();
        return success();
    }
} 
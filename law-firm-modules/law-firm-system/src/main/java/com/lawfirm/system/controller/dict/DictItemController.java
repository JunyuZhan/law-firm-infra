package com.lawfirm.system.controller.dict;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.common.security.annotation.RequiresPermissions;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.model.system.dto.dict.DictItemCreateDTO;
import com.lawfirm.model.system.dto.dict.DictItemUpdateDTO;
import com.lawfirm.model.system.entity.dict.SysDictItem;
import com.lawfirm.model.system.service.DictItemService;
import com.lawfirm.model.system.vo.dict.DictItemVO;
import com.lawfirm.system.constant.SystemConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统字典项控制器
 */
@Tag(name = "字典项管理", description = "管理字典的项数据，包括字典项的增删改查等操作")
@RestController("dictItemController")
@RequestMapping(SystemConstants.API_DICT_ITEM_PREFIX)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DictItemController extends BaseController {

    private final DictItemService dictItemService;

    /**
     * 获取字典项列表
     */
    @Operation(
        summary = "获取字典项列表",
        description = "分页获取字典项列表，支持按字典ID、标签、值等条件筛选"
    )
    @GetMapping("/list")
    @RequiresPermissions("system:dict:list")
    public CommonResult<Page<DictItemVO>> list(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "字典ID") @RequestParam(required = false) Long dictId,
            @Parameter(description = "字典项标签") @RequestParam(required = false) String label,
            @Parameter(description = "字典项值") @RequestParam(required = false) String value) {
        
        Page<SysDictItem> page = new Page<>(pageNum, pageSize);
        SysDictItem dictItem = new SysDictItem();
        dictItem.setDictId(dictId);
        dictItem.setLabel(label);
        dictItem.setValue(value);
        
        Page<DictItemVO> result = dictItemService.pageDictItems(page, dictItem);
        return success(result);
    }

    /**
     * 获取字典项详情
     */
    @Operation(
        summary = "获取字典项详情",
        description = "根据字典项ID获取字典项的详细信息"
    )
    @GetMapping("/{id}")
    @RequiresPermissions("system:dict:query")
    public CommonResult<DictItemVO> getInfo(@Parameter(description = "字典项ID") @PathVariable Long id) {
        DictItemVO dictItem = dictItemService.getDictItemById(id);
        return success(dictItem);
    }

    /**
     * 根据字典ID获取字典项列表
     */
    @Operation(
        summary = "根据字典ID获取字典项列表",
        description = "获取指定字典下的所有字典项列表，用于下拉选择等场景"
    )
    @GetMapping("/dict/{dictId}")
    public CommonResult<List<DictItemVO>> getDictItemsByDictId(@Parameter(description = "字典ID") @PathVariable Long dictId) {
        List<DictItemVO> dictItems = dictItemService.listDictItemsByDictId(dictId);
        return success(dictItems);
    }

    /**
     * 新增字典项
     */
    @Operation(
        summary = "新增字典项",
        description = "创建新的字典项数据，包括标签、值、排序等信息"
    )
    @PostMapping
    @RequiresPermissions("system:dict:add")
    @Log(title = "字典项管理", businessType = "INSERT")
    public CommonResult<Long> add(@Valid @RequestBody DictItemCreateDTO dictItem) {
        Long id = dictItemService.createDictItem(dictItem);
        return success(id);
    }

    /**
     * 修改字典项
     */
    @Operation(
        summary = "修改字典项",
        description = "更新已存在的字典项信息，包括标签、值、排序等"
    )
    @PutMapping("/{id}")
    @RequiresPermissions("system:dict:edit")
    @Log(title = "字典项管理", businessType = "UPDATE")
    public CommonResult<Void> edit(
            @Parameter(description = "字典项ID") @PathVariable Long id,
            @Valid @RequestBody DictItemUpdateDTO dictItem) {
        dictItemService.updateDictItem(id, dictItem);
        return success();
    }

    /**
     * 删除字典项
     */
    @Operation(
        summary = "删除字典项",
        description = "根据ID删除单个字典项"
    )
    @DeleteMapping("/{id}")
    @RequiresPermissions("system:dict:remove")
    @Log(title = "字典项管理", businessType = "DELETE")
    public CommonResult<Void> remove(@Parameter(description = "字典项ID") @PathVariable Long id) {
        dictItemService.deleteDictItem(id);
        return success();
    }

    /**
     * 批量删除字典项
     */
    @Operation(
        summary = "批量删除字典项",
        description = "批量删除多个字典项"
    )
    @DeleteMapping("/batch")
    @RequiresPermissions("system:dict:remove")
    @Log(title = "字典项管理", businessType = "DELETE")
    public CommonResult<Void> batchRemove(@Parameter(description = "字典项ID列表") @RequestBody List<Long> ids) {
        dictItemService.deleteDictItems(ids);
        return success();
    }
} 
package com.lawfirm.admin.controller.system;

import com.lawfirm.common.core.model.ApiResult;
import com.lawfirm.system.model.dto.SysDictDTO;
import com.lawfirm.system.model.vo.SysDictVO;
import com.lawfirm.system.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 系统字典Controller
 */
@Api(tags = "系统字典")
@RestController
@RequestMapping("/system/dict")
@RequiredArgsConstructor
public class SysDictController {

    private final SysDictService dictService;

    @ApiOperation("创建字典")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> createDict(@Valid @RequestBody SysDictDTO dto) {
        dictService.createDict(dto);
        return ApiResult.ok();
    }

    @ApiOperation("更新字典")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> updateDict(@PathVariable Long id, @Valid @RequestBody SysDictDTO dto) {
        dictService.updateDict(id, dto);
        return ApiResult.ok();
    }

    @ApiOperation("删除字典")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> deleteDict(@PathVariable Long id) {
        dictService.deleteDict(id);
        return ApiResult.ok();
    }

    @ApiOperation("获取字典详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<SysDictVO> getDict(@PathVariable Long id) {
        return ApiResult.ok(dictService.getDict(id));
    }

    @ApiOperation("分页查询字典")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<List<SysDictVO>> pageDicts(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(required = false) String dictType,
                                               @RequestParam(required = false) String dictName) {
        return ApiResult.ok(dictService.pageDicts(pageNum, pageSize, dictType, dictName));
    }

    @ApiOperation("根据字典类型获取字典数据")
    @GetMapping("/type/{type}")
    public ApiResult<List<Map<String, Object>>> getDictDataByType(@PathVariable String type) {
        return ApiResult.ok(dictService.getDictDataByType(type));
    }

    @ApiOperation("刷新字典缓存")
    @PostMapping("/refresh")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> refreshDictCache() {
        dictService.refreshDictCache();
        return ApiResult.ok();
    }

    @ApiOperation("检查字典类型是否存在")
    @GetMapping("/check/{type}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Boolean> checkDictTypeExists(@PathVariable String type) {
        return ApiResult.ok(dictService.checkDictTypeExists(type));
    }

    @ApiOperation("导出字典数据")
    @GetMapping("/export")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportDict() {
        dictService.exportDict();
    }
} 
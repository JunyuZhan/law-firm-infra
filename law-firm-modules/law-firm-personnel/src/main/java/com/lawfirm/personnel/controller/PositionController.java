package com.lawfirm.personnel.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.organization.entity.base.Position;
import com.lawfirm.model.organization.service.PositionService;
import com.lawfirm.personnel.converter.PositionConverter;
import com.lawfirm.personnel.constant.PersonnelConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 职位管理控制器
 * 处理职位的创建、更新、删除等操作
 */
@Slf4j
@Tag(name = "职位管理", description = "提供职位管理相关接口")
@RestController("positionController")
@RequestMapping(PersonnelConstants.API_POSITION_PREFIX)
@Validated
public class PositionController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private PositionConverter positionConverter;

    /**
     * 获取职位列表
     */
    @Operation(summary = "获取职位列表", description = "获取所有职位信息")
    @GetMapping
    public CommonResult<List<Position>> listPositions() {
        List<Position> positions = positionService.getAllPositions();
        return CommonResult.success(positions);
    }

    /**
     * 获取单个职位详情
     */
    @Operation(summary = "获取职位详情", description = "根据ID获取职位详细信息")
    @GetMapping("/{id}")
    public CommonResult<Position> getPosition(
            @Parameter(description = "职位ID") @PathVariable Long id) {
        Position position = positionService.getPositionById(id);
        return position != null ? CommonResult.success(position) : CommonResult.error("职位不存在");
    }

    /**
     * 创建职位
     */
    @Operation(summary = "创建职位", description = "创建新的职位")
    @PostMapping
    public CommonResult<Long> createPosition(
            @Parameter(description = "职位信息") @RequestBody @Valid Position position) {
        Long positionId = positionService.createPosition(position);
        return CommonResult.success(positionId);
    }

    /**
     * 更新职位信息
     */
    @Operation(summary = "更新职位", description = "更新职位信息")
    @PutMapping("/{id}")
    public CommonResult<Boolean> updatePosition(
            @Parameter(description = "职位ID") @PathVariable Long id,
            @Parameter(description = "职位信息") @RequestBody @Valid Position position) {
        position.setId(id);
        boolean success = positionService.updatePosition(position);
        return CommonResult.success(success);
    }

    /**
     * 删除职位
     */
    @Operation(summary = "删除职位", description = "根据ID删除职位")
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> deletePosition(
            @Parameter(description = "职位ID") @PathVariable Long id) {
        boolean success = positionService.deletePosition(id);
        return CommonResult.success(success);
    }

    /**
     * 根据部门ID获取职位列表
     */
    @Operation(summary = "获取部门职位列表", description = "根据部门ID获取该部门下的所有职位")
    @GetMapping("/by-department/{departmentId}")
    public CommonResult<List<Position>> getPositionsByDepartment(
            @Parameter(description = "部门ID") @PathVariable Long departmentId) {
        List<Position> positions = positionService.getPositionsByDepartmentId(departmentId);
        return CommonResult.success(positions);
    }
} 
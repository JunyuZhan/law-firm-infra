package com.lawfirm.personnel.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.organization.entity.base.Position;
import com.lawfirm.model.organization.service.PositionService;
import com.lawfirm.personnel.converter.PositionConverter;
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
@RestController
@RequestMapping("/api/personnel/positions")
@Validated
public class PositionController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private PositionConverter positionConverter;

    /**
     * 获取职位列表
     *
     * @return 职位列表
     */
    @GetMapping
    public CommonResult<List<Position>> listPositions() {
        List<Position> positions = positionService.getAllPositions();
        return CommonResult.success(positions);
    }

    /**
     * 获取单个职位详情
     *
     * @param id 职位ID
     * @return 职位详情
     */
    @GetMapping("/{id}")
    public CommonResult<Position> getPosition(@PathVariable Long id) {
        Position position = positionService.getPositionById(id);
        return position != null ? CommonResult.success(position) : CommonResult.error("职位不存在");
    }

    /**
     * 创建职位
     *
     * @param position 职位信息
     * @return 创建的职位ID
     */
    @PostMapping
    public CommonResult<Long> createPosition(@RequestBody @Valid Position position) {
        Long positionId = positionService.createPosition(position);
        return CommonResult.success(positionId);
    }

    /**
     * 更新职位信息
     *
     * @param id 职位ID
     * @param position 职位信息
     * @return 是否成功
     */
    @PutMapping("/{id}")
    public CommonResult<Boolean> updatePosition(@PathVariable Long id, @RequestBody @Valid Position position) {
        position.setId(id);
        boolean success = positionService.updatePosition(position);
        return CommonResult.success(success);
    }

    /**
     * 删除职位
     *
     * @param id 职位ID
     * @return 是否成功
     */
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> deletePosition(@PathVariable Long id) {
        boolean success = positionService.deletePosition(id);
        return CommonResult.success(success);
    }

    /**
     * 根据部门ID获取职位列表
     *
     * @param departmentId 部门ID
     * @return 职位列表
     */
    @GetMapping("/by-department/{departmentId}")
    public CommonResult<List<Position>> getPositionsByDepartment(@PathVariable Long departmentId) {
        List<Position> positions = positionService.getPositionsByDepartmentId(departmentId);
        return CommonResult.success(positions);
    }
} 
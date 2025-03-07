package com.lawfirm.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.position.PositionCreateDTO;
import com.lawfirm.model.auth.dto.position.PositionQueryDTO;
import com.lawfirm.model.auth.dto.position.PositionUpdateDTO;
import com.lawfirm.model.auth.service.PositionService;
import com.lawfirm.model.auth.vo.PositionVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职位管理控制器
 */
@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    /**
     * 创建职位
     */
    @PostMapping
    @PreAuthorize("hasAuthority('system:position:add')")
    public CommonResult<Long> createPosition(@RequestBody @Valid PositionCreateDTO createDTO) {
        Long positionId = positionService.createPosition(createDTO);
        return CommonResult.success(positionId);
    }

    /**
     * 更新职位
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('system:position:edit')")
    public CommonResult<Void> updatePosition(@PathVariable Long id, @RequestBody @Valid PositionUpdateDTO updateDTO) {
        positionService.updatePosition(id, updateDTO);
        return CommonResult.success();
    }

    /**
     * 删除职位
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:position:remove')")
    public CommonResult<Void> deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return CommonResult.success();
    }

    /**
     * 批量删除职位
     */
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('system:position:remove')")
    public CommonResult<Void> batchDeletePositions(@RequestBody List<Long> ids) {
        positionService.deletePositions(ids);
        return CommonResult.success();
    }

    /**
     * 获取职位详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:position:list')")
    public CommonResult<PositionVO> getPosition(@PathVariable Long id) {
        PositionVO positionVO = positionService.getPositionById(id);
        return CommonResult.success(positionVO);
    }

    /**
     * 分页查询职位
     */
    @GetMapping
    @PreAuthorize("hasAuthority('system:position:list')")
    public CommonResult<Page<PositionVO>> pagePositions(PositionQueryDTO queryDTO) {
        Page<PositionVO> page = positionService.pagePositions(queryDTO);
        return CommonResult.success(page);
    }

    /**
     * 获取所有职位
     */
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('system:position:list')")
    public CommonResult<List<PositionVO>> listAllPositions() {
        List<PositionVO> positions = positionService.listAllPositions();
        return CommonResult.success(positions);
    }
}

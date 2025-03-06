package com.lawfirm.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.model.Result;
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
    public Result<Long> createPosition(@RequestBody @Valid PositionCreateDTO createDTO) {
        Long positionId = positionService.createPosition(createDTO);
        return Result.ok().data(positionId);
    }

    /**
     * 更新职位
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('system:position:edit')")
    public Result<Void> updatePosition(@PathVariable Long id, @RequestBody @Valid PositionUpdateDTO updateDTO) {
        positionService.updatePosition(id, updateDTO);
        return Result.ok();
    }

    /**
     * 删除职位
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:position:remove')")
    public Result<Void> deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return Result.ok();
    }

    /**
     * 批量删除职位
     */
    @DeleteMapping("/batch")
    @PreAuthorize("hasAuthority('system:position:remove')")
    public Result<Void> batchDeletePositions(@RequestBody List<Long> ids) {
        positionService.deletePositions(ids);
        return Result.ok();
    }

    /**
     * 获取职位详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:position:list')")
    public Result<PositionVO> getPosition(@PathVariable Long id) {
        PositionVO positionVO = positionService.getPositionById(id);
        return Result.ok().data(positionVO);
    }

    /**
     * 分页查询职位
     */
    @GetMapping
    @PreAuthorize("hasAuthority('system:position:list')")
    public Result<Page<PositionVO>> pagePositions(PositionQueryDTO queryDTO) {
        Page<PositionVO> page = positionService.pagePositions(queryDTO);
        return Result.ok().data(page);
    }

    /**
     * 获取所有职位
     */
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('system:position:list')")
    public Result<List<PositionVO>> listAllPositions() {
        List<PositionVO> positions = positionService.listAllPositions();
        return Result.ok().data(positions);
    }
}

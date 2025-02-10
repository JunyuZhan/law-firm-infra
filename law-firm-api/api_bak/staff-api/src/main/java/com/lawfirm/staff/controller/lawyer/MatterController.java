package com.lawfirm.staff.controller.lawyer;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.annotation.OperationLog;
import com.lawfirm.staff.annotation.OperationType;
import com.lawfirm.cases.model.dto.MatterDTO;
import com.lawfirm.cases.model.query.MatterQuery;
import com.lawfirm.cases.model.vo.MatterVO;
import com.lawfirm.staff.client.MatterClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "案件管理")
@RestController
@RequestMapping("/lawyer/matter")
@RequiredArgsConstructor
public class MatterController {

    private final MatterClient matterClient;

    @Operation(summary = "分页查询案件")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('lawyer:matter:query')")
    @OperationLog(value = "分页查询案件", type = OperationType.QUERY)
    public Result<PageResult<MatterVO>> page(MatterQuery query) {
        return matterClient.page(query);
    }

    @Operation(summary = "添加案件")
    @PostMapping
    @PreAuthorize("hasAuthority('lawyer:matter:add')")
    @OperationLog(value = "添加案件", type = OperationType.CREATE)
    public Result<Void> add(@RequestBody @Validated MatterDTO matterDTO) {
        return matterClient.add(matterDTO);
    }

    @Operation(summary = "修改案件")
    @PutMapping
    @PreAuthorize("hasAuthority('lawyer:matter:update')")
    @OperationLog(value = "修改案件", type = OperationType.UPDATE)
    public Result<Void> update(@RequestBody @Validated MatterDTO matterDTO) {
        return matterClient.update(matterDTO);
    }

    @Operation(summary = "删除案件")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('lawyer:matter:delete')")
    @OperationLog(value = "删除案件", type = OperationType.DELETE)
    public Result<Void> delete(@PathVariable Long id) {
        return matterClient.delete(id);
    }

    @Operation(summary = "获取案件详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('lawyer:matter:query')")
    @OperationLog(value = "获取案件详情", type = OperationType.QUERY)
    public Result<MatterVO> get(@PathVariable Long id) {
        return matterClient.get(id);
    }

    @Operation(summary = "获取我的案件")
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('lawyer:matter:query')")
    @OperationLog(value = "获取我的案件", type = OperationType.QUERY)
    public Result<List<MatterVO>> getMyMatters() {
        return matterClient.getMyMatters();
    }

    @Operation(summary = "获取部门案件")
    @GetMapping("/department")
    @PreAuthorize("hasAuthority('lawyer:matter:query')")
    @OperationLog(value = "获取部门案件", type = OperationType.QUERY)
    public Result<List<MatterVO>> getDepartmentMatters() {
        return matterClient.getDepartmentMatters();
    }
} 
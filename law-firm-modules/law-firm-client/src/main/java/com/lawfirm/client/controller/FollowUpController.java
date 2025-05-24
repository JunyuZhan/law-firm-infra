package com.lawfirm.client.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.client.service.impl.FollowUpServiceImpl;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.client.entity.follow.ClientFollowUp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.lawfirm.client.constant.ClientConstants;
import org.springframework.security.access.prepost.PreAuthorize;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 客户跟进控制器
 */
@Tag(name = "客户跟进记录管理")
@Slf4j
@RestController("followUpController")
@RequiredArgsConstructor
@RequestMapping(ClientConstants.API_FOLLOW_UP_PREFIX)
public class FollowUpController extends BaseController {

    private final FollowUpServiceImpl followUpService;

    /**
     * 获取客户的跟进记录列表
     */
    @Operation(summary = "获取客户的跟进记录列表")
    @GetMapping("/list/{clientId}")
    @PreAuthorize(CLIENT_VIEW)
    public CommonResult<List<ClientFollowUp>> list(@PathVariable("clientId") Long clientId) {
        return success(followUpService.listByClientId(clientId));
    }

    /**
     * 分页查询跟进记录
     */
    @Operation(summary = "分页查询跟进记录")
    @GetMapping("/page")
    @PreAuthorize(CLIENT_VIEW)
    public CommonResult<IPage<ClientFollowUp>> page(
            @RequestParam(value = "clientId", required = false) Long clientId,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<ClientFollowUp> page = new Page<>(pageNum, pageSize);
        IPage<ClientFollowUp> pageResult = followUpService.pageFollowUpRecords(clientId, status, page);
        return success(pageResult);
    }

    /**
     * 获取跟进记录详情
     */
    @Operation(summary = "获取跟进记录详情")
    @GetMapping("/{id}")
    @PreAuthorize(CLIENT_VIEW)
    public CommonResult<ClientFollowUp> getById(@PathVariable("id") Long id) {
        return success(followUpService.getFollowUp(id));
    }

    /**
     * 新增跟进记录
     */
    @Operation(summary = "新增跟进记录")
    @PostMapping
    @PreAuthorize(CLIENT_EDIT)
    public CommonResult<Long> add(@Validated @RequestBody ClientFollowUp followUp) {
        return success(followUpService.addFollowUp(followUp));
    }

    /**
     * 修改跟进记录
     */
    @Operation(summary = "修改跟进记录")
    @PutMapping
    @PreAuthorize(CLIENT_EDIT)
    public CommonResult<Boolean> update(@Validated @RequestBody ClientFollowUp followUp) {
        followUpService.updateFollowUp(followUp);
        return success(true);
    }

    /**
     * 删除跟进记录
     */
    @Operation(summary = "删除跟进记录")
    @DeleteMapping("/{id}")
    @PreAuthorize(CLIENT_DELETE)
    public CommonResult<Boolean> remove(@PathVariable("id") Long id) {
        followUpService.deleteFollowUp(id);
        return success(true);
    }

    /**
     * 完成跟进任务
     */
    @Operation(summary = "完成跟进任务")
    @PostMapping("/complete/{id}")
    @PreAuthorize(CLIENT_EDIT)
    public CommonResult<Boolean> complete(
            @PathVariable("id") Long id,
            @RequestParam("content") String content,
            @RequestParam("result") String result) {
        // 先完成当前跟进任务
        followUpService.completeFollowUp(id, result);
        
        // 如果有内容，可以考虑创建下一次跟进任务
        // 这里简化处理，实际可能需要更复杂的逻辑
        return success(true);
    }

    /**
     * 取消跟进任务
     */
    @Operation(summary = "取消跟进任务")
    @PostMapping("/cancel/{id}")
    @PreAuthorize(CLIENT_EDIT)
    public CommonResult<Boolean> cancel(
            @PathVariable("id") Long id,
            @RequestParam("reason") String reason) {
        followUpService.cancelFollowUp(id, reason);
        return success(true);
    }

    /**
     * 获取待处理的跟进任务
     */
    @Operation(summary = "获取待处理的跟进任务")
    @GetMapping("/pending")
    @PreAuthorize(CLIENT_VIEW)
    public CommonResult<List<ClientFollowUp>> getPendingFollowUps(
            @RequestParam(value = "startTime", required = false) LocalDateTime startTime,
            @RequestParam(value = "endTime", required = false) LocalDateTime endTime) {
        return success(followUpService.listPendingFollowUps(startTime, endTime));
    }
}

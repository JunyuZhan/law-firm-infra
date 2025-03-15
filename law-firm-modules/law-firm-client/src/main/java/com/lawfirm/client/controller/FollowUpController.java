package com.lawfirm.client.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.client.service.impl.FollowUpServiceImpl;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.client.entity.follow.ClientFollowUp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 客户跟进控制器
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/client/follow")
public class FollowUpController extends BaseController {

    private final FollowUpServiceImpl followUpService;

    /**
     * 获取客户的跟进记录列表
     *
     * @param clientId 客户ID
     * @return 跟进记录列表
     */
    @GetMapping("/list/{clientId}")
    public CommonResult<List<ClientFollowUp>> list(@PathVariable("clientId") Long clientId) {
        return success(followUpService.listByClientId(clientId));
    }

    /**
     * 分页查询跟进记录
     *
     * @param clientId 客户ID
     * @param status 状态
     * @return 分页数据
     */
    @GetMapping("/page")
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
     *
     * @param id 跟进记录ID
     * @return 跟进记录详情
     */
    @GetMapping("/{id}")
    public CommonResult<ClientFollowUp> getById(@PathVariable("id") Long id) {
        return success(followUpService.getFollowUp(id));
    }

    /**
     * 新增跟进记录
     *
     * @param followUp 跟进记录
     * @return 操作结果
     */
    @PostMapping
    public CommonResult<Long> add(@Validated @RequestBody ClientFollowUp followUp) {
        return success(followUpService.addFollowUp(followUp));
    }

    /**
     * 修改跟进记录
     *
     * @param followUp 跟进记录
     * @return 操作结果
     */
    @PutMapping
    public CommonResult<Boolean> update(@Validated @RequestBody ClientFollowUp followUp) {
        followUpService.updateFollowUp(followUp);
        return success(true);
    }

    /**
     * 删除跟进记录
     *
     * @param id 跟进记录ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> remove(@PathVariable("id") Long id) {
        followUpService.deleteFollowUp(id);
        return success(true);
    }

    /**
     * 完成跟进任务
     *
     * @param id 跟进记录ID
     * @param content 跟进内容
     * @param result 跟进结果
     * @return 操作结果
     */
    @PostMapping("/complete/{id}")
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
     *
     * @param id 跟进记录ID
     * @param reason 取消原因
     * @return 操作结果
     */
    @PostMapping("/cancel/{id}")
    public CommonResult<Boolean> cancel(
            @PathVariable("id") Long id,
            @RequestParam("reason") String reason) {
        followUpService.cancelFollowUp(id, reason);
        return success(true);
    }

    /**
     * 获取待处理的跟进任务
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 待处理的跟进任务列表
     */
    @GetMapping("/pending")
    public CommonResult<List<ClientFollowUp>> getPendingFollowUps(
            @RequestParam(value = "startTime", required = false) LocalDateTime startTime,
            @RequestParam(value = "endTime", required = false) LocalDateTime endTime) {
        return success(followUpService.listPendingFollowUps(startTime, endTime));
    }
}

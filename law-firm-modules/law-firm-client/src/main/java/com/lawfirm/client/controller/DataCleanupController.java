package com.lawfirm.client.controller;

import com.lawfirm.client.constant.ClientConstants;
import com.lawfirm.client.task.DataCleanupTask;
import com.lawfirm.common.core.api.CommonResult;
import org.springframework.security.access.prepost.PreAuthorize;
import com.lawfirm.model.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户数据清理控制器
 */
@Tag(name = "客户数据清理管理")
@Slf4j
@RestController("dataCleanupController")
@RequiredArgsConstructor
@RequestMapping(ClientConstants.API_PREFIX + "/cleanup")
public class DataCleanupController extends BaseController {

    private final DataCleanupTask dataCleanupTask;

    /**
     * 手动触发临时客户清理
     *
     * @return 清理结果
     */
    @Operation(summary = "清理临时客户数据")
    @PostMapping("/temporary-clients")
    @PreAuthorize("hasAuthority('" + CLIENT_CLEANUP_EXECUTE + "') or hasRole('ROLE_ADMIN')")
    public CommonResult<String> cleanupTemporaryClients() {
        try {
            dataCleanupTask.cleanupTemporaryClients();
            return success("临时客户清理任务已执行完成");
        } catch (Exception e) {
            log.error("手动触发临时客户清理失败", e);
            return error("清理失败：" + e.getMessage());
        }
    }

    /**
     * 手动触发不活跃客户归档
     *
     * @return 归档结果
     */
    @Operation(summary = "归档不活跃客户")
    @PostMapping("/inactive-clients")
    @PreAuthorize("hasAuthority('" + CLIENT_CLEANUP_EXECUTE + "') or hasRole('ROLE_ADMIN')")
    public CommonResult<String> archiveInactiveClients() {
        try {
            dataCleanupTask.archiveInactiveClients();
            return success("不活跃客户归档任务已执行完成");
        } catch (Exception e) {
            log.error("手动触发不活跃客户归档失败", e);
            return error("归档失败：" + e.getMessage());
        }
    }

    /**
     * 手动触发已完成跟进记录清理
     *
     * @return 清理结果
     */
    @Operation(summary = "清理已完成跟进记录")
    @PostMapping("/followups")
    @PreAuthorize("hasAuthority('" + CLIENT_CLEANUP_EXECUTE + "') or hasRole('ROLE_ADMIN')")
    public CommonResult<String> cleanupCompletedFollowUps() {
        try {
            dataCleanupTask.cleanupCompletedFollowUps();
            return success("已完成跟进记录清理任务已执行完成");
        } catch (Exception e) {
            log.error("手动触发已完成跟进记录清理失败", e);
            return error("清理失败：" + e.getMessage());
        }
    }

    /**
     * 查询数据清理的统计信息
     *
     * @return 统计信息
     */
    @Operation(summary = "获取数据清理统计信息")
    @GetMapping("/statistics")
    @PreAuthorize("hasAuthority('" + CLIENT_CLEANUP_VIEW + "') or hasRole('ROLE_ADMIN')")
    public CommonResult<Map<String, Object>> getCleanupStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();
            statistics.put("temporaryClientCount", dataCleanupTask.getTemporaryClientCount());
            statistics.put("inactiveClientCount", dataCleanupTask.getInactiveClientCount());
            statistics.put("completedFollowUpCount", dataCleanupTask.getCompletedFollowUpCount());
            statistics.put("lastCleanupTime", dataCleanupTask.getLastCleanupTime());
            return success(statistics);
        } catch (Exception e) {
            log.error("获取数据清理统计信息失败", e);
            return error("获取统计信息失败：" + e.getMessage());
        }
    }
} 
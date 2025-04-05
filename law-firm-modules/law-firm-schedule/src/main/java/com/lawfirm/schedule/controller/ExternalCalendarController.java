package com.lawfirm.schedule.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.model.schedule.dto.CalendarSyncConfigDTO;
import com.lawfirm.model.schedule.dto.ExternalCalendarAccountDTO;
import com.lawfirm.model.schedule.dto.ExternalCalendarDTO;
import com.lawfirm.model.schedule.entity.ExternalCalendar;
import com.lawfirm.model.schedule.service.ExternalCalendarService;
import com.lawfirm.model.schedule.service.ExternalCalendarSyncService;
import com.lawfirm.model.schedule.vo.CalendarSyncHistoryVO;
import com.lawfirm.model.schedule.vo.ExternalCalendarAccountVO;
import com.lawfirm.model.schedule.vo.ExternalCalendarVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 外部日历控制器
 */
@Tag(name = "外部日历管理")
@RestController("externalCalendarController")
@RequestMapping("/schedule/external-calendar")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ExternalCalendarController {
    
    private final ExternalCalendarService externalCalendarService;
    private final ExternalCalendarSyncService externalCalendarSyncService;
    
    @Operation(summary = "创建外部日历")
    @PostMapping
    @PreAuthorize("hasAuthority('schedule:calendar:external')")
    public CommonResult<Long> createExternalCalendar(@Valid @RequestBody ExternalCalendarDTO calendarDTO) {
        log.info("创建外部日历：{}", calendarDTO.getName());
        Long id = externalCalendarService.createExternalCalendar(calendarDTO);
        return CommonResult.success(id, "创建外部日历成功");
    }
    
    @Operation(summary = "更新外部日历")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:calendar:external')")
    public CommonResult<Boolean> updateExternalCalendar(
            @Parameter(description = "外部日历ID") @PathVariable Long id,
            @Valid @RequestBody ExternalCalendarDTO calendarDTO) {
        log.info("更新外部日历：{}", id);
        boolean success = externalCalendarService.updateExternalCalendar(id, calendarDTO);
        return success ? CommonResult.success(true, "更新外部日历成功") : CommonResult.error("更新外部日历失败");
    }
    
    @Operation(summary = "获取外部日历详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:calendar:external')")
    public CommonResult<ExternalCalendarVO> getExternalCalendarDetail(@Parameter(description = "外部日历ID") @PathVariable Long id) {
        log.info("获取外部日历详情：{}", id);
        ExternalCalendarVO calendarVO = externalCalendarService.getExternalCalendarDetail(id);
        return CommonResult.success(calendarVO);
    }
    
    @Operation(summary = "删除外部日历")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('schedule:calendar:external')")
    public CommonResult<Boolean> deleteExternalCalendar(@Parameter(description = "外部日历ID") @PathVariable Long id) {
        log.info("删除外部日历：{}", id);
        boolean success = externalCalendarService.deleteExternalCalendar(id);
        return success ? CommonResult.success(true, "删除外部日历成功") : CommonResult.error("删除外部日历失败");
    }
    
    @Operation(summary = "获取用户的外部日历列表")
    @GetMapping("/list/user")
    @PreAuthorize("hasAuthority('schedule:calendar:external')")
    public CommonResult<List<ExternalCalendarVO>> listUserExternalCalendars(
            @Parameter(description = "用户ID") @RequestParam(required = false) Long userId) {
        log.info("获取用户的外部日历列表");
        List<ExternalCalendarVO> calendars = externalCalendarService.listUserExternalCalendars(userId);
        return CommonResult.success(calendars);
    }
    
    @Operation(summary = "分页查询外部日历")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('schedule:calendar:external')")
    public CommonResult<Page<ExternalCalendarVO>> pageExternalCalendars(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "名称关键字") @RequestParam(required = false) String keyword,
            @Parameter(description = "提供商类型") @RequestParam(required = false) Integer providerType,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status) {
        log.info("分页查询外部日历，页码：{}，每页大小：{}", pageNum, pageSize);
        Page<ExternalCalendar> page = new Page<>(pageNum, pageSize);
        Page<ExternalCalendarVO> result = externalCalendarService.page(page, keyword, providerType, status);
        return CommonResult.success(result);
    }
    
    @Operation(summary = "同步外部日历")
    @PostMapping("/{id}/sync")
    @PreAuthorize("hasAuthority('schedule:calendar:external')")
    public CommonResult<Integer> syncExternalCalendar(
            @Parameter(description = "外部日历ID") @PathVariable Long id,
            @Parameter(description = "开始时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        log.info("同步外部日历：{}，时间范围：{} - {}", id, startTime, endTime);
        int count = externalCalendarService.syncExternalCalendar(id, startTime, endTime);
        return CommonResult.success(count, "成功同步" + count + "条日程");
    }
    
    @Operation(summary = "添加外部日历授权")
    @PostMapping("/authorize")
    @PreAuthorize("hasAuthority('schedule:calendar:external')")
    public CommonResult<String> authorizeExternalCalendar(@Valid @RequestBody ExternalCalendarDTO calendarDTO) {
        log.info("添加外部日历授权：{}，提供商类型：{}", calendarDTO.getName(), calendarDTO.getProviderType());
        String authUrl = externalCalendarService.getAuthorizationUrl(calendarDTO);
        return CommonResult.success(authUrl);
    }
    
    @Operation(summary = "接收授权回调")
    @GetMapping("/callback")
    public CommonResult<Boolean> handleAuthCallback(
            @Parameter(description = "提供商类型") @RequestParam Integer providerType,
            @Parameter(description = "授权码") @RequestParam String code,
            @Parameter(description = "状态") @RequestParam String state) {
        log.info("接收外部日历授权回调，提供商类型：{}", providerType);
        boolean success = externalCalendarService.handleAuthCallback(providerType, code, state);
        return success ? CommonResult.success(true, "授权成功") : CommonResult.error("授权失败");
    }
    
    @Operation(summary = "更新外部日历状态")
    @PutMapping("/{id}/status/{status}")
    @PreAuthorize("hasAuthority('schedule:calendar:external')")
    public CommonResult<Boolean> updateExternalCalendarStatus(
            @Parameter(description = "外部日历ID") @PathVariable Long id,
            @Parameter(description = "状态") @PathVariable Integer status) {
        log.info("更新外部日历状态，ID：{}，状态：{}", id, status);
        boolean success = externalCalendarService.updateExternalCalendarStatus(id, status);
        return success ? CommonResult.success(true, "更新状态成功") : CommonResult.error("更新状态失败");
    }
    
    @Operation(summary = "导入iCalendar文件")
    @PostMapping("/import-ical")
    @PreAuthorize("hasAuthority('schedule:external-calendar:import')")
    public CommonResult<Integer> importICalendar(
            @Parameter(description = "iCal文件") @RequestParam("file") MultipartFile file,
            @Parameter(description = "是否覆盖已有事件") @RequestParam(defaultValue = "false") boolean override) {
        log.info("导入iCalendar文件：{}", file.getOriginalFilename());
        try {
            InputStream inputStream = file.getInputStream();
            Long userId = SecurityUtils.getUserId();
            int count = externalCalendarSyncService.importICalendar(userId, inputStream, override);
            return CommonResult.success(count, "成功导入" + count + "个日程");
        } catch (Exception e) {
            log.error("导入iCalendar文件失败", e);
            return CommonResult.error("导入失败：" + e.getMessage());
        }
    }
    
    @Operation(summary = "导出iCalendar文件")
    @GetMapping("/export-ical")
    @PreAuthorize("hasAuthority('schedule:external-calendar:export')")
    public ResponseEntity<InputStreamResource> exportICalendar(
            @Parameter(description = "开始时间过滤器") @RequestParam(required = false) String startTimeFilter,
            @Parameter(description = "结束时间过滤器") @RequestParam(required = false) String endTimeFilter) {
        log.info("导出iCalendar文件");
        try {
            Long userId = SecurityUtils.getUserId();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int count = externalCalendarSyncService.exportToICalendar(userId, outputStream, startTimeFilter, endTimeFilter);
            
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=calendar.ics")
                    .contentType(MediaType.parseMediaType("text/calendar"))
                    .body(resource);
        } catch (Exception e) {
            log.error("导出iCalendar文件失败", e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(summary = "获取已连接的日历账号")
    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('schedule:external-calendar:view')")
    public CommonResult<List<ExternalCalendarAccountVO>> getConnectedAccounts() {
        log.info("获取已连接的日历账号");
        Long userId = SecurityUtils.getUserId();
        List<ExternalCalendarAccountVO> accounts = externalCalendarSyncService.getConnectedAccounts(userId);
        return CommonResult.success(accounts);
    }
    
    @Operation(summary = "添加外部日历账号")
    @PostMapping("/accounts")
    @PreAuthorize("hasAuthority('schedule:external-calendar:manage')")
    public CommonResult<Boolean> addCalendarAccount(@Valid @RequestBody ExternalCalendarAccountDTO accountDTO) {
        log.info("添加外部日历账号：{}", accountDTO.getType());
        Long userId = SecurityUtils.getUserId();
        long expireTimeMillis = accountDTO.getExpireTime().toInstant(java.time.ZoneOffset.UTC).toEpochMilli();
        boolean success = externalCalendarSyncService.addCalendarAccount(
                userId, accountDTO.getType(), accountDTO.getAccessToken(), 
                accountDTO.getRefreshToken(), expireTimeMillis);
        return success ? CommonResult.success(true, "添加成功") : CommonResult.error("添加失败");
    }
    
    @Operation(summary = "删除外部日历账号")
    @DeleteMapping("/accounts/{type}")
    @PreAuthorize("hasAuthority('schedule:external-calendar:manage')")
    public CommonResult<Boolean> removeCalendarAccount(@Parameter(description = "账号类型") @PathVariable String type) {
        log.info("删除外部日历账号：{}", type);
        Long userId = SecurityUtils.getUserId();
        boolean success = externalCalendarSyncService.removeCalendarAccount(userId, type);
        return success ? CommonResult.success(true, "删除成功") : CommonResult.error("删除失败");
    }
    
    @Operation(summary = "设置同步配置")
    @PutMapping("/sync-config/{type}")
    @PreAuthorize("hasAuthority('schedule:external-calendar:manage')")
    public CommonResult<Boolean> setSyncConfig(
            @Parameter(description = "账号类型") @PathVariable String type,
            @Valid @RequestBody CalendarSyncConfigDTO configDTO) {
        log.info("设置同步配置：{}", type);
        Long userId = SecurityUtils.getUserId();
        List<Integer> syncCategories = configDTO.getSyncCategories().stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        boolean success = externalCalendarSyncService.setCalendarSyncConfig(
                userId, type, configDTO.getSyncDirection(), configDTO.getSyncInterval(), syncCategories);
        return success ? CommonResult.success(true, "设置成功") : CommonResult.error("设置失败");
    }
    
    @Operation(summary = "获取同步配置")
    @GetMapping("/sync-config/{type}")
    @PreAuthorize("hasAuthority('schedule:external-calendar:view')")
    public CommonResult<Object> getSyncConfig(@Parameter(description = "账号类型") @PathVariable String type) {
        log.info("获取同步配置：{}", type);
        Long userId = SecurityUtils.getUserId();
        Object config = externalCalendarSyncService.getCalendarSyncConfig(userId, type);
        return CommonResult.success(config);
    }
    
    @Operation(summary = "立即同步")
    @PostMapping("/sync-now/{type}")
    @PreAuthorize("hasAuthority('schedule:external-calendar:sync')")
    public CommonResult<Object> syncNow(@Parameter(description = "账号类型") @PathVariable String type) {
        log.info("立即同步：{}", type);
        Long userId = SecurityUtils.getUserId();
        Object result = externalCalendarSyncService.syncCalendarNow(userId, type);
        return CommonResult.success(result);
    }
    
    @Operation(summary = "获取同步历史")
    @GetMapping("/sync-history/{type}")
    @PreAuthorize("hasAuthority('schedule:external-calendar:view')")
    public CommonResult<Page<CalendarSyncHistoryVO>> getSyncHistory(
            @Parameter(description = "账号类型") @PathVariable String type,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("获取同步历史：{}", type);
        Long userId = SecurityUtils.getUserId();
        Object result = externalCalendarSyncService.getSyncHistory(userId, type, pageNum, pageSize);
        @SuppressWarnings("unchecked")
        Page<CalendarSyncHistoryVO> page = (Page<CalendarSyncHistoryVO>) result;
        return CommonResult.success(page);
    }
} 
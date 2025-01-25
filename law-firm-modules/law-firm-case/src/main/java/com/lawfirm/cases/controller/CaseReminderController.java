package com.lawfirm.cases.controller;

import com.lawfirm.model.cases.entity.CaseReminder;
import com.lawfirm.cases.service.CaseReminderService;
import com.lawfirm.common.core.domain.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件提醒Controller
 */
@Slf4j
@RestController
@RequestMapping("/case/reminder")
@Tag(name = "案件提醒")
@RequiredArgsConstructor
public class CaseReminderController {

    private final CaseReminderService reminderService;

    @Operation(summary = "创建案件提醒")
    @PostMapping
    public R<CaseReminder> createReminder(@RequestBody CaseReminder reminder) {
        log.info("Creating reminder: {}", reminder);
        return R.ok(reminderService.createReminder(reminder));
    }

    @Operation(summary = "更新案件提醒")
    @PutMapping
    public R<CaseReminder> updateReminder(@RequestBody CaseReminder reminder) {
        log.info("Updating reminder: {}", reminder);
        return R.ok(reminderService.updateReminder(reminder));
    }

    @Operation(summary = "删除案件提醒")
    @DeleteMapping("/{id}")
    public R<Void> deleteReminder(@PathVariable Long id) {
        log.info("Deleting reminder: {}", id);
        reminderService.deleteReminder(id);
        return R.ok();
    }

    @Operation(summary = "标记提醒为已完成")
    @PutMapping("/{id}/complete")
    public R<Void> completeReminder(@PathVariable Long id, @RequestParam String operator) {
        log.info("Completing reminder: {}, operator: {}", id, operator);
        reminderService.completeReminder(id, operator);
        return R.ok();
    }

    @Operation(summary = "获取案件的所有提醒")
    @GetMapping("/case/{caseId}")
    public R<List<CaseReminder>> getCaseReminders(@PathVariable Long caseId) {
        return R.ok(reminderService.getCaseReminders(caseId));
    }

    @Operation(summary = "获取律师的所有提醒")
    @GetMapping("/lawyer/{lawyer}")
    public R<List<CaseReminder>> getLawyerReminders(@PathVariable String lawyer) {
        return R.ok(reminderService.getLawyerReminders(lawyer));
    }

    @Operation(summary = "获取未完成的提醒")
    @GetMapping("/pending")
    public R<List<CaseReminder>> getPendingReminders() {
        return R.ok(reminderService.getPendingReminders());
    }

    @Operation(summary = "获取即将到期的提醒")
    @GetMapping("/upcoming")
    public R<List<CaseReminder>> getUpcomingReminders() {
        return R.ok(reminderService.getUpcomingReminders());
    }

    @Operation(summary = "获取已过期的提醒")
    @GetMapping("/overdue")
    public R<List<CaseReminder>> getOverdueReminders() {
        return R.ok(reminderService.getOverdueReminders());
    }
} 
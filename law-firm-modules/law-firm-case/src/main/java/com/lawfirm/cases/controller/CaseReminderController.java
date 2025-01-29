package com.lawfirm.cases.controller;

import com.lawfirm.cases.service.CaseReminderService;
import com.lawfirm.common.core.model.Result;
import com.lawfirm.model.cases.entity.CaseReminder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 案件提醒Controller
 */
@RestController
@RequestMapping("/case/reminder")
@RequiredArgsConstructor
public class CaseReminderController {

    private final CaseReminderService caseReminderService;

    @PostMapping("/create")
    public Result<Void> createReminder(@RequestBody CaseReminder reminder) {
        caseReminderService.createReminder(reminder);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result<Void> updateReminder(@RequestBody CaseReminder reminder) {
        caseReminderService.updateReminder(reminder);
        return Result.ok();
    }

    @PostMapping("/delete/{id}")
    public Result<Void> deleteReminder(@PathVariable Long id) {
        caseReminderService.deleteReminder(id);
        return Result.ok();
    }

    @PostMapping("/confirm/{id}")
    public Result<Void> confirmReminder(@PathVariable Long id, @RequestParam String remark) {
        caseReminderService.confirmReminder(id, remark);
        return Result.ok();
    }

    @PostMapping("/complete/{id}")
    public Result<Void> completeReminder(@PathVariable Long id, @RequestParam String remark) {
        caseReminderService.completeReminder(id, remark);
        return Result.ok();
    }

    @GetMapping("/list/case/{caseId}")
    public Result<List<CaseReminder>> listByCaseId(@PathVariable Long caseId) {
        return Result.ok(caseReminderService.listByCaseId(caseId));
    }

    @GetMapping("/list/lawyer/{lawyerId}")
    public Result<List<CaseReminder>> listByLawyerId(@PathVariable String lawyerId) {
        return Result.ok(caseReminderService.getLawyerReminders(lawyerId));
    }

    @GetMapping("/list/pending")
    public Result<List<CaseReminder>> listPendingReminders() {
        return Result.ok(caseReminderService.listPendingReminders());
    }

    @GetMapping("/list/upcoming")
    public Result<List<CaseReminder>> listUpcomingReminders() {
        return Result.ok(caseReminderService.getUpcomingReminders());
    }

    @GetMapping("/list/overdue")
    public Result<List<CaseReminder>> listOverdueReminders() {
        return Result.ok(caseReminderService.getOverdueReminders());
    }
} 
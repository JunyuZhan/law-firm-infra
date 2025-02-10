package com.lawfirm.schedule.controller;

import com.lawfirm.common.core.result.Result;
import com.lawfirm.schedule.entity.Schedule;
import com.lawfirm.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    
    private final ScheduleService scheduleService;
    
    @PostMapping
    public Result<Schedule> create(@RequestBody Schedule schedule) {
        return Result.ok(scheduleService.create(schedule));
    }
    
    @GetMapping("/{id}")
    public Result<Schedule> getById(@PathVariable Long id) {
        return Result.ok(scheduleService.getById(id));
    }
    
    @GetMapping
    public Result<List<Schedule>> list() {
        return Result.ok(scheduleService.list());
    }
    
    @PutMapping("/{id}")
    public Result<Schedule> update(@PathVariable Long id, @RequestBody Schedule schedule) {
        schedule.setId(id);
        return Result.ok(scheduleService.update(schedule));
    }
    
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.ok(scheduleService.removeById(id));
    }
} 
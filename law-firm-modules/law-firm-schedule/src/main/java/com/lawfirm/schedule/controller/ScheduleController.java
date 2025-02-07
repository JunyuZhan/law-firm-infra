package com.lawfirm.schedule.controller;

import com.lawfirm.common.core.result.R;
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
    public R<Schedule> create(@RequestBody Schedule schedule) {
        return R.ok(scheduleService.create(schedule));
    }
    
    @GetMapping("/{id}")
    public R<Schedule> getById(@PathVariable Long id) {
        return R.ok(scheduleService.getById(id));
    }
    
    @GetMapping
    public R<List<Schedule>> list() {
        return R.ok(scheduleService.list());
    }
    
    @PutMapping("/{id}")
    public R<Schedule> update(@PathVariable Long id, @RequestBody Schedule schedule) {
        schedule.setId(id);
        return R.ok(scheduleService.update(schedule));
    }
    
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable Long id) {
        return R.ok(scheduleService.removeById(id));
    }
} 
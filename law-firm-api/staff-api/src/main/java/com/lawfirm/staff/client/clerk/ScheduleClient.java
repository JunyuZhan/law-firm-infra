package com.lawfirm.staff.client;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.schedule.model.dto.ScheduleDTO;
import com.lawfirm.schedule.model.query.ScheduleQuery;
import com.lawfirm.schedule.model.vo.ScheduleVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "law-firm-schedule", contextId = "schedule", path = "/schedule")
public interface ScheduleClient {
    
    @GetMapping("/page")
    Result<PageResult<ScheduleVO>> page(@SpringQueryMap ScheduleQuery query);
    
    @PostMapping
    Result<Void> add(@RequestBody ScheduleDTO scheduleDTO);
    
    @PutMapping
    Result<Void> update(@RequestBody ScheduleDTO scheduleDTO);
    
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    Result<ScheduleVO> get(@PathVariable("id") Long id);
    
    @GetMapping("/my")
    Result<PageResult<ScheduleVO>> getMySchedules(@SpringQueryMap ScheduleQuery query);
    
    @GetMapping("/export")
    void export(@SpringQueryMap ScheduleQuery query);
} 
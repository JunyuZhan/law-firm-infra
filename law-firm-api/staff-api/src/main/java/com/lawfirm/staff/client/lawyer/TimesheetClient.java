package com.lawfirm.staff.client;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.timesheet.model.dto.TimesheetDTO;
import com.lawfirm.timesheet.model.query.TimesheetQuery;
import com.lawfirm.timesheet.model.vo.TimesheetVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 工时管理服务Feign客户端
 */
@FeignClient(name = "law-firm-timesheet", contextId = "timesheet", path = "/timesheet")
public interface TimesheetClient {
    
    @GetMapping("/page")
    Result<PageResult<TimesheetVO>> page(@SpringQueryMap TimesheetQuery query);
    
    @PostMapping
    Result<Void> add(@RequestBody TimesheetDTO timesheetDTO);
    
    @PutMapping
    Result<Void> update(@RequestBody TimesheetDTO timesheetDTO);
    
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    Result<TimesheetVO> get(@PathVariable("id") Long id);
    
    @GetMapping("/my")
    Result<PageResult<TimesheetVO>> getMyTimesheets(@SpringQueryMap TimesheetQuery query);
    
    @GetMapping("/department")
    Result<PageResult<TimesheetVO>> getDepartmentTimesheets(@SpringQueryMap TimesheetQuery query);
    
    @GetMapping("/statistics")
    Result<TimesheetVO> getStatistics(@SpringQueryMap TimesheetQuery query);
    
    @GetMapping("/export")
    void export(@SpringQueryMap TimesheetQuery query);
} 
package com.lawfirm.core.workflow.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.workflow.service.ProcessService;
import com.lawfirm.core.workflow.vo.ProcessInstanceVO;
import com.lawfirm.model.workflow.dto.process.ProcessCreateDTO;
import com.lawfirm.model.workflow.dto.process.ProcessQueryDTO;
import com.lawfirm.model.workflow.vo.ProcessVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程控制�? * 提供流程管理的RESTful API
 * 
 * @author JunyuZhan
 */
@Slf4j
@RestController
@RequestMapping("/api/workflow/processes")
@Tag(name = "流程管理", description = "流程管理相关接口")
@RequiredArgsConstructor
public class ProcessController {
    
    private final ProcessService processService;
    
    /**
     * 创建流程
     */
    @PostMapping
    @Operation(summary = "创建流程")
    public ResponseEntity<Long> createProcess(
            @Parameter(description = "流程创建数据") @Valid @RequestBody ProcessCreateDTO createDTO) {
        log.info("创建流程请求: {}", createDTO);
        
        Long processId = processService.createProcess(createDTO);
        
        log.info("流程创建成功: {}", processId);
        return ResponseEntity.ok(processId);
    }
    
    /**
     * 获取流程详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取流程详情")
    public ResponseEntity<ProcessVO> getProcess(
            @Parameter(description = "流程ID") @PathVariable Long id) {
        log.info("获取流程详情请求: {}", id);
        
        ProcessVO processVO = processService.getProcess(id);
        
        log.info("获取流程详情成功: {}", processVO);
        return ResponseEntity.ok(processVO);
    }
    
    /**
     * 查询流程列表
     */
    @GetMapping
    @Operation(summary = "查询流程列表")
    public ResponseEntity<List<ProcessVO>> listProcesses(
            @Parameter(description = "查询条件") ProcessQueryDTO queryDTO) {
        log.info("查询流程列表请求: {}", queryDTO);
        
        List<ProcessVO> processList = processService.listProcesses(queryDTO);
        
        log.info("查询流程列表成功，共 {} 条记�?, processList.size());
        return ResponseEntity.ok(processList);
    }
    
    /**
     * 分页查询流程
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询流程")
    public ResponseEntity<Page<ProcessVO>> getProcessPage(
            @Parameter(description = "查询条件") ProcessQueryDTO queryDTO,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") int current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        log.info("分页查询流程请求: {}, page={}, size={}", queryDTO, current, size);
        
        Page<ProcessVO> page = processService.getProcessList(queryDTO, current, size);
        
        log.info("分页查询流程成功，共 {} 条记�?, page.getTotal());
        return ResponseEntity.ok(page);
    }
    
    /**
     * 启动流程
     */
    @PostMapping("/{id}/start")
    @Operation(summary = "启动流程")
    public ResponseEntity<Void> startProcess(
            @Parameter(description = "流程ID") @PathVariable Long id) {
        log.info("启动流程请求: {}", id);
        
        processService.startProcess(id);
        
        log.info("流程启动成功: {}", id);
        return ResponseEntity.ok().build();
    }
    
    /**
     * 取消流程
     */
    @PostMapping("/{id}/cancel")
    @Operation(summary = "取消流程")
    public ResponseEntity<Void> cancelProcess(
            @Parameter(description = "流程ID") @PathVariable Long id) {
        log.info("取消流程请求: {}", id);
        
        processService.cancelProcess(id);
        
        log.info("流程取消成功: {}", id);
        return ResponseEntity.ok().build();
    }
    
    /**
     * 挂起流程
     */
    @PostMapping("/{id}/suspend")
    @Operation(summary = "挂起流程")
    public ResponseEntity<Void> suspendProcess(
            @Parameter(description = "流程ID") @PathVariable Long id) {
        log.info("挂起流程请求: {}", id);
        
        processService.suspendProcess(id);
        
        log.info("流程挂起成功: {}", id);
        return ResponseEntity.ok().build();
    }
    
    /**
     * 恢复流程
     */
    @PostMapping("/{id}/resume")
    @Operation(summary = "恢复流程")
    public ResponseEntity<Void> resumeProcess(
            @Parameter(description = "流程ID") @PathVariable Long id) {
        log.info("恢复流程请求: {}", id);
        
        processService.resumeProcess(id);
        
        log.info("流程恢复成功: {}", id);
        return ResponseEntity.ok().build();
    }
    
    /**
     * 删除流程
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除流程")
    public ResponseEntity<Void> deleteProcess(
            @Parameter(description = "流程ID") @PathVariable Long id) {
        log.info("删除流程请求: {}", id);
        
        processService.deleteProcess(id);
        
        log.info("流程删除成功: {}", id);
        return ResponseEntity.ok().build();
    }
} 

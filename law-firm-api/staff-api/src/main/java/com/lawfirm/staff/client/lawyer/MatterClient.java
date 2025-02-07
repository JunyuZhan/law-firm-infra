package com.lawfirm.staff.client;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.cases.model.dto.MatterDTO;
import com.lawfirm.cases.model.query.MatterQuery;
import com.lawfirm.cases.model.vo.MatterVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 案件服务Feign客户端
 */
@FeignClient(name = "law-firm-case", contextId = "matter", path = "/matter")
public interface MatterClient {
    
    @GetMapping("/page")
    Result<PageResult<MatterVO>> page(@SpringQueryMap MatterQuery query);
    
    @PostMapping
    Result<Void> add(@RequestBody MatterDTO matterDTO);
    
    @PutMapping
    Result<Void> update(@RequestBody MatterDTO matterDTO);
    
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    Result<MatterVO> get(@PathVariable("id") Long id);
    
    @GetMapping("/my")
    Result<List<MatterVO>> getMyMatters();
    
    @GetMapping("/department")
    Result<List<MatterVO>> getDepartmentMatters();
} 
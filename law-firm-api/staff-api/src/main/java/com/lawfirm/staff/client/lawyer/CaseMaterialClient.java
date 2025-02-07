package com.lawfirm.staff.client;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.cases.model.dto.CaseMaterialDTO;
import com.lawfirm.cases.model.query.CaseMaterialQuery;
import com.lawfirm.cases.model.vo.CaseMaterialVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 案件材料服务Feign客户端
 */
@FeignClient(name = "law-firm-case", contextId = "case-material", path = "/case-material")
public interface CaseMaterialClient {
    
    @GetMapping("/page")
    Result<PageResult<CaseMaterialVO>> page(@SpringQueryMap CaseMaterialQuery query);
    
    @PostMapping
    Result<Void> add(@RequestBody CaseMaterialDTO caseMaterialDTO);
    
    @PutMapping
    Result<Void> update(@RequestBody CaseMaterialDTO caseMaterialDTO);
    
    @DeleteMapping("/{id}")
    Result<Void> delete(@PathVariable("id") Long id);
    
    @GetMapping("/{id}")
    Result<CaseMaterialVO> get(@PathVariable("id") Long id);
    
    @PostMapping("/upload")
    Result<String> upload(@RequestPart("file") MultipartFile file, @RequestParam("caseId") Long caseId);
    
    @GetMapping("/download/{id}")
    void download(@PathVariable("id") Long id);
    
    @GetMapping("/case/{caseId}")
    Result<List<CaseMaterialVO>> getByCaseId(@PathVariable("caseId") Long caseId);
} 
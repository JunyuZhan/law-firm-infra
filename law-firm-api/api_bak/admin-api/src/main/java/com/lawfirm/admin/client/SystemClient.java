package com.lawfirm.admin.client;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.admin.model.request.system.dept.CreateDeptRequest;
import com.lawfirm.admin.model.request.system.dept.UpdateDeptRequest;
import com.lawfirm.admin.model.request.system.post.CreatePostRequest;
import com.lawfirm.admin.model.request.system.post.PostPageRequest;
import com.lawfirm.admin.model.request.system.post.UpdatePostRequest;
import com.lawfirm.admin.model.request.system.template.CreateTemplateRequest;
import com.lawfirm.admin.model.request.system.template.TemplatePageRequest;
import com.lawfirm.admin.model.request.system.template.UpdateTemplateRequest;
import com.lawfirm.admin.model.response.system.dept.DeptResponse;
import com.lawfirm.admin.model.response.system.post.PostResponse;
import com.lawfirm.admin.model.response.system.monitor.ServerInfoResponse;
import com.lawfirm.admin.model.response.system.template.TemplateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统服务Feign客户端
 */
@FeignClient(name = "law-firm-system", contextId = "system", path = "/system")
public interface SystemClient {
    
    // 部门相关接口
    @PostMapping("/dept")
    Result<Void> createDept(@RequestBody CreateDeptRequest request);
    
    @PutMapping("/dept/{id}")
    Result<Void> updateDept(@PathVariable("id") Long id, @RequestBody UpdateDeptRequest request);
    
    @DeleteMapping("/dept/{id}")
    Result<Void> deleteDept(@PathVariable("id") Long id);
    
    @GetMapping("/dept/{id}")
    Result<DeptResponse> getDept(@PathVariable("id") Long id);
    
    @GetMapping("/dept/tree")
    Result<List<DeptResponse>> getDeptTree();
    
    // 岗位相关接口
    @PostMapping("/post")
    Result<Void> createPost(@RequestBody CreatePostRequest request);
    
    @PutMapping("/post/{id}")
    Result<Void> updatePost(@PathVariable("id") Long id, @RequestBody UpdatePostRequest request);
    
    @DeleteMapping("/post/{id}")
    Result<Void> deletePost(@PathVariable("id") Long id);
    
    @GetMapping("/post/{id}")
    Result<PostResponse> getPost(@PathVariable("id") Long id);
    
    @GetMapping("/post/page")
    Result<PageResult<PostResponse>> pagePost(@SpringQueryMap PostPageRequest request);
    
    // 监控相关接口
    @GetMapping("/monitor/server")
    Result<ServerInfoResponse> getServerInfo();
    
    @GetMapping("/monitor/cache")
    Result<List<String>> getCacheInfo();
    
    // 模板相关接口
    @PostMapping("/template")
    Result<Void> createTemplate(@RequestBody CreateTemplateRequest request);
    
    @PutMapping("/template/{id}")
    Result<Void> updateTemplate(@PathVariable("id") Long id, @RequestBody UpdateTemplateRequest request);
    
    @DeleteMapping("/template/{id}")
    Result<Void> deleteTemplate(@PathVariable("id") Long id);
    
    @GetMapping("/template/{id}")
    Result<TemplateResponse> getTemplate(@PathVariable("id") Long id);
    
    @GetMapping("/template/page")
    Result<PageResult<TemplateResponse>> pageTemplate(@SpringQueryMap TemplatePageRequest request);
    
    @PostMapping("/template/{id}/generate")
    Result<String> generateDocument(@PathVariable("id") Long id, @RequestBody Object data);
} 
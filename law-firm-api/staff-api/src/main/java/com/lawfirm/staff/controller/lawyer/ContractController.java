package com.lawfirm.staff.controller.lawyer;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.common.log.enums.OperationType;
import com.lawfirm.staff.client.finance.ContractClient;
import com.lawfirm.staff.model.request.lawyer.contract.ContractCreateRequest;
import com.lawfirm.staff.model.request.lawyer.contract.ContractPageRequest;
import com.lawfirm.staff.model.request.lawyer.contract.ContractUpdateRequest;
import com.lawfirm.staff.model.response.lawyer.contract.ContractResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "合同管理", description = "合同相关接口")
@RestController
@RequestMapping("/lawyer/contract")
@RequiredArgsConstructor
public class ContractController {

    private final ContractClient contractClient;

    @Operation(summary = "分页查询合同", description = "根据条件分页查询合同列表")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "查询成功"),
        @ApiResponse(responseCode = "401", description = "未授权"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping("/page")
    @PreAuthorize("hasAnyAuthority('lawyer:contract:query', 'clerk:contract:view', 'finance:contract:view')")
    @OperationLog(value = "分页查询合同", type = OperationType.QUERY)
    public Result<PageResult<ContractResponse>> page(
            @Parameter(description = "分页查询参数") ContractPageRequest request) {
        return contractClient.page(request);
    }

    @Operation(summary = "创建合同", description = "创建新的合同")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "创建成功"),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "401", description = "未授权"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('contract:create')")
    @OperationLog(value = "创建合同", type = OperationType.CREATE)
    public Result<ContractResponse> create(
            @Parameter(description = "合同创建参数", required = true) 
            @RequestBody @Validated ContractCreateRequest request) {
        return contractClient.create(request);
    }

    @Operation(summary = "上传合同附件", description = "上传合同相关的附件文件")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "上传成功"),
        @ApiResponse(responseCode = "400", description = "文件格式错误"),
        @ApiResponse(responseCode = "401", description = "未授权"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('contract:upload')")
    @OperationLog(value = "上传合同附件", type = OperationType.CREATE)
    public Result<ContractResponse> upload(
            @Parameter(description = "合同附件文件", required = true) 
            @RequestParam("file") MultipartFile file) {
        return contractClient.upload(file);
    }

    @Operation(summary = "下载合同", description = "下载指定ID的合同文件")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "下载成功"),
        @ApiResponse(responseCode = "404", description = "合同不存在"),
        @ApiResponse(responseCode = "401", description = "未授权"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyAuthority('contract:download', 'clerk:contract:read', 'finance:contract:read')")
    @OperationLog(value = "下载合同", type = OperationType.EXPORT)
    public void download(
            @Parameter(description = "合同ID", required = true) 
            @PathVariable Long id) {
        contractClient.download(id);
    }

    @Operation(summary = "预览合同", description = "预览指定ID的合同内容")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "预览成功"),
        @ApiResponse(responseCode = "404", description = "合同不存在"),
        @ApiResponse(responseCode = "401", description = "未授权"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping("/preview/{id}")
    @PreAuthorize("hasAnyAuthority('contract:preview', 'clerk:contract:read', 'finance:contract:read')")
    @OperationLog(value = "预览合同", type = OperationType.QUERY)
    public Result<String> preview(
            @Parameter(description = "合同ID", required = true) 
            @PathVariable Long id) {
        return contractClient.preview(id);
    }

    @Operation(summary = "删除合同", description = "删除指定ID的合同")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "删除成功"),
        @ApiResponse(responseCode = "404", description = "合同不存在"),
        @ApiResponse(responseCode = "401", description = "未授权"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('contract:delete')")
    @OperationLog(value = "删除合同", type = OperationType.DELETE)
    public Result<Void> delete(
            @Parameter(description = "合同ID", required = true) 
            @PathVariable Long id) {
        return contractClient.delete(id);
    }

    @Operation(summary = "获取合同详情", description = "获取指定ID的合同详细信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "获取成功"),
        @ApiResponse(responseCode = "404", description = "合同不存在"),
        @ApiResponse(responseCode = "401", description = "未授权"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('contract:query', 'clerk:contract:read', 'finance:contract:read')")
    @OperationLog(value = "获取合同详情", type = OperationType.QUERY)
    public Result<ContractResponse> get(
            @Parameter(description = "合同ID", required = true) 
            @PathVariable Long id) {
        return contractClient.get(id);
    }

    @Operation(summary = "修改合同", description = "修改指定合同的信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "修改成功"),
        @ApiResponse(responseCode = "400", description = "请求参数错误"),
        @ApiResponse(responseCode = "404", description = "合同不存在"),
        @ApiResponse(responseCode = "401", description = "未授权"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @PutMapping
    @PreAuthorize("hasAuthority('contract:update')")
    @OperationLog(value = "修改合同", type = OperationType.UPDATE)
    public Result<Void> update(
            @Parameter(description = "合同修改参数", required = true) 
            @RequestBody @Validated ContractUpdateRequest request) {
        return contractClient.update(request);
    }

    @Operation(summary = "签署合同", description = "签署指定ID的合同")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "签署成功"),
        @ApiResponse(responseCode = "404", description = "合同不存在"),
        @ApiResponse(responseCode = "401", description = "未授权"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @PutMapping("/sign/{id}")
    @PreAuthorize("hasAuthority('contract:sign')")
    @OperationLog(value = "签署合同", type = OperationType.UPDATE)
    public Result<Void> sign(
            @Parameter(description = "合同ID", required = true) 
            @PathVariable Long id) {
        return contractClient.sign(id);
    }

    @Operation(summary = "作废合同", description = "作废指定ID的合同")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "作废成功"),
        @ApiResponse(responseCode = "404", description = "合同不存在"),
        @ApiResponse(responseCode = "401", description = "未授权"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @PutMapping("/void/{id}")
    @PreAuthorize("hasAuthority('contract:void')")
    @OperationLog(value = "作废合同", type = OperationType.UPDATE)
    public Result<Void> voidContract(
            @Parameter(description = "合同ID", required = true) 
            @PathVariable Long id) {
        return contractClient.voidContract(id);
    }

    @Operation(summary = "导出合同", description = "导出合同列表")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "导出成功"),
        @ApiResponse(responseCode = "401", description = "未授权"),
        @ApiResponse(responseCode = "403", description = "无权限访问")
    })
    @GetMapping("/export")
    @PreAuthorize("hasAnyAuthority('contract:export', 'clerk:contract:read', 'finance:contract:read')")
    @OperationLog(value = "导出合同", type = OperationType.EXPORT)
    public void export(
            @Parameter(description = "导出查询参数") 
            ContractPageRequest request) {
        contractClient.export(request);
    }
} 
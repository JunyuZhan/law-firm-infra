package com.lawfirm.client.service;

import com.lawfirm.common.data.service.BaseService;
import com.lawfirm.model.client.entity.Client;
import com.lawfirm.model.client.query.ClientQuery;
import com.lawfirm.model.client.vo.ClientVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Tag(name = "客户管理服务", description = "提供客户的基本操作和查询统计功能")
@Validated
public interface ClientService extends BaseService<Client, ClientVO> {
    
    @Operation(summary = "根据查询条件查询客户", description = "根据查询条件查询客户列表")
    List<ClientVO> findByQuery(@Valid ClientQuery query);
    
    @Operation(summary = "启用客户", description = "将客户状态改为启用")
    void enableClient(
            @Parameter(description = "客户ID") @NotNull(message = "客户ID不能为空") Long id,
            @Parameter(description = "操作人") @NotBlank(message = "操作人不能为空") String operator);
    
    @Operation(summary = "批量启用客户", description = "批量将客户状态改为启用")
    void enableClients(
            @Parameter(description = "客户ID列表") @NotEmpty(message = "客户ID列表不能为空") List<Long> ids,
            @Parameter(description = "操作人") @NotBlank(message = "操作人不能为空") String operator);
    
    @Operation(summary = "禁用客户", description = "将客户状态改为禁用")
    void disableClient(
            @Parameter(description = "客户ID") @NotNull(message = "客户ID不能为空") Long id,
            @Parameter(description = "操作人") @NotBlank(message = "操作人不能为空") String operator);
    
    @Operation(summary = "批量禁用客户", description = "批量将客户状态改为禁用")
    void disableClients(
            @Parameter(description = "客户ID列表") @NotEmpty(message = "客户ID列表不能为空") List<Long> ids,
            @Parameter(description = "操作人") @NotBlank(message = "操作人不能为空") String operator);
    
    @Operation(summary = "按类型统计客户", description = "统计各个类型的客户数量")
    Map<String, Long> countByType();
    
    @Operation(summary = "按状态统计客户", description = "统计各个状态的客户数量")
    Map<String, Long> countByStatus();
    
    @Operation(summary = "查询个人客户", description = "查询所有个人客户")
    List<ClientVO> findPersonalClients(@Valid ClientQuery query);
    
    @Operation(summary = "查询企业客户", description = "查询所有企业客户")
    List<ClientVO> findEnterpriseClients(@Valid ClientQuery query);
    
    @Operation(summary = "按名称模糊查询", description = "根据客户名称进行模糊查询")
    List<ClientVO> findByNameLike(
            @Parameter(description = "客户名称") @NotBlank(message = "客户名称不能为空") String name);
    
    @Operation(summary = "检查客户编号是否存在", description = "检查指定的客户编号是否已存在")
    boolean checkClientNumberExists(
            @Parameter(description = "客户编号") @NotBlank(message = "客户编号不能为空") String clientNumber);
    
    @Operation(summary = "检查证件号码是否存在", description = "检查指定的证件号码是否已存在")
    boolean checkIdNumberExists(
            @Parameter(description = "证件号码") @NotBlank(message = "证件号码不能为空") String idNumber);
            
    @Operation(summary = "批量创建客户", description = "批量创建客户信息")
    List<ClientVO> batchCreate(
            @Parameter(description = "客户列表") @NotEmpty(message = "客户列表不能为空") List<Client> clients);
            
    @Operation(summary = "批量更新客户", description = "批量更新客户信息")
    List<ClientVO> batchUpdate(
            @Parameter(description = "客户列表") @NotEmpty(message = "客户列表不能为空") List<Client> clients);
} 
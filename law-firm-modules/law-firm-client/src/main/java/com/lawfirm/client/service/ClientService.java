package com.lawfirm.client.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.data.service.BaseService;
import com.lawfirm.model.client.entity.Client;
import com.lawfirm.model.client.dto.ClientDTO;
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

/**
 * 客户服务接口
 */
@Tag(name = "客户管理服务", description = "提供客户的基本操作和查询统计功能")
@Validated
public interface ClientService extends BaseService<Client, ClientVO> {

    /**
     * 创建客户
     */
    ClientVO create(@Valid ClientDTO dto);

    /**
     * 更新客户
     */
    ClientVO update(@Valid ClientDTO dto);

    /**
     * 批量创建客户
     */
    List<ClientVO> batchCreate(@Valid List<ClientDTO> dtos);

    /**
     * 批量更新客户
     */
    List<ClientVO> batchUpdate(@Valid List<ClientDTO> dtos);

    /**
     * 根据查询条件查询客户
     */
    List<ClientVO> findByQuery(ClientQuery query);

    /**
     * 启用客户
     */
    void enableClient(@NotNull Long id, @NotBlank String operator);

    /**
     * 批量启用客户
     */
    void enableClients(@NotEmpty List<Long> ids, @NotBlank String operator);

    /**
     * 禁用客户
     */
    void disableClient(@NotNull Long id, @NotBlank String operator);

    /**
     * 批量禁用客户
     */
    void disableClients(@NotEmpty List<Long> ids, @NotBlank String operator);

    /**
     * 查询个人客户
     */
    List<ClientVO> findPersonalClients(ClientQuery query);

    /**
     * 查询企业客户
     */
    List<ClientVO> findEnterpriseClients(ClientQuery query);

    /**
     * 根据名称模糊查询
     */
    List<ClientVO> findByNameLike(String name);

    /**
     * 检查客户编号是否存在
     */
    boolean checkClientNumberExists(String clientNumber);

    /**
     * 检查证件号码是否存在
     */
    boolean checkIdNumberExists(String idNumber);

    /**
     * 按类型统计
     */
    Map<String, Long> countByType();

    /**
     * 按状态统计
     */
    Map<String, Long> countByStatus();
}
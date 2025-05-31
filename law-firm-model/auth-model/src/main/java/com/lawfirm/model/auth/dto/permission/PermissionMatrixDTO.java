package com.lawfirm.model.auth.dto.permission;

import com.lawfirm.model.auth.enums.ModuleTypeEnum;
import com.lawfirm.model.auth.enums.OperationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * 权限矩阵请求DTO
 * 用于批量权限检查请求
 *
 * @author System
 */
@Data
@Schema(description = "权限矩阵请求参数")
public class PermissionMatrixDTO {

    @Schema(description = "模块类型")
    @NotNull(message = "模块类型不能为空")
    private ModuleTypeEnum moduleType;

    @Schema(description = "操作类型")
    @NotNull(message = "操作类型不能为空")
    private OperationTypeEnum operationType;

    @Schema(description = "请求标识，用于结果映射")
    private String requestKey;

    public PermissionMatrixDTO() {}

    public PermissionMatrixDTO(ModuleTypeEnum moduleType, OperationTypeEnum operationType) {
        this.moduleType = moduleType;
        this.operationType = operationType;
        this.requestKey = moduleType.getCode() + ":" + operationType.getCode();
    }

    public PermissionMatrixDTO(ModuleTypeEnum moduleType, OperationTypeEnum operationType, String requestKey) {
        this.moduleType = moduleType;
        this.operationType = operationType;
        this.requestKey = requestKey;
    }

    /**
     * 生成默认的请求标识
     */
    public String generateRequestKey() {
        if (requestKey == null && moduleType != null && operationType != null) {
            requestKey = moduleType.getCode() + ":" + operationType.getCode();
        }
        return requestKey;
    }
}
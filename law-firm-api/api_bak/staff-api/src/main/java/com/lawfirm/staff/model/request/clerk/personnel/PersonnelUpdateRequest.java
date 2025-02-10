package com.lawfirm.staff.model.request.clerk.personnel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "修改人员请求")
public class PersonnelUpdateRequest {

    @NotNull(message = "ID不能为空")
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "工号")
    private String employeeNo;

    @Schema(description = "部门ID")
    private Long departmentId;

    @Schema(description = "职位ID")
    private Long positionId;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "入职日期")
    private String entryDate;

    @Schema(description = "备注")
    private String remark;
} 
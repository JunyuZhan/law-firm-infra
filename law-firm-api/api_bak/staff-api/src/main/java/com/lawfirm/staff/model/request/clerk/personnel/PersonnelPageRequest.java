package com.lawfirm.staff.model.request.clerk.personnel;

import com.lawfirm.common.data.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "人员分页查询请求")
public class PersonnelPageRequest extends PageQuery {

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "工号")
    private String employeeNo;

    @Schema(description = "部门ID")
    private Long departmentId;

    @Schema(description = "职位ID")
    private Long positionId;

    @Schema(description = "状态（1：在职，2：离职）")
    private Integer status;
} 
package com.lawfirm.model.organization.query;

import com.lawfirm.model.base.query.PageQuery;
import com.lawfirm.model.organization.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrganizationQuery extends PageQuery {

    private Long lawFirmId;  // 律所ID
    private Long branchId;   // 分支机构ID
    private Long parentId;   // 上级部门ID

    private String name;     // 名称
    private String code;     // 编号

    private OrgTypeEnum orgType;           // 组织类型
    private DepartmentTypeEnum deptType;   // 部门类型
    private PositionTypeEnum positionType; // 职位类型

    private String manager;  // 负责人

    private Boolean enabled; // 是否启用
} 
package com.lawfirm.model.organization.vo;

import com.lawfirm.model.organization.enums.*;
import lombok.Data;

import java.util.List;

@Data
public class OrganizationTreeVO {

    private Long id;
    private Long parentId;
    private String name;
    private String code;
    private OrgTypeEnum orgType;
    private String manager;
    private Boolean enabled;

    // 组织类型特定属性
    private DepartmentTypeEnum deptType;   // 部门类型
    private PositionTypeEnum positionType; // 职位类型
    private Integer level;                 // 职级
    private String duty;                   // 职责说明

    // 联系信息
    private String phone;
    private String email;
    private String address;

    // 树形结构
    private List<OrganizationTreeVO> children;

    // 扩展信息
    private String description;
    private String remark;

    @Data
    public static class LawFirmInfo {
        private Long id;
        private String name;
        private String licenseNumber;
        private String legalRepresentative;
    }

    @Data
    public static class BranchInfo {
        private Long id;
        private String name;
        private String branchCode;
        private String licenseNumber;
    }

    @Data
    public static class DepartmentInfo {
        private Long id;
        private String name;
        private String departmentCode;
        private DepartmentTypeEnum departmentType;
    }

    @Data
    public static class PositionInfo {
        private Long id;
        private String name;
        private String positionCode;
        private PositionTypeEnum positionType;
        private Integer level;
    }
} 
package com.lawfirm.admin.model.response.system.dept;

import com.lawfirm.model.base.response.ApiResponse;
import lombok.Data;

@Data
public class SysDeptResponse {
    private Long id;
    private String name;
    private Long parentId;
    private Integer sort;
    private String leader;
    private String phone;
    private String email;
    private Integer status;
} 
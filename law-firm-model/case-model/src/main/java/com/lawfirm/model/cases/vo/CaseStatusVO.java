package com.lawfirm.model.cases.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseStatusVO {
    private String code;
    private String name;
    private String description;
} 
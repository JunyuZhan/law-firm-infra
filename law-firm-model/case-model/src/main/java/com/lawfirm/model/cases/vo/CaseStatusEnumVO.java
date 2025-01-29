package com.lawfirm.model.cases.vo;

import com.lawfirm.model.cases.enums.CaseStatusEnum;
import lombok.Data;

@Data
public class CaseStatusEnumVO {
    private String code;
    private String name;
    private String description;
    
    public CaseStatusEnumVO() {
    }
    
    public CaseStatusEnumVO(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public static CaseStatusEnumVO from(CaseStatusEnum statusEnum) {
        CaseStatusEnumVO vo = new CaseStatusEnumVO();
        vo.setCode(String.valueOf(statusEnum.ordinal()));
        vo.setName(statusEnum.toString());
        vo.setDescription(statusEnum.toString());
        return vo;
    }
} 
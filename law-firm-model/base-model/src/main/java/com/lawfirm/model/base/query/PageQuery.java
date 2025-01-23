package com.lawfirm.model.base.query;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageQuery implements Serializable {

    @Min(value = 1, message = "页码最小为1")
    private Integer pageNum = 1;

    @Min(value = 1, message = "每页条数最小为1")
    @Max(value = 1000, message = "每页条数最大为1000")
    private Integer pageSize = 10;

    private String sortField;

    private String sortOrder = "asc";

    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }
} 
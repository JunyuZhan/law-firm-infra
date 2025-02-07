package com.lawfirm.common.core.model.page;

import java.io.Serializable;
import lombok.Data;

@Data
public class BasePageRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
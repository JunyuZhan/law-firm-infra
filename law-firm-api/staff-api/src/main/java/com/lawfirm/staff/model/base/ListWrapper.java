package com.lawfirm.staff.model.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * List包装类
 */
@Data
@Schema(description = "List包装类")
public class ListWrapper<T> {
    
    @Schema(description = "数据列表")
    private List<T> list;
    
    public ListWrapper() {
    }
    
    public ListWrapper(List<T> list) {
        this.list = list;
    }
    
    public static <T> ListWrapper<T> of(List<T> list) {
        return new ListWrapper<>(list);
    }
} 
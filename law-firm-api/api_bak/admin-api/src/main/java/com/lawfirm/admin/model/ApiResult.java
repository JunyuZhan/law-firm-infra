// 路径: law-firm-api/admin-api/src/main/java/com/lawfirm/admin/model/ApiResult.java
package com.lawfirm.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "通用返回结果")
public class ApiResult<T> {

    @Schema(description = "状态码")
    private Integer code;

    @Schema(description = "返回信息")
    private String message;

    @Schema(description = "返回数据")
    private T data;

    public static <T> ApiResult<T> success() {
        ApiResult<T> result = new ApiResult<>();
        result.code = 200;
        result.message = "操作成功";
        return result;
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.code = 200;
        result.message = "操作成功";
        result.data = data;
        return result;
    }

    public static <T> ApiResult<T> error(String message) {
        ApiResult<T> result = new ApiResult<>();
        result.code = 500;
        result.message = message;
        return result;
    }

    public static <T> ApiResult<T> error(Integer code, String message) {
        ApiResult<T> result = new ApiResult<>();
        result.code = code;
        result.message = message;
        return result;
    }
import com.lawfirm.model.base.enums.BaseEnum  

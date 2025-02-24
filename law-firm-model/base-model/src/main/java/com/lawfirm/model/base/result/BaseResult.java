package com.lawfirm.model.base.result;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 基础结果对象
 */
@Data
@Accessors(chain = true)
public class BaseResult<T> {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 创建成功结果
     */
    public static <T> BaseResult<T> success() {
        return new BaseResult<T>()
            .setSuccess(true)
            .setCode("200")
            .setMessage("操作成功");
    }

    /**
     * 创建成功结果
     */
    public static <T> BaseResult<T> success(T data) {
        return new BaseResult<T>()
            .setSuccess(true)
            .setCode("200")
            .setMessage("操作成功")
            .setData(data);
    }

    /**
     * 创建失败结果
     */
    public static <T> BaseResult<T> error(String code, String message) {
        return new BaseResult<T>()
            .setSuccess(false)
            .setCode(code)
            .setMessage(message);
    }
}
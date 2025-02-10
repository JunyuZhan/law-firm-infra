package com.lawfirm.staff.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.core.exception.ServiceException;
import com.lawfirm.common.core.model.Result;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;

/**
 * Feign错误解码器
 */
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            // 读取响应体
            InputStream inputStream = response.body().asInputStream();
            Result<?> result = objectMapper.readValue(inputStream, Result.class);

            // 转换为业务异常
            return new ServiceException(result.getCode(), result.getMessage());
        } catch (IOException e) {
            log.error("Feign调用解析响应异常", e);
            return new ServiceException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), 
                "服务调用异常: " + methodKey);
        }
    }
} 
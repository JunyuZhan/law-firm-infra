package com.lawfirm.common.core.constant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultCodeTest {

    @Test
    void shouldHaveCorrectSuccessCode() {
        // given/when
        ResultCode success = ResultCode.SUCCESS;

        // then
        assertEquals(200, success.getCode());
        assertEquals("成功", success.getMessage());
    }

    @Test
    void shouldHaveCorrectErrorCode() {
        // given/when
        ResultCode error = ResultCode.ERROR;

        // then
        assertEquals(500, error.getCode());
        assertEquals("错误", error.getMessage());
    }

    @Test
    void shouldHaveCorrectClientErrorCodes() {
        // 客户端错误码应该在400-499范围内
        assertTrue(ResultCode.BAD_REQUEST.getCode() >= 400 && ResultCode.BAD_REQUEST.getCode() < 500);
        assertTrue(ResultCode.UNAUTHORIZED.getCode() >= 400 && ResultCode.UNAUTHORIZED.getCode() < 500);
        assertTrue(ResultCode.FORBIDDEN.getCode() >= 400 && ResultCode.FORBIDDEN.getCode() < 500);
        assertTrue(ResultCode.NOT_FOUND.getCode() >= 400 && ResultCode.NOT_FOUND.getCode() < 500);
        assertTrue(ResultCode.METHOD_NOT_ALLOWED.getCode() >= 400 && ResultCode.METHOD_NOT_ALLOWED.getCode() < 500);
    }

    @Test
    void shouldHaveCorrectServerErrorCodes() {
        // 服务端错误码应该在500-599范围内
        assertTrue(ResultCode.INTERNAL_ERROR.getCode() >= 500 && ResultCode.INTERNAL_ERROR.getCode() < 600);
        assertTrue(ResultCode.SERVICE_UNAVAILABLE.getCode() >= 500 && ResultCode.SERVICE_UNAVAILABLE.getCode() < 600);
        assertTrue(ResultCode.GATEWAY_ERROR.getCode() >= 500 && ResultCode.GATEWAY_ERROR.getCode() < 600);
    }

    @Test
    void shouldHaveCorrectFrameworkErrorCodes() {
        // 框架错误码应该在600-699范围内
        assertTrue(ResultCode.VALIDATION_ERROR.getCode() >= 600 && ResultCode.VALIDATION_ERROR.getCode() < 700);
        assertTrue(ResultCode.CONCURRENT_ERROR.getCode() >= 600 && ResultCode.CONCURRENT_ERROR.getCode() < 700);
        assertTrue(ResultCode.RATE_LIMIT_ERROR.getCode() >= 600 && ResultCode.RATE_LIMIT_ERROR.getCode() < 700);
    }

    @Test
    void shouldHaveNonNullValues() {
        // 所有枚举值都不应该为空
        for (ResultCode code : ResultCode.values()) {
            assertNotNull(code.getMessage(), "Message should not be null for " + code.name());
            assertTrue(code.getCode() > 0, "Code should be positive for " + code.name());
        }
    }

    @Test
    void shouldHaveUniqueErrorCodes() {
        // 所有错误码都应该是唯一的
        ResultCode[] codes = ResultCode.values();
        for (int i = 0; i < codes.length; i++) {
            for (int j = i + 1; j < codes.length; j++) {
                assertNotEquals(codes[i].getCode(), codes[j].getCode(),
                    "Duplicate code found: " + codes[i].name() + " and " + codes[j].name());
            }
        }
    }
} 
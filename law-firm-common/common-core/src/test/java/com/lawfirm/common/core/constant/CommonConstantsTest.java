package com.lawfirm.common.core.constant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonConstantsTest {

    @Test
    void shouldHaveCorrectStatusConstants() {
        assertEquals(0, CommonConstants.STATUS_NORMAL);
        assertEquals(1, CommonConstants.STATUS_DISABLE);
        assertEquals(2, CommonConstants.STATUS_DELETE);
    }

    @Test
    void shouldHaveCorrectBooleanConstants() {
        assertEquals(1, CommonConstants.TRUE);
        assertEquals(0, CommonConstants.FALSE);
    }

    @Test
    void shouldHaveCorrectPaginationConstants() {
        assertEquals(10, CommonConstants.DEFAULT_PAGE_SIZE);
        assertEquals(1, CommonConstants.DEFAULT_PAGE_NUMBER);
        assertEquals(100, CommonConstants.MAX_PAGE_SIZE);
    }

    @Test
    void shouldHaveCorrectSortConstants() {
        assertEquals("asc", CommonConstants.SORT_ASC);
        assertEquals("desc", CommonConstants.SORT_DESC);
    }

    @Test
    void shouldHaveCorrectDateFormatConstants() {
        assertEquals("yyyy-MM-dd", CommonConstants.DATE_FORMAT);
        assertEquals("HH:mm:ss", CommonConstants.TIME_FORMAT);
        assertEquals("yyyy-MM-dd HH:mm:ss", CommonConstants.DATETIME_FORMAT);
    }

    @Test
    void shouldHaveCorrectCharsetConstants() {
        assertEquals("UTF-8", CommonConstants.UTF8);
        assertEquals("GBK", CommonConstants.GBK);
    }

    @Test
    void shouldHaveCorrectSymbolConstants() {
        assertEquals(",", CommonConstants.COMMA);
        assertEquals(".", CommonConstants.DOT);
        assertEquals(":", CommonConstants.COLON);
        assertEquals(";", CommonConstants.SEMICOLON);
        assertEquals("_", CommonConstants.UNDERSCORE);
        assertEquals("/", CommonConstants.SLASH);
        assertEquals("\\", CommonConstants.BACKSLASH);
    }

    @Test
    void shouldHaveValidPaginationValues() {
        assertTrue(CommonConstants.DEFAULT_PAGE_SIZE > 0);
        assertTrue(CommonConstants.MAX_PAGE_SIZE >= CommonConstants.DEFAULT_PAGE_SIZE);
        assertTrue(CommonConstants.DEFAULT_PAGE_NUMBER > 0);
    }
} 
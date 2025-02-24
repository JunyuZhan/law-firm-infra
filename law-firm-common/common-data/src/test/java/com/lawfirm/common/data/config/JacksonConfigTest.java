package com.lawfirm.common.data.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JacksonConfigTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testDateTimeFormat() throws Exception {
        TestEntity entity = new TestEntity();
        entity.setDateTime(LocalDateTime.of(2024, 2, 15, 14, 30, 0));
        entity.setDate(LocalDate.of(2024, 2, 15));
        entity.setTime(LocalTime.of(14, 30, 0));

        String json = objectMapper.writeValueAsString(entity);
        TestEntity deserializedEntity = objectMapper.readValue(json, TestEntity.class);

        assertEquals(entity.getDateTime(), deserializedEntity.getDateTime());
        assertEquals(entity.getDate(), deserializedEntity.getDate());
        assertEquals(entity.getTime(), deserializedEntity.getTime());
    }

    static class TestEntity {
        private LocalDateTime dateTime;
        private LocalDate date;
        private LocalTime time;

        // Getters and setters
        public LocalDateTime getDateTime() { return dateTime; }
        public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }
        public LocalTime getTime() { return time; }
        public void setTime(LocalTime time) { this.time = time; }
    }
} 
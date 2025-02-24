package com.lawfirm.common.util.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.lawfirm.common.util.BaseUtilTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilsTest extends BaseUtilTest {

    @Test
    void toJsonString_ShouldConvertObjectToJson() {
        // 准备测试数据
        TestUser user = new TestUser("张三", 25);
        
        // 执行测试
        String json = JsonUtils.toJsonString(user);
        
        // 验证结果
        assertNotNull(json);
        assertTrue(json.contains("\"name\":\"张三\""));
        assertTrue(json.contains("\"age\":25"));
    }

    @Test
    void toJsonString_ShouldHandleNull() {
        String json = JsonUtils.toJsonString(null);
        assertNull(json);
    }

    @Test
    void parseObject_ShouldConvertJsonToObject() {
        // 准备测试数据
        String json = "{\"name\":\"张三\",\"age\":25}";
        
        // 执行测试
        TestUser user = JsonUtils.parseObject(json, TestUser.class);
        
        // 验证结果
        assertNotNull(user);
        assertEquals("张三", user.getName());
        assertEquals(25, user.getAge());
    }

    @Test
    void parseObject_ShouldHandleInvalidJson() {
        String json = "invalid json";
        TestUser user = JsonUtils.parseObject(json, TestUser.class);
        assertNull(user);
    }

    @Test
    void parseArray_ShouldConvertJsonToList() {
        // 准备测试数据
        String json = "[{\"name\":\"张三\",\"age\":25},{\"name\":\"李四\",\"age\":30}]";
        
        // 执行测试
        List<TestUser> users = JsonUtils.parseArray(json, TestUser.class);
        
        // 验证结果
        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("张三", users.get(0).getName());
        assertEquals(25, users.get(0).getAge());
        assertEquals("李四", users.get(1).getName());
        assertEquals(30, users.get(1).getAge());
    }

    @Test
    void parseArray_ShouldHandleInvalidJson() {
        String json = "invalid json";
        List<TestUser> users = JsonUtils.parseArray(json, TestUser.class);
        assertNull(users);
    }

    @Test
    void parseMap_ShouldConvertJsonToMap() {
        // 准备测试数据
        String json = "{\"name\":\"张三\",\"age\":25}";
        
        // 执行测试
        Map<String, Object> map = JsonUtils.parseMap(json);
        
        // 验证结果
        assertNotNull(map);
        assertEquals("张三", map.get("name"));
        assertEquals(25, ((Number)map.get("age")).intValue());
    }

    @Test
    void parseMap_ShouldHandleInvalidJson() {
        String json = "invalid json";
        Map<String, Object> map = JsonUtils.parseMap(json);
        assertNull(map);
    }

    @Test
    void toPrettyJsonString_ShouldFormatJson() {
        // 准备测试数据
        TestUser user = new TestUser("张三", 25);
        
        // 执行测试
        String json = JsonUtils.toPrettyJsonString(user);
        
        // 验证结果
        assertNotNull(json);
        assertTrue(json.contains("\n"));
        assertTrue(json.contains("  ")); // 包含缩进
    }

    @Test
    void fromJson_ShouldConvertJsonToGenericType() {
        // 准备测试数据
        String json = "[{\"name\":\"张三\",\"age\":25},{\"name\":\"李四\",\"age\":30}]";
        
        // 执行测试
        List<TestUser> users = JsonUtils.fromJson(json, new TypeReference<List<TestUser>>() {});
        
        // 验证结果
        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("张三", users.get(0).getName());
        assertEquals(25, users.get(0).getAge());
    }

    @Test
    void parseNode_ShouldConvertJsonToJsonNode() {
        // 准备测试数据
        String json = "{\"name\":\"张三\",\"age\":25}";
        
        // 执行测试
        JsonNode node = JsonUtils.parseNode(json);
        
        // 验证结果
        assertNotNull(node);
        assertEquals("张三", node.get("name").asText());
        assertEquals(25, node.get("age").asInt());
    }

    // 测试用的内部类
    static class TestUser {
        private String name;
        private int age;

        public TestUser() {
        }

        public TestUser(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
} 
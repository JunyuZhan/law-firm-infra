package com.lawfirm.system.controller;

import com.lawfirm.common.test.BaseIntegrationTest;
import com.lawfirm.common.test.TestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(classes = com.lawfirm.system.SystemTestApplication.class)
class MenuControllerTest extends BaseIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testGetMenusByRoleId() {
        Long testRoleId = 1L; // 可根据实际测试库调整
        webTestClient.get()
            .uri("/api/v1/menu/role/" + testRoleId + "/menus")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$[0].id").exists(); // 至少有一个菜单项
    }

    @Test
    void testGetMenusByRoleId_NotFound() {
        Long notExistRoleId = 99999L; // 假定此角色ID在测试库中不存在
        webTestClient.get()
            .uri("/api/v1/menu/role/" + notExistRoleId + "/menus")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$").isArray()
            .jsonPath("$.length()").isEqualTo(0); // 应返回空数组
    }

    @Test
    void testGetMenusByRoleId_InvalidParam() {
        webTestClient.get()
            .uri("/api/v1/menu/role/abc/menus") // 非法ID
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is4xxClientError();
    }

    @Test
    void testGetMenusByUserId() {
        Long testUserId = 1L; // 可根据实际测试库调整
        webTestClient.get()
            .uri("/api/v1/menu/user/" + testUserId + "/menus")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$[0].id").exists();
    }

    @Test
    void testGetMenusByUserId_NotFound() {
        Long notExistUserId = 99999L;
        webTestClient.get()
            .uri("/api/v1/menu/user/" + notExistUserId + "/menus")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$").isArray()
            .jsonPath("$.length()").isEqualTo(0);
    }

    @Test
    void testGetMenusByUserId_InvalidParam() {
        webTestClient.get()
            .uri("/api/v1/menu/user/abc/menus")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is4xxClientError();
    }
} 
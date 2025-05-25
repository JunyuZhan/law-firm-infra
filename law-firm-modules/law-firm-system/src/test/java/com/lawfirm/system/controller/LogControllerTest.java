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
class LogControllerTest extends BaseIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testListLogs() {
        webTestClient.get()
            .uri("/api/v1/monitor/log/list")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk();
    }
}

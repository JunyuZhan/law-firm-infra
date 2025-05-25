package com.lawfirm.system.controller;

import com.lawfirm.common.test.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MonitorControllerTest extends BaseIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testMonitorStatus() {
        webTestClient.get()
            .uri("/api/v1/monitor/status")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk();
    }
}

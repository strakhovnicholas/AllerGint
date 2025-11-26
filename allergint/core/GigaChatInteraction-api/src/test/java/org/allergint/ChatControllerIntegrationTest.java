package org.allergint;

import org.allergint.gigachatproxy.dto.GigaChatMetricsRequest;
import org.allergint.gigachatproxy.dto.GigaChatResponse;
import org.allergint.gigachatproxy.dto.GigaChatUserRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .responseTimeout(Duration.ofSeconds(30))
                .build();
    }

    @Test
    void userEndpointReturnsAnswerFromGigaChat() {
        GigaChatUserRequest request = new GigaChatUserRequest();
        request.setClientId(UUID.randomUUID());
        request.setMessage("Привет!");

        webTestClient.post()
                .uri("/ai/user")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GigaChatResponse.class)
                .value(response -> Assertions.assertThat(response.getAnswer()).isNotBlank());
    }

    @Test
    void metricsEndpointReturnsAnswerFromGigaChat() {
        GigaChatMetricsRequest request = new GigaChatMetricsRequest();
        request.setClientId(UUID.randomUUID());
        request.setMetrics(List.of("{\"sleep\":7}", "{\"stress\":3}"));

        webTestClient.post()
                .uri("/ai/metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GigaChatResponse.class)
                .value(response -> Assertions.assertThat(response.getAnswer()).isNotBlank());
    }
}

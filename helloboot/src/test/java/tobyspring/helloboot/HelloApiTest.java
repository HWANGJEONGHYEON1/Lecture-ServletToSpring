package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HelloApiTest {

    @Test
    void helloApi() {
        // http
        // HTTPie

        TestRestTemplate restTemplate = new TestRestTemplate();

        ResponseEntity<String> spring =
                restTemplate.getForEntity("http://localhost:8080/hello?name={name}", String.class, "Spring");

        // statusCode
        Assertions.assertThat(spring.getStatusCode()).isEqualTo(HttpStatus.OK);

        // header
        Assertions.assertThat(spring.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).contains(MediaType.TEXT_PLAIN_VALUE);
        // 응답
        Assertions.assertThat(spring.getBody()).isEqualTo("Hello Spring");
    }

    @Test
    void helloApiException() {
        // http
        // HTTPie

        TestRestTemplate restTemplate = new TestRestTemplate();

        ResponseEntity<String> spring =
                restTemplate.getForEntity("http://localhost:8080/hello?name=", String.class);

        // statusCode
        Assertions.assertThat(spring.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        // header
    }
}

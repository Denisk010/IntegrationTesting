package ru.netology.integrationtesting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTestingApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    private final GenericContainer<?> devApp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);

    @Container
    private final GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeEach
    public void setUp(){
        devApp.start();
        prodApp.start();
    }

    @Test
    void contextLoadsDev() {
        final String expected = "Current profile is dev";
        ResponseEntity<String> devResponse = restTemplate.getForEntity("http://localhost/"
                + devApp.getMappedPort(8080)+ "/profile", String.class);
        System.out.println(devResponse.getBody());
        Assertions.assertEquals(expected, devResponse.getBody());
    }

    @Test
    void contextLoadsProd() {
        final String expected = "Current profile is production";
        ResponseEntity<String> prodResponse = restTemplate.getForEntity("http://localhost/"
                + prodApp.getMappedPort(8081)+ "/profile", String.class);
        System.out.println(prodResponse.getBody());
        Assertions.assertEquals(expected, prodResponse.getBody());
    }
}
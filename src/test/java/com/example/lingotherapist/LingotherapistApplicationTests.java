package com.example.lingotherapist;

import com.example.lingotherapist.service.AiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LingotherapistApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private AiService aiService;

	@Test
	void contextLoads() {
		assertNotNull(aiService);
	}

	@Test
	void homePageLoads() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/",
				String.class
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("LingoTherapist"));
	}

	@Test
	void chatEndpointRespondsToValidMessage() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/api/chat?message=Hello",
				String.class
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertFalse(response.getBody().isEmpty());
	}

	@Test
	void chatEndpointHandlesEmptyMessage() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/api/chat?message=",
				String.class
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Please say something...", response.getBody());
	}

	@Test
	void chatHtmlPageExists() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/chat.html",
				String.class
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("LingoTherapist"));
	}
}
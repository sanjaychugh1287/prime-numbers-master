package com.sanjay.example.prime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanjay.example.prime.util.PrimeNumberUtilTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

/**
 * @author Sanjay Chugh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PrimeController.class)

public class PrimeControllerTest {


    private MockRestServiceServer mockServer;
    private RestTemplate restTemplate = new RestTemplate();
    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    private ObjectMapper mapper = new ObjectMapper();

    // Make sure the service is not currently running when executing these tests
    private String baseUrl = "http://localhost:8080";

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    /**
     * Test prime number generation based on default {@code upperBound}, negative value, and
     * {@link PrimeNumberUtilTest#KNOWN_PRIMES}
     */
    @Test
    public void testPrimeGeneration() throws URISyntaxException, JsonProcessingException {

        // Test default upper bound

        int[] expected = {2, 3, 5, 7, 11, 13, 17, 19};

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(baseUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(expected))
                );
    }

    /**
     * Test the /primes/{number} API resource using combinations of prime and composite numbers
     */
    @Test
    public void testPrimeNumber() {

        // Prime number + sieve
        ResponseEntity<Void> entity = testRestTemplate.getForEntity(baseUrl + "/primes/{0}?algorithm={1}", Void.class, 17, 1);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        // Prime number + fast loop
        entity = testRestTemplate.getForEntity(baseUrl + "/primes/{0}?algorithm={1}", Void.class, 17, 2);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        // Composite number + sieve loop
        entity = testRestTemplate.getForEntity(baseUrl + "/primes/{0}?algorithm={1}", Void.class, 125, 1);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());

        // Composite number + slow loop
        entity = testRestTemplate.getForEntity(baseUrl + "/primes/{0}?algorithm={1}", Void.class, 125, 3);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());

        // Example without supplying algorithm
        entity = testRestTemplate.getForEntity(baseUrl + "/primes/{0}", Void.class, 125);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }
}

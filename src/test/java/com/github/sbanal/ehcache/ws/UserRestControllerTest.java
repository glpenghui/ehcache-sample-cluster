package com.github.sbanal.ehcache.ws;

import com.github.sbanal.ehcache.EhCacheApplication;
import com.github.sbanal.ehcache.model.User;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = { "impl.prefix=TwoStacksPlain", "java.net.preferIPv4Stack=true" })
@SpringApplicationConfiguration(EhCacheApplication.class)
@WebIntegrationTest({"server.port=0", "management.port=0"})
public class UserRestControllerTest {

    RestTemplate restTemplate = new TestRestTemplate();

    @Value("${local.server.port}")
    int testPort;

    private String getRestUrl() {
        return "http://localhost:" + testPort + "/users/{userId}";
    }

    @Test
    public void testGetUser() throws Exception {
        ResponseEntity<User> response = restTemplate.getForEntity(getRestUrl(),
                User.class, Integer.valueOf(1));
        assertThat(response.getBody().getName(), containsString("Test 1"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        restTemplate.delete(getRestUrl(), Integer.valueOf(1));
        ResponseEntity<String> response = restTemplate.getForEntity(getRestUrl(),
                String.class, Integer.valueOf(1));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Ignore
    public void testUpdateUser() throws Exception {
        User user = new User(2, "Test Update " + UUID.randomUUID().toString());
        restTemplate.put(getRestUrl(), user, Integer.valueOf(2));
        RestTemplate restTemplate2 = new TestRestTemplate();
        // FIXME: Second rest call fails
        ResponseEntity<User> response2 = restTemplate2.getForEntity(getRestUrl(),
                User.class, Integer.valueOf(2));
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(user.getName(), response2.getBody().getName());
    }

}
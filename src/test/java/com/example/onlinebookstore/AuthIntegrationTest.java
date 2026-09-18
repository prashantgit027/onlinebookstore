package com.example.onlinebookstore;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void loginReturnsTokenAndAllowsAccessToProtected() {
        Map<String,String> body = new HashMap<>();
        body.put("username","intuser");
        body.put("password","pass");

        // register
        ResponseEntity<String> reg = restTemplate.postForEntity("/api/auth/register", body, String.class);
        Assert.assertTrue(reg.getStatusCode() == HttpStatus.CREATED);

        // login
        ResponseEntity<Map> login = restTemplate.postForEntity("/api/auth/login", body, Map.class);
        Assert.assertEquals(HttpStatus.OK, login.getStatusCode());
        Assert.assertNotNull(login.getBody());
        String token = (String) login.getBody().get("token");
        Assert.assertNotNull(token);

        // access protected endpoint
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> cart = restTemplate.exchange("/api/cart", HttpMethod.GET, entity, String.class);
        Assert.assertEquals(HttpStatus.OK, cart.getStatusCode());
    }
}

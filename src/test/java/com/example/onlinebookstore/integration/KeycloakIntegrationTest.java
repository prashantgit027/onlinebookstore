package com.example.onlinebookstore.integration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

// Requires Docker and a Keycloak Testcontainer setup. Disabled by default.
@Disabled("Enable locally when Docker & Testcontainers available")
public class KeycloakIntegrationTest {
    @Test
    void placeholder() {
        // Template for spinning up Keycloak container with realm import and running OIDC flows.
        // See src/test/resources/keycloak/realm-import.json
    }
}

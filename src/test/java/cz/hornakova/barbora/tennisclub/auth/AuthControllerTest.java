package cz.hornakova.barbora.tennisclub.auth;

import cz.hornakova.barbora.tennisclub.controller.auth.AuthController;
import cz.hornakova.barbora.tennisclub.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_returnsBearerToken_inHeader() {
        String username = "john";
        String password = "secret";
        String token = "jwt-token";

        String basic = "Basic " + Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));

        when(authService.login(username, password)).thenReturn(token);

        ResponseEntity<Void> response = controller.login(basic);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getHeaders().getFirst("Authorization"))
                .isEqualTo("Bearer " + token);

        verify(authService).login(username, password);
    }

    @Test
    void login_handlesDifferentCredentialsCorrectly() {
        String basic = "Basic " + Base64.getEncoder()
                .encodeToString(("alice:pwd123").getBytes(StandardCharsets.UTF_8));

        when(authService.login("alice", "pwd123")).thenReturn("abc");

        ResponseEntity<Void> response = controller.login(basic);

        assertThat(response.getHeaders().getFirst("Authorization"))
                .isEqualTo("Bearer abc");
    }
}
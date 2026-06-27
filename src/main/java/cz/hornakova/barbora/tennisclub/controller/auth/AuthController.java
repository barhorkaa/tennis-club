package cz.hornakova.barbora.tennisclub.controller.auth;

import cz.hornakova.barbora.tennisclub.service.auth.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestHeader("Authorization") String basicAuth
    ) {

        String base64 = basicAuth.substring("Basic ".length());
        String decoded = new String(Base64.getDecoder().decode(base64));

        String[] parts = decoded.split(":");

        String token = authService.login(parts[0], parts[1]);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
    }
}

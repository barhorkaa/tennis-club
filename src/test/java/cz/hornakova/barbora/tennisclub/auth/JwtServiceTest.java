package cz.hornakova.barbora.tennisclub.auth;

import cz.hornakova.barbora.tennisclub.model.entity.User;
import cz.hornakova.barbora.tennisclub.model.entity.UserRole;
import cz.hornakova.barbora.tennisclub.service.auth.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    private JwtService jwtService;

    private static final String SECRET =
            "my-super-secret-key-my-super-secret-key"; // must be >= 32 bytes
    private static final long EXPIRATION = 1000L * 60;

    @BeforeEach
    void setUp() throws Exception {
        jwtService = new JwtService();

        setField("secret", SECRET);
        setField("expiration", EXPIRATION);
    }

    @Test
    void generateToken_createsValidToken() {
        User user = new User();
        user.setUsername("john");
        user.setRole(UserRole.USER);

        String token = jwtService.generateToken(user);

        assertThat(token).isNotNull();
        assertThat(jwtService.extractUsername(token)).isEqualTo("john");
    }

    @Test
    void extractUsername_returnsCorrectUsername() {
        User user = new User();
        user.setUsername("anna");
        user.setRole(UserRole.ADMIN);

        String token = jwtService.generateToken(user);

        String username = jwtService.extractUsername(token);

        assertThat(username).isEqualTo("anna");
    }

    private void setField(String fieldName, Object value) throws Exception {
        Field field = JwtService.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(jwtService, value);
    }
}
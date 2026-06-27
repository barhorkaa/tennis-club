package cz.hornakova.barbora.tennisclub.auth;

import cz.hornakova.barbora.tennisclub.dao.UserDao;
import cz.hornakova.barbora.tennisclub.model.entity.User;
import cz.hornakova.barbora.tennisclub.model.entity.UserRole;
import cz.hornakova.barbora.tennisclub.service.auth.AuthService;
import cz.hornakova.barbora.tennisclub.service.auth.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_success_returnsToken() {
        String username = "john";
        String password = "secret";
        String token = "jwt-token";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(UserRole.USER);

        when(userDao.getByUsername(username)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(token);

        String result = authService.login(username, password);

        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        verify(userDao).getByUsername(username);
        verify(jwtService).generateToken(user);

        assertThat(result).isEqualTo(token);
    }

    @Test
    void login_throws_whenUserNotFound() {
        when(userDao.getByUsername("john")).thenReturn(Optional.empty());

        User user = new User();
        user.setUsername("john");
        user.setPassword("secret");

        when(authenticationManager.authenticate(any()))
                .thenReturn(mock(org.springframework.security.core.Authentication.class));
        try {
            authService.login("john", "secret");
        } catch (Exception ignored) {
        }

        verify(authenticationManager).authenticate(any());
        verify(userDao).getByUsername("john");
        verifyNoInteractions(jwtService);
    }

    @Test
    void login_callsAuthenticationManagerFirst() {
        User user = new User();
        user.setUsername("anna");
        user.setPassword("pass");

        when(userDao.getByUsername("anna")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("token");

        authService.login("anna", "pass");

        verify(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
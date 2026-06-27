package cz.hornakova.barbora.tennisclub.auth;

import cz.hornakova.barbora.tennisclub.dao.UserDao;
import cz.hornakova.barbora.tennisclub.model.entity.User;
import cz.hornakova.barbora.tennisclub.model.entity.UserRole;
import cz.hornakova.barbora.tennisclub.service.auth.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private CustomUserDetailsService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_returnsUserDetails() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("pass");
        user.setRole(UserRole.ADMIN);

        when(userDao.getByUsername("john")).thenReturn(Optional.of(user));

        UserDetails result = service.loadUserByUsername("john");

        assertThat(result.getUsername()).isEqualTo("john");
        assertThat(result.getPassword()).isEqualTo("pass");
        assertThat(result.getAuthorities())
                .extracting("authority")
                .contains("ROLE_ADMIN");

        verify(userDao).getByUsername("john");
    }

    @Test
    void loadUserByUsername_throws_whenUserNotFound() {
        when(userDao.getByUsername("john")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.loadUserByUsername("john"));

        verify(userDao).getByUsername("john");
    }
}
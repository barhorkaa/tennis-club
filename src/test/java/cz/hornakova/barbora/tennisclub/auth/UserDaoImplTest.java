package cz.hornakova.barbora.tennisclub.auth;

import cz.hornakova.barbora.tennisclub.dao.impl.UserDaoImpl;
import cz.hornakova.barbora.tennisclub.model.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserDaoImplTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<User> query;

    @InjectMocks
    private UserDaoImpl userDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByUsername_returnsUser_whenFound() {
        User user = new User();
        user.setUsername("john");

        when(em.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(eq("username"), eq("john"))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(user));

        Optional<User> result = userDao.getByUsername("john");

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("john");
    }

    @Test
    void getByUsername_returnsEmpty_whenNotFound() {
        when(em.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(eq("username"), eq("john"))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of());

        Optional<User> result = userDao.getByUsername("john");

        assertThat(result).isEmpty();
    }

    @Test
    void save_persists_whenNewUser() {
        User user = new User();
        user.setId(null);

        User result = userDao.save(user);

        verify(em).persist(user);
        assertThat(result).isSameAs(user);
    }

    @Test
    void save_merges_whenExistingUser() {
        User user = new User();
        user.setId(1L);

        when(em.merge(user)).thenReturn(user);

        User result = userDao.save(user);

        verify(em).merge(user);
        assertThat(result).isEqualTo(user);
    }
}
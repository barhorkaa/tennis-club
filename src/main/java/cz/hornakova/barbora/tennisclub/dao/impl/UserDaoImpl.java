package cz.hornakova.barbora.tennisclub.dao.impl;

import cz.hornakova.barbora.tennisclub.dao.UserDao;
import cz.hornakova.barbora.tennisclub.model.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<User> getByUsername(String username) {
        List<User> result = em.createQuery("""
            SELECT u FROM User u
            WHERE u.username = :username
            AND u.deleted = false
        """, User.class)
                .setParameter("username", username)
                .getResultList();

        return result.stream().findFirst();
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            em.persist(user);
            return user;
        }

        return em.merge(user);
    }
}
